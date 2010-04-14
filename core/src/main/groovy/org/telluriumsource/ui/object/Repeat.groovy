package org.telluriumsource.ui.object

import org.json.simple.JSONObject
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.dsl.UiID
import org.telluriumsource.ui.locator.LocatorProcessor
import org.telluriumsource.ui.locator.GroupLocateStrategy
import org.telluriumsource.component.data.Accessor

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 3, 2010
 * 
 */
class Repeat extends Container {

    public JSONObject toJSON() {

      return this.buildJSON(){jso ->
        jso.put(UI_TYPE, "Repeat")
      }
    }

    protected void groupLocating(WorkflowContext context, int index){
      //TODO: double check this! seems something is wrong here for Repeat, if useGroupInfo is true,
      //it may return the locator twice 
      if (this.useGroupInfo) {
        if(context.isUseCssSelector()){
          context.appendReferenceLocator(GroupLocateStrategy.select(this, index))
        }else{
          //need to use group information to help us locate the container xpath
          context.appendReferenceLocator(GroupLocateStrategy.locate(this, index))
        }
      } else {
        //do not use the group information, process as regular
        def lp = new LocatorProcessor()
        context.appendReferenceLocator(lp.locate(context, this.locator, index))
      }
    }

    public int getRepeatNum(Closure c){

      return c(this.locator)
    }

    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid){
      
        String index = uiid.pop()

        String part = index.replaceFirst('_', '')

        int nindex = Integer.parseInt(part)

        //if not child listed, return itself
        if(uiid.size() < 1){
            if(this.locator != null){
                groupLocating(context, nindex)
                context.noMoreProcess = true;
            }
          
            return this
        }

        String child = uiid.pop()

        //otherwise, try to find its child
        UiObject cobj = components.get(child)

        //if found the object
        if(cobj != null){
            //update reference locator by append the relative locator for this container
            if(this.locator != null){
//              context.appendReferenceLocatorForUiObject(this)
                groupLocating(context, nindex)

            }
            if(uiid.size() < 1){
                //not more child needs to be found
                return cobj
            }else{
                //recursively call walkTo until the object is found
                return cobj.walkTo(context, uiid)
            }
        }else{

            //cannot find the object

            println i18nBundle.getMessage("Container.CannotFindUIObject" , child , this.uid)

            return null
        }
    }
}
