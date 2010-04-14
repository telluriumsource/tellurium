package org.telluriumsource.ut

import org.telluriumsource.test.groovy.TelluriumGroovyTestCase

/**
 * Test class to find the problem for issue 64
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 17, 2008
 * 
 */
class Window_UT extends TelluriumGroovyTestCase {

    public void initUi() {
    }

    public void setUp(){
//        setUpForClass()
    }

    public void tearDown(){
//        tearDownForClass()
    }

    public void testWaitForPopup(){
      /* [Matt] This test seems not to do anything since everything below is commented,
       * but it fails if these two lines are uncommented.
        TWindow tw = new TWindow()
        tw.defineUi()
      */
        
 //       tw.waitForPopup()

 //       tw.selectOriginalWindow()

//        tw.selectChildWindow("windowName")
//        tw.openWindow("windowName", "www.google.com")
//      tw.openWindow("moreInfo", "www.google.com")
    }

}