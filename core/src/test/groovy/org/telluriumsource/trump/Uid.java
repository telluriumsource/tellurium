package org.telluriumsource.trump;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 13, 2009
 */
public class Uid {
    private static final String UNDEFINED = "undefined";
    private static final String SLASH = "/";

    //generate UID based on the input String
    public static String genUid(String input){
        if(input == null || input.trim().length() == 0){
            return UNDEFINED;
        }

        String[] parts = input.split(SLASH);
        StringBuffer sb = new StringBuffer();
        sb.append("T4");
        for(String part: parts){
            if(part.trim().length() > 0){
                sb.append(part.substring(0,1));
            }
        }

        return sb.toString();
    }
}
