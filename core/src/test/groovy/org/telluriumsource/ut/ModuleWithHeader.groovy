package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

public class ModuleWithHeader extends DslContext {
  
  public void defineUi() {
    ui.Container(uid: "SearchModule", clocator: [tag: "td"]) {
      InputBox(uid: "Input", clocator: [title: "Google Search", header: "/div"])
      SubmitButton(uid: "Search", clocator: [name: "btnG", value: "Google Search", header: "/div[3]/p"])
      SubmitButton(uid: "ImFeelingLucky", clocator: [value: "I'm Feeling Lucky"], header: "/div[2]/p/div[@class='good']")
    }
  }
}