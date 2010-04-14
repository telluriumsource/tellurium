package test;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.AfterClass;
import module.JettyLogonModule;
import static org.junit.Assert.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 3, 2010
 *
 */
public class JettyLogonJUnitTestCase  extends TelluriumJUnitTestCase {
    private static JettyLogonModule jlm;
    private static MockHttpServer server;

    @BeforeClass
    public static void initUi() {
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/logon.html", JettyLogonModule.HTML_BODY);
        server.start();

        jlm = new  JettyLogonModule();
        jlm.defineUi();
        connectSeleniumServer();
        useCssSelector(true);
        useTelluriumEngine(true);
        useTrace(true);
    }

    @Before
    public void connectToLocal() {
        connectUrl("http://localhost:8080/logon.html");
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
    public void testGetUiByTag(){
        String[] teuids = jlm.getInputBox();
        assertNotNull(teuids);
        for(String teuid: teuids){
            jlm.keyType(teuid, "Tellurium Source");
        }
        jlm.removeMarkedUids("input");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
        server.stop();
    }
}
