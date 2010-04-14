package org.telluriumsource.dsl

import org.telluriumsource.ui.builder.UiObjectBuilderRegistry
import org.telluriumsource.exception.UiObjectNotFoundException
import org.telluriumsource.ui.object.*
import org.telluriumsource.exception.InvalidObjectTypeException

import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.framework.Environment;



class UiDslParser extends BuilderSupport{
       public static final String UID = "uid"
       public static final String REF = "ref"
       public static final String INCLUDE = "Include"
       protected IResourceBundle i18nBundle

       def registry = [:]

       //this should return a singleton class with default builders populated
       def UiObjectBuilderRegistry builderRegistry = new UiObjectBuilderRegistry()

       public UiDslParser(){
 		  i18nBundle = Environment.instance.myResourceBundle()
       }
       protected String nestObjectName(UiObject obj){
          String id
          if(obj.parent != null && obj.parent instanceof Table){
              id =  Table.internalId(obj.uid)
          }else if(obj.parent != null && obj.parent instanceof StandardTable){
              id =  StandardTable.internalId(obj.uid)
          }else{
              id = obj.uid
          }

          def op = obj

          while(op.parent != null){
            op = op.parent
            if(op.parent != null && op.parent instanceof Table){
                id = Table.internalId(op.uid) + "." + id
            }else if(op.parent != null && op.parent instanceof StandardTable){
                id = StandardTable.internalId(op.uid) + "." + id
            }else{
                id = op.uid + "." + id
            }
           }

          return id
       }

       public UiObject walkTo(WorkflowContext context, String id)
       {
          //if only one ui object in the registry, i.e., user only defined one UI module
          //in this case, the top id can be omitted, here we need to put it back
          if(registry.size() == 1){
            UiObject topobj = registry.values().asList().get(0)
            if(!id.startsWith("${topobj.uid}")){
              id = "${topobj.uid}.${id}"
            }
          }

          UiID uiid = UiID.convertToUiID(id)

          if(uiid.size() >= 1){
              String first = uiid.pop()
              //first object (i.e., the top object in a UI module) can be found from the registry
              UiObject fo = registry.get(first)
              if(fo != null){
                  return fo.walkTo(context, uiid)
              }else{
                  println(i18nBundle.getMessage("UiDslParser.CannotFindTopObject" , first))
                  return null
              }
          }

          println i18nBundle.getMessage("UiDslParser.IdNotEmpty")

          return null
       }

       def addUiObjectToRegistry(UiObject obj){

           registry.put(nestObjectName(obj), obj)
       }

      def removeUiObjectFromRegistry(UiObject obj){
          registry.remove(nestObjectName(obj));
      }

       UiObject findIncludedUiObject(Map map){
         String uid = map.get(UID)
         String ref = map.get(REF)
         WorkflowContext context = WorkflowContext.getDefaultContext()
         UiObject obj = walkTo(context, ref)
         if(obj == null)
             throw new UiObjectNotFoundException(i18nBundle.getMessage("UiDslParser.CannotFindUiObject" , ref))

         if(uid != null && (!uid.equalsIgnoreCase(obj.uid))){
         //IF UID is specified and is not equals to the referenced obj uid, we need to clone
         //the object
           obj = obj.clone()
           obj.uid = uid
         }

         return obj
       }

       protected void setParent(Object parent, Object child) {
           if(parent instanceof Container ){
               parent.add(child)
               child.parent = parent
           }
           if(parent instanceof Option){
               parent.add(child)
           }
       }

       protected Object createNode(Object name) {

           def builder = builderRegistry.getBuilder(name)

           if (builder != null) {
             def obj = builder.build(null, null)

             return obj
           }else{

             throw new InvalidObjectTypeException(i18nBundle.getMessage("UiDslParser.InvalidUIObject" , name))
           }

       }

      //should not come here for Our DSL
       protected Object createNode(Object name, Object value) {

           return null
       }

       protected Object createNode(Object name, Map map) {
         if (INCLUDE.equalsIgnoreCase(name)) {

           return this.findIncludedUiObject(map)

         }else {
           def builder = builderRegistry.getBuilder(name)

           if(builder != null){
                def obj =  builder.build(map, null)

                return obj
           }else{
              throw new InvalidObjectTypeException(i18nBundle.getMessage("UiDslParser.InvalidUIObject" , name))
           }
         }
       }

       protected Object createNode(Object name, Map map, Object value) {
           def builder = builderRegistry.getBuilder(name)

           if(builder != null){
               def obj =  builder.build(map, (Closure)value)

               return obj
           }else{
              throw new InvalidObjectTypeException(i18nBundle.getMessage("UiDslParser.InvalidUIObject" , name))
           }
       }

       protected void nodeCompleted(Object parent, Object node) {
          //when the node is completed and its parent is null, it means this node is at the top level
          if(parent == null){
               UiObject uo = (UiObject)node
               //only put the top level nodes into the registry
               registry.put(uo.uid, node)
          }

       }

   }