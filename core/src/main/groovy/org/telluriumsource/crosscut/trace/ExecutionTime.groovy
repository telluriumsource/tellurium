package org.telluriumsource.crosscut.trace
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 21, 2009
 * 
 */

public class ExecutionTime {

  private String testName;

  private long startTime;

  private long duration;


  def ExecutionTime(testName, startTime, duration) {
    this.testName = testName;
    this.startTime = startTime;
    this.duration = duration;
  }
}