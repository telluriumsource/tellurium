package org.telluriumsource.exception;

/**
 *
 * @author  Jian Fang (John.Jian.Fang@gmail.com)
 *
 * June 16, 2009
 *
 */
public class InvalidObjectTypeException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_INVALID_OBJECT_TYPE";

  public InvalidObjectTypeException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
