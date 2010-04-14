package org.telluriumsource.ui.routing

import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.object.TextBox
import org.telluriumsource.udl.TableBodyMetaData
import org.telluriumsource.exception.InvalidIndexException
import org.telluriumsource.framework.Environment
import org.telluriumsource.udl.Index

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Mar 12, 2010
 * 
 */
class RGraph {

  String[] EMPTY_PATH = [];
  String[] ROOT_PATH = ["all"];
  String[] ODD_PATH = ["all", "odd"];
  String[] EVEN_PATH = ["all", "even"];
  String[] INDEX_LIST = ["all", "odd", "even", "any", "first", "last"];

  //Key to UI template mapping
  Map<String, UiObject> indices;

  //Internal ID to Template mapping
  Map<String, UiObject> templates;

  //row
  RNode r;

  //column
  RNode c;

  //tbody
  RNode t;

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

  public boolean isRef(String key){
    return !isIndex(key);
  }

  public void createIndex(String key, UiObject obj){
    this.indices.put(key, obj);
  }

  protected String getInternalId(UiObject object){
    TableBodyMetaData meta = object.metaData;
    String tx = meta.tbody.getValue();
    String rx = meta.row.getValue();
    String cx = meta.column.getValue();

    return "_${tx}_${rx}_${cx}";
  }

   protected String getInternalId(String tx, String rx, String cx){
     return "_${tx}_${rx}_${cx}";
   }
  
  public void storeTemplate(UiObject object){
    String iid = this.getInternalId(object);
    this.templates.put(iid, object);
  }

  void insertRTree(RNode root, String index, UiObject object, String iid){
    if("all".equalsIgnoreCase(index)){
      root.objectRef = object;
      root.presented = true;
      root.templates.add(iid);
    }else if("odd".equalsIgnoreCase(index)){
      RNode oddNode = root.findChild("odd");
      oddNode.presented = true;
      oddNode.objectRef = object;
      oddNode.templates.add(iid);
    }else if("even".equalsIgnoreCase(index)){
      RNode evenNode = root.findChild("even");
      evenNode.presented = true;
      evenNode.objectRef = object;
      evenNode.templates.add(iid);
    }else if("last".equalsIgnoreCase(index)){
      RNode last = root.findChild("last");
      if(last == null){
        last = new RNode("last", root, object, true);
        root.addChild(last);
      }
      last.templates.add(iid);
    }else if("any".equalsIgnoreCase(index)){
      RNode any = root.findChild("any");
      if(any == null){
         any = new RNode("any", root, object, true);
         root.addChild(any);
      }
      any.templates.add(iid);
    }else if("first".equalsIgnoreCase(index)){
      RNode oddNode = root.findChild("odd");
      RNode first = oddNode.findChild("1");
      if(first == null){
        first = new RNode("1", oddNode, object, true);
        oddNode.addChild(first);
      }
      first.templates.add(iid);
    }else if(index =~ /^\d+$/){
      int inx = Integer.parseInt(index);
      if((inx % 2) == 1){
        RNode oddNode = root.findChild("odd");
        RNode inode = oddNode.findChild(index);
        if(inode == null){
          inode = new RNode(index, oddNode, object, true);
          oddNode.addChild(inode);
        }
        inode.templates.add(iid);
      }else{
        RNode evenNode = root.findChild("even");
        RNode inode = evenNode.findChild(index);
        if(inode == null){
          inode = new RNode(index, evenNode, object, true);
          evenNode.addChild(inode);
        }
        inode.templates.add(iid);
      }
    }else{
      //reference node
      RNode ref = root.findChild(index);
      if (ref == null) {
        ref = new RNode(index, root, object, true);
        root.addChild(ref);
      }
      ref.templates.add(iid);
     
//       throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", index))
    }
  }

  void insertTBody(UiObject object, String iid) {
    TableBodyMetaData meta = object.metaData;
    String index = meta.tbody.getValue();
    insertRTree(this.t, index, object, iid);
  }

  void insertRow(UiObject object, String iid) {
    TableBodyMetaData meta = object.metaData;
    String index = meta.row.getValue();
    insertRTree(this.r, index, object, iid);
  }

  void insertColumn(UiObject object, String iid) {
    TableBodyMetaData meta = object.metaData;
    String index = meta.column.getValue();
    insertRTree(this.c, index, object, iid);
  }

  void insert(UiObject object) {
    String iid = this.getInternalId(object);
    this.templates.put(iid, object);
    
    insertTBody(object, iid);
    insertRow(object, iid);
    insertColumn(object, iid);
  }
  
  void preBuild() {
    this.templates = new HashMap<String, UiObject>();
    TextBox defaultUi = new TextBox();
    TableBodyMetaData meta = new TableBodyMetaData();
    meta.setTbody(new Index("all"));
    meta.setRow(new Index("all"));
    meta.setColumn(new Index("all"));
    meta.setId("defaultUi");
    defaultUi.metaData = meta;
    this.templates.put("_all_all_all", defaultUi);

    RNode taNode = new RNode("all", null, defaultUi, true);
    taNode.addTemplate("_all_all_all");
    this.t = taNode;
    this.t.bias = 0.1;
    RNode toNode = new RNode('odd', taNode, defaultUi, false);
    this.t.addChild(toNode);
    RNode teNode = new RNode('even', taNode, defaultUi, false);
    this.t.addChild(teNode);

    RNode raNode = new RNode("all", null, defaultUi, true);
    raNode.addTemplate("_all_all_all");
    this.r = raNode;
    this.r.bias = 0.2;
    RNode roNode = new RNode('odd', raNode, defaultUi, false);
    this.r.addChild(roNode);
    RNode reNode = new RNode('even', raNode, defaultUi, false);
    this.r.addChild(reNode);
    
    RNode caNode = new RNode("all", null, defaultUi, true);
    caNode.addTemplate("_all_all_all");
    this.c = caNode;
    this.c.bias = 0.3;
    RNode coNode = new RNode('odd', caNode, defaultUi, false);
    this.c.addChild(coNode);
    RNode ceNode = new RNode('even', caNode, defaultUi, false);
    this.c.addChild(ceNode);
  }

  UiObject route(String key) {
    UiObject object = this.indices.get(key);
    if(object == null){
      String[] parts= key.replaceFirst('_', '').split('_');
      String[] ids = parts;
      if(parts.length < 3){
        ids = ["1", parts].flatten();
      }
      String x = ids[0];
      if("first".equalsIgnoreCase(x)){
        x = "1";
      }
      String y = ids[1];
      if("first".equalsIgnoreCase(y)){
        y = "1";
      }
      String z = ids[2];
      if("first".equalsIgnoreCase(z)){
        z = "1";
      }
      
      String[] list = this.generatePath(x);
      Path path = new Path(list);
      RNode nx = this.walkTo(this.t, x, path);
      list = this.generatePath(y);
      path = new Path(list);
      RNode ny = this.walkTo(this.r, y, path);
      list = this.generatePath(z);
      path = new Path(list);
      RNode nz = this.walkTo(this.c, z, path);

      int smallestFitness = 100 * 4;
      RNode xp = nx;
      while (xp != null) {
        RNode yp = ny;
        while (yp != null) {
          RNode zp = nz;
          while(zp != null){
            String iid = this.getInternalId(xp.getKey(), yp.getKey(), zp.getKey());
            if(xp.templates.contains(iid) && yp.templates.contains(iid) && zp.templates.contains(iid)){
              int fitness = (nx.getLevel() - xp.getLevel()) * 100 + (ny.getLevel() - yp.getLevel()) * 10 + (nz.getLevel() - zp.getLevel());
              if(fitness < smallestFitness){
                object = this.templates.get(iid);
                smallestFitness = fitness;
              }
            }
            zp = zp.parent;
          }
          yp = yp.parent;
         }

        xp = xp.parent;
      }
    }

    return object;
  }

  boolean shareTemplate(RNode x, RNode y, RNode z){
    String iid = this.getInternalId(x.getKey(), y.getKey(), z.getKey());

    return x.templates.contains(iid) && y.templates.contains(iid) && z.templates.contains(iid);
  }

  String[] generatePath(String key){
    if("odd".equalsIgnoreCase(key) || "even".equalsIgnoreCase(key) || "last".equalsIgnoreCase(key) || "any".equalsIgnoreCase(key)){
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
      return ROOT_PATH;
//      throw new InvalidIndexException(Environment.instance.myResourceBundle().getMessage("UIObject.InvalidIndex", key));
    }
  }

  RNode walkTo(RNode root, String key, Path path) {
    if(key.equalsIgnoreCase("all"))
      return root;

    if(path != null && path.size() > 0){
      path.pop();
      RNode node = root.walkTo(key, path);
      if(node != null){
        return node;
      }
    }

    return null;
  }
}