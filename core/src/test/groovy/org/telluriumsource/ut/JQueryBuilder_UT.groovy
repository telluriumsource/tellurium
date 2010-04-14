package org.telluriumsource.ut

import org.telluriumsource.ui.locator.JQueryBuilder

public class JQueryBuilder_UT extends GroovyTestCase {

  public void testCheckTag(){
    String tag = null
    String result = JQueryBuilder.checkTag(tag)
    assertEquals("*", result)

    tag = " "
    result = JQueryBuilder.checkTag(tag)
    assertEquals("*", result)

    tag = "div"
    result = JQueryBuilder.checkTag(tag)
    assertEquals("div", result)
  }

  public void testAttrId(){
    String id = null
    String result = JQueryBuilder.attrId(id)
    assertEquals("[id]", result)

    id = "*hp_table"
    result = JQueryBuilder.attrId(id)
    assertEquals("[id*=hp_table]", result)
    
    id = "^hp_table"
    result = JQueryBuilder.attrId(id)
    assertEquals("[id^=hp_table]", result)

    id = "\$hp_table"
    result = JQueryBuilder.attrId(id)
    assertEquals("[id\$=hp_table]", result)

    id = "!hp_table"
    result = JQueryBuilder.attrId(id)
    assertEquals("[id!=hp_table]", result)
  }

  public void testAttrClass(){
    String clazz = ""

    String result = JQueryBuilder.attrClass(clazz)
    assertEquals("[class]", result)

    clazz = "test"
    result =  JQueryBuilder.attrClass(clazz)
    assertEquals(".test", result)

    clazz = "test demo"
    result =  JQueryBuilder.attrClass(clazz)
    assertEquals(".test.demo", result)

    clazz = "^good"
    result =  JQueryBuilder.attrClass(clazz)
    assertEquals("[class^=good]", result)

    clazz = "*good"
    result =  JQueryBuilder.attrClass(clazz)
    assertEquals("[class*=good]", result)

    clazz = "!good"
    result =  JQueryBuilder.attrClass(clazz)
//    assertEquals("[class!=good]", result)
    assertEquals(":not(.good)", result)

    clazz = "\$good"
    result =  JQueryBuilder.attrClass(clazz)
    assertEquals("[class\$=good]", result)

    clazz = "demo \$good"
    result =  JQueryBuilder.attrClass(clazz)
    assertEquals(".demo[class\$=good]", result)

    clazz = "demo \$good *bad"
    result =  JQueryBuilder.attrClass(clazz)
    assertEquals(".demo[class\$=good][class*=bad]", result)  
  }

  public void testAttrText(){
    String text = "%%good"
    String result = JQueryBuilder.containText(text.substring(2))
    assertEquals(":contains(good)", result)

    text = "*good"
    result = JQueryBuilder.containText(text.substring(1))
    assertEquals(":contains(good)", result)

    text = "good"
    result = JQueryBuilder.attrText(text)
    assertEquals(":te_text(good)", result)

    text = "I'm Feeling Lucky"
    result = JQueryBuilder.attrText(text)
    assertEquals(":contains(m Feeling Lucky)", result)
  }

  public void testAttrPairs(){
    String key="method"
    String val="list"

    String result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[method=list]", result)
    assertFalse(JQueryBuilder.inBlackList(key))

    key = "action"
    assertTrue(JQueryBuilder.inBlackList(key))

    key = "mclazz"
    val = " good' sugges'tion "
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz*=sugges]", result)

    key = "mclazz"
    val = "^good"
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz^=good]", result)

    key = "mclazz"
    val = "\$good"
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz\$=good]", result)

    key = "mclazz"
    val = "*good"
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz*=good]", result)

    key = "mclazz"
    val = null
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz]", result)

    key = "mclazz"
    val = " "
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz]", result)

    key = "mclazz"
    val = "!bad"
    result = JQueryBuilder.attrPairs(key, val)
    assertEquals("[mclazz!=bad]", result)    
  }

  public void testConvHeader(){
    String header = null
    String result = JQueryBuilder.convHeader(header)
    assertEquals("", result)

    header = "/div"
    result = JQueryBuilder.convHeader(header)
    assertEquals(" div", result)

    header = "//div"
    result = JQueryBuilder.convHeader(header)
    assertEquals(" div", result)

    header = "/div[3]"
    result = JQueryBuilder.convHeader(header)
    assertEquals(" div", result)

    header = "/div[@id]"
    result = JQueryBuilder.convHeader(header)
    assertEquals(" div", result)

    header = "/div[@id]/p[1]/span[@class='test']"
    result = JQueryBuilder.convHeader(header)
    assertEquals(" div > p > span", result)
    
  }

  public void testModuleWithHeader(){
    ModuleWithHeader mwh = new ModuleWithHeader()
    mwh.defineUi()
    mwh.enableCssSelector()
    String jqsel = mwh.getLocator("SearchModule.Input")
    assertEquals("jquery=td div input[title=Google Search]", jqsel)
    jqsel = mwh.getLocator("SearchModule.Search")
    assertEquals("jquery=td div > p input[name=btnG][value=Google Search][type=submit]", jqsel)
    jqsel = mwh.getLocator("SearchModule.ImFeelingLucky")
    assertEquals("jquery=td input[value\$=m Feeling Lucky][type=submit]", jqsel)
  }
}