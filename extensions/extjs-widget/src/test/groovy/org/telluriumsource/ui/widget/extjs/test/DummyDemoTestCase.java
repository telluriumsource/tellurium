package org.telluriumsource.ui.widget.extjs.test;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.ui.widget.extjs.module.DummyDemo;
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 21, 2009
 */
public class DummyDemoTestCase extends TelluriumJavaTestCase {
    private static DummyDemo dd;

    @BeforeClass
    public static void initUi() {
        dd = new DummyDemo();
        dd.defineUi();
    }

    @Test
    public void testHello(){
        String hello = (String)dd.hello();
        assertEquals("Hello extJS Widget!", hello);
    }

}
