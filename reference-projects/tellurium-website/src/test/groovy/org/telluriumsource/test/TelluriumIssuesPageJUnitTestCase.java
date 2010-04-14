package org.telluriumsource.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.TelluriumIssuesPage;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.util.Helper;

import java.util.List;

import static org.junit.Assert.*;
import org.json.simple.JSONArray;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 18, 2010
 */
public class TelluriumIssuesPageJUnitTestCase extends TelluriumJUnitTestCase {
    private static TelluriumIssuesPage tisp;

    @BeforeClass
    public static void initUi() {
        tisp = new TelluriumIssuesPage();
        tisp.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useCache(true);
    }

    @Test
    public void testDiagnoseAdvancedSearch(){
        connectUrl("http://code.google.com/p/aost/issues/advsearch");
        tisp.diagnose("issueAdvancedSearch");
    }

    @Test
    public void testValidateAdvancedSearch(){
        connectUrl("http://code.google.com/p/aost/issues/advsearch");
        tisp.validate("issueAdvancedSearch");
    }
    
    @Test
    public void testDiagnoseIssueSearch(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.diagnose("issueSearch.searchButton");
    }

    @Test
    public void testValidateIssueSearch(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.validate("issueSearch");
    }
    
    @Test
    public void testToString(){
        JSONArray ar = tisp.toJSONArray("selectColumnMenu");
        System.out.println(ar.toString());    
    }

    @Test
    public void testGetIssueTypes(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        String[] ists = tisp.getIsssueTypes();
        assertNotNull(ists);
        assertTrue(ists[2].contains("Open issues"));
        tisp.selectIssueType(ists[2]);
        tisp.searchIssue("Alter");
    }

    @Test
    public void testAdvancedSearch(){
        connectUrl("http://code.google.com/p/aost/issues/advsearch");
        String[] ists = tisp.getAdvancedIssueTypes();
        assertNotNull(ists);
        assertTrue(ists[1].contains("All issues"));
        tisp.selectIssueType(ists[1]);

        tisp.advancedSearchIssue(ists[1], "table", null, null, null, null, null, null, null);
    }

    @Test
    public void testAdvancedSearchTips(){
        connectUrl("http://code.google.com/p/aost/issues/advsearch");
        tisp.clickMoreSearchTips();
    }

    @Test
    public void testIssueData(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        int mcolumn = tisp.getTableHeaderNum();
        assertEquals(10, mcolumn);
        List<String> list = tisp.getHeaderNames();
        assertNotNull(list);
        assertEquals(10, list.size());
        assertTrue(Helper.include(list, "Status"));
        list = tisp.getDataForColumn(7);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "John.Jian.Fang"));
    }

    @Test
    public void testClickIssueResult(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,2);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,3);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,4);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,5);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,6);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,7);
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickTable(1,9);
    }

    @Test
    public void testClickHeader(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        tisp.clickOnTableHeader(2);
        tisp.clickOnTableHeader(3);
        tisp.clickOnTableHeader(4);
        tisp.clickOnTableHeader(5);
        tisp.clickOnTableHeader(6);
        tisp.clickOnTableHeader(7);
        tisp.clickOnTableHeader(9);
    }

    @Test
    public void testIdMenu(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        useCache(false);
        useCssSelector(false);
        tisp.clickOnTableHeader(2);
        tisp.mouseMoveIdMenu();
        tisp.clickIdMenuSortDown();
        tisp.clickIdMenuSortUp();
        useCache(true);
        useCssSelector(true);
    }

    @Test
    public void testSelectColumnMenu(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        useCache(false);
        useCssSelector(false);
        tisp.toggleIdColumn("ID");
        tisp.toggleIdColumn("Owner");
//        tisp.toggleIdColumn("Closed");
        useCache(true);
        useCssSelector(false);
    }

    @Test
    public void testSelectDataLayout(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        useCache(false);
        useCssSelector(false);
        tisp.selectDataLayout("Grid");
        tisp.selectDataLayout("List");
        useCache(true);
        useCssSelector(false);
    }

    @Test
    public void testGetCellCount(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        useCssSelector(true);
        useCache(true);
        useTelluriumApi(true);
        int count = tisp.getTableCellCount();
        assertTrue(count > 0);
        System.out.println("Cell size: " + count);
        String[] details = tisp.getAllText();
        useTelluriumApi(false);
        assertNotNull(details);
        assertEquals(details.length, count);
    }

    @Test
    public void testSearchIssueTypes(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        useCssSelector(true);
        useCache(true);
        setCacheMaxSize(10);
        String[] ists = tisp.getIsssueTypes();
        tisp.selectIssueType(ists[2]);
        tisp.searchIssue("Alter");
        showCacheUsage();
    }

    @Test
    public void testDump(){
        useCssSelector(false);
        tisp.dump("issueAdvancedSearch");

        useCssSelector(true);
        useCache(false);
        tisp.dump("issueAdvancedSearch");

        useCssSelector(true);
        useCache(true);
        tisp.dump("issueAdvancedSearch");
    }

    @AfterClass
    public static void showCacheUsage(){
        int size = getCacheSize();
        int maxSize = getCacheMaxSize();
        System.out.println("Cache Size: " + size + ", Cache Max Size: " + maxSize);
        System.out.println("Cache Usage: " + getCacheUsage());
    }

}
