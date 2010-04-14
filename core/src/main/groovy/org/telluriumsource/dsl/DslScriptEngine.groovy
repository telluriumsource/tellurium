package org.telluriumsource.dsl

import org.telluriumsource.test.groovy.DslTelluriumGroovyTestCase

class DslScriptEngine extends DdDslContext {

  @Delegate
  private DslTelluriumGroovyTestCase aost = new DslTelluriumGroovyTestCase()
//    protected TelluriumFramework tellurium

/*
  protected def init() {
    tellurium = TelluriumSupport.addSupport()
    tellurium.start()
    aost.conn = tellurium.connector
    aost.connectSeleniumServer();
  }

  protected def connectUrl(String url) {
    aost.connectUrl(url)
  }
  */

  //try to delegate missing methods to the AostTestCase, if still could not find,
  //throw a MissingMethodException
/*
  protected def methodMissing(String name, args) {
    if ("init".equals(name))
      return init()
    if ("connectUrl".equals(name))
      return connectUrl(args)
    if ("shutDown".equals(name))
      return shutDown()

    if (DslTelluriumGroovyTestCase.metaClass.respondsTo(aost, name, args)) {
      return aost.invokeMethod(name, args)
    }

    throw new MissingMethodException(name, DslScriptEngine.class, args)
  }


  protected void shutDown() {
    if (tellurium != null)
      tellurium.stop()
  }
  */
}