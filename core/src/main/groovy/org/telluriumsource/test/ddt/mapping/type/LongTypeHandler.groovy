package org.telluriumsource.test.ddt.mapping.type
/**
 * Handle Long type
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class LongTypeHandler implements TypeHandler{

    public valueOf(String s) {
        Long.parseLong(s)
    }

}