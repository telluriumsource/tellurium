package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 6, 2008
 *
 */
class Table8 extends DslContext{
   public void defineUi() {    
       ui.Table(uid: "IdMenu", clocator:[header: "/div[@class='popup' and @id='pop_0']"], group: "true"){
           TextBox(uid: "{row:1, column:1}", clocator:[text: "Sort Up"])
           TextBox(uid: "{row:2, column:1}", clocator:[text: "Sort Down"])
           TextBox(uid: "{row:3, column:1}", clocator:[text: "Hide Column"])
       }
   }

}