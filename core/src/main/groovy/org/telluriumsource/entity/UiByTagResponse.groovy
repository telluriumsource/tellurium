package org.telluriumsource.entity

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 7, 2010
 * 
 */
class UiByTagResponse {
  //tag name
  String tag;

  Map filters

  //temporally assigned IDs
  String[] tids;

  def UiByTagResponse(){

  }
  
  def UiByTagResponse(tag, filters, tids) {
    this.tag = tag;
    this.filters = filters;
    this.tids = tids;
  }
}
