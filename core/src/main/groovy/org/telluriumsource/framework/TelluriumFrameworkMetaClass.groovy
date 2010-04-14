package org.telluriumsource.framework

/**
 * Make the Tellurium Framework a singleton
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 * Date: Jun 2, 2008
 */
class TelluriumFrameworkMetaClass extends MetaClassImpl{

    private final static INSTANCE = new TelluriumFramework()

    TelluriumFrameworkMetaClass() { super(TelluriumFramework) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}