package org.telluriumsource.ut;

import org.junit.Test;
import org.telluriumsource.trump.Element;
import org.telluriumsource.trump.Tree;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 13, 2009
 */
public class Tree_UT {
    public static final String TAG = "tag";
    public static final String ID = "id";

    @Test
    public void testAddInOrderElement(){
        Tree tree = new Tree();
        Element e1 = new Element();
        e1.setUid("A");
        e1.setXpath("/html/body/table[@id='mt']");
        e1.addAttribute(TAG, "table");
        tree.addElement(e1);

        Element e2 = new Element();
        e2.setUid("B");
        e2.setXpath("/html/body/table[@id='mt']/tbody/tr/th[3]");
        e2.addAttribute(TAG, "th");
        tree.addElement(e2);

        Element e3 = new Element();
        e3.setUid("C");
        e3.setXpath("/html/body/table[@id='mt']/tbody/tr/th[3]/div");
        e3.addAttribute(TAG, "div");
        tree.addElement(e3);

        Element e4 = new Element();
        e4.setUid("D");
        e4.setXpath("/html/body/table[@id='mt']/tbody/tr/th[3]/div/div[4]");
        e4.addAttribute(TAG, "div");
        tree.addElement(e4);

        Element e5 = new Element();
        e5.setUid("E");
        e5.setXpath("/html/body/table[@id='mt']/tbody/tr/th[3]/div/div[4]/a");
        e5.addAttribute(TAG, "a");
        tree.addElement(e5);

        tree.printUI();
    }

    @Test
    public void testAddRandomOrderElement(){
        Tree tree = new Tree();
        Element e1 = new Element();
        e1.setUid("A");
        e1.setXpath("/html/body/table[@id='mt']");
        e1.addAttribute(TAG, "table");
        tree.addElement(e1);
        tree.printUI();

        Element e2 = new Element();
        e2.setUid("B");
        e2.setXpath("/html/body/table[@id='mt']/tbody/tr/th[3]");
        e2.addAttribute(TAG, "th");
        tree.addElement(e2);
        tree.printUI();

        Element e3 = new Element();
        e3.setUid("C");
        e3.setXpath("/html/body/table[@id='mt']/tbody/tr/th[3]/div");
        e3.addAttribute(TAG, "div");
        tree.addElement(e3);
        tree.printUI();

        Element e4 = new Element();
        e4.setUid("D");
        e4.setXpath("/html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']");
        e4.addAttribute(TAG, "div");
        tree.addElement(e4);
        tree.printUI();

        Element e5 = new Element();
        e5.setUid("E");
        e5.setXpath("/html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']");
        e5.addAttribute(TAG, "table");
        e5.addAttribute(ID, "resultstable");
        tree.addElement(e5);
        tree.printUI();

        Element e6 = new Element();
        e6.setUid("F");
        e6.setXpath("/html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']/tbody/tr[2]/td[3]/a");
        e6.addAttribute(TAG, "a");
        tree.addElement(e6);
        tree.printUI();
    }
}
