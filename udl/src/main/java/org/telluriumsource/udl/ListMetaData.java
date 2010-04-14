package org.telluriumsource.udl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 18, 2010
 */
public class ListMetaData extends MetaData {
    public static final String INDEX = "index";
    protected Index index;

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    public ListMetaData(String id, Index index) {
        super(id);
        this.index = index;
    }

    public ListMetaData(String id, String index) {
        super(id);
        this.index = new Index(index);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jso = super.toJSON();
        jso.put(INDEX, this.index.toJSON());
        jso.put(TYPE, "List");
        
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
