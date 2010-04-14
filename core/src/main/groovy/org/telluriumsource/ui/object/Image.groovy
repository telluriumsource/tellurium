package org.telluriumsource.ui.object

import org.json.simple.JSONObject

/**
 *  IMAGE
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Image extends UiObject{
    public static final String TAG = "img"

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "Image")
      }
    }

    def click(Closure c) {

        c(locator, respondToEvents)
    }

    def doubleClick(Closure c) {

        c(locator, respondToEvents)
    }

    String getImageSource(Closure c){
        c(locator, "@src")
    }

    String getImageAlt(Closure c){
        c(locator, "@alt")
    }

    String getImageTitle(Closure c){
        c(locator, "@title")
    }
}