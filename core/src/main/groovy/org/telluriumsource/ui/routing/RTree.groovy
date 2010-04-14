package org.telluriumsource.ui.routing

import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.object.TextBox
import org.telluriumsource.udl.ListMetaData
import org.telluriumsource.exception.InvalidIndexException
import org.telluriumsource.framework.Environment

/**
 * Routing Tree
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 22, 2010
 * 
 */
class RTree {
  //RTree holds the tree nodes for routing search
  RNode root;

  //ID to UI template mapping
  Map<String, UiObject> indices;

  String[] EMPTY_PATH = [];
  String[] ROOT_PATH = ["all"];
  String[] ODD_PATH = ["all", "odd"];
  String[] EVEN_PATH = ["all", "even"];
  String[] INDEX_LIST = ["all", "odd", "even", "any", "first", "last"];
  
  protected boolean isInList(String str, String[] list){
    boolean result = false;
    list.each {String elem ->
      if(elem.equals(str)){
        result = true;
      }
    }

    return result;
  }

  public boolean isIndex(String key){
    return (key =~ /^\d+$/ || isInList(key, INDEX_LIST));
  }

  public boolean isId(String key){
    return !isIndex(key);
  }
  
  public void createIndex(String key, UiObject obj){
    this.indices.put(key, obj);
  }

  void insert(UiObject object) {
    ListMetaData meta = object.metaData;
    String index = meta.getIndex().getValue();
    if("all".equalsIgnoreCase(index)){
      this.root.objectRef = object;
      this.root.presented = true;
    }else if("odd".equalsIgnoreCase(index)){
      RNode oddNode = this.root.findChild("odd");
      oddNode.presented = true;
      oddNode.objectRef = object;
    }else if("even".equalsIgnoreCase(index)){
      RNode evenNode = this.root.findChild("even");
      evenNode.presented = true;
      evenNode.objectRef = object;
    }else if("last".equalsIgnoreCase(index)){
      RNode last = this.root.findChild("last");
      if(last == null){
        last = new RNode("last", this.root, object, true);
        this.root.addChild(last);
      }
    }else if("any".equalsIgnoreCase(index)){
      //do nothing
    }else if("first".equalsIgnoreCase(index)){
      RNode oddNode = this.root.findChild("odd");
      RNode first = oddNode.findChild("1");
      if(first == null){
        first = new RNode("1", oddNode, object, true);
        oddNode.addChild(first);
      }
    }else if(index =~ /^\d+$/){
      int inx = Integer.parseInt(index);
      if((inx % 2) == 1){
        RNode oddNode = this.root.findChild("odd");
        RNode inode = oddNode.findChild(index);
        if(inode == null){
          inode = new RNode(index, oddNode, object, true);
          oddNode.addChild(inode);
        }
      }else{
        RNode evenNode = this.root.findChild("even");
        RNode inode = evenNode.findChild(index);
        if(inode == null){
          inode = new RNode(index, evenNode, object, true);
          evenNode.addChild(inode);
        }
      }
    }else{
       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    }
  }
  
  //setup the routing tree structure before actually insert the UI object into the tree
  void preBuild() {
    TextBox defaultUi = new TextBox();
    ListMetaData meta = new ListMetaData("defaultUi", "all");
    defaultUi.metaData = meta;
    RNode allNode = new RNode("all", null, defaultUi, true);
    this.root = allNode;
    RNode oddNode = new RNode('odd', allNode, defaultUi, false);
    this.root.addChild(oddNode);
    RNode evenNode = new RNode('even', allNode, defaultUi, false);
    this.root.addChild(evenNode);
  }

  UiObject route(String key) {
    UiObject object = this.indices.get(key);
    if(object == null){
      if("first".equalsIgnoreCase(key)){
        key = "1";
      }
      String[] list = this.generatePath(key);
      Path path = new Path(list);
      object = this.walkTo(key, path);
    }

    return object;
  }

  String[] generatePath(String key){
    if("odd".equalsIgnoreCase(key) || "even".equalsIgnoreCase(key) || "last".equalsIgnoreCase(key)){
      return ROOT_PATH;
    }else if(key =~ /^\d+$/){
      int inx = Integer.parseInt(key);
      if((inx % 2) == 1){
        return ODD_PATH;
      }else{
        return EVEN_PATH;
      }
    }else if("all".equalsIgnoreCase(key)){
      return EMPTY_PATH;  
    }else{
      throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", key));      
    }
  }

  UiObject walkTo(String key, Path path) {
    if(key.equalsIgnoreCase("all"))
      return this.root.objectRef;

    if(path != null && path.size() > 0){
      path.pop();
      RNode node = this.root.walkTo(key, path);
      if(node != null){
        return node.objectRef;
      }
    }

    return null;
  }
}
