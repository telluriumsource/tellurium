package org.telluriumsource.test;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.TableExampleModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 8, 2010
 */
public class TableExampleTestCase  extends TelluriumMockJUnitTestCase {
    private static TableExampleModule tem;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("TableExample");

        tem = new TableExampleModule();
        tem.defineUi();

        useCssSelector(true);
        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToLocal() {
        connect("TableExample");
    }

    @Test
    public void testToJSONString(){

        System.out.println(tem.toString("GT"));
    }

    @Test
    public void testValidateUiModule(){
        tem.validate("GT");
    }

    @Test
    public void testWork(){
        useCache(false);
        System.out.println(tem.getText("GT.header[2]"));
        System.out.println(tem.getText("GT.A"));
        tem.work("Tellurium jQuery");
        useCache(true);
    }

    @Test
    public void testWorkWithCache(){
        useCache(true);
        System.out.println(tem.getText("GT.header[2]"));
        System.out.println(tem.getText("GT.A"));
        tem.work("Tellurium jQuery");
    }

    @Test
    public void testGetTableText(){
        String[] texts = tem.getAllTableCellText("GT");
        assertNotNull(texts);
        System.out.println("Get Table body text for GT");
        for(String text: texts){
            System.out.println(text);
        }
    }

    @Test
    public void testGetTableSize(){
        int size = tem.getTableMaxTbodyNum("GT");
        assertEquals(1, size);
        size = tem.getTableMaxColumnNumForTbody("GT", 1);
        assertEquals(3, size);
        size = tem.getTableMaxRowNumForTbody("GT", 1);
        assertEquals(1, size);
        size = tem.getTableMaxColumnNum("GT");
        assertEquals(3, size);
        size = tem.getTableMaxRowNum("GT");
        assertEquals(1, size);
        size = tem.getTableFootColumnNum("GT");
        assertEquals(0, size);
        size = tem.getTableHeaderColumnNum("GT");
        assertEquals(3, size);
    }

    @Test
    public void testShowUi() {
        useEngineLog(true);
//        tem.show("GT", 10000);
        tem.startShow("GT");
        tem.endShow("GT");
    }

    @Test
    public void testIndex(){
        useCache(true);
        System.out.println(tem.getText("NGT.header.One"));
        System.out.println(tem.getText("NGT.header.Two"));
        System.out.println(tem.getText("NGT.header.Three"));
        System.out.println(tem.getText("NGT.header[1]"));
        System.out.println(tem.getText("NGT.header[2]"));
        System.out.println(tem.getText("NGT.header[3]"));
        System.out.println(tem.getText("NGT.A"));
        System.out.println(tem.getText("NGT[1][1]"));
        System.out.println(tem.getText("NGT[1][One]"));
        System.out.println(tem.getText("NGT.Hello"));
        System.out.println(tem.getText("NGT[1][3]"));
        System.out.println(tem.getText("NGT[1][Three]"));
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
