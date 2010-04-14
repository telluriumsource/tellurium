package org.telluriumsource.ut;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.telluriumsource.misc.Trie;
import org.telluriumsource.misc.Node;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 21, 2009
 */
public class Trie_UT {
    public String[] randomize(String[] dictionary){
        if(dictionary != null && dictionary.length > 0){
            Random rand = new Random();
            List<String> list = new ArrayList<String>();
            for(String str: dictionary){
                list.add(str);
            }
            List<String> nlist = new ArrayList<String>();
            while(list.size() > 0){
                int index = rand.nextInt(list.size());
                nlist.add(list.remove(index));
            }

            return nlist.toArray(new String[0]);
        }

        return null;
    }

    @Test
    public void testInsert(){
        String[] dictionary = {"a", "an", "and", "andy", "bo", "body", "bodyguard", "some", "someday", "goodluck", "joke"};
        Trie trie = new Trie();
        String[] ndict = randomize(dictionary);
        trie.buildTree(ndict);
        trie.checkAndIndexLevel();
        trie.printMe();
        Node deepest = trie.getDeepestNode();
        assertNotNull(deepest);
        System.out.println("deepest word: " + deepest.getFullWord() + ", level: " + deepest.getLevel());
        
    }
    
}
