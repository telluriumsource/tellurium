package org.telluriumsource.test.ddt.mapping.type
/**
 * Registry to hold type handlers for different types of Java object
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class TypeHandlerRegistry {

    //type name to TypeHandler map

    Map<String, TypeHandler> registry = new HashMap<String, TypeHandler>()

    public void addTypeHandler(String type, TypeHandler handler){
        registry.put(type.toUpperCase(), handler)
    }

    public TypeHandler getTypeHandler(String type){
        return registry.get(type.toUpperCase())
    }
}