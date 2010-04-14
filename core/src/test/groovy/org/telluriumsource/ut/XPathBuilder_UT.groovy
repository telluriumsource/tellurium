package org.telluriumsource.ut

import org.telluriumsource.ui.locator.XPathBuilder

/**
 *   Test case for XPathBuilder
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class XPathBuilder_UT extends GroovyTestCase{

  void testBuildXPath() {
    String result = XPathBuilder.buildXPath(null, null, null, null)
    assertEquals("/descendant-or-self::*", result)
    result = XPathBuilder.buildXPath("input", null, null, null)
    assertEquals("/descendant-or-self::input", result)
    result = XPathBuilder.buildXPath(null, "Submit", null, null)
    assertEquals("/descendant-or-self::*[normalize-space(text())=normalize-space(\"Submit\")]", result)
    result = XPathBuilder.buildXPath(null, "%%Submit", null, null)
    assertEquals("/descendant-or-self::*[contains(text(),\"Submit\")]", result)
    result = XPathBuilder.buildXPath("input", null, "3", null)
    assertEquals("/descendant-or-self::input[position()=3]", result)
    result = XPathBuilder.buildXPath("input", "Submit", "3", null)
    assertEquals("/descendant-or-self::input[normalize-space(text())=normalize-space(\"Submit\") and position()=3]", result)
    result = XPathBuilder.buildXPath("input", "Submit", "3", [class: "button"])
    assertEquals("/descendant-or-self::input[normalize-space(text())=normalize-space(\"Submit\") and position()=3 and @class=\"button\"]", result)
    result = XPathBuilder.buildXPath("input", "Submit", "3", [class: "button", id: "%%input1"])
    assertEquals("/descendant-or-self::input[normalize-space(text())=normalize-space(\"Submit\") and position()=3 and @class=\"button\" and contains(@id,\"input1\")]", result)

    result = XPathBuilder.buildDescendantXPath("input", "Submit", "3", [class: "button"])
    assertEquals("descendant::input[normalize-space(text())=normalize-space(\"Submit\") and position()=3 and @class=\"button\"]", result)
    result = XPathBuilder.buildChildXPath("input", "Submit", "3", [class: "button"])
    assertEquals("child::input[normalize-space(text())=normalize-space(\"Submit\") and position()=3 and @class=\"button\"]", result)
  }

  void testBuidXPathFromAttributes() {
    Map attributes = [:]

    String result = XPathBuilder.buidXPathFromAttributes(attributes)
    assertEquals("", result)

    attributes.put("tag", "")
    result = XPathBuilder.buidXPathFromAttributes(attributes)
    assertEquals("/*", result)

    attributes.put("tag", "tbody")
    result = XPathBuilder.buidXPathFromAttributes(attributes)
    assertEquals("/tbody", result)

    attributes.put("text", "goodwill")
    result = XPathBuilder.buidXPathFromAttributes(attributes)
    assertEquals("/tbody[normalize-space(text())=normalize-space(\"goodwill\")]", result)

    attributes.put("position", "3")
    result = XPathBuilder.buidXPathFromAttributes(attributes)
    assertEquals("/tbody[normalize-space(text())=normalize-space(\"goodwill\") and position()=3]", result)

    attributes.put("id", "tbodyID1")
    result = XPathBuilder.buidXPathFromAttributes(attributes)
    assertEquals("/tbody[normalize-space(text())=normalize-space(\"goodwill\") and position()=3 and @id=\"tbodyID1\"]", result)

    attributes.put("class", null)
    result = XPathBuilder.buidXPathFromAttributes(attributes)
    assertEquals("/tbody[normalize-space(text())=normalize-space(\"goodwill\") and position()=3 and @id=\"tbodyID1\" and @class]", result)

  }

  void testPartialMatch(){

    String result = XPathBuilder.buildXPath(null, "%%Submit", null, null)
    assertEquals("/descendant-or-self::*[contains(text(),\"Submit\")]", result)

    result =  XPathBuilder.buildXPath(null, "^Submit", null, null)
    assertEquals("/descendant-or-self::*[starts-with(text(),\"Submit\")]", result)

    result =  XPathBuilder.buildXPath(null, "\$Submit", null, null)
    assertEquals("/descendant-or-self::*[contains(text(),\"Submit\")]", result)

    result =  XPathBuilder.buildXPath(null, "*Submit", null, null)
    assertEquals("/descendant-or-self::*[contains(text(),\"Submit\")]", result)    
  }

  void testNamespace(){
    String result = XPathBuilder.buildDescendantXPath("te:input", "Submit", "3", [class: "button"])
    assertEquals("descendant::te:input[normalize-space(text())=normalize-space(\"Submit\") and position()=3 and @class=\"button\"]", result)
    result = XPathBuilder.buildChildXPath("te:input", "Submit", "3", [class: "button"])
    assertEquals("child::te:input[normalize-space(text())=normalize-space(\"Submit\") and position()=3 and @class=\"button\"]", result)
  }
}