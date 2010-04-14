package org.telluriumsource.ut

import org.telluriumsource.trump.XPathMatcher

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 10, 2008
 * 
 */
class XPathMatcher_UT  extends GroovyTestCase{

    void testMatch(){
        String xpath1 = "/html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']/tbody/tr[@id='headingrow']/th[@id='summaryheading']/a"
        String xpath2 = "/html/body/div[@id='maincol']/div[@id='colcontrol']"

        String common = XPathMatcher.match(xpath1, xpath2)
        assertEquals("/html/body/div[@id='maincol']/div[@id='colcontrol']", common)

        xpath2 = "/html/body/div[@id='maincol']/div[@id='colcontrol']/a/div"
        common = XPathMatcher.match(xpath1, xpath2);
        assertEquals("/html/body/div[@id='maincol']/div[@id='colcontrol']", common)

        xpath2 = "/html/body/div[@id='maincol']/div[@id='colcontrol2']";
        common = XPathMatcher.match(xpath1, xpath2);
        assertEquals("/html/body/div[@id='maincol']", common);
    }

    void testRemainingXPath(){
        String xpath1 = "/html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']/tbody/tr[@id='headingrow']/th[@id='summaryheading']/a"
        String xpath2 = "/html/body/div[@id='maincol']/div[@id='colcontrol']"

        String remain = XPathMatcher.remainingXPath(xpath1, xpath2);
        assertEquals("/div/div[@id='bub']/table[@id='resultstable']/tbody/tr[@id='headingrow']/th[@id='summaryheading']/a", remain);

        remain =  XPathMatcher.remainingXPath(null, null);
        assertNull(remain);

        String xpath3 =  "/html/body/div[@id='maincol']/div[@id='colcontrol2']"

        try{
            remain = XPathMatcher.remainingXPath(xpath1, xpath3);
            fail("Should throw runtime exception here")
        }catch(Exception e){

        }
    }

}