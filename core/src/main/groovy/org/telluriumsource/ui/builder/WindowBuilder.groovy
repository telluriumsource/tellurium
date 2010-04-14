package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.object.Window

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 30, 2008
 *
 */
class WindowBuilder extends UiObjectBuilder{

    def build(Map map, Closure closure){
       def df = [:]
       Window window =this.internBuild(new Window(), map, df)
       window.id = map.get(ID)
       window.name = map.get(NAME)
       window.title = map.get(TITLE)

       if(closure)
          closure(window)

       return window
   }

   def build(Window window, UiObject[] objects){

      if(window == null || objects == null || objects.length < 1)
        return window

      objects.each {obj -> window.add(obj)}

      return window
   }

   def build(Window window, UiObject object){

      if(window == null || object == null)
        return window

      window.add(object)

      return window
   }


}