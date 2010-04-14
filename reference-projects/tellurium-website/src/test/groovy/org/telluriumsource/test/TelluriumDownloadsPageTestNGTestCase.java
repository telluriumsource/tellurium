package org.telluriumsource.test;

import org.telluriumsource.test.java.TelluriumTestNGTestCase;
import org.telluriumsource.util.Helper;
import org.telluriumsource.module.TelluriumDownloadsPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;
import java.util.List;

/**
 * Tests Downloads Page on Tellurium project website
 *
 * @author Quan Bui (Quan.Bui@gmail.com)
 * @author Haroon Rasheed (haroonzone@gmail.com)
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 18, 2010
 */
public class TelluriumDownloadsPageTestNGTestCase extends TelluriumTestNGTestCase{
    private static TelluriumDownloadsPage downloadPage;

    @BeforeClass
    public static void initUi() {
        downloadPage = new TelluriumDownloadsPage();
        downloadPage.defineUi();
        connectSeleniumServer();
        useCache(true);
//        useCssSelector(true);
//        useClosestMatch(true);
    }

    @BeforeMethod
    public void setUpForMethod(){
        connectUrl("http://code.google.com/p/aost/downloads/list");
    }

    @Test
    public void testValidate(){
        downloadPage.validate("downloadResult");    
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
    public void testDefaultDownloadType(){
        // Set download type with other value
        downloadPage.selectDownloadType(" All downloads");

        // Navigate away from download page
        connectUrl("http://code.google.com/p/aost/downloads/list");
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
        List<String> list = downloadPage.getDownloadFileNames();
        useTelluriumApi(false);
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
        List<String> list = downloadPage.getDownloadFileNames();
        useTelluriumApi(false);
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    public void testDownloadFileNames(){
        int mcolumn = downloadPage.getTableHeaderNum();
        assertEquals(7, mcolumn);
        List<String> list = downloadPage.getHeaderNames();
        assertNotNull(list);
        assertEquals(7, list.size());
        assertTrue(Helper.include(list, "Filename"));
        list = downloadPage.getDownloadFileNames();
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
}
