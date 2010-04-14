package org.telluriumsource.component.dispatch

/*
    Use MetaClass to force you always get back the same instance, which is the Groovy style
    of singleton pattern using MetaProgramming

    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class DispatcherMetaClass extends MetaClassImpl{

    private final static INSTANCE = new Dispatcher()

    DispatcherMetaClass() { super(Dispatcher) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}