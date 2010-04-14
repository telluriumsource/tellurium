package org.telluriumsource.ui.builder

import org.telluriumsource.ui.locator.BaseLocator
import org.telluriumsource.ui.locator.CompositeLocator
import org.telluriumsource.ui.locator.JQLocator
import org.telluriumsource.ui.object.Container
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.Const
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.ui.Const
import org.telluriumsource.udl.MetaData
import org.telluriumsource.udl.UidParser
import org.antlr.runtime.RecognitionException
import org.telluriumsource.exception.UidRecognitionException

/**
 *  Basic UI object builder
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
abstract class UiObjectBuilder extends Const {

    protected IResourceBundle i18nBundle

    def abstract build(Map map, Closure c);

    public UiObjectBuilder(){
    	i18nBundle = Environment.instance.myResourceBundle();
    }
    boolean validate(UiObject obj, Map map){
//    	Environment env = Environment.instance
        boolean valid = true
        if(map == null || map.isEmpty()){

            println i18nBundle.getMessage("UIObjectBuilder.EmptyMap")
            return false
        }

        if(map.get(UID) == null){
            println i18nBundle.getMessage("UIObjectBuilder.UIDRequired")
            return false
        }

/*        if(map.get(LOCATOR) != null && map.get(CLOCATOR) != null){
            println i18nBundle.getMessage("UIObjectBuilder.LocatorRequired")
            return false
        }*/

        if(map.get(USE_GROUP_INFO) != null && (!Container.class.isAssignableFrom(obj.getClass())) ){
           println i18nBundle.getMessage("UIObjectBuilder.GroupInfoRequired")
           return false
        }
        return valid
    }

    Map makeCaseInsensitive(Map map){
        def newmap = [:]
        map.each{ String key, value ->
            //make all lower cases
            newmap.put(key.toLowerCase(), value)
        }

        return newmap
    }

    def internBuild(UiObject obj, Map map, Map df){
       if(!validate(obj, map))
         throw new RuntimeException(i18nBundle.getMessage("UIObjectBuilder.ObjectDefinitionError"))

        //make all lower cases
        map = makeCaseInsensitive(map)

        String dsluid = map.get(UID);
        try{
          obj.metaData = UidParser.parse(dsluid)
        }catch(RecognitionException e){
          throw new UidRecognitionException(i18nBundle.getMessage("UidParser.CannotParseUid" , dsluid))
        }

//        obj.uid = map.get(UID)
        obj.uid = dsluid;
        //by default, the ui object's template id is its uid, which is a String constant
//        obj.tid = obj.uid
        obj.tid = obj.metaData.getId();
        String ns = map.get(NAMESPACE)
        if(ns != null && ns.trim().length() > 0){
          obj.namespace = ns.trim()
        }

        String useGroup = map.get(USE_GROUP_INFO)
        if(useGroup != null){
          if(TRUE.equalsIgnoreCase(useGroup.toUpperCase())){
            ((Container)obj).useGroupInfo = true
          }else{
            ((Container)obj).useGroupInfo = false
          }
        }
      
        String cache = map.get(CACHEABLE)
        if(cache != null){
          if(TRUE.equalsIgnoreCase(cache.toUpperCase())){
            obj.cacheable = true
          }else{
            obj.cacheable = false
          }
        }

        String self = map.get(SELF)
        if(self != null){
          if(TRUE.equalsIgnoreCase(self.toUpperCase())){
            obj.self = true
          }else{
            obj.self = false
          }
        }

        String nocachechildren = map.get(NO_CACHE_FOR_CHILDREN)
        if(nocachechildren != null && nocachechildren.trim().length() > 0){
          if(obj instanceof Container){
            if(TRUE.equalsIgnoreCase(nocachechildren)){
              obj.noCacheForChildren = true
            }
          }
        }

/*
        else{
           if(obj instanceof org.telluriumsource.ui.object.Table || obj instanceof org.tellurium.object.List){
              //not to use cache for children for all tables and Lists by default
              obj.noCacheForChildren = true
           }
        }
*/

        if(map.get(LOCATOR) != null){
            //use base locator
            obj.locator = buildBaseLocator(map.get(LOCATOR))
        }else if (map.get(CLOCATOR) != null){
            //use composite locator, it must be a map
            obj.locator = buildCompositeLocator(map.get(CLOCATOR), df)
            if(obj.namespace != null && obj.namespace.trim().length() > 0){
              obj.locator.namespace = obj.namespace
            }
        }else if (map.get(JQLOCATOR) != null){
            //use jquery locator
            obj.locator = buildJQueryLocator(map.get(JQLOCATOR))
        }else{
            //use default base Locator
 //           obj.locator = new BaseLocator()
        }

        //add respond to events
        if(map.get(RESPOND_TO_EVENTS) != null){
            obj.respondToEvents = map.get(RESPOND_TO_EVENTS);
/*          def rte = map.get(RESPOND_TO_EVENTS);
            if(rte != null){
              String[] parts = rte.split(",");
              ArrayList lst = new ArrayList();
              for(String part: parts){
                lst.add(part.trim());
              }
              obj.respondToEvents = lst.toArray(new String[0]);
            }*/

            //add dynamic method "click" if the Click event is included and is not on the original object
            //this will be implemented by the methodMissing method
        }

        return obj
    }

    def buildBaseLocator(String loc){
        BaseLocator locator = new BaseLocator(loc:loc)
        return locator
    }

    def buildJQueryLocator(String loc){
        JQLocator locator = new JQLocator(loc:loc)

        return locator
    }

    def buildCompositeLocator(Map map, Map df){
        CompositeLocator locator = new CompositeLocator()
        Map<String, String> attributes = [:]
        locator.header = map.get(HEADER)
        locator.trailer = map.get(TRAILER)

        if(map.get(DIRECT) != null && TRUE.equalsIgnoreCase(map.get(DIRECT)))
            locator.direct = true

        if(map.get(INSIDE) != null && TRUE.equalsIgnoreCase(map.get(INSIDE)))
            locator.inside = true

        locator.tag = map.get(TAG)
        //use default value  if TAG not specified
        if(locator.tag == null && df != null)
            locator.tag = df.get(TAG)

        locator.text = map.get(TEXT)
        locator.position = map.get(POSITION)

        map.each { String key, value ->
            if(!HEADER.equals(key) && !TRAILER.equals(key) && !TAG.equals(key) && !TEXT.equals(key) && !POSITION.equals(key) && !DIRECT.equals(key))
                attributes.put(key, value)
        }
        if(df != null && (!df.isEmpty())){
            df.each { String key, value ->
                //only apply default value when the attribute is not specified
                if(!TAG.equals(key) && attributes.get(key) == null){
                    attributes.put(key, value)
                }
            }
        }
        locator.attributes = attributes

        return locator
    }
}