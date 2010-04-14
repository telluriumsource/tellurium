package org.telluriumsource.ui.object

import org.json.simple.JSONObject

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 7, 2010
 *
 */
class AllPurposeObject extends UiObject {

  JSONObject toJSON() {

    return buildJSON() {jso ->
      jso.put(UI_TYPE, "AllPurposeObject");
    }
  }

  def click(Closure c) {

    c(locator, respondToEvents)
  }

  def doubleClick(Closure c) {

    c(locator, respondToEvents)
  }

  def clickAt(String coordination, Closure c) {

    c(locator, respondToEvents)
  }

  String getImageSource(Closure c) {
    c(locator, "@src")
  }

  String getImageAlt(Closure c) {
    c(locator, "@alt")
  }

  String getImageTitle(Closure c) {
    c(locator, "@title")
  }

  def type(String input, Closure c) {
    c(locator, respondToEvents)
  }

  def keyType(String input, Closure c) {
    c(locator, respondToEvents)
  }

  def typeAndReturn(String input, Closure c) {

    c(locator, respondToEvents)
  }

  def clearText(Closure c) {

    c(locator, respondToEvents)
  }

  boolean isEditable(Closure c) {
    return c(locator)
  }

  def check(Closure c) {

    c(locator, respondToEvents)
  }

  def boolean isChecked(Closure c) {

//        c(locator, respondToEvents)
    c(locator)
  }

  def uncheck(Closure c) {

    c(locator, respondToEvents)
  }

  String getValue(Closure c) {
    return c(locator)
  }


  def selectByLabel(String target, Closure c) {
    c(locator, "label=${target}", respondToEvents)
  }

  def selectByValue(String value, Closure c) {

    c(locator, "value=${value}", respondToEvents)
  }

  def addSelectionByLabel(String target, Closure c) {
    c(locator, "label=${target}")
  }

  def addSelectionByValue(String value, Closure c) {
    c(locator, "value=${value}")
  }

  def removeSelectionByLabel(String target, Closure c) {
    c(locator, "label=${target}")
  }

  def removeSelectionByValue(String value, Closure c) {
    c(locator, "value=${value}")
  }

  def removeAllSelections(Closure c) {
    c(locator)
  }

  String[] getSelectOptions(Closure c) {
    c(locator)
  }

  String[] getSelectedLabels(Closure c) {
    c(locator)
  }

  String getSelectedLabel(Closure c) {
    c(locator)
  }

  String[] getSelectedValues(Closure c) {
    c(locator)
  }

  String getSelectedValue(Closure c) {
    c(locator)
  }

  String[] getSelectedIndexes(Closure c) {
    c(locator)
  }

  String getSelectedIndex(Closure c) {
    c(locator)
  }

  String[] getSelectedIds(Closure c) {
    c(locator)
  }

  String getSelectedId(Closure c) {
    c(locator)
  }

  boolean isSomethingSelected(Closure c) {
    c(locator)
  }


  String waitForText(int timeout, Closure c) {
    c(locator)
  }

  String getLink(Closure c) {
    c(locator, "@href")
  }

  def submit(Closure c) {
    c(locator)
  }
}
