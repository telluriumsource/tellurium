package org.telluriumsource.dsl

import org.telluriumsource.component.event.EventHandler
import org.telluriumsource.component.data.Accessor
import org.telluriumsource.component.custom.Extension
import org.telluriumsource.framework.Environment
import org.telluriumsource.util.Helper

import org.telluriumsource.component.dispatch.Dispatcher
import org.telluriumsource.component.bundle.BundleProcessor
import org.stringtree.json.JSONReader
import org.telluriumsource.entity.EngineState
import org.telluriumsource.entity.CacheUsageResponse
import org.telluriumsource.util.LogLevels

/**
 * Global methods, which should not be tired to an individual UI module
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 26, 2009
 *
 *
 */

public class GlobalDslContext {

  //decoupling eventhandler, locateProcessor, and accessor from UI objects
  //and let DslContext to handle all of them directly. This will also make
  //the framework reconfiguration much easier.
  EventHandler eventHandler = new EventHandler()
  Accessor accessor = new Accessor()
  Extension extension = new Extension()

  protected JSONReader reader = new JSONReader()

  protected Object parseSeleniumJSONReturnValue(String out) {
    if (out.startsWith("OK,")) {
      out = out.substring(3);
    }
    if(out.length() >0){
      return reader.read(out);
    }

    return null;
  }

  protected Object parseSeleniumJSONReturnValue(Map out) {
//    return out.get("map");
    return out;
  }

  protected Object parseSeleniumJSONReturnValue(List out) {
    return out;
  }

  public EngineState getEngineState(){
    WorkflowContext context = WorkflowContext.getDefaultContext();
//    String out = extension.getEngineState(context);
//    Map map = parseSeleniumJSONReturnValue(out);
    Map map = extension.getEngineState(context);
    EngineState state = new EngineState();
    state.parseJson(map);
    
    return state;
  }
  //flag to decide whether we should use jQuery Selector
  protected boolean exploreCssSelector() {
    return Environment.instance.isUseCssSelector();
  }

  //flag to decide whether we should cache jQuery selector
  protected boolean exploreUiModuleCache() {
    return Environment.instance.isUseCache()
  }

  public void enableClosestMatch(){
    Environment.instance.useClosestMatch(true);
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.useClosestMatch(context, true);
  }

  public void disableClosestMatch(){
    Environment.instance.useClosestMatch(false);
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.useClosestMatch(context, false)
  }

  public void enableCssSelector() {
    Environment.instance.useCssSelector(true);
  }

  public void disableCssSelector() {
    Environment.instance.useCssSelector(false);
  }

  public void setUseCacheFlag(boolean isUse){
    Environment.instance.useCache(isUse);
  }

  public void enableCache() {
    Environment.instance.useCache(true);
//      this.exploreUiModuleCache = true
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.enableCache(context)
  }

  public boolean disableCache() {
    Environment.instance.useCache(false);
//      this.exploreUiModuleCache = false
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.disableCache(context)
  }

  public void cleanCache() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.cleanCache(context)
  }

  public boolean getCacheState() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    return extension.getCacheState(context)
  }

  public void setCacheMaxSize(int size) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.setCacheMaxSize(context, size)
  }

  public int getCacheSize() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    return extension.getCacheSize(context).intValue()
  }

  public int getCacheMaxSize() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    return extension.getCacheMaxSize(context).intValue()
  }

  public String getCacheUsage(){
    CacheUsageResponse resp = this.getCacheUsageResponse();
    return resp.toString();
  }
  
  public CacheUsageResponse getCacheUsageResponse() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

/*
    String out = extension.getCacheUsage(context);
    ArrayList list = (ArrayList) parseSeleniumJSONReturnValue(out)
    Map<String, Long> usages = new HashMap<String, Long>()
    list.each {Map elem ->
      elem.each {key, value ->
        usages.put(key, value)
      }
    }

    return usages
*/
    List out = extension.getCacheUsage(context);
    CacheUsageResponse resp = new CacheUsageResponse();
    resp.parseJSON(out);

    return resp;
  }

  public void useDiscardNewCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.useDiscardNewCachePolicy(context)
  }

  public void useDiscardOldCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.useDiscardOldCachePolicy(context)
  }

  public void useDiscardLeastUsedCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.useDiscardLeastUsedCachePolicy(context)
  }

  public void useDiscardInvalidCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.useDiscardInvalidCachePolicy(context)
  }

  public String getCurrentCachePolicy() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    return extension.getCachePolicyName(context)
  }

  public void useDefaultXPathLibrary() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    accessor.useXpathLibrary(context, DEFAULT_XPATH)
  }

  public void useJavascriptXPathLibrary() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    accessor.useXpathLibrary(context, JAVASCRIPT_XPATH)
  }

  public void useAjaxsltXPathLibrary() {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())
    accessor.useXpathLibrary(context, AJAXSLT_XPATH)
  }

  public void registerNamespace(String prefix, String namespace) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    extension.addNamespace(context, prefix, namespace)
  }

  public String getNamespace(String prefix) {
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreCssSelector(), this.exploreUiModuleCache())

    return extension.getNamespace(context, prefix)
  }

  public void pause(int milliseconds) {
    //flush out remaining commands in the command bundle before disconnection
    BundleProcessor processor = BundleProcessor.instance;
    processor.flush();

    Helper.pause(milliseconds);
  }

  public void flush() {
    //flush out remaining commands in the command bundle before disconnection
    BundleProcessor processor = BundleProcessor.instance;
    processor.flush();
  }

  public void enableMacroCmd() {
     Environment.instance.useBundle(true);
  }

  public void disableMacroCmd() {
      Environment.instance.useBundle(false);
  }

  public void enableTrace(){
     Environment.instance.useTrace(true);
  }

  public void disableTrace(){
     Environment.instance.useTrace(false);
  }
  
  public void showTrace() {
    Dispatcher dispatcher = new Dispatcher();
    dispatcher.showTrace()
  }

  public void setEnvironment(String name, Object value){
    Environment.instance.setCustomEnvironment(name, value);
  }

  public Object getEnvironment(String name){
    return Environment.instance.getCustomEnvironment(name);
  }

  public void enableLocatorWithCache(){
    Environment.instance.useLocatorWithCache(true);
  }

  public void disableLocatorWithCache(){
    Environment.instance.useLocatorWithCache(false);
  }

  public useMaxMacroCmd(int max){
    Environment.instance.useMaxMacroCmd(max);
  }

  public int getMaxMacroCmd(){
    return Environment.instance.myMaxMacroCmd();
  }

  public boolean isUseTelluriumApi(){
    return Environment.instance.isUseTelluriumApi();
  }

  public void enableTelluriumApi(){
    useTelluriumApi(true);
  }

  public void disableTelluriumApi(){
    useTelluriumApi(false);
  }

  public void useTelluriumApi(boolean isUse){
    Environment.instance.useTelluriumApi(isUse);
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.useTeApi(context, isUse);
  }
  
  void allowNativeXpath(boolean allow) {
    WorkflowContext context = WorkflowContext.getDefaultContext()

    accessor.allowNativeXpath(context, allow)
  }

  void addScript(String scriptContent, String scriptTagId){
     WorkflowContext context = WorkflowContext.getDefaultContext();
     accessor.addScript(context, scriptContent, scriptTagId);
  }

  void removeScript(String scriptTagId){
     WorkflowContext context = WorkflowContext.getDefaultContext();
     accessor.removeScript(context, scriptTagId);
  }

   /*void enableLogging(LogLevels levels){
    Environment.instance.enableLogging(levels);
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.enableLogging(context, levels.toString());
  }
  */
  void enableEngineLog(){
     WorkflowContext context = WorkflowContext.getDefaultContext();
     Environment.instance.useEngineLog(true);
     extension.useEngineLog(context, true);
  }

  void disableEngineLog(){
    WorkflowContext context = WorkflowContext.getDefaultContext();
    Environment.instance.useEngineLog(false);
    extension.useEngineLog(context, false);
  }

  void useEngineLog(boolean isUse){
     WorkflowContext context = WorkflowContext.getDefaultContext();
     Environment.instance.useEngineLog(isUse);
     extension.useEngineLog(context, isUse);   
  }

  void enableTelluriumEngine(){
    this.enableCache();
    this.enableTelluriumApi();
    this.enableMacroCmd();
  }

  void disableTelluriumEngine(){
    this.disableCache();
    this.disableTelluriumApi();
    this.disableMacroCmd();
  }
}