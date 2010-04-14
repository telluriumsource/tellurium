package org.telluriumsource.exception;

/**
 * Invalid locator exception
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 10, 2009
 *
 */
public class InvalidLocatorException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_INVALID_LOCATOR";

  public InvalidLocatorException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
