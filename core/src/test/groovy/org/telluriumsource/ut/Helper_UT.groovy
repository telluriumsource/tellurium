package org.telluriumsource.ut

import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.util.Helper

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Mar 19, 2010
 * 
 */
class Helper_UT extends GroovyTestCase{

  public void testClone(){
    WorkflowContext context = new WorkflowContext();
    context.appendReferenceLocator("[id!=hp_table]");
    context.noMoreProcess = true;

    WorkflowContext cxt = Helper.clone(context);
    assertNotNull(cxt);
    assertTrue(cxt instanceof WorkflowContext);
    assertEquals("[id!=hp_table]", context.getReferenceLocator());
  }
}
