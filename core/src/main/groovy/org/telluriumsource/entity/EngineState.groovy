package org.telluriumsource.entity

import org.json.simple.JSONObject

/**
 * 
 * Class to hold the current state of the Engine
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 19, 2010
 *
 */
public class EngineState {
  
  //flag to decide whether we should cache Ui Module
  public static final String EXPLOIT_ENGINE_CACHE = "cache";
  protected boolean exploitEngineCache = false;

  //Whether to use Tellurium jQuery-based APIs
  public static final String EXPLOIT_TELLURIUM_API = "teApi";
  protected boolean exploitTelluriumApi = false;

  //Whether to use closest match in UI module group locating
  public static final String CLOSEST_MATCH = "relax";
  protected boolean closestMatch = false;

  public boolean isUseClosestMatch(){
    return this.closestMatch;
  }

  public boolean isUseCache(){
    return this.exploitEngineCache;
  }

  public boolean isUseTelluriumApi(){
    return this.exploitTelluriumApi;
  }

  public void useClosestMatch(boolean isUse){
    this.closestMatch = isUse;
  }

  public void useCache(boolean isUse){
    this.exploitEngineCache = isUse;
  }

  public void useTelluriumApi(boolean isUse) {
    this.exploitTelluriumApi = isUse;
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(EXPLOIT_ENGINE_CACHE, this.exploitEngineCache);
    obj.put(EXPLOIT_TELLURIUM_API, this.exploitTelluriumApi);
    obj.put(CLOSEST_MATCH, this.closestMatch);
//    return obj.toString()
    return obj;
  }

  public String toString(){
    JSONObject obj = this.toJSON();
    
    return obj.toString();
  }

  public void parseJson(Map map) {
    this.exploitEngineCache = map.get(EXPLOIT_ENGINE_CACHE);
    this.exploitTelluriumApi = map.get(EXPLOIT_TELLURIUM_API);
    this.closestMatch = map.get(CLOSEST_MATCH);
  }

  public String showMe(){
    
    return "Engine State: [expolitCache: ${this.exploitEngineCache}, useTeApi: ${this.exploitTelluriumApi}, closestMatch: ${this.closestMatch}";
  }
}