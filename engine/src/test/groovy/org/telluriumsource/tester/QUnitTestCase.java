package org.telluriumsource.tester;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Dec 16, 2009
 */
public class QUnitTestCase extends TelluriumJUnitTestCase {
    private static QUnitHttpServer server;
    static{
        setCustomConfig(true, 4444, "*chrome", true, null);
    }

    @BeforeClass
    public static void setUp(){
        server = new QUnitHttpServer(8080);
        server.start();
        connectSeleniumServer();
    }
    
    @AfterClass
    public static void tearDown(){
        if(server != null)
            server.stop();
    }

    public static void registerTest(String testname, String javascript, String body){
       server.registerHtmlBody("/" + testname + ".html", javascript, body);
    }
    
    public static void registerTest(String testname){
       server.registerHtmlBody("/" + testname + ".html", server.getJsFile(testname), server.getHtmlFile(testname));
    }

    public static void registerTestAsHtml(String testname, String html){
        server.registerHtml("/" + testname + ".html", html);
    }

    public static void runTest(String testname){
        connectUrl("http://localhost:8080/" + testname + ".html");    
    }
}
