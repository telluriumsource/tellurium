package org.telluriumsource.ui.object

import org.telluriumsource.dsl.UiID
import org.telluriumsource.dsl.WorkflowContext

import org.json.simple.JSONObject

/**
 *  Prototype for windows
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Window  extends Container{

    public static final String ID = "id"
    private String id;

    public static final String NAME = "name"
    private String name;

    public static final String TITLE = "title"
    private String title;

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "Window")
        if(id != null && id.trim().length() > 0)
          jso.put(ID, id)
        if(name != null && name.trim().length() > 0)
          jso.put(NAME, name)
        if(title != null && title.trim().length() > 0)
          jso.put(TITLE, title)
      }
    }

    void openWindow(String url, Closure c){
        c(name, url)
    }

    void selectWindow(Closure c){
        c(name)
    }

    void closeWindow(Closure c){
        c(name)
    }

    boolean getWhetherThisWindowMatchWindowExpression(String target, Closure c){
       c(name, target)
    }

    void waitForPopUp(int timeout, Closure c){
//       c(name, timeout)
        c(name)
    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid){

        //if not child listed, return itself
        if(uiid.size() < 1)
            return this

        String child = uiid.pop()

        //otherwise, try to find its child
        UiObject cobj = components.get(child)

        //if found the object
        if(cobj != null){
            //do not need to update the reference locator for frame/iframe since it
            //includes a total new html body
            if(uiid.size() < 1){
                //not more child needs to be found
                return cobj
            }else{
                //recursively call walkTo until the object is found
                return cobj.walkTo(context, uiid)
            }
        }else{

            //cannot find the object
            println(i18nBundle.getMessage("Container.CannotFindUIObject" , {[child , this.uid]}))

            return null
        }
    }

}