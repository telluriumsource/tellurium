package org.telluriumsource.ut

public class StandardTable_UT extends GroovyTestCase {

  void testTable1(){
      StandardTable1 table1 = new StandardTable1()
      table1.defineUi()
      String result = table1.getTableLocator("table1.header[2]")
      assertNotNull(result)
      assertEquals("//descendant-or-self::table[@id=\"std\"]/thead[1]/tr/td[2]/descendant-or-self::a[contains(text(),\"Filename\")]", result)
      result = table1.getTableLocator("table1[1][1][3]")
      assertEquals("//descendant-or-self::table[@id=\"std\"]/tbody[1]/tr[1]/td[3]/descendant-or-self::select[@name=\"can\"]", result)
      result = table1.getTableLocator("table1[1][1][4]")
      assertEquals("//descendant-or-self::table[@id=\"std\"]/tbody[1]/tr[1]/td[4]/descendant-or-self::input[@value=\"Search\" and @name=\"btn\" and @type=\"submit\"]", result)
      result = table1.getTableLocator("table1[2][3]")
      assertEquals("//descendant-or-self::table[@id=\"std\"]/tbody[1]/tr[2]/td[3]/descendant-or-self::input[@name=\"words\"]", result)
      result = table1.getTableLocator("table1[2][2][3]")
      assertEquals("//descendant-or-self::table[@id=\"std\"]/tbody[2]/tr[2]/td[3]/descendant-or-self::input[@name=\"without\"]", result)
      result = table1.getTableLocator("table1[2][2][1]")
      assertEquals("//descendant-or-self::table[@id=\"std\"]/tbody[2]/tr[2]/td[1]/descendant-or-self::input[@name=\"labels\"]", result)
      result = table1.getTableLocator("table1[2][1][2]")
      assertEquals("//descendant-or-self::table[@id=\"std\"]/tbody[2]/tr[1]/td[2]", result)
      result = table1.getTableLocator("table1.footer[2]")
      assertEquals("//descendant-or-self::table[@id=\"std\"]/tfoot[1]/tr/td[2]", result)
  }

  void testTable2(){
      StandardTable1 table2 = new StandardTable1()
      table2.defineUi()
      String result = table2.getTableLocator("table2[1][1]")
      assertNotNull(result)
      assertEquals("//descendant-or-self::table[@id=\"normal\"]/tbody[1]/tr[1]/td[1]/descendant-or-self::select[@name=\"can\" and @id=\"can\"]", result)
      result = table2.getTableLocator("table2[2][1]")
      assertEquals("//descendant-or-self::table[@id=\"normal\"]/tbody[1]/tr[2]/td[1]/descendant-or-self::span", result)
      result = table2.getTableLocator("table2[3][1]")
      assertEquals("//descendant-or-self::table[@id=\"normal\"]/tbody[1]/tr[3]/td[1]/descendant-or-self::input[@name=\"q\"]", result)
      result = table2.getTableLocator("table2[4][1]")
      assertEquals("//descendant-or-self::table[@id=\"normal\"]/tbody[1]/tr[4]/td[1]/descendant-or-self::input[@value=\"Search\" and @type=\"submit\"]", result)
  }

   void testDump(){
     StandardTable1 table = new StandardTable1()
     table.defineUi()

     table.dump("table1")
     table.dump("table2")
     table.enableCssSelector();
     table.setUseCacheFlag(false);
     table.dump("table1")
     table.dump("table2")
     table.setUseCacheFlag(true);
     table.dump("table1")
     table.dump("table2")
   }

   void testTableTagOverwrite(){
     StandardTable1 table = new StandardTable1()
     table.defineUi()

     table.dump("table3")     
   }
}