package org.telluriumsource.crosscut.i18n

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;

/**
 * ResourceBundle - provides internationalization support
 *
 * @author Ajay (ajay.ravichandran@gmail.com)
 *
 * Date: Sep 23, 2009
 *
 */
class ResourceBundle implements IResourceBundle
{

	public ResourceBundle(){
		ResourceBundleSource.instance.init()
	}

    public void updateDefaultLocale(Locale locale){
        ResourceBundleSource.instance.updateDefaultLocale(locale)
    }

	public void addResourceBundle(String resourceBundle, Locale specificLocale = null){
		ResourceBundleSource.instance.addResourceBundle(resourceBundle , specificLocale)
	}
	//i18n for currency and numbers
	public String getCurrency(Double doubleValue){
		return getCurrency(doubleValue, getLocale())
	}

	public Locale getLocale(){
		return ResourceBundleSource.instance.getLocale()
	}

   	public String getNumber(Double doubleValue){
		return getNumber(doubleValue, getLocale())
	}
	public String getCurrency(Double doubleValue , Locale specificLocale){
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(specificLocale)
		return currencyFormatter.format(doubleValue)
	}
	public String getNumber(Double doubleValue , Locale specificLocale){
		NumberFormat numberFormatter = NumberFormat.getNumberInstance(specificLocale)
		return numberFormatter.format(doubleValue)
	}
	  //i18n for dates and time

	public String getDate(Date dateValue){
		return getDate(dateValue, getLocale())
	}
	public String getTime(Date timeValue){
		return getTime(timeValue, getLocale())
	}
	public String getDate(Date dateValue , Locale specificLocale){
		DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, specificLocale)
		return dateFormatter.format(dateValue)
	}
	public String getTime(Date timeValue , Locale specificLocale){
		DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, specificLocale)
		return timeFormatter.format(timeValue)
	}

	public String getMessage(String messageKey , Object... arguments){
		return getMessage(messageKey , getLocale() , arguments)
	}
	public String getMessage(String messageKey , Locale specificLocale , Object... arguments){
		MessageFormat formatter = new MessageFormat(messageKey, specificLocale)
		String translatedMessage = ResourceBundleSource.instance.getMessageFromBundle(specificLocale , messageKey)
		if(translatedMessage == null)
		{
			throw new MissingResourceException("Can't find resource in any of the bundles "
	                + this.getClass().getName()
	                +", key "+messageKey,
	                this.getClass().getName(),
	                messageKey);
		}
		return formatter.format(translatedMessage , arguments);
	}


	public String getMessage(String messageKey){
		getMessage(messageKey , getLocale())
	}
	public String getMessage(String messageKey , Locale specificLocale){
		String translatedMessage = ResourceBundleSource.instance.getMessageFromBundle(specificLocale , messageKey)
		if(translatedMessage == null)
		{
			throw new MissingResourceException("Can't find resource in any of the bundles "
                  + this.getClass().getName()
                  +", key "+messageKey,
                  this.getClass().getName(),
                  messageKey);
		}
		return translatedMessage
	}

}