package org.telluriumsource.ui.object

import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.dsl.UiID
import org.telluriumsource.component.data.Accessor
import org.json.simple.JSONObject

/**
 *
 * Option hold all optional UIs
 * There are limitations for use option. If you use option, you cannot use group locating concept for the current
 * UI object since the informaion is not determinstic and will be changed at run time
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 7, 2008
 *
 */
class Option extends UiObject{

    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "Option")
      }
    }

    //since we useString map, the component name must be unique
    def components = new LinkedList<UiObject>()

    def add(UiObject component){
        components.add(component)
    }

    //note that we may get back mulitiple results
    protected java.util.List<UiObject> getComponents(String id){
        return components.findAll { UiObject element ->
            id.equals(element.getUid())
        }
    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid){

        //get the reference locator and store it
        String refenernceLocator = context.getReferenceLocator()

        //the option should always use the same Uid as its direct children
        //i.e., it just passes through and forward to its children.
        //as a result, we should not do the following
        //String child = uiid.pop()

        //Try to find all its children with the same uiid
        java.util.List<UiObject> children = getComponents(this.uid)
        if(children != null && children.size() > 0){
            Accessor accessor = new Accessor()
//            children.each { UiObject child ->
            for(UiObject child : children){

                UiID uiidclone = uiid.clone()

                //keep walking until get the last UiOjbect
                UiObject lastOne = child.walkTo(context, uiidclone)

                //get the whole xpath to the last UiObject
                String guess = context.getReferenceLocator()
                if(!guess.startsWith("//")){
                    guess =  "/" + guess
                }
                //check the current DOM to see if this xpath does exist
                int count = accessor.getXpathCount(WorkflowContext.getDefaultContext(), guess)
                //found the xpath for the given UIID
                if(count == 1){
                     return lastOne
                }else{
                    //cannot find, or there are errors, try the next one
                    //need to reset the reference locator
                    context.setReferenceLocator(refenernceLocator)
                }
            }
        }else{
        	println i18nBundle.getMessage("Container.NoUIObjectForOption", {[this.uid , this.uid]})
        	return null
        }
        println i18nBundle.getMessage("Container.CannotFindXPath", {this.uid})
        return null
    }
}