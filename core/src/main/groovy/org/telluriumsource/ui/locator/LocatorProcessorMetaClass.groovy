package org.telluriumsource.ui.locator

/*
    Use MetaClass to force you always get back the same instance, which is the Groovy style
    of singleton pattern using MetaProgramming, which also makes it possible for us to swap
    in different LocatorProcessor instances by setting the metaclass in groovy system registry.

    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class LocatorProcessorMetaClass extends MetaClassImpl{

    private final static INSTANCE = new LocatorProcessor()

    LocatorProcessorMetaClass() { super(LocatorProcessor) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}