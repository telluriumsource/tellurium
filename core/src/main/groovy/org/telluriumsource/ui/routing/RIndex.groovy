package org.telluriumsource.ui.routing

/**
 * Runtime indices for table body elements
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Mar 16, 2010
 * 
 */
class RIndex {
  //for tbody
  String x;

  //for row
  String y;

  //for column
  String z;

  public void setTBody(String x){
    this.x = x;
  }

  public void setRow(String y){
    this.y = y;
  }

  public void setColumn(String z){
    this.z = z;
  }
}
