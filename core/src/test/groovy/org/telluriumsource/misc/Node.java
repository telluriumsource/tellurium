package org.telluriumsource.misc;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Data structure for a Node in a trie, or prefix tree
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 21, 2009
 */
public class Node {
    public static final int LENGTH = 64;

    //hold the String value for this node
    private String elem;

    //the level of this node in the Trie tree
    private int level;

    //pointer to its parent
    private Node parent;

    //child nodes 
    private LinkedList<Node> children = new LinkedList<Node>();

    public String getElem() {
        return elem;
    }

    public void setElem(String elem) {
        this.elem = elem;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public LinkedList<Node> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<Node> children) {
        this.children = children;
    }

    public void addChild(Node child){
        children.add(child);
    }

    public void removeChild(Node child){
        children.remove(child);
    }

    public int getChildrenSize(){
        return this.children.size();
    }

    public void checkLevel(){
        if(this.parent == null)
            this.level = 0;
        else
            this.level = this.parent.getLevel() + 1;
        if(this.children.size() > 0){
            for(Node node: children){
                node.checkLevel();
            }
        }
    }

    //check the level and also store the level to a priority queue
    public void checkAndIndexLevel(PriorityQueue<Node> queue){
        if(this.parent == null)
            this.level = 0;
        else
            this.level = this.parent.getLevel() + 1;
        queue.add(this);
        if(this.children.size() > 0){
            for(Node node: children){
                node.checkAndIndexLevel(queue);
            }
        }
    }

    public String getFullWord(){
        if(parent == null){
            return this.elem;
        }

        return parent.getFullWord() + this.elem;
    }

    public void printMe(){
        boolean hasChildren = false;
        if(children.size() > 0)
            hasChildren = true;
        StringBuffer sb = new StringBuffer(LENGTH);
        for(int i=0; i<this.level; i++){
            sb.append("  ");
        }
        sb.append(this.elem);
        if(hasChildren)
            sb.append("{");
        System.out.println(sb.toString());
        if(hasChildren){
            for(Node node: children){
                node.printMe();
            }
        }
        if(hasChildren){
            StringBuffer indent = new StringBuffer(LENGTH);
            for(int i=0; i<level; i++){
                indent.append("  ");
            }
            indent.append("}");
            System.out.println(indent.toString());
        }
    }

}
