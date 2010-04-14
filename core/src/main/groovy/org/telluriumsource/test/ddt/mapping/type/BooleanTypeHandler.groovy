package org.telluriumsource.test.ddt.mapping.type
/**
 * Handle boolean type
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class BooleanTypeHandler implements TypeHandler{

    protected final static String TRUE_VALUE = "true"

   // must check if value is null before call the following read methods
    public valueOf(String s) {
		//note, here we useString white list for the true value. Be careful when you handle null value/default value
		if(TRUE_VALUE.equalsIgnoreCase(s))
			return true
		return false        
    }

}