package org.telluriumsource.entity.config

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 9, 2010
 * 
 */
class I18N {
  public static String LOCALE = "locale";
  String locale = "en_US";
  
  def I18N() {
  }

  def I18N(Map map) {
    if (map != null) {
      if (map.get(LOCALE) != null)
        this.locale = map.get(LOCALE);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(LOCALE, this.locale);

    return obj;
  }

  public void toProperties(Properties properties, String path){
    properties.setProperty(path + "." + LOCALE, this.locale);
  }
}
