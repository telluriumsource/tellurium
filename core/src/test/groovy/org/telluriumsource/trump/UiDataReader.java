package org.telluriumsource.trump;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.telluriumsource.crosscut.i18n.ResourceBundle;
import org.telluriumsource.crosscut.i18n.IResourceBundle;


/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 13, 2009
 */
public class UiDataReader {

    public final static String UID = "uid";
    public final static String ATTRIBUTES = "attributes";
    public final static String XPATH = "xpath";

    protected final static String FIELD_DELIMITER = "\\|";
	protected final static String ESCAPE_START = "\\Q";
	protected final static String ESCAPE_END = "\\E";

	protected IResourceBundle i18nBundle;


	public UiDataReader(){
    	i18nBundle = new ResourceBundle();
	}
    public BufferedReader getReaderForDate(String data){
        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
        InputStreamReader isr = new InputStreamReader(bais);
        BufferedReader br = new BufferedReader(isr);

        return br;
    }

    public BufferedReader getReaderForFile(String filename) throws FileNotFoundException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filename));
        BufferedReader br = new BufferedReader(isr);

        return br;
    }

    public Map<String, String> readData(BufferedReader reader){
        Map<String, String> dfm = new HashMap<String, String>();

        try{
            String line = reader.readLine();
			//If we reached the end of the file, no more lines, just return.
			if(line == null)
                //should make EOF different from empty line
                return null;
//				return dfm;

			String escapedLine = ESCAPE_START + line + ESCAPE_END;

			String[] fields = escapedLine.split(FIELD_DELIMITER);

            //remove \Q for the first record
			if(fields[0].startsWith(ESCAPE_START))
				fields[0] = fields[0].substring(2);
			//remove \E for the last record
			int fl = fields.length;
			if(fields[fl-1].endsWith(ESCAPE_END))
				fields[fl-1] = fields[fl-1].substring(0, fields[fl-1].length()-2);

//            if(fields.length != 3){
//                throw new RuntimeException(ERROR_DATA_SIZE_EXCEPTION + ": " + fields.length);

//            }

            if(fields.length == 3){
                //only handle valid line
                dfm.put(UID, fields[0].trim());
                dfm.put(ATTRIBUTES, fields[1].trim());
                dfm.put(XPATH, fields[2].trim());
            }

        }catch (IOException e) {
        	i18nBundle.getMessage("UIDataReader.ReadDataException" , new Object[]{e.getMessage()});
		}

		return dfm;
    }
}