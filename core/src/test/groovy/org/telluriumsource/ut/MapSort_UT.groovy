package org.telluriumsource.ut

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 28, 2008
 *
 */
class MapSort_UT extends GroovyTestCase {

    public void testSort(){
        Map<Integer, String> map = new HashMap<Integer, String>()
        map.put(1, new Test(1,"a"))
        map.put(2, new Test(2,"b"))
        map.put(3, new Test(3,"c"))
        map.put(5, new Test(5,"d"))

        List<String> list = map.values().sort { x, y ->
            x.id <=> y.id
        }
        assertNotNull(list)
        assertEquals(4, list.size().value)
        assertEquals("a", list.get(0).value)
        assertEquals("b", list.get(1).value)
        assertEquals("c", list.get(2).value)
        assertEquals("d", list.get(3).value)
    }
}