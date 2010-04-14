package org.telluriumsource.udl;

import org.json.simple.JSONObject;
import org.telluriumsource.udl.code.IndexType;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 18, 2010
 */
public class Index {
    public static final String TYPE = "type";
    IndexType type;

    public static final String VALUE = "value";
    String value;

    public Index() {
    }

    public Index(String value) {
        type = IndexType.VAL;
        this.value = value;
    }

    public Index(IndexType type, String value) {
        this.type = type;
        this.value = value;
    }

    public IndexType getType() {
        return type;
    }

    public void setType(IndexType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public JSONObject toJSON() {
        JSONObject jso = new JSONObject();
        jso.put(TYPE, this.type.name());
        jso.put(VALUE, this.value);
        
        return jso;
    }
}
