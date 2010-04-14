package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 19, 2010
 */
public class UidRecognitionException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_UID_RECOGNITION_ERROR";

  public UidRecognitionException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
