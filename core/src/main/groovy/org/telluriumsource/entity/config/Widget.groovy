package org.telluriumsource.entity.config

import org.telluriumsource.entity.config.widget.Module
import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Widget {
  public static String MODULE = "module";
  Module module;

  def Widget() {
  }

  def Widget(Map map) {
    if (map != null) {
      Map m = map.get(MODULE);
      this.module = new Module(m);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(MODULE, this.module?.toJSON());

    return obj;
  }

  public void getDefault(){
    this.module = new Module();
  }

  public void toProperties(Properties properties, String path){
    this.module.toProperties(properties, path + "." + MODULE);
  }
}
