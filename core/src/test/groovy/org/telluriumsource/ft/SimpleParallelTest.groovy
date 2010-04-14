package org.telluriumsource.ft

import org.telluriumsource.test.groovy.TelluriumGroovyTestNGTestCase
import org.junit.Test
import static org.junit.Assert.*;

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Mar 19, 2010
 * 
 */
class SimpleParallelTest extends TelluriumGroovyTestNGTestCase {

  public void initUi() { }

/*  public void setUp(){
    setUpForClass();  
  }*/

  @Test
  public void testOne() {
    setUpForClass();
//    connectSeleniumServer();
    1.upto(10) {
      openUrl("http://google.com");
      assertEquals("location should be google", "http://www.google.com/", connector.sel.getProperty("location"));
      sleep(500)
    }
  }

  @Test
  public void testTwo() {
    setUpForClass();
//    connectSeleniumServer();
    1.upto(10) {
      openUrl("http://amazon.com");
      assertEquals("location should be amazon", "http://www.amazon.com/", connector.sel.getProperty("location"));
      sleep(500)
    }
  }
}
