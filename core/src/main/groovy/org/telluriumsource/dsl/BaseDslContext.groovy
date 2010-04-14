package org.telluriumsource.dsl


import org.telluriumsource.exception.UiObjectNotFoundException;


import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.ui.locator.JQueryOptimizer;
import org.telluriumsource.ui.locator.LocatorProcessor;
import org.telluriumsource.ui.object.StandardTable;

import org.telluriumsource.ui.object.UiObject;
import org.telluriumsource.ui.locator.JQueryProcessor
import org.telluriumsource.ui.locator.XPathProcessor
import org.json.simple.JSONArray
import org.telluriumsource.framework.Environment
import org.telluriumsource.entity.DiagnosisOption
import org.telluriumsource.entity.DiagnosisRequest
import org.telluriumsource.entity.DiagnosisResponse
import org.telluriumsource.entity.UiModuleValidationResponse
import org.telluriumsource.ui.object.Repeat
import org.telluriumsource.entity.KeyValuePairs
import org.telluriumsource.entity.UiByTagResponse
import org.telluriumsource.ui.object.AllPurposeObject
import org.telluriumsource.ui.builder.AllPurposeObjectBuilder

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 *
 */
abstract class BaseDslContext extends GlobalDslContext {

  protected IResourceBundle i18nBundle

  protected static final String JQUERY_SELECTOR = "jquery="
  protected static final String DEFAULT_XPATH = "default"
  protected static final String JAVASCRIPT_XPATH = "javascript-xpath"
  protected static final String AJAXSLT_XPATH = "ajaxslt"
  protected static final String LOCATOR = "locator"
  protected static final String OPTIMIZED_LOCATOR = "optimized"

  public static final String KEY = "key"
  public static final String OBJECT = "obj"
  public static final String GENERATED = "generated"

  protected JQueryOptimizer optimizer = new JQueryOptimizer()

  UiDslParser ui = new UiDslParser()

  LocatorProcessor locatorProcessor = new LocatorProcessor()

  public BaseDslContext(){
	  i18nBundle = Environment.instance.myResourceBundle()
  }
  
  abstract protected String locatorMapping(WorkflowContext context, loc)

  abstract protected String locatorMappingWithOption(WorkflowContext context, loc, optLoc)

  protected geti18nBundle() {
    return this.i18nBundle;
  }

  protected String postProcessSelector(WorkflowContext context, String jqs) {
//    String locator = jqs

    String optimized = optimizer.optimize(jqs)

/*    if (context.isUseUiModuleCache()) {
      JSONObject obj = new JSONObject()
      //meta command shoud not be null for locators
      MetaCmd metaCmd = context.extraMetaCmd()
      obj.put(LOCATOR, locator)
      obj.put(OPTIMIZED_LOCATOR, optimized)

      obj.put(MetaCmd.UID, metaCmd.getProperty(MetaCmd.UID))
      obj.put(MetaCmd.CACHEABLE, metaCmd.getProperty(MetaCmd.CACHEABLE))
      obj.put(MetaCmd.UNIQUE, metaCmd.getProperty(MetaCmd.UNIQUE))

      String jsonjqs = obj.toString()

      return UIModule_CACHE + jsonjqs
    }
    */
    
    return JQUERY_SELECTOR + optimized
  }

  protected UiModuleValidationResponse parseUseUiModuleResponse(String result) {
    String out = result;
    if (result.startsWith("OK,")) {
      out = result.substring(3);
    }

    if(out.length() > 0){
      Map map = reader.read(out);
      UiModuleValidationResponse response = new UiModuleValidationResponse(map);

      return response;
    }

    return null;
  }

//  protected UiModuleValidationResponse parseUseUiModuleResponse(Map result) {
  protected Map parseUseUiModuleResponse(Map result) {
    return result;
  }

  def customUiCall(String uid, String method, Object[] args) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    return walkToWithException(context, uid).customMethod() {loc ->
      String locator = locatorMapping(context, loc)
      Object[] list = [context, locator, args].flatten()
      return extension.invokeMethod(method, list)
    }
  }

  def customDirectCall(String method, Object[] args) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    Object[] list = [context, args].flatten()
    return extension.invokeMethod(method, list)
  }

  public void triggerEventOn(String uid, String event) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid).customMethod() {loc ->
      String locator = locatorMapping(context, loc)
      Object[] list = [context, locator, event]

      extension.invokeMethod("triggerEvent", list)
    }
  }

  //uid should use the format table2[2][3] for Table or list[2] for List
  def getUiElement(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = ui.walkTo(context, uid)

    return obj
  }

  def UiObject walkToWithException(WorkflowContext context, String uid) {
    UiObject obj = ui.walkTo(context, uid)
    if (obj != null) {
      context.attachMetaCmd(uid, obj.amICacheable(), true)
      context.putContext(WorkflowContext.DSLCONTEXT, this)

      return obj
    }

    throw new UiObjectNotFoundException(i18nBundle.getMessage("BaseDslContext.CannotFindUIObject", uid))
  }

  String getConsoleInput() {
    return (String) System.in.withReader {
      it.readLine()
    }
  }

  def click(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.click() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.click(context, locator, events)
    }
  }

  def doubleClick(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.doubleClick() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.doubleClick(context, locator, events)
    }
  }

  def clickAt(String uid, String coordination) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.clickAt(coordination) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.clickAt(context, locator, coordination, events)
    }
  }

  def check(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.check() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.check(context, locator, events)
    }
  }

  def uncheck(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.uncheck() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.uncheck(context, locator, events)
    }
  }

  def type(String uid, def input) {
    String str = (input==null) ? "" : input.toString();
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.type(str) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.type(context, locator, str, events)
    }
  }


  def keyType(String uid, def input) {
    String str = (input==null) ? "" : input.toString();
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.keyType(str) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.keyType(context, locator, str, events)
    }
  }


  def typeAndReturn(String uid, def input) {
    String str = (input==null) ? "" : input.toString();
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.typeAndReturn(str) {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.typeAndReturn(context, locator, str, events)
    }
  }

  def altKeyUp() {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      eventHandler.altKeyUp(context)
  }
  def altKeyDown() {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      eventHandler.altKeyDown(context)
  }
  def ctrlKeyUp() {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      eventHandler.ctrlKeyUp(context)
  }
  def ctrlKeyDown() {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      eventHandler.ctrlKeyDown(context)
  }
  def shiftKeyUp() {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      eventHandler.shiftKeyUp(context)
  }
  def shiftKeyDown() {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      eventHandler.shiftKeyDown(context)
  }
  def metaKeyUp() {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      eventHandler.metaKeyUp(context)
  }
  def metaKeyDown() {
      WorkflowContext context = WorkflowContext.getDefaultContext()
      eventHandler.metaKeyDown(context)
  }

  def clearText(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.clearText() {loc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.clearText(context, locator, events)
    }
  }

  def select(String uid, String target) {
    selectByLabel(uid, target)
  }

  def selectByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.selectByLabel(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(context, locator, optloc, events)
    }
  }

  def selectByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.selectByValue(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(context, locator, optloc, events)
    }
  }

  def selectByIndex(String uid, int target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.selectByIndex(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(context, locator, optloc, events)
    }
  }

  def selectById(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.selectById(target) {loc, optloc, String[] events ->
      String locator = locatorMapping(context, loc)
      eventHandler.select(context, locator, optloc, events)
    }
  }

  def addSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.addSelectionByLabel(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.addSelection(context, locator, optloc)
    }
  }

  def addSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.addSelectionByValue(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.addSelection(context, locator, optloc)
    }
  }

  def removeSelectionByLabel(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.removeSelectionByLabel(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeSelection(context, locator, optloc)
    }
  }

  def removeSelectionByValue(String uid, String target) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.removeSelectionByValue(target) {loc, optloc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeSelection(context, locator, optloc)
    }
  }

  def removeAllSelections(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.removeAllSelections() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.removeAllSelections(context, locator)
    }
  }

  String[] getSelectOptions(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectOptions() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectOptions(context, locator)
    }
  }

  String[] getSelectValues(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectValues() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectValues(context, locator)
    }
  }

  String[] getSelectedLabels(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectedLabels() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedLabels(context, locator)
    }
  }

  String getSelectedLabel(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectedLabel() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedLabel(context, locator)
    }
  }

  String[] getSelectedValues(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectedValues() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedValues(context, locator)
    }
  }

  String getSelectedValue(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectedValue() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedValue(context, locator)
    }
  }

  String[] getSelectedIndexes(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIndexes() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIndexes(context, locator)
    }
  }

  String getSelectedIndex(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIndex() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIndex(context, locator)
    }
  }

  String[] getSelectedIds(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectedIds() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedIds(context, locator)
    }
  }

  String getSelectedId(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.getSelectedId() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getSelectedId(context, locator)
    }
  }

  boolean isSomethingSelected(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)

    return obj.isSomethingSelected() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isSomethingSelected(context, locator)
    }
  }

  String waitForText(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    return walkToWithException(context, uid)?.waitForText(timeout) {loc, int tmo ->
      String locator = locatorMapping(context, loc)
      accessor.waitForText(context, locator, tmo)
    }
  }

  boolean isElementPresent(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    if(this.exploreUiModuleCache() && Environment.instance.isUseTelluriumApi()){
      walkToWithException(context, uid);
//      return extension.isElementPresent(context, uid);
      return accessor.isElementPresent(context, uid); 
    }else{
      def obj = walkToWithException(context, uid);
      return obj.isElementPresent() {loc ->
        String locator = locatorMapping(context, loc);
        accessor.isElementPresent(context, locator);
      }
    }
  }

  boolean isVisible(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    return obj.isVisible() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isVisible(context, locator)
    }
  }

  boolean isChecked(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    return obj.isChecked() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.isChecked(context, locator)
    }
  }

  def boolean isEnabled(String uid) {
    return !isDisabled(uid);
  }

  boolean waitForElementPresent(String uid, int timeout) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    return obj.waitForElementPresent(timeout) {loc ->
      String locator = locatorMapping(context, loc)
      accessor.waitForElementPresent(context, locator, timeout)
    }
  }

  boolean waitForElementPresent(String uid, int timeout, int step) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    return obj.waitForElementPresent(timeout, step) {loc ->
      String locator = locatorMapping(context, loc)
      accessor.waitForElementPresent(context, locator, timeout, step)
    }
  }

  boolean waitForCondition(String script, int timeoutInMilliSecond) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    accessor.waitForCondition(context, script, Integer.toString(timeoutInMilliSecond))
  }

  String getText(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.getText() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getText(context, locator)
    }
  }

  String getValue(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.getValue() {loc ->
      String locator = locatorMapping(context, loc)
      accessor.getValue(context, locator)
    }
  }

  String getLink(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.getLink() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(context, locator + attr)
    }
  }

  String getImageSource(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.getImageSource() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(context, locator + attr)
    }
  }

  String getImageAlt(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.getImageAlt() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(context, locator + attr)
    }
  }

  String getImageTitle(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.getImageTitle() {loc, attr ->
      String locator = locatorMapping(context, loc)
      accessor.getAttribute(context, locator + attr)
    }
  }

  def submit(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.submit() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.submit(context, locator)
    }
  }

  boolean isEditable(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    return walkToWithException(context, uid)?.isEditable() {loc ->
      String locator = locatorMapping(context, loc)
      return accessor.isEditable(context, locator)
    }
  }

  String getEval(String script) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    return accessor.getEval(context, script)
  }

  def mouseOver(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseOver() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseOver(context, locator)
    }
  }

  def mouseOut(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseOut() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseOut(context, locator)
    }
  }

  def dragAndDrop(String uid, String movementsString) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.dragAndDrop(movementsString) {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.dragAndDrop(context, locator, movementsString)
    }
  }

  def dragAndDropTo(String sourceUid, String targetUid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def src = walkToWithException(context, sourceUid)

    WorkflowContext ncontext = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def target = walkToWithException(ncontext, targetUid)

    if (src != null && target != null) {
      String srcLocator = locatorMapping(context, src.locator)
      String targetLocator = locatorMapping(ncontext, target.locator)
      eventHandler.dragAndDropToObject(context, srcLocator, targetLocator)
    }
  }

  def mouseDown(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseDown() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDown(context, locator)
    }
  }

  def mouseDownRight(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseDownRight() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownRight(context, locator)
    }
  }

  def mouseDownAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseDownAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownAt(context, locator, coordinate)
    }
  }

  def mouseDownRightAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseDownRightAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseDownRightAt(context, locator, coordinate)
    }
  }

  def mouseUp(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseUp() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUp(context, locator)
    }
  }

  def mouseUpRight(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseUpRight() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUpRight(context, locator)
    }
  }

  def mouseUpRightAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseUpRightAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseUpRightAt(context, locator, coordinate)
    }
  }

  def mouseMove(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseMove() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseMove(context, locator)
    }
  }

  def mouseMoveAt(String uid, String coordinate) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.mouseMoveAt() {loc ->
      String locator = locatorMapping(context, loc)
      eventHandler.mouseMoveAt(context, locator, coordinate)
    }
  }

  Number getXpathCount(String xpath) {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    return accessor.getXpathCount(context, xpath)
  }

  String captureNetworkTraffic(String type) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    return accessor.captureNetworkTraffic(context, type)
  }

  void addCustomRequestHeader(String key, String value) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    accessor.addCustomRequestHeader(context, key, value)
  }

  Number getCssSelectorCount(String cssSelector) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, false)
    //do not cache any selectors for counting
    context.updateCacheableForMetaCmd(false);
    String jq = postProcessSelector(context, cssSelector.trim())

    return extension.getCssSelectorCount(context, jq)
  }

  Number getLocatorCount(String locator) {
    if (this.exploreCssSelector())
      return getCssSelectorCount(locator)

    return getXpathCount(locator)
  }

  String getXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    walkToWithException(context, uid)?.getXPath() {loc ->
      locatorMapping(context, loc)
    }

    String locator = context.getReferenceLocator()
//    if (locator != null && (!locator.startsWith("//")) && (!locator.startsWith(JQUERY_SELECTOR))) {
    if (locator != null && (!locator.startsWith("//"))) {
      locator = "/" + locator
    }

    return locator
  }

  String getSelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    walkToWithException(context, uid)?.getSelector() {loc ->
      locatorMapping(context, loc)
    }

    String locator = context.getReferenceLocator()
    locator = optimizer.optimize(locator.trim())

    return JQUERY_SELECTOR + locator
  }

  String getLocator(String uid) {
    if (this.exploreCssSelector()) {
      return getSelector(uid)
    }

    return getXPath(uid)
  }

  String[] getCSS(String uid, String cssName) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid)?.getCSS(cssName) {loc ->
      String locator = locatorMapping(context, loc)

      def out = extension.getCSS(context, locator, cssName)

      return (ArrayList) parseSeleniumJSONReturnValue(out)
    }
  }

  //This only works for jQuery selector
  String[] getAllTableCellText(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
      def obj = walkToWithException(context, uid)
      if(obj != null && obj.respondsTo("getAllTableCellText")){
        def out = extension.getAllTableBodyText(context, uid)
        
        return (ArrayList) parseSeleniumJSONReturnValue(out)
      }

    } else {
      return walkToWithException(context, uid)?.getAllTableCellText() {loc, cell ->
        //for bulk data, the selector will not return a unique element
        context.updateUniqueForMetaCmd(false)
        //force not to cache the selector
        context.updateCacheableForMetaCmd(false)
        String locator = locatorMappingWithOption(context, loc, cell)

        def out = extension.getAllText(context, locator)

        return (ArrayList) parseSeleniumJSONReturnValue(out)
      }
    }

    return null;
  }

  //This only works for jQuery selector and Standard Table
  String[] getAllTableCellTextForTbody(String uid, int index) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    return walkToWithException(context, uid)?.getAllTableCellTextForTbody(index) {loc, cell ->
      context.updateUniqueForMetaCmd(false)
      //force not to cache the selector
      context.updateCacheableForMetaCmd(false)
      String locator = locatorMappingWithOption(context, loc, cell)

      def out = extension.getAllText(context, locator)

      return (ArrayList) parseSeleniumJSONReturnValue(out)
    }
  }

  int getTableHeaderColumnNumByXPath(String uid) {
    //force to use xpath
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableHeaderColumnNumByXPath {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableFootColumnNumByXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableFootColumnNumByXPath {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxRowNumByXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableMaxRowNumByXPath() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxColumnNumByXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    def obj = walkToWithException(context, uid)

    return obj.getTableMaxColumnNumByXPath() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxRowNumForTbodyByXPath(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxRowNumForTbodyByXPath(ntbody) {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxColumnNumForTbodyByXPath(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxColumnNumForTbodyByXPath(ntbody) {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableMaxTbodyNumByXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    StandardTable obj = (StandardTable) walkToWithException(context, uid)

    return obj.getTableMaxTbodyNumByXPath() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  int getTableHeaderColumnNumBySelector(String uid) {
    //force to use jQuery selector
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableHeaderColumnNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

      return extension.getCssSelectorCount(context, locator)
    }
  }

  int getTableFootColumnNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableFootColumnNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

      return extension.getCssSelectorCount(context, locator)
    }
  }

  int getTableMaxRowNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableMaxRowNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

      return extension.getCssSelectorCount(context, locator)
    }
  }

  int getTableMaxColumnNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableMaxColumnNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

      return extension.getCssSelectorCount(context, locator)
    }
  }

  int getTableMaxRowNumForTbodyBySelector(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    StandardTable obj = (StandardTable) walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableMaxRowNumForTbodyBySelector(ntbody) {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

      return extension.getCssSelectorCount(context, locator)
    }
  }

  int getTableMaxColumnNumForTbodyBySelector(String uid, int ntbody) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    StandardTable obj = (StandardTable) walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableMaxColumnNumForTbodyBySelector(ntbody) {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

      return extension.getCssSelectorCount(context, locator)
    }
  }

  int getTableMaxTbodyNumBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    StandardTable obj = (StandardTable) walkToWithException(context, uid)
    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
    context.updateCacheableForMetaCmd(false)

    return obj.getTableMaxTbodyNumBySelector() {loc, optloc ->
      String locator = locatorMappingWithOption(context, loc, optloc)
//      locator = locator + optloc
//      String jq = postProcessSelector(context, locator.trim())

      return extension.getCssSelectorCount(context, locator)
    }
  }

  int getTeTableHeaderColumnNum(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)

    return extension.getTeTableHeaderColumnNum(context, uid)
  }

  int getTableHeaderColumnNum(String uid) {
    if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
      return getTeTableHeaderColumnNum(uid)
    }else{
      if (this.exploreCssSelector())
        return getTableHeaderColumnNumBySelector(uid)

      return getTableHeaderColumnNumByXPath(uid)      
    }
  }

  int getTeTableFootColumnNum(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)

    return extension.getTeTableFootColumnNum(context, uid)
  }

  int getTableFootColumnNum(String uid) {
    if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
      return getTeTableFootColumnNum(uid)
    }else{
      if (this.exploreCssSelector())
        return getTableFootColumnNumBySelector(uid)

      return getTableFootColumnNumByXPath(uid)
    }
  }

  int getTeTableRowNum(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)

    return extension.getTeTableRowNum(context, uid)
  }

  int getTableMaxRowNum(String uid) {
    if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
      return getTeTableRowNum(uid)
    }else{
      if (this.exploreCssSelector())
        return getTableMaxRowNumBySelector(uid)

      return getTableMaxRowNumByXPath(uid)
    }
   }

  int getTeTableColumnNum(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)

    return extension.getTeTableColumnNum(context, uid)
  }

  int getTableMaxColumnNum(String uid) {
    if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
      return getTeTableColumnNum(uid)
    }else{
      if (this.exploreCssSelector())
        return getTableMaxColumnNumBySelector(uid)

      return getTableMaxColumnNumByXPath(uid)
    }  
  }

  int getTeTableRowNumForTbody(String uid, int ntbody){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)

    return extension.getTeTableRowNumForTbody(context, uid, ntbody)
  }

  int getTableMaxRowNumForTbody(String uid, int ntbody) {
    if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
      return getTeTableRowNumForTbody(uid, ntbody)
    }else{
      if (this.exploreCssSelector())
        return getTableMaxRowNumForTbodyBySelector(uid, ntbody)

      return getTableMaxRowNumForTbodyByXPath(uid, ntbody)
    }
  }

  int getTeTableColumnNumForTbody(String uid, int ntbody){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)

    return extension.getTeTableColumnNumForTbody(context, uid, ntbody)
  }

  int getTableMaxColumnNumForTbody(String uid, int ntbody) {
    if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
      return getTeTableColumnNumForTbody(uid, ntbody)
    }else{
      if (this.exploreCssSelector())
        return getTableMaxColumnNumForTbodyBySelector(uid, ntbody)

      return getTableMaxColumnNumForTbodyByXPath(uid, ntbody)
    }    
  }

  int getTeTableTbodyNum(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)

    return extension.getTeTableTbodyNum(context, uid)
  }

  int getTableMaxTbodyNum(String uid) {
    if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
      return getTeTableTbodyNum(uid)
    }else{
      if (this.exploreCssSelector())
        return getTableMaxTbodyNumBySelector(uid)

      return getTableMaxTbodyNumByXPath(uid)
    }
  }

  int getRepeatNumByXPath(String uid){
    WorkflowContext context = WorkflowContext.getDefaultContext()
    Repeat obj = (Repeat) walkToWithException(context, uid)
    return obj.getRepeatNum() {loc ->
      String locator = locatorMapping(context, loc)

      return accessor.getXpathCount(context, locator)
    }
  }

  int getRepeatNumByCssSelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    Repeat obj = (Repeat) walkToWithException(context, uid)
    return obj.getRepeatNum() {loc ->
      String locator = locatorMapping(context, loc)
      String jq = postProcessSelector(context, locator.trim())

      return extension.getCssSelectorCount(context, jq)
    }
  }
  int getTeRepeatNum(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)

    return extension.getRepeatNum(context, uid)
  }

  int getRepeatNum(String uid) {
    if (this.exploreUiModuleCache() && this.isUseTelluriumApi()) {
      return getTeRepeatNum(uid)
    } else {
      if (this.exploreCssSelector())
        return getRepeatNumByCssSelector(uid)

      return getRepeatNumByXPath(uid)
    }
  }

  int getListSizeByXPath(String uid) {
    WorkflowContext context = WorkflowContext.getDefaultContext()
    org.telluriumsource.ui.object.List obj = (org.telluriumsource.ui.object.List) walkToWithException(context, uid)
    return obj.getListSizeByXPath() {loc ->
      String locator = locatorMapping(context, loc)
      locator
    }
  }

  //use CSS Selector to optimize the list operations
  int getListSizeBySelector(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(true, this.exploreUiModuleCache())
    org.telluriumsource.ui.object.List obj = (org.telluriumsource.ui.object.List) walkToWithException(context, uid)
//    context.updateUniqueForMetaCmd(false)
    //force not to cache the selector
//    context.updateCacheableForMetaCmd(false)
    return obj.getListSizeByCssSelector() {loc, separators ->
      String locator = locatorMapping(context, loc)

      return extension.getListSize(context, locator, separators)
    }
  }

  int getTeListSize(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)

    return extension.getTeListSize(context, uid)
  }

  int getListSize(String uid) {
    if(this.exploreUiModuleCache() && this.isUseTelluriumApi()){
      return getTeListSize(uid)
    }else{
      if (this.exploreCssSelector())
        return getListSizeBySelector(uid)

      return getListSizeByXPath(uid)
    }    
  }
  
  def boolean isDisabled(String uid) {
     WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    return walkToWithException(context, uid).isDisabled() {loc ->
      String locator = locatorMapping(context, loc)

      extension.isDisabled(context, locator)
    }
  }

  def getParentAttribute(String uid, String attribute) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.getAttribute(attribute) {loc, attr ->
      String locator = locatorMapping(context, loc)
      if (this.exploreCssSelector()) {
        String ploc = JQueryProcessor.popLast(locator)
        return accessor.getAttribute(context, ploc + "@${attr}")
      } else {
        String ploc = XPathProcessor.popXPath(locator)
        return accessor.getAttribute(context, ploc + "/self::node()@${attr}")
      }
    }
  }

  def getAttribute(String uid, String attribute) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.getAttribute(attribute) {loc, attr ->
      String locator = locatorMapping(context, loc)
      if (this.exploreCssSelector()) {
        return accessor.getAttribute(context, locator + "@${attr}")
      } else {
        return accessor.getAttribute(context, locator + "/self::node()@${attr}")
      }
    }
  }

  def hasCssClass(String uid, String cssClass) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    String[] strings = walkToWithException(context, uid)?.hasCssClass() {loc, classAttr ->
      String locator = locatorMapping(context, loc)
      String clazz
      if (this.exploreCssSelector()) {
        clazz = accessor.getAttribute(context, locator + "@${classAttr}")
      } else {
        clazz = accessor.getAttribute(context, locator + "/self::node()@${classAttr}")
      }
      if (clazz != null && clazz.trim().length() > 0) {
        return clazz.split(" ")
      }
      return null
    }
    if (strings?.length) {
      for (i in 0..<strings?.length) {
        if (cssClass.equalsIgnoreCase(strings[i])) {
          return true
        }
      }
    }
    return false
  }

  //Toggle displaying each of the set of matched elements.
  //If they are shown, toggle makes them hidden.
  //If they are hidden, toggle makes them shown
  void toggle(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    walkToWithException(context, uid).toggle() {loc ->
      String locator = locatorMapping(context, loc)

      extension.toggle(context, locator)
    }
  }

  //delay in milliseconds
  void show(String uid, int delay) {
    if(!this.exploreUiModuleCache() || !this.isUseTelluriumApi()){
      println(i18nBundle.getMessage("BaseDslContext.ShowRequirement", uid))
    }else{
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      def obj = walkToWithException(context, uid)
      if(obj != null){
        extension.showUi(context, uid)
        pause(delay)
        extension.cleanUi(context, uid)
        pause(200)
      }
    }
  }

  void startShow(String uid) {
    if(!this.exploreUiModuleCache() || !this.isUseTelluriumApi()){
      println(i18nBundle.getMessage("BaseDslContext.ShowRequirement", uid))
    }else{
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      def obj = walkToWithException(context, uid)
      if(obj != null){
        extension.showUi(context, uid)
        pause(200)
      }
    }
  }

  void endShow(String uid) {
    if(!this.exploreUiModuleCache() || !this.isUseTelluriumApi()){
      println(i18nBundle.getMessage("BaseDslContext.ShowRequirement", uid))
    }else{
      WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

      def obj = walkToWithException(context, uid)
      if(obj != null){
        extension.cleanUi(context, uid)
        pause(200)
      }
    }
  }

  public void dump(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    if (obj != null) {
      context.setNewUid(uid)
      obj.traverse(context)
      ArrayList list = context.getUidList()

      println(i18nBundle.getMessage("BaseDslContext.DumpLocatorInformation", uid))
      println("-------------------------------------------------------")
      list.each {String key ->
        String loc = getLocator(key)
        context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
        walkToWithException(context, key)

        if (this.exploreCssSelector()) {
          loc = this.postProcessSelector(context, loc)
        }
        println("${key}: ${loc}")
      }
      println("-------------------------------------------------------\n")
    }
  }

  public String toString(String uid) {
/*    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid);
    JSONArray arr = new JSONArray();
    context.setJSONArray(arr);
    obj.treeWalk(context);
    JSONArray jsa = context.getJSONArray();

    return jsa.toString();*/
    JSONArray ar = toJSONArray(uid);
    if(ar != null)
      return ar.toString();

    return null;
  }

  public JSONArray toJSONArray(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid);
    JSONArray arr = new JSONArray();
    context.setJSONArray(arr);
    obj.treeWalk(context);

    return context.getJSONArray();
  }

  public void validate(String uid) {
    UiModuleValidationResponse response = getUiModuleValidationResult(uid);
    response?.showMe();
  }

  public UiModuleValidationResponse getUiModuleValidationResult(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid);
    JSONArray arr = new JSONArray();
    context.setJSONArray(arr);
    obj.treeWalk(context);
    JSONArray jsa = context.getJSONArray();
    
//    def out = extension.getValidateUiModule(context, jsa.toString());
    def out = extension.getValidateUiModule(context, jsa);

    return parseUseUiModuleResponse(out);
  }

  public DiagnosisResponse getDiagnosisResult(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.diagnose() {loc ->
      String locator = locatorMapping(context, loc)
      String ploc;
      if (this.exploreCssSelector()) {
        ploc = JQueryProcessor.popLast(locator)
      } else {
        ploc = XPathProcessor.popXPath(locator)
      }
      DiagnosisOption options = new DiagnosisOption()
      DiagnosisRequest request = new DiagnosisRequest(uid, ploc, loc, options)

      def out = extension.getDiagnosisResponse(context, locator, request.toJSON())

      return new DiagnosisResponse(parseSeleniumJSONReturnValue(out))
    }
  }

  public DiagnosisResponse getDiagnosisResult(String uid, DiagnosisOption options) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)?.diagnose() {loc ->
      String locator = locatorMapping(context, loc)
      String ploc
      if (this.exploreCssSelector()) {
        ploc = JQueryProcessor.popLast(locator)
      } else {
        ploc = XPathProcessor.popXPath(locator)
      }

      DiagnosisRequest request = new DiagnosisRequest(uid, ploc, loc, options)

      def out = extension.getDiagnosisResponse(context, locator, request.toJSON())

      return new DiagnosisResponse(parseSeleniumJSONReturnValue(out))
    }
  }

  public void diagnose(String uid) {
    DiagnosisResponse resp = this.getDiagnosisResult(uid)
    resp.showMe()
  }

  public void diagnose(String uid, DiagnosisOption options) {
    DiagnosisResponse resp = this.getDiagnosisResult(uid, options)
    resp.showMe()
  }

  public String toHTML(String uid) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    def obj = walkToWithException(context, uid)
    return obj.toHTML()
  }

  public String toHTML() {
    StringBuffer sb = new StringBuffer(128)
    ui.registry.each {String key, UiObject val ->
      sb.append(val.toHTML())
    }

    return sb.toString()
  }

  public java.util.List getHTMLSourceResponse(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    walkToWithException(context, uid)
    def out = extension.getHTMLSource(context, uid)

    return parseSeleniumJSONReturnValue(out);
  }

  public void getHTMLSource(String uid){
    java.util.List list =  getHTMLSourceResponse(uid);
    if(list != null && list.size() > 0){
      list.each{Map map ->
        String key = map.get("key");
        String val = map.get("val");
        println(key + ": ");
        println("");
        println(val);
        println("");
      }
    }
  }

  void setTimeout(long timeoutInMilliseconds) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    accessor.setTimeout(context, (new Long(timeoutInMilliseconds)).toString())
  }

  boolean isCookiePresent(String name) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    return accessor.isCookiePresent(context, name)
  }

  String getCookie() {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    return accessor.getCookie(context)
  }

  String getCookieByName(String name) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    return accessor.getCookieByName(context, name)
  }

  void createCookie(String nameValuePair, String optionsString) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    accessor.createCookie(context, nameValuePair, optionsString)
  }

  void deleteCookie(String name, String optionsString) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    accessor.deleteCookie(context, name, optionsString)
  }

  void deleteAllVisibleCookies() {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    accessor.deleteAllVisibleCookies(context)
  }

  //NEW Cookie APIs based on jQuery Cookie plugin
  void deleteAllCookiesByJQuery() {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    extension.deleteAllCookiesByJQuery(context)
  }

  void deleteCookieByJQuery(String cookieName) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    extension.deleteCookieByJQuery(context, cookieName);
  }

  void setCookieByJQuery(String cookieName, String value, def options){
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.setCookieByJQuery(context, cookieName, value, options);
  }

  void setCookieByJQuery(String cookieName, String value){
    setCookieByJQuery(cookieName, value, null);
  }

  String getCookieByJQuery(String cookieName){
    WorkflowContext context = WorkflowContext.getDefaultContext();

    return extension.getCookieByJQuery(context, cookieName);
  }

  UiByTagResponse getUiByTag(String tag, Map filters){
    KeyValuePairs pairs = new KeyValuePairs(filters);
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    def out = extension.getUiByTag(context, tag, pairs.toJSON());

    UiByTagResponse response = new UiByTagResponse(tag, filters, out);
    AllPurposeObjectBuilder builder = new AllPurposeObjectBuilder();
    if(out != null && out.size() > 0){
      for(int i=0; i<out.size(); i++){
        AllPurposeObject obj = builder.build(out[i], out[i], tag, filters, false);
        ui.addUiObjectToRegistry(obj);
      }
    }
    
    return response;
  }

  void removeMarkedUids(String tag){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.removeMarkedUids(context, tag);
  }
}