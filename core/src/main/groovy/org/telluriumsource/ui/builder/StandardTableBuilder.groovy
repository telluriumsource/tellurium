package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.StandardTable
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.routing.RTree
import org.telluriumsource.ui.routing.RGraph

/**
 * Build Standard Table
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 4, 2008
 * 
 */
class StandardTableBuilder extends UiObjectBuilder{

   def build(Map map, Closure closure){
       //add default parameters so that the builder can use them if not specified
       def df = [:]
       df.put(TAG, StandardTable.TAG)
       StandardTable table = this.internBuild(new StandardTable(), map, df)
       table.hTree = new RTree();
       table.hTree.indices = table.headers;
       table.hTree.preBuild();
       table.fTree = new RTree();
       table.fTree.indices = table.footers;
       table.fTree.preBuild();
       table.rGraph = new RGraph();
       table.rGraph.indices = table.components;
       table.rGraph.preBuild();
     
       String ht = map.get(StandardTable.HEAD_TAG)
       if(ht != null){
         table.headTag = ht
       }
       String hrt = map.get(StandardTable.HEAD_ROW_TAG)
       if(hrt != null)
          table.headRowTag = hrt
       String hct = map.get(StandardTable.HEAD_COLUMN_TAG)
       if(hct != null)
          table.headColumnTag = hct

       String bt = map.get(StandardTable.BODY_TAG)
       if(bt != null){
         table.bodyTag = bt
       }
       String brt = map.get(StandardTable.BODY_ROW_TAG)
       if(brt != null){
         table.bodyRowTag = brt
       }
       String bct = map.get(StandardTable.BODY_COLUMN_TAG)
       if(bct != null){
         table.bodyColumnTag = bct
       }
     
       String ft = map.get(StandardTable.FOOT_TAG)
       if(ft != null){
         table.footTag = ft
       }
       String frt = map.get(StandardTable.FOOT_ROW_TAG)
       if(frt != null){
          table.footRowTag = frt
       }
       String fct = map.get(StandardTable.FOOT_COLUMN_TAG)
       if(fct != null){
          table.footColumnTag = fct
       }

       if(closure)
          closure(table)

       return table
   }

   def build(StandardTable table, UiObject[] objects){

      if(table == null || objects == null || objects.length < 1)
        return table

      objects.each {UiObject obj -> table.add(obj)}

      return table
   }

   def build(StandardTable table, UiObject object){

      if(table == null || object == null)
        return table

      table.add(object)

      return table
   }

}