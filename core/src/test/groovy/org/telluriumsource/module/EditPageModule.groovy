package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 16, 2010
 * 
 */
class EditPageModule  extends DslContext {
  public void defineUi() {
    ui.Form(uid: "Account", clocator: [tag: "form", method: "post"]) {
      InputBox(uid: "Name", clocator: [tag: "input", type: "text", name: "A"])
      InputBox(uid: "Site", clocator: [tag: "input", type: "text", name: "B"])
      InputBox(uid: "Revenue", clocator: [tag: "input", type: "text", name: "C"])
      Container(uid: "Info", clocator: [tag: "div", class: "info"]){
        TextBox(uid: "Label", clocator: [tag: "div", text: "Test:", id: "label1"])
        InputBox(uid: "Test", clocator: [tag: "input", name: "testname", id: "input5", type: "text"])
      }
      SubmitButton(uid: "Save", clocator: [tag: "input", class: "btn", type: "submit", title: "Save", name: "save"])
    }
  }

  public void fillForm(String name, String site, double revenue, String test){
    keyType "Account.Name", name
    keyType "Account.Site", site
    keyType "Account.Revenue", revenue
    keyType "Account.Info.Test", test
    click "Account.Save"
    waitForPageToLoad 30000
  }
}

