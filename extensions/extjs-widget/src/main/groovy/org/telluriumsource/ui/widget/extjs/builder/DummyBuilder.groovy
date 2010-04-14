package org.telluriumsource.ui.widget.extjs.builder

import org.telluriumsource.ui.widget.extjs.object.Dummy
import org.telluriumsource.ui.builder.UiObjectBuilder

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 21, 2009
 * 
 */
class DummyBuilder extends UiObjectBuilder{

    public Object build(Map map, Closure closure) {
       //add default parameters so that the builder can use them if not specified
        def df = [:]
        Dummy dummy = this.internBuild(new Dummy(), map, df)
        dummy.defineWidget()

        return dummy
    }

}