package org.telluriumsource.ui.locator

import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle;


class PlaceHolderLocateStrategy {

    public static final String PLACE_HOLDER = "\\?";
    def static boolean canHandle(locator){
       if(locator instanceof PlaceHolderLocator)
        return true
       else
        return false
    }

    def static String locate(PlaceHolderLocator locator){
    	IResourceBundle i18nBundle = Environment.instance.myResourceBundle()
        if(locator == null)
            throw new RuntimeException(i18nBundle.getMessage("PlaceHolderLocatorStrategy.InvalidNullLocator"))
        def template = locator.template
        def attributes = locator.attributes

        if(template == null || attributes == null)
			return template;

		String loc = new String(template);
		for(String attribute : attributes){

			if(attribute != null){
				loc = loc.replaceFirst(PLACE_HOLDER, attribute);
			}else{
	            throw new RuntimeException(i18nBundle.getMessage("PlaceHolderLocatorStrategy.InvalidNullAttribute"))
			}
		}

		return loc;
    }
}