package org.telluriumsource.ut

import org.telluriumsource.exception.InvalidObjectTypeException

/**
 *
 * @author Jian Fang(John.Jian.Fang@gmail.com)
 *
 * Date: Jun 16, 2009
 *
 */

public class InvalidUiModule_UT extends GroovyTestCase{

  public void testException(){
    InvalidUIModule ium = new InvalidUIModule();
    try{
      ium.defineUi();
      fail("Expect invalidObjectTypeException!");
    }catch(InvalidObjectTypeException e){
      assertTrue(true);
    }
  }

}