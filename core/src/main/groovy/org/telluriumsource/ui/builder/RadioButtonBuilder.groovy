package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.RadioButton

/**
 *  Radio Button Builder
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class RadioButtonBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        //add default parameters so that the builder can useString them if not specified
        def df = [:]
        df.put(TAG, RadioButton.TAG)
        df.put(TYPE, RadioButton.TYPE)
        RadioButton radiobutton = this.internBuild(new RadioButton(), map, df)

        return radiobutton
    }

}