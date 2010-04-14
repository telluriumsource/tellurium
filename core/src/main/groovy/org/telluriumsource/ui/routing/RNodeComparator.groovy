package org.telluriumsource.ui.routing

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Mar 15, 2010
 * 
 */
class RNodeComparator implements Comparator<RNode>{

  int compare(RNode first, RNode second) {
    double f1 = first.getFitness();
    double f2 = second.getFitness();
    if(f1 > f2)
      return -1;
    if(f1 == f2)
      return 0;
    else
      return 1;
  }
}
