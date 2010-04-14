package org.telluriumsource.test.ddt.mapping.io

import org.telluriumsource.framework.Environment;
import org.telluriumsource.ui.Const;
import org.telluriumsource.test.ddt.mapping.DataMappingException
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.ui.Const

/**
 * The implementation for the field set reader with pipe field delimiter
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class PipeDataReader implements DataReader{
		protected final static String FIELD_DELIMITER = "\\|"
		protected final static String ESCAPE_START = "\\Q"
		protected final static String ESCAPE_END = "\\E"
		protected IResourceBundle i18nBundle ;


		public PipeDataReader(){
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
			return Const.PIPE_TYPE
		}

	    //read in a line from a file and then convert them to a String list
	    public List readLine(BufferedReader reader) {

			List<String, String> lst = new ArrayList<String, String>()

	        try {
				String line = reader.readLine()
				//If we reached the end of the file, no more lines, just return.
				if(line == null)
					return lst

	            String escapedLine = ESCAPE_START + line + ESCAPE_END;

	//			String[] fields = line.split(FIELD_DELIMITER)
	            String[] fields = escapedLine.split(FIELD_DELIMITER);
	 			//remove \Q for the first record
				if(fields[0].startsWith(ESCAPE_START))
					fields[0] = fields[0].substring(2);
				//remove \E for the last record
				int fl = fields.length;
				if(fields[fl-1].endsWith(ESCAPE_END))
					fields[fl-1] = fields[fl-1].substring(0, fields[fl-1].length()-2);

	            for(String s : fields){
	                lst.add(s.trim())
	            }

			} catch (IOException e) {
				throw new DataMappingException(i18nBundle.getMessage("DataReader.ReadDataException" , e.getMessage()))
			}

			return lst
		}
}