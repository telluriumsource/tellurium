package org.telluriumsource.crosscut.i18n

import java.util.Locale
import java.util.Map
import java.util.Set
import java.util.ResourceBundle

import groovy.lang.Singleton;
import org.telluriumsource.framework.config.Configurable

/**
 * ResourceBundleSource
 *
 * @author Ajay (ajay.ravichandran@gmail.com)
 *
 * Date: Sep 23, 2009
 *
 */
@Singleton
class ResourceBundleSource implements Configurable {

	//define the default locale
	private Locale defaultLocale
	//map every locale to the ResourceBundle name
	private Set<String> bundleBaseNames = new HashSet<String>()

	//map every locale to a ResourceBundle defined for that locale
	private Map<Locale , Set<java.util.ResourceBundle>> bundles = new HashMap<Locale , Set<java.util.ResourceBundle>>()
	
	private boolean isInitialized = false;

	public void init(){
		if(!isInitialized){
			defaultLocale = Locale.getDefault()
			addResourceBundle("DefaultMessagesBundle" , defaultLocale)
			isInitialized = true;
		}
	}

	def addResourceBundle(String resourceBundleName , Locale specificLocale = null)
	{
		if(specificLocale == null)
			specificLocale = defaultLocale;
		Set<java.util.ResourceBundle> bundlesFromMap = bundles.get(specificLocale)

		if(bundlesFromMap == null)
			bundlesFromMap = new HashSet<java.util.ResourceBundle>()
		
		bundlesFromMap.add(java.util.ResourceBundle.getBundle(resourceBundleName , specificLocale))
		bundles.put(specificLocale, bundlesFromMap)
		
		bundleBaseNames.add(resourceBundleName)
	}

	def String getMessageFromBundle(Locale specificLocale , String messageKey )
	  {
		  String translatedMessage
			//check if the given locale has bundles defined
		  Set<java.util.ResourceBundle> bundleSet = bundles.get(specificLocale);
		  if (bundleSet == null)
		  {
		     bundleSet = new HashSet<java.util.ResourceBundle>();
		     for (String bundleNames: bundleBaseNames) {
		    	 bundleSet.add(java.util.ResourceBundle.getBundle(bundleNames , specificLocale))
		     }
		     bundles.put(specificLocale , bundleSet);
		  }

		  for (java.util.ResourceBundle bundle : bundleSet)
		  {
			  try
			  {
				  translatedMessage = bundle.getString(messageKey)
			  }
			  catch(MissingResourceException ex)
			  {
				  translatedMessage = null;
			  }
			  if(translatedMessage !=null ) break
				  
		  }
		  return translatedMessage
	  }

	def getLocale(){
		return defaultLocale
	}

    public void updateDefaultLocale(Locale locale){
        this.defaultLocale = locale;
    } 
}