package org.telluriumsource.ui.object

import org.telluriumsource.dsl.UiID
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.ui.locator.LocatorProcessor
import org.json.simple.JSONObject

/**
 * Module Menu
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 6, 2008
 *
 */
class SimpleMenu extends UiObject{
    public static final String TAG = "div"

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "SimpleMenu")
      }
    }

    Map<String, String> menuItemMap = new HashMap<String, String>()
    //map to hold the alias name for the menu item in the format of "alias name" : "menu item"
    Map<String, String> aliasMap

    def click(Closure c){
        c(null, respondToEvents)
    }

    def mouseOver(Closure c){
//        c(null, respondToEvents)
        c(null)
    }

    def mouseOut(Closure c){
//        c(null, respondToEvents)
        c(null)
    }

    public void addMenuItems(Map<String, String> menuItems){
        aliasMap = menuItems
        menuItems.each{ String key, String value ->
           menuItemMap.put(value, "descendant::*[text()=\"${value}\"]")
        }
//        List<String> mis =  (List<String>)menuItems.values().asList()
//        for(String menuItem : mis){
//            menuItemMap.put(menuItem, "descendant::*[text()=\"${menuItem}\"")
//        }
    }

    protected getMenuItemLocator(String menuItem){
        StringBuffer sb = new StringBuffer(256)
        sb.append("/table[")
        Collection<String> mixp = menuItemMap.values()
        sb.append(mixp.join(" and ")).append("]/tbody/tr[td[text()=\"${menuItem}\"]]")

        return sb.toString()
    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid) {

        //if not child listed, return itself
        if (uiid.size() < 1)
            return this

        //this is the alias name
        String child = uiid.pop()
        //try to find the real menu item
        if(aliasMap == null || aliasMap.isEmpty()){
            return new RuntimeException( "Error: cannot find menu item for ${child}")
        }

        String realItem = aliasMap.get(child)

        if(menuItemMap.get(realItem) == null){
            return new RuntimeException( "Error: cannot find menu item ${realItem}")
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {

            def lp = new LocatorProcessor()
                context.appendReferenceLocator(lp.locate(context, this.locator))
        }

        //append relative location to the locator
        String loc = getMenuItemLocator(realItem)
        context.appendReferenceLocator(loc)

        //For menu, do not allow child nodes, no more walk and just return itself
        return this
    }

}
