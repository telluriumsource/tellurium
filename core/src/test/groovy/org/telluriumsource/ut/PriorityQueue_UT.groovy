package org.telluriumsource.ut

import org.telluriumsource.trump.PriorityQueue

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 1, 2009
 * 
 */

public class PriorityQueue_UT extends GroovyTestCase{
  def B = [2, 8, 4, 14, 7, 1, 16, 9, 10, 3];
  org.telluriumsource.trump.PriorityQueue queue;

  public void testInsertExtraMax(){
    queue = new PriorityQueue();
    for(int i=0; i<10; i++){
      Data data = new Data();
      data.key = B[i];
      queue.insert(data);
    }

    println("After insertion, the queue is: ")
    for(int i=0; i<queue.size(); i++){
      println(queue.A.get(i).key);
    }

    println("Extract Max: ")
    while(queue.size() > 0){
      Data max = queue.extractMax();
      println(max.key);
    }
  }

  public void testHeapify(){
    ArrayList A = new ArrayList();
    for(int i=0; i<10; i++){
      Data data = new Data();
      data.key = B[i];
      A.push(data);
    }
    
    queue = new PriorityQueue();
    queue.A = A.toArray();
    queue.buildHeap();
    println("After buildHeap, the queue is: ")
    for(int i=0; i<queue.size(); i++){
      println(queue.A.get(i).key);
    }

    println("Extract Max: ")
    while(queue.size() > 0){
      Data max = queue.extractMax();
      println(max.key);
    }

    A.pop();
    queue.A = A.toArray();
    queue.buildHeap();
    println("After buildHeap, the queue is: ")
    for(int i=0; i<queue.size(); i++){
      println(queue.A.get(i).key);
    }

    println("Extract Max: ")
    while(queue.size() > 0){
      Data max = queue.extractMax();
      println(max.key);
    }       
  }

}