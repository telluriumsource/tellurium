package org.telluriumsource.ut

import org.telluriumsource.entity.config.Config
import org.stringtree.json.JSONReader

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 8, 2010
 * 
 */
class Config_UT extends GroovyTestCase {

  public void testToJSON(){
    Config conf = new Config();
    conf.getDefault();
    println conf.toJSON().toString();
  }

  public void testFromJSON(){
    String json = """{"tellurium":{"test":{"result":{"reporter":"XMLResultReporter","filename":"TestResult.output","output":"Console"},"exception":{"filenamePattern":"Screenshot?.png","captureScreenshot":false},"execution":{"trace":false}},"accessor":{"checkElement":false},"embeddedserver":{"port":"4444","browserSessionReuse":false,"debugMode":false,"ensureCleanSession":false,"interactive":false,"avoidProxy":false,"timeoutInSeconds":30,"runInternally":true,"trustAllSSLCertificates":true,"useMultiWindows":false,"userExtension":"","profile":""},"uiobject":{"builder":{}},"eventhandler":{"checkElement":false,"extraEvent":false},"i18n":{"locale":"en_US"},"connector":{"baseUrl":"http://localhost:8080","port":"4444","browser":"*chrome","customClass":"","serverHost":"localhost","options":""},"bundle":{"maxMacroCmd":5,"useMacroCommand":true},"datadriven":{"dataprovider":{"reader":"PipeFileReader"}},"widget":{"module":{"included":""}}}}""";
    println json;
    JSONReader reader = new JSONReader();
    Map map = reader.read(json);
    Config conf = new Config(map);
    assertNotNull(conf);
    println conf.toJSON().toString();
  }

  public void testToProperties(){
    Config conf = new Config();
    conf.getDefault();
    Properties props = conf.toProperties();
    props.each {key, val ->
      println("key: " + key + ", val: " + val);
    }
  }
}
