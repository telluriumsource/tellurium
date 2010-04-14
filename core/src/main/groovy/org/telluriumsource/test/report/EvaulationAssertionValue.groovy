package org.telluriumsource.test.report
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle;


/**
 * hold single parameter for evaulation
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 20, 2008
 *
 */
class EvaulationAssertionValue extends AssertionValue{

    private def value
    protected IResourceBundle i18nBundle


    public EvaulationAssertionValue(){
    	  i18nBundle = Environment.instance.myResourceBundle()
    }
    public String toString() {
        final int typicalLength = 64
        final String avpSeparator = ": "
        final String fieldSeparator = ","
        final String fieldStart = " "

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(i18nBundle.getMessage("EvaulationAssertionValue.Expected")).append(avpSeparator).append("\"" + value + "\"").append(fieldSeparator)

        return sb.toString()
    }
}