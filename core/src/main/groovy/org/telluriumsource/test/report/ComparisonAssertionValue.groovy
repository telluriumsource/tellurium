package org.telluriumsource.test.report
/**
 * hold two parameters, expected and actual
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 20, 2008
 *
 */
class ComparisonAssertionValue extends AssertionValue{

    private def expected

    private def actual

    public String toString() {
        final int typicalLength = 64
        final String avpSeparator = ": "
        final String fieldSeparator = ","
        final String fieldStart = " "

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(i18nBundle.getMessage("ComparisonAssertionValue.Expected")).append(avpSeparator).append("\"" + expected + "\"").append(fieldSeparator)
        sb.append(fieldStart).append(i18nBundle.getMessage("ComparisonAssertionValue.Actual")).append(avpSeparator).append("\"" + actual + "\"").append(fieldSeparator)

        return sb.toString()
    }

}