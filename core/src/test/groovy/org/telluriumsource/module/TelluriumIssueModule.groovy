package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext
import org.telluriumsource.ui.object.UiObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 6, 2010
 * 
 */

public class TelluriumIssueModule extends DslContext {

  public void defineUi() {

    //define UI module of a form include issue type selector and issue search
    ui.Form(uid: "issueSearch", clocator: [action: "list", method: "GET"], group: "true") {
      Selector(uid: "issueType", clocator: [name: "can", id: "can"])
      TextBox(uid: "searchLabel", clocator: [tag: "span", text: "*for"])
      InputBox(uid: "searchBox", clocator: [type: "text", name: "q"])
      SubmitButton(uid: "searchButton", clocator: [value: "Search"])
    }

    ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
      //Define the header elements
      UrlLink(uid: "{header: any} as ID", clocator: [text: "*ID"])
      UrlLink(uid: "{header: any} as Type", clocator: [text: "*Type"])
      UrlLink(uid: "{header: any} as Status", clocator: [text: "*Status"])
      UrlLink(uid: "{header: any} as Priority", clocator: [text: "*Priority"])
      UrlLink(uid: "{header: any} as Milestone", clocator: [text: "*Milestone"])
      UrlLink(uid: "{header: any} as Owner", clocator: [text: "*Owner"])
      UrlLink(uid: "{header: any} as Summary", clocator: [text: "*Summary + Labels"])
      UrlLink(uid: "{header: any} as Extra", clocator: [text: "*..."])

      //Define table body elements
      //Column "Extra" are TextBoxs
      TextBox(uid: "{row: all, column -> Extra}", clocator: [:])
      //For the rest, they are UrlLinks
      UrlLink(uid: "{row: all, column: all}", clocator: [:])
    }

    ui.Table(uid: "issueResultWithCache", cacheable: "true", noCacheForChildren: "false", clocator: [id: "resultstable", class: "results"], group: "true") {
      //define table header
      //for the border column
//      TextBox(uid: "{header: 1} as h1", clocator: [:])
      UrlLink(uid: "{header: any} as ID", clocator: [text: "*ID"])
      UrlLink(uid: "{header: any} as Type", clocator: [text: "*Type"])
      UrlLink(uid: "{header: any} as Status", clocator: [text: "*Status"])
      UrlLink(uid: "{header: any} as Priority", clocator: [text: "*Priority"])
      UrlLink(uid: "{header: any} as Milestone", clocator: [text: "*Milestone"])
      UrlLink(uid: "{header: any} as Owner", clocator: [text: "*Owner"])
      UrlLink(uid: "{header: any} as Summary", clocator: [text: "*Summary + Labels"])
      UrlLink(uid: "{header: any} as Extra", clocator: [text: "*..."])

      //define table elements
      //for the border column
//      TextBox(uid: "{row: all, column: 1}", clocator: [:])
//      TextBox(uid: "{row: all, column: 8}", clocator: [:])
      TextBox(uid: "{row: all, column -> Extra}", clocator: [:])
      //For the rest, just UrlLink
      UrlLink(uid: "{row: all, column: all}", clocator: [:])
    }

  }

  public java.util.List<String> getDataForRow(int row){
    java.util.List<String> lst = new ArrayList<String>()
    lst.add(getText("issueResult[${row}][ID]"));
    lst.add(getText("issueResult[${row}][Type]"));
    lst.add(getText("issueResult[${row}][Status]"));
    lst.add(getText("issueResult[${row}][Priority]"));
    lst.add(getText("issueResult[${row}][Milestone]"));
    lst.add(getText("issueResult[${row}][Owner]"));
    lst.add(getText("issueResult[${row}][Summary]"));
    lst.add(getText("issueResult[${row}][Extra]"));

    return lst;
  }

  public void dumpDataForRow(int row){
    println "----------------- Data for Row ${row} -------------------"
    println "ID: " + getText("issueResult[${row}][ID]");
    println "Type: " + getText("issueResult[${row}][Type]");
    println "Status: " + getText("issueResult[${row}][Status]");
    println "Priority: " + getText("issueResult[${row}][Priority]");
    println "Milestone: " + getText("issueResult[${row}][Milestone]");
    println "Owner: " + getText("issueResult[${row}][Owner]");
    println "Summary: " + getText("issueResult[${row}][Summary]");
    println "Extra: " + getText("issueResult[${row}][Extra]");
  }

  public int getRowNum(){
    return getTableMaxRowNum("issueResult");
  }

  public java.util.List<String> getDataForColumn(int column){
        int nrow = getTableMaxRowNum("issueResult")
        if(nrow > 20) nrow = 20
        java.util.List<String> lst = new ArrayList<String>()
        for(int i=1; i<nrow; i++){
            lst.add(getText("issueResult[${i}][${column}]"))
        }

        return lst
  }

  public java.util.List<String> getDataForColumnWithCache(int column){
        int nrow = getTableMaxRowNum("issueResultWithCache")
        if(nrow > 20) nrow = 20
        java.util.List<String> lst = new ArrayList<String>()
        for(int i=1; i<nrow; i++){
            lst.add(getText("issueResultWithCache[${i}][${column}]"))
        }

        return lst
  }

  public String[] getAllText(){
    return getAllTableCellText("issueResult")
  }

  public int getTableCellCount(){
    String sel = getSelector("issueResult")
    sel = sel + " > tbody > tr > td"

    return getCssSelectorCount(sel)
  }

  public String[] getTableCSS(String name){
    String[] result = getCSS("issueResult.header[1]", name)

    return result
  }

  public String[] getIsssueTypes() {
    return getSelectOptions("issueSearch.issueType")
  }

  public void selectIssueType(String type) {
    selectByLabel "issueSearch.issueType", type
  }

  public void searchIssue(String issue) {
    keyType "issueSearch.searchBox", issue
//        type "issueSearch.searchBox", issue
    click "issueSearch.searchButton"
    waitForPageToLoad 30000
  }

  public boolean checkamICacheable(String uid){
    UiObject obj = getUiElement(uid)
    boolean cacheable = obj.amICacheable()
    return cacheable
  }

}