package org.telluriumsource.component.bundle

import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 *
 * Command bundle
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 19, 2009
 * 
 */

public class MacroCmd implements BundleStrategy{
  public static final String BUNDLE = "bundle";


  private List<CmdRequest> bundle = new ArrayList<CmdRequest>();

  private String parentUid = null;
  
  public void addToBundle(CmdRequest newcmd){
    bundle.add(newcmd);
    parentUid = newcmd.getParentUid();
  }

  public List<CmdRequest> getAllCmds(){
    return this.bundle;
  }

  public String getFirstCmdName(){
    if(this.bundle.size() > 0){
      return this.bundle[0].name;
    }
    
    return null;
  }

  public List<CmdRequest> extractAllCmds(){
    List<CmdRequest> result = new ArrayList<CmdRequest>();
    result.addAll(bundle);

    bundle.clear();

    return result;
  }

  public String extractAllAndConvertToJson(){
    JSONArray arr = new JSONArray();
    bundle.each {CmdRequest cmd ->
      JSONObject obj = new JSONObject()
      obj.put(CmdRequest.SEQUENCE, cmd.sequ);
      obj.put(CmdRequest.UID, cmd.uid);
//      obj.put(CmdRequest.LOCATOR_SPECIFIC, cmd.locatorSpecific);
//      obj.put(CmdRequest.RETURN_TYPE, cmd.returnType.toString());
      obj.put(CmdRequest.NAME, cmd.name);
      JSONArray arglist = new JSONArray();
      if(cmd.args != null){
        cmd.args.each {param ->
          arglist.add(param);
        }
      }
      obj.put(CmdRequest.ARGS, arglist);
      arr.add(obj);
    }

    String json = arr.toString();
    bundle.clear();
    
    return json;
  }

  //only append to the bundle if they are in the same UI module
  public boolean shouldAppend(CmdRequest newcmd) {
    if(bundle.size() == 0){
      return true;
    }

    if(parentUid == null)
      return false;

    String puid = newcmd.getParentUid();

    return parentUid.equalsIgnoreCase(puid);  
  }

  public int size(){
    return bundle.size();
  }
}