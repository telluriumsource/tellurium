package org.telluriumsource.trump;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * The tree to hold all selected nodes and it includes the container type UI object generated automatically
 * during the tree insertion stage
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Dec 10, 2008
 */

public class Tree {

    private Node root;

    private XPathComparator xpc = new XPathComparator();

    public void printUI(){
        if(root != null){
            root.printUI();
        }
    }

    public void addElement(Element element){
        //case I: root is null, insert the first node
        if(root == null){
            root = new Node();
            root.setId(element.getUid());
            root.setParent(null);
            root.setXpath(element.getXpath());
            root.setAttributes(element.getAttributes());
        }else{
            //not the first node, need to match element's xpath with current node's relative xpath starting from the root
            //First, need to check the root and get the common xpath
            String common = XPathMatcher.match(root.getXpath(), element.getXpath());
            String leftover = XPathMatcher.remainingXPath(element.getXpath(), common);
            if(root.getXpath().equals(common)){
                //the current node shares the same common xpath as the new node
                //no extra node need to be added for the current node
                //then check current node's children
                if(root.getChildren().size() == 0){
                    //no children, so create a new child
                    if(leftover != null && leftover.length() > 0){
                        //only create the child if there are extra xpath
                        Node child = new Node();
                        child.setId(element.getUid());
                        child.setXpath(XPathMatcher.remainingXPath(element.getXpath(), common));
                        child.setAttributes(element.getAttributes());
                        child.setParent(root);
                        root.addChild(child);
                    }
                }else{
                    //there are children
                    walk(root, element.getUid(), leftover, element.getAttributes());
                }

            }else{
                Node newroot = new Node();
                newroot.setId("root");
                newroot.setXpath(common);
                newroot.setParent(null);
                String newxpath = XPathMatcher.remainingXPath(root.getXpath(), common);
                if(root.getId() != null && root.getId().equals("root"))
                    root.setId(Uid.genUid(newxpath));
                root.setXpath(newxpath);
                root.setParent(newroot);
                newroot.addChild(root);
                root = newroot;

                if (leftover != null && leftover.length() > 0) {
                    //only create the child if there are extra xpath
                    Node child = new Node();
                    child.setId(element.getUid());
                    child.setXpath(XPathMatcher.remainingXPath(element.getXpath(), common));
                    child.setAttributes(element.getAttributes());
                    child.setParent(root);
                    root.addChild(child);
                }
            }
        }
    }

    protected void walk(Node current, String uid, String xpath, Map attribute) {
        if (current.getChildren().size() == 0) {
            //there is no children
            if (xpath.trim().length() > 0) {
                //only create the child if there are extra xpath
                Node child = new Node();
                child.setId(uid);
                child.setXpath(xpath);
                child.setAttributes(attribute);
                child.setParent(current);
                current.addChild(child);
            }
        } else {
            PriorityQueue<XPath> queue = new PriorityQueue<XPath>(16, xpc);
            for (Node node : current.getChildren()) {
                XPath xp = new XPath();
                xp.setXpath(XPathMatcher.match(node.getXpath(), xpath));
                xp.setNode(node);
                queue.add(xp);
            }
            List<XPath> max = new ArrayList<XPath>();
            int maxlen = queue.peek().getXpath().length();
            //need to handle the situation where there is no common xpath
            if (maxlen == 0) {
                //there is no shared common xpath, add the node directly
                Node child = new Node();
                child.setId(uid);
                child.setXpath(xpath);
                child.setAttributes(attribute);
                child.setParent(current);
                current.addChild(child);
            } else {
                //there are shared common xpath
                int length;
                for (int i = 0; i < queue.size(); i++) {
                    XPath mxp = queue.remove();
                    length = mxp.getXpath().length();
                    if (length == maxlen) {
                        max.add(mxp);
                    } else {
                        break;
                    }
                }

                XPath mx = max.get(0);
                String common = mx.getXpath();
                if (mx.getNode().getXpath().equals(common)) {
                    //The xpath includes the common part, that is to say, we need to walk down to the child
                    if (max.size() > 1) {
                        //we need to merge multiple nodes into one
                        for (int i = 1; i < max.size(); i++) {
                            Node cnode = max.get(i).getNode();
                            String left = XPathMatcher.remainingXPath(cnode.getXpath(), common);
                            if (left.length() > 0) {
                                //have more for the left over xpath
                                cnode.setXpath(left);
                                cnode.setParent(mx.getNode());
                                current.removeChild(cnode.getId());
                            } else {
                                for (Node nd : cnode.getChildren()) {
                                    mx.getNode().addChild(nd);
                                }
                                current.removeChild(cnode.getId());
                            }
                        }
                    }
                    walk(mx.getNode(), uid, XPathMatcher.remainingXPath(xpath, common), attribute);
                } else {
                    //need to create extra node
                    Node extra = new Node();
                    extra.setXpath(common);
                    extra.setParent(current);
                    extra.setId(Uid.genUid(common));
                    current.addChild(extra);
                    for (XPath xp : max) {
                        Node cnode = xp.getNode();
                        cnode.setXpath(XPathMatcher.remainingXPath(cnode.getXpath(), common));
                        cnode.setParent(extra);
                        extra.addChild(cnode);
                        current.removeChild(cnode.getId());
                    }

                    Node child = new Node();
                    child.setId(uid);
                    child.setXpath(XPathMatcher.remainingXPath(xpath, common));
                    child.setAttributes(attribute);
                    child.setParent(extra);
                    extra.addChild(child);
                }
            }

        }
    }
}
