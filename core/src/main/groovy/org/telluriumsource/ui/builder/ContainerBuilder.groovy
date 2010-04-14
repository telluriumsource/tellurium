package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.Container

import org.telluriumsource.ui.object.UiObject

/**
 *    Container builder
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class ContainerBuilder extends UiObjectBuilder{

    def build(Map map, Closure closure){
       def df = [:]
       Container container =this.internBuild(new Container(), map, df)

       if(closure)
          closure(container)
        
       return container
   }

   def build(Container container, UiObject[] objects){

      if(container == null || objects == null || objects.length < 1)
        return container

      objects.each {obj -> container.add(obj)}

      return container
   }

   def build(Container container, UiObject object){

      if(container == null || object == null)
        return container

      container.add(object)

      return container
   }
}