package org.telluriumsource.test;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.TelluriumDownloadsPage;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.util.Helper;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests Downloads Page on Tellurium project website
 * 
 * @author Quan Bui (Quan.Bui@gmail.com)
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 18, 2010
 */
public class TelluriumDownloadsPageJUnitTestCase extends TelluriumJUnitTestCase {
    private static TelluriumDownloadsPage downloadPage;

    @BeforeClass
    public static void initUi() {
        downloadPage = new TelluriumDownloadsPage();
        downloadPage.defineUi();
        connectSeleniumServer();
        useCache(true);
        useCssSelector(true);
//        useClosestMatch(true);
    }

    @Before
    public void connect(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
    }

    @Test
    public void testDownloadTypes(){
        String[] allTypes = downloadPage.getAllDownloadTypes();
        assertNotNull(allTypes);
        assertTrue(allTypes[1].contains("All downloads"));
        assertTrue(allTypes[2].contains("Featured downloads"));
        assertTrue(allTypes[3].contains("Current downloads"));
        assertTrue(allTypes[4].contains("Deprecated downloads"));
    }

    @Test
    public void testSelectAllDownloads(){
        // Set download type with other value
        downloadPage.selectDownloadType(" All downloads");
    }

    @Test
    public void testDefaultDownloadType(){
        String defaultType = downloadPage.getCurrentDownloadType();
        assertNotNull(defaultType);
        assertTrue(defaultType.contains("Current downloads"));
    }

    @Test
    public void testSearchByText(){
        // Set download type with other value
        downloadPage.selectDownloadType(" All downloads");
        downloadPage.searchDownload("Tellurium-0.6.0");

        useTelluriumApi(true);
        useEngineLog(true);
        List<String> list = downloadPage.getDownloadFileNames();
        useTelluriumApi(false);
        useEngineLog(false);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "tellurium-core.0.6.0.tar.gz"));
    }

    @Test
    public void testSearchByLabel(){
        // Set download type with other value
        downloadPage.selectDownloadType(" All downloads");
        downloadPage.searchDownload("label:Featured");

        useTelluriumApi(true);
        useEngineLog(true);
        List<String> list = downloadPage.getDownloadFileNames();
        useTelluriumApi(false);
        useEngineLog(false);
        assertNotNull(list);
        assertFalse(list.isEmpty());
//        assertTrue(Helper.include(list, "tellurium-core.0.6.0.tar.gz"));
    }

    @Test
    public void testDownloadFileNames(){
        int mcolumn = downloadPage.getTableHeaderNum();
        assertEquals(7, mcolumn);
        List<String> list = downloadPage.getHeaderNames();
        assertNotNull(list);
        assertEquals(7, list.size());
        assertTrue(Helper.include(list, "Filename"));
        useTelluriumApi(true);
        list = downloadPage.getDownloadFileNames();
        useTelluriumApi(false);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(Helper.include(list, "tellurium-core.0.6.0.tar.gz"));
    }

    @Test
    public void testClickDownload(){
        downloadPage.clickFileNameColumn(1);
    }

    @Test
    public void testClickSummaryLabels(){
        downloadPage.clickSummaryLabelsColumn(1,1);
    }

    @Test
    public void testClickUploaded(){
        downloadPage.clickUploadedColumn(1);
    }

    @Test
    public void testClickSize(){
        downloadPage.clickSizeColumn(1);
    }

    @Test
    public void testClickDownloadedCount(){
        downloadPage.clickDownloadedCountColumn(1);
    }

    @Test
    public void testClickHeader(){
        downloadPage.clickOnTableHeader(2);
        downloadPage.clickOnTableHeader(3);
        downloadPage.clickOnTableHeader(4);
        downloadPage.clickOnTableHeader(5);
        downloadPage.clickOnTableHeader(6);
    }

    @AfterClass
    public static void showUsage(){
        int size = getCacheSize();
        int maxSize = getCacheMaxSize();
        System.out.println("Cache Size: " + size + ", Cache Max Size: " + maxSize);
        System.out.println("Cache Usage: " + getCacheUsage());
    }
}
