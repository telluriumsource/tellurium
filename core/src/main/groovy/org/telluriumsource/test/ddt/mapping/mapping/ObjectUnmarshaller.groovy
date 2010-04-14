package org.telluriumsource.test.ddt.mapping.mapping
/**
 * Unmarshal a data field to java object
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
interface ObjectUnmarshaller {

    public Object unmarshal(String type, String data)
    
}