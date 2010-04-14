package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.object.List
import org.telluriumsource.ui.routing.RTree

/**
 * List builder
 *
 *
* @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class ListBuilder extends UiObjectBuilder{
   static final String SEPARATOR = "separator"

   def build(Map map, Closure closure){
       def df = [:]
       List list = this.internBuild(new List(), map, df)
       list.rTree = new RTree();
       list.rTree.preBuild();
       list.rTree.indices = list.components;
       if(map.get(SEPARATOR) != null){
           list.separator = map.get(SEPARATOR)
       }
       
       if(closure)
          closure(list)

       return list
   }

   def build(org.telluriumsource.ui.object.List list, UiObject[] objects){

      if(list == null || objects == null || objects.length < 1)
        return list

      objects.each {UiObject obj ->

        list.add(obj)
      }

      return list
   }

   def build(org.telluriumsource.ui.object.List list, UiObject object){

      if(list == null || object == null)
        return list

      list.add(object)

      return list
   }

}