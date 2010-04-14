package org.telluriumsource.ft;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.CheckBoxExample;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 28, 2010
 */
public class CheckBoxExampleJUnitTestCase extends TelluriumJUnitTestCase
{
    protected static CheckBoxExample cbx;

    @BeforeClass
    public static void initUi() {
        cbx = new CheckBoxExample();
        cbx.defineUi();
        connectSeleniumServer();
    }

    @Before
    public void connectToSamples(){
        connectUrl("http://www.asp101.com/samples/checkbox.asp");
    }

    @Test
    public void testCheckOneBox() {
        cbx.checkOneBox("root.input0");
        assertEquals("on", cbx.checkBoxValues("root.input0"));
    }

    @Test
    public void testIsChecked(){
        cbx.check("root.input0");
        assertTrue(cbx.isChecked("root.input0"));
        cbx.uncheck("root.input0");
        assertFalse(cbx.isChecked("root.input0"));
    }
}
