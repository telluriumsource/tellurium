package org.telluriumsource.trump;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A node for the tree
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Dec 10, 2008
 */
public class Node {
    public static final String TAG = "tag";
    public static final int LENGTH = 64;

    private String id;

    //relative xpath
    private String xpath;

    private Map<String, String> attributes = new HashMap<String, String>();

    private Node parent;

    private LinkedList<Node> children = new LinkedList<Node>();

    private int getLevel(){
        int level = 0;
        Node current = this;
        while(current.parent != null){
            level++;
            current = current.parent;
        }

        return level;
    }

    public void printUI(){
        boolean hasChildren = false;
        if(children.size() > 0)
            hasChildren = true;
        StringBuffer sb = new StringBuffer(LENGTH);
        //get the current level of the node so that we can do pretty print
        int level = this.getLevel();
        for(int i=0; i<level; i++){
            sb.append("  ");
        }
        sb.append(Ui.getType(attributes.get(TAG), attributes, hasChildren)).append("(UID: '").append(id).append("', clocator: [");
        if(attributes.size() == 0){
            sb.append(":");
        }else{
            int count = 0;
            for(String key: attributes.keySet()){
                if(++count > 1)
                    sb.append(",");
                sb.append(key).append(":").append("'").append(attributes.get(key)).append("'");
            }
        }

        sb.append("]");
        //comment this line out if you do not want xpath to display
        sb.append("[xpath: ").append(xpath).append("]");
        sb.append(")");
        if(hasChildren)
            sb.append("{");
        System.out.println(sb.toString());
        if(hasChildren){
            for(Node node: children){
                node.printUI();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
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

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    public boolean isEmpty(){
        if(children != null && children.size() > 0)
            return true;
        
        return false;
    }

    public void addChild(Node child){
        children.add(child);
    }
    
    public void removeChild(String uid){
        Node child = findChild(uid);
        if(child != null){
            children.remove(child);
        }
    }

    public Node findChild(String uid){
        for(Node current: children){
            if(current.getId().equals(uid)){
                return current;
            }
        }

        return null;
    }
}
