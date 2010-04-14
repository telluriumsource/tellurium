package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.Option

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 9, 2008
 * 
 */
class OptionBuilder  extends UiObjectBuilder{

    public build(Map map, Closure c) {
       //add default parameters so that the builder can useString them if not specified
        def df = [:]
        Option option = this.internBuild(new Option(), map, df)

        return option
    }

}