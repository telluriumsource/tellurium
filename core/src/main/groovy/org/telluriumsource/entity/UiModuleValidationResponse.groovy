package org.telluriumsource.entity

import org.telluriumsource.entity.RelaxDetail
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.framework.Environment
import org.telluriumsource.ui.locator.CompositeLocator
import org.json.simple.JSONObject
import org.json.simple.JSONArray

/**
 * The response object Passing back from Engine when do UI module locating and caching
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 31, 2009
 * 
 */

public class UiModuleValidationResponse {
    protected IResourceBundle i18nBundle;
  
    //ID for the UI module
    public static String ID = "id";
    private String id = null;

    //Successfully found or not
    public static String FOUND = "found";
    private boolean found = false;

    //whether this the UI module used closest Match or not
    public static String RELAXED = "relaxed";
    private boolean relaxed = false;

    //match count
    public static String MATCHCOUNT = "matches";
    private int matches = 0;

    //scaled match score (0-100)
    public static String SCORE = "score";
    private float score = 0.0;

    public static String RELAXDETAIL = "relaxDetail";
    //details for the relax, i.e., closest match
    public static String RELAXDETAILS = "relaxDetails";
    private List<RelaxDetail> relaxDetails = null;


    def UiModuleValidationResponse(){
      
    }

    def UiModuleValidationResponse(Map map){
      this.id = map.get(ID);
      this.found = map.get(FOUND);
      this.relaxed = map.get(RELAXED);
      this.matches = map.get(MATCHCOUNT);
      this.score = map.get(SCORE);
      this.relaxDetails = new ArrayList();
      List lst = map.get(RELAXDETAILS);
      if(lst != null && lst.size() > 0){
        for(int i=0; i<lst.size(); i++){
          Map rdm = lst.get(i);
          RelaxDetail rd = new RelaxDetail(rdm);
          this.relaxDetails.add(rd);
        }
      }
    }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(ID, this.id);
    obj.put(FOUND, this.found);
    obj.put(RELAXED, relaxed);
    obj.put(MATCHCOUNT, this.matches);
    obj.put(SCORE, this.score);
    JSONArray ar = new JSONArray();
    this.relaxDetails?.each { RelaxDetail rd ->
      ar.add(rd.toJSON());
    }
    obj.put(RELAXDETAILS, ar);

    return obj;
  }

  public String toString(){
    JSONObject obj = this.toJSON();

    return obj.toString();
  }

  public void showMe() {
    IResourceBundle i18nBundle  = Environment.instance.myResourceBundle();

    println i18nBundle.getMessage("UiModuleValidationResponse.ValidationResult" , id);

    println("\n-------------------------------------------------------\n");

    println i18nBundle.getMessage("UiModuleValidationResponse.Found" , found);
    println i18nBundle.getMessage("UiModuleValidationResponse.Relaxed" , relaxed);
    println i18nBundle.getMessage("UiModuleValidationResponse.MatchCount" , matches);
    println i18nBundle.getMessage("UiModuleValidationResponse.MatchScore" , score);

    if(relaxDetails != null && relaxDetails.size() > 0){
      println i18nBundle.getMessage("UiModuleValidationResponse.RelaxDetails");
      relaxDetails.each {RelaxDetail rd ->
        println i18nBundle.getMessage("UiModuleValidationResponse.Element" , rd.uid);
        CompositeLocator locator = rd.locator;
        String cl = "<>";
        if(locator != null){
          cl = locator.toHTML(true);
        }
        println i18nBundle.getMessage("UiModuleValidationResponse.CompositeLocator" , cl);
        println i18nBundle.getMessage("UiModuleValidationResponse.Html" , rd.html);
        println("\n");
      }
    }
    
    println("\n-------------------------------------------------------\n")
  }

}