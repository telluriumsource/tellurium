package org.telluriumsource.ft

import org.telluriumsource.dsl.DslScriptEngine

/**
 * sample DdDslContext to demonstrate the usage of DdDslContext
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class GoogleDdDslContext extends DslScriptEngine{

    protected String data = """
        true | 865-692-6000 | tellurium
        false| 865-123-4444 | tellurium selenium test
        true |755-452-444|tellurium groovy
        false|666-784-123 | tellurium user group
        true |865-123-5555|tellurium data driven
    """
    public void test(){
        //define google start page
        ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true") {
            InputBox(uid: "searchbox", clocator: [title: "Google Search"])
            SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
            SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
        }

        //define custom data type and its type handler
        typeHandler "phoneNumber", "org.telluriumsource.ut.PhoneNumberTypeHandler"

        //define file data format
        fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search") {
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        //load file
//        loadData "src/test/example/test/ddt/googlesearchpullinput.txt"
        //use String
        useData data

        step{
            //bind variables
            boolean regularSearch = bind("regularSearch")
            def phoneNumber = bind("fs4googlesearch.phoneNumber")
            String input = bind("input")

            connectUrl "http://www.google.com"
            type "google_start_page.searchbox", input
            pause 500

        }

        stepOver()
        
        //data stepToEnd test assuming the input data format is defined in FieldSet "fs4googlesearch"
        stepToEnd {
            //bind variables
            boolean regularSearch = bind("regularSearch")
            def phoneNumber = bind("fs4googlesearch.phoneNumber")
            String input = bind("input")

            connectUrl "http://www.google.com"
            type "google_start_page.searchbox", input
            pause 500

            if (regularSearch)
                click "google_start_page.googlesearch"
            else
                click "google_start_page.Imfeelinglucky"

            pause 1000

            connectUrl "http://www.google.com"
            type "google_start_page.searchbox", phoneNumber
            click "google_start_page.Imfeelinglucky"

            pause 1000
        }

        //close file
        closeData()
    }
}