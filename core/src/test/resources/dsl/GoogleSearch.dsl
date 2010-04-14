ui.Container(uid: "Google", clocator: [tag: "table"]) {
  InputBox(uid: "Input", clocator: [tag: "input", title: "Google Search", name: "q"])
  SubmitButton(uid: "Search", clocator: [tag: "input", type: "submit", value: "Google Search", name: "btnG"])
  SubmitButton(uid: "ImFeelingLucky", clocator: [tag: "input", type: "submit", value: "I'm Feeling Lucky", name: "btnI"])
}

connectSeleniumServer();
connectUrl("http://www.google.com/intl/en/");
keyType "Google.Input", "Tellurium Selenium test"
pause 500
click "Google.Search"
waitForPageToLoad 30000

connectUrl("http://www.google.com/intl/en/");
type "Google.Input", "Tellurium Groovy Testing"
pause 500
click "Google.ImFeelingLucky"
waitForPageToLoad 30000
