package org.telluriumsource.ut

import org.telluriumsource.ui.locator.XPathProcessor

/**
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *    Date: Feb 27, 2009
 *
 */

public class XPathProcesssor_UT extends GroovyTestCase{

  public void testSplitXPath() {
    String xpath = "//table/tbody/tr[child::td][2]/td[2]"
    String[] result = XPathProcessor.splitXPath(xpath)
    assertNotNull(result)
    assertEquals(4, result.length)
    assertEquals("td[2]", result[3])
    xpath = "/table/tbody/tr[child::td][2]/td[2]"
    result = XPathProcessor.splitXPath(xpath)
    assertNotNull(result)
    assertEquals(4, result.length)
    assertEquals("td[2]", result[3])
    xpath = "table/tbody/tr[child::td][2]/td[2]"
    result = XPathProcessor.splitXPath(xpath)
    assertNotNull(result)
    assertEquals(4, result.length)
    assertEquals("td[2]", result[3])
  }

  public void testPopXPath() {
    String xpath = "//table/tbody/tr[child::td][2]/td[2]"
    String result = XPathProcessor.popXPath(xpath)
    assertNotNull(result)
    assertEquals("//table/tbody/tr[child::td][2]", result)
    xpath = "/table/tbody/tr[child::td][2]/td[2]"
    result = XPathProcessor.popXPath(xpath)
    assertNotNull(result)
    assertEquals("/table/tbody/tr[child::td][2]", result)
  }

  public void testLastXPath() {
    String xpath = "//table/tbody/tr[child::td][2]/td[2]"
    String result = XPathProcessor.lastXPath(xpath)
    assertNotNull(result)
    assertEquals("td[2]", result)
    xpath = null
    result = XPathProcessor.lastXPath(xpath)
    assertNull(result)
  }

  public void testRemovePrefix(){
    String xpath = "child::td[2]"
    String result = XPathProcessor.removePrefix(xpath)
    assertNotNull(result)
    assertEquals("td[2]", result)
    xpath="td[2]"
    result = XPathProcessor.removePrefix(xpath)
    assertNotNull(result)
    assertEquals("td[2]", result)
    xpath = null
    result = XPathProcessor.removePrefix(xpath)
    assertNull(result)
  }

  public void testCheckPosition(){
    String xpath = "child::td[2]"
    int result = XPathProcessor.checkPosition(xpath)
    assertTrue(2 == result)
    xpath = "td"
    result = XPathProcessor.checkPosition(xpath)
    assertTrue(-1 == result)
    xpath = "td[id='test' and position()=2]"
    result = XPathProcessor.checkPosition(xpath)
    assertTrue(2 == result)
    xpath = "td[position()=2]"
    result = XPathProcessor.checkPosition(xpath)
    assertTrue(2 == result)   
  }

  public void testAddPositionAttribute(){
    String xpath = "child::td[@id='good']"
    String result = XPathProcessor.addPositionAttribute(xpath, 2)
    assertNotNull(result)
    assertEquals("child::td[@id='good' and position()=2]", result)
    xpath = "td"
    result = XPathProcessor.addPositionAttribute(xpath, 2)
    assertNotNull(result)
    assertEquals("td[2]", result)
    xpath = "th[@class='mt']"
    result = XPathProcessor.addPositionAttribute(xpath, 2)
    assertNotNull(result)
    assertEquals("th[@class='mt' and position()=2]", result)    
  }

  public void testGetTagFromXPath(){
    String xpath =  "child::td[@id='good']"
    String result = XPathProcessor.getTagFromXPath(xpath)
    assertNotNull(result)
    assertEquals("td", result)
    xpath = "td"
    result = XPathProcessor.getTagFromXPath(xpath)
    assertNotNull(result)
    assertEquals("td", result)
    xpath = "td[@id='good']"
    result = XPathProcessor.getTagFromXPath(xpath)
    assertNotNull(result)
    assertEquals("td", result)   
  }
}