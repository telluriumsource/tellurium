package org.telluriumsource.test.ddt.mapping

/**
 * The identifier for a Field Set
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 25, 2008
 *
 */
class IdentifierField extends Field{

    //value which we can use it to determine which fieldset we read from a stream or a file
    private String value

    public void setValue(String value){
        this.value = value
    }

    public String getValue(){
        return this.value
    }

}