package org.telluriumsource.ft

import org.telluriumsource.test.groovy.TelluriumGroovyTestCase
import org.telluriumsource.module.GoogleSearchModule
import org.telluriumsource.entity.CachePolicy
import org.telluriumsource.util.LogLevels

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 8, 2009
 * 
 */

public class GoogleSearchGroovyTestCase extends TelluriumGroovyTestCase {

  private GoogleSearchModule gsm;
    private String te_ns = "http://telluriumsource.org/ns";

    public void setUp() {
        setUpForClass();
        gsm = new GoogleSearchModule();
        gsm.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        //enableLogging(LogLevels.ALL);

        useTelluriumApi(true);

        useTrace(true);

//        useCache(true);
    }

    public void testGoogleSearch() {
        connectUrl("http://www.google.com");
        gsm.doGoogleSearch("tellurium . ( Groovy ) Test");
    }

    public void testGoogleSearchFeelingLucky() {
        connectUrl("http://www.google.com");
        gsm.doImFeelingLucky("tellurium automated Testing");
    }

    public void testLogo(){
        connectUrl("http://www.google.com");
        String alt = gsm.getLogoAlt();
        assertNotNull(alt);
//        assertEquals("Google", alt);
    }

    public void testIsDisabled(){
        connectUrl("http://www.google.com");
        useCssSelector(true);
        boolean result = gsm.isInputDisabled();
        assertFalse(result);
        useCssSelector(false);
        result = gsm.isInputDisabled();
        assertFalse(result);
    }

    public void testUseCache(){
        connectUrl("http://www.google.com");
        useCache(true);
        boolean result = gsm.getCacheState();
        assertTrue(result);

        useCache(false);
        result = gsm.getCacheState();
        assertFalse(result);
    }

    public void testTypeRepeated(){
        connectUrl("http://www.google.com");
        gsm.doTypeRepeated("tellurium jQuery");
    }

    public void testRegisterNamespace(){
        connectUrl("http://www.google.com");
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

    public void testCachePolicy(){
        connectUrl("http://www.google.com");
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

    public void tearDown(){
        showTrace();
        tearDownForClass();
    }
}