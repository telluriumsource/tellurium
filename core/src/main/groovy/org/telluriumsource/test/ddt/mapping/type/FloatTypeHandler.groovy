package org.telluriumsource.test.ddt.mapping.type
/**
 * Handle Float type
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class FloatTypeHandler implements TypeHandler{

    public valueOf(String s) {
        Float.parseFloat(s)
    }

}