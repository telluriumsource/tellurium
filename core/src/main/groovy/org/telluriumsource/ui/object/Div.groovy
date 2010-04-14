package org.telluriumsource.ui.object

import org.json.simple.JSONObject

/**
 * 
 * User: vivmon1
 * Date: Jul 25, 2008
 * Time: 5:16:01 PM
 *
 */
class Div extends Container {
    public static final String TAG = "div"

    def click(Closure c){
        c(locator, respondToEvents)
    }

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "Div")
      }
    }
}