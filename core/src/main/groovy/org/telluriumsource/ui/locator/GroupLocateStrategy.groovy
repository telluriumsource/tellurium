package org.telluriumsource.ui.locator

import org.telluriumsource.ui.object.Container
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.exception.InvalidLocatorException
import static org.telluriumsource.ui.Const.*
import org.telluriumsource.ui.object.Table
import org.telluriumsource.ui.object.StandardTable
import org.telluriumsource.ui.object.Repeat

/**
 *   Use group information, i.e., use its direct descendants' information to form its own locator
 *   In the future, we may consider deeper information such as grandchild descendants'. Note, only
 *   Container and its extended classes can use GroupLocateStrategy
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 */
class GroupLocateStrategy {

    protected static final String ERROR_MESSAGE = "Required Composite Locator, Invalid locator"

    protected static final int LENGTH = 64

    def static String select(StandardTable obj){
      if(!obj.locator instanceof CompositeLocator)
        throw new InvalidLocatorException("${ERROR_MESSAGE} ${obj.uid}")

        List<String> groupAttributes = new ArrayList<String>()
        obj.headers?.each {
            String key, UiObject child ->
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
//                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes)
                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, null, cloc.direct, cloc.attributes)
                if(!MATCH_ALL.equals(gattr.trim()))
                  groupAttributes.add(gattr.trim())
            }

        }

        obj.components.each{ String key, UiObject child ->
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
//                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes)
                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, null, cloc.direct, cloc.attributes)
                if(!MATCH_ALL.equals(gattr.trim()))
                  groupAttributes.add(gattr.trim())
            }
        }
      
//        obj.footers?.headers?.each {
       obj.footers?.each {
            String key, UiObject child ->
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
//                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes)
                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, null, cloc.direct, cloc.attributes)
                if(!MATCH_ALL.equals(gattr.trim()))
                  groupAttributes.add(gattr.trim())
            }

        }

        CompositeLocator locator = obj.locator
        String self = JQueryBuilder.buildJQuerySelector(locator.tag, locator.text, locator.position, locator.direct, locator.attributes)
        StringBuffer sb = new StringBuffer(LENGTH)
        sb.append(self)
        //Do not need group locating if there is ID attribute in itself since ID is uniquely defined in jQuery
        if(locator.noIdIncluded() && groupAttributes.size() > 0){
          sb.append(HAS).append("(").append(groupAttributes.join(SELECTOR_SEPARATOR)).append(")")
        }

        return sb.toString()
    }

    def static String select(Table obj){
      if(!obj.locator instanceof CompositeLocator)
        throw new InvalidLocatorException("${ERROR_MESSAGE} ${obj.uid}")

        List<String> groupAttributes = new ArrayList<String>()
        obj.headers?.each {
            String key, UiObject child ->
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
//                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes)
                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, null, cloc.direct, cloc.attributes)
                if(!MATCH_ALL.equals(gattr.trim()))
                  groupAttributes.add(gattr.trim())
            }

        }

        obj.components.each{ String key, UiObject child ->
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
//                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes)
                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, null, cloc.direct, cloc.attributes)
                if(!MATCH_ALL.equals(gattr.trim()))
                  groupAttributes.add(gattr.trim())
            }
        }

        CompositeLocator locator = obj.locator
        String self = JQueryBuilder.buildJQuerySelector(locator.tag, locator.text, locator.position, locator.direct, locator.attributes)
        StringBuffer sb = new StringBuffer(LENGTH)
        sb.append(self)
        //Do not need group locating if there is ID attribute in itself since ID is uniquely defined in jQuery
        if(locator.noIdIncluded() && groupAttributes.size() > 0){
          sb.append(HAS).append("(").append(groupAttributes.join(SELECTOR_SEPARATOR)).append(")")
        }

        return sb.toString()
    }

    def static String select(Container obj){
      if(!obj.locator instanceof CompositeLocator)
        throw new InvalidLocatorException("${ERROR_MESSAGE} ${obj.uid}")

        List<String> groupAttributes = new ArrayList<String>()

        obj.components.each{ String key, UiObject child ->
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
//                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes)
                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, null, cloc.direct, cloc.attributes)
                if(!MATCH_ALL.equals(gattr.trim()))
                  groupAttributes.add(gattr.trim())
            }
        }

        CompositeLocator locator = obj.locator
        String self = JQueryBuilder.buildJQuerySelector(locator.tag, locator.text, locator.position, locator.direct, locator.attributes)
        StringBuffer sb = new StringBuffer(LENGTH)
        sb.append(self)
        //Do not need group locating if there is ID attribute in itself since ID is uniquely defined in jQuery
        if(locator.noIdIncluded() && groupAttributes.size() > 0){
          sb.append(HAS).append("(").append(groupAttributes.join(SELECTOR_SEPARATOR)).append(")")
        }

        return sb.toString()
    }

    //group locate strategy is kind of special since it requires the object and its children which it holds
    //must be a container or its inherited classes to call this
    def static String locate(Container obj){
        if(obj.locator instanceof BaseLocator)
            return DefaultLocateStrategy.locate(obj.locator)

        List<String> groupAttributes = new ArrayList<String>()

        obj.components.each{ String key, UiObject child ->
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
                String tagvns = cloc.tag
                if(cloc.namespace != null){
                  tagvns = "${cloc.namespace}:${cloc.tag}"
                }
                String gattr
                if(cloc.direct)
                    gattr= XPathBuilder.buildChildXPath(tagvns, cloc.text, cloc.position, cloc.attributes)
                else
                    gattr= XPathBuilder.buildDescendantXPath(tagvns, cloc.text, cloc.position, cloc.attributes)
              if(!(ANY_DESCENDANT.equals(gattr.trim()) || ANY_CHILD.equals(gattr.trim())))
                groupAttributes.add(gattr)
            }
        }

        CompositeLocator locator = obj.locator
        String otagns = locator.tag
        if(locator.namespace != null){
          otagns = "${locator.namespace}:${locator.tag}"
        }
//        String xpath = XPathBuilder.buildGroupXPath(locator.tag, locator.text, locator.position, locator.direct, locator.attributes) {List<String> list ->
        String xpath = XPathBuilder.buildGroupXPathWithHeader(otagns, locator.text, locator.position, locator.direct, locator.attributes, locator.header) {List<String> list ->
            if (!groupAttributes.isEmpty()) {
                list.addAll(groupAttributes)
            }
        }
//        if (locator.header != null && (locator.header.trim().length() > 0)) {
//            xpath = locator.header + xpath
 //       }

        if (locator.trailer != null && (locator.trailer.trim().length() > 0)) {
            xpath = xpath + locator.trailer
        }

        return xpath
    }

    def static String select(Repeat obj, int index){
      if(!obj.locator instanceof CompositeLocator)
        throw new InvalidLocatorException("${ERROR_MESSAGE} ${obj.uid}")

        List<String> groupAttributes = new ArrayList<String>()

        obj.components.each{ String key, UiObject child ->
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
//                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, cloc.position, cloc.direct, cloc.attributes)
                String gattr = JQueryBuilder.buildJQuerySelector(cloc.tag, cloc.text, null, cloc.direct, cloc.attributes)
                if(!MATCH_ALL.equals(gattr.trim()))
                  groupAttributes.add(gattr.trim())
            }
        }

        CompositeLocator locator = obj.locator
        String self = JQueryBuilder.buildJQuerySelector(locator.tag, locator.text, index, locator.direct, locator.attributes)
        StringBuffer sb = new StringBuffer(LENGTH)
        sb.append(self)
        //Do not need group locating if there is ID attribute in itself since ID is uniquely defined in jQuery
        if(locator.noIdIncluded() && groupAttributes.size() > 0){
          sb.append(HAS).append("(").append(groupAttributes.join(SELECTOR_SEPARATOR)).append(")")
        }

        return sb.toString()
    }

    //group locate strategy is kind of special since it requires the object and its children which it holds
    //must be a container or its inherited classes to call this
    def static String locate(Repeat obj, int index){
        if(obj.locator instanceof BaseLocator)
            return DefaultLocateStrategy.locate(obj.locator)

        List<String> groupAttributes = new ArrayList<String>()

        obj.components.each{ String key, UiObject child ->
            //can only use the child's information in the CompositeLocator
            //cannot use other Locator type for the timebeing
            if(child.locator instanceof CompositeLocator){
                CompositeLocator cloc = child.locator
                String tagvns = cloc.tag
                if(cloc.namespace != null){
                  tagvns = "${cloc.namespace}:${cloc.tag}"
                }
                String gattr
                if(cloc.direct)
                    gattr= XPathBuilder.buildChildXPath(tagvns, cloc.text, cloc.position, cloc.attributes)
                else
                    gattr= XPathBuilder.buildDescendantXPath(tagvns, cloc.text, cloc.position, cloc.attributes)
              if(!(ANY_DESCENDANT.equals(gattr.trim()) || ANY_CHILD.equals(gattr.trim())))
                groupAttributes.add(gattr)
            }
        }

        CompositeLocator locator = obj.locator
        String otagns = locator.tag
        if(locator.namespace != null){
          otagns = "${locator.namespace}:${locator.tag}"
        }
//        String xpath = XPathBuilder.buildGroupXPath(locator.tag, locator.text, locator.position, locator.direct, locator.attributes) {List<String> list ->
        String xpath = XPathBuilder.buildGroupXPathWithHeader(otagns, locator.text, index, locator.direct, locator.attributes, locator.header) {List<String> list ->
            if (!groupAttributes.isEmpty()) {
                list.addAll(groupAttributes)
            }
        }
//        if (locator.header != null && (locator.header.trim().length() > 0)) {
//            xpath = locator.header + xpath
 //       }

        if (locator.trailer != null && (locator.trailer.trim().length() > 0)) {
            xpath = xpath + locator.trailer
        }

        return xpath
    }
}