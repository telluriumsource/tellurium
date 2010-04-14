package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.Selector

/**
 *    Selector builder
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class SelectorBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        //add default parameters so that the builder can useString them if not specified
        def df = [:]
        df.put(TAG, Selector.TAG)
        Selector selector = this.internBuild(new Selector(), map, df)

        return selector  
    }
}