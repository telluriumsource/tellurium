package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Mar 16, 2010
 */
public class InvalidIndexRefException extends TelluriumException{
  protected static final String ERROR_CODE = "TELLURIUM_INVALID_INDEX_REF";

  public InvalidIndexRefException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
