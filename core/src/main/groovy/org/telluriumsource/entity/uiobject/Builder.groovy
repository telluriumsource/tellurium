package org.telluriumsource.entity.uiobject

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Builder extends HashMap<String, String>{

  def Builder() {
  }

  def Builder(Map map) {
    if(map != null && !map.isEmpty()){
        this.putAll(map);
    }
  }

  public JSONObject toJSON() {
    JSONObject obj = new JSONObject();
    if (this.size() > 0) {
      this.each {String key, String val->
          obj.put(key, val);
      }
    }

    return obj;
  }
  
  public void toProperties(Properties properties, String path){
    if(!isEmpty()){
      this.each {String key, String val ->
        properties.setProperty(path + "." + key, val);
      }
    }

  }
}
