package org.telluriumsource.ui.widget.dojo.object

import org.telluriumsource.ui.widget.dojo.DojoWidget
import org.telluriumsource.dsl.WorkflowContext
import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 12, 2009
 * 
 */
class JtvTabContainer extends DojoWidget{
    public static final String PLACE_HOLDER = "\\?";

    private List<String> tabList = new ArrayList<String>()

    private String groupLocate(){
        StringBuffer sb = new StringBuffer()
        for(String tab: tabList){
            sb.append(" and ").append("descendant::span[text()='${tab}']")
        }

        return sb.toString()
    }

    public void defineWidget() {
        String groupLocator = groupLocate()
        ui.Container(uid: "TabContainer", locator: "/div[contains(@id, 'jtv_widget_JtvTabContainer') and @wairole='tablist' ${groupLocator}]"){
            Container(uid: "dijitTab", locator: "/div[contains(@widgetid, 'jtv_widget_JtvTabButton') and contains(@class, 'dijitTab') and descendant::span[text()='?']]", respond: ["mouseOver", "mouseOut", "click"]){
                Container(uid: "innerTab", clocator: [tag: "div", class: "dijitTabInnerDiv", dojoattachpoint: "innerDiv"]){
                    Span(uid: "tab", clocator: [dojoattachpoint: "%%containerNode"])
                    Span(uid: "Close", clocator: [dojoattachpoint: "closeButtonNode", class: "closeImage"])
                }
            }
        }
    }

    //should override the click method to handle the place holder "?"
    protected void clickTab(String uid, String tab){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.click(){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            locator = locator.replaceFirst(PLACE_HOLDER, tab)
            eventHandler.click(locator, events)
        }
    }

    protected boolean isSelectedTab(String uid, String tab, String cssClass){
      WorkflowContext context = WorkflowContext.getDefaultContext()
       String[] strings = ui.walkTo(context, uid)?.hasCssClass(){ loc, classAttr ->
         String locator = locatorMapping(context, loc)
         locator = locator.replaceFirst(PLACE_HOLDER, tab)
         ((String)accessor.getAttribute(locator + classAttr ))?.split(" ")
        }

        if(strings?.length){
          for(i in 0..strings?.length){
           if(cssClass.equalsIgnoreCase(strings[i])){
             return true
           }
         }
       }
       return false
    }

    public void clickOnTab(String tabName){
        clickTab "TabContainer.dijitTab", tabName
    }

    public boolean isSelectedTab(String tabName, String className){
      isSelectedTab "TabContainer.dijitTab", tabName, className
    }

  public JSONObject toJSON() {
    return buildJSON() {jso ->
     jso.put(UI_TYPE, NAMESPACE + "_" + "JtvTabContainer")
   }
  }
}