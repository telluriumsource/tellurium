package org.telluriumsource.ddt

import org.telluriumsource.test.ddt.TelluriumDataDrivenTest

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 18, 2010
 * 
 * 
 */
class GoogleDataDrivenTest extends TelluriumDataDrivenTest{

    String data = """
    ##Data test for "Google Search"
    ##TEST | INPUT
    DoGoogleSearch | tellurium testing
    DoFeelingLuckySearch | aost groovy
    DoGoogleSearch | data driven testing"""

    public void testDataDriven() {

        includeModule org.telluriumsource.ddt.GoogleDataDrivenModule.class

        //load String
        useData data

        connectSeleniumServer()

        useCache(true);
        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()
    }
}
