package org.telluriumsource.component.event

/*
    Use MetaClass to force you always get back the same instance, which is the Groovy style
    of singleton pattern using MetaProgramming, which also makes it possible for us to swap
    in different EventHandler instances by setting the metaclass in groovy system registry.
    
    @author Jian Fang (Jian.Fang@jtv.com) 
 */
class EventHandlerMetaClass extends MetaClassImpl{

    private final static INSTANCE = new EventHandler()

    EventHandlerMetaClass() { super(EventHandler) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}