package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.InputBox

/**
 *    Input Box builder
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class InputBoxBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        def df = [:]
        df.put(TAG, InputBox.TAG)
        InputBox inputbox = this.internBuild(new InputBox(), map, df)

        return inputbox
    }
}