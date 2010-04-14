package org.telluriumsource.ui.widget.dojo.object

import org.telluriumsource.ui.widget.dojo.DojoWidget
import org.json.simple.JSONObject

/**
 * Tellurium Widget object for the Dojo Date Picker widget
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 3, 2008
 * 
 */
class DatePicker extends DojoWidget{
    
    //the class attribute of the day
    protected static final String DAY_CLASS_PREV_MONTH = "previousMonth"
    protected static final String DAY_CLASS_CURRENT_MONTH = "currentMonth"
    protected static final String DAY_CLASS_NEXT_MONTH = "nextMonth"

    //i.e., number of columns for the standard table "calendar"
    protected static final int DAYS_PER_WEEK = 7
    //i.e., number of rows for the standard table "calendar"
    protected static final int WEEKS = 6
    
    public void defineWidget() {
        ui.Container(uid: "DatePicker", locator: "/div[@class='datePickerContainer' and child::table[@class='calendarContainer']]"){
            Container(uid: "Title", locator: "/table[@class='calendarContainer']/thead/tr/td[@class='monthWrapper']/table[@class='monthContainer']/tbody/tr/td[@class='monthLabelContainer']"){
                Icon(uid: "increaseWeek", locator: "/span[@dojoattachpoint='increaseWeekNode']")
                Icon(uid: "increaseMonth", locator: "/span[@dojoattachpoint='increaseMonthNode']")
                Icon(uid: "decreaseWeek", locator: "/span[@dojoattachpoint='decreaseWeekNode']")
                Icon(uid: "decreaseMonth", locator: "/span[@dojoattachpoint='decreaseMonthNode']")
                TextBox(uid: "monthLabel", locator: "/span[@dojoattachpoint='monthLabelNode']")   
            }
            StandardTable(uid: "calendar", locator: "/table[@class='calendarContainer']/tbody/tr/td/table[@class='calendarBodyContainer']"){
                TextBox(uid: "header: all", locator: "")
                ClickableUi(uid: "all", locator: "")
            }
            Container(uid: "year", locator: "/table[@class='calendarContainer']/tfoot/tr/td/table[@class='yearContainer']/tbody/tr/td/h3[@class='yearLabel']"){
                Span(uid: "prevYear", locator: "/span[@class='previousYear' and @dojoattachpoint='previousYearLabelNode']")
                TextBox(uid: "currentYear", locator: "/span[@class='selectedYear' and @dojoattachpoint='currentYearLabelNode']")
                Span(uid: "nextYear", locator: "/span[@class='nextYear' and @dojoattachpoint='nextYearLabelNode']")
            }
        }
    }

    public List<Integer> peekDaysForPrevMonth(){
       return peekDaysForMonth(DAY_CLASS_PREV_MONTH)
    }
    
    public List<Integer> peekDaysForCurrentMonth(){
       return peekDaysForMonth(DAY_CLASS_CURRENT_MONTH)
    }

    public List<Integer> peekDaysForNextMonth(){
       return peekDaysForMonth(DAY_CLASS_NEXT_MONTH)
    }

    protected List<Integer> peekDaysForMonth(String month){
        List<Integer> list = new ArrayList<Integer>()
        for(int i=1; i<=WEEKS; i++){
            for(int j=1; j<=DAYS_PER_WEEK; j++){
                //get the attribute for the day
                String tdattr = getAttribute("DatePicker.calendar[${i}][${j}]", "class")
                if(tdattr != null && tdattr.contains(month)){
                    String day = getText("DatePicker.calendar[${i}][${j}]")
                    list.add(Integer.parseInt(day))
                }
            }
        }

        return list
    }

    public void selectDayForPrevMonth(int day){
        selectDaysForMonth(DAY_CLASS_PREV_MONTH, day)
    }

    public void selectDayForCurrentMonth(int day){
        selectDaysForMonth(DAY_CLASS_CURRENT_MONTH, day)
    }

    public void selectDayForNextMonth(int day){
        selectDaysForMonth(DAY_CLASS_NEXT_MONTH, day)    
    }

    protected void selectDaysForMonth(String month, int dayInMonth){
        String dayAsString = Integer.toString(dayInMonth)

        for(int i=1; i<=WEEKS; i++){
            for(int j=1; j<=DAYS_PER_WEEK; j++){
                //get the attribute for the day
                String tdattr = getAttribute( "DatePicker.calendar[${i}][${j}]", "class")
                if(tdattr != null && tdattr.contains(month)){
                    String day = getText("DatePicker.calendar[${i}][${j}]")
                    if(dayAsString.equals(day)){
                        click "DatePicker.calendar[${i}][${j}]"
                    }
                }
            }
        }
    }

    public void increaseWeek(){
        click "DatePicker.Title.increaseWeek"
    }

    public void increaseMonth(){
        click "DatePicker.Title.increaseMonth"
    }

    public void decreaseWeek(){
        click "DatePicker.Title.decreaseWeek"
    }

    public void decreaseMonth(){
        click "DatePicker.Title.decreaseMonth"
    }

    public String getMonthLabel(){
        return getText("DatePicker.Title.monthLabel")
    }

    public String getCurrentMonth(){
        return getText("DatePicker.Title.monthLabel")
    }
    
    public String getCurrentYear(){
        return getText("DatePicker.year.currentYear")
    }

    public void selectPrevYear(){
        click "DatePicker.year.prevYear"
    }

    public void selectNextYear(){
        click "DatePicker.year.nextYear"
    }

  public JSONObject toJSON() {
       return buildJSON() {jso ->
        jso.put(UI_TYPE, NAMESPACE + "_" + "DatePicker")
      }
  }
}