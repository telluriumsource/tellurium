package org.telluriumsource.test.groovy

import org.telluriumsource.framework.config.CustomConfig
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.component.connector.SeleniumConnector
import org.telluriumsource.framework.TelluriumFramework
import org.telluriumsource.entity.CachePolicy
import org.telluriumsource.util.Helper
import org.telluriumsource.util.LogLevels
import org.telluriumsource.entity.EngineState

abstract class BaseTelluriumGroovyTestCase extends GroovyTestCase{

  //custom configuration
	protected IResourceBundle i18nBundle
    protected CustomConfig customConfig = null

    protected SeleniumConnector conn;
    protected TelluriumFramework tellurium

    public BaseTelluriumGroovyTestCase(){
		  i18nBundle = Environment.instance.myResourceBundle()
	}

    public abstract SeleniumConnector getConnector()

	public IResourceBundle geti18nBundle()
	{
		return this.i18nBundle;
	}
    public void openUrl(String url){
        getConnector().connectSeleniumServer()
        getConnector().connectUrl(url)
    }

    public void connectUrl(String url) {
         getConnector().connectUrl(url)
    }

    public void connectSeleniumServer(){
        getConnector().connectSeleniumServer()
    }

    public void disconnectSeleniumServer(){
         getConnector().disconnectSeleniumServer()
    }

    public void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation)
    }

    public void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost)
    }

    public void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost, String browserOptions){
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost, browserOptions)
    }

    public boolean isUseLocatorWithCache(){
        return tellurium.isUseLocatorWithCache();
    }

    public void useLocatorWithCache(boolean isUse){
        tellurium.useLocatorWithCache(isUse);
    }

    public void useClosestMatch(boolean isUse){
      tellurium.useClosestMatch(isUse);
    }

    public void useCssSelector(boolean isUse){
      tellurium.useCssSelector(isUse);
    }

    public void useCache(boolean isUse){
      tellurium.useCache(isUse);
    }

    public void cleanCache(){
      tellurium.cleanCache();
    }

    public boolean isUsingCache(){
      return tellurium.isUsingCache();
    }

    public void setCacheMaxSize(int size){
      tellurium.setCacheMaxSize(size);
    }

    public int getCacheSize(){
      return tellurium.getCacheSize();
    }

    public int getCacheMaxSize(){
      return tellurium.getCacheMaxSize();
    }

    public String getCacheUsage(){
      return tellurium.getCacheUsage();
    }

    public void useCachePolicy(CachePolicy policy){
      tellurium.useCachePolicy(policy);
    }

    public String getCurrentCachePolicy(){
      return tellurium.getCurrentCachePolicy();
    }

    public void useDefaultXPathLibrary(){
      tellurium.useDefaultXPathLibrary();
    }

    public void useJavascriptXPathLibrary(){
      tellurium.useJavascriptXPathLibrary();
    }

    public void useAjaxsltXPathLibrary() {
      tellurium.useAjaxsltXPathLibrary();
    }

    public void registerNamespace(String prefix, String namespace){
      tellurium.registerNamespace(prefix, namespace);
    }

    public String getNamespace(String prefix) {
      return tellurium.getNamespace(prefix);
    }

    public void pause(int milliseconds) {
      tellurium.pause(milliseconds);
//      Helper.pause(milliseconds);
    }

    public void useMacroCmd(boolean isUse){
      tellurium.useMacroCmd(isUse);
    }

    public void setMaxMacroCmd(int max){
      tellurium.setMaxMacroCmd(max);
    }

    public int getMaxMacroCmd(){
      return tellurium.getMaxMacroCmd();
    }

    public boolean isUseTelluriumApi(){
      return tellurium.isUseTelluriumApi();
    }

    public void useTelluriumApi(boolean isUse){
      tellurium.useTelluriumApi(isUse) ;
    }

   /*public void enableLogging(LogLevels loggingLevel){
      tellurium.enableLogging(loggingLevel) ;
    }
    */

    public void useTrace(boolean isUse){
      tellurium.useTrace(isUse);
    }

    public void showTrace() {
      tellurium.showTrace();
    }

    public void setEnvironment(String name, Object value){
      tellurium.setEnvironment(name, value) ;
    }

    public Object getEnvironment(String name){
      return tellurium.getEnvironment(name);
    }

    public void allowNativeXpath(boolean allow){
      tellurium.allowNativeXpath(allow);
    }

    public void addScript(String scriptContent, String scriptTagId){
        tellurium.addScript(scriptContent, scriptTagId);
    }

    public void removeScript(String scriptTagId){
        tellurium.removeScript(scriptTagId);
    }

    public EngineState getEngineState(){
        return tellurium.getEngineState();
    }

    public void useEngineLog(boolean isUse){
        tellurium.useEngineLog(isUse);
    }
  
    public void useTelluriumEngine(boolean isUse){
        this.useCache(isUse);
        this.useMacroCmd(isUse);
        this.useTelluriumApi(isUse);
    }

    public void dumpEnvironment(){
        tellurium.dumpEnvironment();
    }
}