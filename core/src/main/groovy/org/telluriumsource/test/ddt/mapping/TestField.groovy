package org.telluriumsource.test.ddt.mapping
/**
 * Action field and it identified which method the field set should should apply to 
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 * 
 */
class TestField extends Field{
    //value which we can use it to specify which action the field set is defined for. We can use this field together
    //with the IdentifierField to handle more general cases
    private String value

    public void setValue(String value){
        this.value = value
    }

    public String getValue(){
        return this.value
    }
}