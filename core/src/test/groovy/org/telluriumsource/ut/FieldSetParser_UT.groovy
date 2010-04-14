package org.telluriumsource.ut

import org.telluriumsource.test.ddt.mapping.FieldSetParser
import org.telluriumsource.test.ddt.mapping.FieldSetRegistry
import org.telluriumsource.test.ddt.mapping.FieldSet

/**
 * Unit test for Field Set Parser
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class FieldSetParser_UT extends GroovyTestCase{

    void testFieldSet(){
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        assertNotNull(fsr)
        assertEquals(1, fsr.size())
        FieldSet fs = fsr.getFieldSetByName("fs4googlesearch")
        assertNotNull(fs)
        assertEquals(3, fs.getFields().size())
        assertFalse(fs.isHasIdentifier())
        assertFalse(fs.isHasAction())
        assertNotNull(fsr.getFieldSetByInternalName(""))
    }

    void testFieldSetIdentifier(){
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Identifier(name: "field set identifier", value: "google")
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        assertNotNull(fsr)
        assertEquals(1, fsr.size())
        FieldSet fs = fsr.getFieldSetByName("fs4googlesearch")
        assertNotNull(fs)
        assertEquals(4, fs.getFields().size())
        assertTrue(fs.isHasIdentifier())
        assertFalse(fs.isHasAction())
        assertNotNull(fsr.getFieldSetByInternalName("google"))
    }

    void testActionField(){
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Test(value: "googlesearch")
            Identifier(name: "field set identifier", value: "google")
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        assertNotNull(fsr)
        assertEquals(1, fsr.size())
        FieldSet fs = fsr.getFieldSetByName("fs4googlesearch")
        assertNotNull(fs)
        assertEquals(5, fs.getFields().size())
        assertTrue(fs.isHasIdentifier())
        assertTrue(fs.isHasAction())
        assertNotNull(fsr.getFieldSetByInternalName("googlesearch"))
    }
}