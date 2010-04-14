package org.telluriumsource.entity.config.widget

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Module {
//define your widget modules here, for example Dojo or ExtJs
//            included="dojo, extjs"
  public static String INCLUDED = "included";
  String included = "";

  def Module() {
  }

  def Module(Map map) {
    if (map != null) {
      if (map.get(INCLUDED) != null)
        this.included = map.get(INCLUDED);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(INCLUDED, this.included);

    return obj;
  }

  public void toProperties(Properties properties, String path){
     properties.setProperty(path + "." + INCLUDED, this.included);
  }  
}
