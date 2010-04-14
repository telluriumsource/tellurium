package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.Button

/**
 *    Button builder
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class ButtonBuilder extends UiObjectBuilder{
    
    public build(Map map, Closure c) {
        //add default parameters so that the builder can useString them if not specified
        def df = [:]
        df.put(TAG, Button.TAG)
        Button button = this.internBuild(new Button(), map, df)

        return button
    }
}