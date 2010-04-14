package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.Span

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 4, 2008
 * 
 */
class SpanBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        def df = [:]
        df.put(TAG, Span.TAG)
        Span span = this.internBuild( new Span(), map, df)

        return span
    }

}