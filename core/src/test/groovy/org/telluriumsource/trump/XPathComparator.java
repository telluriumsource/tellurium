package org.telluriumsource.trump;

import java.util.Comparator;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 13, 2009
 */
public class XPathComparator implements Comparator<XPath> {
    public int compare(XPath first, XPath second) {
        return second.getXpath().length() - first.getXpath().length();
    }
}
