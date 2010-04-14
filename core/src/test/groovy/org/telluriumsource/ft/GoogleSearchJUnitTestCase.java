package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.junit.*;
import static org.junit.Assert.*;
import org.telluriumsource.module.GoogleSearchModule;
import org.telluriumsource.entity.CachePolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * Google search module test case to demonstrate the usage of composite locator and CSS selector
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 9, 2009
 *
 */

public class GoogleSearchJUnitTestCase extends TelluriumJUnitTestCase {
    private static GoogleSearchModule gsm;
    private static String te_ns = "http://telluriumsource.org/ns";

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useEngineLog(true);
        useTelluriumEngine(true);
//        useTelluriumApi(true);
        useTrace(true);
//        useCache(true);
    }

    @Before
    public void connectToGoogle() {

        connectUrl("http://www.google.com/intl/en/");
    }

    @Test
    public void testJsonfyUiModule(){
        String json = gsm.toString("Google");
        System.out.println(json);
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
    public void testIsElementPresent(){
        assertTrue(gsm.isElementPresent("Google.Input"));
//        assertFalse(gsm.isElementPresent("Logo"));
    }

    @Test
    public void testLogo(){
        gsm.validate("Logo");
        gsm.diagnose("Logo");
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
//        assertEquals("Google", alt);
//        assertEquals("E.C. Segar's Birthday", alt);
    }

    @Test
    public void testClosestMatch(){
        useClosestMatch(true);
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);        
        useClosestMatch(false);
    }

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
    }

    @Test
    public void testKeyType(){
        //Test case for Issue 290: key type event is not working in IE
        gsm.doGoogleSearch("admin@telluriumsource.org");
    }
    
    @Test
    public void testTypeRepeated(){
        gsm.doTypeRepeated("tellurium jQuery");
    }

    @Test
    public void testDifferentTypeInput(){
        gsm.doType(null);
        gsm.doType("telluriumsource.org");
        gsm.doType(2515);
        gsm.doType(12.15);
        gsm.doType(true);
        gsm.doType(gsm);
    }

    @Test
    public void testDifferentKeyTypeInput(){
        gsm.doKeyType(null);
        gsm.doKeyType("telluriumsource.org");
        gsm.doKeyType(2515);
        gsm.doKeyType(12.15);
        gsm.doKeyType(true);
        gsm.doKeyType(gsm);
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
        useCache(true);
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

//    @Ignore
    @Test
    public void testCookies(){
        useTelluriumApi(false);
        gsm.setCookieByJQuery("tellurium", "cool");
        String cookie = gsm.getCookieByJQuery("tellurium");
        assertEquals("cool", cookie);
        gsm.deleteCookieByJQuery("tellurium");
        cookie = gsm.getCookieByJQuery("tellurium");
        assertNull(cookie);
        gsm.setCookieByJQuery("aost", "cool");
        gsm.setCookieByJQuery("tellurium", "great");
        gsm.deleteAllCookiesByJQuery();
        cookie = gsm.getCookieByJQuery("aost");
        assertNull(cookie);
        cookie = gsm.getCookieByJQuery("tellurium");
        assertNull(cookie);
    }
    
    @AfterClass
    public static void tearDown(){
        showTrace();
     }
}
