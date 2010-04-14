package org.telluriumsource.component.bundle
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 26, 2009
 * 
 */

abstract public class BaseCmd {

  //sequence number for this command
  public static final String SEQUENCE = "sequ";
  protected int sequ;

  //the command name
  public static final String NAME = "name";
  protected String name;

}