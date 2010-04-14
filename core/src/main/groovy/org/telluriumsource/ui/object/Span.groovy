package org.telluriumsource.ui.object

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 4, 2008
 * 
 */
class Span extends UiObject{
    public static final String TAG = "span"

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "Span")
      }
    }

    def click(Closure c){
        c(locator, respondToEvents)
    }

}