package org.telluriumsource.ui.locator

import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.exception.InvalidLocatorException
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.framework.Environment;



/**
 * convert different locator data structures to actual locators or partial locators
 * delegate to different locate strategies, like a handler chain.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class LocatorProcessor{
    public static final String CANNOT_HANDLE_LOCATOR= "Cannot handle locator"
  	protected IResourceBundle i18nBundle

  	public LocatorProcessor(){
    	  i18nBundle = Environment.instance.myResourceBundle()

    }

    def String locate(WorkflowContext context, locator, int index){
        if(locator == null)
            return ""

        if(locator instanceof BaseLocator)
            return DefaultLocateStrategy.locate(locator, index)

        if(locator instanceof CompositeLocator){
            if(context.isUseCssSelector())
              return CompositeLocateStrategy.select(locator, index)

            return CompositeLocateStrategy.locate(locator, index)
        }

        throw new InvalidLocatorException(i18nBundle.getMessage("LocatorProcessor.CannnotHandleLocator" , locator.getClass()))
    }

    def String locate(WorkflowContext context, locator){
        if(locator == null)
            return ""

        if(locator instanceof BaseLocator)
            return DefaultLocateStrategy.locate(locator)

        if(locator instanceof CompositeLocator){
            if(context.isUseCssSelector())
              return CompositeLocateStrategy.select(locator)

            return CompositeLocateStrategy.locate(locator)

        }

        throw new InvalidLocatorException(i18nBundle.getMessage("LocatorProcessor.CannnotHandleLocator" , locator.getClass()))
    }
}