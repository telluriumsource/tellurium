package org.telluriumsource.test;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.RepeatExampleModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 8, 2010
 */
public class RepeatExampleTestCase extends TelluriumMockJUnitTestCase {
   private static RepeatExampleModule rem;

    @BeforeClass
    public static void initUi() {
        registerHtmlBody("RepeatExample");
        rem = new RepeatExampleModule();
        rem.defineUi();
        useCssSelector(true);
        useTelluriumEngine(true);
        useTrace(true);
        useEngineLog(true);
    }

    @Before
    public void connectToLocal() {
        connect("RepeatExample");
    }

    @Test
    public void testRepeatForXPath(){
        useCssSelector(false);
        int num = rem.getRepeatNum("SailingForm.Section");
        assertEquals(2, num);
        num = rem.getRepeatNum("SailingForm.Section[1].Option");
        assertEquals(2, num);
        int size = rem.getListSize("SailingForm.Section[1].Option[1].Fares");
        assertEquals(2, size);
        String ship = rem.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Ship");
        assertEquals("A", ship);
        String departureTime = rem.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Departure.Time");
        assertEquals("08:00", departureTime);
        String arrivalTime = rem.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Arrival.Time");
        assertEquals("11:45", arrivalTime);
    }

    @Test
    public void testRepeatForSelector(){
        useCssSelector(true);
        int num = rem.getRepeatNum("SailingForm.Section");
        assertEquals(2, num);
        num = rem.getRepeatNum("SailingForm.Section[1].Option");
        assertEquals(2, num);
        int size = rem.getListSize("SailingForm.Section[1].Option[1].Fares");
        assertEquals(2, size);
        String ship = rem.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Ship");
        assertEquals("A", ship);
        String departureTime = rem.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Departure.Time");
        assertEquals("08:00", departureTime);
        String arrivalTime = rem.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Arrival.Time");
        assertEquals("11:45", arrivalTime);
    }

    @Test
    public void testRepeatForCache(){
        useEngineLog(true);
        int num = rem.getRepeatNum("SailingForm.Section");
        assertEquals(2, num);
        num = rem.getRepeatNum("SailingForm.Section[1].Option");
        assertEquals(2, num);
        int size = rem.getListSize("SailingForm.Section[1].Option[1].Fares");
        assertEquals(2, size);
        String ship = rem.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Ship");
        assertEquals("A", ship);
        String departureTime = rem.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Departure.Time");
        assertEquals("08:00", departureTime);
        String arrivalTime = rem.getText("SailingForm.Section[1].Option[1].Details.ShipInfo.Arrival.Time");
        assertEquals("11:45", arrivalTime);
    }

    @Test
    public void testShowUI(){
        useEngineLog(true);
//        rem.show("SailingForm", 5000);
        rem.startShow("SailingForm");
        rem.endShow("SailingForm");
    }

    @Test
    public void testGetHTMLSource(){
        useEngineLog(true);
        rem.getHTMLSource("SailingForm");
    }

    @Test
    public void testIsElementPresent(){
        useEngineLog(true);
        boolean present = rem.isElementPresent("SailingForm.Section[3]");
        assertFalse(present);
    }

    @AfterClass
    public static void tearDown(){
        showTrace();
    }    
}
