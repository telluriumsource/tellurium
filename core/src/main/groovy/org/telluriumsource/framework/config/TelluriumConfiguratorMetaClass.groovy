package org.telluriumsource.framework.config
/**
 * Another singleton so that all classes can reach this configurator, may not be very elegant, but keep it this
 * way until we have better way to configure or wire the framework.
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 4, 2008
 *
 */
class TelluriumConfiguratorMetaClass extends MetaClassImpl{

    private final static INSTANCE = new TelluriumConfigurator()

    TelluriumConfiguratorMetaClass() { super(TelluriumConfigurator) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }

}