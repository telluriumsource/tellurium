package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.Frame
import org.telluriumsource.ui.object.UiObject

/**
 * Frame/IFrame builder
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 28, 2008
 * 
 */
class FrameBuilder extends UiObjectBuilder{

    def build(Map map, Closure closure){
       def df = [:]
       Frame frame =this.internBuild(new Frame(), map, df)
       frame.id = map.get(ID)
       frame.name = map.get(NAME)
       frame.title = map.get(TITLE)

       if(closure)
          closure(frame)

       return frame
   }

   def build(Frame frame, UiObject[] objects){

      if(frame == null || objects == null || objects.length < 1)
        return frame

      objects.each {obj -> frame.add(obj)}

      return frame
   }

   def build(Frame frame, UiObject object){

      if(frame == null || object == null)
        return frame

      frame.add(object)

      return frame
   }

}