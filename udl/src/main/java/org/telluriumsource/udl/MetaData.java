package org.telluriumsource.udl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.rmi.server.UID;
import java.util.List;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 17, 2010
 *
 */
public class MetaData {
    public static String ID = "id";
    protected String id;

    public static String TYPE = "type";

    public static String VARIABLES = "variables";
    List<String> variables;

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public MetaData() {
    }

    public MetaData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONObject toJSON() {
        JSONObject jso = new JSONObject();
        jso.put(ID, this.id);
        jso.put(TYPE, "UiObject");
        
        if(this.variables != null && this.variables.size() > 0){
            JSONArray ar = new JSONArray();
            for(String var: this.variables){
                ar.add(var);
            }
            jso.put(VARIABLES, ar);
        }

        return jso;
    }

}
