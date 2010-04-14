package org.telluriumsource.test;

import org.telluriumsource.tester.QUnitTestCase;
import org.junit.Test;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 16, 2009
 */
public class QUnitDemoTest extends QUnitTestCase {

    @Test
    public void testDemo(){
//        registerTest("demo",Demo.js, Demo.body);
        registerTest("demo");
        runTest("demo");
    }

    @Test
    public void testGoogleSearch(){
        registerTest("googlesearch");
        runTest("googlesearch");
    }
}
