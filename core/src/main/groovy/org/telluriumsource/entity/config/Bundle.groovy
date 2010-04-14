package org.telluriumsource.entity.config

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Bundle {
  public static String MAX_MACRO_CMD = "maxMacroCmd";
  int maxMacroCmd = 5;
  
  //false means  maxMacroCmd = 1
  public static String USE_MACRO_COMMAND = "useMacroCommand";
  boolean useMacroCommand = true;

  def Bundle() {
  }

  def Bundle(Map map) {
    if (map != null) {
      if (map.get(MAX_MACRO_CMD) != null)
        this.maxMacroCmd = map.get(MAX_MACRO_CMD);

      if (map.get(USE_MACRO_COMMAND) != null)
        this.useMacroCommand = map.get(USE_MACRO_COMMAND);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(MAX_MACRO_CMD, this.maxMacroCmd);
    obj.put(USE_MACRO_COMMAND, this.useMacroCommand);

    return obj;
  }
  
  public void toProperties(Properties properties, String path){
    properties.setProperty(path + "." + MAX_MACRO_CMD, Integer.toString(this.maxMacroCmd));
    properties.setProperty(path + "." + USE_MACRO_COMMAND, Boolean.toString(this.useMacroCommand));
  }
}
