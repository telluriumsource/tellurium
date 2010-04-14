package org.telluriumsource.ui.locator
/**
 * Meta command passed to Tellurium Engine
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 20, 2009
 * 
 */

public class MetaCmd implements Serializable {

  public static final String UID = "uid"

  //The UID the locator associated with
  protected String uid = ""

  public static final String CACHEABLE = "cacheable"

  //Whether the locator could be cached or not
  protected boolean cacheable = false

  public static final String UNIQUE = "unique"

  //Whether the element/elements matching the locator should be unique or multiple
  protected boolean unique = false

  //force to clean up cache
  public static final String CLEAN = "clean"

  protected boolean clean = false

}