package org.telluriumsource.component.connector

/**
 * Use to create a shared connector so that individual test case always gets back the same connector
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
class SeleniumConnectorMetaClass extends MetaClassImpl{
    
    private final static INSTANCE = new SeleniumConnector()

    SeleniumConnectorMetaClass() { super(SeleniumConnector) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}