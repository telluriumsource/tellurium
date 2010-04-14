package org.telluriumsource.component.dispatch

import org.telluriumsource.component.client.SeleniumClient
import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.crosscut.trace.DefaultExecutionTracer
import org.telluriumsource.crosscut.trace.ExecutionTracer
import org.telluriumsource.framework.Environment
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.util.Helper

class Dispatcher implements Configurable {
    public static final String PLACE_HOLDER = "\\?"
    protected IResourceBundle i18nBundle ;

    private String filenamePattern = "Screenshot?.png";

    private SeleniumClient sc = new SeleniumClient();
    private ExecutionTracer tracer = new DefaultExecutionTracer();

    public Dispatcher(){
    	i18nBundle = Environment.instance.myResourceBundle()
    }
  
    public boolean isConnected(){
      //TODO: sometimes, the selenium client is not singleton ??  Fix it
      if(sc.client == null)
        sc = new SeleniumClient()
      
      if(sc.client == null || sc.client.getActiveSeleniumSession() == null)
        return false;

      return true;
    }

    private boolean isUseScreenshot(){
      return Environment.instance.isUseScreenshot();
    }

    private boolean isUseTrace(){
      return Environment.instance.isUseTrace();
    }

    def methodMissing(String name, args) {
      WorkflowContext context = args[0]
      String apiname = context.getApiName();
      Object[] params = Helper.removeFirst(args);

      //TODO: sometimes, the selenium client is not singleton ??  Fix it
      //here reset selenium client to use the new singleton instance which has the client set
      if (sc.client == null || sc.client.getActiveSeleniumSession() == null)
        sc = new SeleniumClient()

      try {
        long beforeTime = System.currentTimeMillis()
        def result = sc.client.getActiveSeleniumSession().metaClass.invokeMethod(sc.client.getActiveSeleniumSession(), name, params)
        long duration = System.currentTimeMillis() - beforeTime
        if (isUseTrace())
          tracer.publish(apiname, beforeTime, duration)

        return result
      } catch (Exception e) {
        if (isUseScreenshot()) {
          long timestamp = System.currentTimeMillis()
          String filename = filenamePattern.replaceFirst(PLACE_HOLDER, "${timestamp}")
          sc.client.getActiveSeleniumSession().captureScreenshot(filename)
          println i18nBundle.getMessage("Dispatcher.ExceptionMessage", e.getMessage(), filename)
        }
        //dump Environment variables
        println Environment.instance.toString();

        throw e
      }
  }

 /*
 protected def methodMissing(String name, args) {
    return invokeMethod(name, args)
  }
*/

  def showTrace() {
    tracer.report()
  }

  public void warn(String message){
    tracer.warn(message);
  }

  public void log(String message){
    tracer.log(message); 
  }

}