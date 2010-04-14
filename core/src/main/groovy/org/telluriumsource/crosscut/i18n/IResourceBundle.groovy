package org.telluriumsource.crosscut.i18n
import org.telluriumsource.framework.config.Configurable;

/**
 * IResourceBundle - provides internationalization support
 *
 * @author Ajay (ajay.ravichandran@gmail.com)
 *
 * Date: Sep 23, 2009
 *
 */

interface IResourceBundle extends Configurable
{
    public void updateDefaultLocale(Locale locale);
	public Locale getLocale();
	public String getCurrency(Double doubleValue)
    public String getCurrency(Double doubleValue, Locale specificLocale)
	public String getNumber(Double doubleValue)
    public String getNumber(Double doubleValue, Locale specificLocale)
	public String getDate(Date dateValue)
	public String getDate(Date dateValue , Locale specificLocale)
	public String getTime(Date dateValue)
	public String getTime(Date dateValue , Locale specificLocale)
	public String getMessage(String messageKey , Object... arguments)
	public String getMessage(String messageKey , Locale specificLocale , Object... arguments)
	public String getMessage(String messageKey)
	public String getMessage(String messageKey , Locale specificLocale)
	public void addResourceBundle(String resourceBundle, Locale specificLocale)
}