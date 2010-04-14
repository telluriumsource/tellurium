package org.telluriumsource.ut

import org.telluriumsource.component.event.Event
import org.telluriumsource.component.event.EventSorter

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 15, 2008
 * 
 */
class EventSorter_UT extends GroovyTestCase{
    EventSorter alg = new EventSorter()

    public void testSortNull(){
        Event[] result = alg.sort(null, null)
        assertNotNull(result)
        assertEquals(1, result.length)
        assertEquals(Event.ACTION, result[0])
    }

    public void testSortNullDefault(){
        String[] events = ["click", "focus"]
        Event[] result = alg.sort(events, null)
        assertNotNull(result)
        assertEquals(2, result.length)
        assertEquals(Event.FOCUS, result[0])
        assertEquals(Event.ACTION, result[1])
    }

    public void testSortNullEvents(){
        String[] defaults = ["focus"]
        Event[] result = alg.sort(null, defaults)
        assertNotNull(result)
        assertEquals(2, result.length)
        assertEquals(Event.FOCUS, result[0])
        assertEquals(Event.ACTION, result[1])
    }

    public void testSortFull(){
        String[] defaults = ["focus", "mouseOver", "mouseOut", "blur"]
        String[] events = ["click", "blur"]
        Event[] result = alg.sort(events, defaults)
        assertNotNull(result)
        assertEquals(5, result.length)
        assertEquals(Event.MOUSEOVER, result[0])
        assertEquals(Event.FOCUS, result[1])
        assertEquals(Event.ACTION, result[2])
        assertEquals(Event.MOUSEOUT, result[3])
        assertEquals(Event.BLUR, result[4])
    }
}