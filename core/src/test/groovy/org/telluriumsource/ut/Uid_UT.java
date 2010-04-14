package org.telluriumsource.ut;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.telluriumsource.trump.Uid;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 13, 2009
 */
public class Uid_UT {

    @Test
    public void testGenUid(){
        assertEquals("T4hbt", Uid.genUid("/html/body/table[@id='mt']"));
        assertEquals("T4ttt", Uid.genUid("/tbody/tr/th[3]]"));
        assertEquals("T4d", Uid.genUid("/div"));
        assertEquals("undefined", Uid.genUid(null));
        assertEquals("undefined", Uid.genUid(""));
    }
}
