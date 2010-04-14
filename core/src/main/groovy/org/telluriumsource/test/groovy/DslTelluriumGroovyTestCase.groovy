package org.telluriumsource.test.groovy

import org.telluriumsource.component.connector.SeleniumConnector
import org.telluriumsource.framework.bootstrap.TelluriumSupport

/**
 * Used by the DSL Script Engine
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class DslTelluriumGroovyTestCase extends BaseTelluriumGroovyTestCase{
    
//    protected SeleniumConnector conn

    public SeleniumConnector getConnector() {
        if(conn == null)
            return new SeleniumConnector()
        else
            return conn
    }


    public void testAlwaysSucceeds() {
    }

    public void init(){
        tellurium = TelluriumSupport.addSupport()
        tellurium.start(customConfig)
        conn = tellurium.connector
    }


    public void shutDown(){
        if (tellurium != null)
          tellurium.stop()  
    }
}