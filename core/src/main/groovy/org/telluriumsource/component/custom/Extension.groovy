package org.telluriumsource.component.custom

import org.telluriumsource.component.bundle.BundleProcessor

/**
 * Class to pass user custom methods to the delegator
 *
 * @author John.Jian.Fang@gmail.com
 *
 * Date: Mar 19, 2009
 *
 */

//public class Extension implements GroovyInterceptable {
public class Extension {

   private BundleProcessor cbp  = BundleProcessor.instance

   def methodMissing(String name, args) {
      return cbp.metaClass.invokeMethod(cbp, name, args)
   }
}
