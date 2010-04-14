package org.telluriumsource.crosscut.trace

import org.telluriumsource.crosscut.log.ConsoleAppender
import org.telluriumsource.crosscut.log.SimpleLogger
import org.telluriumsource.crosscut.log.Logger

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 21, 2009
 * 
 */

public class DefaultExecutionTracer implements ExecutionTracer{

  private Logger logger;

  private ExecutionAnalytics analytics;
  private ExecutionReporter reporter;

  def DefaultExecutionTracer() {
     logger = SimpleLogger.instance;
     logger.addAppender(new ConsoleAppender());
     analytics = new DefaultExecutionAnalytics();
     reporter = analytics;
  }

  public void publish(String testname, long start, long duration) {

    analytics.analysize(new ExecutionTime(testname, start, duration));
    logger.log("Name: ${testname}, start: ${start}, duration: ${duration}ms");
  }

  public void log(String message){
    logger.log(message);
  }
  
  public void warn(String message){
    logger.log("WARNING: " + message);  
  }

   def report() {
      String result = reporter.report();
      logger.log(result);
   }
}