package org.telluriumsource.component.bundle

import org.telluriumsource.dsl.UiID

/**
 * Selenium Command (Request) sending to the Engine
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 19, 2009
 * 
 */

public class CmdRequest extends BaseCmd {

  //The element UID associated with this command
  public static final String UID = "uid";
  private String uid;

  //does this method call require the locator parameter?
//  public static final String LOCATOR_SPECIFIC = "locatorSpecific";
//  private boolean locatorSpecific = true;

  //arguments for this command
  public static final String ARGS = "args";
  private def args;


  public String getParentUid(){
    if(uid != null && uid.trim().length() > 0){
      UiID uid = UiID.convertToUiID(uid);
      String parentUid = uid.pop();

      return parentUid;
    }

    return null;
  }

  public CmdRequest(int sequ, String uid, String name, args) {
    this.name = name;
    this.sequ = sequ;
    this.uid = uid;
//    this.locatorSpecific = locatorSpecific;
//    this.returnType = type;
    this.args = args;
  }
}