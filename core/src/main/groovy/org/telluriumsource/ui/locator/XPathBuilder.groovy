package org.telluriumsource.ui.locator

import static org.telluriumsource.ui.Const.*

/**
 *   A utility class to build xpath from a given parameter set
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class XPathBuilder {

  private static final int TYPICAL_LENGTH = 64

  public static String buildDescendantXPath(String tag, String text, String position, Map<String, String> attributes) {
    if (position != null && position.isInteger())
      return buildXPathWithPrefix(DESCENDANT_PREFIX, tag, text, Integer.parseInt(position), attributes, null)

    return buildXPathWithPrefix(DESCENDANT_PREFIX, tag, text, -1, attributes, null)
  }

  public static String buildChildXPath(String tag, String text, String position, Map<String, String> attributes) {
    if (position != null && position.isInteger())
      return buildXPathWithPrefix(CHILD_PREFIX, tag, text, Integer.parseInt(position), attributes, null)

    return buildXPathWithPrefix(CHILD_PREFIX, tag, text, -1, attributes, null)
  }

  public static String buildXPathWithoutPosition(String tag, String text, Map<String, String> attributes){
    StringBuffer sb = new StringBuffer(TYPICAL_LENGTH)
    if (tag != null && (tag.length() > 0)) {
      //if the tag is available, use it
      sb.append(tag)
    } else {
      //otherwise, use match all
      sb.append(MATCH_ALL)
    }

    List<String> list = new ArrayList<String>()

    String vText = buildText(text)
    if (vText.length() > 0)
      list.add(vText)

    if (attributes != null && (!attributes.isEmpty())) {
      attributes.each {String key, String value ->
        String vAttr = buildAttribute(key, value)
        if (vAttr.length() > 0)
          list.add(vAttr)
      }
    }

    if (!list.isEmpty()) {
      String attr = list.join(" and ")
      sb.append("[").append(attr).append("]")
    }

    return sb.toString()
  }

  private static String buildXPathWithPrefix(String prefix, String tag, String text, int position, Map<String, String> attributes, Closure c) {
    StringBuffer sb = new StringBuffer(TYPICAL_LENGTH)
    sb.append(prefix)
    if (tag != null && (tag.length() > 0)) {
      //if the tag is available, use it
      sb.append(tag)
    } else {
      //otherwise, use match all
      sb.append(MATCH_ALL)
    }

    List<String> list = new ArrayList<String>()

    if (c != null) {
      c(list)
    }

    String vText = buildText(text)
    if (vText.length() > 0)
      list.add(vText)
    String vPosition = buildPosition(position)
    if (vPosition.length() > 0)
      list.add(vPosition)

    if (attributes != null && (!attributes.isEmpty())) {
      attributes.each {String key, String value ->
        String vAttr = buildAttribute(key, value)
        if (vAttr.length() > 0)
          list.add(vAttr)
      }
    }

    if (!list.isEmpty()) {
      String attr = list.join(" and ")
      sb.append("[").append(attr).append("]")
    }

    return sb.toString()
  }

  protected static String internBuildXPath(String tag, String text, int position, boolean direct, Map<String, String> attributes, Closure c) {
    if (direct)
      return buildXPathWithPrefix(CHILD_PATH, tag, text, position, attributes, c)
    else
      return buildXPathWithPrefix(DESCENDANT_OR_SELF_PATH, tag, text, position, attributes, c)
  }

  public static String buildGroupXPath(String tag, String text, String position, boolean direct, Map<String, String> attributes, Closure c) {
    if (position != null && position.isInteger())
      return internBuildXPath(tag, text, Integer.parseInt(position), direct, attributes, c)

    return internBuildXPath(tag, text, -1, direct, attributes, c)
  }

  public static String buildXPath(String tag, String text, String position, Map<String, String> attributes) {
    if (position != null && position.isInteger())
      return internBuildXPath(tag, text, Integer.parseInt(position), false, attributes, null)

    return internBuildXPath(tag, text, -1, false, attributes, null)
  }

  public static String buildOptionalXPath(String tag, String text, String position, boolean direct, Map<String, String> attributes) {
    if (position != null && position.isInteger())
      return internBuildXPath(tag, text, Integer.parseInt(position), direct, attributes, null)

    return internBuildXPath(tag, text, -1, direct, attributes, null)
  }

  public static String buildOptionalXPathWithHeader(String tag, String text, String position, boolean direct, Map<String, String> attributes, String header) {
    if (position != null && position.isInteger())
      return internBuildXPathWithHeader(tag, text, Integer.parseInt(position), direct, attributes, header, null)

    return internBuildXPathWithHeader(tag, text, -1, direct, attributes, header, null)
  }

  public static String buildOptionalXPathWithHeader(String tag, String text, int position, boolean direct, Map<String, String> attributes, String header) {
      return internBuildXPathWithHeader(tag, text, position, direct, attributes, header, null)
  }

  protected static String internBuildXPathWithHeader(String tag, String text, int position, boolean direct, Map<String, String> attributes, String header, Closure c) {
    String appendheader = ""

    if (header != null && header.trim().length() > 0) {

      int inx = header.indexOf("/");
      //need to remove the "/"
      appendheader = header.substring(inx + 1, header.length()) + "/";
    }

    if (direct)
      return buildXPathWithPrefix(CHILD_PATH + appendheader, tag, text, position, attributes, c)
    else
      return buildXPathWithPrefix(DESCENDANT_OR_SELF_PATH + appendheader, tag, text, position, attributes, c)
  }

  public static String buildGroupXPathWithHeader(String tag, String text, String position, boolean direct, Map<String, String> attributes, String header, Closure c) {
    if (position != null && position.isInteger())
      return internBuildXPathWithHeader(tag, text, Integer.parseInt(position), direct, attributes, header, c)

    return internBuildXPathWithHeader(tag, text, -1, direct, attributes, header, c)
  }

  public static String buildXPath(String tag, int position, Map<String, String> attributes) {
    return internBuildXPath(tag, null, position, false, attributes, null)
  }

  public static String buildXPath(String tag, String text, Map<String, String> attributes) {
    return internBuildXPath(tag, text, -1, false, attributes, null)
  }

  public static String buildXPath(String tag, Map<String, String> attributes) {
    return internBuildXPath(tag, null, -1, false, attributes, null)
  }

  protected static String buildPosition(int position) {
    if (position < 1)
      return ""

    return "position()=${position}"
  }

  //For string value, need to use double quota "", otherwise, the value will become
  //invalid for single quota '' once we have the value as "I'm feeling lucky"
  protected static String buildText(String value) {
    if (value == null || (value.trim().length() <= 0))
      return ""

    String trimed = value.trim()
    //TODO: for backward compatiblity, still keep "%%" here, but will remove it later
    if (trimed.startsWith(CONTAIN_PREFIX)){
      String actual = trimed.substring(2)
      return "contains(text(),\"${actual}\")"
    }else if(trimed.startsWith(START_PREFIX)){
      String actual = trimed.substring(1)
      return "starts-with(text(),\"${actual}\")"
    }else if(trimed.startsWith(END_PREFIX) || trimed.startsWith(ANY_PREFIX)) {
      String actual = trimed.substring(1)
      return "contains(text(),\"${actual}\")"
    }else if(trimed.startsWith(NOT_PREFIX)){
      String actual = trimed.substring(1)
      return "not(contains(text(),\"${actual}\"))"
    } else {
      return "normalize-space(text())=normalize-space(\"${trimed}\")"
    }

  }

  protected static String buildAttribute(String name, String value) {
    //must have an attribute name
    if (name == null || (name.trim().length() <= 0))
      return ""

    //indicate has the attribute
    if (value == null || (value.trim().length() <= 0)) {
      return "@${name}"
    }

//    String trimed = value.trim()
    String trimed = value
    
    //if it is a partial match
    if (trimed.startsWith(CONTAIN_PREFIX)) {
      String actual = value.substring(2)
      return "contains(@${name},\"${actual}\")"
    }else if(trimed.startsWith(START_PREFIX)){
      String actual = value.substring(1)
      return "starts-with(@${name},\"${actual}\")"
    }else if(trimed.startsWith(END_PREFIX) || trimed.startsWith(ANY_PREFIX)){
      String actual = value.substring(1)
      return "contains(@${name},\"${actual}\")"
    }else if(trimed.startsWith(NOT_PREFIX)){
      String actual = value.substring(1)
      return "not(contains(@${name},\"${actual}\"))"      
    } else {
      return "@${name}=\"${trimed}\""
    }
  }

  public static String buidXPathFromAttributes(Map<String, String> attributes) {
    StringBuffer sb = new StringBuffer(TYPICAL_LENGTH)

    List<String> list = new ArrayList<String>()

    if (attributes != null && attributes.size() > 0) {
      attributes.each {String key, value ->
        if (TAG.equalsIgnoreCase(key)) {
          String tag = value
          if (tag == null || tag.length() == 0) {
            tag = MATCH_ALL
          }

          sb.append(SEPARATOR).append(tag)
        } else if (TEXT.equalsIgnoreCase(key)) {
          String vText = buildText(value)
          if (vText.length() > 0)
            list.add(vText)
        } else if (POSITION.equalsIgnoreCase(key)) {
          if (value != null && value.isInteger()) {
            value = Integer.parseInt(value)
          }
          String vPosition = buildPosition(value)
          if (vPosition.length() > 0)
            list.add(vPosition)
        } else {
          String vAttr = buildAttribute(key, value)
          if (vAttr.length() > 0)
            list.add(vAttr)

        }
      }
    }

    if (!list.isEmpty()) {
      String attr = list.join(" and ")
      sb.append("[").append(attr).append("]")
    }

    return sb.toString()
  }

  

}