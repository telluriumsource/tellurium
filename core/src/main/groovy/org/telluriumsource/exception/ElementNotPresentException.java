package org.telluriumsource.exception;

import org.telluriumsource.exception.TelluriumException;

/**
 * Element not present exception
 *
 *  John.jian.fang@gmail.com
 *
 * Date: Mar 6, 2009
 *
 */

public class ElementNotPresentException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_ELEMENT_NOT_PRESENT";
  public ElementNotPresentException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
