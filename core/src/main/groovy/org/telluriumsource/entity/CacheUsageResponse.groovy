package org.telluriumsource.entity

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 24, 2010
 * 
 */

public class CacheUsageResponse {
//  public static final String UI_MODULE_ID = "uim";

  private Map<String, CacheUsage> map;

  public void parseJSON(List list){
    map = new HashMap<String, CacheUsage>();
    list?.each{Map m ->
      String uim = m.keySet().asList().get(0);
      Map u = m.get(uim);
      CacheUsage usage = new CacheUsage();
      usage.parseJSON(uim, u);
      map.put(uim, usage);
    }

  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    map?.each{String uim, CacheUsage usage ->
      obj.put(uim, usage);
    }

    return obj;
  }

  public String toString(){
    JSONObject obj = this.toJSON();

    return obj.toString();
  }

}