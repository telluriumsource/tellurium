package org.telluriumsource.test.ddt.mapping.mapping

import org.telluriumsource.ui.Const;
import org.telluriumsource.test.ddt.mapping.type.TypeHandlerRegistry
import org.telluriumsource.test.ddt.mapping.FieldSetRegistry

import org.telluriumsource.test.ddt.mapping.type.TypeHandlerRegistryConfigurator
import org.telluriumsource.test.ddt.mapping.FieldSetType
import org.telluriumsource.ui.Const

/**
 * Handle Pipe format file
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class DataFieldSetObjectMapper extends BaseFieldSetObjectMapper{

    private BufferedReader br = null;
    private FileInputStream fis= null;
    
    public DataFieldSetObjectMapper(FieldSetRegistry fsr, TypeHandlerRegistry thr){
//        reader = new PipeDataReader()
//        this.reader = reader
        marshaller = new DefaultObjectUnmarshaller()
        //configure the default type handlers and put them into the registry
        TypeHandlerRegistryConfigurator.configure(thr)
        marshaller.setTypeHandlerRegistry(thr)
        this.registry = fsr
    }

    protected void openFile(String filePath){
    	fis = new FileInputStream (filePath)
        br = new BufferedReader(new InputStreamReader(fis))
    	//if it is an ExcelReader, then we need to invoke the function setupDataStream()
    	if(reader.getReaderType() == Const.EXCEL_TYPE)
    		reader.setupDataStream(fis)
    }

    protected void readData(String data){
        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
        InputStreamReader isr = new InputStreamReader(bais)
        br = new BufferedReader(isr)
    }
    
    protected FieldSetMapResult readNextFieldSet(){
        List<String, String> fieldData = this.readNextLine(br)
        FieldSetType type = this.checkFieldSetType(fieldData)
        //TODO: skip the meta data for the time being, need to refactor this part if we decide to use meta data
        while(type != FieldSetType.REGULAR && type != FieldSetType.END){
            fieldData = this.readNextLine(br)
            type = this.checkFieldSetType(fieldData)
        }
        
        if(type == FieldSetType.END)
            return null
        else
            return this.mapFieldSet(fieldData)
    }

    protected void close(){
        if(br != null)
            br.close()
        if(fis != null)
        	fis.close()
    }
}