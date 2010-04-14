package org.telluriumsource.ui.builder

/*
    Use MetaClass to force you always get back the same instance, which is the Groovy style
    of singleton pattern using MetaProgramming

    @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class UiObjectBuilderRegistryMetaClass extends MetaClassImpl{

    private final static INSTANCE = new UiObjectBuilderRegistry()

    UiObjectBuilderRegistryMetaClass() { super(UiObjectBuilderRegistry) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}