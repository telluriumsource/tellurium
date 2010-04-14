package org.telluriumsource.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.TelluriumWikiPage;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.util.Helper;

import java.util.List;
import static org.junit.Assert.*;

/**
 * Tests Wiki Page on Tellurium project website
 *
 * @author Quan Bui (Quan.Bui@gmail.com)
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 18, 2010
 */
public class TelluriumWikiPageJUnitTestCase extends TelluriumJUnitTestCase {
    private static TelluriumWikiPage wikiPage;

    @BeforeClass
    public static void initUi() {
        wikiPage = new TelluriumWikiPage();
        wikiPage.defineUi();
        connectSeleniumServer();
        useTelluriumEngine(true);
//        useEngineLog(true);
    }

    @Before
    public void connect(){
        connectUrl("http://code.google.com/p/aost/w/list");
    }

    @Test
    public void testPageTypes(){
        String[] types = wikiPage.getSearchOptions();
        assertNotNull(types);
        assertTrue(types[1].contains("All wiki pages"));
        assertTrue(types[2].contains("Featured pages"));
        assertTrue(types[3].contains("Current pages"));
        assertTrue(types[4].contains("Deprecated pages"));
    }

    @Test
    public void testSearchPageByText(){
        wikiPage.validate("wikiSearch");
        // Set download type with other value
        wikiPage.selectSearchType(" All wiki pages");
        wikiPage.searchForKeyword("Tutorial");

        List<String> list = wikiPage.getPageNames();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "Tutorial"));
    }

    @Test
    public void testSearchByLabel(){
        // Set download type with other value
        wikiPage.selectSearchType(" All wiki pages");
        wikiPage.searchForKeyword("label:Featured");

        List<String> list = wikiPage.getPageNames();
        assertNotNull(list);
        assertFalse(list.isEmpty());
//        assertTrue(Helper.include(list, "FAQ"));
//        assertTrue(Helper.include(list, "Tutorial"));
//        assertTrue(Helper.include(list, "Introduction"));
    }

    @Test
    public void testClickPageName(){
        wikiPage.clickPageNameColumn(1);
    }

    @Test
    public void testClickSummaryLabels(){
        wikiPage.clickSummaryLabelsColumn(1,1);
    }

    @Test
    public void testClickChanged(){
        wikiPage.clickChangedColumn(1);
    }

    @Test
    public void testClickChangedBy(){
        wikiPage.clickChangedByColumn(1);
    }

    @Test
    public void testClickHeader(){
        wikiPage.clickOnTableHeader(2);
        wikiPage.clickOnTableHeader(3);
        wikiPage.clickOnTableHeader(4);
        wikiPage.clickOnTableHeader(5);
    }
}
