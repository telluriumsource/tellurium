package org.telluriumsource.ut

import org.telluriumsource.dsl.WorkflowContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 4, 2009
 * 
 */

public class WorkflowContext_UT extends GroovyTestCase {

  public void testUiid(){
    WorkflowContext context = WorkflowContext.getDefaultContext()
    context.setNewUid("main.table1[3][5].[1][2]")
    String uid = context.getUid()
    assertEquals("main.table1[3][5].[1][2]", uid)
    uid = context.popUid()
    assertEquals("[1][2]", uid)
    uid = context.popUid()
    assertEquals("[3][5]", uid)
    uid = context.popUid()
    assertEquals("table1", uid)
    uid = context.popUid()
    assertEquals("main", uid)
    context.pushUid("main")
    uid = context.getUid()
    assertEquals("main", uid)
    context.pushUid("table1")
    uid = context.getUid()
    assertEquals("main.table1", uid)
    context.directPushUid("[3][5]")
    uid = context.getUid()
    assertEquals("main.table1[3][5]", uid)
    context.pushUid("[1][2]")
    uid = context.getUid()
    assertEquals("main.table1[3][5].[1][2]", uid)
  }

}