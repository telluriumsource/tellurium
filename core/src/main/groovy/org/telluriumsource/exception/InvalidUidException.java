package org.telluriumsource.exception;

/**
 * Invalid UID exception
 *
 * @author John.jian.fang@gmail.com
 *
 * Date: Mar 6, 2009
 *
 */
public class InvalidUidException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_INVALID_UID";

  public InvalidUidException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
