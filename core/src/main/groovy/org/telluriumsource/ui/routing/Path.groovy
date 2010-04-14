package org.telluriumsource.ui.routing

/**
 * RTree Search Path
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 23, 2010
 * 
 * 
 */
class Path extends Stack<String>{

  def Path() {
  }

  def Path(String[] path) {
    if(path != null && path.length > 0){
      for(int i=path.length-1; i>=0; i--){
        this.push(path[i]);
      }
    }
  }
}
