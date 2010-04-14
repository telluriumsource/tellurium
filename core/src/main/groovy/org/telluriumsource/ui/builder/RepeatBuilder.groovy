package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.Repeat
import org.telluriumsource.ui.object.UiObject

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 3, 2010
 * 
 */
class RepeatBuilder extends UiObjectBuilder {

    def build(Map map, Closure closure){
       def df = [:]
       Repeat repeat =this.internBuild(new Repeat(), map, df)

       if(closure)
          closure(repeat)

       return repeat
   }

   def build(Repeat repeat, UiObject[] objects){

      if(repeat == null || objects == null || objects.length < 1)
        return repeat

      objects.each {obj -> repeat.add(obj)}

      return repeat
   }

   def build(Repeat repeat, UiObject object){

      if(repeat == null || object == null)
        return repeat

      repeat.add(object)

      return repeat
   }
}
