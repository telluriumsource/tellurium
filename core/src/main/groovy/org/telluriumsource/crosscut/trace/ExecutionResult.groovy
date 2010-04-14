package org.telluriumsource.crosscut.trace
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 21, 2009
 * 
 */

public class ExecutionResult {

  private String name;

  private int count;

  private long total;

  def ExecutionResult(name, count, total) {
    this.name = name;
    this.count = count;
    this.total = total;
  }
}