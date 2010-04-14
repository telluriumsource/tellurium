package org.telluriumsource.ui.object

import org.json.simple.JSONObject

/**
 *  Text Box
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class TextBox extends UiObject {

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "TextBox")
      }
    }

    String waitForText(int timeout, Closure c){
       c(locator)
    }
}