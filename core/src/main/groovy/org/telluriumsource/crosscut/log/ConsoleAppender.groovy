package org.telluriumsource.crosscut.log
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 27, 2009
 * 
 */

public class ConsoleAppender implements Appender {

  public static final String TE = "TE"

  public void listen(String message) {
    println "${TE}: ${message}"
  }

}