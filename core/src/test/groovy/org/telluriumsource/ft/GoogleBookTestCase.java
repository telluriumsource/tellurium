package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.module.GoogleBookModule;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.Test;
import org.telluriumsource.util.LogLevels;

import static org.junit.Assert.assertEquals;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 6, 2010
 */
public class GoogleBookTestCase extends TelluriumJUnitTestCase {
    private static GoogleBookModule gbm;
    private static MockHttpServer server;

    @BeforeClass
    public static void initUi() {
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/gbook.html", GoogleBookModule.HTML_BODY);
        server.start();

        gbm = new  GoogleBookModule();
        gbm.defineUi();
        connectSeleniumServer();
        //enableLogging(LogLevels.ALL);        

        useCssSelector(true);
        useTelluriumEngine(true);
//        useTelluriumApi(true);
        useTrace(true);
//        useCache(true);
//        useMacroCmd(true);
    }

    @Before
    public void connectToLocal() {
        connectUrl("http://localhost:8080/gbook.html");
    }

    @Test
    public void testDump(){
        gbm.dump("GoogleBooksList");
    }
    
    @Test
    public void testDiagnose(){
        gbm.diagnose("GoogleBooksList.subcategory[1]");
        gbm.diagnose("GoogleBooksList.subcategory[1].title");
        gbm.diagnose("GoogleBooksList.subcategory[1].links[1]");
        gbm.diagnose("GoogleBooksList.subcategory[2]");
    }

    @Test
    public void testValidateUiModule(){
        gbm.validate("GoogleBooksList");
    }

    @Test
    public void testListSize(){
        int size = gbm.getListSize("GoogleBooksList.subcategory");
        assertEquals(3, size);
        for(int i=1; i<=size; i++){
            int ls = gbm.getListSize("GoogleBooksList.subcategory[" + i + "].links");
            assertEquals(8, ls);
        }
    }

    @Test
    public void testToHTML(){
        System.out.println(gbm.toHTML("GoogleBooksList"));
    }

    @Test
    public void testGetHTMLSource(){
        useEngineLog(true);
        useTelluriumApi(true);
        useCache(true);
        gbm.getHTMLSource("GoogleBooksList");
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
        server.stop();
    }
}
