package org.telluriumsource.test;

import org.telluriumsource.module.TelluriumProjectPage;
import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * @author Haroon Rasheed (haroonzone@gmail.com)
 *
 *         Date: Jan 18, 2010
 */
public class TelluriumProjectPageTestNGTestCase extends TelluriumTestNGTestCase {
    private static TelluriumProjectPage app;

    @BeforeClass
    public static void initUi() {
        app = new TelluriumProjectPage();
        app.defineUi();
        connectSeleniumServer();
        useCache(true);
        useEngineLog(true);
        useClosestMatch(true);
    }

    @BeforeMethod
    public void setUpForMethod(){
       connectUrl("http://code.google.com/p/aost/");
    }

    @Test
    public void testTelluriumProjectPage(){
        app.clickDownloads();
        app.clickIssues();
        app.clickSource();
    }

    @Test
    public void testSearchProject(){
        app.clickProjectHome();
        app.searchProject("tellurium");
    }

}
