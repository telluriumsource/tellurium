package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.module.JettyLogonModule;
import org.telluriumsource.entity.EngineState;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;

/**
 * Test Engine state update offline
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 20, 2010
 */
public class JettyLogonOfflineTestCase extends TelluriumJUnitTestCase {
    private static JettyLogonModule jlm;
    private static MockHttpServer server;

    @BeforeClass
    public static void initUi() {
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/logon.html", JettyLogonModule.HTML_BODY);
        server.start();
        jlm = new  JettyLogonModule();
        jlm.defineUi();
    }

    @Test
    public void testOfflineEngineStateUpdate(){
        useCssSelector(true);
        useTelluriumApi(true);
        useTrace(true);
        useCache(true);
        connectSeleniumServer();
        connectUrl("http://localhost:8080/logon.html");
        jlm.logon("test", "test");
        useClosestMatch(true);
        connectUrl("http://localhost:8080/logon.html");
        jlm.plogon("test", "test");
    }

    @Test
    public void testGetEngineState(){
        connectSeleniumServer();
        connectUrl("http://localhost:8080/logon.html");
        EngineState state = getEngineState();
        assertNotNull(state);
        System.out.println(state.showMe());
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
        server.stop();
    }
}
