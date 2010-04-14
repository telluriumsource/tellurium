package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 28, 2010
 * 
 * 
 */
class CheckBoxExample extends DslContext
{
  public void defineUi() {
    ui.Form(uid: "root", clocator: [tag: "form", method: "get", action: "/samples/checkbox.asp"])
    {
      CheckBox(uid: "input0", clocator: [tag: "input", type: "checkbox", name: "default"])
    }
  }

  def checkOneBox(String input) {
    click input
  }

  def checkBoxValues(String input) {
    getValue(input)
  }

}

