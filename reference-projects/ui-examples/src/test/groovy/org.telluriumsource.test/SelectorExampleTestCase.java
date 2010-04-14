package org.telluriumsource.test;

import org.junit.*;
import org.telluriumsource.module.SelectorExampleModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Apr 12, 2010
 */
public class SelectorExampleTestCase  extends TelluriumMockJUnitTestCase {
    private static SelectorExampleModule sem;

    @BeforeClass
    public static void initUi() {
        registerHtml("SelectorExample");

        sem = new SelectorExampleModule();
        sem.defineUi();
        //comment out the following line, the test will break
        useTelluriumEngine(true);
        useTrace(true);
        useTelluriumApi(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("SelectorExample");
    }

    @Test
    public void testValidateUiModule(){
        sem.validate("Form");
    }

    @Test
    public void testGetHTMLSource(){
        sem.getHTMLSource("Form");
    }

    @Test
    public void testCheck(){
        sem.check("US", "123456789");
    }

    //TODO: multiple selection still got problem, fix this
    @Ignore
    @Test
    public void testMultiSelect(){
        sem.selectByLabel("Form.Country", "*E");
        String[] selected = sem.getSelectedLabels("Form.Country");
        assertNotNull(selected);
        assertEquals(2, selected.length);
        assertEquals("BE", selected[0]);
        assertEquals("ES", selected[1]);
    }

    @Test
    public void testSelect(){
        String[] countries = sem.getSelectOptions("Form.Country");
        for(String country: countries){
            System.out.println("Country: " + country);
        }
        String[] values = sem.getSelectValues("Form.Country");
        for(String value: values){
            System.out.println("Value: " + value);
        }
        sem.selectByLabel("Form.Country", "US");
        String selected = sem.getSelectedLabel("Form.Country");
        assertEquals("US", selected);
        sem.selectByLabel("Form.Country", "$E");
        selected = sem.getSelectedLabel("Form.Country");
        assertEquals("BE", selected);
        sem.selectByLabel("Form.Country", "^E");
        selected = sem.getSelectedLabel("Form.Country");
        assertEquals("ES", selected);
        sem.selectByValue("Form.Country", "8");
        selected = sem.getSelectedLabel("Form.Country");
        assertEquals("VG", selected);
        sem.selectByIndex("Form.Country", 6);
        selected = sem.getSelectedLabel("Form.Country");
        assertEquals("CN", selected);
        sem.selectById("Form.Country", "uk");
        selected = sem.getSelectedLabel("Form.Country");
        assertEquals("UK", selected);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
