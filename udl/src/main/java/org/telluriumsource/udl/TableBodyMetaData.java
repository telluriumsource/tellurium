package org.telluriumsource.udl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 18, 2010
 */
public class TableBodyMetaData extends MetaData {
    public static final String TBODY = "tbody";
    protected Index tbody;

    public static final String ROW = "row";
    protected Index row;

    public static final String COLUMN = "column";
    protected Index column;

    public Index getTbody() {
        return tbody;
    }

    public void setTbody(Index tbody) {
        this.tbody = tbody;
    }

    public Index getRow() {
        return row;
    }

    public void setRow(Index row) {
        this.row = row;
    }

    public Index getColumn() {
        return column;
    }

    public void setColumn(Index column) {
        this.column = column;
    }

    public TableBodyMetaData(){

    }

    public TableBodyMetaData(String id) {
        super(id);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jso = super.toJSON();
        jso.put(TBODY, this.tbody.toJSON());
        jso.put(ROW, this.row.toJSON());
        jso.put(COLUMN, this.column.toJSON());
        jso.put(TYPE, "TBody");

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
