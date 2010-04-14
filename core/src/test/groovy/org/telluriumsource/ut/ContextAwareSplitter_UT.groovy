package org.telluriumsource.ut

import org.telluriumsource.ui.locator.ContextAwareSplitter

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 16, 2009
 * 
 */

public class ContextAwareSplitter_UT extends GroovyTestCase {
  
  public void testSplit(){
    ContextAwareSplitter splitter = new ContextAwareSplitter()
    
    String input = null
    String[] list = splitter.split(input)
    assertNull(list)

    input = "table#resultstable > tbody > tr > td"
    list = splitter.split(input)
    assertNotNull(list)
    assertEquals(7, list.length)
    assertEquals("table#resultstable", list[0])
    assertEquals(">", list[1])
    assertEquals("tbody", list[2])
    assertEquals(">", list[3])
    assertEquals("tr", list[4])
    assertEquals(">", list[5])
    assertEquals("td", list[6])

    input = "form[method=get]:has(select#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) select#can"
    list = splitter.split(input)
    assertNotNull(list)
    assertEquals(2, list.length)
    assertEquals("form[method=get]:has(select#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit])", list[0])
    assertEquals("select#can", list[1])
  }

}