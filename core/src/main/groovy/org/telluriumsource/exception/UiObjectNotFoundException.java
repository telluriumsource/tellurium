package org.telluriumsource.exception;

/**
 *  UI object not found exception
 *
 *  John.jian.fang@gmail.com
 *
 * Date: Feb 26, 2009
 *
 */

public class UiObjectNotFoundException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_UI_OBJECT_NOT_FOUND";

  public UiObjectNotFoundException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}