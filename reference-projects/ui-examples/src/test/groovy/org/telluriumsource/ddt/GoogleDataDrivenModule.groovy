package org.telluriumsource.ddt

import org.telluriumsource.test.ddt.TelluriumDataDrivenModule

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 18, 2010
 * 
 * 
 */
class GoogleDataDrivenModule extends TelluriumDataDrivenModule{

  public void defineModule() {
    ui.Container(uid: "Google", clocator: [tag: "table"]) {
      InputBox(uid: "Input", clocator: [tag: "input", title: "Google Search", name: "q"])
      SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btnG"])
      SubmitButton(uid: "ImFeelingLucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnI"])
    }

    fs.FieldSet(name: "GoogleSearch", description: "Data format for testing Google Search") {
        Test(value: "DoGoogleSearch")
        Field(name: "SearchWords", description: "Search Key Words")
    }

    fs.FieldSet(name: "FeelingLuckySearch", description: "Data format for testing Google Search") {
        Test(value: "DoFeelingLuckySearch")
        Field(name: "SearchWords", description: "Search Key Words")
    }

    defineTest("DoGoogleSearch") {
      connectUrl "http://www.google.com"
      String input = bind("GoogleSearch.SearchWords")
      keyType "Google.Input", input
      pause 500
      click "Google.Search"
      waitForPageToLoad 30000
    }

    defineTest("DoFeelingLuckySearch") {
      connectUrl "http://www.google.com"
      String input = bind("FeelingLuckySearch.SearchWords")
      keyType "Google.Input", input
      pause 500
      click "Google.ImFeelingLucky"
      waitForPageToLoad 30000
    }
  }
}
