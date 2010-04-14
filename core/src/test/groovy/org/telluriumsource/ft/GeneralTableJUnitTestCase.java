package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;
import org.telluriumsource.module.GeneralTableModule;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;

import static org.junit.Assert.*;

/**
 * Test StandardTable with more general layout
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 22, 2010
 */
public class GeneralTableJUnitTestCase extends TelluriumMockJUnitTestCase {
    private static GeneralTableModule gtm;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("GeneralTable");

        gtm = new GeneralTableModule();
        gtm.defineUi();
        useEngineLog(true);
        //enableLogging(LogLevels.ALL);

        useCssSelector(true);
        useTelluriumApi(true);
        useTrace(true);
        useCache(true);
    }

    @Before
    public void connectToLocal() {
        connect("GeneralTable");
    }

    @Test
    public void testToJSONString(){

        System.out.println(gtm.toString("GT"));
    }

    @Test
    public void testValidateUiModule(){
        gtm.validate("GT");
    }

    @Test
    public void testIsElementPresent(){
        useCache(true);
        assertTrue(gtm.isElementPresent("GT.header[2]"));
        assertFalse(gtm.isElementPresent("GT[12][12]"));
    }

    @Test
    public void testWork(){
        useCache(false);
        System.out.println(gtm.getText("GT.header[2]"));
        System.out.println(gtm.getText("GT[1][1]"));
        gtm.work("Tellurium jQuery");
    }

    @Test
    public void testGwork(){
        useCache(false);
        System.out.println(gtm.getText("GT.header[2]"));
        System.out.println(gtm.getText("GT.Label"));
        gtm.gwork("Tellurium jQuery");
    }

    @Test
    public void testWorkWithCache(){
        useCache(true);
        System.out.println(gtm.getText("GT.header[2]"));
        System.out.println(gtm.getText("GT[1][1]"));
        gtm.work("Tellurium jQuery");
    }

    @Test
    public void testGetTableText(){
        useCache(true);
        useTelluriumApi(true);
        String[] texts = gtm.getAllTableCellText("GT");
        assertNotNull(texts);
        System.out.println("Get Table body text for GT");
        for(String text: texts){
            System.out.println(text);
        }
    }

    @Test
    public void testShowUiModule(){
        useCache(true);
        useTelluriumApi(true);
        gtm.show("GT", 2000);
    }

    @Test
    public void testGetTableSize(){
        useCache(true);
        useTelluriumApi(true);
        int size = gtm.getTableMaxTbodyNum("GT");
        assertEquals(1, size);
        size = gtm.getTableMaxColumnNumForTbody("GT", 1);
        assertEquals(3, size);
        size = gtm.getTableMaxRowNumForTbody("GT", 1);
        assertEquals(1, size);
        size = gtm.getTableMaxColumnNum("GT");
        assertEquals(3, size);
        size = gtm.getTableMaxRowNum("GT");
        assertEquals(1, size);
        size = gtm.getTableFootColumnNum("GT");
        assertEquals(0, size);
        size = gtm.getTableHeaderColumnNum("GT");
        assertEquals(3, size);
    }

    @Test
    public void testShowUi() {
        useEngineLog(true);
        useTelluriumApi(true);
        useCache(true);
//        gtm.show("GT", 10000);
        gtm.startShow("GT");
        gtm.endShow("GT");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
