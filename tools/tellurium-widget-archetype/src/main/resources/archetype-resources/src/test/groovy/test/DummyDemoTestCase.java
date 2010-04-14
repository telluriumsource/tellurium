#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import ${package}.module.DummyDemo;
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
        assertEquals("Hello Tellurium Widget!", hello);
    }

}
