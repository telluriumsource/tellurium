package org.telluriumsource.ui.locator

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 20, 2009
 * 
 */

public class JQueryProcessor {
  protected static final String JQUERY_PREFIX = "jquery="
  protected static final int LENGTH = 64
  protected static final String JQUERY_SEPARATOR = " "

  public static popLast(String jqs){
    if(jqs == null || jqs.trim().length() == 0)
      return jqs

    String jsel = removePrefix(jqs)
    ContextAwareSplitter splitter = new ContextAwareSplitter()
    String[] splited = splitter.split(jsel)

    StringBuffer sb = new StringBuffer(LENGTH)
    sb.append(JQUERY_PREFIX);
    for(int i=0; i <splited.length-1; i++){
      sb.append(splited[i]).append(JQUERY_SEPARATOR)
    }

    return sb.toString().trim();
  }

  protected static String removePrefix(String jqs){
    String input = jqs.trim()

    if(input.startsWith(JQUERY_PREFIX)){
      return input.substring(JQUERY_PREFIX.length())
    }

    return input
  }

  protected static String addPrefix(String jqs){
    return JQUERY_PREFIX + jqs
  }
}