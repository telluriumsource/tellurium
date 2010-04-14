package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * Ui Modules for Tellurium Issues page at
 *
 *   http://code.google.com/p/aost/issues/list
 *
 * @author   Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 18, 2010
 * 
 */
class TelluriumIssuesPage extends DslContext{

   public void defineUi() {
       //define UI module of a form include issue type selector and issue search
        ui.Form(uid: "issueSearch", clocator: [action: "list", method: "GET"], group: "true") {
          Selector(uid: "issueType", clocator: [name: "can", id: "can", direct: "true"])
          TextBox(uid: "searchLabel", clocator: [tag: "span", text: "for"])
          InputBox(uid: "searchBox", clocator: [type: "text", name: "q", id: "q"])
          SubmitButton(uid: "searchButton", clocator: [value: "Search", direct: "true"])
        }
 
       ui.Form(uid: "issueAdvancedSearch", clocator: [action: "advsearch.do", method: "POST"], group: "true"){
         Container(uid: "searchTable", clocator: [class: "advquery"]){
             Selector(uid: "issueTypes", clocator: [name: "can"])
             SubmitButton(uid: "search", clocator: [value: "Search", name: "btn"])
             InputBox(uid: "include", clocator:[name: "words"])
             InputBox(uid: "exclude", clocator:[name: "without"])
             InputBox(uid: "labels", clocator:[name: "labels"])
             UrlLink(uid: "searchTips", clocator:[text: "*Search Tips"])
             InputBox(uid: "statuses", clocator:[name: "statuses"])
             InputBox(uid: "reporters", clocator:[name: "reporters"])
             InputBox(uid: "owners", clocator:[name: "owners"])
             InputBox(uid: "cc", clocator:[name: "cc"])
             InputBox(uid: "commentBy", clocator:[name: "commentby"])
         }
       }

       ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
         TextBox(uid: "{header: 1}", clocator: [:])
         UrlLink(uid: "{header: 2} as ID", clocator: [text: "*ID"])
         UrlLink(uid: "{header: 3} as Type", clocator: [text: "*Type"])
         UrlLink(uid: "{header: 4} as Status", clocator: [text: "*Status"])
         UrlLink(uid: "{header: 5} as Priority", clocator: [text: "*Priority"])
         UrlLink(uid: "{header: 6} as Milestone", clocator: [text: "*Milestone"])
         UrlLink(uid: "{header: 7} as Owner", clocator: [text: "*Owner"])
         UrlLink(uid: "{header: 9} as Summary", clocator: [text: "*Summary + Labels"])
         UrlLink(uid: "{header: 10} as Extra", clocator: [text: "*..."])

         //define table elements
         //for the border column
//         TextBox(uid: "row: *, column: 1", clocator: [:])
//         TextBox(uid: "row: *, column: 8", clocator: [:])
//         TextBox(uid: "row: *, column: 10", clocator: [:])
         TextBox(uid: "{row: all, column: 1}")
         TextBox(uid: "{row: all, column: 8}")
         TextBox(uid: "{row: all, column: 10}")
         //For the rest, just UrlLink
         UrlLink(uid: "{row: all, column: all}", clocator: [:])
       }

       //items is a map in the format of "alias name" : menu_item
       ui.SimpleMenu(uid: "IdMenu", clocator:[class: "popup", id: "pop_0"],
               items: ["SortUp":"Sort Up", "SortDown":"Sort Down", "HideColumn":"Hide Column"])

       //define the dot menu where you can select different column to display
       ui.SelectMenu(uid: "selectColumnMenu", clocator:[class: "popup",id: "pop__dot"], title: "Show columns:",
               items: ["ID":"ID", "Type":"Type", "Status":"Status", "Priority":"Priority", "Milestone":"Milestone", "Owner":"Owner", "Summary":"Summary", "Stars":"Stars", "Opened":"Opened", "Closed":"Closed", "Modified":"Modified", "EditColumn":"Edit Column Spec..." ])

       //The selector to choose the data grid layout as List or Grid
       ui.Option(uid: "layoutSelector"){
           Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
               TextBox(uid: "List", clocator: [tag: "b", text: "List", direct: "true"], respond: ["click"])
               UrlLink(uid: "Grid", clocator: [text: "Grid", direct: "true"])
           }
           Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
               UrlLink(uid: "List", clocator: [text: "List", direct: "true"])
               TextBox(uid: "Grid", clocator: [tag: "b", text: "Grid", direct: "true"], respond: ["click"])
           }
       }
   }

    public String[] getIsssueTypes(){
        return  getSelectOptions("issueSearch.issueType")
    }

    public void selectIssueType(String type){
        selectByLabel "issueSearch.issueType", type
    }

    public void searchIssue(String issue){
        keyType "issueSearch.searchBox", issue
//        type  "issueSearch.searchBox", issue
        click "issueSearch.searchButton"
        waitForPageToLoad 30000
    }

    public String[] getAdvancedIssueTypes(){
        return  getSelectOptions("issueAdvancedSearch.searchTable.issueTypes")
    }

    public void advancedSearchIssue(String issueType, String words, String without, String labels, String statuses,
        String reporters, String owners, String cc, String commentby){
        if(issueType != null){
            selectByLabel "issueAdvancedSearch.searchTable.issueTypes", issueType
        }

        if(words != null){
            keyType "issueAdvancedSearch.searchTable.include", words
        }

        if(without != null){
            keyType "issueAdvancedSearch.searchTable.exclude", without
        }

        if(labels != null){
            keyType "issueAdvancedSearch.searchTable.labels", labels
        }

        if(statuses != null){
            keyType "issueAdvancedSearch.searchTable.statuses", statuses
        }

        if(reporters != null){
            keyType "issueAdvancedSearch.searchTable.reporters", reporters
        }

        if(owners != null){
            keyType "issueAdvancedSearch.searchTable.owners", owners
        }

        if(cc != null){
           keyType "issueAdvancedSearch.searchTable.cc", cc
        }

        if(commentby != null){
           keyType "issueAdvancedSearch.searchTable.commentBy", commentby
        }

        click "issueAdvancedSearch.searchTable.search"
        waitForPageToLoad 30000
    }

    public void clickMoreSearchTips(){
        click "issueAdvancedSearch.searchTable.searchTips"
        waitForPageToLoad 30000
    }

    public int getTableHeaderNum(){
        return getTableHeaderColumnNum("issueResult")
    }

    public List<String> getHeaderNames(){
        List<String> headernames = new ArrayList<String>()
        int mcolumn = getTableHeaderColumnNum("issueResult")
        for(int i=1; i<=mcolumn; i++){
            headernames.add(getText("issueResult.header[${i}]"))
        }

        return headernames
    }

    public List<String> getDataForColumn(int column){
        int mcolumn = getTableMaxRowNum("issueResult")
        List<String> lst = new ArrayList<String>()
        for(int i=1; i<=mcolumn; i++){
            lst.add(getText("issueResult[${i}][${column}]"))
        }

        return lst
    }

    public void clickTable(int row, int column){
        click "issueResult[${row}][${column}]"
        waitForPageToLoad 30000
    }

    public void clickOnTableHeader(int column){
        click "issueResult.header[${column}]"
        pause 1000
    }

    public void mouseMoveIdMenu(){
        mouseOver "IdMenu.SortUp"
        pause 500
        mouseOut  "IdMenu.SortUp"
        pause 50

        mouseOver "IdMenu.SortDown"
        pause 500
        mouseOut  "IdMenu.SortDown"
        pause 50

        mouseOver "IdMenu.HideColumn"
        pause 500
        mouseOut  "IdMenu.HideColumn"
        pause 50

        mouseOver "IdMenu.SortDown"
        pause 500
        mouseOut  "IdMenu.SortDown"
        pause 50

        mouseOver "IdMenu.SortUp"
        pause 500
        mouseOut  "IdMenu.SortUp"
        pause 50
    }

    public void toggleIdColumn(String column){
        click "issueResult.header[10]"
        pause 1000
        click "selectColumnMenu.${column}"
        pause 1000
        click "issueResult.header[10]"
        pause 1000
        click "selectColumnMenu.${column}"
        pause 1000
    }

    public void clickIdMenuSortUp(){
        click "IdMenu.SortUp"
        waitForPageToLoad 30000
    }

    public void clickIdMenuSortDown(){
        click "IdMenu.SortDown"
        waitForPageToLoad 30000
    }

    public void clickIdMenuHideColumn(){
        click "IdMenu.HideColumn"
        waitForPageToLoad 30000
    }

    public void selectDataLayout(String layout){
        if("List".equalsIgnoreCase(layout)){
            click "layoutSelector.List"
            waitForPageToLoad 30000
        }
        if("Grid".equalsIgnoreCase(layout)){
            click "layoutSelector.Grid"
            waitForPageToLoad 30000
        }
    }

    public String[] getAllText(){
      return getAllTableCellText("issueResult")
    }

    public int getTableCellCount(){
      String sel = getSelector("issueResult")
      sel = sel + " > tbody > tr > td"

      return getCssSelectorCount(sel)
    }
}
