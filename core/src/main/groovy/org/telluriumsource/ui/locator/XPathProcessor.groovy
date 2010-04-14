package org.telluriumsource.ui.locator

/**
 * A Util class to process XPath
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 27, 2009
 *
 */

public class XPathProcessor {
  protected static final String DELIMITER = "/"
  protected static final String SEPARATOR = "::"
  protected static final String LEFT_BRACKET = "["
  protected static final String RIGHT_BRACKET = "]"

  public static String[] splitXPath(String xpath){
    String[] result = null

    if(xpath != null){

      String xp = null
      if(xpath.startsWith("//")){
        xp = xpath.substring(2)
      }else if(xpath.startsWith("/")){
        xp = xpath.substring(1)
      }else{
        xp = xpath
      }
      
      result = xp.split(DELIMITER)
    }

    return result
  }

  public static String popXPath(String xpath) {
    if (xpath != null) {
      StringBuffer sb = new StringBuffer();
      String[] splited = splitXPath(xpath)
      for (int i = 0; i < splited.length - 1; i++) {
        sb.append(DELIMITER).append(splited[i])
      }
      String xp = sb.toString()
      if (xpath.startsWith("//")) {
        return "/" + xp
      }

      return xp
    }

    return null
  }

  public static String lastXPath(String xpath) {
    if (xpath != null) {
      StringBuffer sb = new StringBuffer();
      String[] splited = splitXPath(xpath)

      return splited[splited.length-1]
    }

    return null
  }

  public static String removePrefix(String xpath){
    if(xpath != null){
      String[] splited = xpath.split(SEPARATOR)
      if(splited.length == 2){
        return splited[1]
      }

      return splited[0]
    }
    
    return null
  }

  public static int checkPosition(String xpath) {
    if (xpath != null) {
      //check the pattern such as "//a/td[13]/" and "table[@class='st' and position()=3]"

      def matcher = xpath =~ /(position\(\)=[\d]+|\[[\d]+\])/
      if(matcher != null && matcher.size() > 0) {
//        matcher.each { println it }
        def pos = matcher[0] =~ /[\d]+/
        if(pos != null && pos.size() > 0){
          return Integer.parseInt(pos[0])
        }
      }
    }

    return -1
  }

  public static String addPositionAttribute(String xpath, int pos){
    if(xpath != null){
      if(xpath.endsWith(RIGHT_BRACKET)){
        String xp = xpath.substring(0, xpath.length()-1)
        xp = xp + " and position()=${pos}]"
        return xp
      }else{
        return xpath + "[${pos}]"
      }
    }

    return null
  }

  public static String getTagFromXPath(String xpath){
    if(xpath != null){
       String xp = removePrefix(xpath)
       String[] splited = xp.split(/\[/)
       
       return splited[0].trim()
    }

    return null
  }
}