package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 17, 2008
 * 
 */
class TWindow extends DslContext{

    public void defineUi() {
         ui.Window(uid: "windowName", name: "windowName", id: "windowName"){
             InputBox(uid: "UserName", clocator: [id: "username", type: "text"])             
         }

        ui.Window(uid:'moreInfo',clocator:[name:'moreInfoChart',id:'moreInfoChart']){
            InputBox(uid: "UserName", clocator: [id: "username", type: "text"])
        } 
    }

    public void waitForPopup(){
        waitForPopUp "windowName", 30000
    }

    public void selectOriginalWindow(){
        selectMainWindow()
    }

    public void selectChildWindow(String uid){
        selectWindow(uid)
    }
}