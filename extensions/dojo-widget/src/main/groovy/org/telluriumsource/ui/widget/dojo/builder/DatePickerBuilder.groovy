package org.telluriumsource.ui.widget.dojo.builder

import org.telluriumsource.ui.widget.dojo.object.DatePicker
import org.telluriumsource.ui.builder.UiObjectBuilder

/**
 * Builder for widget Date picker
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 3, 2008
 * 
 */
class DatePickerBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
       //add default parameters so that the builder can use them if not specified
        def df = [:]
        DatePicker datepicker = this.internBuild(new DatePicker(), map, df)
        datepicker.defineWidget()

        return datepicker
    }


}