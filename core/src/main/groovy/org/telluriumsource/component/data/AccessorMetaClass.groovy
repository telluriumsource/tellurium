package org.telluriumsource.component.data

/*
    Use MetaClass to force you always get back the same instance, which is the Groovy style
    of singleton pattern using MetaProgramming
    
    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class AccessorMetaClass extends MetaClassImpl{

    private final static INSTANCE = new Accessor()

    AccessorMetaClass() { super(Accessor) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}