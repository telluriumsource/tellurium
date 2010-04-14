package org.telluriumsource.ft;

import org.junit.*;
import org.telluriumsource.entity.CachePolicy;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.module.TelluriumIssueModule;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 6, 2010
 */
public class TelluriumIssueTestCase extends TelluriumJUnitTestCase {
    private static TelluriumIssueModule tim;
    @BeforeClass
    public static void initUi() {
        tim = new TelluriumIssueModule();
        tim.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumEngine(true);
//        setCacheMaxSize(30);
//        useTrace(true);
//        useLocatorWithCache(false);
//        useClosestMatch(true);
        useEngineLog(true);
    }

    @Before
    public void setUp(){
        connectUrl("http://code.google.com/p/aost/issues/list");
    }

    @Test
    public void testGetHTMLSource(){
        tim.validate("issueSearch");
        tim.getHTMLSource("issueSearch");
    }

    @Ignore
    @Test
    public void testShowUI(){
        int colnum = tim.getTableMaxColumnNum("issueResult");
        int rownum = tim.getTableMaxRowNum("issueResult");
        System.out.println("Row num: " + rownum + ", Column num: " + colnum);
        tim.startShow("issueResult");
        tim.endShow("issueResult");
    }

    @Test
    public void testValidateUiModule(){
        tim.validate("issueSearch");
    }

    @Test
    public void testSearchIssueTypes(){
        String[] ists = tim.getIsssueTypes();
        tim.selectIssueType(ists[2]);
        tim.searchIssue("Alter");
    }

    @Test
    public void testGetAttribute(){
        String clazz = (String)tim.getAttribute("issueSearch.searchBox","style");
        System.out.println("Style for issueSearch.searchBox is" + clazz);
    }

    @Test
    public void testAllIssues(){
        String[] details = (String[])tim.getAllText();
        assertNotNull(details);
        for(String content: details){
            System.out.println(content);
        }
    }

    @Test
    public void testGetCellCount(){
        int count = tim.getTableCellCount();
        assertTrue(count > 0);
        System.out.println("Cell size: " + count);
        String[] details = tim.getAllText();
        assertNotNull(details);
        assertEquals(details.length, count);
    }

    @Test
    public void testCSS(){
        useCache(false);
        String[] css = tim.getTableCSS("font-size");
        assertNotNull(css);
    }
    
    @Test
    public void checkCacheable(){
        boolean result = tim.checkamICacheable("issueResult[1][ID]");
        assertTrue(result);
        result = tim.checkamICacheable("issueResult");
        assertTrue(result);
    }

    @Test
    public void testDumpRow(){
        int num = tim.getRowNum();
        if(num > 10)
            num = 10;
        for(int i=1; i<=num; i++)
            tim.dumpDataForRow(i);
    }

   @Test
    public void testDumpRowNoCache(){
        useCache(false);
        useTelluriumApi(true);
        int num = tim.getRowNum();
        if(num > 10)
            num = 10;
        for(int i=1; i<=num; i++)
            tim.dumpDataForRow(i);
        useTelluriumEngine(true);
    }

    @Test
    public void testGetDataForColumn(){
        long beforeTime = System.currentTimeMillis();
        tim.getDataForColumn(3);
        long endTime = System.currentTimeMillis();
        System.out.println("Time noCacheForChildren " + (endTime-beforeTime) + "ms");
        beforeTime = System.currentTimeMillis();
        tim.getDataForColumnWithCache(3);
        endTime = System.currentTimeMillis();
        System.out.println("Time with all cache " + (endTime-beforeTime) + "ms");
    }

    @Test
    public void testCachePolicy(){
        setCacheMaxSize(10);
        useCachePolicy(CachePolicy.DISCARD_LEAST_USED);
        tim.validate("issueSearch");
        tim.searchIssue("Alter");
        tim.getTableCSS("font-size");
        tim.getIsssueTypes();
        tim.searchIssue("Alter");
        setCacheMaxSize(30);
    }

    public static void showCacheUsage(){
        int size = tim.getCacheSize();
        int maxSize = tim.getCacheMaxSize();
        System.out.println("Cache Size: " + size + ", Cache Max Size: " + maxSize);

        System.out.println("Cache Usage: " + tim.getCacheUsage());        
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
        showCacheUsage();
    }
}
