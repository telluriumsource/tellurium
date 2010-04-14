package org.telluriumsource.trump;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.telluriumsource.crosscut.i18n.ResourceBundle;
import org.telluriumsource.crosscut.i18n.IResourceBundle;


/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 13, 2009
 */
public class UiParser {
    //parse input file in the following pipe format of
    //   Uid | attributes | xpath
    //where the attributes should be like a map, i.e., in "key : value" pair

    protected static final String FIELD_SEPARATOR = ",";
    protected static final String PAIR_SEPARATOR = ":";

    private UiDataReader reader = new UiDataReader();

    private Tree tree = new Tree();
    protected Map<String, String> parseAttributes(String attributes){
        Map<String, String> attrs = new HashMap<String, String>();
        if(attributes != null && attributes.trim().length() > 0){
            String[] fields = attributes.split(FIELD_SEPARATOR);
            for(String field: fields){
                if(field.trim().length() > 0){
                    String[] pairs = field.split(PAIR_SEPARATOR);
                    if(pairs.length == 2){
                        //only deal with correct format of atrributes
                        attrs.put(pairs[0].trim(), pairs[1].trim());
                    }
                }
            }
        }

        return attrs;
    }

    protected boolean parseLine(BufferedReader br){

        Map<String, String> map = reader.readData(br);
        if(map == null)
            return true;

        if(map.size() > 0){
            Element element = new Element();
            element.setUid(map.get(UiDataReader.UID));
            element.setXpath(map.get(UiDataReader.XPATH));
            Map<String, String> attributes = parseAttributes(map.get(UiDataReader.ATTRIBUTES));
            element.setAttributes(attributes);

            tree.addElement(element);
        }

        return false;
    }

    public void parseData(String data){
        BufferedReader br = reader.getReaderForDate(data);
        while(!parseLine(br)){
        }

        tree.printUI();
    }

    public void parseFile(String filename){
        try {
            BufferedReader br = reader.getReaderForFile(filename);
            while(!parseLine(br)){

            }

            tree.printUI();
        } catch (FileNotFoundException e) {
        	IResourceBundle i18nBundle = new ResourceBundle();
        	System.out.println(i18nBundle.getMessage("UIParser.FileNotFoundException" , new Object[]{filename}));
        }
    }

    public static void main(String[] args){
       if(args != null && args.length == 1){
           UiParser parser = new UiParser();
           parser.parseFile(args[0]);
       }else{
       		IResourceBundle i18nBundle = new ResourceBundle();
       		System.out.println(i18nBundle.getMessage("UIParser.Usage" ));

       }
    }
}

