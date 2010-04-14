package org.telluriumsource.dsl

import org.telluriumsource.ui.locator.XPathProcessor
import org.telluriumsource.ui.locator.MetaCmd
import org.json.simple.JSONArray

/**
 * Hold meta data for execution workflow
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 2, 2008
 *
 */
class WorkflowContext implements Serializable {

  public static final String REFERENCE_LOCATOR = "Reference_Locator"
  public static final String OPTION_LOCATOR = "Option_Locator"
  public static final String MATCH_ALL = "*"
  public static final String UID_LIST = "UID_List"
  public static final String JSON_ARRAY = "JSON_Array"
  public static final String SKIP_NEXT = "SKIP_NEXT"
  public static final String TRUE = "true"
  public static final String DSLCONTEXT = "DSLCONTEXT"
  public static final String API_NAME = "API_Name"

  private boolean useOption = false
  //Table's child object's tag will be duplicated with the current relative xpath provided by xpath
  //For example, /table/tr/td + /td/input
  private boolean tableDuplicateTag = false

  private boolean exploreCssSelector = false

  private boolean exploreUiModuleCache = false

  private boolean bundlingable = false

  private boolean noMoreProcess = false;

  private MetaCmd metaCmd = new MetaCmd();

  private Stack<String> uiid = new Stack<String>();

  def context = [:]

  public void notBundlingable(){
    this.bundlingable = false
  }

  public void makeBundlingable(){
    this.bundlingable = true
  }

  public boolean isBundlingable(){
    return this.bundlingable
  }

  public void skipNext(){
    context.put(SKIP_NEXT, "true");
  }

  protected boolean isSkipNext(){
    String skip = context.get(SKIP_NEXT)
    if(skip != null && TRUE.equalsIgnoreCase(skip)){
      context.put(SKIP_NEXT, "false");
      return true
    }else{
      return false
    }
  }

  public ArrayList getUidList(){
    return context.get(UID_LIST)
  }

  public void setUidList(ArrayList list){
    context.put(UID_LIST, list)
  }

  public ArrayList appendToUidList(String uid){
    ArrayList list = context.get(UID_LIST)
    if(list == null){
      list = new ArrayList()
    }
    list.add(uid)
    context.put(UID_LIST, list)

    return list
  }

  public JSONArray getJSONArray() {
    return context.get(JSON_ARRAY);
  }

  public void setJSONArray(JSONArray jsarray){
    context.put(JSON_ARRAY, jsarray);
  }

  public void setNewUid(String uid){
    this.uiid = new Stack();
    if(uid != null && (uid.trim().length() > 0)){
      String[] ids = uid.split(UiID.ID_SEPARATOR)
      for(int i=0; i<ids.length; i++){
        String[] pp = preprocess(ids[i])
        if(pp.length == 1){
          this.uiid.push(pp[0])
        }else{
          this.uiid.push(pp[0])
          this.uiid.push(pp[1])
        }
      }
    }
  }

  public void pushUid(String uid){
    this.uiid.push(uid)
  }

  //List or Table element should use this to push uid
  public void directPushUid(String uid){
    this.uiid.push("_" + uid)
  }

  public String popUid(){
    return this.uiid.pop().replaceAll("_\\[","\\[")
  }

  public String peekUid(){
    return this.uiid.peek().replaceAll("_\\[","\\[")
  }

  public String getUid(){
    return this.uiid.join(".").replaceAll("\\._\\[","\\[")
  }

  protected String[] preprocess(String id) {
    if (id != null && (id.trim().length() > 0) && id.contains("[")) {
      if (id.startsWith("[")) {
        return [id]
      } else {
        int index = id.indexOf("[")
        String first = id.substring(0, index)
        String second = "_" + id.substring(index)
        return [first, second]
      }
    } else {
      return [id]
    }
  }

  public void attachMetaCmd(String uid, boolean isCacheable, boolean unique){
    this.metaCmd.setProperty(MetaCmd.UID, uid)
    this.metaCmd.setProperty(MetaCmd.CACHEABLE, isCacheable)
    this.metaCmd.setProperty(MetaCmd.UNIQUE, unique)
  }

  public MetaCmd extraMetaCmd(){
    return this.metaCmd
  }

  public void updateUniqueForMetaCmd(boolean isUnique){
    this.metaCmd.setProperty(MetaCmd.UNIQUE, isUnique)
  }

  public void updateCacheableForMetaCmd(boolean cacheable){
    this.metaCmd.setProperty(MetaCmd.CACHEABLE, cacheable)
  }

  public void setTableDuplicateTag() {
    this.tableDuplicateTag = true
  }

  public boolean isUseOption() {
    return useOption
  }

  public void setUseOption(boolean useOption) {
    this.useOption = useOption
  }

  public def getOptionLocator() {
    return context.get(OPTION_LOCATOR)
  }

  public Object getContext(String name) {

    return context.get(name)
  }

  public void putContext(String name, Object obj) {
    context.put(name, obj)
  }

  public static WorkflowContext getDefaultContext() {

    WorkflowContext context = new WorkflowContext()
    context.putContext(REFERENCE_LOCATOR, "")

    return context
  }

  public static WorkflowContext getContextByEnvironment(boolean useCssSelector, boolean useUiModuleCache){
    WorkflowContext context = new WorkflowContext()

    context.setUseCssSelector(useCssSelector)
    context.setUseUiModuleCache(useUiModuleCache)
    context.putContext(REFERENCE_LOCATOR, "")

    return context
  }

  public boolean isUseCssSelector(){

    return this.exploreCssSelector
  }

  public void disableCssSelector(){
    this.exploreCssSelector = false
  }

  public void setUseCssSelector(boolean isUse){
    this.exploreCssSelector = isUse
  }

  public boolean isUseUiModuleCache(){
    return this.exploreUiModuleCache
  }

  public void disableUiModuleCache(){
    this.exploreUiModuleCache = false
  }

  public void setUseUiModuleCache(boolean isUse){
    this.exploreUiModuleCache = isUse
  }

  public String getReferenceLocator() {

    return context.get(REFERENCE_LOCATOR)
  }

  public void setReferenceLocator(String loc) {

    context.put(REFERENCE_LOCATOR, loc)
  }

  public String getApiName(){
    return context.get(API_NAME)
  }

  public void setApiName(String name){
    context.put(API_NAME, name)
  }

  //append the relative locator to the end of the reference locator
  public void appendReferenceLocator(String loc) {
    if (!this.isSkipNext()) {
      if (loc == null || loc.trim().length() == 0)
        return

      //matching all does not work for jQuery selector, skip it
      if (this.exploreCssSelector && MATCH_ALL.equals(loc.trim())) {
        return
      }

      String rl = context.get(REFERENCE_LOCATOR)

      if (rl == null) {
        rl = loc
      } else {
        rl = rl + loc
      }

      context.put(REFERENCE_LOCATOR, rl)
    }
  }
}