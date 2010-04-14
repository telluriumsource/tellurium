package org.telluriumsource.test;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.ContainerExampleModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 8, 2010
 */
public class ContainerExampleTestCase extends TelluriumMockJUnitTestCase {
    private static ContainerExampleModule cem;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("ContainerExample");

        cem = new ContainerExampleModule();
        cem.defineUi();
        useTelluriumEngine(true);
        useTrace(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("ContainerExample");
    }

    @Test
    public void testToHtml(){
        System.out.println(cem.toHTML("mainnav"));
    }

    @Test
    public void testIsElementPresent() {
        cem.click("mainnav.events");
        cem.waitForPageToLoad(10000);
        assertTrue(cem.isElementPresent("mainnav"));
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }

}
