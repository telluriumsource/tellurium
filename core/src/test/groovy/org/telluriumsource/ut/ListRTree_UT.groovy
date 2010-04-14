package org.telluriumsource.ut

import org.telluriumsource.udl.ListMetaData
import org.telluriumsource.ui.object.TextBox
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.routing.RTree
import org.telluriumsource.exception.InvalidIndexException

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 23, 2010
 * 
 */
class ListRTree_UT extends GroovyTestCase {

  public void testFullTree(){
    RTree tree = new RTree();
    tree.preBuild();
    tree.indices = new HashMap<String, UiObject>();

    ListMetaData allMeta = new ListMetaData("Rest", "all");
    UiObject all = new TextBox();
    all.metaData = allMeta;
    tree.indices.put("Rest", all);

    ListMetaData anyMeta = new ListMetaData("A", "any");
    UiObject any = new TextBox();
    any.metaData = anyMeta;
    tree.indices.put("A", any);

    ListMetaData oddMeta = new ListMetaData("B", "odd");
    UiObject odd = new TextBox();
    odd.metaData = oddMeta;
    tree.indices.put("B", odd);

    ListMetaData evenMeta = new ListMetaData("C", "even");
    UiObject even = new TextBox();
    even.metaData = evenMeta;
    tree.indices.put("C", even);

    ListMetaData firstMeta = new ListMetaData("D", "first");
    UiObject first = new TextBox();
    first.metaData = firstMeta;
    tree.indices.put("D", first);

    ListMetaData lastMeta = new ListMetaData("E", "last");
    UiObject last = new TextBox();
    last.metaData = lastMeta;
    tree.indices.put("E", last);

    ListMetaData twoMeta = new ListMetaData("F", "2");
    UiObject two = new TextBox();
    two.metaData = twoMeta;
    tree.indices.put("F", two);

    ListMetaData threeMeta = new ListMetaData("G", "3");
    UiObject three = new TextBox();
    three.metaData = threeMeta;
    tree.indices.put("G", three);

    ListMetaData fiveMeta = new ListMetaData("H", "5");
    UiObject five = new TextBox();
    five.metaData = fiveMeta;
    tree.indices.put("H", three);

    tree.insert(all);
    tree.insert(any);
    tree.insert(odd);
    tree.insert(even);
    tree.insert(first);
    tree.insert(last);
    tree.insert(two);
    tree.insert(three);
    tree.insert(five);

    UiObject a = tree.route("A");
    assertNotNull(a);
    assertEquals("A", a.metaData.id);
    assertEquals("any", a.metaData.index.value);

    UiObject b = tree.route("last");
    assertNotNull(b);
    assertEquals("E", b.metaData.id);
    assertEquals("last", b.metaData.index.value);

    UiObject c = tree.route("odd");
    assertNotNull(c);
    assertEquals("B", c.metaData.id);
    assertEquals("odd", c.metaData.index.value);

    UiObject d = tree.route("3");
    assertNotNull(d);
    assertEquals("G", d.metaData.id);
    assertEquals("3", d.metaData.index.value);

    UiObject e = tree.route("4");
    assertNotNull(e);
    assertEquals("C", e.metaData.id);
    assertEquals("even", e.metaData.index.value);
    
    UiObject f = tree.route("7");
    assertNotNull(f);
    assertEquals("B", f.metaData.id);
    assertEquals("odd", f.metaData.index.value);

    UiObject g = tree.route("first");
    assertNotNull(g);
    assertEquals("D", g.metaData.id);
    assertEquals("first", g.metaData.index.value);

    UiObject h = tree.route("1");
    assertNotNull(h);
    assertEquals("D", h.metaData.id);
    assertEquals("first", h.metaData.index.value);
  }

  public void testPartialTree(){
    RTree tree = new RTree();
    tree.preBuild();
    tree.indices = new HashMap<String, UiObject>();

    ListMetaData oddMeta = new ListMetaData("B", "odd");
    UiObject odd = new TextBox();
    odd.metaData = oddMeta;
    tree.indices.put("B", odd);

    ListMetaData twoMeta = new ListMetaData("F", "2");
    UiObject two = new TextBox();
    two.metaData = twoMeta;
    tree.indices.put("F", two);

    ListMetaData threeMeta = new ListMetaData("G", "3");
    UiObject three = new TextBox();
    three.metaData = threeMeta;
    tree.indices.put("G", three);
    
    tree.insert(odd);
    tree.insert(two);
    tree.insert(three);

    try{
      tree.route("A");
      fail("Should throw InvalidIndexException");
    }catch(InvalidIndexException e){
    }

    UiObject b = tree.route("last");
    assertNotNull(b);
    assertNotNull(b.metaData);

    UiObject c = tree.route("odd");
    assertNotNull(c);
    assertEquals("B", c.metaData.id);
    assertEquals("odd", c.metaData.index.value);

    UiObject d = tree.route("3");
    assertNotNull(d);
    assertEquals("G", d.metaData.id);
    assertEquals("3", d.metaData.index.value);

    UiObject e = tree.route("4");
    assertNotNull(e);
    assertNotNull(e.metaData);

    UiObject f = tree.route("7");
    assertNotNull(f);
    assertEquals("B", f.metaData.id);
    assertEquals("odd", f.metaData.index.value);

    UiObject g = tree.route("all");
    assertNotNull(g);
    assertNotNull(g.metaData);
  }

}
