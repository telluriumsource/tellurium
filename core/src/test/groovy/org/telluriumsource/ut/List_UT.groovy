package org.telluriumsource.ut

import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.ui.object.InputBox
import org.telluriumsource.ui.object.TextBox
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.object.UrlLink

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 2, 2008
 *
 */
class List_UT extends GroovyTestCase{

    public void testListXPath() {
        List1 list = new List1()
        list.defineUi()
        WorkflowContext context = WorkflowContext.getDefaultContext()
        UiObject obj = list.ui.walkTo(context, "sample[1].[1][1]")
        assertNotNull(obj)
        assertTrue(obj instanceof UrlLink)
        assertEquals("/descendant-or-self::div/table[1]/tbody/tr[child::td][1]/td[1]", context.getReferenceLocator())

        context = WorkflowContext.getDefaultContext()
        obj = list.ui.walkTo(context, "sample.T.text")
        assertNotNull(obj)
        assertTrue(obj instanceof TextBox)
        assertEquals("/descendant-or-self::div/div[1]", context.getReferenceLocator())

        context = WorkflowContext.getDefaultContext()
        obj = list.ui.walkTo(context, "sample[3].[1][1]")
        assertNotNull(obj)
        assertTrue(obj instanceof UrlLink)
        assertEquals("/descendant-or-self::div/table[2]/tbody/tr[child::td][1]/td[1]", context.getReferenceLocator())

        context = WorkflowContext.getDefaultContext()
        obj = list.ui.walkTo(context, "sample.Cool")
        assertNotNull(obj)
        assertTrue(obj instanceof InputBox)
        assertEquals("/descendant-or-self::div/input[@title=\"cool\"][1]", context.getReferenceLocator())

        context = WorkflowContext.getDefaultContext()
        obj = list.ui.walkTo(context, "sample[5].[1][1]")
        assertNotNull(obj)
        assertTrue(obj instanceof UrlLink)
        assertEquals("/descendant-or-self::div/table[3]/tbody/tr[child::td][1]/td[1]", context.getReferenceLocator())
   }

    public void testUserProfileList(){
      UserProfileList upl = new UserProfileList();
      upl.defineUi();
      upl.enableCssSelector();
      upl.dump("MainPanel1")
      upl.dump("MainPanel2")
//      upl.getListSize("MainPanel1.UserProfileDetails")
//      upl.getListSize("MainPanel2.UserProfileDetails")
      println upl.getLocator("MainPanel2.UserProfileDetails[2].Name")
    }

    public void testListSeparator(){
      List1 list = new List1()
      list.defineList()
      list.setUseCacheFlag(false)
      list.dump("A")
      list.dump("B")
      list.setUseCacheFlag(true)
      list.dump("A")
      list.dump("B")

      list.defineSeparatorList();
      list.dump("rotator")
      list.dump("rotator.tnails[6]")
    }
}