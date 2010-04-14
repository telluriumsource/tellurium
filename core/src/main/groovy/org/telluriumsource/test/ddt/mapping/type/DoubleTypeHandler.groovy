package org.telluriumsource.test.ddt.mapping.type
/**
 * Handle double type
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class DoubleTypeHandler implements TypeHandler{

    public valueOf(String s) {
        return Double.parseDouble(s)
    }

}