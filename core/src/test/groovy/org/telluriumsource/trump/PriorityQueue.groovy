package org.telluriumsource.trump
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 1, 2009
 * 
 */

public class PriorityQueue {
  private List A = new ArrayList();

  public int size(){
    return this.A.size();
  }

  public int parent(int val){
    return (val-1)/2;
  }

  public int left(int val){
    return 2*val+1;
  }

  public int right(int val){
    return 2*val+2;
  }

  public void heapify(int index){
    int l = this.left(index);
    int r = this.right(index);

    int largest;
    if(l < this.A.size() && this.A.get(l).key > this.A.get(index).key){
      largest = l;
    }else{
      largest = index;
    }

    if(r < this.A.size() && this.A.get(r).key > this.A.get(largest).key){
      largest = r;
    }

    if(largest != index){
      def tmp = this.A.get(index);
      this.A.putAt(index, this.A.get(largest));
      this.A.putAt(largest, tmp);
      this.heapify(largest); 
    }
  }

  public void insert(elem){
    this.A.push(elem);
    int i = this.A.size()-1;
    while(i>0 && this.A.get(this.parent(i)).key < elem.key){
      this.A.putAt(i, this.A.get(this.parent(i)));
      i=this.parent(i);
    }
    this.A.putAt(i, elem);
  }

  public def extractMax(){
    if(this.A.size() < 1)
      return null;

    def max = this.A.get(0);
    def last = this.A.pop();
    if(this.A.size() > 0){
      this.A.putAt(0, last);
      this.heapify(0);
    }

    return max;
  }

  public void buildHeap(){
    for(int i=this.A.size()/2-1; i>=0; i--){
      this.heapify(i);
    }
  }
}