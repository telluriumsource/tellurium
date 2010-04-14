package org.telluriumsource.framework.config

import org.telluriumsource.component.data.Accessor
import org.telluriumsource.ui.builder.UiObjectBuilder
import org.telluriumsource.ui.builder.UiObjectBuilderRegistry

import org.telluriumsource.component.connector.SeleniumConnector
import org.telluriumsource.test.ddt.DataProvider
import org.telluriumsource.test.ddt.mapping.io.CSVDataReader
import org.telluriumsource.test.ddt.mapping.io.ExcelDataReader;
import org.telluriumsource.test.ddt.mapping.io.PipeDataReader
import org.telluriumsource.component.dispatch.Dispatcher
import org.telluriumsource.component.event.EventHandler
import org.telluriumsource.server.EmbeddedSeleniumServer
import org.telluriumsource.ui.widget.WidgetConfigurator
import org.telluriumsource.test.report.*

import org.telluriumsource.component.bundle.BundleProcessor
import org.telluriumsource.framework.Environment
import org.telluriumsource.exception.ConfigNotFoundException

/**
 * Tellurium Configurator
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 3, 2008
 *
 */
class TelluriumConfigurator extends TelluriumConfigParser implements Configurator {

  protected void checkConfig(String name){
    String key = name.substring(5)
    def obj = this.props.get(key)
    if(obj == null){
       throw new ConfigNotFoundException(i18nBundle.getMessage("TelluriumConfigurator.ConfigNotFound.default" ,  key, "http://code.google.com/p/aost/wiki/TelluriumConfig070", "Tellurium user group at http://groups.google.com/group/tellurium-users" ))
    }
  }

  protected void configEmbeddedServer(EmbeddedSeleniumServer server) {
    checkConfig("conf.tellurium.embeddedserver.port")
    server.setProperty("port", Integer.parseInt(conf.tellurium.embeddedserver.port))

    checkConfig("conf.tellurium.embeddedserver.useMultiWindows")
    server.setProperty("useMultiWindows", conf.tellurium.embeddedserver.useMultiWindows)

    checkConfig("conf.tellurium.embeddedserver.trustAllSSLCertificates")
    server.setProperty("trustAllSSLCertificates", conf.tellurium.embeddedserver.trustAllSSLCertificates)

    checkConfig("conf.tellurium.embeddedserver.runInternally")
    server.setProperty("runSeleniumServerInternally", conf.tellurium.embeddedserver.runInternally)

    checkConfig("conf.tellurium.embeddedserver.profile")
    server.setProperty("profileLocation", conf.tellurium.embeddedserver.profile)

    checkConfig("conf.tellurium.embeddedserver.userExtension")
    server.setProperty("userExtension", conf.tellurium.embeddedserver.userExtension)
  }

  protected void configEmbeddedServerDefaultValues(EmbeddedSeleniumServer server) {
    server.setProperty("port", 4444)
    server.setProperty("useMultiWindows", false)
    server.setProperty("runSeleniumServerInternally", true)
  }

  protected configBundleProcessor(BundleProcessor processor) {
//    processor.setProperty("maxMacroCmd", conf.tellurium.bundle.maxMacroCmd)
//    processor.setProperty("exploitBundle", conf.tellurium.bundle.useMacroCommand)
  }

  protected configBundleProcessorDefaultValues(BundleProcessor processor) {
//    processor.setProperty("maxMacroCmd", 5)
//    processor.setProperty("exploitBundle", false)
  }

  protected void configSeleniumConnector(SeleniumConnector connector) {
    checkConfig("conf.tellurium.connector.serverHost")
    connector.setProperty("seleniumServerHost", conf.tellurium.connector.serverHost)

    checkConfig("conf.tellurium.connector.port")
    connector.setProperty("port", Integer.parseInt(conf.tellurium.connector.port))

    checkConfig("conf.tellurium.connector.baseUrl")
    connector.setProperty("baseURL", conf.tellurium.connector.baseUrl)

    checkConfig("conf.tellurium.connector.browser")
    connector.setProperty("browser", conf.tellurium.connector.browser)

    checkConfig("conf.tellurium.embeddedserver.userExtension")
    connector.setProperty("userExtension", conf.tellurium.embeddedserver.userExtension)

    checkConfig("conf.tellurium.connector.customClass")
    String clazz = conf.tellurium.connector.customClass
    if (clazz != null && clazz.trim().length() > 0)
      connector.setProperty("customClass", Class.forName(clazz).newInstance())

    checkConfig("conf.tellurium.connector.options")
    String options = conf.tellurium.connector.options
    if (options != null && options.trim().length() > 0) {
      connector.setProperty("options", options);
    }
  }

  protected void configSeleniumConnectorDefaultValues(SeleniumConnector connector) {
    connector.setProperty("seleniumServerHost", "localhost")
    connector.setProperty("port", 4444)
    connector.setProperty("baseURL", "http://localhost:8080")
    connector.setProperty("browser", "*chrome")
    connector.setProperty("customClass", null)
    connector.setProperty("options", null)
  }

  protected void configDataProvider(DataProvider dataProvider) {
    checkConfig("conf.tellurium.datadriven.dataprovider.reader")
    if ("PipeFileReader".equalsIgnoreCase(conf.tellurium.datadriven.dataprovider.reader)) {
      dataProvider.setProperty("reader", new PipeDataReader())
    } else if ("CSVFileReader".equalsIgnoreCase(conf.tellurium.datadriven.dataprovider.reader)) {
      dataProvider.setProperty("reader", new CSVDataReader())
    } else if ("ExcelFileReader".equalsIgnoreCase(conf.tellurium.datadriven.dataprovider.reader)) {
      dataProvider.setProperty("reader", new ExcelDataReader())
    } else {
      println i18nBundle.getMessage("TelluriumConfigurator.UnsupportedReader", conf.tellurium.datadriven.dataprovider.reader)
    }
  }

  protected void configDataProviderDefaultValues(DataProvider dataProvider) {
    dataProvider.setProperty("reader", new PipeDataReader())
  }

  protected void configResultListener(DefaultResultListener resultListener) {
    checkConfig("conf.tellurium.test.result.reporter")
    checkConfig("conf.tellurium.test.result.output")
    if ("SimpleResultReporter".equalsIgnoreCase(conf.tellurium.test.result.reporter)) {
      resultListener.setProperty("reporter", new SimpleResultReporter())
    }
    if ("XMLResultReporter".equalsIgnoreCase(conf.tellurium.test.result.reporter)) {
      resultListener.setProperty("reporter", new XMLResultReporter())
    }
    if ("StreamXMLResultReporter".equalsIgnoreCase(conf.tellurium.test.result.reporter)) {
      resultListener.setProperty("reporter", new StreamXMLResultReporter())
    }
    if ("Console".equalsIgnoreCase(conf.tellurium.test.result.output)) {
      resultListener.setProperty("output", new ConsoleOutput())
    }
    if ("File".equalsIgnoreCase(conf.tellurium.test.result.output)) {
      resultListener.setProperty("output", new FileOutput())
    }
  }

  protected void configResultListenerDefaultValues(DefaultResultListener resultListener) {
    resultListener.setProperty("reporter", new XMLResultReporter())
    resultListener.setProperty("output", new ConsoleOutput())
  }

  protected void configFileOutput(FileOutput fileOutput) {
    checkConfig("conf.tellurium.test.result.filename")
    fileOutput.setProperty("fileName", conf.tellurium.test.result.filename)
  }

  protected void configFileOutputDefaultValues(FileOutput fileOutput) {
    fileOutput.setProperty("fileName", "TestResult.output")
  }

  protected void configUiObjectBuilder(UiObjectBuilderRegistry uobRegistry) {
//    ui object builder is a special case, which could be empty
//    checkConfig("conf.tellurium.uiobject.builder")
    Map builders = conf.tellurium.uiobject.builder

    if (builders != null && (!builders.isEmpty())) {
      builders.each {key, value ->
        UiObjectBuilder builder = (UiObjectBuilder) Class.forName(value).newInstance()
        uobRegistry.registerBuilder(key, builder)
      }
    }
  }

  protected void configUiObjectBuilderDefaultValues(UiObjectBuilderRegistry uobRegistry) {

  }

  protected void configWidgetModule(WidgetConfigurator wgConfigurator) {
    checkConfig("conf.tellurium.widget.module.included")
    wgConfigurator.configWidgetModule(conf.tellurium.widget.module.included)
  }

  protected void configWidgetModuleDefaultValues(WidgetConfigurator wgConfigurator) {

  }

  protected void configEventHandler(EventHandler eventHandler) {
    checkConfig("conf.tellurium.eventhandler.checkElement")
    checkConfig("conf.tellurium.eventhandler.extraEvent")

    if (conf.tellurium.eventhandler.checkElement) {
      eventHandler.mustCheckElement()
    } else {
      eventHandler.notCheckElement()
    }
    if (conf.tellurium.eventhandler.extraEvent) {
      eventHandler.useExtraEvent()
    } else {
      eventHandler.noExtraEvent()
    }
  }

  protected void configEventHandlerDefaultValues(EventHandler eventHandler) {
//        eventHandler.mustCheckElement()
//        eventHandler.useExtraEvent()
    eventHandler.notCheckElement()
    eventHandler.noExtraEvent()
  }

  protected void configAccessor(Accessor accessor) {
    checkConfig("conf.tellurium.accessor.checkElement")

    if (conf.tellurium.accessor.checkElement) {
      accessor.mustCheckElement()
    } else {
      accessor.notCheckElement()
    }
  }

  protected void configAccessorDefaultValues(Accessor accessor) {
    accessor.mustCheckElement()
  }

  protected void configDispatcher(Dispatcher dispatcher) {
    checkConfig("conf.tellurium.test.exception.filenamePattern")

//    dispatcher.captureScreenshot = conf.tellurium.test.exception.captureScreenshot
    dispatcher.filenamePattern = conf.tellurium.test.exception.filenamePattern
//    dispatcher.trace = conf.tellurium.test.execution.trace
  }

  protected void configDispatcherDefaultValues(Dispatcher dispatcher) {
//    dispatcher.captureScreenshot = false
    dispatcher.filenamePattern = "Screenshot?.png"
//    dispatcher.trace = false
  }

  protected void configEnvironment(Environment env) {
    checkConfig("conf.tellurium.bundle.maxMacroCmd")
    checkConfig("conf.tellurium.bundle.useMacroCommand")
    checkConfig("conf.tellurium.test.execution.trace")
    checkConfig("conf.tellurium.test.exception.captureScreenshot")
    checkConfig("conf.tellurium.i18n.locale")
    
//    env.setProperty("maxMacroCmd", conf.tellurium.bundle.maxMacroCmd);
    env.useMaxMacroCmd(conf.tellurium.bundle.maxMacroCmd);
    env.setProperty("exploitBundle", conf.tellurium.bundle.useMacroCommand);
    env.setProperty("trace", conf.tellurium.test.execution.trace);
    env.setProperty("captureScreenshot", conf.tellurium.test.exception.captureScreenshot);
    env.useLocale(conf.tellurium.i18n.locale);
  }

  protected void configEnvironmentDefaultValues(Environment env) {
//    env.setProperty("maxMacroCmd", 5);   
    env.useMaxMacroCmd(5);
    env.setProperty("exploitBundle", false);
    env.setProperty("trace", false);
    env.setProperty("captureScreenshot", false);
    env.useLocale("en_US");
//    env.setProperty("locale", "en_US");
  }

  public void config(Configurable configurable) {

    if (conf != null) {
      if (configurable instanceof EmbeddedSeleniumServer) {
        println i18nBundle.getMessage("TelluriumConfigurator.EmbeddedSeleniumServer")
        configEmbeddedServer(configurable)
      } else if (configurable instanceof SeleniumConnector) {
        println i18nBundle.getMessage("TelluriumConfigurator.SeleniumClient")
        configSeleniumConnector(configurable)
      } else if (configurable instanceof DataProvider) {
        println i18nBundle.getMessage("TelluriumConfigurator.DataProvider")
        configDataProvider(configurable)
      } else if (configurable instanceof DefaultResultListener) {
        println i18nBundle.getMessage("TelluriumConfigurator.ResultListener")
        configResultListener(configurable)
      } else if (configurable instanceof FileOutput) {
        println i18nBundle.getMessage("TelluriumConfigurator.FileOutput")
        configFileOutput(configurable)
      } else if (configurable instanceof UiObjectBuilderRegistry) {
        println i18nBundle.getMessage("TelluriumConfigurator.UIObjectBuilder")
        configUiObjectBuilder(configurable)
      } else if (configurable instanceof WidgetConfigurator) {
        println i18nBundle.getMessage("TelluriumConfigurator.WidgetModules");
        configWidgetModule(configurable)
      } else if (configurable instanceof EventHandler) {
        println i18nBundle.getMessage("TelluriumConfigurator.EventHandler");
        configEventHandler(configurable)
      } else if (configurable instanceof Accessor) {
        println i18nBundle.getMessage("TelluriumConfigurator.DataAccessor");
        configAccessor(configurable)
      } else if (configurable instanceof Dispatcher) {
        println i18nBundle.getMessage("TelluriumConfigurator.Dispatcher");
        configDispatcher(configurable)
      } else if (configurable instanceof BundleProcessor) {
        println i18nBundle.getMessage("TelluriumConfigurator.Bundle")
        configBundleProcessor(configurable)
      } else if (configurable instanceof Environment) {
        configEnvironment(configurable)
      } else {
        println i18nBundle.getMessage("TelluriumConfigurator.UnsupportedType");
      }
    } else {
      //use default values instead
      if (configurable instanceof EmbeddedSeleniumServer) {
        println i18nBundle.getMessage("TelluriumConfigurator.EmbeddedSeleniumServer.default")
        configEmbeddedServerDefaultValues(configurable)
      } else if (configurable instanceof SeleniumConnector) {
        println i18nBundle.getMessage("TelluriumConfigurator.SeleniumClient.default")
        configSeleniumConnectorDefaultValues(configurable)
      } else if (configurable instanceof DataProvider) {
        println i18nBundle.getMessage("TelluriumConfigurator.DataProvider.default")
        configDataProviderDefaultValues(configurable)
      } else if (configurable instanceof DefaultResultListener) {
        println i18nBundle.getMessage("TelluriumConfigurator.ResultListener.default")
        configResultListenerDefaultValues(configurable)
      } else if (configurable instanceof FileOutput) {
        println i18nBundle.getMessage("TelluriumConfigurator.FileOutput.default")
        configFileOutputDefaultValues(configurable)
      } else if (configurable instanceof UiObjectBuilderRegistry) {
        println i18nBundle.getMessage("TelluriumConfigurator.UIObjectBuilder.default")
        configUiObjectBuilderDefaultValues(configurable)
      } else if (configurable instanceof WidgetConfigurator) {
        println i18nBundle.getMessage("TelluriumConfigurator.WidgetConfigurator.default")
        configWidgetModuleDefaultValues(configurable)
      } else if (configurable instanceof EventHandler) {
        println i18nBundle.getMessage("TelluriumConfigurator.EventHandler.default")
        configEventHandlerDefaultValues(configurable)
      } else if (configurable instanceof Accessor) {
        println i18nBundle.getMessage("TelluriumConfigurator.DataAccessor.default")
        configAccessorDefaultValues(configurable)
      } else if (configurable instanceof Dispatcher) {
        println i18nBundle.getMessage("TelluriumConfigurator.Dispatcher.default")
        configDispatcherDefaultValues(configurable)
      } else if (configurable instanceof BundleProcessor) {
        println i18nBundle.getMessage("TelluriumConfigurator.Bundle.default")
        configBundleProcessorDefaultValues(configurable)
      } else if (configurable instanceof Environment) {
        configEnvironmentDefaultValues(configurable)
      } else {
        println i18nBundle.getMessage("TelluriumConfigurator.UnsupportedType");
      }

    }

  }

}