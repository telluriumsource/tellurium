package org.telluriumsource.ut

import org.telluriumsource.test.ddt.mapping.FieldSetRegistry
import org.telluriumsource.test.ddt.mapping.FieldSetParser
import org.telluriumsource.test.ddt.mapping.io.PipeDataReader

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class PipeFieldSetReader_UT extends GroovyTestCase {

    protected String data = """true | 865-692-6000 | tellurium
       false| 865-123-4444 | tellurium selenium test"""

    void testReadData(){
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        assertNotNull(fsr)
        assertEquals(1, fsr.size())
//        FieldSet fs = fsr.getFieldSetByName("fs4googlesearch")

        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(bais));
        PipeDataReader reader = new PipeDataReader()
        List<String, String> list = reader.readLine(br)
        assertNotNull(list)
        assertEquals(3, list.size())
        list = reader.readLine(br)
        assertNotNull(list)
        assertEquals(3, list.size())
        list = reader.readLine(br)
        assertTrue(list.isEmpty())
    }

}