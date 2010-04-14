package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 20, 2010
 */
public class EngineNotConnectedException extends TelluriumException {
  protected static final String ERROR_CODE = "TELLURIUM_ENGINE_NOT_CONNECTED";

  public EngineNotConnectedException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }    
}
