package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 *   A sample UI includes all default UI object types
 * 
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class SampleUI extends DslContext {
  public void defineUi() {
    ui.Container(uid: "main1") {
      InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
      Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
      Icon(uid: "icon1", locator: "//image")
      Image(uid: "image1", locator: "//image[@src='a.gif")
      Selector(uid: "select1", locator: "//selector[1]")
      RadioButton(uid: "radioButton1", locator: "//input[@type='radio")
      TextBox(uid: "textbox1", locator: "//div[3]/p")
      UrlLink(uid: "urllink1", locator: "//a[@href='text.htm'")
      Table(uid: "table1", locator: "//table") {
        Button(uid: "{row: all, column: all}", locator: "/input[@type='submit']")
      }
      List(uid: "subcategory", locator: "/p", separator: "/p") {
        UrlLink(uid: "{all}", locator: "/a")
      }
    }
  }

  public void defineCompositeUi() {
    ui.Container(uid: "main2") {
      InputBox(uid: "inputbox1", clocator: [tag: "input", title: "Google Search"])
      Button(uid: "button1", clocator: [tag: "input", name: "btn", type: "submit"])
      Icon(uid: "icon1", clocator: [tag: "image"])
      Image(uid: "image1", clocator: [tag: "image", src: "a.gif"])
      Selector(uid: "select1", clocator: [tag: "selector", position: "1"])
      RadioButton(uid: "radioButton1", clocator: [tag: "input", type: "radio"])
      TextBox(uid: "textbox1", clocator: [tag: "div", position: "3"])
      UrlLink(uid: "urllink1", clocator: [tag: "a", href: "text.htm"])
      Table(uid: "table1", clocator: [tag: "table"]) {
        Button(uid: "{row: all, column: all}", clocator: [tag: "input", type: "submit"])
      }
      List(uid: "subcategory", clocator: [tag: "p"], separator: "/p") {
        UrlLink(uid: "{all}", clocator: [tag: "a"])
      }
    }
  }

  public void defineMultipleUiModules() {
    ui.Container(uid: "main1") {
      InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
      Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
      Icon(uid: "icon1", locator: "//image")
      Image(uid: "image1", locator: "//image[@src='a.gif")
      Selector(uid: "select1", locator: "//selector[1]")
      RadioButton(uid: "radioButton1", locator: "//input[@type='radio")
      TextBox(uid: "textbox1", locator: "//div[3]/p")
      UrlLink(uid: "urllink1", locator: "//a[@href='text.htm'")
      Table(uid: "table1", locator: "//table") {
        Button(uid: "{row: all, column: all}", locator: "/input[@type='submit']")
      }
      List(uid: "subcategory", locator: "/p", separator: "/p") {
        UrlLink(uid: "{all}", locator: "/a")
      }
    }

    ui.Container(uid: "main2") {
      InputBox(uid: "inputbox1", clocator: [tag: "input", title: "Google Search"])
      Button(uid: "button1", clocator: [tag: "input", name: "btn", type: "submit"])
      Icon(uid: "icon1", clocator: [tag: "image"])
      Image(uid: "image1", clocator: [tag: "image", src: "a.gif"])
      Selector(uid: "select1", clocator: [tag: "selector", position: "1"])
      RadioButton(uid: "radioButton1", clocator: [tag: "input", type: "radio"])
      TextBox(uid: "textbox1", clocator: [tag: "div", position: "3"])
      UrlLink(uid: "urllink1", clocator: [tag: "a", href: "text.htm"])
      Table(uid: "table1", clocator: [tag: "table"]) {
        Button(uid: "{row: all, column: all}", clocator: [tag: "input", type: "submit"])
      }
      List(uid: "subcategory", clocator: [tag: "p"], separator: "/p") {
        UrlLink(uid: "{all}", clocator: [tag: "a"])
      }
    }

    ui.Table(uid: "table2", clocator: [tag: "table"]) {
      Button(uid: "{row: all, column: all}", clocator: [tag: "input", type: "submit"])
    }

    ui.List(uid: "list2", clocator: [tag: "p"], separator: "/p") {
      UrlLink(uid: "{all}", clocator: [tag: "a"])
    }
    }

    public void defineUIWithNamespace() {
//        registerNamespace("xform", XFORM_URI)
/*
        <xforms:submit....>
               ...
           <xforms:input model="clinicalExaminationModel" ref="/root/ClinicalExamination/GeneralExamination/Weight">
           <xforms:label>Weight </xforms:label>
*/
        ui.Form(uid: "SubmitForm", namespace: "xforms", clocator:[:]){
          InputBox(uid: "Input", namespace: "xforms", clocator:[module: "clinicalExaminationModel"])
          TextBox(uid: "Label", namespace: "xforms", clocator: [tag: "label"])
        }
    }
}