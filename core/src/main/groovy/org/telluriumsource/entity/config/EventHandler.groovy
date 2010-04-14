package org.telluriumsource.entity.config

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class EventHandler {
  //whether we should check if the UI element is presented
  public static String CHECK_ELEMENT = "checkElement";
  boolean checkElement = false
  
  //wether we add additional events like "mouse over"
  public static String EXTRA_EVENT = "extraEvent";
  boolean extraEvent = false

  def EventHandler() {
  }

  def EventHandler(Map map) {
    if (map != null) {
      if (map.get(CHECK_ELEMENT) != null)
        this.checkElement = map.get(CHECK_ELEMENT);

      if (map.get(EXTRA_EVENT) != null)
        this.extraEvent = map.get(EXTRA_EVENT);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(CHECK_ELEMENT, this.checkElement);
    obj.put(EXTRA_EVENT, this.extraEvent);

    return obj;
  }

  public void toProperties(Properties properties, String path){
    properties.setProperty(path + "." + CHECK_ELEMENT, Boolean.toString(this.checkElement));
    properties.setProperty(path + "." + EXTRA_EVENT, Boolean.toString(this.extraEvent));
  }
}
