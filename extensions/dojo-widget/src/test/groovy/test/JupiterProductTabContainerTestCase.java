package test;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import module.JupiterProductTabContainer;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 12, 2009
 */
public class JupiterProductTabContainerTestCase extends TelluriumJavaTestCase {
    private static JupiterProductTabContainer jptc;

    @BeforeClass
    public static void initUi() {
        jptc = new JupiterProductTabContainer();
        jptc.defineUi();
    }

    @Ignore
    @Test
    public void testClickTabs(){
        connectUrl("https://qa.jupiter.jewelry.qa:8083/jupiter/");
        //many steps to the Customer Care app
        jptc.clickTab("Recent");
        jptc.clickTab("Search");
        jptc.clickTab("Turntable");
    }
}
