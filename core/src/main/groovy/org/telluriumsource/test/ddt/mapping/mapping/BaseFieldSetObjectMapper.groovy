package org.telluriumsource.test.ddt.mapping.mapping

import org.telluriumsource.ui.Const
import org.telluriumsource.test.ddt.mapping.FieldSetRegistry
import org.telluriumsource.test.ddt.mapping.io.DataReader
import org.telluriumsource.test.ddt.mapping.FieldSet
import org.telluriumsource.test.ddt.mapping.Field
import org.telluriumsource.test.ddt.mapping.DataMappingException
import org.telluriumsource.test.ddt.mapping.validator.FieldSetValidator
import org.telluriumsource.test.ddt.mapping.FieldSetType
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.framework.Environment

/**
 * The default implemention of the FieldSet Object Mapper
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
abstract class BaseFieldSetObjectMapper implements FieldSetObjectMapper{
    protected final static String COMMENT_PREFIX = "##"
    protected final static String BLOCK_START_PREFIX = "#{"
    protected final static String BLOCK_END_PREFIX = "#}"
    protected final static String META_DATA_PREFIX = "#!"
    protected IResourceBundle i18nBundle;


    protected FieldSetRegistry registry

    protected DataReader reader

    protected ObjectUnmarshaller marshaller

    public BaseFieldSetObjectMapper(){
    	i18nBundle = Environment.instance.myResourceBundle()
    }
    protected boolean isEnd(List fieldData){
         //end of file
        if(fieldData == null || fieldData.size() < 1)
            return true

        return false
    }

    public FieldSetMapResult mapFieldSet(List fieldData){

        if(isEnd(fieldData))
            return null

        //logic to autmoatically detect which field set by its field set identifier
        //or useString the only one that exists in the registry

        //check the fieldset registry first
        FieldSet fs
        if(registry.size() == 1){
            //there is only one field set in the registry
            fs = registry.getUniqueOne()
        }else{
            //check the identifier or action and assume it always to be the first one
            String id = fieldData.get(0)
//            fs = registry.getFieldSetByIdentifier(id)
            fs = registry.getFieldSetByInternalName(id)
        }

        if(fs == null)

            throw new DataMappingException(i18nBundle.getMessage("FieldSetObjectMapper.CannotFindFieldSet" , convString(fieldData)))

        FieldSetValidator.validate(fs, fieldData)
        FieldSetMapResult result = new FieldSetMapResult()
        result.setFieldSetName(fs.getName())

        if(fieldData != null && fieldData.size() > 0){
			//check all the type maps for this field set
            if(fieldData.size() != fs.getFields().size())
                throw new DataMappingException(i18nBundle.getMessage("FieldSetObjectMapper.DataFieldSizeDoNotMatch" , fs.getName()))
            for(int i=0; i<fieldData.size(); i++){
                Field df = fs.getFields().get(i)
                def value = marshaller.unmarshal(df.getType(), fieldData.get(i))
                result.addDataField(df.getName(), value)
            }
		}

		return result
    }

    public FieldSetType checkFieldSetType(List fieldData){
        if(isEnd(fieldData))
            return FieldSetType.END

        String first = fieldData.get(0)

        if(fieldData.size() == 1 && (first.length() <= 0))
            return FieldSetType.EMPTY

        if(first.startsWith(COMMENT_PREFIX))
            return FieldSetType.COMMENT

        if(first.startsWith(BLOCK_START_PREFIX))
            return FieldSetType.BLOCK_START

        if(first.startsWith(BLOCK_END_PREFIX))
            return FieldSetType.BLOCK_END

        if(first.startsWith(META_DATA_PREFIX))
            return FieldSetType.META_DATA

        return FieldSetType.REGULAR
    }

    public List readNextLine(BufferedReader inputReader) {
    	List<String, String> fieldData = null
    	//read the data from the input stream
    	//first check to see if the reader is of excel type if so invoke an alternate function
    	if(reader.getReaderType() == Const.EXCEL_TYPE)
    		fieldData = reader.readLineFromDataStream()
    	else
    		fieldData = reader.readLine(inputReader)

        return fieldData
    }

    protected String convString(List fieldData){
        if(fieldData != null && fieldData.size() > 0){
            StringBuffer sb = new StringBuffer(128)
            sb.append("[")
            sb.append(fieldData.join(", "))
            sb.append("]")

            return sb.toString()
        }else{
            return ""
        }
    }
}