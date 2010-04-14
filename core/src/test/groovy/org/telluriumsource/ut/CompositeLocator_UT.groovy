package org.telluriumsource.ut

import org.telluriumsource.ui.locator.CompositeLocator

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 16, 2009
 * 
 */

public class CompositeLocator_UT extends GroovyTestCase {

  public void testIsIdIncluded(){
    CompositeLocator loc = new CompositeLocator()
    boolean result = loc.isIdIncluded()
    assertFalse(result)

    loc.attributes.put("id", "^good")
    result = loc.isIdIncluded()
    assertFalse(result)

    loc.attributes.put("id", "\$good")
    result = loc.isIdIncluded()
    assertFalse(result)

    loc.attributes.put("id", "*good")
    result = loc.isIdIncluded()
    assertFalse(result)

    loc.attributes.put("id", "!good")
    result = loc.isIdIncluded()
    assertFalse(result)

    loc.attributes.put("id", "good")
    result = loc.isIdIncluded()
    assertTrue(result)        
  }

}