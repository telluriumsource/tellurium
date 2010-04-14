/*
//Ui Module cache data
function UmCacheData(){
    this.uiModule = null;
    this.count = 0;
    this.timestamp = Number(new Date());
}


//XXX: Depreciated, the cached data is UI module now
//Cached Data, use uid as the key to reference it
function CacheData(){
    //jQuery selector associated with the DOM reference, which is a whole selector
    //without optimization so that it is easier to find the reminding selector for its children
    this.selector = null;
    //optimized selector for actual DOM search
    this.optimized = null;
    //jQuery object for DOM reference
    this.reference = null;
    //number of reuse
    this.count = 0;
    //last use time
    this.timestamp = Number(new Date());
}
*/

//cache eviction policies
//simply discard new selector
function DiscardNewPolicy(){
    this.name = "DiscardNewPolicy";
}

DiscardNewPolicy.prototype.applyPolicy = function (cache, key, data){

};

DiscardNewPolicy.prototype.myName = function(){
    return this.name;
};

var discardNewCachePolicy = new DiscardNewPolicy();

function DiscardOldPolicy(){
    this.name = "DiscardOldPolicy";
}

DiscardOldPolicy.prototype.applyPolicy = function (cache, key, data){
    var keys = cache.keySet();
    var toBeRemoved = keys[0];
    var oldest = cache.get(toBeRemoved).timestamp;
    for(var i=1; i<keys.length; i++){
        var akey = keys[i];
        var val = cache.get(akey).timestamp;
        if(val < oldest){
            toBeRemoved = akey;
            oldest = val;
        }
    }

    cache.remove(toBeRemoved);
    cache.put(key, data);
};

DiscardOldPolicy.prototype.myName = function(){
    return this.name;
};

var discardOldCachePolicy = new DiscardOldPolicy();

//remove the least used select in the cache
function DiscardLeastUsedPolicy (){
    this.name = "DiscardLeastUsedPolicy";
}

DiscardLeastUsedPolicy.prototype.applyPolicy = function(cache, key, data){
    var keys = cache.keySet();
    var toBeRemoved = keys[0];
//    var leastCount = cache.get(toBeRemoved).count;
    var leastCount = cache.get(toBeRemoved).cacheHit;
    for(var i=1; i<keys.length; i++){
        var akey = keys[i];
//        var val = cache.get(akey).count;
        var val = cache.get(akey).cacheHit;
        if(val < leastCount){
            toBeRemoved = akey;
            leastCount = val;
        }
    }

    cache.remove(toBeRemoved);
    cache.put(key, data);
};

DiscardLeastUsedPolicy.prototype.myName = function(){
    return this.name;
};

var discardLeastUsedCachePolicy = new DiscardLeastUsedPolicy();

//If found invalid selector, remove it and put the new one in
//otherwise, discard the new one
function DiscardInvalidPolicy(){
    this.name = "DiscardInvalidPolicy";
}

DiscardInvalidPolicy.prototype.applyPolicy = function(cache, key, data){
    var keys = cache.keySet();
    for(var i=0; i<keys.length; i++){
        var akey = keys[i];
        if(!akey.valid){
            cache.remove(akey);
            cache.put(key, data);
            break;            
        }
    }
};

DiscardInvalidPolicy.prototype.myName = function(){
    return this.name;
};

var discardInvalidCachePolicy = new DiscardInvalidPolicy();

function CacheUsage(){
    this.totalCall = 0;
    this.cacheHit = 0;
    //percentage of the cacheHit/totalCall
    this.usage = 0;
}

function TelluriumCache(){

    //global flag to decide whether to cache jQuery selectors
    this.cacheOption = false;

    //cache for UI modules
    this.sCache = new Hashtable();

    this.maxCacheSize = 50;

    this.cachePolicy = discardOldCachePolicy;

    //Algorithm handler for UI
    this.uiAlg = new UiAlg();
}

TelluriumCache.prototype.useClosestMatch = function(isUse){
    !tellurium.logManager.isUseLog || fbLog("call useClosestMatch", isUse);
    if (typeof(isUse) == "boolean") {
        this.uiAlg.allowRelax = isUse;
    } else {
        this.uiAlg.allowRelax = ("true" == isUse || "TRUE" == isUse);
    }

    !tellurium.logManager.isUseLog || fbLog("Call useClosestMatch(" + isUse + ") to set allowRelax to ", this.uiAlg.allowRelax);
};

TelluriumCache.prototype.cleanCache = function(){
    this.sCache.clear();
};

TelluriumCache.prototype.getCacheSize = function(){
    return this.sCache.size();
};

TelluriumCache.prototype.updateUseCount = function(key, data){
    if(key != null && data != null){
        data.count++;
        this.sCache.put(key, data);
    }
};

TelluriumCache.prototype.addToCache = function(key, val){
    if(this.sCache.size() < this.maxCacheSize){
        val.timestamp = Number(new Date());
        this.sCache.put(key, val);
    }else{
        this.cachePolicy.applyPolicy(this.sCache, key, val);
    }
};

//update existing data to the cache
TelluriumCache.prototype.updateToCache = function(key, val){
//    val.count++;
    val.timestamp = Number(new Date());
    this.sCache.put(key, val);
};

TelluriumCache.prototype.getCachedData = function(key){

    return this.sCache.get(key);
};

/*
Remove this, do relocating instead, possibly with reference uimodule to speed up the search

TelluriumCache.prototype.findUiElementFromAncestor = function(uimodule, uid){
    //TODO: Need to do individual locating either from its parent or use the generated locator
    !tellurium.logManager.isUseLog || fbLog("Trying to find UI element" + uid + " from its ancestor in UI module", uimodule);
    var context = new WorkflowContext();
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    uiid.reverse();
    var queue = uimodule.findInvalidAncestor(context, uiid);
    !tellurium.logManager.isUseLog || fbLog("Find invalid ancestor", queue);
    
};
*/

TelluriumCache.prototype.relocateUiModule = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
 //   uiid.reverse();
    if(uiid.size() > 0){
        var first = uiid.peek();
        var uim = this.sCache.get(first);
        !tellurium.logManager.isUseLog || fbLog("Found cached UI module " + first, uim);
        if (uim != null) {
            //TODO: optimize this by using some still valid dom references in the UI module
            this.uiAlg.santa(uim, null);
            //set the UI Module to be valid after it is located
            uim.valid = true;
            !tellurium.logManager.isUseLog || fbLog("Ui Module after redoing Group Locating: ", uim);
        }
    }
};

TelluriumCache.prototype.getIndexedTree = function(context, uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var elist = new Array();

    if(uiid.size() > 0){
        var first = uiid.peek();
        var uim = this.sCache.get(first);
        !tellurium.logManager.isUseLog || fbLog("Found cached UI module " + first, uim);
        if(uim != null){
            var elems = uim.indices.valSet();
            elist = elist.concat(elems);
        }
    }

    !tellurium.logManager.isUseLog || fbLog("Get Indexed Tree for " + uid, elist);
    return elist;
};

TelluriumCache.prototype.getSubtree = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var elist = new Array();

    if(uiid.size() > 0){
        var first = uiid.peek();
        var uim = this.sCache.get(first);
        !tellurium.logManager.isUseLog || fbLog("Found cached UI module " + first, uim);
        if(uim != null){
            var context = new WorkflowContext();
            var obj = uim.walkTo(context, uiid);
            !tellurium.logManager.isUseLog || fbLog("After walkTo, found object ", obj);
            !tellurium.logManager.isUseLog || fbLog("After walkTo, context ", context);
            if (obj != null) {

            }
            
        }
    }

    return elist;
};

TelluriumCache.prototype.walkToUiObject = function(context, uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var obj = null;

    if(uiid.size() > 0){
        var first = uiid.peek();
        var uim = this.sCache.get(first);
        !tellurium.logManager.isUseLog || fbLog("Found cached UI module " + first, uim);
        if(uim != null){
            obj = uim.walkTo(context, uiid);
            !tellurium.logManager.isUseLog || fbLog("After walkTo, found object ", obj);
            !tellurium.logManager.isUseLog || fbLog("After walkTo, context ", context);
        }
    }

    return obj;
};

TelluriumCache.prototype.walkToUiObjectWithException = function(context, uid){
    var obj = this.walkToUiObject(context, uid);
    if (obj == null) {
        fbError("Cannot find UI object " + uid, this);
        throw new SeleniumError("Cannot find UI object " + uid);
    }

    return obj;
};

TelluriumCache.prototype.takeSnapshot = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);
    var stree = null;
    if(uiid.size() > 0){
        var first = uiid.peek();
        var uim = this.sCache.get(first);
        if(uim == null){
            fbError("Cannot find UI Module " + first, this.sCache);
        }else{
            !tellurium.logManager.isUseLog || fbLog("Found cached UI module " + first, uim);
            stree = this.uiAlg.buildSTree(uim);
            uim.stree = stree;
        }

    }

    return stree;
};

TelluriumCache.prototype.getSTree = function(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    if (uiid.size() > 0) {
        var first = uiid.peek();
        var uim = this.sCache.get(first);
        if (uim != null)
            return uim.stree;
    }

    return null;
};

TelluriumCache.prototype.getCachedUiElement = function(uid){

    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    if(uiid.size() > 0){
        var first = uiid.peek();
        var uim = this.sCache.get(first);
        !tellurium.logManager.isUseLog || fbLog("Found cached UI module " + first, uim);
        if(uim != null){
            var domref = uim.index(uid);
            !tellurium.logManager.isUseLog || fbLog("Got cached UI element " + uid + " from indices.", domref);
            if(domref == null){
                uim.increaseCacheMiss();
                //if could not find directly from the UI module indices, then try to use walkTo to find the element first
                //and then its domRef
                var context = new WorkflowContext();
                context.alg = this.uiAlg;
                var obj = uim.walkTo(context, uiid);
                !tellurium.logManager.isUseLog || fbLog("After walkTo, found object ", obj);
                !tellurium.logManager.isUseLog || fbLog("After walkTo, context ", context);
                if(obj != null){
                    domref = context.domRef;
                }
                !tellurium.logManager.isUseLog || fbLog("Got UI element " + uid + " by walking through the UI module " + first, domref);
            }else{
                uim.increaseCacheHit();
            }
            
            return domref;
        }
    }

    return null;
};

TelluriumCache.prototype.useUiModule = function(jsonarray){
    var uim = new UiModule();
    !tellurium.logManager.isUseLog || fbLog("Input JSON Array for UI Module: ", jsonarray);
    uim.parseUiModule(jsonarray);
    var response = new UiModuleLocatingResponse();
    var result = this.uiAlg.santa(uim, null);
    if(result){
        //set the UI Module to be valid after it is located
        uim.valid = true;
        !tellurium.logManager.isUseLog || fbLog("Ui Module after Group Locating: ", uim);
        var id = uim.getId();
        var cached = this.getCachedData(id);
        if (cached == null) {
            !tellurium.logManager.isUseLog || fbLog("Adding Ui Module " + id + " to cache...", uim);
            this.addToCache(id, uim);
        } else {
            !tellurium.logManager.isUseLog || fbLog("Updating Ui Module " + id + " to cache...", uim);
            this.updateToCache(id, uim);
        }

        response.id = id;
        response.relaxed = uim.relaxed;
        if (!response.relaxed)
            response.found = true;
        response.relaxDetails = uim.relaxDetails;
        response.matches = uim.matches;
        response.score = uim.score;
        !tellurium.logManager.isUseLog || fbLog("UseUiModule Response for " + id, response);
    }

    return response;
};

TelluriumCache.prototype.validateUiModule = function(jsonarray){
    var uim = new UiModule();
    !tellurium.logManager.isUseLog || fbLog("Input JSON for UI Module: ", jsonarray);
    uim.parseUiModule(jsonarray);
    this.uiAlg.validate(uim, null);
    //set the UI Module to be valid after it is located
    uim.valid = true;
    !tellurium.logManager.isUseLog || fbLog("Ui Module after Group Locating: ", uim);
    var id = uim.getId();

    var response = new UiModuleLocatingResponse();
    response.id = id;
    response.relaxed = uim.relaxed;
    if(!response.relaxed)
        response.found = true;
    response.relaxDetails = uim.relaxDetails;
    response.matches = uim.matches; 
    response.score = uim.score;
    !tellurium.logManager.isUseLog || fbLog("UseUiModule Response for " + id, response);

//    return JSON.stringify(response);
    return response;
};

TelluriumCache.prototype.isUiModuleCached = function(id){

    return this.sCache.get(id) != null;
};

//Cache Selectors

TelluriumCache.prototype.getCachedSelector = function(key){

    return this.sCache.get(key);
};

TelluriumCache.prototype.addSelectorToCache = function(key, data){
    if(this.sCache.size() < this.maxCacheSize){
        this.sCache.put(key, data);
    }else{
        this.cachePolicy.applyPolicy(this.sCache, key, data);
    }
};

//update existing selector to the cache
TelluriumCache.prototype.updateSelectorToCache = function(key, data){
    data.count++;
    data.timestamp = Number(new Date());
    this.sCache.put(key, data);
};


TelluriumCache.prototype.validateCache = function($reference){
    //This may impose some problem if the DOM element becomes invisable instead of being removed
    try{
        return $reference.is(':visible');
//        return ($reference.eq(0).parent().length > 0);
//        return jQuery($reference).is(':visible');
//        return jQuery($reference).parents('html').length > 0;
//        return $reference.parents('html').length > 0;
//        return true;
    }catch(e){
        //seems for IE, it throws exception
//        jslogger.error("Validate exception " + e.message);
        return false;
    }
};

TelluriumCache.prototype.checkSelectorFromCache = function(key){
    var $found = null;

    var cached = this.getCachedSelector(key);
    if (cached != null) {
        $found = cached.reference;
        //validate the DOM reference
        if (!this.validateCache($found)) {
            $found = null;
            this.sCache.remove(key);
        }else{
            this.updateSelectorToCache(key, cached);
        }
    }

    return $found;
};

TelluriumCache.prototype.checkAncestorSelector = function(akey){
    var cached = this.getCachedSelector(akey);

    if (cached != null) {
        //check if the ancestor's DOM reference is still valid
        if (!this.validateCache(cached)) {
            //if not valid, try to select it using jQuery
            var $newsel = teJQuery(cached.optimized);
            if ($newsel.length > 0) {
                cached.reference = $newsel;
                cached.count = 0;
                this.updateSelectorToCache(akey, cached);
            } else {
                //remove invalid selector
                this.sCache.remove(akey);
                cached = null;
            }
        }else{
            this.updateSelectorToCache(akey, cached);
        }
    }

    return cached;
};

TelluriumCache.prototype.findFromAncestor = function(ancestor, sel){
    var asel = ancestor.selector;
    var $found = null;

    if(sel.length > asel.length){
        var start = sel.substring(0, asel.length);
        if(start == asel){
            var leftover = trimString(sel.substring(asel.length));
            $found = ancestor.reference.find(leftover);
        }
    }

    return $found;
};

TelluriumCache.prototype.setCacheState = function(flag){
    this.cacheOption = flag;
};

TelluriumCache.prototype.getCacheUsage = function(){
    var out = [];
    var keys = this.sCache.keySet();
    for(var i=0; i< keys.length; i++){
        var key = keys[i];
        var uim = this.sCache.get(key);
        var usage = {};
        usage[key] = uim.getCacheUsage();
        out.push(usage);
    }

    return out;
//    return JSON.stringify(out);
};

TelluriumCache.prototype.useDiscardNewPolicy = function(){
    this.cachePolicy = discardNewCachePolicy;
};

TelluriumCache.prototype.useDiscardOldPolicy = function(){
    this.cachePolicy = discardOldCachePolicy;
};

TelluriumCache.prototype.useDiscardLeastUsedPolicy = function(){
    this.cachePolicy = discardLeastUsedCachePolicy;
};

TelluriumCache.prototype.useDiscardInvalidPolicy = function(){
    this.cachePolicy = discardInvalidCachePolicy;
};

TelluriumCache.prototype.getCachePolicyName = function(){
    return this.cachePolicy.name;
};
