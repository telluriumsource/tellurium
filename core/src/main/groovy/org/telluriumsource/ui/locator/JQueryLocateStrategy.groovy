package org.telluriumsource.ui.locator

class JQueryLocateStrategy  {

	def static boolean canHandle(Object locator) {
		if(locator instanceof JQLocator){
        	return true
		} else {
        	return false
		}
	}

	def static String locate(Object locator) {
		return "jquery="+locator.loc
	}

}
