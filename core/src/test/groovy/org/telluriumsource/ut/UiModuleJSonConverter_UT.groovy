package org.telluriumsource.ut

import org.telluriumsource.module.GoogleSearchModule

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 31, 2009
 *
 */

public class UiModuleJSonConverter_UT extends GroovyTestCase {

  public void testToJSONString(){
    GoogleSearchModule gsm = new GoogleSearchModule();
    gsm.defineUi();

//    println gsm.jsonify("Google");
    println gsm.toString("Google");
  }
}
