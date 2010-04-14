package org.telluriumsource.entity.config

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 8, 2010
 * 
 */
class Config {
  public static String TELLURIUM = "tellurium";
  Tellurium tellurium;

  def Config() {
  }

  def Config(Map map) {
    if(map != null){
      Map m = map.get(TELLURIUM);
      this.tellurium = new Tellurium(m);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(TELLURIUM, this.tellurium?.toJSON());

    return obj;
  }

  public void getDefault(){
    this.tellurium = new Tellurium();
    this.tellurium.getDefault();
  }

  public Properties toProperties(){
    Properties properties = new Properties();
    this.tellurium.toProperties(properties, TELLURIUM);

    return properties;
  }

}
