package org.telluriumsource.ut

public class ExtendUiModule_UT extends GroovyTestCase {

  public void testIncludeGoolgeSearch() {
    ExtendUiModule module = new ExtendUiModule()
    module.defineUi()

    String result = module.getXPath("SearchModule.Input")
    assertEquals("//descendant-or-self::td[descendant::input[@title=\"Google Search\"] and descendant::input[@name=\"btnG\" and @value=\"Google Search\" and @type=\"submit\"] and descendant::input[@value=\"I'm Feeling Lucky\" and @type=\"submit\"]]/descendant-or-self::input[@title=\"Google Search\"]", result)

    result = module.getXPath("Google.SearchModule.Input")
    assertEquals("//descendant-or-self::table/descendant-or-self::td[descendant::input[@title=\"Google Search\"] and descendant::input[@name=\"btnG\" and @value=\"Google Search\" and @type=\"submit\"] and descendant::input[@value=\"I'm Feeling Lucky\" and @type=\"submit\"]]/descendant-or-self::input[@title=\"Google Search\"]", result)

    result = module.getXPath("Test.newcategory")
    assertEquals("//descendant-or-self::div/descendant-or-self::div[@class=\"sub_cat_title\"]", result)

    result = module.getXPath("Test.secondcategory")
    assertEquals("//descendant-or-self::div/descendant-or-self::div[@class=\"sub_cat_title\"]", result)

    result = module.getXPath("GoogleBooksList.category")
    assertEquals("//descendant-or-self::table[descendant::div[@class=\"sub_cat_title\"] and descendant::div[@class=\"sub_cat_section\"] and @id=\"hp_table\"]/descendant-or-self::div[@class=\"sub_cat_title\"]", result)

    result = module.getXPath("Test.newsubcategory")
    assertEquals("//descendant-or-self::div/descendant-or-self::div[@class=\"sub_cat_section\"]", result)
  }

}