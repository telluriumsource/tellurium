package org.telluriumsource.entity

import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle;


/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 17, 2009
 *
 */

public class DiagnosisResponse {

  public static final String UID = "uid"
  private String uid;

  public static final String COUNT = "count"
  private int count;

  public static final String MATCHES = "matches"
  private ArrayList<String> matches;

  public static final String PARENTS = "parents"
  private ArrayList<String> parents;

  public static final String CLOSEST = "closest"
  private ArrayList<String> closest;

  private static final String HTML = "html"
  private String html;

  def DiagnosisResponse() {
  }

  def DiagnosisResponse(Map map) {
    this.uid = map.get(UID);
    this.count = map.get(COUNT);
    this.matches = map.get(MATCHES);
    this.parents = map.get(PARENTS);
    this.closest = map.get(CLOSEST);
    this.html = map.get(HTML);
  }

  public void showMe() {
    IResourceBundle i18nBundle  = Environment.instance.myResourceBundle();

    println i18nBundle.getMessage("DiagnosisResponse.DiagnosisResult" , uid);

    println("\n-------------------------------------------------------\n");

    println i18nBundle.getMessage("DiagnosisResponse.MatchingCount" , count);
    if(matches != null && matches.size() > 0){
      println i18nBundle.getMessage("DiagnosisResponse.MatchingElement");
      int i = 0;
      matches.each {String elem ->
        i++;
        println i18nBundle.getMessage("DiagnosisResponse.Element" , i);
        println(elem + "\n");
      }
    }

    if(parents != null && parents.size() > 0){
      println("\n\tParents: \n");
      int j = 0;
      parents.each {String elem ->
        j++;
        println i18nBundle.getMessage("DiagnosisResponse.Parent" , j);
        println(elem + "\n");
      }
    }

    if(closest != null && closest.size() > 0){
      println i18nBundle.getMessage("DiagnosisResponse.Closest");
      println("\n\tClosest: \n");
      int k = 0;

      closest.each {String elem ->
        k++;
        println i18nBundle.getMessage("DiagnosisResponse.ClosestElement" , k);
        println(elem + "\n");
      }
    }

    if(html != null){
      println i18nBundle.getMessage("DiagnosisResponse.HtmlSource");
      println(html);
      println("\n");
    }
    println("\n-------------------------------------------------------\n");
  }
}