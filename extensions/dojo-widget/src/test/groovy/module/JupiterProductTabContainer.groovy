package module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 12, 2009
 * 
 */
class JupiterProductTabContainer extends DslContext{

    public void defineUi() {
        ui.Container(uid: "ProductTab", clocator: [tag: "div", id: "%%jtv_widget_JtvTabContainer", class: "%%productTabContainer"]){
            DOJO_JtvTabContainer(uid: "TabContainer", clocator: [:], tabs: ["Recent", "Search", "Turntable"])
        }
    }

    public void clickTab(String tabName){
        onWidget "ProductTab.TabContainer", clickOnTab, tabName
        waitForPageToLoad 30000
    }
}