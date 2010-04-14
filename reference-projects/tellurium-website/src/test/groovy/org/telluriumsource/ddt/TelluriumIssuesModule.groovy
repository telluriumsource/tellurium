package org.telluriumsource.ddt

import org.telluriumsource.test.ddt.TelluriumDataDrivenModule

/**
 * Date driven testing module for tellurium issues page at
 *
 *  http://code.google.com/p/aost/issues/list
 * 
 * @author   Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 18, 2010
 * 
 */
class TelluriumIssuesModule extends TelluriumDataDrivenModule{

    public void defineModule() {
        //define UI module
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

/*
        ui.Form(uid: "issueAdvancedSearch", clocator: [action: "advsearch.do", method: "POST"], group: "true") {
            Table(uid: "searchTable", clocator: [class: "advquery"]) {
                Selector(uid: "{row:1, column: 3} as Type", clocator: [name: "can"])
                SubmitButton(uid: "{row:1, column:4} as Search", clocator: [value: "Search", name: "btn"])
                InputBox(uid: "{row:2, column:3} as Words", clocator: [name: "words"])
                InputBox(uid: "{row:3, column:3} as Without", clocator: [name: "without"])
                InputBox(uid: "{row:5, column:3} as Labels", clocator: [name: "labels"])
                Table(uid: "{row:6, column:1} as Tips", clocator: [:]) {
                    UrlLink(uid: "{row:1, column:1} as SearchTips", clocator: [text: "*Search Tips"])
                }
                InputBox(uid: "{row:6, column:3} as Status", clocator: [name: "statuses"])
                InputBox(uid: "{row:7, column:2} as Reporter", clocator: [name: "reporters"])
                InputBox(uid: "{row:8, column:2} as Owner", clocator: [name: "owners"])
                InputBox(uid: "{row:9, column:2} as CC", clocator: [name: "cc"])
                InputBox(uid: "{row:10, column:3} as CommentBy", clocator: [name: "commentby"])
            }
        }
*/

        ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
            //define table header
            //for the border column
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
            TextBox(uid: "{row: all, column: 1}", clocator: [:])
            TextBox(uid: "{row: all, column: 8}")
            TextBox(uid: "{row: all, column: 10}")
          
            //For the rest, just UrlLink
            UrlLink(uid: "{row: all, column: all}", clocator: [:])
        }

        //items is a map in the format of "alias name" : menu_item
        ui.SimpleMenu(uid: "IdMenu", clocator: [class: "popup", id: "pop_0"],
                items: ["SortUp": "Sort Up", "SortDown": "Sort Down", "HideColumn": "Hide Column"])

        //define the dot menu where you can select different column to display
        ui.SelectMenu(uid: "selectColumnMenu", clocator: [class: "popup", id: "pop__dot"], title: "Show columns:",
                items: ["ID": "ID", "Type": "Type", "Status": "Status", "Priority": "Priority", "Milestone": "Milestone", "Owner": "Owner", "Summary": "Summary", "Stars": "Stars", "Opened": "Opened", "Closed": "Closed", "Modified": "Modified", "EditColumn": "Edit Column Spec..."])

        //The selector to choose the data grid layout as List or Grid
        ui.Option(uid: "layoutSelector") {
            Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
                TextBox(uid: "List", clocator: [tag: "b", text: "List", direct: "true"])
                UrlLink(uid: "Grid", clocator: [text: "Grid", direct: "true"])
            }
            Container(uid: "layoutSelector", clocator: [tag: "div"], group: "true") {
                UrlLink(uid: "List", clocator: [text: "List", direct: "true"])
                TextBox(uid: "Grid", clocator: [tag: "b", text: "Grid", direct: "true"])
            }
        }

        //define file data format
        fs.FieldSet(name: "OpenIssuesPage") {
            Test(value: "OpenTelluriumIssuesPage")
        }

        fs.FieldSet(name: "IssueForOwner", description: "Data format for test SearchIssueForOwner") {
            Test(value: "SearchIssueForOwner")
            Field(name: "issueType", description: "Issue Type")
            Field(name: "owner", description: "Owner")
        }

        defineTest("OpenTelluriumIssuesPage") {
            connectUrl "http://code.google.com/p/aost/issues/list"
            int headernum = getTableHeaderNum()
            checkResult(headernum) {
                assertTrue(headernum >= 7)
            }
            //store header number
            cacheVariable("headernum", headernum)
            List<String> headernames = getHeaderNames(headernum)
            assertNotNull(headernames)
            assertTrue(headernames.size() > 1)
            //store header names
            cacheVariable("headernames", headernames)

            String[] issueTypes = getIsssueTypes()
            assertNotNull(issueTypes)
            assertTrue(issueTypes.length > 1)
            cacheVariable("issuetypes", issueTypes)
        }

        defineTest("SearchIssueForOwner") {
            String issueType = bind("IssueForOwner.issueType")
            String issueOwner = bind("IssueForOwner.owner")
            int headernum = getCachedVariable("headernum")
            int expectedHeaderNum = getTableHeaderNum()
            compareResult(expectedHeaderNum, headernum)

            List<String> headernames = getCachedVariable("headernames")
            String[] issueTypes = getCachedVariable("issuetypes")
            String issueTypeLabel = getIssueTypeLabel(issueTypes, issueType)
            checkResult(issueTypeLabel) {
                assertTrue(issueTypeLabel != null)
            }
            //select issue type
            if (issueTypeLabel != null) {
                selectIssueType(issueTypeLabel)
            }
            //search for all owners
            if ("all".equalsIgnoreCase(issueOwner.trim())) {
                searchForAllIssues()
            } else {
                searchIssue("owner:" + issueOwner)
            }
            //sort ID
            clickIdMenuSortUp()
            int column = getSummaryLabelsColumn(headernames)
            int rownum = getTableRowNum()
            List<String> list = getDataForColumn(column, rownum)
            if (list != null && list.size() > 0) {
                List<String> actual = new ArrayList<String>()
                list.each {String element ->
                    if(element != null)
                        actual.add(element)
                }

                logMessage "Found ${actual.size()} ${issueTypeLabel} for owner " + issueOwner
                actual.each {String element ->
                    logMessage "Issue: ${element}"
                }
            }else{
                logMessage "Did not find any ${issueTypeLabel} for owner " + issueOwner
            }
        }

    }

    //common methods
    protected String[] getIsssueTypes() {
        return getSelectOptions("issueSearch.issueType")
    }

    protected void selectIssueType(String type) {
        selectByLabel "issueSearch.issueType", type
    }

    protected String getIssueTypeLabel(String[] issueTypes, String issue) {
        for (String issueType: issueTypes) {
            if (issueType.contains(issue)) {
                return issueType
            }
        }

        return null
    }

    protected int getSummaryLabelsColumn(List<String> headernames) {
        int column = 9

        headernames.eachWithIndex {String name, int index ->
            if (name.contains("Summary + Labels")) {
                column = index + 1
            }
        }

        return column
    }

    protected void searchIssue(String issue) {
        clearText "issueSearch.searchBox"
//        keyType "issueSearch.searchBox", issue
        type "issueSearch.searchBox", issue
        click "issueSearch.searchButton"
        waitForPageToLoad 30000
    }

    protected void searchForAllIssues() {
        clearText "issueSearch.searchBox"
        click "issueSearch.searchButton"
        waitForPageToLoad 30000
    }

    protected void clickIdMenuSortUp() {
        click "IdMenu.SortUp"
        waitForPageToLoad 30000
    }

    protected void clickIdMenuSortDown() {
        click "IdMenu.SortDown"
        waitForPageToLoad 30000
    }

    protected int getTableHeaderNum() {
        return getTableHeaderColumnNum("issueResult")
    }

    protected int getTableRowNum() {
        return getTableMaxRowNum("issueResult")
    }

    protected List<String> getHeaderNames(int mcolumn) {
        List<String> headernames = new ArrayList<String>()
        for (int i = 1; i <= mcolumn; i++) {
            headernames.add(getText("issueResult.header[${i}]"))
        }

        return headernames
    }

    protected List<String> getDataForColumn(int column, int mcolumn) {
        List<String> lst = new ArrayList<String>()
        for (int i = 1; i <= mcolumn; i++) {
            lst.add(getText("issueResult[${i}][${column}]"))
        }

        return lst
    }

}
