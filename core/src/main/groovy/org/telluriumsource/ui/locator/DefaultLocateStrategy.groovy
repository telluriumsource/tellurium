package org.telluriumsource.ui.locator

class DefaultLocateStrategy{

    def static boolean canHandle(locator){
       if(locator instanceof BaseLocator)
        return true
       else
        return false
    }

    def static String locate(BaseLocator locator){

        locator.loc
    }

    def static String locate(BaseLocator locator, int index){

        locator.loc + "[${index}]"
    }
}