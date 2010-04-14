package org.telluriumsource.ui.object

import org.json.simple.JSONObject

 /**
 *  Check Box
 *
 * you can select one or more options from a set of alternatives.
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class CheckBox extends UiObject{

  public static final String TAG = "input"
  public static final String TYPE = "checkbox"

  public JSONObject toJSON() {

    return buildJSON() {jso ->
      jso.put(UI_TYPE, "CheckBox")
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

}