package org.telluriumsource.entity

import org.json.simple.JSONArray

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 7, 2010
 * 
 */
class KeyValuePairs {


  public static final String PAIRS = "pairs";
  List<KeyValuePair> pairs;

  def KeyValuePairs(){

  }

  def KeyValuePairs(pairs) {
    this.pairs = pairs;
  }

  def KeyValuePairs(Map map){
    pairs = new ArrayList<KeyValuePair>();
    map?.each{String key, String val ->
      KeyValuePair pair = new KeyValuePair(key, val);
      pairs.add(pair);
    }
  }

  def KeyValuePairs(List<Map> list){
    pairs = new ArrayList<KeyValuePair>();
    list?.each{Map map ->
      KeyValuePair pair = new KeyValuePair(map);
      pairs.add(pair);
    }
  }

  public JSONArray toJSON(){
    JSONArray obj = new JSONArray();
    pairs?.each{ KeyValuePair pair ->
      obj.add(pair.toJSON());
    }
     
    return obj;
  }
}
