package org.telluriumsource.ut

import org.telluriumsource.test.ddt.mapping.io.CSVDataReader

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 18, 2008
 * 
 */
class CSVDataReader_UT extends GroovyTestCase {
    protected String data = """true , 865-692-6000 , tellurium
       false, 865-123-4444 , tellurium selenium test"""

    void testReadLine(){
        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(bais));
        CSVDataReader reader = new CSVDataReader()
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