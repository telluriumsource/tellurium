package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 27, 2009
 * 
 */

public class CustomUIExample extends DslContext {

  public void defineUi() {
    ui.Container(uid: "switch", clocator: [id: "switch1", tag: "switch"], namespace: "xforms", group: "true") {
      Container(uid: "case1", clocator: [id: "case-1", tag: "case"], namespace: "xforms")
      Container(uid: "case2", clocator: [id: "case-2", tag: "case"], namespace: "xforms")
      Container(uid: "case3", clocator: [id: "case-3", tag: "case"], namespace: "xforms")

    }

    ui.Container(uid:"caseRecordPopUp", clocator:[id:"CaseRecordsPopUp", tag:"div"],namespace:"xhtml",group:"true") {
      Container(uid:"date",clocator:[id:"caseRecordPopUpDate", tag:"input"], namespace:"xforms")
      Container(uid:"complaints",clocator:[id:"caseRecordPopUpComplaints", tag:"input"], namespace:"xforms")
      Container(uid:"weight",clocator:[id:"caseRecordPopUpWeight", tag:"input"], namespace:"xforms")
      Container(uid:"bp",clocator:[id:"caseRecordPopUpBP", tag:"input"], namespace:"xforms")
      Container(uid:"fh",clocator:[id:"caseRecordPopUpFH", tag:"input"], namespace:"xforms")
      Container(uid:"fhr",clocator:[id:"caseRecordPopUpFHR", tag:"input"], namespace:"xforms")
      Container(uid:"po",clocator:[id:"caseRecordPopUpPO", tag:"textarea"], namespace:"xforms")
      Container(uid:"pv",clocator:[id:"caseRecordPopUpPV", tag:"textarea"], namespace:"xforms")
      Container(uid:"remarks",clocator:[id:"caseRecordPopUpRemarks", tag:"textarea"], namespace:"xforms")

    }

    ui.Container(uid: "subnav", clocator: [tag: "ul", id: "subnav"]) {
      Container(uid: "CoreLinks", clocator: [tag: "li", id: "core_links"]) {
        List(uid: "links", clocator: [tag: "ul"], separator: "li") {
          UrlLink(uid: "{all}", clocator: [:])
        }
      }
      UrlLink(uid: "subscribe", clocator: [tag: "li", id: "subscribe"])
    }
    
    ui.Form(uid: "accountEdit", clocator: [tag: "form", id: "editPage", method: "post"]) {
      InputBox(uid: "accountName", clocator: [tag: "input", type: "text", name: "acc2", id: "acc2"])
      InputBox(uid: "accountSite", clocator: [tag: "input", type: "text", name: "acc23", id: "acc23"])
      InputBox(uid: "accountRevenue", clocator: [tag: "input", type: "text", name: "acc8", id: "acc8"])
      TextBox(uid: "heading", clocator: [tag: "h2", text: "*Account Edit "])
      SubmitButton(uid: "save", clocator: [tag: "input", class: "btn", type: "submit", title: "Save", name: "save"])
    }

    ui.TextBox(uid: "confirmation", clocator: [tag: "h3", text: "\$Confirmation"])

    ui.Container(uid: "familyScreen", clocator: [tag: "table"], group: "true") {
        List(uid: "familyDetails", clocator: [id: "AddAnotherDependent"], separator: "table", group: "true") {
          InputBox(uid: "{any} as dependentFirstName", clocator: [id: '^ctl00_ctl00_bcr_bcr_ucDependent_RFirstName_'])
        }
    }

    ui.Container(uid: "repeat", clocator:[tag: "div"]){
      Repeat(uid:'expender',clocator:[tag:'div',class:'Expander !narrow'],respond:['click']){
        UrlLink(uid: "link", clocator:[:])
      }
      Repeat(uid:'narrow',clocator:[tag:'div',class:'Expander narrow'],respond:['click']){
        UrlLink(uid: "link", clocator:[:])
      }
    }

    ui.Container(uid: "Header", locator: "//table[@class ='outerBox']/tbody/tr[1]") {
         Container(uid: "nav", clocator: [tag: "table"]) {
            UrlLink(uid: "logout", clocator: [href: "*logout.do"])
         }
    }

  }

  public String getXCaseStatus(int tabnum){
      String caseId = "switch.case"+tabnum;
      String status = (String)customUiCall(caseId,"getXCaseStatus"); 
      return status;
  }

}