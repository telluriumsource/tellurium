package module

import org.telluriumsource.dsl.DslContext

/**
 * Example to demonstrate the Date Picker widget at
 *
 *   http://turtle.dojotoolkit.org/~dylan/dojo/tests/widget/demo_DatePicker.html
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 3, 2008
 * 
 */
class DatePickerDemo extends DslContext{
    
    public void defineUi() {
        ui.Form(uid: "dropdown", clocator: [:], group: "true"){
            TextBox(uid: "label", clocator: [tag: "h4", text: "Dropdown:"])
            InputBox(uid: "input", clocator: [dojoattachpoint: "valueInputNode"])
            Image(uid: "selectDate", clocator: [title: "select a date", dojoattachpoint: "containerDropdownNode", alt: "date"])
            DOJO_DatePicker(uid: "datePicker", clocator: [tag: "div", dojoattachpoint: "subWidgetContainerNode"])
        }
    }

    public void clickWidget(){
        click "dropdown.selectDate"
        pause 500
    }

    public void increaseWeek(){
        onWidget "dropdown.datePicker", increaseWeek
        pause 500
    }

    public void decreaseWeek(){
        onWidget "dropdown.datePicker", decreaseWeek
        pause 500
    }

    public void increaseMonth(){
        onWidget "dropdown.datePicker", increaseMonth
        pause 500
    }

    public void decreaseMonth(){
        onWidget "dropdown.datePicker", decreaseMonth
        pause 500
    }

    public void selectPrevYear(){
        onWidget "dropdown.datePicker", selectPrevYear
        pause 500
    }

    public void selectNextYear(){
        onWidget "dropdown.datePicker", selectNextYear
        pause 500
    }

    public String getCurrentYear(){
        return onWidget("dropdown.datePicker", getCurrentYear)
    }

    public String getCurrentMonth(){
        return onWidget("dropdown.datePicker", getCurrentMonth)
    }

    public List peekDaysForCurrentMonth(){
        return onWidget("dropdown.datePicker", peekDaysForCurrentMonth)
    }

    public void selectDaysForCurrentMonth(int day){
        onWidget "dropdown.datePicker", selectDayForCurrentMonth, day
    }
}