// User extensions can be added here.
//
// Keep this file to avoid  mystifying "Invalid Character" error in IE

Selenium.prototype.getAllText = function(locator) {
    var out = [];
    var $e = teJQuery(this.browserbot.findElement(locator));
    $e.each(function() {
        out.push(teJQuery(this).text());
    });
//    return JSON.stringify(out);
    !tellurium.logManager.isUseLog || fbLog("GetAllText result ", out);
    return out;
};

Selenium.prototype.getCssSelectorCount = function(locator) {
    !tellurium.logManager.isUseLog || fbLog("GetCssSelectorCount for Locator", locator);
    if(locator.startsWith("jquery=")){
        locator = locator.substring(7);
    }else if(locator.startsWith("uimcal=")){
        var cal = JSON.parse(locator.substring(7), null);
         locator = cal.locator;
    }
    !tellurium.logManager.isUseLog || fbLog("Parsed locator", locator);
    var $e = teJQuery(this.browserbot.findElement(locator));
    !tellurium.logManager.isUseLog || fbLog("Found elements for CSS Selector", $e.get());
    if ($e == null)
        return 0;

    return $e.length;
};

Selenium.prototype.getCSS = function(locator, cssName) {
    var out = [];
    var $e = teJQuery(this.browserbot.findElement(locator));
    for(var i=0; i<$e.length; i++){
        var elem = $e.get(i);
        var val = teJQuery(elem).css(cssName);
        //['backgroundColor', 'borderBottomColor', 'borderLeftColor', 'borderRightColor', 'borderTopColor', 'color', 'outlineColor']
         //need to walk up the tree if the color is transparent
        if(val == "transparent" && (cssName == "background-color" || cssName == "backgroundColor" || cssName == "color")){
            val = getColor(elem, cssName);
        }
        out.push(val);
    }

    return out;
/*    $e.each(function() {
        var val = teJQuery(this).css(cssName);
        //need to walk up the tree if the color is transparent
        if(val == "transparent" && (cssName == "background-color" || cssName == "backgroundColor" || cssName == "color")){
            val = getColor(this, cssName);
        }
        out.push(val);        
//        out.push(teJQuery(this).css(cssName));
    });*/
//    return JSON.stringify(out);
};

Selenium.prototype.isDisabled = function(locator) {
    var $e = teJQuery(this.browserbot.findElement(locator));
    if ($e == null || $e.length < 1)
        Assert.fail("Cannot find Element for " + locator);
    if ($e.length > 1)
        Assert.fail("Element for " + locator + " is not unique.");
    return $e.attr('disabled');
};

Selenium.prototype.getListSize = function(locator, separators) {
    var $e = teJQuery(this.browserbot.findElement(locator));
    if ($e == null || $e.length < 1)
        Assert.fail("Cannot find Element for " + locator);

    //TODO: this may not be correct for example we have div/div/div span/span, what would $(().find("div, span") return? 
//    var jq = separators.join(",")

//    var list = $e.find(separators);
    var list = $e.children(separators);

    return list.length;
};


Selenium.prototype.getCacheState = function(){

    return tellurium.cache.cacheOption;
};

Selenium.prototype.doEnableCache = function(){
    tellurium.cache.cacheOption = true;
};

Selenium.prototype.doDisableCache = function(){
    tellurium.cache.cacheOption = false;
};

Selenium.prototype.doCleanCache = function(){
    tellurium.cache.cleanCache();
};

Selenium.prototype.doSetCacheMaxSize = function(size){
    tellurium.cache.maxCacheSize = size;
};

Selenium.prototype.getCacheSize = function(){
    return tellurium.cache.getCacheSize();
};

Selenium.prototype.getCacheMaxSize = function(){
    return tellurium.cache.maxCacheSize;
};

Selenium.prototype.getCacheUsage = function(){
    return tellurium.cache.getCacheUsage();
};

Selenium.prototype.doAddNamespace = function(prefix, namespace){
    this.browserbot.addNamespace(prefix, namespace);
};

Selenium.prototype.getNamespace = function(prefix){
   return this.browserbot._namespaceResolver(prefix);
};

Selenium.prototype.doUseDiscardNewCachePolicy = function(){
    tellurium.cache.useDiscardNewPolicy();
};

Selenium.prototype.doUseDiscardOldCachePolicy = function(){
    tellurium.cache.useDiscardOldPolicy();
};

Selenium.prototype.doUseDiscardLeastUsedCachePolicy = function(){
    tellurium.cache.useDiscardLeastUsedPolicy();
};

Selenium.prototype.doUseDiscardInvalidCachePolicy = function(){
    tellurium.cache.useDiscardInvalidPolicy();
};

Selenium.prototype.getCachePolicyName = function(){
    return tellurium.cache.getCachePolicyName();
};

Selenium.prototype.doTypeKey = function(locator, key){
    var $elem = teJQuery(this.browserbot.findElement(locator));
    var objRef = $elem.get(0);

	$elem.val($elem.val()+key).trigger(getEvent("keydown", key, objRef)).trigger(getEvent("keypress", key, objRef)).trigger(getEvent("keyup", key, objRef));
};

Selenium.prototype.doTriggerEvent = function(locator, event){
    var $elem = teJQuery(this.browserbot.findElement(locator));

	$elem.trigger(event);
};


function DiagnosisRequest(){
    this.uid = null;
    this.pLocator = null;
    this.attributes = null;
    this.retMatch = true;
    this.retHtml = true;
    this.retParent = true;
    this.retClosest = true;
}

function DiagnosisResponse(){
    this.uid = null;
    this.count = 0;
    this.matches = null;
    this.parents = null;
    this.closest = null;
    this.html = null;
}

Selenium.prototype.getHtml = function(){
    var $iframe = teJQuery("#selenium_myiframe");
    var $p = null;
    if($iframe != null && $iframe.length > 0){
        //run in single Window mode
        $p = $iframe.contents().find("html:first");
    }else{
        //run in multiple Window mode
        //TODO: need to double check this
        $p = teJQuery(this.browserbot.findElement("//html"));
    }

    return $p;
};

Selenium.prototype.getDiagnosisResponse = function(locator, dreq){
 //   var dreq = JSON.parse(req, null);

    var request = new DiagnosisRequest();
    request.uid = dreq.uid;
    request.pLocator = dreq.pLocator;
    request.attributes = dreq.attributes;
    request.retMatch = dreq.retMatch;
    request.retHtml = dreq.retHtml;
    request.retParent = dreq.retParent;
    !tellurium.logManager.isUseLog || fbLog("diagnosis request", request);
    
    var response = new DiagnosisResponse();
    response.uid = request.uid;
    var $e = null;
    try{
      $e = teJQuery(this.browserbot.findElement(locator));
    }catch(err){

    }

    if ($e == null) {
        response.count = 0;
    } else {
        response.count = $e.length;
        if (request.retMatch) {
            response.matches = new Array();
            $e.each(function() {
//                response.matches.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                response.matches.push(teJQuery(this).outerHTML());
            });
        }
    }

    if(request.retParent){
        response.parents = new Array();
        //if the parent is null or empty, return the whole html source
        if(request.pLocator == null || trimString(request.pLocator).length == 0 || request.pLocator == "jquery="){
//            response.parents.push(teJQuery('<div>').append(teJQuery("html:first").clone()).html());
             response.parents.push(teJQuery('<div>').append(this.getHtml().clone()).html());
        }else{
            var $p = null;
            try{
                $p = teJQuery(this.browserbot.findElement(request.pLocator));
            }catch(err){
//                $p = teJQuery("html:first");
//                $p = teJQuery("#selenium_myiframe").contents().find("html:first");
                $p = this.getHtml();
            }

            $p.each(function() {
//                response.parents.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                response.parents.push(teJQuery(this).outerHTML());
            });
        }        
    }

    if(request.retClosest){
        response.closest = new Array();
        var $parents = null;
        if(request.pLocator == null || trimString(request.pLocator).length == 0 || request.pLocator == "jquery="){
        //    $parents = teJQuery("html:first");
//            $parents =  teJQuery("#selenium_myiframe").contents().find("html:first");
            $parents = this.getHtml();
        }else{
            try{
                $parents = teJQuery(this.browserbot.findElement(request.pLocator));
            }catch(err){
             //   $parents = teJQuery("html:first");
             //   $parents =  teJQuery("#selenium_myiframe").contents().find("html:first");
                $parents = this.getHtml();
            }
        }

        if($parents != null && $parents.length > 0){
            if(request.attributes != null){

                var builder = new JQueryBuilder();

                var keys = new Array();
                for(var key in request.attributes){
                    if(key != "tag" && (!builder.inBlackList(key))){
                        keys.push(key);
                    }
                }
                var jqs = "";
                var id = request.attributes["id"];
                var tag = request.attributes["tag"];

                if(tag == null || tag == undefined || tag.trim().length == 0){
                    //TODO: need to double check if this is correct or not in jQuery
                    tag = "*";
                }
//                alert("tag " + tag);
//                var $closest = null;
                //Use tag for the initial search
                var $closest = $parents.find(tag);
                if(id != null && id != undefined && (!builder.isPartial(id))){
                    jqs = builder.buildId(id);
//                    alert("With ID jqs=" + jqs);
                    $closest = $parents.find(jqs);
//                    alert("Found closest " + $closest.length);
                    $closest.each(function() {
//                        response.closest.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                        response.closest.push(teJQuery(this).outerHTML());
                    });
                }else{
                    jqs = tag;
//                    alert("jqs=" + jqs);
                    for (var m = 0; m < keys.length; m++) {
                        var attr = keys[m];
                        var tsel = builder.buildSelector(attr, request.attributes[attr]);
                        var $mt = $parents.find(jqs + tsel);
//                            alert("Found for attr=" + attr + " val=" + request.attributes[attr] + " jqs=" + jqs + " tsel=" + tsel + " is " + $mt.length);
                        if ($mt.length > 0) {
                            $closest = $mt;
                            jqs = jqs + tsel;
                        }
//                            alert("jqs=" + jqs);
                    }
                    if ($closest != null && $closest.length > 0) {
                        $closest.each(function() {
//                            response.closest.push(teJQuery('<div>').append(teJQuery(this).clone()).html());
                            response.closest.push(teJQuery(this).outerHTML());
                        });
                    }
                }
            }
        }
    }

    if(request.retMatch){
//       $html = teJQuery("#selenium_myiframe").contents().find("html:first");
//       response.html = teJQuery('<div>').append($html.clone()).html();
//       response.html =  teJQuery('<div>').append(this.getHtml().clone()).html();
       response.html = this.getHtmlSource();
    }
//    return JSON.stringify(response);
    return response;
};

Selenium.prototype.getBundleResponse = function(bundle){
    !tellurium.logManager.isUseLog || !tellurium.logManager.isUseLog || fbLog("Issue Bundle Command ", bundle);
    tellurium.parseMacroCmd(bundle);
//    return tellurium.processMacroCmd();
    return tellurium.dispatchMacroCmd();
};

Selenium.prototype.getUseUiModule = function(jsonarray){
    return tellurium.cache.useUiModule(jsonarray);
};

Selenium.prototype.getValidateUiModule = function(jsonarray){
    return tellurium.cache.validateUiModule(jsonarray);
};

Selenium.prototype.isUiModuleCached = function(id){
    return tellurium.cache.isUiModuleCached(id);
};

Selenium.prototype.doUseTeApi = function(isUse){
    tellurium.useTeApi(isUse);
};

Selenium.prototype.doUseClosestMatch = function(isUse){
     tellurium.cache.useClosestMatch(isUse);
};

Selenium.prototype.doToggle = function(locator){
    var $elem = teJQuery(this.browserbot.findElement(locator));

	$elem.toggle();
};

Selenium.prototype.doDeleteAllCookies = function() {
    jaaulde.utils.cookies.del(true);
};

Selenium.prototype.doDeleteAllCookiesByJQuery = function() {
    teJQuery.cookies.del(true);
};

Selenium.prototype.doDeleteCookieByJQuery = function(cookieName) {
    teJQuery.cookies.del(cookieName);
};

Selenium.prototype.doSetCookieByJQuery = function(cookieName, value, options){
    teJQuery.cookies.set(cookieName, value, options);
};

Selenium.prototype.getCookieByJQuery = function(cookieName){
    return teJQuery.cookies.get(cookieName);
};

Selenium.prototype.doUseClosestMatch = function(isUse){
    tellurium.cache.useClosestMatch(isUse);
};

Selenium.prototype.doUpdateEngineState = function(state){
//    var state = JSON.parse(json, null);
    tellurium.cache.useClosestMatch(state.relax);
    tellurium.useTeApi(state.teApi);
    tellurium.cache.cacheOption = state.cache;
};

Selenium.prototype.getEngineState = function(){
    var state = new EngineState();
    state.relax = tellurium.cache.uiAlg.allowRelax;
    state.teApi = tellurium.isUseTeApi;
    state.cache = tellurium.cache.cacheOption;

//    return JSON.stringify(state);
    return state;
};

Selenium.prototype.doUseEngineLog = function(isUse){
    tellurium.logManager.isUseLog = isUse;
    if(firebug != undefined)
        firebug.env.debug = isUse;    
};


Selenium.prototype.clickElementForTe = function (element){
    var elementWithHref = getAncestorOrSelfWithJavascriptHref(element);

    if (browserVersion.isChrome && elementWithHref != null) {
        // SEL-621: Firefox chrome: Race condition bug in alert-handling code
        //
        // This appears to be because javascript href's are being executed in a
        // separate thread from the main thread when running in chrome mode.
        //
        // This workaround injects a callback into the executing href that
        // lowers a flag, which is initially raised. Execution of this click
        // command will wait for the flag to be lowered.

        var win = elementWithHref.ownerDocument.defaultView;
        var originalLocation = win.location.href;
        var originalHref = elementWithHref.href;

        elementWithHref.href = 'javascript:try { '
            + originalHref.replace(/^\s*javascript:/i, "")
            + ' } finally { window._executingJavascriptHref = undefined; }' ;

        win._executingJavascriptHref = true;

        this.browserbot.clickElement(element);

        return Selenium.decorateFunctionWithTimeout(function() {
            if (win.closed) {
                return true;
            }
            if (win.location.href != originalLocation) {
                // navigated to some other page ... javascript from previous
                // page can't still be executing!
                return true;
            }
            if (! win._executingJavascriptHref) {
                try {
                    elementWithHref.href = originalHref;
                }
                catch (e) {
                    // maybe the javascript removed the element ... should be
                    // no danger in not reverting its href attribute
                }
                return true;
            }

            return false;
        }, this.defaultTimeout);
    }

    this.browserbot.clickElement(element);
};

Selenium.prototype.getIndex = function(locator){
    var elem = this.browserbot.findElement(locator);
    return teJQuery(elem).index();
};

Selenium.prototype.getSelectValues = function(selectLocator) {
    /** Gets all option labels in the specified select drop-down.
   *
   * @param selectLocator an <a href="#locators">element locator</a> identifying a drop-down menu
   * @return string[] an array of all option labels in the specified select drop-down
   */
    var element = this.browserbot.findElement(selectLocator);

    var selectValues = [];

    for (var i = 0; i < element.options.length; i++) {
        var option = element.options[i].value;
        selectValues.push(option);
    }

    return selectValues;
};
