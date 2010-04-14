package org.telluriumsource.ui.object

import org.json.simple.JSONObject

/**
 *  URL link
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class UrlLink extends UiObject {
    public static final String TAG = "a"

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "UrlLink")
      }
    }

    String getLink(Closure c){
        c(locator, "@href")
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
}