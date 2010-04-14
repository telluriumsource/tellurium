package org.telluriumsource.component.data

import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.util.Helper

import org.telluriumsource.component.bundle.BundleProcessor
import org.telluriumsource.dsl.WorkflowContext;


class Accessor implements Configurable {


  protected static final int ACCESS_WAIT_TIME = 50

  private static final String DISABLED_ATTRIBUTE = "@disabled"

  protected static final String ELEMENT_NOT_PRESENT_ERROR_MESSAGE = "Element is not present"

  private BundleProcessor cbp = BundleProcessor.instance

  private boolean checkElement = true

  public void mustCheckElement() {
    this.checkElement = true
  }

  public void notCheckElement() {
    this.checkElement = false
  }

  protected void checkElement(WorkflowContext context, String locator) {
    cbp.flush()
    if (checkElement && (!cbp.isElementPresent(context, locator))) {
      waitForElementPresent(context, locator, ACCESS_WAIT_TIME)
      cbp.flush()
    }
  }

  def boolean isElementPresent(WorkflowContext context, String locator) {

    return cbp.isElementPresent(context, locator)
  }

  def boolean isVisible(WorkflowContext context, String locator) {

    return cbp.isVisible(context, locator)
  }

  def boolean isChecked(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.isChecked(context, locator)
  }

  def boolean isDisabled(WorkflowContext context, String locator) {

    checkElement(context, locator)

    locator += DISABLED_ATTRIBUTE
    String attr = getAttribute(context, locator)
    return (attr != null && (attr == "true" || attr == "disabled"));
  }

  def boolean waitForElementPresent(WorkflowContext context, String locator, int timeout) {

    //boolean result = false

    for (int second = 0; second < timeout; second += 500) {
      try {
        if (cbp.isElementPresent(context, locator)) {
          //result = true
          return true
        }
      } catch (Exception e) {

      }

      Helper.pause(500)
    }

    //return result;
    return false
  }

  def boolean waitForElementPresent(WorkflowContext context, String locator, int timeout, int step) {

    //boolean result = false

    for (int second = 0; second < timeout; second += step) {
      try {
        if (cbp.isElementPresent(context, locator)) {
          //result = true
          return true
        }
      } catch (Exception e) {

      }

      Helper.pause(step)
    }

    //return result;
    return false
  }

  boolean waitForCondition(WorkflowContext context, String script, String timeoutInMilliSecond) {

    boolean result = true

    try {
      cbp.waitForCondition(context, script, timeoutInMilliSecond)
    } catch (Exception e) {
      result = false
    }

    return result
  }

  def String getText(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getText(context, locator)
  }

  def String getValue(WorkflowContext context, String locator) {

    checkElement(context, locator)

    return cbp.getValue(context, locator)
  }

  def String[] getSelectOptions(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectOptions(context, locator)
  }

  def String[] getSelectValues(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectValues(context, locator)
  }

  String[] getSelectedLabels(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectedLabels(context, locator)
  }

  String getSelectedLabel(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectedLabel(context, locator)
  }

  String[] getSelectedValues(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectedValues(context, locator)
  }

  String getSelectedValue(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectedValue(context, locator)
  }

  String[] getSelectedIndexes(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectedIndexes(context, locator)
  }

  String getSelectedIndex(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectedIndex(context, locator)
  }

  String[] getSelectedIds(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectedIds(context, locator)
  }

  String getSelectedId(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.getSelectedId(context, locator)
  }

  boolean isSomethingSelected(WorkflowContext context, String locator) {
    checkElement(context, locator)

    return cbp.isSomethingSelected(context, locator)
  }

  String getAttribute(WorkflowContext context, String locator) {

    return cbp.getAttribute(context, locator)
  }

  void waitForPopUp(WorkflowContext context, String windowID, String timeout) {

    cbp.waitForPopUp(context, windowID, timeout)
  }

  String getBodyText(WorkflowContext context) {

    return cbp.getBodyText(context)
  }

  boolean isTextPresent(WorkflowContext context, String pattern) {

    return cbp.isTextPresent(context, pattern)
  }

  boolean isEditable(WorkflowContext context, String locator) {

    return cbp.isEditable(context, locator)
  }

  String getHtmlSource(WorkflowContext context) {

    return cbp.getHtmlSource(context)
  }

  String getExpression(WorkflowContext context, String expression) {

    return cbp.getExpression(context, expression)
  }

  Number getXpathCount(WorkflowContext context, String xpath) {

    return cbp.getXpathCount(context, xpath)
  }

  String getCookie(WorkflowContext context) {
    return cbp.getCookie(context)
  }

  void runScript(WorkflowContext context, String script) {

    cbp.runScript(context, script)
  }

  void captureScreenshot(WorkflowContext context, String filename) {

    cbp.captureScreenshot(context, filename)
  }

  void captureEntirePageScreenshot(WorkflowContext context, String filename, String kwargs) {
    cbp.captureEntirePageScreenshot(context, filename, kwargs)
  }

  String captureScreenshotToString(WorkflowContext context) {
    return cbp.captureScreenshotToString(context)
  }

  String captureEntirePageScreenshotToString(WorkflowContext context, String kwargs) {
    return cbp.captureEntirePageScreenshotToString(context, kwargs)
  }

  String retrieveLastRemoteControlLogs(context) {
    return cbp.retrieveLastRemoteControlLogs(context)
  }

  boolean isAlertPresent(WorkflowContext context) {

    return cbp.isAlertPresent(context)
  }

  boolean isPromptPresent(WorkflowContext context) {

    return cbp.isPromptPresent(context)
  }

  boolean isConfirmationPresent(WorkflowContext context) {

    return cbp.isConfirmationPresent(context)
  }

  String getAlert(WorkflowContext context) {
    return cbp.getAlert(context)
  }

  String getConfirmation(WorkflowContext context) {

    return cbp.getConfirmation(context)
  }

  String getPrompt(WorkflowContext context) {

    return cbp.getPrompt(context)
  }

  String getLocation(WorkflowContext context) {

    return cbp.getLocation(context)
  }

  String getTitle(WorkflowContext context) {

    return cbp.getTitle(context)
  }

  String[] getAllButtons(WorkflowContext context) {

    return cbp.getAllButtons(context)
  }

  String[] getAllLinks(WorkflowContext context) {
    return cbp.getAllLinks(context)
  }

  String[] getAllFields(WorkflowContext context) {
    return cbp.getAllFields(context)
  }

  String[] getAllWindowIds(WorkflowContext context) {
    return cbp.getAllWindowIds(context)
  }

  String[] getAllWindowNames(WorkflowContext context) {
    return cbp.getAllWindowNames(context)
  }

  String[] getAllWindowTitles(WorkflowContext context) {
    return cbp.getAllWindowTitles(context)
  }

  void waitForPageToLoad(WorkflowContext context, String timeout) {
    cbp.waitForPageToLoad(context, timeout)
  }

  void waitForFrameToLoad(WorkflowContext context, String frameAddress, String timeout) {
    cbp.waitForFrameToLoad(context, frameAddress, timeout)
  }

  String getEval(WorkflowContext context, String script) {
    return cbp.getEval(context, script)
  }

  boolean getWhetherThisFrameMatchFrameExpression(WorkflowContext context, String currentFrameString, String target) {
    return cbp.getWhetherThisFrameMatchFrameExpression(context, currentFrameString, target)
  }

  boolean getWhetherThisWindowMatchWindowExpression(WorkflowContext context, String currentWindowString, String target) {
    return cbp.getWhetherThisWindowMatchWindowExpression(context, currentWindowString, target)
  }

  void useXpathLibrary(WorkflowContext context, String libraryName) {
    cbp.useXpathLibrary(context, libraryName)
  }

  String waitForText(WorkflowContext context, String locator, int timeout) {
    waitForElementPresent(locator, timeout)

    return cbp.getText(context, locator)
  }

  String captureNetworkTraffic(WorkflowContext context, String type) {
    return cbp.captureNetworkTraffic(context, type)
  }

  void addCustomRequestHeader(WorkflowContext context, String key, String value) {
    cbp.addCustomRequestHeader(context, key, value)
  }

  void setTimeout(WorkflowContext context, String timeout) {
    cbp.setTimeout(context, timeout)
  }

  boolean isCookiePresent(WorkflowContext context, String name) {
    return cbp.isCookiePresent(context, name)
  }

  String getCookieByName(WorkflowContext context, String name) {
    return cbp.getCookieByName(context, name)
  }

  void createCookie(WorkflowContext context, String nameValuePair, String optionsString) {
    cbp.createCookie(context, nameValuePair, optionsString)
  }

  void deleteCookie(WorkflowContext context, String name, String optionsString) {
    cbp.deleteCookie(context, name, optionsString)
  }

  void deleteAllVisibleCookies(WorkflowContext context) {
    cbp.deleteAllVisibleCookies(context)
  }

  void allowNativeXpath(WorkflowContext context, boolean allow) {
    String allowed = (allow) ? "true" : "false";
    cbp.allowNativeXpath(context, allowed);
  }

  void addScript(WorkflowContext context, String scriptContent, String scriptTagId){
     cbp.addScript(context, scriptContent, scriptTagId);
  }

  void removeScript(WorkflowContext context, String scriptTagId){
     cbp.removeScript(context, scriptTagId);
  }
  
  int getIndex(WorkflowContext context, String locator){
     return cbp.getIndex(context, locator);
  }
}