package org.telluriumsource.test.groovy

import org.telluriumsource.component.connector.SeleniumConnector

/**
 * This test case is supposed to be one part of a test suite and there are dependency between test cases.
 * They will share a same conn
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
abstract class TelluriumSuiteGroovyTestCase extends BaseTelluriumGroovyTestCase {
//     abstract protected void initalization()
//
//     protected void setUp() {
//
//       //Since SeleniumConnector becomes a Singleton class, should get back a shared conn here
//       conn = new SeleniumConnector()
//
//       //extra initalization here
//       initalization()
//     }

//    protected static SeleniumConnector conn  = new SeleniumConnector()

//    protected static void initalization(){
//    }

/*
 //   @BeforeClass
    protected static void setUpForClass(){
        conn = new SeleniumConnector()
        initalization()
    }
*/
//    protected SeleniumConnector conn;

    public SeleniumConnector getConnector(){
        if(conn == null)
            return new SeleniumConnector()
        else
            return conn
    }
}