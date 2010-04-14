package org.telluriumsource.entity.config

import org.telluriumsource.entity.uiobject.Builder
import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class UiObject {
  public static String BUILDER = "builder";
  Builder builder;

  def UiObject() {
  }

  def UiObject(Map map) {
    if (map != null) {
      Map b = map.get(BUILDER);
      this.builder = new Builder(b);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(BUILDER, this.builder?.toJSON());

    return obj;
  }

  public void getDefault(){
    this.builder = new Builder();
  }

  public void toProperties(Properties properties, String path){
    this.builder.toProperties(properties, path + "." + BUILDER);
  }
}
