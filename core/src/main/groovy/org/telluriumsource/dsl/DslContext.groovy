package org.telluriumsource.dsl

import org.telluriumsource.exception.NotWidgetObjectException
import org.telluriumsource.ui.widget.Widget
import org.telluriumsource.framework.Environment

abstract class DslContext extends BaseDslContext {

    //later on, may need to refactor it to use resource file so that we can show message for different localities
    protected static final String XML_DOCUMENT_SCRIPT = """var doc = window.document;
        var xml = null;
        if(doc instanceof XMLDocument){
           xml = new XMLSerializer().serializeToString(doc);
        }else{
           xml = getText(doc.body);
        }
        xml; """

//    def defUi = ui.&Container

    //Must implement this method to define UI
//    remove this constraint so that DSL script does not need to define this method
//    public abstract void defineUi()

    def getWidget(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        def obj = walkToWithException(context, uid)
        if (!(obj instanceof Widget)) {
            println i18nBundle.getMessage("DslContext.UIObject" , {uid})

            throw new NotWidgetObjectException(i18nBundle.getMessage("DslContext.UIObject" , uid))
        }

        //add reference xpath for the widget
        Widget widget = (Widget)obj
        widget.updateParentRef(context.getReferenceLocator())

        return obj
    }

    def onWidget(String uid, String method, Object[] args) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        def obj = walkToWithException(context, uid)
        if (!(obj instanceof Widget)) {
            println i18nBundle.getMessage("DslContext.UIObject" , uid)

//            throw new RuntimeException(i18nManager.getMessage("DslContext.UIObject" , {uid}))
            throw new NotWidgetObjectException(i18nBundle.getMessage("DslContext.UIObject" , uid))
        } else {
            if (obj.metaClass.respondsTo(obj, method, args)) {

                //add reference xpath for the widget
                Widget widget = (Widget)obj
                widget.updateParentRef(context.getReferenceLocator())

                return obj.invokeMethod(method, args)
            } else {

                throw new MissingMethodException(method, obj.metaClass.class, args)
            }
        }
    }

    protected String locatorMapping(WorkflowContext context, loc) {
      return locatorMappingWithOption(context, loc, null)
    }

    protected String locatorMappingWithOption(WorkflowContext context, loc, optLoc) {
      if(Environment.instance.isUseCache() && (!Environment.instance.isUseLocatorWithCache())){
         if(optLoc != null)
            return JQUERY_SELECTOR + optLoc;

         return JQUERY_SELECTOR;
      }else{
        //get ui object's locator

        String locator;
        if(context.noMoreProcess){
          locator = ""
        }else{
          locator = locatorProcessor.locate(context, loc)
        }

        //get the reference locator all the way to the ui object
        if (context.getReferenceLocator() != null){
//            locator = context.getReferenceLocator() + locator
            context.appendReferenceLocator(locator)
            locator = context.getReferenceLocator()
        }

        if(optLoc != null)
          locator = locator + optLoc

        if(context.isUseCssSelector()){
//          locator = optimizer.optimize(JQUERY_SELECTOR + locator.trim())
            locator = postProcessSelector(context, locator.trim())
        } else {
          //make sure the xpath starts with "//"
//          if (locator != null && (!locator.startsWith("//")) && (!locator.startsWith(JQUERY_SELECTOR))) {
            if (locator != null && (!locator.startsWith("//"))){
              locator = "/" + locator
          }
        }

        return locator

      }
    }

    def selectFrame(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectFrame() {String loc ->
            eventHandler.selectFrame(context, loc)
        }
    }

    def selectFrameByIndex(int index) {
        WorkflowContext context = WorkflowContext.getDefaultContext()

        eventHandler.selectFrame(context, "index=${index}")
    }

    def selectParentFrameFrom(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectParentFrame() {String loc ->
            eventHandler.selectFrame(context, loc)
        }
    }

    def selectTopFrameFrom(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectTopFrame() {String loc ->
            eventHandler.selectFrame(context, loc)
        }
    }

    boolean getWhetherThisFrameMatchFrameExpression(String uid, String target) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.getWhetherThisFrameMatchFrameExpression(target) {String loc, String tgt ->
            accessor.getWhetherThisFrameMatchFrameExpression(context, loc, tgt)
        }
    }

    void waitForFrameToLoad(String uid, int timeout) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.waitForFrameToLoad(timeout) {String loc, int tmo ->
            accessor.waitForFrameToLoad(context, loc, Integer.toString(tmo))
        }
    }

    def openWindow(String uid, String url) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.openWindow(url) {String loc, String aurl ->
            eventHandler.openWindow(context, aurl, loc)
        }
    }

    def selectWindow(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.selectWindow() {String loc ->
            eventHandler.selectWindow(context, loc)
        }
    }

    def closeWindow(String uid) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.closeWindow() {String loc ->
            eventHandler.closeWindow(context, loc)
        }
    }

    def selectMainWindow() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.selectWindow(context, null)
    }

    def selectParentWindow() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.selectWindow(context, ".")
    }

    def waitForPopUp(String uid, int timeout) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.waitForPopUp(timeout) {String loc ->
            accessor.waitForPopUp(context, loc, Integer.toString(timeout))
        }
    }

    boolean getWhetherThisWindowMatchWindowExpression(String uid, String target) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        walkToWithException(context, uid)?.getWhetherThisWindowMatchWindowExpression(target) {String loc ->
            accessor.getWhetherThisWindowMatchWindowExpression(context, loc, target)
        }
    }

    def windowFocus() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.windowFocus(context)
    }

    def windowMaximize() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.windowMaximize(context)
    }

    String getBodyText() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getBodyText(context)
    }

    boolean isTextPresent(String pattern) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.isTextPresent(context, pattern)
    }

    String getHtmlSource() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getHtmlSource(context)
    }

    String getExpression(String expression) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getExpression(context, expression)
    }

    String getCookie() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getCookie(context)
    }

    void runScript(String script) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        accessor.runScript(context, script)
    }

    void captureScreenshot(String filename) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        accessor.captureScreenshot(context, filename)
    }

    void captureEntirePageScreenshot(String filename, String kwargs){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        accessor.captureEntirePageScreenshot(context, filename, kwargs)
    }

    String captureScreenshotToString(){
       WorkflowContext context = WorkflowContext.getDefaultContext()
       return accessor.captureScreenshotToString(context)
    }

    String captureEntirePageScreenshotToString(String kwargs){
       WorkflowContext context = WorkflowContext.getDefaultContext()
       return accessor.captureEntirePageScreenshotToString(context, kwargs)
    }

    String retrieveLastRemoteControlLogs(){
       WorkflowContext context = WorkflowContext.getDefaultContext()
       return accessor.retrieveLastRemoteControlLogs(context)
    }
  
    void chooseCancelOnNextConfirmation() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.chooseCancelOnNextConfirmation(context)
    }

    void chooseOkOnNextConfirmation() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.chooseOkOnNextConfirmation(context)
    }

    void answerOnNextPrompt(String answer) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.answerOnNextPrompt(context, answer)
    }

    void goBack() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.goBack(context)
    }

    void refresh() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        eventHandler.refresh(context)
    }

    boolean isAlertPresent() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.isAlertPresent(context)
    }

    boolean isPromptPresent() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.isPromptPresent(context)
    }

    boolean isConfirmationPresent() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.isConfirmationPresent(context)
    }

    String getAlert() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAlert(context)
    }

    String getConfirmation() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getConfirmation(context)
    }

    String getPrompt() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getPrompt(context)
    }

    String getLocation() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getLocation(context)
    }

    String getTitle() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getTitle(context)
    }

    String[] getAllButtons() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllButtons(context)
    }

    String[] getAllLinks() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllLinks(context)
    }

    String[] getAllFields() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllFields(context)
    }

    String[] getAllWindowIds() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllWindowIds(context)
    }

    String[] getAllWindowNames() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllWindowNames(context)
    }

    String[] getAllWindowTitles() {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        return accessor.getAllWindowTitles(context)
    }

    void waitForPageToLoad(int timeout) {
        WorkflowContext context = WorkflowContext.getDefaultContext()
        accessor.waitForPageToLoad(context, Integer.toString(timeout))
    }

    public String getXMLDocument(){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        String xml =  getEval(context, XML_DOCUMENT_SCRIPT)

        return xml
    }

    //let the missing property return the a string of the properity, this is useful for the onWidget method
    //so that we can pass in widget method directly, instead of passing in the method name as a String
    def propertyMissing(String name) {
        println i18nBundle.getMessage("BaseDslContext.PropertyIsMissing" , name)
        return name
    }
}