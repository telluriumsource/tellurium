package org.telluriumsource.test.ddt.mapping.builder

import org.telluriumsource.test.ddt.mapping.TestField

/**
 * 
 * Actioin Field builder
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 *
 */
class TestFieldBuilder extends BaseBuilder{
    private static final String DEFAULT_ACTION_NAME = "test"

    public TestField build(Map map) {
        map = makeCaseInsensitive(map)

        TestField f = new TestField()

        //if the action name is not specified, use the default name
        if(map.get(NAME))
            f.name = map.get(NAME)
        else
            f.name = DEFAULT_ACTION_NAME

        f.description = map.get(DESCRIPTION)

        //do not override the default type
        if(map.get(TYPE) != null)
            f.type = map.get(TYPE)

        //action field cannot be null
        f.nullable = false

        if(map.get(LENGTH) != null)
            f.length = map.get(LENGTH)

        f.pattern = map.get(PATTERN)

        f.value = map.get(VALUE)

        return f

    }
}