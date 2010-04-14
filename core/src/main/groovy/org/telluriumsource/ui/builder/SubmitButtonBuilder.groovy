package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.SubmitButton

/**
 *  Submit Button Builder
 * 
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class SubmitButtonBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        //add default parameters so that the builder can useString them if not specified
        def df = [:]
        df.put(TAG, SubmitButton.TAG)
        df.put(TYPE, SubmitButton.TYPE)
        SubmitButton button = this.internBuild(new SubmitButton(), map, df)

        return button
    }

}