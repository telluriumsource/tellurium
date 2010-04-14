function UiModule(){
    //top level UI object
    this.root = null;

    this.valid = false;

    //hold a hash table of the uid to UI objects for fast access
    this.map = new Hashtable();

    //index for uid - dom reference for fast access
    this.indices = null;

    //If the UI Module is relaxed, i.e., use closest match
    this.relaxed = false;

    //the relax details including the UIDs and their corresponding html source
    this.relaxDetails = null;

    //number of matched snapshots
    this.matches = 0;

    //scaled score (0-100) for percentage of match
    this.score = 0;

    //ID Prefix tree, i.e., Trie, for the lookForId operation in group locating
    this.idTrie = new Trie();

    //Cache hit, i.e., direct get dom reference from the cache
    this.cacheHit = 0;

    //Cache miss, i.e., have to use walkTo to locate elements
    this.cacheMiss = 0;

    //the latest time stamp for the cache access
    this.timestamp = null;

    //UI module dump visitor
    this.dumpVisitor = new UiDumpVisitor();

    //Snapshot Tree, i.e., STree
    this.stree = null;
}

UiModule.prototype.dumpMe = function(){
    if(this.root != null){
        fbLog("Dump UI Module " + this.id, this);
        var context = new WorkflowContext();
        this.root.traverse(context, this.dumpVisitor);
    }
};

UiModule.prototype.increaseCacheHit = function(){
    this.cacheHit++;
    this.timestamp = Number(new Date());
};

UiModule.prototype.increaseCacheMiss = function(){
    this.cacheMiss++;
    this.timestamp = Number(new Date());
};

UiModule.prototype.getCacheUsage = function(){
    var cusage = new CacheUsage();
    cusage.cacheHit = this.cacheHit;
    cusage.totalCall = this.cacheHit + this.cacheMiss;
    if(cusage.totalCall > 0){
        cusage.usage = 100*cusage.cacheHit/cusage.totalCall;
    }

    !tellurium.logManager.isUseLog || fbLog("Get Cache Usage for UI Module " + this.id, cusage);
    return cusage;
};

UiModule.prototype.getId = function(){
    if(this.root != null)
        return this.root.uid;

    return null;
};

UiModule.prototype.parseUiModule = function(ulst){

//    var ulst = JSON.parse(json, null);
//    var ulst = jsonarray;
    var klst = new Array();
    !tellurium.logManager.isUseLog || fbLog("JSON Object ulst: ", ulst);
    !tellurium.logManager.isUseLog || fbLog("ulst length: ", ulst.length);
    for(var i=0; i<ulst.length; i++){
        !tellurium.logManager.isUseLog || fbLog("i: ", i);
        !tellurium.logManager.isUseLog || fbLog("Build from JSON object: ", ulst[i].obj);
        this.map.put(ulst[i].key, this.buildFromJSON(ulst[i].obj));
        klst.push(ulst[i].key);
    }

    this.buildTree(klst);
    !tellurium.logManager.isUseLog || fbLog("Parsed Ui Module " + this.id + ": ", this);
    if(tellurium.logManager.isUseLog)
        this.dumpMe();
};

UiModule.prototype.buildFromJSON = function(jobj){
    var builder = tellurium.uiBuilderMap.get(jobj.uiType);

    var obj = null;
    if(builder  != null){
        obj = builder.build();
    }
    if(obj == null){
        obj = new UiContainer();
    }

    objectCopy(obj, jobj);
    !tellurium.logManager.isUseLog || fbLog("Build from JSON: ", jobj);
    !tellurium.logManager.isUseLog || fbLog("Object built: ", obj);

    return obj;
};

UiModule.prototype.buildTree = function(keys){
    for(var i=0; i<keys.length; i++){
        var uiobj = this.map.get(keys[i]);
        //link the uiobject back to the ui module so that it knows which UI module it lives in
        uiobj.uim = this;
        var id = uiobj.getIdAttribute();
        //build ID Prefix tree, i.e., Trie
        //TODO: may consider stricter requirement that the ID cannot be partial, i.e., cannot starts with * ^ ! $
        if(id != null && id.trim().length > 0){
            !tellurium.logManager.isUseLog || fbLog("Add object " + keys[i] + "'s id " + id + " to ID Trie. ", uiobj);
            this.idTrie.insert(keys[i], id);
        }

        if(this.root == null){
            this.root = uiobj;
            this.id = uiobj.uid;
        }else{
            var uiid = new Uiid();
            uiid.convertToUiid(keys[i]);
            this.root.goToPlace(uiid, uiobj);
        }
    }
};

UiModule.prototype.prelocate = function(){
    if(this.root != null){
        this.root.prelocate();
        this.valid = true;
    }
};

UiModule.prototype.index = function(uid){
    return this.indices.get(uid);
};

UiModule.prototype.walkTo = function(context, uiid) {
    var first = uiid.pop();
    if (first == this.root.uid) {
        context.domRef = this.root.domRef;
        return this.root.walkTo(context, uiid);
    }

    return null;
};


UiModule.prototype.findInvalidAncestor = function(context, uiid){
    var obj = this.walkTo(context, uiid);
    var queue = new FiloQueue();
    queue.push(obj);
    while(obj.parent != null){
        if(!validateDomRef(obj.parent)){
            queue.push(obj.parent);
            obj = obj.parent;
        }
    }

    return queue;
};

function UiModuleLocatingResponse(){
    //ID for the UI module
    this.id = null;

    //Successfully found or not
    this.found = false;

    //whether this the UI module used closest Match or not
    this.relaxed = false;

    //number of matched snapshots
    this.matches = 0;

    //scaled score (0-100) for percentage of match
    this.score = 0;

    //details for the relax, i.e., closest match
    this.relaxDetails = null;
}
