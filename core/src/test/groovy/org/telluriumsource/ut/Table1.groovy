package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 * Test scenario for ALL MATCHING
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jun 30, 2008
 */
class Table1 extends DslContext {
    public void defineUi() {
        ui.Container(uid: "main"){
            InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
            Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
            Table(uid: "table1", locator: "//table"){
               Button(uid: "{row: all, column: all}", locator: "//input[@type='submit']")
            }
        }
    }

}