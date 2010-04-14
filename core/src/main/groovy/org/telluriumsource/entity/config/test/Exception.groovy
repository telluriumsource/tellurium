package org.telluriumsource.entity.config.test

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Exception {
  //whether Tellurium captures the screenshot when exception occurs.
  //Note that the exception is the one thrown by Selenium Server
  //we do not care the test logic errors here
  public static String CAPTURE_SCREEN_SHOT = "captureScreenshot";
  boolean captureScreenshot = false;

  //we may have a series of screenshots, specify the file name pattern here
  //Here the ? will be replaced by the timestamp and you might also want to put
  //file path in the file name pattern
  public static String FILENAME_PATTERN = "filenamePattern";
  String filenamePattern = "Screenshot?.png";

  def Exception() {
  }

  def Exception(Map map) {
    if (map != null) {
      if (map.get(CAPTURE_SCREEN_SHOT) != null)
        this.captureScreenshot = map.get(CAPTURE_SCREEN_SHOT);

      if (map.get(FILENAME_PATTERN) != null)
        this.filenamePattern = map.get(FILENAME_PATTERN);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(CAPTURE_SCREEN_SHOT, this.captureScreenshot);
    obj.put(FILENAME_PATTERN, this.filenamePattern);

    return obj;
  }

  public void toProperties(Properties properties, String path){
    properties.setProperty(path + "." + CAPTURE_SCREEN_SHOT, Boolean.toString(this.captureScreenshot));
    properties.setProperty(path + "." + FILENAME_PATTERN, this.filenamePattern);
  }  
}
