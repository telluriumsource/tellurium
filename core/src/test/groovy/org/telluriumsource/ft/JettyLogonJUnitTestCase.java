package org.telluriumsource.ft;

import org.telluriumsource.framework.Environment;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;
import org.telluriumsource.module.JettyLogonModule;
import org.telluriumsource.entity.EngineState;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Dec 23, 2009
 */
public class JettyLogonJUnitTestCase extends TelluriumMockJUnitTestCase {
    private static JettyLogonModule jlm;
    static{
        Environment env = Environment.getEnvironment();
        env.useConfigString(JettyLogonModule.JSON_CONF);
    }

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("JettyLogon");

        jlm = new JettyLogonModule();
        jlm.defineUi();
        useCssSelector(true);
        useTelluriumEngine(true);
        useTrace(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("JettyLogon");
    }

    @Test
    public void testDumpEnvironment(){
        dumpEnvironment();    
    }

    @Test
    public void testDump(){
        jlm.dump("Form");
    }

    @Test
    public void testStringifyUiModule(){
        String json = jlm.toString("Form");
        System.out.println(json);
    }

    @Test
    public void testDiagnose(){
        jlm.diagnose("Form.Username.Input");
        jlm.diagnose("ProblematicForm.Username.Input");
    }

    @Test
    public void testValidateUiModule(){
        jlm.validate("Form");
        jlm.validate("ProblematicForm");
    }

    @Test
    public void testLogon() {
        jlm.logon("test", "test");
    }

    @Test
    public void testLogonWithClosestMatch() {
        useClosestMatch(true);
        jlm.plogon("test", "test");
        useClosestMatch(false);
    }

    @Test
    public void testToggle(){
        jlm.toggle("Form.Username.Input");
        pause(500);
        jlm.toggle("Form.Username.Input");
    }

    @Test
    public void testIsDisabled(){
        useCssSelector(true);
        boolean result = jlm.isDisabled("Form.Username.Input");
        assertFalse(result);
        useCssSelector(false);
        result = jlm.isDisabled("Form.Username.Input");
        assertFalse(result);
        useCssSelector(true);
    }

    @Test
    public void testGetCSS(){
        String[] css = jlm.getCSS("Form.Username.Input", "background-color");
        assertNotNull(css);
        System.out.println("Background color for Form.Username.Input: " + css[0]);
    }

    @Ignore
    @Test
    public void testCookies(){
        useTelluriumApi(false);
        jlm.setCookieByJQuery("tellurium", "cool");
        String cookie = jlm.getCookieByJQuery("tellurium");
        assertEquals("cool", cookie);
        jlm.deleteCookieByJQuery("tellurium");
        cookie = jlm.getCookieByJQuery("tellurium");
        assertNull(cookie);
        jlm.setCookieByJQuery("aost", "cool");
        jlm.setCookieByJQuery("tellurium", "great");
        jlm.deleteAllCookiesByJQuery();
        cookie = jlm.getCookieByJQuery("aost");
        assertNull(cookie);
        cookie = jlm.getCookieByJQuery("tellurium");
        assertNull(cookie);
    }

    @Test
    public void testLogo(){
        jlm.validate("Logo");
        jlm.diagnose("Logo");
        String alt = jlm.getLogoAlt();
        assertNotNull(alt);
        assertEquals("Logo", alt);
    }

    @Test
    public void testSpecialCharacter(){
        String alt = jlm.getImageAlt();
        assertNotNull(alt);
        assertEquals("Image 5", alt);
        jlm.typeImageName("Image 5");
    }

    @Test
    public void testLogicContainer(){
        jlm.validate("AbstractForm");
        jlm.diagnose("AbstractForm.Form1.Password.Password1.Label");
        jlm.alogon("test", "test");
    }

    @Ignore
    @Test
    public void testAddRemoveScript(){
        String script = "var firebug=document.createElement('script');" +
       "firebug.setAttribute('src','http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js');" +
       "document.body.appendChild(firebug);" +
       "(function(){if(window.firebug.version){firebug.init();}else{setTimeout(arguments.callee);}})();" +
       "void(firebug);";
        addScript(script, "firebug-litle");
        pause(500);
        removeScript("firebug-litle");
    }

    @Test
    public void testGetEngineState(){
        EngineState state = getEngineState();
        assertNotNull(state);
        System.out.println(state.showMe());
    }

    @Test
    public void testUseEngineLog(){
        useEngineLog(true);
        jlm.logon("tellurium", "source");
        useEngineLog(false);
        connect("JettyLogon");
        jlm.logon("tellurium", "testing");
    }

    @Test
    public void testGetHTMLSource(){
        useEngineLog(true);
        useTelluriumEngine(true);
        jlm.getHTMLSource("Form");
    }

    @Test
    public void testShowUi(){
        useEngineLog(true);
        useTelluriumApi(true);
        useCache(true);
//        jlm.show("Form", 10000);
        jlm.startShow("Form");
        jlm.endShow("Form");
    }

    @Test
    public void testGetUiByTag(){
        useEngineLog(true);
        useTelluriumEngine(true);
        String[] teuids = jlm.getInputBox();
        assertNotNull(teuids);
        for(String teuid: teuids){
            jlm.keyType(teuid, "Tellurium Source");
        }
        jlm.removeMarkedUids("input");
    }

    @Test
    public void testWaitForCondition(){
        useEngineLog(true); 
        useTelluriumEngine(false);
        jlm.waitForCondition("try {var doc =selenium.browserbot.getCurrentWindow(true).document;doc.readyState =='incomplete'}catch(err){false}", 10000);
        jlm.waitForCondition("selenium.getText(\"//input[@type='text' and @name='j_username']\")=='Tellurium'", 10000);
        useTelluriumEngine(true);
        jlm.waitForCondition("try {var doc =selenium.browserbot.getCurrentWindow(true).document;doc.readyState =='incomplete'}catch(err){false}", 10000);
        jlm.waitForCondition("selenium.getText(\"//input[@type='text' and @name='j_username']\")=='Tellurium'", 10000);
    }

    @Test
    public void testCaptureScreen(){
        jlm.captureScreenshot("jettyLogon1.png");
        //XXX: this method requires absolute path !!!
        jlm.captureEntirePageScreenshot("\\tmp\\jettylogon2.png", "background=#CCFFDD");
    }

    @Test
    public void testCaptureScreenToString(){
        String shot1 = jlm.captureScreenshotToString();
        assertNotNull(shot1);
        System.out.println("Screenshot: \n" + shot1);
        String shot2 = jlm.captureEntirePageScreenshotToString("background=#CCFFDD");
        assertNotNull(shot2);
        System.out.println("Entire Screenshot: \n" + shot2);
    }

    @Test
    public void testRetrieveLastRemoteControlLogs(){
        jlm.logon("tellurium", "testing");
        String log = jlm.retrieveLastRemoteControlLogs();
        assertNotNull(log);
        System.out.println(log);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }
}
