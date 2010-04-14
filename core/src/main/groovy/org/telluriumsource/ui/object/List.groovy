package org.telluriumsource.ui.object

import org.telluriumsource.component.data.Accessor
import org.telluriumsource.dsl.UiID
import org.telluriumsource.dsl.WorkflowContext
import org.json.simple.JSONObject
import org.telluriumsource.ui.locator.CompositeLocator
import org.telluriumsource.ui.locator.XPathBuilder
import org.telluriumsource.ui.locator.JQueryBuilder
import org.telluriumsource.udl.MetaData
import org.telluriumsource.udl.ListMetaData
import org.telluriumsource.ui.routing.RTree
import org.telluriumsource.exception.InvalidUidException

/**
 * Abstracted class for a list, which holds one dimension array of Ui objects
 * similar to Table, but table is two dimensions.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class List extends Container {

    public static final String ALL_MATCH = "ALL";

    public static final String SEPARATOR = "separator"
    //the separator for the list, it is empty by default
    protected String separator = ""
  
    //Routing tree
    RTree rTree;

    @Override
    public JSONObject toJSON() {

      return buildJSON() {jso ->
        jso.put(UI_TYPE, "List")
        if(separator != null && separator.trim().length() > 0)
          jso.put(SEPARATOR, this.separator)
      }
    }

    protected TextBox defaultUi = new TextBox()

    @Override
    def add(UiObject component) {
        MetaData metaData = component.metaData;
        if(metaData instanceof ListMetaData){
            components.put(metaData.getId(), component);
            if(this.rTree == null){
              this.rTree = new RTree();
              this.rTree.preBuild();
            }
            this.rTree.insert(component);
        }else{
           println i18nBundle.getMessage("Container.InvalidID" , {component.uid})
        }
    }

    //should validate the uid before call this to convert it to internal representation
    public static String internalId(String id) {

        //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

        return "_${upperId}"
    }

    public UiObject locateChild(String id){
      return this.rTree.route(id);
    }

    public boolean validId(String id) {
        //UID cannot be empty
        if (id == null || (id.trim().length() <= 0))
            return false

        //convert to upper case so that it is case insensitive
        String upperId = id.trim().toUpperCase()

        //check match all case
        if (ALL_MATCH.equals(upperId))
            return true
        return (upperId ==~ /[0-9]+/)
    }

    protected String buildLocatorWithoutPosition(CompositeLocator locator){
       return XPathBuilder.buildXPathWithoutPosition(locator.getTag(), locator.getText(), locator.getAttributes())
    }

    protected String buildJQuerySelectorWithoutPosition(CompositeLocator locator){
       return JQueryBuilder.buildJQuerySelectorWithoutPosition(locator.getTag(), locator.getText(), locator.getAttributes())
    }

    String getListLocator(String key, UiObject obj){
      ListMetaData meta = (ListMetaData)obj.metaData;
      String index = meta.getIndex().getValue();
      if(separator != null && separator.trim().size() > 0){
        if("any".equalsIgnoreCase(index)){
          return this.getAnyLocatorWithSeparator(obj);
        }else if("first".equalsIgnoreCase(index)){
          return this.getFirstLocatorWithSeparator();
        }else if("last".equalsIgnoreCase(index)){
          return this.getLastLocatorWithSeparator();
        }else if(key ==~ /[0-9]+/){
          return this.getLocatorByIndexWithSeparator(key);
        }else if(index ==~ /[0-9]+/){
          return this.getLocatorByIndexWithSeparator(index);
        }else{
          //TODO: rename Container.InvalidID to UiObject.InvalidID
          throw new InvalidUidException(i18nBundle.getMessage("Container.InvalidID" , key));
        }
      }else{
       if("any".equalsIgnoreCase(index)){
          return this.getAnyLocatorWithoutSeparator(obj);
        }else if("first".equalsIgnoreCase(index)){
          return this.getFirstLocatorWithoutSeparator(obj);
        }else if("last".equalsIgnoreCase(index)){
          return this.getLastLocatorWithoutSeparator(obj);
        }else if(key ==~ /[0-9]+/){
          return this.getLocatorByIndexWithoutSeparator(key);
        }else if(index ==~ /[0-9]+/){
          return this.getLocatorByIndexWithoutSeparator(index);
        }else{
          //TODO: rename Container.InvalidID to UiObject.InvalidID
          throw new InvalidUidException(i18nBundle.getMessage("Container.InvalidID" , key));
        }
      }
    }

    protected String getAnyLocatorWithSeparator(UiObject obj){
      String loc = this.buildLocatorWithoutPosition(obj.locator);
      if(this.namespace != null && this.namespace.trim().length() > 0){
        return "/${this.namespace}:" + separator + "[${loc}]"
      }

      return "/" + separator + "[${loc}]"
    }

    protected String getAnyLocatorWithoutSeparator(UiObject obj){
      String loc = this.buildLocatorWithoutPosition(obj.locator);
      
      return "/${loc}";
    }

    protected String getFirstLocatorWithSeparator(){
      if (this.namespace != null && this.namespace.trim().length() > 0) {
        return "/${this.namespace}:" + separator + "[1]"
      }
      
      return "/" + separator + "[1]"
    }

    protected String getFirstLocatorWithoutSeparator(UiObject obj){
      String loc = this.buildLocatorWithoutPosition(obj.locator);

      return "/${loc}[1]";      
    }

    protected String getLastLocatorWithSeparator(){
      if (this.namespace != null && this.namespace.trim().length() > 0) {
        return "/${this.namespace}:" + separator + "[last()]"
      }

      return "/" + separator + "[last()]"
    }

    protected String getLastLocatorWithoutSeparator(UiObject obj){
      String loc = this.buildLocatorWithoutPosition(obj.locator);

      return "/${loc}[last()]";
    }

    protected String getLocatorByIndexWithSeparator(String index){
        if(this.namespace != null && this.namespace.trim().length() > 0){
          return "/${this.namespace}:" + separator + "[${index}]";
        }

        return "/" + separator + "[${index}]";
    }

    protected String getLocatorByIndexWithoutSeparator(String index){
        Map<String, Integer> locs = new HashMap<String, Integer>();
        String last = null;
        int inx = Integer.parseInt(index);
        for (int i = 1; i <= inx; i++) {
            UiObject obj = this.locateChild("${i}");
            String pl = this.buildLocatorWithoutPosition(obj.locator)
            Integer occur = locs.get(pl)
            if (occur == null) {
                locs.put(pl, 1)
            } else {
                locs.put(pl, occur + 1)
            }
//            if (i == index) {
            if (i == inx) {
                last = pl
            }
        }

          Integer lastOccur = locs.get(last)


        //force to be direct child (if consider List trailer)
        if(this.namespace != null && this.namespace.trim().length() > 0){
          return "/${this.namespace}:${last}[${lastOccur}]"
        }
      
        return "/${last}[${lastOccur}]"
    }
  
    String getListSelector(String index, UiObject obj){
      String key = index;
      if(this.rTree.isId(index)){
        key = obj.metaData.index.value;
      }

      if(separator != null && separator.trim().size() > 0){
        if("any".equalsIgnoreCase(key)){
          return this.getAnySelectorWithSeparator(obj);
        }else if("first".equalsIgnoreCase(key)){
          return this.getFirstSelectorWithSeparator();
        }else if("last".equalsIgnoreCase(key)){
          return this.getLastSelectorWithSeparator();
        }else if(key ==~ /[0-9]+/){
          return this.getSelectorByIndexWithSeparator(key);
        }else{
          //TODO: rename Container.InvalidID to UiObject.InvalidID
          throw new InvalidUidException(i18nBundle.getMessage("Container.InvalidID" , key));
        }
      }else{
       if("any".equalsIgnoreCase(key)){
          return this.getAnySelectorWithoutSeparator(obj);
        }else if("first".equalsIgnoreCase(key) ){
          return this.getFirstSelectorWithoutSeparator(obj);
        }else if("last".equalsIgnoreCase(key)){
          return this.getLastSelectorWithoutSeparator(obj);
        }else if(key ==~ /[0-9]+/){
          return this.getSelectorByIndexWithoutSeparator(key);
        }else{
          //TODO: rename Container.InvalidID to UiObject.InvalidID
          throw new InvalidUidException(i18nBundle.getMessage("Container.InvalidID" , key));
        }
      }
    }

    protected String getAnySelectorWithSeparator(UiObject obj){
      String sel = this.buildJQuerySelectorWithoutPosition(obj.locator);

      return " > " + separator + "has[${sel}]"
    }

    protected String getAnySelectorWithoutSeparator(UiObject obj){

      return this.buildJQuerySelectorWithoutPosition(obj.locator);
    }

    protected String getFirstSelectorWithSeparator(){

      return " > " + separator + ":first";
    }

    protected String getFirstSelectorWithoutSeparator(UiObject obj){
      String sel = this.buildJQuerySelectorWithoutPosition(obj.locator);

      return " > ${sel}:first";
    }

    protected String getLastSelectorWithSeparator(){

      return " > " + separator + ":last"
    }

    protected String getLastSelectorWithoutSeparator(UiObject obj){
      String sel = this.buildJQuerySelectorWithoutPosition(obj.locator);

      return " > ${sel}:last";
    }

    protected String getSelectorByIndexWithSeparator(String index){
        int inx = Integer.parseInt(index) - 1;
        return " > " + separator + ":eq(${inx})";
    }

    protected String getSelectorByIndexWithoutSeparator(String index){
        Map<String, Integer> locs = new HashMap<String, Integer>();
        String last = null;
        int inx = Integer.parseInt(index);
        for (int i = 1; i <= inx; i++) {
            UiObject obj = this.locateChild("${inx}");
            String pl = this.buildJQuerySelectorWithoutPosition(obj.locator)
            Integer occur = locs.get(pl)
            if (occur == null) {
                locs.put(pl, 1)
            } else {
                locs.put(pl, occur + 1)
            }
            if (i == inx) {
                last = pl
            }
        }

        Integer lastOccur = locs.get(last)

        //force to be direct child (if consider List trailer)
        return " > ${last}:eq(${lastOccur-1})"
    }

    int getListSizeByXPath(Closure c) {

      String rl = c(this.locator)

      Accessor accessor = new Accessor()
      WorkflowContext context = WorkflowContext.getDefaultContext()
      if (this.separator != null && this.separator.trim().length() > 0) {
        if(this.namespace != null && this.namespace.trim().length() > 0){
          return accessor.getXpathCount(context, rl + "/${this.namespace}:${this.separator}")
        }
        return accessor.getXpathCount(context, rl + "/${this.separator}")
      } else {
        int index = 1
        while (accessor.isElementPresent(context, rl + getLocatorByIndexWithoutSeparator("${index}"))) {
          index++
        }

        index--

        return index
      }
    }

    int getListSizeByCssSelector(Closure c){
      java.util.List separators = new ArrayList()
      if(separator != null  && this.separator.trim().size() > 0){
        separators.add(this.separator)
      }else{
        this.components.each {key, component->
          separators.add(this.buildJQuerySelectorWithoutPosition(component.locator))
        }
      }

      return c(this.locator, separators.join(","))
    }


  @Override
  public String toHTML() {
    StringBuffer sb = new StringBuffer(64);
    String ident = getIndent();
    if (this.components.size() > 0) {
      if (this.locator != null)
        sb.append(ident + this.locator.toHTML(false)).append("\n");

      int max = 0;
      boolean hasAll = false;
      this.components.each {String uid, UiObject obj ->
//        String auid = uid.replaceFirst('_', '')
        String auid = obj.metaData.getIndex().getValue()
        if ("all".equalsIgnoreCase(auid)) {
          hasAll = true;
        }else if("any".equalsIgnoreCase(auid)){
          if(this.separator != null && this.separator.trim().length() > 0){
             sb.append(ident + "  <${separator}>\n")
          }
          sb.append(obj.toHTML());
          if (this.separator != null && this.separator.trim().length() > 0) {
            sb.append(ident + "  </${separator}>\n")
          }
        }else{
          int indx = Integer.parseInt(auid)
          if (indx > max) {
            max = indx
          }
        }
      }

      if(hasAll)
        max++;

      for (int i = 1; i <= max; i++) {
        if(this.separator != null && this.separator.trim().length() > 0){
          sb.append(ident + "  <${separator}>\n")
        }
//        UiObject obj = findUiObject(i)
        UiObject obj = this.locateChild("${i}")
        if(obj == null)
          obj = defaultUi
        sb.append(obj.toHTML());
        if(this.separator != null && this.separator.trim().length() > 0){
          sb.append(ident + "  </${separator}>\n")
        }
      }

      if (this.locator != null){
        sb.append(getIndent() + this.locator.generateCloseTag() + "\n");
      }
    }

    return sb.toString();
  }

  @Override
    public void traverse(WorkflowContext context){
      context.appendToUidList(context.getUid())

      int max = 1;
      UiObject mcomponent = null;
      components.each {key, component->
        String aid = component.metaData.getIndex().getValue()
        if(aid ==~ /[0-9]+/){
          context.directPushUid("[${aid}]")
          component.traverse(context)
          if(max < Integer.parseInt(aid))
            max = Integer.parseInt(aid)
        }else if("any".equalsIgnoreCase(aid) || "last".equalsIgnoreCase(aid) || "first".equalsIgnoreCase(aid)){
           String id =component.metaData.getId() 
           context.directPushUid("[${id}]")
           component.traverse(context)
        }else if("all".equalsIgnoreCase(aid)){
           max++;
           mcomponent = component;
        }
      }

      if (mcomponent != null) {
        context.directPushUid("[${max}]")
        mcomponent.traverse(context);
      }
    
      context.popUid()
    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid) {

        //if not child listed, return itself
        if(uiid.size() < 1){
            if(this.locator != null){
                groupLocating(context)  
                context.noMoreProcess = true;
            }

            return this
        }

        String child = uiid.pop()

        String key = child.replaceFirst('_', '')

        UiObject cobj = this.locateChild(key);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi
        }

        //update reference locator by append the relative locator for this container
        if (this.locator != null) {
          groupLocating(context)
        }

        //append relative location, i.e., index to the locator
        String loc
        if(context.isUseCssSelector()){
//          loc = getListSelector(nindex)
          loc = getListSelector(key, cobj);
        }else{
//          loc = getListLocator(nindex)
          loc = getListLocator(key, cobj);
        }
        context.appendReferenceLocator(loc)
        //If the List does not have a separator
        //tell WorkflowContext not to process the next object's locator because List has already added that
        if(this.separator == null || this.separator.trim().length() == 0 || cobj.self){
          context.skipNext()
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            return cobj
        } else {
            //recursively call walkTo until the object is found
            return cobj.walkTo(context, uiid)
        }
    }

}