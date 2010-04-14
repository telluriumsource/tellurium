package org.telluriumsource.test.ddt.mapping.mapping

import org.telluriumsource.test.ddt.mapping.type.TypeHandlerRegistry
import org.telluriumsource.test.ddt.mapping.type.TypeHandler
import org.telluriumsource.test.ddt.mapping.DataMappingException
import org.telluriumsource.framework.Environment
import org.telluriumsource.crosscut.i18n.IResourceBundle


/**
 * Default implementation to convert a data field to a Java object
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class DefaultObjectUnmarshaller implements ObjectUnmarshaller{

    protected IResourceBundle i18nBundle ;

    protected TypeHandlerRegistry registry

    DefaultObjectUnmarshaller(){
    	i18nBundle = Environment.instance.myResourceBundle()
    }

    public void setTypeHandlerRegistry(TypeHandlerRegistry registry){
        this.registry = registry
    }

    public Object unmarshal(String type, String data) {
        TypeHandler handler = registry.getTypeHandler(type)
        if(handler == null)
            throw new DataMappingException(i18nBundle.getMessage("ObjectUnmarshaller.UnsupportedFieldType"))
        return handler.valueOf(data)
    }

}