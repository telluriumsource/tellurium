package org.telluriumsource.component.client


/*                                           
  Use Groovy's meta-programming capabilities to achieve the singleton pattern
  so that we can set the Selenium client in Runtime just by creating a new instance
  and assign the client to it.
  
  @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class SeleniumClientMetaClass extends MetaClassImpl{
    private final static INSTANCE = new SeleniumClient()
    SeleniumClientMetaClass() { super(SeleniumClient) }
    def invokeConstructor(Object[] arguments) { return INSTANCE }
}