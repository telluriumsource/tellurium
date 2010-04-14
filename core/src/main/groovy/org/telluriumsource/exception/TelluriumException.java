package org.telluriumsource.exception;

/**
 * Base class for Tellurium exception hierarchy
 *
 * John.jian.fang@gmail.com
 *
 * Date: Feb 26, 2009
 *
 */

public class TelluriumException extends RuntimeException{
//  protected static final String SEPARATOR = " ";

  protected String errorCode;

  public TelluriumException(String message) {
    super(message);
  }

  public String getErrorCode(){

    return this.errorCode;
  }
    
}
