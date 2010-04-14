package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.Div
import org.telluriumsource.ui.object.UiObject

/**
 * 
 * User: vivmon1
 * Date: Jul 25, 2008
 * Time: 5:18:33 PM
 *
 */
class DivBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        def df = [:]
        df.put(TAG, Div.TAG)
        Div div = this.internBuild( new Div(), map, df)

        if(c)
          c(div)

        return div
    }

   def build(Div div, UiObject[] objects){

      if(div == null || objects == null || objects.length < 1)
        return div

      objects.each {obj -> div.add(obj)}

      return div
   }

   def build(Div div, UiObject object){

      if(div == null || object == null)
        return div

      div.add(object)

      return div
   }}