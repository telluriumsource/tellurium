package org.telluriumsource.test.ddt.mapping.io

import org.telluriumsource.ui.Const;
import org.telluriumsource.test.ddt.mapping.DataMappingException
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.ui.Const

/**
 * The implementation for the field set reader with comma-separated values
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 18, 2008
 *
 */
class CSVDataReader implements DataReader{
		protected final static String FIELD_DELIMITER = ","
		//	protected final static String ESCAPE_START = "\\Q"
		//	protected final static String ESCAPE_END = "\\E"
		protected IResourceBundle i18nBundle;

		public CSVDataReader(){
			i18nBundle = Environment.instance.myResourceBundle()
		}

		public void setupDataStream(FileInputStream input)
		{
			//not defined for this class
		}
		public List readLineFromDataStream()
		{
			//not defined for this class
			return null
		}

		public int getReaderType()
		{
			return Const.CSV_TYPE
		}

	    //read in a line from a file and then convert them to a String list
	    public List readLine(BufferedReader reader) {

			List<String, String> lst = new ArrayList<String, String>()

	        try {
				String line = reader.readLine()
				//If we reached the end of the file, no more lines, just return.
				if(line == null)
					return lst

	            String[] fields = line.split(FIELD_DELIMITER)

	            for(String s : fields){
	                lst.add(s.trim())
	            }

			} catch (IOException e) {
				throw new DataMappingException(i18nBundle.getMessage("DataReader.ReadDataException" , e.getMessage()))
			}

			return lst
		}
}