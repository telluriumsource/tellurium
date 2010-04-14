package org.telluriumsource.entity.config

import org.telluriumsource.entity.config.datadriven.DataProvider
import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class DataDriven {
  public String DATA_PROVIDER = "dataprovider";
  DataProvider dataprovider;

  def DataDriven() {
  }

  def DataDriven(Map map) {
    if (map != null) {
      Map m = map.get(DATA_PROVIDER);
      this.dataprovider = new DataProvider(m);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(DATA_PROVIDER, this.dataprovider?.toJSON());
    
    return obj;
  }

  public void getDefault(){
    this.dataprovider = new DataProvider();
  }

  public void toProperties(Properties properties, String path){
   this.dataprovider.toProperties(properties, path + "." + DATA_PROVIDER);
  }
}
