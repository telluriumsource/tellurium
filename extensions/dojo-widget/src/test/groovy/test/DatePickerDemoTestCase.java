package test;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import module.DatePickerDemo;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import java.util.List;

/**
 * Test dojo Data picker
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 4, 2008
 */

public class DatePickerDemoTestCase extends TelluriumJavaTestCase {
    private static DatePickerDemo dpd;

    @BeforeClass
    public static void initUi() {
        dpd = new DatePickerDemo();
        dpd.defineUi();
        connectSeleniumServer();
    }

    @Test
    public void testClickDataPicker(){
        connectUrl("http://turtle.dojotoolkit.org/~dylan/dojo/tests/widget/demo_DatePicker.html");
        dpd.clickWidget();
        dpd.increaseWeek();
        dpd.increaseMonth();
        dpd.decreaseMonth();
        dpd.decreaseWeek();
        dpd.selectPrevYear();
        dpd.selectNextYear();
        List days = (List) dpd.peekDaysForCurrentMonth();
        assertNotNull(days);
        assertFalse(days.isEmpty());
        for(Object day: days){
            int dayInMonth = (Integer)day;
            System.out.println(dayInMonth);
        }
        dpd.selectDaysForCurrentMonth((Integer)days.get(0));
        String currentYear = (String) dpd.getCurrentYear();
        System.out.println(currentYear);
        String currentMonth = (String) dpd.getCurrentMonth();
        System.out.println(currentMonth);
        dpd.clickWidget();
    }

}
