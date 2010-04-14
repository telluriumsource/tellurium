package org.telluriumsource.ui.locator
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 16, 2009
 * 
 */

public class Token {
  protected static final int LENGTH = 64

  StringBuffer sb

  Token(){
    sb = new StringBuffer(LENGTH)
  }

  public Token append(text){
    sb.append(text)
    return this
  }

  public String toString(){
    return sb.toString()  
  }
}