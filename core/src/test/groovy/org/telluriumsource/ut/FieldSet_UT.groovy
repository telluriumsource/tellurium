package org.telluriumsource.ut

import org.telluriumsource.test.ddt.mapping.FieldSetRegistry
import org.telluriumsource.test.ddt.mapping.FieldSetParser
import org.telluriumsource.test.ddt.mapping.FieldSet

/**
 * Unit tests for Field Set
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 30, 2008
 *
 */
class FieldSet_UT extends GroovyTestCase {

    void testNoIdentifierNoAction(){
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        FieldSet fs = fsr.getFieldSetByName("fs4googlesearch")
 
        assertEquals("", fs.getInternalName())
    }

    void testHasIdentifierNoAction(){
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Identifier(name: "id", value: "123")
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        FieldSet fs = fsr.getFieldSetByName("fs4googlesearch")

        assertEquals("123", fs.getInternalName())
    }

    void testNoIdentifierHasAction(){
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Test(value: "search")
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        FieldSet fs = fsr.getFieldSetByName("fs4googlesearch")

        assertEquals("search", fs.getInternalName())
    }

    void testHasIdentifierHasAction() {
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search") {
            Identifier(name: "id", value: "123")
            Test(name: "action", value: "search")
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        FieldSet fs = fsr.getFieldSetByName("fs4googlesearch")

        assertEquals("123", fs.getInternalName())
    }

    void testHasActionHasIdentifier() {
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search") {
            Test(value: "search")
            Identifier(name: "id", value: "123")
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        FieldSet fs = fsr.getFieldSetByName("fs4googlesearch")

        assertEquals("search", fs.getInternalName())
    }
}