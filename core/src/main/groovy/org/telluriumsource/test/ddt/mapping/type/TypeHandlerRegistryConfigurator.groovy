package org.telluriumsource.test.ddt.mapping.type
/**
 * set all type handlers to the registry
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class TypeHandlerRegistryConfigurator {

    public static void configure(TypeHandlerRegistry registry){

        registry.addTypeHandler("boolean", new BooleanTypeHandler())
        registry.addTypeHandler("byte", new ByteTypeHandler())
        registry.addTypeHandler("char", new CharTypeHandler())
        registry.addTypeHandler("double", new DoubleTypeHandler())
        registry.addTypeHandler("float", new FloatTypeHandler())
        registry.addTypeHandler("int", new IntegerTypeHandler())
        registry.addTypeHandler("long", new LongTypeHandler())
        registry.addTypeHandler("short", new ShortTypeHandler())
        registry.addTypeHandler("string", new StringTypeHandler())

        //put custom type handler here
    }

    public static void addCustomTypeHandler(TypeHandlerRegistry registry, String handlerName, String fullClassName){

        TypeHandler handler = (TypeHandler) Class.forName(fullClassName).newInstance()
        registry.addTypeHandler(handlerName, handler)
    }
}