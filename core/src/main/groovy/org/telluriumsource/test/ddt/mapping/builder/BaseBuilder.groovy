package org.telluriumsource.test.ddt.mapping.builder
/**
 * Shared code for builder
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
abstract class BaseBuilder {
    protected static final String ID = "id"
    protected static final String DESCRIPTION = "description"
    protected static final String NAME = "name"
    protected static final String TYPE = "type"
    protected static final String NULLABLE = "nullable"
    protected static final String NULLVALUE = "nullValue"
    protected static final String LENGTH = "length"
    protected static final String PATTERN = "pattern"
    protected static final String VALUE = "value"
    
    Map makeCaseInsensitive(Map map){
        def newmap = [:]
        map.each{ String key, value ->
            //make all lower cases
            newmap.put(key.toLowerCase(), value)
        }

        return newmap
    }

    def abstract build(Map map)
}