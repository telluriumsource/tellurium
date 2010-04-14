package org.telluriumsource.entity

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 24, 2010
 * 
 */

public class CacheUsage {
  public static final String UIM = "uim";
  private String uim;

  public static final String CACHE_HIT = "cacheHit";
  private int cacheHit;

  public static final String TOTAL_CALL = "totalCall";
  private int totalCall;

  public static final String USAGE = "usage";
  private double usage;

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(UIM, this.uim);
    obj.put(CACHE_HIT, this.cacheHit);
    obj.put(TOTAL_CALL, this.totalCall);
    obj.put(USAGE, this.usage);

    return obj;
  }

  public String toString(){
    JSONObject obj = this.toJSON();

    return obj.toString();
  }

  public void parseJSON(String uim, Map map) {
    this.uim = uim;
    this.cacheHit = map.get(CACHE_HIT);
    this.totalCall = map.get(TOTAL_CALL);
    this.usage = map.get(USAGE);
  } 

}