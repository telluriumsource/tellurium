package org.telluriumsource.ui.locator

import static org.telluriumsource.ui.Const.*

/**
 *
 *
 *  A utility class to build jQuery from a given parameter set
 *
 *    @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *  Date: Apr 9, 2009
 *
 */

public class JQueryBuilder {

  protected static final String TEXT_PSEUDO_CLASS = ":te_text"

  protected static final int LENGTH = 64

  protected static def ATTR_BLACK_LIST = ['action']

  protected static boolean inBlackList(String attr){
    return ATTR_BLACK_LIST.contains(attr)
  }

  protected static String checkTag(String tag){
    if(tag != null && tag.trim().length() > 0){
      return tag
    }

    return MATCH_ALL
  }

  protected static String containText(String text){
    String val = text
    if(includeSingleQuote(text)){
      SplitInfo info = getLargestPortion(text)
      val = info.val
    }
    return "${CONTAINS_FILTER}(${val})"
  }

  protected static String attrText(String text){
    String val = text
    if(includeSingleQuote(text)){
      SplitInfo info = getLargestPortion(text)
      val = info.val
      return "${CONTAINS_FILTER}(${val})"
    }

    //need the following custom selector ":te_text()" support
    return "${TEXT_PSEUDO_CLASS}(${val})"
  }

  //starts from zero
  protected static String attrPosition(int index){
    return ":eq(${index})"
  }

  protected static String attrId(String id){
    if(id == null)
       return "[id]"
    
    if (id.startsWith(START_PREFIX)) {
      return "[id^=${id.substring(1)}]"
    } else if (id.startsWith(END_PREFIX)) {
      return "[id\$=${id.substring(1)}]"
    } else if (id.startsWith(ANY_PREFIX)) {
      return "[id*=${id.substring(1)}]"
    } else if (id.startsWith(NOT_PREFIX)) {
      return "[id!=${id.substring(1)}]"
    } else {
      //should never come here
      return "[id]"
    }
//    if(id != null && id.trim().length() > 0){
//      return "${ID_SELECTOR_PREFIX}${id}"
//    }

//    return "[id]"
  }

  protected static String attrClass(String clazz){
    //need to consider the multiple-class syntax, for example, $('.myclass.otherclass')
    //As a CSS selector, the multiple-class syntax of example 3 is supported by all modern
    //web browsers, but not by Internet Explorer versions 6 and below
    if(clazz != null && clazz.trim().length() > 0){
      String[] parts = clazz.split(SPACE)
      if(parts.length == 1){
        //only only 1 class
        
         return attrSingleClass(parts[0])
      }else{
        StringBuffer sb = new StringBuffer()
        for(String part: parts){
//          sb.append("${CLASS_SELECTOR_PREFIX}${part}")
          sb.append(attrSingleClass(part))
        }

        return sb.toString()
      }
    }

//    return "${CLASS_SELECTOR_PREFIX}${clazz}"
    return "[class]"
  }

  protected static String attrSingleClass(String clazz){
    String val = clazz
    if(val == null || val.trim().length() == 0){
      return "[class]"
    }

    if(val.startsWith(START_PREFIX)){
      return "[class^=${val.substring(1)}]"
    }else if(val.startsWith(END_PREFIX)){
      return "[class\$=${val.substring(1)}]"
    }else if(val.startsWith(ANY_PREFIX)){
      return "[class*=${val.substring(1)}]"
    }else if(val.startsWith(NOT_PREFIX)){
//      return "[class!=${val.substring(1)}]"
      return ":not(${CLASS_SELECTOR_PREFIX}${val.substring(1)})"
    }else{
      return "${CLASS_SELECTOR_PREFIX}${val}"
    }
  }

  protected static String attrStyle(String style){
    String val = style
    if(val == null || val.trim().length() == 0){
      return "[style]"
    }

    return ":styles(${val})"
  }

  protected static String attrPairs(String attr, String val){
    if(val == null || val.trim().length() == 0){
      return "[${attr}]"
    }
    
    if(includeSingleQuote(val)){
      SplitInfo info = getLargestPortion(val)
      String result = null
      switch(info.pos){
        case Pos.FIRST:
          result = "[${attr}^=${info.val}]"
          break
        case Pos.MIDDLE:
          result = "[${attr}*=${info.val}]"
          break
        case Pos.LAST:
          result = "[${attr}\$=${info.val}]"
      }

      return result
    }

    if(val.startsWith(START_PREFIX)){
      return "[${attr}^=${val.substring(1)}]"
    }else if(val.startsWith(END_PREFIX)){
      return "[${attr}\$=${val.substring(1)}]"
    }else if(val.startsWith(ANY_PREFIX)){
      return "[${attr}*=${val.substring(1)}]"
    }else if(val.startsWith(NOT_PREFIX)){
      return "[${attr}!=${val.substring(1)}]"
    }else{
      return "[${attr}=${val}]"
    }
  }

  //not really working if we convert String multiple times
  protected static String guardQuote(String val){
    if(val!= null && val.indexOf(SINGLE_QUOTE) > 0){
      return val.replaceAll("'", "\\\'")
//      return "\"${val}\""
    }

    return val
  }

  protected static SplitInfo getLargestPortion(String val){
    String[] parts = val.split(SINGLE_QUOTE)
    int max = 0
    String mval = parts[0].trim()
    for(int i=1; i<parts.length; i++){
      String current = parts[i].trim()
      if(current.length() > mval.length()){
        max = i
        mval = current
      }
    }
    SplitInfo result = new SplitInfo()
    if(max==0){
      result.pos = Pos.FIRST
    }else if(max == parts.length-1){
      result.pos = Pos.LAST
    }else{
      result.pos = Pos.MIDDLE
    }
    result.val = mval

    return result
  }

  protected static boolean includeSingleQuote(String val){
    if(val != null && val.indexOf(SINGLE_QUOTE) > 0)
      return true

    return false
  }

  public static String buildJQuerySelectorWithoutPosition(String tag, String text, Map<String, String> attributes){
    StringBuffer sb = new StringBuffer(LENGTH)
    //put the tag name first
    sb.append(checkTag(tag))
    if(attributes != null && attributes.size() > 0){
      String id = attributes.get(ID)
      if(id != null && id.trim().length() > 0){
        id = id.trim()
        if(id.startsWith(START_PREFIX) || id.startsWith(END_PREFIX) || id.startsWith(ANY_PREFIX) || id.startsWith(NOT_PREFIX)){
               sb.append(attrId(id))
        }else{
        //should not add other attributes if the ID is presented since jQuery will only select the first element for
        // the ID and additional attributes will not help at all
        //also since id is unique, we do not need to include tag here
          return " #${id}"
        }
      }

      String clazz = attributes.get(CLASS)
      if(clazz != null){
          clazz = clazz.trim()
          sb.append(attrClass(clazz))
      }

      String style = attributes.get(STYLE)
      if(style != null){
        style = style.trim()
        sb.append(attrStyle(style))
      }

      Set keys = attributes.keySet()
      for (String key: keys) {
        String val = attributes.get(key)
        if ((!key.equals(ID)) && (!key.equals(CLASS)) && (!key.equals(STYLE)) && (!inBlackList(key))) {
          sb.append(attrPairs(key, val))
        }
      }
    }

    if(text != null && text.trim().length() > 0){
      if(text.startsWith(CONTAIN_PREFIX)){
        sb.append(containText(text.substring(2)))
      }else if(text.startsWith(START_PREFIX) || text.startsWith(END_PREFIX) || text.startsWith(ANY_PREFIX)){
        //TODO: need to refact this to use start, end, any partial match
        sb.append(containText(text.substring(1)))
      }else if(text.startsWith(NOT_PREFIX)){
        sb.append(":not(${containText(text.substring(1))})")
      }else{
        sb.append(attrText(text))
      }
    }
    
    return sb.toString()
  }

  public static String buildJQuerySelector(String tag, String text, int position, boolean direct, Map<String, String> attributes) {
    StringBuffer sb = new StringBuffer(LENGTH)
    if(direct){
      sb.append(CHILD_SEPARATOR)
    }else{
      sb.append(DESCENDANT_SEPARATOR)
    }

    //put the tag name first
    sb.append(checkTag(tag))
    if(attributes != null && attributes.size() > 0){
      String id = attributes.get(ID)
      if(id != null && id.trim().length() > 0){
        id = id.trim()
        if(id.startsWith(START_PREFIX) || id.startsWith(END_PREFIX) || id.startsWith(ANY_PREFIX) || id.startsWith(NOT_PREFIX)){
               sb.append(attrId(id))
        }else{
        //should not add other attributes if the ID is presented since jQuery will only select the first element for
        // the ID and additional attributes will not help at all
        //also since id is unique, we do not need to include tag here
          return " #${id}"
        }
      }

      String clazz = attributes.get(CLASS)
      if(clazz != null){
          clazz = clazz.trim()
          sb.append(attrClass(clazz))
      }

      String style = attributes.get(STYLE)
      if(style != null){
        style = style.trim()
        sb.append(attrStyle(style))
      }

      Set keys = attributes.keySet()
      for (String key: keys) {
        String val = attributes.get(key)
        if ((!key.equals(ID)) && (!key.equals(CLASS)) && (!key.equals(STYLE)) && (!inBlackList(key))) {
          sb.append(attrPairs(key, val))
        }
      }
    }

    if(text != null && text.trim().length() > 0){
      if(text.startsWith(CONTAIN_PREFIX)){
        sb.append(containText(text.substring(2)))
      }else if(text.startsWith(START_PREFIX) || text.startsWith(END_PREFIX) || text.startsWith(ANY_PREFIX)){
        //TODO: need to refact this to use start, end, any partial match
        sb.append(containText(text.substring(1)))
      }else if(text.startsWith(NOT_PREFIX)){
        sb.append(":not(${containText(text.substring(1))})")
      }else{
        sb.append(attrText(text))
      }
    }

    if (position > 0) {
      sb.append(attrPosition(position - 1))
    }

    return sb.toString()
  }

  public static String buildJQuerySelector(String tag, String text, String position, boolean direct, Map<String, String> attributes) {
    StringBuffer sb = new StringBuffer(LENGTH)
    if(direct){
      sb.append(CHILD_SEPARATOR)
    }else{
      sb.append(DESCENDANT_SEPARATOR)
    }

    //put the tag name first
    sb.append(checkTag(tag))
    if(attributes != null && attributes.size() > 0){
      String id = attributes.get(ID)
      if(id != null && id.trim().length() > 0){
        id = id.trim()
        if(id.startsWith(START_PREFIX) || id.startsWith(END_PREFIX) || id.startsWith(ANY_PREFIX) || id.startsWith(NOT_PREFIX)){
               sb.append(attrId(id))
        }else{
        //should not add other attributes if the ID is presented since jQuery will only select the first element for
        // the ID and additional attributes will not help at all
        //also since id is unique, we do not need to include tag here
          return " #${id}"
        }
      }

      String clazz = attributes.get(CLASS)
      if(clazz != null){
          clazz = clazz.trim()
          sb.append(attrClass(clazz))
      }

      String style = attributes.get(STYLE)
      if(style != null){
        style = style.trim()
        sb.append(attrStyle(style))
      }

      Set keys = attributes.keySet()
      for (String key: keys) {
        String val = attributes.get(key)
        if ((!key.equals(ID)) && (!key.equals(CLASS)) && (!key.equals(STYLE)) && (!inBlackList(key))) {
          sb.append(attrPairs(key, val))
        }
      }
    }

    if(text != null && text.trim().length() > 0){
      if(text.startsWith(CONTAIN_PREFIX)){
        sb.append(containText(text.substring(2)))
      }else if(text.startsWith(START_PREFIX) || text.startsWith(END_PREFIX) || text.startsWith(ANY_PREFIX)){
        //TODO: need to refact this to use start, end, any partial match
        sb.append(containText(text.substring(1)))
      }else if(text.startsWith(NOT_PREFIX)){
        sb.append(":not(${containText(text.substring(1))})")
      }else{
        sb.append(attrText(text))
      }
    }

    if(position != null && position.trim().length() > 0){
      int index = Integer.parseInt(position)
      if(index > 0){
        sb.append(attrPosition(index - 1))
      }
    }

    return sb.toString()
  }

  public static String convHeader(String header){
    if (header != null && header.trim().length() > 0){
      String[] xps = XPathProcessor.splitXPath(header)
      StringBuffer sb = new StringBuffer(LENGTH)

      String tag = XPathProcessor.getTagFromXPath(xps[0])

      sb.append(DESCENDANT_SEPARATOR).append(tag)
      for(int i=1; i<xps.length; i++){
        tag = XPathProcessor.getTagFromXPath(xps[i])
        sb.append(CHILD_SEPARATOR).append(tag)
      }

      return sb.toString()
    }
    
    return ""
  }

  public static String convTrailer(String trailer){
    if (trailer != null && trailer.trim().length() > 0){
      String[] xps = XPathProcessor.splitXPath(trailer)
      StringBuffer sb = new StringBuffer(LENGTH)

      String tag = XPathProcessor.getTagFromXPath(xps[0])

      sb.append(CHILD_SEPARATOR).append(tag)
      for(int i=1; i<xps.length; i++){
        tag = XPathProcessor.getTagFromXPath(xps[i])
        sb.append(CHILD_SEPARATOR).append(tag)
      }

      return sb.toString()
    }

    return ""
  }
}