package org.telluriumsource.test.java;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.telluriumsource.test.mock.MockHttpServer;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 14, 2010
 */
public class TelluriumMockJUnitTestCase extends TelluriumJUnitTestCase {
    protected static MockHttpServer server;

    @BeforeClass
    public static void init(){
        server = new MockHttpServer(8080);
        server.start();
        connectSeleniumServer();
    }

    public static void registerHtml(String testname){
       server.registerHtml("/" + testname + ".html", server.getHtmlFile(testname));
    }

    public static void registerHtmlBody(String testname){
       server.registerHtmlBody("/" + testname + ".html", server.getHtmlFile(testname));
    }

    public static void setHtmlClassPath(String path){
        server.setHtmlClassPath(path);
    }

    public static void connect(String testname){
        connectUrl("http://localhost:8080/" + testname + ".html");
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
