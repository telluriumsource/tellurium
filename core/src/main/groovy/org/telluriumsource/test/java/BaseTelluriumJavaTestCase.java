package org.telluriumsource.test.java;

import org.telluriumsource.framework.config.CustomConfig;
import org.telluriumsource.component.connector.SeleniumConnector;
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.framework.TelluriumFramework;
import org.telluriumsource.entity.CachePolicy;
import org.telluriumsource.entity.EngineState;
import org.telluriumsource.crosscut.i18n.ResourceBundle;
import org.telluriumsource.util.Helper;
import org.telluriumsource.util.LogLevels;

import java.util.Map;

/**
 * The base Java Testcase class for Tellurium and it will not include the before class and after class methods
 * so that the TestCase can be run as in a TestSuite
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */
public abstract class BaseTelluriumJavaTestCase {
    //custom configuration
    protected static CustomConfig customConfig = null;
    protected IResourceBundle i18nBundle;

    protected static SeleniumConnector connector;

    protected static TelluriumFramework tellurium;

    public BaseTelluriumJavaTestCase() {
        i18nBundle = new ResourceBundle();
    }

    public static void openUrl(String url) {
        connector.connectSeleniumServer();
        connector.connectUrl(url);
    }

    public IResourceBundle geti18nBundle() {
        return this.i18nBundle;
    }

    public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String baseUrl, String browser, String browserOptions) {
        connector.configBrowser(serverHost, serverPort, baseUrl, browser, browserOptions);
        openUrl(url);
    }

    public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String browser, String browserOptions) {
        openUrlWithBrowserParameters(url, serverHost, serverPort, null, browser, browserOptions);
    }

    public static void openUrlWithBrowserParameters(String url, String serverHost, int serverPort, String browser) {
        openUrlWithBrowserParameters(url, serverHost, serverPort, null, browser, null);
    }

    public static void connectUrl(String url) {
        connector.connectUrl(url);
    }

    public static void connectSeleniumServer() {
        connector.connectSeleniumServer();
    }

    public static void disconnectSeleniumServer() {
        connector.disconnectSeleniumServer();
    }

    public static void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation) {
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation);
    }

    public static void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost) {
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost);
    }

    public static void setCustomConfig(boolean runInternally, int port, String browser,
                                       boolean useMultiWindows, String profileLocation, String serverHost, String browserOptions) {
        customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost, browserOptions);
    }

    public static boolean isUseLocatorWithCache(){
        return tellurium.isUseLocatorWithCache();
    }

    public static void useLocatorWithCache(boolean isUse){
        tellurium.useLocatorWithCache(isUse);
    }

    public static void useClosestMatch(boolean isUse) {
        tellurium.useClosestMatch(isUse);
    }

    public static void useCssSelector(boolean isUse) {
        tellurium.useCssSelector(isUse);
    }

    public static void useCache(boolean isUse) {
        tellurium.useCache(isUse);
    }

    public static void cleanCache() {
        tellurium.cleanCache();
    }

    public static boolean isUsingCache() {
        return tellurium.isUsingCache();
    }

    public static void setCacheMaxSize(int size) {
        tellurium.setCacheMaxSize(size);
    }

    public static int getCacheSize() {
        return tellurium.getCacheSize();
    }

    public static int getCacheMaxSize() {
        return tellurium.getCacheMaxSize();
    }

    public static String getCacheUsage() {
        return tellurium.getCacheUsage();
    }

    public static void useCachePolicy(CachePolicy policy) {
        tellurium.useCachePolicy(policy);
    }

    public static String getCurrentCachePolicy() {
        return tellurium.getCurrentCachePolicy();
    }

    public static void useDefaultXPathLibrary() {
        tellurium.useDefaultXPathLibrary();
    }

    public static void useJavascriptXPathLibrary() {
        tellurium.useJavascriptXPathLibrary();
    }

    public static void useAjaxsltXPathLibrary() {
        tellurium.useAjaxsltXPathLibrary();
    }

    public static void registerNamespace(String prefix, String namespace) {
        tellurium.registerNamespace(prefix, namespace);
    }

    public static String getNamespace(String prefix) {
        return tellurium.getNamespace(prefix);
    }

    public static void pause(int milliseconds) {
        tellurium.pause(milliseconds);
//      Helper.pause(milliseconds);
    }

    public static void useMacroCmd(boolean isUse) {
        tellurium.useMacroCmd(isUse);
    }

    public static void setMaxMacroCmd(int max) {
        tellurium.setMaxMacroCmd(max);
    }

    public static int getMaxMacroCmd() {
        return tellurium.getMaxMacroCmd();
    }

    public static boolean isUseTelluriumApi() {
        return tellurium.isUseTelluriumApi();
    }

    public static void useTelluriumApi(boolean isUse) {
        tellurium.useTelluriumApi(isUse);
    }

    /*public static void enableLogging(LogLevels loggingLevel){
      tellurium.enableLogging(loggingLevel) ;
    }
    */

    public static void useTrace(boolean isUse){
      tellurium.useTrace(isUse);
    }

    public static void showTrace() {
        tellurium.showTrace();
    }

    public static void setEnvironment(String name, Object value) {
        tellurium.setEnvironment(name, value);
    }

    public static Object getEnvironment(String name) {
        return tellurium.getEnvironment(name);
    }

    public static void allowNativeXpath(boolean allow) {
        tellurium.allowNativeXpath(allow);
    }

    public static void addScript(String scriptContent, String scriptTagId){
        tellurium.addScript(scriptContent, scriptTagId);
    }

    public static void removeScript(String scriptTagId){
        tellurium.removeScript(scriptTagId);
    }

    public static EngineState getEngineState(){
        return tellurium.getEngineState();
    }

    public static void useEngineLog(boolean isUse){
        tellurium.useEngineLog(isUse);
    }

    public static void useTelluriumEngine(boolean isUse){
        useCache(isUse);
        useMacroCmd(isUse);
        useTelluriumApi(isUse);
    }

    public static void dumpEnvironment(){
        tellurium.dumpEnvironment();
    }
}
