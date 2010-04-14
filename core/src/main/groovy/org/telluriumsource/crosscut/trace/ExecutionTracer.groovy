package org.telluriumsource.crosscut.trace

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 21, 2009
 * 
 */
public interface ExecutionTracer extends ExecutionListener, ExecutionReporter{
  
   public void log(String message);

   public void warn(String message);
}