package org.telluriumsource.entity

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 7, 2010
 * 
 */
class KeyValuePair {
  public static final String KEY = "key";
  String key;

  public static final String VAL = "val";
  String val;

   public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(KEY, this.key);
    obj.put(VAL, this.val);

    return obj;
  }

  def KeyValuePair(){

  }

  def KeyValuePair(key, val) {
    this.key = key;
    this.val = val;
  }

  def KeyValuePair(Map map){
    this.key = map.get(KEY);
    this.val = map.get(VAL);
  }
}
