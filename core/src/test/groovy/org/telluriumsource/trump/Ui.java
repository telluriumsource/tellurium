package org.telluriumsource.trump;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 13, 2009
 */
public class Ui {
    private static final String INPUT = "input";
    private static final String TYPE = "type";
    private static final String CHECKBOX = "checkbox";
    private static final String RADIO = "radio";
    private static final String SUBMIT = "submit";
    
    private static Map<String, String> map = new HashMap<String, String>();

    static {
        map.put("divN", "DIV");
        map.put("divY", "Container");
        map.put("aN", "UrlLink");
        map.put("inputN", "InputBox");
        map.put("imgN", "Image");
        map.put("selectN", "Selector");
        map.put("formN", "Form");
        map.put("formY", "Form");
        map.put("tableN", "Table");
        map.put("tableY", "Table");
    }

    public static String getType(String tag, boolean hasChildren){
        return getType(tag, null, hasChildren);
    }

    public static String getType(String tag, Map<String, String> extra, boolean hasChildren){
        String addition = "N";
        if(hasChildren)
            addition = "Y";
        String uitype = map.get(tag + addition);
        if(uitype == null){
            if(hasChildren)
                uitype = "Container";
            else
                uitype = "TextBox";
        }

        if(INPUT.equals(tag) && extra != null){
            String type = extra.get(TYPE);
            if(type != null){
                if(CHECKBOX.equals(type)){
                    uitype = "CheckBox";
                }else if(RADIO.equals(type)){
                    uitype = "RadioButton";
                }else if(SUBMIT.equals(type)){
                    uitype = "SubmitButton";
                }
            }
        }

        return uitype;
    }
}
