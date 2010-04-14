package org.telluriumsource.test.groovy;

import groovy.util.GroovyTestSuite;
import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.telluriumsource.framework.bootstrap.TelluriumSupport;
import org.telluriumsource.framework.TelluriumFramework;

/**
 * The test suite can hold many test cases. We need to common/shared processing here.
 *
 * Leave this class as a template and do not try to extend it
 * 
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
public final class SampleTelluriumGroovyTestSuite extends TestSuite
{

    protected static String TEST_ROOT = "src/example/test/";

    protected static GroovyTestSuite gsuite;

    protected static TestSuite suite;

    static {
        gsuite = new GroovyTestSuite();
        suite  = new TestSuite();
    }

    protected static TelluriumFramework tellurium;

//    protected static EmbeddedSeleniumServer server;
//
//    protected static SeleniumConnector conn = new SeleniumConnector();

    //need to override this method to put test cases to the test suite gsuite
    //but unfortunately static method cannot be overridden.   You have to duplicate
    //everyting here and fill in the addTestCases() method.
    //So, just leave this class as a template and do not try to extend it.
    protected static void addTestCases() throws Exception{

    }

    public static Test suite() throws Exception {
        addTestCases();

        TestSetup wrapper = new TestSetup(suite) {

            protected void setUp() {
                oneTimeSetUp();
            }

            protected void tearDown() {
                oneTimeTearDown();
            }
        };

        return wrapper;
    }

    public static void oneTimeSetUp() {
        tellurium = TelluriumSupport.addSupport();
        tellurium.start();
    }

    public static void oneTimeTearDown() {
        tellurium.stop();
    }

}
