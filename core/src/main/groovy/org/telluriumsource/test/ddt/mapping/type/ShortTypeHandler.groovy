package org.telluriumsource.test.ddt.mapping.type
/**
 * Handle Short Type
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class ShortTypeHandler implements TypeHandler {

    public valueOf(String s) {
        return Short.parseShort(s)
    }

}