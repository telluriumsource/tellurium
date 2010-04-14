package org.telluriumsource.ddt

import org.telluriumsource.test.ddt.TelluriumDataDrivenTest

/**
 * Date driven testing for Tellurium issues page
 *
 * @author   Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 18, 2010
 * 
 */
class TelluriumIssuesDataDrivenTest extends TelluriumDataDrivenTest{


    public void testDataDriven() {

        includeModule org.telluriumsource.ddt.TelluriumIssuesModule.class

        //load file
        loadData "src/test/resources/org/telluriumsource/data/TelluriumIssuesInput.txt"

        useCache(true);
        useCssSelector(false);
//        useClosestMatch(true);
      
        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()

    }
}
