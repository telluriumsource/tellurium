package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;
import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Mar 20, 2010
 */
public class SampleTestNGParallelTest extends TelluriumTestNGTestCase {

    @BeforeClass
    public static void initUi() {
        connectSeleniumServer();
        useTrace(true);
    }

    @Test(threadPoolSize = 5, invocationCount = 10)
    public void testOne() {

      connectUrl("http://google.com");
      assertEquals("location should be google", "http://www.google.com/", connector.getSelenium().getLocation());
        try {
            sleep(500);
        } catch (InterruptedException e) {
            
        }
    }

    @Test(threadPoolSize = 5, invocationCount = 10)
    public void testTwo() {
      connectUrl("http://amazon.com");
      assertEquals("location should be amazon", "http://www.amazon.com/", connector.getSelenium().getLocation());
        try {
            sleep(500);
        } catch (InterruptedException e) {

        }
    }

    @AfterClass
    public static void tearDown() {
        showTrace();
    }
}
