package org.telluriumsource.ui.object

import org.json.simple.JSONObject

/**
 *  ICON
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Icon extends UiObject {

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "Icon")
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
}