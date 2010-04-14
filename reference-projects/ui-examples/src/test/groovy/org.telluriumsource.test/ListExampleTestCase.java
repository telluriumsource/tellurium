package org.telluriumsource.test;

import org.junit.*;
import org.telluriumsource.module.ListExampleModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertEquals;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Feb 8, 2010
 */
public class ListExampleTestCase extends TelluriumMockJUnitTestCase {
    private static ListExampleModule lem;
    
    @BeforeClass
    public static void initUi() {
        registerHtmlBody("ListExample");

        lem = new ListExampleModule();
        lem.defineUi();
        useCssSelector(true);
        useTelluriumEngine(true);
        useTrace(true);
    }
    
    @Before
    public void connectToLocal() {
        connect("ListExample");
    }
    
     @Test
    public void testDump(){
        lem.dump("GoogleBooksList");
    }
    
    @Test
    public void testDiagnose(){
        lem.diagnose("GoogleBooksList.subcategory[1]");
        lem.diagnose("GoogleBooksList.subcategory[1].title");
        lem.diagnose("GoogleBooksList.subcategory[1].links[1]");
        lem.diagnose("GoogleBooksList.subcategory[2]");
    }

    @Test
    public void testValidateUiModule(){
        lem.validate("GoogleBooksList");
    }

    @Test
    public void testListSize(){
        int size = lem.getListSize("GoogleBooksList.subcategory");
        assertEquals(3, size);
        for(int i=1; i<=size; i++){
            int ls = lem.getListSize("GoogleBooksList.subcategory[" + i + "].links");
            assertEquals(8, ls);
        }
    }

    @Ignore
    @Test
    public void testGetHTMLSource(){
        useEngineLog(true);
        lem.getHTMLSource("GoogleBooksList");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }   
    
}
