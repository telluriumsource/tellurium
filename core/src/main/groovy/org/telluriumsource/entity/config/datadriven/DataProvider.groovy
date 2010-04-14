package org.telluriumsource.entity.config.datadriven

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class DataProvider {
  //specify which data reader you like the data provider to use
  //the valid options include "PipeFileReader", "CSVFileReader" , "ExcelFileReader" at this point
  public static String READER = "reader";
  String reader = "PipeFileReader";

  def DataProvider() {
  }

  def DataProvider(Map map) {
    if (map != null) {
      if (map.get(READER) != null)
        this.reader = map.get(READER);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(READER, this.reader);

    return obj;
  }

  public void toProperties(Properties properties, String path){
    properties.setProperty(path + "." + READER, this.reader);
  }  
}
