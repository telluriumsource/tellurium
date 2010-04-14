package test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

import org.telluriumsource.entity.CachePolicy;

import java.util.List;
import java.util.ArrayList;

import module.GoogleSearchModule;


/**
 * Test cases created based on the GoogleSearchModule UI module
 */
public class GoogleSearchJUnitTestCase extends TelluriumJUnitTestCase {

    private static GoogleSearchModule gsm;
    private static String te_ns = "http://telluriumsource.org/ns";

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

    @Test
    public void testGoogleSearch() {
        gsm.doGoogleSearch("tellurium . ( Groovy ) Test");
    }

    @Test
    public void testGoogleSearchFeelingLucky() {
        gsm.doImFeelingLucky("tellurium automated Testing");
    }

    @Test
    public void testLogo(){
        gsm.diagnose("Logo");
        useClosestMatch(true);
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
        useClosestMatch(false);
//        assertEquals("Google", alt);
//        assertEquals("E.C. Segar's Birthday", alt);
    }

/*
    @Test
    public void testClosestMatch(){
        useClosestMatch(true);
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
        useClosestMatch(false);
    }
    */

    @Test
    public void testIsDisabled(){
        useCssSelector(true);
        boolean result = gsm.isInputDisabled();
        assertFalse(result);
        useCssSelector(false);
        result = gsm.isInputDisabled();
        assertFalse(result);
    }

    @Test
    public void testUseCache(){
        useCache(true);
        boolean result = gsm.getCacheState();
        assertTrue(result);

        useCache(false);
        result = gsm.getCacheState();
        assertFalse(result);
        useCache(true);
    }

    @Test
    public void testRegisterNamespace(){
        registerNamespace("te", te_ns);
        String ns = getNamespace("te");
        assertNotNull(ns);
        assertEquals(te_ns, ns);
        ns = getNamespace("x");
        assertNotNull(ns);
        assertEquals("http://www.w3.org/1999/xhtml", ns);
        ns = getNamespace("mathml");
        assertNotNull(ns);
        assertEquals("http://www.w3.org/1998/Math/MathML", ns);
    }

    @Test
    public void testCachePolicy(){
        useCssSelector(true);
        String policy = getCurrentCachePolicy();
        assertEquals("DiscardOldPolicy", policy);
        useCachePolicy(CachePolicy.DISCARD_LEAST_USED);
        policy = getCurrentCachePolicy();
        assertEquals("DiscardLeastUsedPolicy", policy);
        useCachePolicy(CachePolicy.DISCARD_INVALID);
        policy = getCurrentCachePolicy();
        assertEquals("DiscardInvalidPolicy", policy);
        useCachePolicy(CachePolicy.DISCARD_NEW);
        policy = getCurrentCachePolicy();
        assertEquals("DiscardNewPolicy", policy);
        useCachePolicy(CachePolicy.DISCARD_OLD);
        policy = getCurrentCachePolicy();
        assertEquals("DiscardOldPolicy", policy);
    }

    @Test
    public void testCustomDirectCall(){
        List<String> list  = new ArrayList<String>();
        list.add("//input[@title='Google Search']");
        gsm.customDirectCall("click", list.toArray());
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
