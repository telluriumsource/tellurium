package org.telluriumsource.misc;


import java.util.Comparator;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Dec 21, 2009
 */
public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node first, Node second) {
        return second.getLevel() - first.getLevel();
    }
}
