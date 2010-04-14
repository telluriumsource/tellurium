package org.telluriumsource.test.groovy

import org.telluriumsource.framework.bootstrap.TelluriumSupport
import org.telluriumsource.framework.config.CustomConfig
import org.telluriumsource.component.connector.SeleniumConnector
import org.telluriumsource.framework.TelluriumFramework
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle;

import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass

/**
 * Groovy Test NG Test Case
 *
 * @author John.Jian.Fang@gmail.com
 *
 * Date: Mar 9, 2009
 *
 */

abstract public class TelluriumGroovyTestNGTestCase {
  //custom configuration
  protected CustomConfig customConfig = null
  protected IResourceBundle i18nBundle

  protected SeleniumConnector conn;
  protected TelluriumFramework tellurium

  public TelluriumGroovyTestNGTestCase(){
	  i18nBundle = Environment.instance.myResourceBundle()
  }
  public SeleniumConnector getConnector() {
    return conn;
  }

  abstract public void initUi()

  public void openUrl(String url) {
    getConnector().connectSeleniumServer()
    getConnector().connectUrl(url)
  }
  public IResourceBundle geti18nBundle()
  {
	return this.i18nBundle;
  }
  public void connectUrl(String url) {
    getConnector().connectUrl(url)
  }

  public void connectSeleniumServer() {
    getConnector().connectSeleniumServer()
  }

  public void disconnectSeleniumServer() {
    getConnector().disconnectSeleniumServer()
  }

  public void setCustomConfig(boolean runInternally, int port, String browser,
                              boolean useMultiWindows, String profileLocation) {
    customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation)
  }

  public void setCustomConfig(boolean runInternally, int port, String browser,
                              boolean useMultiWindows, String profileLocation, String serverHost) {
    customConfig = new CustomConfig(runInternally, port, browser, useMultiWindows, profileLocation, serverHost)
  }

  @BeforeClass
  protected void setUpForClass() {
    tellurium = TelluriumSupport.addSupport()
    tellurium.start(customConfig)
    conn = tellurium.connector
    initUi()
  }

  @AfterClass
  protected void tearDownForClass() {
    if (tellurium != null)
      tellurium.stop()
  }
}