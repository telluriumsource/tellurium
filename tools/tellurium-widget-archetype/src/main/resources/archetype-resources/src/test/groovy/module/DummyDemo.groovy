#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 21, 2009
 * 
 */
class DummyDemo extends DslContext {
    public void defineUi() {
        ui.Container(uid: "test", clocator: [:]) {
            Tellurium_Dummy(uid: "dummy", clocator: [:])
        }
    }

    public String hello() {
        return onWidget("test.dummy", helloTelluriumWidget)
    }
}