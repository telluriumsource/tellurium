package org.telluriumsource.ut

import org.telluriumsource.dsl.UiID

/**
 * Unit tests for UiID
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class UiID_UT extends GroovyTestCase{

    void testConvertToUiId(){
        String id = "input"
        UiID result = UiID.convertToUiID(id)
        assertEquals(1, result.size())
        assertEquals("input", result.pop())

        id="main.input"
        result = UiID.convertToUiID(id)
        assertEquals(2, result.size())
        assertEquals("main", result.pop())
        assertEquals("input", result.pop())
        assertEquals(0, result.size())

        id = "table1[1][2]"
        result = UiID.convertToUiID(id)
        assertEquals(2, result.size())
        assertEquals("table1", result.pop())
        assertEquals("_1_2", result.pop())
        assertEquals(0, result.size())

        id = "main.input.table2[3][5]"
        result = UiID.convertToUiID(id)
        assertEquals(4, result.size())
        assertEquals("main", result.pop())
        assertEquals("input", result.pop())
        assertEquals("table2", result.pop())
        assertEquals("_3_5", result.pop())
        assertEquals(0, result.size())

        id = "main.list[5]"
        result = UiID.convertToUiID(id)
        assertEquals(3, result.size())
        assertEquals("main", result.pop())
        assertEquals("list", result.pop())
        assertEquals("_5", result.pop())
        assertEquals(0, result.size())

        id ="main.table1[3][5].[1][2]"
        result = UiID.convertToUiID(id)
        assertEquals(4, result.size())
        assertEquals("main", result.pop())
        assertEquals("table1", result.pop())
        assertEquals("_3_5", result.pop())
        assertEquals("_1_2", result.pop())
        assertEquals(0, result.size())
    }

    public void testGetUiID(){
      String id ="main.table1[3][5].[1][2]"
      UiID result = UiID.convertToUiID(id)
      assertEquals(4, result.size())
      assertEquals("main.table1._3_5._1_2", result.getUiID())
    }
}