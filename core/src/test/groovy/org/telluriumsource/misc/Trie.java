package org.telluriumsource.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Data structure for a trie, or prefix tree
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 21, 2009
 *
 */
public class Trie {
    //the root of the Trie, or Prefix Tree
    private Node root;
    private PriorityQueue<Node> queue = new PriorityQueue<Node>(64, new NodeComparator());

    public void buildTree(String[] dictionary){
        if(dictionary != null && dictionary.length > 0){
            for(String word: dictionary){
                this.insert(word);
            }
        }
    }

    public void insert(String word){
        if(this.root == null){
            //If it is the first time to insert an word to the Tire
            this.root = new Node();
            //root is an empty String, more like a logic node
            this.root.setElem("");
            this.root.setLevel(0);
            this.root.setParent(null);

            //add the word as the child of the root node
            Node child = new Node();
            child.setElem(word);
            child.setParent(this.root);
            this.root.addChild(child);
        }else{
            //not the first node, need to walk all the way down to find a place to insert
            this.walk(this.root, word);
        }
    }

    protected void walk(Node current, String word) {
        //look at current node's children
        if(current.getChildrenSize() == 0){
            //no child yet, add itself as the first child
            Node child = new Node();
            child.setElem(word);
            child.setParent(current);
            current.addChild(child);
        }else{
            //there are children for the current node
            //check if the new String is a prefix of a set of children
            List<Node> common = new ArrayList<Node>();
            for(Node node: current.getChildren()){
                if(node.getElem().startsWith(word)){
                    common.add(node);
                }
            }
            //if the new String is indeed a prefix of a set of children
            if(common.size() > 0){
                Node shared = new Node();
                shared.setElem(word);
                shared.setParent(current);
                for(Node node: common){
                    //assume no duplication in the dictionary, otherwise, need to consider the empty string case for a child
                    node.setElem(node.getElem().substring(word.length()));
                    node.setParent(shared);
                    shared.addChild(node);
                    current.removeChild(node);
                }
                current.addChild(shared);
            }else{
                //no common prefix available, then check if the child is a prefix of the input String
                boolean found = false;
                Node next = null;
                for(Node node: current.getChildren()){
                    if(word.startsWith(node.getElem())){
                        found = true;
                        next = node;
                        break;
                    }
                }
                if(found){
                    //not a duplication, otherwise, do nothing
                    if(word.length() != next.getElem().length()){
                        String leftover = word.substring(next.getElem().length());
                        walk(next, leftover);
                    }
                }else{
                    //not found, need to create a new node a the child of the current node
                    Node child = new Node();
                    child.setParent(current);
                    child.setElem(word);
                    current.addChild(child);
                }
            }
        }
    }

    public void checkLevel(){
        if(root != null){
            root.checkLevel();
        }
    }

    public void checkAndIndexLevel(){
        if(root != null){
            root.checkAndIndexLevel(this.queue);
        }
    }

    //pop up the deepest node 
    public Node getDeepestNode(){
        return this.queue.remove();
    }

    public void printMe(){
        if(this.root != null){
            System.out.println("---------------------------- Trie/Prefix Tree ----------------------------\n");
            this.root.printMe();
            System.out.println("--------------------------------------------------------------------------\n");
        }        
    }
}
