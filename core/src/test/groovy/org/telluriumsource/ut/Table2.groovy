package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 * Test scenario for ROW ALL MATCHING
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jun 30, 2008
 */
class Table2 extends DslContext{
    public void defineUi() {
        ui.Container(uid: "main"){
            InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
            Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
            Table(uid: "table1", locator: "//table"){
               Button(uid: "{row: all, column: 1}", locator: "/input[@type='submit']")
               InputBox(uid: "{row: all, column: 2}", locator: "/input[@title='Google Search']")
            }
        }
    }
}