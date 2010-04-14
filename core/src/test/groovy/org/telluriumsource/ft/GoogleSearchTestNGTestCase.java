package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.telluriumsource.module.GoogleSearchModule;
import org.telluriumsource.entity.CachePolicy;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import static org.testng.Assert.*;


/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 8, 2009
 *
 */
public class GoogleSearchTestNGTestCase extends TelluriumTestNGTestCase {
    private static GoogleSearchModule gsm;
    private static String te_ns = "http://telluriumsource.org/ns";

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumEngine(true);
//        useTelluriumApi(true);
        useTrace(true);
//        useCache(true);
    }

    @BeforeMethod
    public void connectToGoogle() {

        connectUrl("http://www.google.com");
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
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
//        assertEquals("Google", alt);
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
    public void testTypeRepeated(){
        gsm.doTypeRepeated("tellurium jQuery");
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

    @AfterClass
    public static void tearDown(){
        showTrace();
    }

}
