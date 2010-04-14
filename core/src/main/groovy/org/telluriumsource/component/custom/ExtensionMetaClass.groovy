package org.telluriumsource.component.custom

/**
 * MetaClass of the Extension class
 *
 * @author John.Jian.Fang@gmail.com
 *
 * Date: Mar 19, 2009
 *
 */
public class ExtensionMetaClass  extends MetaClassImpl{

    private final static INSTANCE = new Extension()

    ExtensionMetaClass() { super(Extension) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}