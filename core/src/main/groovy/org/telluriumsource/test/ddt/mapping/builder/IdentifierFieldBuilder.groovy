package org.telluriumsource.test.ddt.mapping.builder

import org.telluriumsource.test.ddt.mapping.IdentifierField

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 25, 2008
 *
 */
class IdentifierFieldBuilder extends BaseBuilder{

    public IdentifierField build(Map map) {
        map = makeCaseInsensitive(map)

        IdentifierField f = new IdentifierField()
        f.name = map.get(NAME)
        f.description = map.get(DESCRIPTION)

        //do not override the default type
        if(map.get(TYPE) != null)
            f.type = map.get(TYPE)

        //field set identifier cannot be null
        f.nullable = false

        if(map.get(LENGTH) != null)
            f.length = map.get(LENGTH)

        f.pattern = map.get(PATTERN)

        f.value = map.get(VALUE)
        
        return f

    }

}