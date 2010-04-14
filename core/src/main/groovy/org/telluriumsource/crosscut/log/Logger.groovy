package org.telluriumsource.crosscut.log

import org.telluriumsource.crosscut.log.Appender

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 27, 2009
 * 
 */
public interface Logger {

  public void log(String message)

  public void addAppender(Appender appender)
  
}