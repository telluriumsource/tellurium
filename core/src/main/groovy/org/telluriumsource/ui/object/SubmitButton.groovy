package org.telluriumsource.ui.object

import org.json.simple.JSONObject

/**
 *  A Button and its type is "submit"
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class SubmitButton extends Button{
    public static final String TYPE = "submit"

    @Override
    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "SubmitButton")
      }
    }
}