package org.telluriumsource.ut

import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.ui.object.Button
import org.telluriumsource.ui.object.TextBox
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.ui.object.UrlLink

/**
 * Unit tests for Table
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Table_UT extends GroovyTestCase{

   void testTable1(){
       Table1 table1 = new Table1()
       table1.defineUi()
//       assertNotNull(table1.findObject("table1"))
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table1.ui.walkTo(context, "table1[1][1]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[child::td][1]/td[1]")
   }

    void testTable2(){
       Table2 table2 = new Table2()
       table2.defineUi()
//       assertNotNull(table2.findObject("table1"))
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table2.ui.walkTo(context, "table1[1][1]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[child::td][1]/td[1]")
   }

   void testTable3(){
       Table3 table3 = new Table3()
       table3.defineUi()
//       assertNotNull(table3.findObject("table1"))
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table3.ui.walkTo(context, "table1[3][4]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[child::td][3]/td[4]")
   }

   void testTable4(){
       Table4 table4 = new Table4()
       table4.defineUi()
 //      assertNotNull(table4.findObject("table1"))
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table4.ui.walkTo(context, "main.table1[2][1]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[child::td][2]/td[1]")
   }

   void testTable5(){
       Table5 table5 = new Table5()
       table5.defineUi()
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table5.ui.walkTo(context, "main.table1.header[1]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals("//table/tbody/tr[child::th]/th[1]", context.getReferenceLocator())
       context = WorkflowContext.getDefaultContext()
       obj = table5.ui.walkTo(context, "table1[2][2]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[child::td][2]/td[2]")
   }

   void testTable6(){
       Table6 table6 = new Table6()
       table6.defineUi()
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table6.ui.walkTo(context, "main.table1.header[1]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals("//table/tbody/tr[child::th]/th[1]", context.getReferenceLocator())
       
       context = WorkflowContext.getDefaultContext()
       obj = table6.ui.walkTo(context, "main.table1.header[2]")
       assertNotNull(obj)
       assertTrue(obj instanceof TextBox)
       assertEquals("//table/tbody/tr[child::th]/th[2]", context.getReferenceLocator())

       context = WorkflowContext.getDefaultContext()
       obj = table6.ui.walkTo(context, "table1[2][2]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[child::td][2]/td[2]")
   }

   void testTable7(){
       Table7 table7 = new Table7()
       table7.defineUi()
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table7.ui.walkTo(context, "main.table1.header[1]")
       assertNotNull(obj)
       assertTrue(obj instanceof TextBox)
       assertEquals("//table/tbody/tr[child::th]/th[1]", context.getReferenceLocator())

       context = WorkflowContext.getDefaultContext()
       obj = table7.ui.walkTo(context, "main.table1.header[2]")
       assertNotNull(obj)
       assertTrue(obj instanceof UrlLink)
       assertEquals("//table/tbody/tr[child::th]/th[2]", context.getReferenceLocator())

       context = WorkflowContext.getDefaultContext()
       obj = table7.ui.walkTo(context, "table1[2][2]")
       assertNotNull(obj)
       assertTrue(obj instanceof Button)
       assertEquals(context.getReferenceLocator(), "//table/tbody/tr[child::td][2]/td[2]")
   }

   void testTable8(){
       Table8 table8 = new Table8()
       table8.defineUi()
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table8.ui.walkTo(context, "IdMenu[1][1")
       assertNotNull(obj)
       assertTrue(obj instanceof TextBox)
       assertEquals("/descendant-or-self::div[@class='popup' and @id='pop_0']/table[descendant::*[normalize-space(text())=normalize-space(\"Sort Up\")] and descendant::*[normalize-space(text())=normalize-space(\"Hide Column\")] and descendant::*[normalize-space(text())=normalize-space(\"Sort Down\")]]/tbody/tr[child::td][1]/td[1]", context.getReferenceLocator())
   }

  void testTable9(){
       Table9 table9 = new Table9()
       table9.defineUi()
       WorkflowContext context = WorkflowContext.getDefaultContext()
       UiObject obj = table9.ui.walkTo(context, "IdMenu[1][1")
       assertNotNull(obj)
       assertTrue(obj instanceof TextBox)
//       assertEquals("/descendant-or-self::table[@id=\"someId\"]/tbody[@id=\"tbody1_Id\"]/tr[child::td][1]/td[1]", context.getReferenceLocator())
       assertEquals("/descendant-or-self::table[@id=\"someId\"]/tbody/tr[child::td][1]/td[1]", context.getReferenceLocator()) 
  }

  void testTable10(){
      Table10 table10 = new Table10()
      table10.defineUi()
      String result = table10.getTableLocator("Actions.header[1]")
      assertNotNull(result)
      assertEquals("//descendant-or-self::table[@id=\"ipclb1\" and @class=\"coolBar\"]/tbody/tr[child::th]/th[1]", result)
      result = table10.getTableLocator("Actions[1][1]")
      assertEquals("//descendant-or-self::table[@id=\"ipclb1\" and @class=\"coolBar\"]/tbody/tr[child::td][1]/td[1]/descendant-or-self::input[@type=\"checkbox\" and @name=\"EntityKey\"]", result)
      result = table10.getTableLocator("Actions[2][2]")
      assertEquals("//descendant-or-self::table[@id=\"ipclb1\" and @class=\"coolBar\"]/tbody/tr[child::td][2]/td[2]/descendant-or-self::a[normalize-space(text())=normalize-space(\"Y100000542\")]", result)
      result = table10.getTableLocator("Actions[2][3]")
      assertEquals("//descendant-or-self::table[@id=\"ipclb1\" and @class=\"coolBar\"]/tbody/tr[child::td][2]/td[3]/descendant-or-self::td[@class=\"abc\"]", result)
      result = table10.getTableLocator("Actions[3][4]")
      assertEquals("//descendant-or-self::table[@id=\"ipclb1\" and @class=\"coolBar\"]/tbody/tr[child::td][3]/td[4]/descendant-or-self::td[@class=\"abc\"]", result)
  }

  void testTable11(){
    Table11 table11 = new Table11()
    table11.defineUi()
    String result = table11.getXPath("Actions.header[1]")
    assertNotNull(result)
    assertEquals("//descendant-or-self::*[@id=\"ipclb1\" and @class=\"coolBar\"]/tbody/tr[child::th]/th[1]", result)

    result = table11.getXPath("Test")
    assertEquals("//descendant-or-self::*[@id=\"xyz\"]", result)
  }

  void testTable12(){
    Table12 table12 = new Table12()
    table12.defineUI()
    table12.disableCssSelector()
    String result = table12.getLocator("OrderDetail[1][1]")
    assertNotNull(result)
    assertEquals("//descendant-or-self::table[@class=\"table\"]/tbody/tr[child::td][1]/td[1]", result)
    table12.enableCssSelector()
    result = table12.getLocator("OrderDetail[1][1]")
    assertNotNull(result)
    assertEquals("jquery=table.table > tbody > tr:has(td):eq(0) > td:eq(0)", result)
    table12.disableCssSelector()
    table12.dump("chromeDoctorSearchResultsresultstable")
  }
}