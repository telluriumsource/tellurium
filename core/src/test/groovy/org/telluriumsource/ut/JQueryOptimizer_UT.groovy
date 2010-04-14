package org.telluriumsource.ut

import org.telluriumsource.ui.locator.JQueryOptimizer

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 15, 2009
 * 
 */

public class JQueryOptimizer_UT extends GroovyTestCase{
  protected JQueryOptimizer jqp = new JQueryOptimizer()

  public void testRemovePrefix(){
    String jqs = "jquery=table#resultstable > tbody > tr > td"
    String result = jqp.removePrefix(jqs)
    assertEquals("table#resultstable > tbody > tr > td", result)
    jqs = " table#resultstable > tbody > tr > td"
    result = jqp.removePrefix(jqs)
    assertEquals("table#resultstable > tbody > tr > td", result)
  }

  public void testContainIdSelector(){
    String jqs = "select#scan"
    boolean result = jqp.containIdSelector(jqs)
    assertTrue(result)

    jqs="select:has(div#scan)"
    result = jqp.containIdSelector(jqs)
    assertFalse(result)

    jqs="select>div#scan"
    result = jqp.containIdSelector(jqs)
    assertFalse(result)

    jqs="select,div#scan"
    result = jqp.containIdSelector(jqs)
    assertFalse(result)

    jqs="#scan"
    result = jqp.containIdSelector(jqs)
    assertTrue(result)
  }

  public void testPickIdSelector(){
    String jqs = "form[method=get]:has(select#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) select#can"
    String result = jqp.pickIdSelector(jqs)
    assertNotNull(result)
    assertEquals("select#can", result)
  }

  public void testOptimize(){
    String jqs = "jquery=form[method=get]:has(select#can, span:contains(for), input[type=text][name=q], input[value=Search][type=submit]) select#can"
    String result = jqp.optimize(jqs)
    assertNotNull(result)
    assertEquals("select#can", result)
    jqs = "jquery=table#resultstable > tbody > tr > td"
    result = jqp.optimize(jqs)
    assertNotNull(result)
    assertEquals("table#resultstable > tbody > tr > td", result)
  }
}