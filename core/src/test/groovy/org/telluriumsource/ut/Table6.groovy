package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 * Test scenario for specifying header "header: 2"
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 5, 2008
 *
 */
class Table6 extends DslContext{
    public void defineUi() {
        ui.Container(uid: "main"){
            InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
            Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
            Table(uid: "table1", locator: "//table"){
               UrlLink(uid: "{header: 1}", locator: "/link")
               TextBox(uid: "{header: 2}", locator: "/div")
               Button(uid: "{row: 1, column: 1}", locator: "/input[@type='submit']")
               InputBox(uid: "{row: 1, column: 2}", locator: "/input")
               UrlLink(uid: "{row: 2, column: 1}", locator: "/link")
               Button(uid: "{row: 2, column: 2}", locator: "/input")
            }
        }
    }

}