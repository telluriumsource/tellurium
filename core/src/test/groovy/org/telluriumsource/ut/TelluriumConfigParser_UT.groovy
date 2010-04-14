package org.telluriumsource.ut

import org.telluriumsource.framework.config.TelluriumConfigParser

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 2, 2008
 *
 */
class TelluriumConfigParser_UT extends GroovyTestCase {

  public void testParse() {
    TelluriumConfigParser parser = new TelluriumConfigParser()
    parser.parse("config/TelluriumConfigForExcelReader.groovy")
    def config = parser.getProperty("conf")
    assertNotNull(config)
    assertEquals("4444", config.tellurium.embeddedserver.port)
  }

  public void testParseJSON() {
    String json =  """{"tellurium":{"test":{"result":{"reporter":"XMLResultReporter","filename":"TestResult.output","output":"Console"},"exception":{"filenamePattern":"Screenshot?.png","captureScreenshot":false},"execution":{"trace":false}},"accessor":{"checkElement":false},"embeddedserver":{"port":"4444","browserSessionReuse":false,"debugMode":false,"ensureCleanSession":false,"interactive":false,"avoidProxy":false,"timeoutInSeconds":30,"runInternally":true,"trustAllSSLCertificates":true,"useMultiWindows":false,"userExtension":"","profile":""},"uiobject":{"builder":{}},"eventhandler":{"checkElement":false,"extraEvent":false},"i18n":{"locale":"en_US"},"connector":{"baseUrl":"http://localhost:8080","port":"4444","browser":"*chrome","customClass":"","serverHost":"localhost","options":""},"bundle":{"maxMacroCmd":5,"useMacroCommand":true},"datadriven":{"dataprovider":{"reader":"PipeFileReader"}},"widget":{"module":{"included":""}}}}""";
    TelluriumConfigParser parser = new TelluriumConfigParser();
    parser.parseJSON(json);
    def config = parser.getProperty("conf")
    assertNotNull(config)
    assertEquals("4444", config.tellurium.embeddedserver.port)
    assertEquals("en_US", config.tellurium.i18n.locale)
  }
}