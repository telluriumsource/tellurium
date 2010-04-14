package org.telluriumsource.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.telluriumsource.module.TelluriumProjectPage;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 18, 2010
 */
public class TelluriumProjectPageJUnitTestCase extends TelluriumJUnitTestCase {
    private static TelluriumProjectPage app;

    @BeforeClass
    public static void initUi() {
        app = new TelluriumProjectPage();
        app.defineUi();
        connectSeleniumServer();
        useCache(true);
        useClosestMatch(true);
    }

    @Before
    public void setUpForTest(){
       connectUrl("http://code.google.com/p/aost/");
    }

    @Test
    public void testTelluriumProjectPage(){
        app.clickDownloads();
        app.clickWiki();
        app.clickIssues();
        app.clickSource();
    }

    @Test
    public void testSearchProject(){
        app.clickProjectHome();
        app.searchProject("tellurium");
    }

    @Test
    public void testSeachWeb(){
        app.searchWeb("tellurium selenium dsl groovy");
    }
}
