package org.telluriumsource.test;

import org.telluriumsource.entity.UiByTagResponse;
import org.telluriumsource.module.FormExampleModule;
import org.telluriumsource.test.java.TelluriumMockTestNGTestCase;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static org.testng.AssertJUnit.assertNotNull;


/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 3, 2010
 *
 */
public class FormExampleTestCase extends TelluriumMockTestNGTestCase {
    private static FormExampleModule fem;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("FormExample");

        fem = new FormExampleModule();
        fem.defineUi();
        useTelluriumEngine(true);
        useTrace(true);
        useEngineLog(true);
    }

    @DataProvider(name = "config-provider")
    public Object[][] configParameters() {
        // boolean useSelector, boolean useCache, boolean useTeApi
        return new Object[][]{
                new Object[]{true, true, true},
                new Object[]{true, true, false},
                new Object[]{true, false, true},
                new Object[]{true, false, false},
                new Object[]{false, true, true},
                new Object[]{false, true, false},
                new Object[]{false, false, true},
                new Object[]{false, false, false}
        };
    }

    @BeforeMethod
    public void connectToLocal() {
        connect("FormExample");
    }

    @Test
    public void testStringifyUiModule(){
        String json = fem.toString("Form");
        System.out.println(json);
    }

    @Test
    public void testDiagnose(){
        fem.diagnose("Form.Username.Input");
    }

    @Test
    public void testValidateUiModule(){
        fem.validate("Form");
    }

    @Test
    public void testGetCSS(){
        String[] css = fem.getCSS("Form.Username.Input", "background-color");
        assertNotNull(css);
        System.out.println("Background color for Form.Username.Input: " + css[0]);
    }

    @Test
    public void testGetHTMLSource(){
        fem.getHTMLSource("Form");
    }

    @Test
    public void testShowUi(){
//        fem.show("Form", 10000);
        fem.startShow("Form");
        fem.endShow("Form");
    }

    @Test
    public void testGetUiByTag(){
        Map filter = new HashMap();
        filter.put("type", "text");
        UiByTagResponse resp = fem.getUiByTag("input", filter);
        String[] teuids = resp.getTids();
        assertNotNull(teuids);
        for(String teuid: teuids){
            fem.keyType(teuid, "Tellurium Source");
        }
        fem.removeMarkedUids("input");
    }

    @Test(dataProvider = "config-provider")
    @Parameters({"useSelector", "useCache", "useTeApi"})
    public void testLogon(boolean useSelector, boolean useCache, boolean useTeApi) {
        useCssSelector(useSelector);
        useCache(useCache);
        useTelluriumApi(useTeApi);
        fem.logon("test", "test");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
