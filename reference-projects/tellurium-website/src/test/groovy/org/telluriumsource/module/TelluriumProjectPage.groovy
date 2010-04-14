package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * Sample test for the Tellurium project page at:
 *
 *      http://code.google.com/p/aost/
 * 
 * @author   Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 18, 2010
 * 
 */
class TelluriumProjectPage extends DslContext{

    public void defineUi() {
        //define the menu
        //It is fine to use Container to abstract Table if you have special table
        ui.Container(uid: "menu", clocator: [tag: "table", id: "mt", trailer: "/tbody/tr/th"], group: "true"){
            //since the actual text is  Project&nbsp;Home, we can useString partial match here. Note "%%" stands for partial match
            UrlLink(uid: "project_home", clocator: [text: "*Home"])
            UrlLink(uid: "downloads", clocator: [text: "Downloads"])
            UrlLink(uid: "wiki", clocator: [text: "Wiki"])
            UrlLink(uid: "issues", clocator: [text: "Issues"])
            UrlLink(uid: "source", clocator: [text: "Source"])
        }

        //define the search module, which includes an input box, two search buttons
        ui.Form(uid: "search", clocator: [:], group: "true"){
            InputBox(uid: "searchbox", clocator: [name: "q"])
            SubmitButton(uid: "search_project_button", clocator: [value: "Search projects"])
            SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
        }
    }

    def clickProjectHome(){
        click "menu.project_home"
        waitForPageToLoad 30000
    }

    def clickDownloads(){
        click "menu.downloads"
        waitForPageToLoad 30000
    }

    def clickWiki(){
        click "menu.wiki"
        waitForPageToLoad 30000
    }

    def clickIssues(){
        click "menu.issues"
        waitForPageToLoad 30000
    }

    def clickSource(){
        click "menu.source"
        waitForPageToLoad 30000
    }

    def searchProject(String input){
        type "search.searchbox", input
        click "search.search_project_button"
        waitForPageToLoad 30000
    }

    def searchWeb(String input){
        type "search.searchbox", input
        click "search.search_web_button"
        waitForPageToLoad 30000
    }
}
