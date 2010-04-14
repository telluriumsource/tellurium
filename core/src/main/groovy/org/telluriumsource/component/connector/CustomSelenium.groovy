package org.telluriumsource.component.connector

import com.thoughtworks.selenium.DefaultSelenium
import com.thoughtworks.selenium.CommandProcessor
import org.telluriumsource.exception.*
import org.telluriumsource.framework.Environment;
import org.telluriumsource.util.grid.GridSupport
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.entity.UiModuleValidationRequest
import org.telluriumsource.util.LogLevels

/**
 * Customize Selenium RC so that we can add custom methods to Selenium RC
 * Added Selenium Grid support.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * @author Haroon Rasheed (haroonzone@gmail.com)
 *
 * Date: Oct 21, 2008
 *
 */
class CustomSelenium extends DefaultSelenium {

  protected IResourceBundle i18nBundle;

    protected CustomCommand customClass = null
    protected String userExtension = null

    CustomSelenium(String host, int port, String browser, String url){
      super(host, port, browser, url)
      i18nBundle = Environment.instance.myResourceBundle()
    }

    CustomSelenium(CommandProcessor commandProcessor) {
      super (commandProcessor)
      i18nBundle = Environment.instance.myResourceBundle()
    }

    public void setUserExt(String userExt){
      this.userExtension = userExt
    }

    protected void passCommandProcessor(CommandProcessor commandProcessor){
      if(customClass != null){
        customClass.setProcessor(this.commandProcessor)
      }
    }

    protected def methodMissing(String name, args) {

         if(customClass != null && customClass.metaClass.respondsTo(customClass, name, args)){
              return customClass.invokeMethod(name, args)
         }

        throw new MissingMethodException(name, CustomSelenium.class, args)
    }

    // Added for the selenium grid support.
    // Start the selenium session specified by the arguments.
    // and register the selenium rc with Selenium HUB
    def void startSeleniumSession(String host, int port, String browser, String url) throws Exception{
      try{
        GridSupport.startSeleniumSession(host, port, browser, url)
      }catch (Exception e){

        throw new TelluriumException (i18nBundle.getMessage("CustomSelenium.CannotStartSelenium" , e.getMessage()))
      }
    }

    def void startSeleniumSession(String host, int port, String browser, String url, String options) throws Exception{
      try{
        GridSupport.startSeleniumSession(host, port, browser, url, options)
      }catch (Exception e){
        throw new TelluriumException (i18nBundle.getMessage("CustomSelenium.CannotStartSelenium" , e.getMessage()))
      }
    }

    // Close the selenium session and unregister the Selenium RC
    // from Selenium Hub
    def void closeSeleniumSession() throws Exception{
      try{
        GridSupport.closeSeleniumSession()
      }catch (Exception e){

        throw new TelluriumException (i18nBundle.getMessage("CustomSelenium.CannotCloseSelenium" , e.getMessage()))
      }


    }

    // Get the active Selenium RC session
    def CustomSelenium getActiveSeleniumSession(){
//      DefaultSelenium sel =  com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session()
//      CommandProcessor processor = sel.commandProcessor
//      CustomSelenium csel = new CustomSelenium(processor)

      CustomSelenium csel = GridSupport.session()
/*
      if(this.userExtension != null && this.userExtension.trim().length() > 0){
          File userExt = new File(this.userExtension);
//        processor.setExtensionJs(userExt.getAbsolutePath())
          processor.setExtensionJs(this.userExtension)
          println "Add user-extensions.js found at given path: " + userExt.getAbsolutePath() + " to Command Processor";
      }
 */
      if(csel != null){
        csel.customClass = this.customClass
        csel.passCommandProcessor(csel.commandProcessor)
      }
//      csel.passCommandProcessor(processor)

      return csel
    }

    /*Please add custom methods here for Selenium RC after you add user extension to Selenium Core

   For instance,

       public void typeRepeated(String locator, String text) {

         commandProcessor.doCommand("typeRepeated", new String[]{locator, text});

       }
    */

    def String getAllText(String locator){
		String[] arr = [locator];
		String st = commandProcessor.doCommand("getAllText", arr);
		return st;
	}

    def String getCSS(String locator, String cssName){
		String[] arr = [locator, cssName];
		String st = commandProcessor.doCommand("getCSS", arr);
		return st;
	}

    def Number getCssSelectorCount(String locator){
		String[] arr = [locator];
		Number num = commandProcessor.getNumber("getCssSelectorCount", arr);
		return num;
	}

    def Number getListSize(String locator, String separators){
      String[] arr = [locator, separators];
      Number num = commandProcessor.getNumber("getListSize", arr);
      return num;
    }

    def boolean isDisabled(String locator){
      String[] arr = [locator];
      boolean result = commandProcessor.getBoolean("isDisabled", arr);
      return result;
    }

    public void enableCache(){
      String[] arr = [];
      commandProcessor.doCommand("enableCache", arr);
    }

    public void disableCache(){
      String[] arr = [];
      commandProcessor.doCommand("disableCache",  arr);
    }

    public void cleanCache(){
      String[] arr = [];
      commandProcessor.doCommand("cleanCache", arr);
    }

    def boolean getCacheState(){
      String[] arr = [];
      boolean result = commandProcessor.getBoolean("getCacheState", arr);
      return result;
    }

    public void setCacheMaxSize(int size){
//      String[] arr = [Integer.toString(size)];
      String[] arr = [size];
      commandProcessor.doCommand("setCacheMaxSize",  arr);
    }

    public Number getCacheSize(){
    	String[] arr = [];
        Number num = commandProcessor.getNumber("getCacheSize", arr);
		return num;
    }

    public Number getCacheMaxSize(){
    	String[] arr = [];
        Number num = commandProcessor.getNumber("getCacheMaxSize", arr);
		return num;
    }

    public String getCacheUsage(){
       	String[] arr = [];
		String st = commandProcessor.doCommand("getCacheUsage", arr);
		return st;
    }

    public void addNamespace(String prefix, String namespace){
       String[] arr = [prefix, namespace];
       commandProcessor.doCommand("addNamespace", arr);
    }

    public String getNamespace(String prefix){
       String[] arr = [prefix];
       String st = commandProcessor.getString("getNamespace", arr);

       return st;
    }

    public void useDiscardNewCachePolicy(){
        String[] arr = [];
        commandProcessor.doCommand("useDiscardNewCachePolicy", arr);
    }

    public void useDiscardOldCachePolicy(){
        String[] arr = [];
        commandProcessor.doCommand("useDiscardOldCachePolicy", arr);
    }

    public void useDiscardLeastUsedCachePolicy(){
        String[] arr = [];
        commandProcessor.doCommand("useDiscardLeastUsedCachePolicy", arr);
    }

    public void useDiscardInvalidCachePolicy(){
        String[] arr = [];
        commandProcessor.doCommand("useDiscardInvalidCachePolicy", arr);
    }

    public String getCachePolicyName(){
        String[] arr = [];
        return commandProcessor.getString("getCachePolicyName", arr);
    }

    public void typeKey(String locator, String key){
        String[] arr = [locator, key];
        commandProcessor.doCommand("typeKey", arr);
    }

    public void triggerEvent(String locator, String event){
        String[] arr = [locator, event];
        commandProcessor.doCommand("triggerEvent", arr);
    }

    public String getDiagnosisResponse(String locator, String request){
		String[] arr = [locator, request];
		String st = commandProcessor.doCommand("getDiagnosisResponse", arr);
		return st;
    }

    public String getBundleResponse(String json){
        String[] arr = [json];

        return commandProcessor.doCommand("getBundleResponse", arr);
    }

    public String getUseUiModule(String json){
        String[] arr = [json];
        String st = commandProcessor.doCommand(UiModuleValidationRequest.CMD_NAME, arr);
        return st;
    }

    public String getValidateUiModule(String json){
        String[] arr = [json];
        String st = commandProcessor.doCommand("getValidateUiModule", arr);
        return st;
    }

    public boolean isUiModuleCached(String id){
       String[] arr = [id];

       return commandProcessor.getBoolean("isUiModuleCached", arr);
    }

    public void useTeApi(boolean isUse){
       String[] arr = [isUse];

       commandProcessor.doCommand("useTeApi", arr);
    }

    public void useClosestMatch(boolean isUse){
       String[] arr = [isUse];

       commandProcessor.doCommand("useClosestMatch", arr);
    }
  
    public void toggle(String locator){
        String[] arr = [locator];
        commandProcessor.doCommand("toggle", arr);
    }

    public void deleteAllCookiesByJQuery() {
        String[] arr = [];
        commandProcessor.doCommand("deleteAllCookiesByJQuery", arr);
    }

    public void deleteCookieByJQuery(cookieName){
        String[] arr = [cookieName];
        commandProcessor.doCommand("deleteCookieByJQuery", arr);
    }

    public void setCookieByJQuery(String cookieName, String value, def options){
        String[] arr;
        if(options != null){
            arr = [cookieName, value, options].flatten();
        }else{
            arr = [cookieName, value];
        }
        commandProcessor.doCommand("setCookieByJQuery", arr);
    }

    public String getCookieByJQuery(String cookieName){
        String[] arr = [cookieName];
        return commandProcessor.doCommand("getCookieByJQuery", arr);
    }

    public void updateEngineState(String state){
        String[] arr = [state];
        commandProcessor.doCommand("updateEngineState", arr);
    }

    public String getEngineState(){
        String[] arr = [];
        return commandProcessor.doCommand("getEngineState", arr);
    }
    
    /*public void enableLogging(LogLevels loggingLevels){
       String[] arr = [loggingLevels.toString()];

       commandProcessor.doCommand("enableLogging", arr);
    }
    */

    public void showUi(String uid, long delay){
        String[] arr = [uid, delay];
        commandProcessor.doCommand("showUi", arr);
    }
}