package org.telluriumsource.component.event

/**
 * Us algorithm to sort given lists of events so that it satisifies the execution flow constraint
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 15, 2008
 * 
 */
class EventSorter {
    //define events for before method invocation
    //the array defines the event occurring order
    Event[] left = [Event.MOUSEOVER, Event.FOCUS, Event.MOUSEDOWN]
    Event[] right = [Event.MOUSEUP, Event.MOUSEOUT, Event.BLUR]
    Map<String, Integer> indices = new HashMap<String, Integer>()

    public EventSorter(){
        int i = 0;
        left.each { Event event ->
            indices.put(event.toString().toUpperCase(), i)
            i++
        }
        //put the placeholder Action there so that we know what events should be run before the action
        //and what events should be run after the action
        indices.put(Event.ACTION.toString(), i)
        i++
        right.each { Event event ->
            indices.put(event.toString(), i)
            i++
        }
    }

    //use linear sorting similar to the counting sort
    public Event[] sort(String[] events, String[] defaultEvents){
        Event[] counting = new Event[indices.size()]

        events?.each{ String event ->
            Integer index = indices.get(event.toUpperCase())
            //this likes a filter so that we only care about the events we defined in left and right
            if(index != null){
                counting[index] = Event.valueOf(event.toUpperCase())
            }
        }

        defaultEvents?.each{ String event ->
            Integer index = indices.get(event.toUpperCase())
            //this likes a filter so that we only care about the events we defined in left and right
            if(index != null){
                //if we have duplications in events and defaultEvents, they will be overridden here, removed
                counting[index] = Event.valueOf(event.toUpperCase())
            }
        }

        int i = indices.get(Event.ACTION.toString())
        counting[i] = Event.ACTION
        
        LinkedList<Event> list = new LinkedList<Event>()
        counting.each { Event e ->
            if(e != null)
                list.add(e)
        }

        return list.toArray()
    }

}