package org.telluriumsource.framework
import org.telluriumsource.crosscut.i18n.ResourceBundle;
import org.telluriumsource.crosscut.i18n.IResourceBundle;

import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.util.LogLevels
                     
/**
 * Environment to hold runtime environment variables
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 25, 2009
 *
 */

@Singleton
public class Environment implements Configurable{

  protected String configFileName = "TelluriumConfig.groovy";

  protected String configString = "";

  //flag to decide whether we should use CSS Selector
  protected boolean exploitCssSelector = true;

  //flag to decide whether we should cache Ui Module
  protected boolean exploitEngineCache = false;

  protected IResourceBundle resourceBundle = new ResourceBundle();

  protected boolean trace = false;

  protected boolean exploitBundle = false;

  protected int maxMacroCmd = 5;

  protected boolean captureScreenshot = false;

  protected String locale = "en_US";

  def envVariables = [:];

  protected boolean exploitTelluriumApi = false;

  protected boolean closestMatch = false;

  protected boolean locatorWithCache = true;

  protected boolean logEngine = false;

  //protected LogLevels loggingLevel = LogLevels.OFF

  public String toString(){
    String result = """
    Environment Variables:
      configFileName: ${this.configFileName}
      configString: ${this.configString}
      useEngineCache: ${this.exploitEngineCache}
      useClosestMatch: ${closestMatch}
      useMacroCommand: ${this.exploitBundle}
      maxMacroCmd: ${this.maxMacroCmd}
      useTelluriumApi: ${this.exploitTelluriumApi}
      locatorWithCache: ${this.locatorWithCache}
      useCSSSelector: ${this.exploitCssSelector}
      useTrace: ${this.trace}
      logEngine: ${this.logEngine}
      locale: ${this.locale}
      """

      return result;
  }

  public static Environment getEnvironment(){
    return Environment.instance;
  }

  public void useConfigString(String json){
    this.configString = json;
  }

  public boolean isUseEngineLog(){
    return this.logEngine;
  }

  public void useEngineLog(boolean isUse){
    this.logEngine = isUse;
  }

  public boolean isUseLocatorWithCache(){
    return this.locatorWithCache;
  }

  public boolean isUseClosestMatch(){
    return this.closestMatch;
  }

  public boolean isUseCssSelector(){
    return this.exploitCssSelector;
  }

  public boolean isUseCache(){
    return this.exploitEngineCache;
  }

  public boolean isUseBundle(){
    return this.exploitBundle;
  }

  public boolean isUseScreenshot(){
    return this.captureScreenshot;
  }

  public boolean isUseTrace(){
    return this.trace;
  }

  public boolean isUseTelluriumApi(){
    return this.exploitTelluriumApi;
  }

  public void useClosestMatch(boolean isUse){
    this.closestMatch = isUse;  
  }

  public void useLocatorWithCache(boolean isUse){
    this.locatorWithCache = isUse;  
  }

  public void useCssSelector(boolean isUse){
    this.exploitCssSelector = isUse;
  }

  public void useCache(boolean isUse){
    this.exploitEngineCache = isUse;
  }

  public void useBundle(boolean isUse){
    this.exploitBundle = isUse;
  }

  public void useScreenshot(boolean isUse){
    this.captureScreenshot = isUse;
  }

  public void useTrace(boolean isUse){
    this.trace = isUse;
  }

  public void useTelluriumApi(boolean isUse) {
    this.exploitTelluriumApi = isUse;
  }

  /*
  public void enableLogging(LogLevels loggingLevel) {
    this.loggingLevel = loggingLevel;
  }
  */

  public useMaxMacroCmd(int max){
    if(max < 1)
      this.maxMacroCmd = 1;
    else
      this.maxMacroCmd = max;
  }

  public int myMaxMacroCmd(){
    return this.maxMacroCmd;
  }

  public IResourceBundle myResourceBundle(){
    return this.resourceBundle;
  }

  public String myLocale(){
    return this.locale;
  }

  public void useLocale(String locale){
    this.locale = locale;
    String[] split = locale.split("_");
    Locale loc = new Locale(split[0], split[1]);
    this.resourceBundle.updateDefaultLocale(loc);
  }

  public void setCustomEnvironment(String name, Object value){
    envVariables.put(name, value);
  }

  public Object getCustomEnvironment(String name){
    return envVariables.get(name);
  }
}