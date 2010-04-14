package org.telluriumsource.ut;

import java.sql.Date;


import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle;


public class ResourceBundle_UT extends GroovyTestCase {

	public void testTranslateWithEnglishLocale()
	{
		Locale defaultLocaleForTest = new Locale("en" , "US")
		IResourceBundle i18nBundle = Environment.instance.myResourceBundle()
		i18nBundle.addResourceBundle("TestMessagesBundle")

		//translating of strings
		String messageFromResourceBundle = i18nBundle.getMessage("i18nManager.testString",defaultLocaleForTest)
		assertEquals("This is a testString in English", messageFromResourceBundle)
		
		//translation of number data types
		Double amount = new Double(345987.246);
		String translatedValue = i18nBundle.getNumber(amount,defaultLocaleForTest)
		assertEquals("345,987.246" , translatedValue)

		//translation of currency data types
		amount = new Double(9876543.21);
		translatedValue = i18nBundle.getCurrency(amount,defaultLocaleForTest)
		assertEquals("\$9,876,543.21" , translatedValue)

		//translation of dates - date is 2009, Jan 1
		Date date = new Date(109 , 0 , 1)
		translatedValue = i18nBundle.getDate(date)
		assertEquals("Jan 1, 2009" , translatedValue)

		//now test functionality of adding another resource bundle
		i18nBundle.addResourceBundle("AnotherTestMessagesBundle")
		messageFromResourceBundle = i18nBundle.getMessage("i18nManager.anotherTestString",defaultLocaleForTest)
		assertEquals("This is another testString in English", messageFromResourceBundle)

	}

	public void testTranslateWithFrenchLocale()
	{
		Locale defaultLocaleForTest = new Locale("fr" , "FR")
		IResourceBundle i18nBundle = Environment.instance.myResourceBundle()
		i18nBundle.addResourceBundle("TestMessagesBundle",defaultLocaleForTest)
		
		//String messageFromResourceBundle = i18nBundle.translate("i18nManager.testString")
		//assertEquals("c'est une corde d'essai en fran�ais", messageFromResourceBundle)

		//translation of number data types
		Double amount = new Double(21.26);
		String translatedValue = i18nBundle.getNumber(amount,defaultLocaleForTest)
		assertEquals("21,26" , translatedValue)

		//translation of dates - date is 2009, Jan 1
		Date date = new Date(109 , 0, 1)
		translatedValue = i18nBundle.getDate(date,defaultLocaleForTest)
		assertEquals("1 janv. 2009" , translatedValue)
	}

}