package org.telluriumsource.ui.locator

/**
 *   Automcatically generate relate xpath based on composite elements in the
 *   UI object
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class CompositeLocateStrategy {

  def static boolean canHandle(locator) {
    if (locator instanceof CompositeLocator)
      return true
    else
      return false
  }

  public static String locate(CompositeLocator locator) {

//    String xpath = XPathBuilder.buildXPath(locator.tag, locator.text, locator.position, locator.attributes)
//    String xpath = XPathBuilder.buildOptionalXPath(locator.tag, locator.text, locator.position, locator.direct, locator.attributes)
    String tagvns = locator.tag
    if(locator.namespace != null){
      tagvns = "${locator.namespace}:${locator.tag}"
    }
    String xpath = XPathBuilder.buildOptionalXPathWithHeader(tagvns, locator.text, locator.position, locator.direct, locator.attributes, locator.header)
//    if (locator.header != null && (locator.header.trim().length() > 0)) {
//      xpath = locator.header + xpath
//    }

    if (locator.trailer != null && (locator.trailer.trim().length() > 0)) {
      xpath = xpath + locator.trailer
    }

    return xpath
  }

  public static String locate(CompositeLocator locator, int index) {

//    String xpath = XPathBuilder.buildXPath(locator.tag, locator.text, locator.position, locator.attributes)
//    String xpath = XPathBuilder.buildOptionalXPath(locator.tag, locator.text, locator.position, locator.direct, locator.attributes)
    String tagvns = locator.tag
    if(locator.namespace != null){
      tagvns = "${locator.namespace}:${locator.tag}"
    }
    String xpath = XPathBuilder.buildOptionalXPathWithHeader(tagvns, locator.text, index, locator.direct, locator.attributes, locator.header)
//    if (locator.header != null && (locator.header.trim().length() > 0)) {
//      xpath = locator.header + xpath
//    }

    if (locator.trailer != null && (locator.trailer.trim().length() > 0)) {
      xpath = xpath + locator.trailer
    }

    return xpath
  }

  //use jQuery Selector instead of xpath
  public static String select(CompositeLocator locator){

    String jqsel = JQueryBuilder.buildJQuerySelector(locator.tag, locator.text, locator.position, locator.direct, locator.attributes)
    if(locator.header != null && locator.header.trim().length() > 0){
      jqsel = JQueryBuilder.convHeader(locator.header) + jqsel
    }
    if(locator.trailer != null && locator.trailer.trim().length() > 0){
      jqsel = jqsel + JQueryBuilder.convTrailer(locator.trailer)
    }

    return jqsel
  }

  public static String select(CompositeLocator locator, int index){

    String jqsel = JQueryBuilder.buildJQuerySelector(locator.tag, locator.text, index, locator.direct, locator.attributes)
    if(locator.header != null && locator.header.trim().length() > 0){
      jqsel = JQueryBuilder.convHeader(locator.header) + jqsel
    }
    if(locator.trailer != null && locator.trailer.trim().length() > 0){
      jqsel = jqsel + JQueryBuilder.convTrailer(locator.trailer)
    }

    return jqsel
  }

}