package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.AllPurposeObject
import org.telluriumsource.ui.locator.CompositeLocator
import org.telluriumsource.ui.Const

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 7, 2010
 * 
 */
class AllPurposeObjectBuilder extends Const {

  public static final String TEUID = "teuid";

  public AllPurposeObject build(String uid, String teuid, String tag, Map attributes, boolean isCacheable){
    AllPurposeObject obj = new AllPurposeObject();
    obj.uid = uid;
    obj.cacheable = isCacheable;
    
    CompositeLocator locator = new CompositeLocator();
    Map<String, String> attrs = [:];
    locator.tag = tag;
    locator.text = attributes.get(TEXT);
    locator.position = attributes.get(POSITION);

    attributes.each { String key, value ->
        if(!HEADER.equals(key) && !TRAILER.equals(key) && !TAG.equals(key) && !TEXT.equals(key) && !POSITION.equals(key) && !DIRECT.equals(key))
            attrs.put(key, value);
    }

    attrs.put(TEUID, teuid);

    locator.attributes = attrs;
    obj.locator = locator;

    return obj;
  }

}
