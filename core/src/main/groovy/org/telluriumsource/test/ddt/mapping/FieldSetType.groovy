package org.telluriumsource.test.ddt.mapping
/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
enum FieldSetType {
    END,  //end of file
    EMPTY, //empty line
    REGULAR,   //normal field set
    COMMENT,   //start with ##
    BLOCK_START,  //start with #{
    BLOCK_END,    //start with #}
    META_DATA     //start with #!
}