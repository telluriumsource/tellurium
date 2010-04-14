package org.telluriumsource.ui.object

import org.json.simple.JSONObject

 /**
 *  FORM
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Form extends Container{

    public static final String TAG = "form"

    def submit(Closure c){
        c(locator)
    }

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "Form")
      }
    }
}