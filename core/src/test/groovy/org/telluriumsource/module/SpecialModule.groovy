package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 6, 2010
 * 
 */
class SpecialModule extends DslContext{

  public void defineUi(){
    ui.Form(uid: "Form", clocator: [method: "POST", action: "check_phone"]){
      Selector(uid: "Country", clocator: [name: "\$CountryAccessCode"])
      InputBox(uid: "Number", clocator: [name: "\$PhoneNumber"])
      SubmitButton(uid: "check", clocator: [value: "Check"])
    }
  }

  public void check(String country, String number){
    select "Form.Country", country
    keyType "Form.Number", number
    click "Form.check"
    waitForPageToLoad 30000
  }
}
