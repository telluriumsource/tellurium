package org.telluriumsource.test.ddt.mapping.mapping
/**
 * The result for the field mapping
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class FieldSetMapResult {

    //the field set id associated with this result
    private String fieldSetName

    //Hashmap holds the field name and its value
    def result = [:]

    public void setFieldSetName(String fieldSetName){
        this.fieldSetName = fieldSetName
    }

    public String getFieldSetName(){
        return this.fieldSetName
    }

    public void addDataField(String name, Object obj){
        result.put(name, obj)
    }

    //duck type
    public def getDataField(String name){
        return result.get(name)
    }

    public boolean isEmpty(){
        return result.isEmpty()
    }

    public Map getResults(){
        return result
    }
}