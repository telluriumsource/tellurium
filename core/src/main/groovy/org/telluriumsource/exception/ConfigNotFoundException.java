package org.telluriumsource.exception;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 11, 2009
 * 
 */
public class ConfigNotFoundException extends TelluriumException{

  protected static final String ERROR_CODE = "TELLURIUM_CONFIG_NOT_FOUND";
  public ConfigNotFoundException(String message) {
    super(message);
    this.errorCode = ERROR_CODE;
  }
}
