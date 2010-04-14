package org.telluriumsource.trump;

import java.util.HashMap;
import java.util.Map;

/**
 * Hold data for screen captured data for one UI element
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Dec 10, 2008
 */
public class Element {
    private String uid;
    private String xpath;
    private Map attributes = new HashMap<String, String>();

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String key, String value){
        this.attributes.put(key, value);
    }
}
