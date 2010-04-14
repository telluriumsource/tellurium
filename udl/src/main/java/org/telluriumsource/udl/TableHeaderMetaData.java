package org.telluriumsource.udl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 18, 2010
 */
public class TableHeaderMetaData extends ListMetaData {

    public TableHeaderMetaData(String id, Index index) {
        super(id, index);
    }

    public TableHeaderMetaData(String id, String index) {
        super(id, index);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jso = super.toJSON();
        jso.put(INDEX, this.index.toJSON());
        jso.put(TYPE, "Header");

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
