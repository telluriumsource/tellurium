package org.telluriumsource.ft;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.GoogleSearchModule;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 15, 2010
 */
public class UiModuleDemoTestCase extends TelluriumJUnitTestCase {
    private static GoogleSearchModule gsm;

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        connectSeleniumServer();
        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToGoogle() {

        connectUrl("http://www.google.com/intl/en/");
    }

    @Test
    public void testDump(){
        useCssSelector(false);
        gsm.dump("Google");
        useCssSelector(true);
        gsm.dump("Google");
    }

    @Test
    public void testToString(){
        String json = gsm.toString("Google");
        System.out.println(json);
    }

    @Test
    public void testToHTML(){
        String html = gsm.toHTML("Google");
        System.out.println(html);
    }

    @Test
    public void testGetHTMLSource(){
        gsm.getHTMLSource("Google");
    }

    @Test
    public void testShow(){
        gsm.show("Google", 10000);
//        gsm.startShow("Form");
//        gsm.endShow("Form");
    }

    @Test
    public void testValidate(){
        gsm.validate("Google");
        gsm.validate("ProblematicGoogle");
    }

    @Test
    public void testClosestMatch(){
        useClosestMatch(true);
        gsm.doProblematicGoogleSearch("Tellurium Source");
        useClosestMatch(false);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
