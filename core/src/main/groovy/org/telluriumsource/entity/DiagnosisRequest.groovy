package org.telluriumsource.entity

import org.telluriumsource.ui.locator.CompositeLocator
import org.json.simple.JSONObject
import org.telluriumsource.entity.DiagnosisOption

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 17, 2009
 *
 */

public class DiagnosisRequest {

  public static final String REQUEST = "request"
  public static final String TAG = "tag"
  public static final String TEXT = "text"

  public static final String UID = "uid"
  private String uid;

  public static final String PARENT_LOCATOR = "pLocator"
  private String pLocator;

  public static final String ATTRIBUTES = "attributes"
  private Map<String, String> attributes;

  public static final String RETURN_MATCH = "retMatch"
  private boolean retMatch;
  
  public static final String RETURN_HTML = "retHtml"
  private boolean retHtml;

  public static final String RETURN_PARENT = "retParent"
  private boolean retParent;

  public static final String RETURN_CLOSEST = "retClosest"
  private boolean retClosest;

  def DiagnosisRequest(String uid, String pLocator, locator, DiagnosisOption options) {
    this.uid = uid;
    this.pLocator = pLocator;
    if(locator != null && locator instanceof CompositeLocator){
      this.attributes = new HashMap<String, String>();
      this.attributes.put(TAG, locator.tag);
      if(locator.text != null && locator.text.size()){
        this.attributes.put(TEXT, locator.text); 
      }

      if(locator.attributes.size() > 0){
        this.attributes.putAll(locator.attributes);
      }
    }

    this.retMatch = options.retMatch;
    this.retHtml = options.retHtml;
    this.retParent = options.retParent;
    this.retClosest = options.retClosest;
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(UID, this.uid);
    obj.put(PARENT_LOCATOR, this.pLocator);
    JSONObject attrs = new JSONObject();
    if(this.attributes != null && this.attributes.size() > 0){
      this.attributes.each {String key, String val->
        attrs.put(key, val);
      }
    }

    obj.put(ATTRIBUTES, attrs);
    obj.put(RETURN_MATCH, retMatch);
    obj.put(RETURN_HTML, retHtml);
    obj.put(RETURN_PARENT, retParent);
    obj.put(RETURN_CLOSEST, retClosest);
    
//    return obj.toString()
    return obj;
  }

  public String toString(){
    JSONObject obj = this.toJSON();
    
    return obj.toString();
  }
}