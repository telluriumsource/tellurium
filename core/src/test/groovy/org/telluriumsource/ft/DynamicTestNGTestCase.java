package org.telluriumsource.ft;

import org.telluriumsource.module.DynamicModule;
import org.telluriumsource.test.java.TelluriumMockTestNGTestCase;
import org.testng.annotations.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Apr 8, 2010
 */
public class DynamicTestNGTestCase extends TelluriumMockTestNGTestCase {
    private static DynamicModule dm;

    @BeforeClass
    public static void initUi() {
        registerHtml("Dynamic");

        dm = new DynamicModule();
        dm.defineUi();
        useTelluriumEngine(true);
        useTrace(true);
    }

    @DataProvider(name = "keyvalue-provider")
    public Object[][] configKeyValuePairs() {
        // boolean useSelector, boolean useCache, boolean useTeApi
        return new Object[][]{
                new Object[]{"AAA", "111"},
                new Object[]{"BBB", "222"},
                new Object[]{"CCC", "333"}
        };
    }

    @DataProvider(name = "id-provider")
    public Object[][] configIds() {
        // boolean useSelector, boolean useCache, boolean useTeApi
        return new Object[][]{
                new Object[]{"111", "222"},
                new Object[]{"333", "444"},
                new Object[]{"555", "666"}
        };
    }

    @BeforeMethod
    public void connectToLocal() {
        connect("Dynamic");
    }

    @Test(dataProvider = "keyvalue-provider")
    @Parameters({"key", "value"})
    public void testKeyValues(String key, String value){

    }

    @Test(dataProvider = "id-provider")
    @Parameters({"part1", "part2"})
    public void testIds(String part1, String part2){

    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }

}
