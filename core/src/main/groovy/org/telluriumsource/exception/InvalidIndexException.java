package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 22, 2010
 */
public class InvalidIndexException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_INVALID_INDEX";

  public InvalidIndexException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
