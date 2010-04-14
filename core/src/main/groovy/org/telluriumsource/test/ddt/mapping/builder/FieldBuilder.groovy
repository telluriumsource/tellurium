package org.telluriumsource.test.ddt.mapping.builder

import org.telluriumsource.test.ddt.mapping.Field

/**
 * Build Field from a collection of attributes
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class FieldBuilder extends BaseBuilder{

    public build(Map map) {
        map = makeCaseInsensitive(map)

        Field f = new Field()
        f.name = map.get(NAME)
        f.description = map.get(DESCRIPTION)

        //do not override the default type
        if(map.get(TYPE) != null)
            f.type = map.get(TYPE)

        if(map.get(NULLABLE) != null)
            f.nullable = map.get(NULLABLE)

        f.nullValue = map.get(NULLVALUE)

        if(map.get(LENGTH) != null)
            f.length = map.get(LENGTH)

        f.pattern = map.get(PATTERN)
        
        return f

    }

}