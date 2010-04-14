package org.telluriumsource.ft;

import org.junit.*;
import org.telluriumsource.module.SpecialModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Apr 6, 2010
 */
public class SpecialJunitTestCase extends TelluriumMockJUnitTestCase {
    private static SpecialModule sm;

    @BeforeClass
    public static void initUi() {
        registerHtml("Special");

        sm = new SpecialModule();
        sm.defineUi();
        //comment out the following line, the test will break
        useTelluriumEngine(true);
        useTrace(true);
        useTelluriumApi(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("Special");
    }

    @Test
    public void testValidateUiModule(){
        sm.validate("Form");
    }

    @Test
    public void testGetHTMLSource(){
        sm.getHTMLSource("Form");
    }

    @Test
    public void testCheck(){
        sm.check("US", "123456789");
    }

    //TODO: multiple selection still got problem, fix this
    @Ignore
    @Test
    public void testMultiSelect(){
        sm.selectByLabel("Form.Country", "*E");
        String[] selected = sm.getSelectedLabels("Form.Country");
        assertNotNull(selected);
        assertEquals(2, selected.length);
        assertEquals("BE", selected[0]);
        assertEquals("ES", selected[1]);
    }

    @Test
    public void testSelect(){
        String[] countries = sm.getSelectOptions("Form.Country");
        for(String country: countries){
            System.out.println("Country: " + country);
        }
        String[] values = sm.getSelectValues("Form.Country");
        for(String value: values){
            System.out.println("Value: " + value);
        }
        sm.selectByLabel("Form.Country", "US");
        String selected = sm.getSelectedLabel("Form.Country");
        assertEquals("US", selected);
        sm.selectByLabel("Form.Country", "$E");
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("BE", selected);
        sm.selectByLabel("Form.Country", "^E");
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("ES", selected);
        sm.selectByValue("Form.Country", "8");
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("VG", selected);
        sm.selectByIndex("Form.Country", 6);
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("CN", selected);
        sm.selectById("Form.Country", "uk");
        selected = sm.getSelectedLabel("Form.Country");
        assertEquals("UK", selected);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
