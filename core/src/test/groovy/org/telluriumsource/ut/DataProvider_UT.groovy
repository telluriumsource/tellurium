package org.telluriumsource.ut

import org.telluriumsource.framework.config.TelluriumConfigurator;
import org.telluriumsource.framework.config.TelluriumConfiguratorMetaClass;
import org.telluriumsource.test.ddt.DataProvider
import org.telluriumsource.test.ddt.mapping.FieldSetParser
import org.telluriumsource.test.ddt.mapping.FieldSetRegistry
import org.telluriumsource.test.ddt.mapping.mapping.FieldSetMapResult
import org.telluriumsource.test.ddt.mapping.type.TypeHandlerRegistry
import org.telluriumsource.test.ddt.mapping.type.TypeHandlerRegistryConfigurator

/**
 *
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class DataProvider_UT extends GroovyTestCase{
    protected TypeHandlerRegistry thr  = new TypeHandlerRegistry()
    protected FieldSetRegistry fsr = new FieldSetRegistry()

    protected DataProvider dataProvider = new DataProvider(fsr, thr)

    protected FieldSetParser fs = new FieldSetParser(fsr)

    public void setUp(){
        TypeHandlerRegistryConfigurator.addCustomTypeHandler(thr, "phoneNumber", "org.telluriumsource.ut.PhoneNumberTypeHandler")

        fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        def registry = GroovySystem.metaClassRegistry
        registry.setMetaClass(TelluriumConfigurator, new TelluriumConfiguratorMetaClass())
    }

    public void testFetchData(){
        String data = """
            true | 865-692-6000 | tellurium groovy
            false| 865-123-4444 | tellurium selenium.test
        """
//        dataprovider.start("src/example/test/ddt/googlesearchpullinput.txt")
        dataProvider.useString(data)
        
        FieldSetMapResult result = dataProvider.nextFieldSet()
        assertNotNull(result)
        assertFalse(result.isEmpty())
        boolean var1 = dataProvider.bind("fs4googlesearch.regularSearch")
        def var2 = dataProvider.bind("fs4googlesearch.phoneNumber")
        String var3 = dataProvider.bind("fs4googlesearch.input")
        assertTrue(var1)
        assertEquals("8656926000", var2)
        assertEquals("tellurium groovy", var3)
        result = dataProvider.nextFieldSet()
        var1 = dataProvider.bind("regularSearch")
        var2 = dataProvider.bind("fs4googlesearch.phoneNumber")
        var3 = dataProvider.bind("fs4googlesearch.input")
        assertFalse(var1)
        assertEquals("8651234444", var2)
        assertEquals("tellurium selenium.test", var3)
        assertNotNull(result)
        assertFalse(result.isEmpty())
        result = dataProvider.nextFieldSet()
        assertNull(result)
        dataProvider.stop()
    }

    public void testFetchExcelData(){   
    	TelluriumConfigurator telluriumConfigurator = new TelluriumConfigurator()
        telluriumConfigurator.parse("config/TelluriumConfigForExcelReader.groovy")
        
    	dataProvider.useFile(ClassLoader.getSystemResource("data/excelDataReaderTest.xls").getFile())
        
        FieldSetMapResult result = dataProvider.nextFieldSet()
        assertNotNull(result)
        assertFalse(result.isEmpty())
        boolean var1 = dataProvider.bind("fs4googlesearch.regularSearch")
        def var2 = dataProvider.bind("fs4googlesearch.phoneNumber")
        String var3 = dataProvider.bind("fs4googlesearch.input")
        assertTrue(var1)
        assertEquals("8656926000", var2)
        assertEquals("tellurium", var3)
        result = dataProvider.nextFieldSet()
        var1 = dataProvider.bind("regularSearch")
        var2 = dataProvider.bind("fs4googlesearch.phoneNumber")
        var3 = dataProvider.bind("fs4googlesearch.input")
        assertFalse(var1)
        assertEquals("8651234444", var2)
        assertEquals("tellurium selenium test", var3)
        assertNotNull(result)
        assertFalse(result.isEmpty())
        result = dataProvider.nextFieldSet()
        assertNull(result)
        dataProvider.stop()
    }

    public void tearDown(){

    }

}