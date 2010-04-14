package org.telluriumsource.ui.widget

import org.telluriumsource.dsl.BaseDslContext
import org.telluriumsource.dsl.WorkflowContext

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 31, 2010
 * 
 */
class WidgetDslContext extends BaseDslContext {
  //the reference xpath for widget's parent
  private String pRef;

  public void updateParentRef(String ref) {
     this.pRef = ref

  }

  protected String locatorMapping(WorkflowContext context, loc) {
    return locatorMappingWithOption(context, loc, null)
  }

  protected String locatorMappingWithOption(WorkflowContext context, loc, optLoc) {
    //get ui object's locator
    String lcr = locatorProcessor.locate(context, loc)

    //widget's locator
    String wlc = locatorProcessor.locate(context, this.locator)

    //get the reference locator all the way to the ui object
    if (context.getReferenceLocator() != null) {
      context.appendReferenceLocator(lcr)
      lcr = context.getReferenceLocator()
    }

    if(optLoc != null)
        lcr = lcr + optLoc

    //append the object's locator to widget's locator
    lcr = wlc + lcr

    //add parent reference xpath
    if (pRef != null)
      lcr = pRef + lcr
    if(context.isUseCssSelector()){
//      lcr = optimizer.optimize(JQUERY_SELECTOR + lcr.trim())
      lcr = postProcessSelector(context, lcr.trim())
    } else {
      //make sure the xpath starts with "//"
      if (lcr != null && (!lcr.startsWith("//"))) {
        lcr = "/" + lcr
      }
    }

    return lcr
  }

}
