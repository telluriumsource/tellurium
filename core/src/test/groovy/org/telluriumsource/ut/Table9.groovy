package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 *   Used to test the tbody attribute
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *    Date: Feb 24, 2009
  *
 */

public class Table9 extends DslContext{
   public void defineUi() {
       ui.Table(uid: "IdMenu", clocator: [id: "someId", tbody: [id: "tbody1_Id"]]){
           TextBox(uid: "{row:1, column:1}", clocator:[text: "Sort Up"])
           TextBox(uid: "{row:2, column:1}", clocator:[text: "Sort Down"])
           TextBox(uid: "{row:3, column:1}", clocator:[text: "Hide Column"])
       }
   }

}