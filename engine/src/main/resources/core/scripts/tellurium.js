var getEvent = function(name, key , objRef){
    var e = teJQuery.Event(name);
    e.which = key.charCodeAt(0);
    e.ctrlKey = objRef.ctrl;
    e.shiftKey = objRef.shift;
    e.altKey = objRef.alt;
    e.metaKey = objRef.metaKey;
    return e;
};

function Outlines(){
    this.defaultOutline = null;
    this.outlines = new Array();
}

Outlines.prototype.init = function(){
//    this.defaultOutLine = "2px solid #0000FF";
    //Green
    this.defaultOutLine = "2px solid #00FF00";
    //Red
    this.outlines.push("2px solid #FF0000");
    //Yellow
    this.outlines.push("2px solid #FFFF00");
    //Blue
    this.outlines.push("2px solid #0000FF");
    //Pink
    this.outlines.push("2px solid #FF00FF");
    //Brown
    this.outlines.push("2px solid #804000");
};

Outlines.prototype.getDefaultOutline = function(){
    return this.defaultOutLine;
};

Outlines.prototype.getOutline = function(index){
    var i = index % this.outlines.length;

    return this.outlines[i];
};

var tellurium = null;


teJQuery(document).ready(function() {
    tellurium = new Tellurium();
    tellurium.initialize();
    !tellurium.logManager.isUseLog || fbLog("Tellurium initialized after document ready", tellurium);
//    document.body.appendChild(firebug);
    (function() {
        if (window.firebug != undefined && window.firebug.version) {
            firebug.init();
        } else {
            setTimeout(arguments.callee);
        }
    })();
    if(typeof (firebug) != "undefined")
        void(firebug);
});

//add custom jQuery Selector :te_text()
//

teJQuery.extend(teJQuery.expr[':'], {
    te_text: function(a, i, m) {
        return teJQuery.trim(teJQuery(a).text()) === teJQuery.trim(m[3]);
    }
});

teJQuery.expr[':'].group = function(obj, index, m){
      var $this = teJQuery(obj);

      var splitted = m[3].split(",");
      var result = true;

      for(var i=0; i<splitted.length; i++){
         result = result && ($this.find(splitted[i]).length > 0);
      }

      return result;
};

teJQuery.expr[':'].styles = function(obj, index, m){
      var $this = teJQuery(obj);

      var splitted = new Array();

      var fs = m[3].split(/:|;/);
      for(var i=0; i<fs.length; i++){
          var trimed = teJQuery.trim(fs[i]);
          if(trimed.length > 0){
              splitted.push(trimed);
          }
      }

      var result = true;

      var l=0;
      while(l < splitted.length){
         result = result && (teJQuery.trim($this.css(splitted[l])) == splitted[l+1]);
         l=l+2;
      }

      return result;
};

/*
teJQuery.expr[':'].nextToLast = function(obj, index, m){
    var $this = teJQuery(obj);

    //      return $this.find(m[3]).last().prev();
//    return $this.find(m[3]).slice(-2, -1);
    if ($this.index() == $this.siblings().length - 1) {
        return true;
    } else {
        return false;
    }
};
*/

// this is a selector called nextToLast. its sole purpose is to return the next to last
// element of the array of elements supplied to it.
// the parameters in the function below are as follows;
// obj => the current node being checked
// ind => the index of obj in the array of objects being checked
// prop => the properties passed in with the expression
// node => the array of nodes being checked
teJQuery.expr[':'].nextToLast = function(obj, ind, prop, node){

     // if ind is 2 less than the length of the array of nodes, keep it
     return ind == node.length - 2;
};

teJQuery.expr[':'].regex = function(elem, index, match) {
    var matchParams = match[3].split(','),
        validLabels = /^(data|css):/,
        attr = {
            method: matchParams[0].match(validLabels) ?
                        matchParams[0].split(':')[0] : 'attr',
            property: matchParams.shift().replace(validLabels,'')
        },
        regexFlags = 'ig',
        regex = new RegExp(matchParams.join('').replace(/^\s+|\s+$/g,''), regexFlags);
    return regex.test(teJQuery(elem)[attr.method](attr.property));
};

teJQuery.fn.outerHTML = function() {
    return teJQuery("<div/>").append( teJQuery(this[0]).clone() ).html();
};

function getColor(elem, cssName){
   var color = null;

   if (elem != null) {
        var parent = elem.parentNode;

        while (parent != null) {
//            color = teJQuery(parent).css(cssName);
            color = teJQuery.curCSS(parent, cssName);
            !tellurium.logManager.isUseLog || fbLog(cssName + " is " + color + " for ", parent);
            if (color != '' && color != 'transparent' || teJQuery.nodeName(parent, "body"))
                break;
            parent = parent.parentNode;
        }
    }

   return color;
}

function Identifier(){
    this.sn = 100;
}

Identifier.prototype.next = function(){
    this.sn++;

    return this.sn;
};

// Command Request for Command bundle
function CmdRequest(){
    this.sequ = 0;
    this.uid = null;
    this.name = null;
    this.args = null;
}

// Command Request for Command bundle
function CmdResponse(){
    this.sequ = 0;
    this.name = null;
    this.returnType = null;
    this.returnResult = null;
}

function BundleResponse(){
    this.response = new Array();
}

BundleResponse.prototype.addVoidResponse = function(sequ, name){
    var resp = new CmdResponse();
    resp.sequ = sequ;
    resp.name = name;
    resp.returnType = "VOID";
    resp.returnResult = null;

    this.response.push(resp);
};

BundleResponse.prototype.addResponse = function(sequ, name, returnType, returnResult){
    var resp = new CmdResponse();
    resp.sequ = sequ;
    resp.name = name;
    resp.returnType = returnType;
    resp.returnResult = returnResult;

    this.response.push(resp);
};

BundleResponse.prototype.getResponse = function(){
    return this.response;
};

BundleResponse.prototype.toJSon = function(){
    var out = [];
    for(var i=0; i<this.response.length; i++){
        out.push(this.response[i]);
    }

    return JSON.stringify(out);
};

function MacroCmd(){
    this.bundle = new Array();

}

MacroCmd.prototype.size = function(){
    return this.bundle.length;
};

MacroCmd.prototype.first = function(){
    return this.bundle.shift();
};

MacroCmd.prototype.empty = function(){
    this.bundle = new Array();
};

MacroCmd.prototype.addCmd = function(sequ, uid, name, args){
    var cmd = new CmdRequest();
    cmd.sequ = sequ;
    cmd.uid = uid;
    cmd.name = name;
    cmd.args = args;
    this.bundle.push(cmd);
};

MacroCmd.prototype.parse = function(json){
    //Need to empty the bundle otherwise, old bundle commands will stay in the case of exception
    this.empty();
    var cmdbundle = JSON.parse(json, null);
    for(var i=0; i<cmdbundle.length; i++){
        this.addCmd(cmdbundle[i].sequ,  cmdbundle[i].uid, cmdbundle[i].name, cmdbundle[i].args);
    }
};

function TelluriumCommandHandler(api, requireElement, returnType) {
    //api method
    this.api = api;
    //whether it requires an element/elements to act on
    this.requireElement = requireElement;
    //return type
    this.returnType = returnType;
}

function EngineState(){
    this.cache = null;
    this.teApi = null;
    this.relax = null;
}

function Tellurium (){

    this.cache = new TelluriumCache();

    this.currentWindow = null;

    this.currentDocument = null;

    //Macro command for Tellurium
    this.macroCmd = new MacroCmd();

    //whether to use Tellurium new jQuery selector based APIs
    this.isUseTeApi = false;

    //cache to hold the element corresponding to a UID in command bundle
//    this.cbCache = new Hashtable();

    this.teApi = new TelluriumApi(this.cache);

    //api name to method mapping for command bundle processing
    this.apiMap = new Hashtable();

    //identifier generator
    this.idGen = new Identifier();

    //JQuery Builder
    this.jqbuilder = new JQueryBuilder();

    //UI object name to Javascript object builder mapping
    this.uiBuilderMap = new Hashtable();

    //log manager for Tellurium
    this.logManager = new LogManager();

    //outlines
    this.outlines = new Outlines();
}

Tellurium.prototype.isUseCache = function(){
    return this.cache.cacheOption;    
};

//TODO: Refactor --> use Javascript itself to do automatically discovery like selenium does instead of manually registering them
Tellurium.prototype.initialize = function(){
    this.outlines.init();
    this.registerTeApis();
    this.registerDefaultUiBuilders();
};

Tellurium.prototype.registerDefaultUiBuilders = function(){
    this.uiBuilderMap.put("Button", new UiButtonBuilder());
    this.uiBuilderMap.put("CheckBox", new UiCheckBoxBuilder());
    this.uiBuilderMap.put("Div", new UiDivBuilder());
    this.uiBuilderMap.put("Icon", new UiIconBuilder());
    this.uiBuilderMap.put("Image", new UiImageBuilder());
    this.uiBuilderMap.put("InputBox", new UiInputBoxBuilder());
    this.uiBuilderMap.put("RadioButton", new UiRadioButtonBuilder());
    this.uiBuilderMap.put("Selector", new UiSelectorBuilder());
    this.uiBuilderMap.put("Span", new UiSpanBuilder());
    this.uiBuilderMap.put("SubmitButton", new UiSubmitButtonBuilder());
    this.uiBuilderMap.put("TextBox", new UiTextBoxBuilder());
    this.uiBuilderMap.put("UrlLink", new UiUrlLinkBuilder());
    this.uiBuilderMap.put("Container", new UiContainerBuilder());
    this.uiBuilderMap.put("Frame", new UiFrameBuilder());
    this.uiBuilderMap.put("List", new UiListBuilder());
    this.uiBuilderMap.put("Table", new UiTableBuilder());
    this.uiBuilderMap.put("StandardTable", new UiStandardTableBuilder());
    this.uiBuilderMap.put("Window", new UiWindowBuilder());
    this.uiBuilderMap.put("Repeat", new UiRepeatBuilder());
    this.uiBuilderMap.put("UiAllPurposeObject", new UiAllPurposeObjectBuilder());
};

//expose this so that users can hook in their own custom UI objects or even overwrite the default UI objects
Tellurium.prototype.registerUiBuilder = function(name, builder){
    this.uiBuilderMap.put(name, builder);
};

Tellurium.prototype.registerTeApis = function(){
    this.registerApi("isElementPresent", true, "BOOLEAN");
    this.registerApi("blur", true, "VOID");
//    this.registerApi("click", true, "VOID");
    this.registerApi("doubleClick", true, "VOID");
    this.registerApi("fireEvent", true, "VOID");
    this.registerApi("focus", true, "VOID");
    this.registerApi("type", true, "VOID");
    this.registerApi("typeKey", true, "VOID");
    this.registerApi("keyDown", true, "VOID");
    this.registerApi("keyPress", true, "VOID");
    this.registerApi("keyUp", true, "VOID");
    this.registerApi("altKeyUp", false, "VOID");
    this.registerApi("altKeydown", false, "VOID");
    this.registerApi("ctrlKeyUp", false, "VOID");
    this.registerApi("ctrlKeydown", false, "VOID");
    this.registerApi("shiftKeyUp", false, "VOID");
    this.registerApi("shiftKeydown", false, "VOID");
    this.registerApi("metaKeyUp", false, "VOID");
    this.registerApi("metaKeydown", false, "VOID");
    this.registerApi("mouseOver", true, "VOID");
    this.registerApi("mouseDown", true, "VOID");
    this.registerApi("mouseEnter", true, "VOID");
    this.registerApi("mouseOut", true, "VOID");
    this.registerApi("mouseLeave", true, "VOID");
    this.registerApi("submit", true, "VOID");
    this.registerApi("check", true, "VOID");
    this.registerApi("uncheck", true, "VOID");
    this.registerApi("waitForPageToLoad", false, "VOID");
    this.registerApi("getAttribute", true, "STRING");
    this.registerApi("getSelectOptions", true, "ARRAY");
    this.registerApi("getSelectValues", true, "ARRAY");
    this.registerApi("select", true, "VOID");
    this.registerApi("addSelection", true, "VOID");
    this.registerApi("removeSelection", true, "VOID");
    this.registerApi("removeAllSelections", true, "VOID");
    this.registerApi("getSelectedLabel", true, "STRING");
    this.registerApi("getSelectedLabels", true, "ARRAY");
    this.registerApi("getSelectedValue", true, "STRING");
    this.registerApi("getSelectedValues", true, "ARRAY");    
    this.registerApi("open", false, "VOID");
    this.registerApi("getText", true, "STRING");
    this.registerApi("isChecked", true, "BOOLEAN");
    this.registerApi("isVisible", true, "BOOLEAN");
    this.registerApi("isEditable", true, "BOOLEAN");
    this.registerApi("getXpathCount", false, "NUMBER");

    //converted from custom selenium apis, tellurium-extensions.js
    this.registerApi("getAllText", true, "ARRAY");
    this.registerApi("getCssSelectorCount", true, "NUMBER");
    this.registerApi("getCSS", true, "Array");
    this.registerApi("isDisable", true, "BOOLEAN");
    this.registerApi("getListSize", true, "NUMBER");
    this.registerApi("getCacheState", false, "STRING");
    this.registerApi("enableCache", false, "VOID");
    this.registerApi("disableCache", false, "VOID");
    this.registerApi("cleanCache", false, "VOID");
    this.registerApi("setCacheMaxSize", false, "VOID");
    this.registerApi("getCacheSize", false, "NUMBER");
    this.registerApi("getCacheMaxSize", false, "NUMBER");
    this.registerApi("getCacheUsage", false, "ARRAY");
    this.registerApi("addNamespace", false, "VOID");
    this.registerApi("getNamespace", false, "STRING");
    this.registerApi("useDiscardNewCachePolicy", false, "VOID");
    this.registerApi("useDiscardOldCachePolicy", false, "VOID");
    this.registerApi("useDiscardLeastUsedCachePolicy", false, "VOID");
    this.registerApi("useDiscardInvalidCachePolicy", false, "VOID");
    this.registerApi("getCachePolicyName", false, "STRING");

    this.registerApi("getUseUiModule", false, "STRING");
    this.registerApi("getValidateUiModule", false, "STRING");
    this.registerApi("useClosestMatch", false, "VOID");
    this.registerApi("useTeApi", false, "VOID");
    this.registerApi("isUiModuleCached", false, "BOOLEAN");
    this.registerApi("toggle", true, "VOID");
    this.registerApi("showUi", false, "VOID");
    this.registerApi("cleanUi", false, "VOID");
    this.registerApi("deleteAllCookiesByJQuery", false, "VOID");
    this.registerApi("deletelCookieByJQuery", false, "VOID");
    this.registerApi("setCookieByJQuery", false, "VOID");
    this.registerApi("getCookieByJQuery", false, "STRING");
    this.registerApi("updateEngineState", false, "VOID");
    this.registerApi("getEngineState", false, "OBJECT");
    this.registerApi("useEngineLog", false, "VOID");
    this.registerApi("getAllTableBodyText", true, "ARRAY");
    this.registerApi("getTeListSize", true, "NUMBER");
    this.registerApi("getTeTableTbodyNum", true, "NUMBER");
    this.registerApi("getTeTableColumnNumForTbody", true, "NUMBER");
    this.registerApi("getTeTableRowNumForTbody", true, "NUMBER");
    this.registerApi("getTeTableColumnNum", true, "NUMBER");
    this.registerApi("getTeTableRowNum", true, "NUMBER");
    this.registerApi("getTeTableFootColumnNum", true, "NUMBER");
    this.registerApi("getTeTableHeaderColumnNum", true, "NUMBER");
    this.registerApi("getHTMLSource", true, "ARRAY");
    this.registerApi("getRepeatNum", true, "NUMBER");
    this.registerApi("getUiByTag", false, "ARRAY");
    this.registerApi("removeMarkedUids", false, "ARRAY");
    this.registerApi("getIndex", true, "NUMBER");
};

Tellurium.prototype.flipLog = function(){
    this.logManager.isUseLog = !this.logManager.isUseLog;
    if(firebug != undefined)
        firebug.env.debug = this.logManager.isUseLog;  
};

Tellurium.prototype.isUseLog = function(){
    return this.logManager.isUseLog;
};

Tellurium.prototype.useTeApi = function(isUse){
    if (typeof(isUse) == "boolean") {
        tellurium.isUseTeApi = isUse;
    } else {
        tellurium.isUseTeApi = ("true" == isUse || "TRUE" == isUse);
    }
};

Tellurium.prototype.registerApi = function(apiName, requireElement, returnType){
    var api =  this.teApi[apiName];

    if (typeof(api) == 'function') {
        this.apiMap.put(apiName, new TelluriumCommandHandler(api, requireElement, returnType));
    }
};

Tellurium.prototype.isApiMissing =function(apiName){

    return this.apiMap.get(apiName) == null;
};

Tellurium.prototype.parseMacroCmd = function(json){
    this.macroCmd.parse(json);
};

Tellurium.prototype.prepareArgumentList = function(handler, args, element){
    if(args == null)
        return [];

    var params = [];

    if (handler.requireElement) {
        params.push(element);
        for (var i = 1; i < args.length; i++) {
            params.push(args[i]);
        }
    } else {
        params = args;
    }

    return params;
};

function validateDomRef(domref){
    try{
        return teJQuery(domref).is(':visible');
    }catch(e){
        fbError("Dom reference is not valid", e);
        return false;
    }
}

Tellurium.prototype.getUiElementAndDescendant = function(uid){
    //TODO: need to change it to getSubtree(uid) after implement that
    var context = new WorkflowContext();
    return this.cache.getIndexedTree(context, uid);
};

Tellurium.prototype.getUiElementFromCache = function(uid){

    return this.cache.getCachedUiElement(uid);
};

Tellurium.prototype.dispatchCommand = function(response, cmd, element){
    var result = null;

    var handler = this.apiMap.get(cmd.name);

    if(handler != null){
        var api = handler.api;
        //prepare the argument list
        var params = this.prepareArgumentList(handler, cmd.args, element);
        if(params != null && params.length > 0){
            if(handler.returnType == "VOID"){
                api.apply(this, params);
            }else{
                result = api.apply(this, params);
                response.addResponse(cmd.sequ, cmd.name, handler.returnType, result);
            }
        }else{
            if(handler.returnType == "VOID"){
                api.apply(this, params);
            }else{
                result = api.apply(this, params);
                response.addResponse(cmd.sequ, cmd.name, handler.returnType, result);
            }
        }

    }else{
        throw SeleniumError("Unknown command " + cmd.name + " in Command Bundle.");
    }
};

Tellurium.prototype.locate = function(locator){

    return selenium.browserbot.findElementOrNull(locator);
};

Tellurium.prototype.isLocator = function(locator){
    if(typeof(locator) != "string")
        return false;
    
    return locator.startsWith('//') || locator.startsWith('jquery=') || locator.startsWith('jquerycache=') || locator.startsWith('document.');
};

Tellurium.prototype.camelizeApiName = function(apiName){
    return "do" + apiName.charAt(0).toUpperCase() + apiName.substring(1);
};

Tellurium.prototype.processMacroCmd = function(){

    var response = new BundleResponse();

    while (this.macroCmd.size() > 0) {
        var cmd = this.macroCmd.first();
        //If don't want to use Tellurium APIs
        //or could not find the appropriate API from Tellurium APIs, delegate it to selenium directly
        //TODO: pay attention to tellurium only APIs, should not delegate to selenium if they are Tellurium only
        //should be fine if the same methods are duplicated in Selenium as well
        if ((!this.isUseTeApi) || this.isApiMissing(cmd.name)) {
            this.delegateToSelenium(response, cmd);
        } else {
            var element = null;
            !tellurium.logManager.isUseLog || fbLog("Process Macro Command: ", cmd);
            var locator = cmd.args[0];
            //some commands do not have any arguments, null guard args
            if (locator != null) {
                var isLoc = this.isLocator(locator);
                //if the first argument is a locator
                if (isLoc) {
                    //handle attribute locator for the getAttribute call
                    //pay attention to the xpath such as
                    // //descendant-or-self::table/descendant-or-self::input[@title="Google Search" and @name="q"]/self::node()[@disabled]
                    if (cmd.name == "getAttribute" || cmd.name == "isElementPresent") {
                        var attributePos = locator.lastIndexOf("@");
                        var attributeName = locator.slice(attributePos + 1);
                        if(attributeName.endsWith("]")){
                            attributeName = attributeName.substr(0, attributeName.length-1);
                        }
                        cmd.args.push(attributeName);
                        locator = locator.slice(0, attributePos);
                        if(locator.endsWith("[")){
                            locator = locator.substr(0, locator.length-1);
                        }
                    }

                    if (cmd.uid == null) {
                        element = this.locate(locator);
                    } else {
                        !tellurium.logManager.isUseLog || fbLog("Tellurium Cache option: ", this.isUseCache());
                        if(this.isUseCache()){
                            element = this.getUiElementFromCache(cmd.uid);
                            !tellurium.logManager.isUseLog || fbLog("Got UI element " + cmd.uid + " from Cache.", element);
                        }
                        
                        if (element == null) {
                            element = this.locate(locator);
                            if(element == null)
                               throw SeleniumError("Cannot locate element for uid " + cmd.uid + " in Command " + cmd.name + ".");
                        }

                    }
                }
            }

            this.dispatchCommand(response, cmd, element);
        }
    }

    return response.toJSon();
};

Tellurium.prototype.locateElementByCSSSelector = function(locator, inDocument, inWindow){
    var loc = locator;
    var attr = null;
    var isattr = false;
    var inx = locator.lastIndexOf('@');
    if (inx != -1) {
        loc = locator.substring(0, inx);
        attr = locator.substring(inx + 1);
        isattr = true;
    }
    var found = teJQuery(inDocument).find(loc);
    if (found.length == 1) {
        if (isattr) {
            return found[0].getAttributeNode(attr);
        } else {
            return found[0];
        }
    } else if (found.length > 1) {
        if (isattr) {
            return found.get().getAttributeNode(attr);
        } else {
            return found.get();
        }
    } else {
        return null;
    }
};

Tellurium.prototype.getDOMElement = function($found){
    if ($found.length == 1) {
        return $found[0];
    } else if ($found.length > 1) {
        return $found.get();
    } else {
        return null;
    }
};

Tellurium.prototype.getDOMAttributeNode = function($found, attr) {
    if ($found.length == 1) {
        return $found[0].getAttributeNode(attr);

    } else if ($found.length > 1) {
        return $found.get().getAttributeNode(attr);
    } else {
        return null;
    }
};

//convert jQuery result to DOM reference
Tellurium.prototype.convResult = function($result, input){
    if(input.isAttribute){
        return this.getDOMAttributeNode($result, input.attribute);
    }

    return this.getDOMElement($result);
};

function MetaCmd(){
    this.uid = null;
    this.cacheable = true;
    this.unique = true;
}

function TeInput(){
    this.metaCmd = null;
    this.selector = null;
    this.optimized = null;
    this.isAttribute = false;
    this.attribute = null;
}

Tellurium.prototype.parseLocator = function(locator){
    var input = new TeInput();
    
    var purged = locator;
    var inx = locator.lastIndexOf('@');
    if (inx != -1) {
        purged = locator.substring(0, inx);
        input.attribute = locator.substring(inx + 1);
        input.isAttribute = true;
    }
//    alert("Pured locator " + purged);
    var tecmd = JSON.parse(purged, null);

    input.selector = tecmd.locator;
    input.optimized = tecmd.optimized;
    var metaCmd = new MetaCmd();
    metaCmd.uid = tecmd.uid;
    metaCmd.cacheable = tecmd.cacheable;
    metaCmd.unique = tecmd.unique;
    input.metaCmd = metaCmd;

    return input;
};

Tellurium.prototype.validateResult = function($result, unique, selector){
    if(unique){
        if($result != null && $result.length > 1){
            throw new SeleniumError("Element is not unique, Found " + $result.length + " elements for " + selector);
        }
    }
};

/*
Tellurium.prototype.locateElementByCacheAwareCSSSelector = function(locator, inDocument, inWindow){
    var input = this.parseLocator(locator);
    var $found = null;
    
    //If do not cache selector or meta command without UID, directly find CSS selector
    if((!this.cacheOption) || input.metaCmd.uid == null || trimString(input.metaCmd.uid).length == 0){
        //cannot cache without uid, thus, go directly to find the element using jQuery
         $found = teJQuery(inDocument).find(input.optimized);
         this.validateResult($found);
         return this.convResult($found, input);
    }else{
        var sid = new Uiid();
        sid.convertToUiid(input.metaCmd.uid);
        var key = sid.getUid();
        //if this selector is cacheable, need to check the cache first
        if(input.metaCmd.cacheable){
            $found = this.cache.checkSelectorFromCache(key);
            
            if($found == null){
                //could not find from cache or the cached one is invalid
                while(sid.size() > 1){
                    //try to find from its ancestor
                    sid.pop();
                    var akey = sid.getUid();
                    var ancestor = this.cache.checkAncestorSelector(akey);
                    if(ancestor != null){
                        $found = this.cache.findFromAncestor(ancestor, input.selector);
                        break;
                    }
                }

                //if still could not find do jQuery select
                if($found == null){
                    $found = teJQuery(inDocument).find(input.optimized);
                }

                //validate the result before storing it
                this.validateResult($found);

                //If find valid selector, update to cache
                if($found != null && $found.length > 0){
                    var cachedata = new CacheData();
                    cachedata.selector = input.selector;
                    cachedata.optimized = input.optimized;
                    cachedata.reference = $found;
                    this.cache.addSelectorToCache(key, cachedata);
                }
            }

            return this.convResult($found, input);
        } else {
            //cannot cache the selector directly, try to find the DOM elements using ancestor first
            while (sid.size() > 1) {
                //try to find from its ancestor
                sid.pop();
                var ckey = sid.getUid();
                var ancester = this.cache.checkAncestorSelector(ckey);
                if (ancester != null) {
                    $found = this.cache.findFromAncestor(ancester, input.selector);
                    break;
                }
            }
            
            //if still could not find do jQuery select
            if ($found == null) {
                $found = teJQuery(inDocument).find(input.optimized);
            }

            //validate the result before storing it
            this.validateResult($found);

            return this.convResult($found, input);
        }
    }   
};
*/

function CacheAwareLocator(){
    //runtime id
    this.rid = null;

    //original locator
    this.locator = null;
}

Tellurium.prototype.locateElementWithCacheAware = function(json, inDocument, inWindow){
    var element = null;
    
//    var json = locator.substring(7);
//    var json = locator;
    !tellurium.logManager.isUseLog || fbLog("JSON presentation of the cache aware locator: ", json);
    var cal = JSON.parse(json, null);
    !tellurium.logManager.isUseLog || fbLog("Parsed cache aware locator: ", cal);
    
    !tellurium.logManager.isUseLog || fbLog("Tellurium Cache option: ", this.isUseCache());
    if (this.isUseCache()) {
        //if Cache is used, try to get the UI element from the cache first
        element = this.getUiElementFromCache(cal.rid);
        !tellurium.logManager.isUseLog || fbLog("Got UI element " + cal.rid + " from Cache.", element);
        
        if (element != null) {
            //need to validate the result from the cache
            !tellurium.logManager.isUseLog || fbLog("Trying to validate the found UI element " + cal.rid, element);
            if (!validateDomRef(element)) {
                fbError("The UI element " + cal.rid + " from cache is not valid", element);
                this.cache.relocateUiModule(cal.rid);
                //after relocating the UI module, retry to get the UI element from the cache
                element = this.getUiElementFromCache(cal.rid);
                !tellurium.logManager.isUseLog || fbLog("After relocating UI module, found ui element" + cal.rid, element);
            }
        }else{
            if(cal.locator != null && cal.locator.trim().length > 0){
                //If cannot find the UI element from the cache, locate it as the last resort
                !tellurium.logManager.isUseLog || fbLog("Trying to locate the UI element " + cal.rid + " with its locator " + cal.locator + " because cannot find vaild one from cache", cal);
                element = this.locate(cal.locator);
            }
        }
    }else{
        !tellurium.logManager.isUseLog || fbLog("Trying to locate the UI element " + cal.rid + " with its locator " + cal.locator + " because cache option is off", cal);
        element = this.locate(cal.locator);
    } 

    if(element == null){
        fbError("Cannot locate element for uid " + cal.rid + " with locator " + cal.locator, element);
        
        //Disable this and let the caller to decide whether to throw exception or not
//        throw SeleniumError("Cannot locate element for uid " + cal.rid + " with locator " + cal.locator);
    }

    !tellurium.logManager.isUseLog || fbLog("Returning found UI element ", element);
    return element;
};

Tellurium.prototype.dispatchMacroCmd = function(){
    var response = new BundleResponse();

    while (this.macroCmd.size() > 0) {
        var cmd = this.macroCmd.first();
        if(cmd.name == "getUseUiModule"){
            //do UI module locating
            this.delegateToTellurium(response, cmd);
        }else{
            //for other commands
            !tellurium.logManager.isUseLog || fbLog("Dispatching command: ", cmd);
            this.updateArgumentList(cmd);
            !tellurium.logManager.isUseLog || fbLog("Command after updating argument list: ", cmd);
            if ((!this.isUseTeApi) || this.isApiMissing(cmd.name)) {
                !tellurium.logManager.isUseLog || fbLog("delegate command to Selenium", cmd);
                this.delegateToSelenium(response, cmd);
            }else{
                !tellurium.logManager.isUseLog || fbLog("delegate command to Tellurium", cmd);
                this.delegateToTellurium(response, cmd);
            }
        }
    }

    return response.toJSon();
};

Tellurium.prototype.delegateToSelenium = function(response, cmd) {
    // need to use selenium api name conversion to find the api
    var apiName = cmd.name;
    var result = null;
    !tellurium.logManager.isUseLog || fbLog("Delegate Call " + cmd.name + " to Selenium", cmd);

    var returnType = null;

    //Try to get back the return type by looking at Tellurium API counterpart
    var handler = this.apiMap.get(cmd.name);
    if(handler != null){
        returnType = handler.returnType;
    }
    
    if (apiName.startsWith("is")) {
        result = selenium[apiName].apply(selenium, cmd.args);
        if(returnType == null)
            returnType = "BOOLEAN";
        response.addResponse(cmd.sequ, apiName, returnType, result);
    } else if (apiName.startsWith("get")) {
        result = selenium[apiName].apply(selenium, cmd.args);
        if(apiName.indexOf("All") != -1){
            //api Name includes "All" should return an array
            if(returnType == null)
                returnType = "ARRAY";
            response.addResponse(cmd.sequ, apiName, returnType, result);
        }else{
            //assume the rest return "String"
            if(returnType == null)
                returnType = "STRING";
            response.addResponse(cmd.sequ, apiName, returnType, result);
        }
    } else {
        apiName = this.camelizeApiName(apiName);
        !tellurium.logManager.isUseLog || fbLog("Call Selenium method " + apiName, selenium);
        selenium[apiName].apply(selenium, cmd.args);
    }
};

Tellurium.prototype.delegateToTellurium = function(response, cmd) {
    var result = null;

    var handler = this.apiMap.get(cmd.name);

    if (handler != null) {
        var api = handler.api;
        //the argument list
        var params = cmd.args;
        if (params != null && params.length > 0) {
            if (handler.returnType == "VOID") {
                api.apply(this.teApi, params);
            } else {
                result = api.apply(this.teApi, params);
                response.addResponse(cmd.sequ, cmd.name, handler.returnType, result);
            }
        } else {
            if (handler.returnType == "VOID") {
                api.apply(this.teApi, params);
            } else {
                result = api.apply(this.teApi, params);
                response.addResponse(cmd.sequ, cmd.name, handler.returnType, result);
            }
        }

    } else {
        throw SeleniumError("Unknown command " + cmd.name + " in Command Bundle.");
    }
};

Tellurium.prototype.updateArgumentList = function(cmd){
    if (cmd.args != null) {

        //check the first argument to see if it is a locator or not
        var locator = cmd.args[0];

        if (this.isLocator(locator)) {
            //if it is a locator
            var cal = new CacheAwareLocator();
            cal.rid = cmd.uid;
            cal.locator = locator;

            //convert to locator string so that selenium could use it
            cmd.args[0] = "uimcal=" + JSON.stringify(cal);              
            !tellurium.logManager.isUseLog || fbLog("Update argument list for command " + cmd.name, cmd);
        }
        //otherwise, no modification, use the original argument list
    }
};

