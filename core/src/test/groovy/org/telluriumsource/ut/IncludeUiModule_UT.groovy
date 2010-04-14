package org.telluriumsource.ut

public class IncludeUiModule_UT extends GroovyTestCase {

  public void testIncludeGoolgeSearch(){
    IncludeUiModule module = new IncludeUiModule()
    module.defineUi()
    
    String result = module.getXPath("SearchModule.Input")
    assertEquals("//descendant-or-self::td[descendant::input[@title=\"Google Search\"] and descendant::input[@name=\"btnG\" and @value=\"Google Search\" and @type=\"submit\"] and descendant::input[@value=\"I'm Feeling Lucky\" and @type=\"submit\"]]/descendant-or-self::input[@title=\"Google Search\"]", result)

    result = module.getXPath("Google.SearchModule.Input")
    assertEquals("//descendant-or-self::table/descendant-or-self::td[descendant::input[@title=\"Google Search\"] and descendant::input[@name=\"btnG\" and @value=\"Google Search\" and @type=\"submit\"] and descendant::input[@value=\"I'm Feeling Lucky\" and @type=\"submit\"]]/descendant-or-self::input[@title=\"Google Search\"]", result)

    result = module.getXPath("GoogleBooksList.category")
    assertEquals("//descendant-or-self::table[descendant::div[@class=\"sub_cat_title\"] and descendant::div[@class=\"sub_cat_section\"] and @id=\"hp_table\"]/descendant-or-self::div[@class=\"sub_cat_title\"]", result)

    result = module.getXPath("Test.newcategory")
    assertEquals("//descendant-or-self::div/descendant-or-self::div[@class=\"sub_cat_title\"]", result)

    result = module.getXPath("Test.newsubcategory")
    assertEquals("//descendant-or-self::div/descendant-or-self::div[@class=\"sub_cat_section\"]", result)
  }

  public void testDump(){
    IncludeUiModule module = new IncludeUiModule()
    module.defineUi()
    module.dump("Google")
    module.dump("Test")
  }

}