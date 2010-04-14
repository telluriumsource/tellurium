
//base UI object
var UiObject = Class.extend({
    init: function() {
        //UI object identification
        this.uid = null;

        //meta data
        this.metaData = null;

        //its parent UI object
        this.parent = null;

        //namespace, useful for XML, XHTML, XForms
        this.namespace = null;

        this.locator = null;

        //event this object should be respond to
        this.events = null;

        //should we do lazy locating or not, i.e., wait to the time we actually use this UI object
        //usually this flag is set because the content is dynamic at runtime
        this.lazy = false;

        //If it is contained in its parent or not
        this.self = false;

        this.uiType = null;

        //Tellurium Core generated locator for this UI Object
        this.generated = null;

        //dom reference
        this.domRef = null;

        //UI Module reference, which UI module this UI object belongs to
        this.uim = null;
    },

    checkLevel: function(){
        if(this.parent != null){
            return this.parent.checkLevel() + 1;
        }

        return 1;
    },

    getIdAttribute: function(){
        //return the ID attribute
        var ida = null;
        if(this.locator != null && this.locator.attributes != undefined && this.locator.attributes != null){
//            ida = this.locator.attributes.get("id");
            ida = this.locator.attributes["id"];
            if(ida == undefined)
                ida = null;
        }

        return ida;
    },

    getChildrenIds: function(){
        return null;
    },

    goToPlace: function(uiid, uiobj) {

        if(uiid.size() == 1){
            uiid.pop();
            objectCopy(this, uiobj);
        }else{
             fbError("Wrong uiid ",  uiid);
        }
    },

    locate: function(uialg) {
        uialg.locateInAllSnapshots(this);
    },

    locateSelf: function(context, domRef){
        if (this.locator != null && domRef != null) {
            var alg = context.alg;
            var sel = alg.buildSelector(this.locator);
            var $found = teJQuery(domRef).find(sel);

            if ($found.size() == 1) {
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, $found.get(0));
                return $found.get(0);
            } else {
                if ($found.size() == 0){
                    fbError("Cannot find UI element " + this.uid, this);
                    throw new SeleniumError("Cannot find UI element " + this.uid);
                }
                if ($found.size() > 1) {
                    fbError("Found " + $found.size() + " matches for UI element " + this.uid, $found.get());
                    throw new SeleniumError("Found " + $found.size() + " matches for UI element " + this.uid);
                }
            }
        }else{
            return domRef;
        }
    },

    buildPid: function(pid, rid){
        if(pid == null)
            return rid;
        
        return pid + "." + rid;
    },

    buildSNode: function(context, pid, rid, domref){
        var node = new UiSNode();
        node.objRef = this;
        node.rid = rid;
        node.pid = pid;
//        node.uid = this.uid;
/*        if(this.domRef != null){
            node.domRef = this.domRef;
        }else{
            node.domRef = this.locateSelf(context, domref);
        }*/
        node.domRef = domref;

        return node;
    },

    lookChildren: function() {
        return null;
    },

    //add all children in no matter if they are cacheable or not, useful for templates
    lookChildrenNoMatterWhat: function() {
        return null;
    },

    bind: function(snapshot, uialg) {
        var fuid = this.fullUid();
        if (!this.lazy) {
            this.domRef = snapshot.getUi(fuid);
        }
    },

    snapshot: function() {
        if (this.generated)
            this.domRef = selenium.browserbot.findElement(this.generated);
    },

    prelocate: function() {
        if (this.amICacheable())
            this.snapshot();
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                //if the parent or root dom reference is null, cannot go any further
                //If its locator, i.e., itself is a logical object, do not do any locating
                if (this.locator != null && context.domRef != null) {

                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef);
                    if(sel !=null && sel.trim().length > 0){
                        $found = $found.find(sel);
                    }
                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0)
                            fbError("Cannot find UI element " + uiid, this);
                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + uiid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }

        return this;
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
    },

    amICacheable: function() {
        //check its parent and do not cache if its parent is not cacheable
        //If an object is cacheable, the path from the root to itself should
        //be all cacheable
        if (this.parent != null) {
            return (!this.lazy) && this.parent.amICacheable() && (!this.parent.noCacheForChildren);
        }

        return (!this.lazy);
    },

    fullUid: function() {
        if (this.parent != null) {
            return this.parent.fullUid() + "." + this.uid;
        }

        return this.uid;
    },

    respondsTo: function(methodName) {
        return this[methodName] != undefined; 
    },

    respondsToWithException: function(methodName){
        if(this[methodName] != undefined){
            return true;
        }else{
            var fid = this.fullUid();
            fbError("UI Object " + fid + " does not have the method " + methodName, this);
            throw new SeleniumError("UI Object " + fid + " does not have the method " + methodName);
        }
    }
});

var UiAllPurposeObject = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'AllPurposeObject';
    }
});

var UiButton = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Button';
        this.tag = "input";
    }
});

var UiCheckBox = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'CheckBox';
        this.tag = "input";
        this.type = "checkbox";
    }
});


var UiIcon = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Icon';
    }
});

var UiImage = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Image';
        this.tag = "img";
    }
});

var UiInputBox = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'InputBox';
        this.tag = "input";
    }
});

var UiRadioButton = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'RadioButton';
        this.tag = "input";
        this.type = "radio";
    }
});

var UiSelector = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Selector';
        this.tag = "select";
    }
});

var UiSpan = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Span';
        this.tag = "span";
    }
});

var UiSubmitButton = UiButton.extend({
    init: function(){
        this._super();
        this.uiType = 'SubmitButton';
        this.type = "submit";
    }
});

var UiTextBox = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'TextBox';
    }
});

var UiUrlLink = UiObject.extend({
    uiType: 'UrlLink',
    tag: "a"
});

var UiContainer = UiObject.extend({
    init: function(){
        this._super();
        this.uiType = 'Container';
        this.group = false;
        this.noCacheForChildren = false;
        this.components = new Hashtable();
    },

    getChildrenIds: function(){
        var ids = null;
        if(this.uim != null){
            var fid = this.fullUid();
            ids = this.uim.idTrie.getChildrenData(fid);
            !tellurium.logManager.isUseLog || fbLog("Get children ids from ID Trie for " + fid, ids);
        }

        return ids;
    },

    goToPlace:  function(uiid, uiobj) {
        if(uiid.size() == 1){
            uiid.pop();
//            if (this.uid == null)
            objectCopy(this, uiobj);            
        }else{
            uiid.pop();
            var cuid = uiid.peek();

            if(uiid.size() == 1){
                uiid.pop();
                uiobj.parent = this;
                this.components.put(cuid, uiobj);
            }else{
                var child = this.components.get(cuid);
                child.goToPlace(uiid, uiobj);
            }
        }
    },

    locate:  function(uialg){
        uialg.locateInAllSnapshots(this);
        
        if (!this.noCacheForChildren) {
            //need to push all its children into the object queue
            !tellurium.logManager.isUseLog || fbLog("Children for Container " + this.uid + ": ", this.components.showMe());
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if (!component.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Add child of Container " + this.uid + " to UiAlg : ", component);
                    uialg.addChildUiObject(component);
                }
            }
        }
    },

    lookChildren: function() {
        var validChildren = new Array();

        if (!this.noCacheForChildren) {
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if (!component.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable child of Container " + this.uid + ": ", component);
                    if(component.locator != null && (!component.self)){
                        validChildren.push(component);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                        var ccr = component.lookChildren();
                        if(ccr != null && ccr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                            validChildren = validChildren.concat(ccr);
                        }
                    }
                }
            }
        }

        return validChildren;
    },

    lookChildrenNoMatterWhat: function() {
        var children = new Array();

        var valset = this.components.valSet();
        !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
        for (var i = 0; i < valset.length; i++) {
            var component = valset[i];
            !tellurium.logManager.isUseLog || fbLog("Look ahead nomatter what at cachable child of Container " + this.uid + ": ", component);
            if (component.locator != null && (!component.self)) {
                children.push(component);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                var ccr = component.lookChildrenNoMatterWhat();
                if (ccr != null && ccr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                    children = children.concat(ccr);
                }
            }
        }

        return children;
    },

    bind: function(snapshot, uialg) {
        var fuid = this.fullUid();
        if (!this.lazy) {
            this.domRef = snapshot.getUi(fuid);
        }
        //need to push all its children into the object queue
        var valset = this.components.valSet();
        for(var i=0; i<valset.length; i++){
            if(!valset[i].lazy)
                uialg.addChildUiObject(valset[i]);
        }
    },
    
    prelocate: function(){
        if(this.amICacheable()){
            this.snapshot();
            var keys = this.components.keySet();
            for(var i=0; i<keys.length; i++){
                var child = this.components.get(keys[i]);
                child.prelocate();
            }
        }
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        if(this.components != null && this.components.length > 0){
            var keys = this.components.keySet();
            for(var i=0; i<keys.length; i++){
                var child = this.components.get(keys[i]);
                child.traverse(context, visitor);
            }
        }
    },
    
    buildSNode: function(context, pid, rid, domref){

        var node = new UiCNode();
        node.objRef = this;
        node.rid = rid;
        node.pid = pid;
        node.domRef = domref;
        
        if(domref != null && this.components.size() > 0){
            var keys = this.components.keySet();
            for(var i=0; i<keys.length; i++){
                var key = keys[i];
                var child = this.components.get(key);
                if(child.uiType == "Repeat"){
                    this.buildSNodeForRepeat(context, node, child);
                }else{
                     var cdomref = child.domRef;
                     if(cdomref == null){
                         cdomref = this.locateChild(context, domref, child);
                     }
                     var csdata = new UiSData(this.buildPid(pid, rid), key, child, cdomref);
                     var alg = context.alg;
                     alg.addChildUiObject(csdata);
                }
            }
        }

        return node;
    },

    buildSNodeForRepeat: function(context, pnode, repeat){
        var cdoms = this.locateChildren(context, pnode.domRef, repeat);
        if(cdoms != null && cdoms.length > 0){
            var keys = repeat.components.keySet();

            for(var i=0; i<cdoms.length; i++){
                var rnode = new UiCNode();
                rnode.objRef = repeat;
                rnode.parent = pnode;
                rnode.pid = this.buildPid(pnode.pid, pnode.rid);
                rnode.rid = repeat.uid + "_" + (i+1);
                rnode.domRef = cdoms[i];
                pnode.insert(context, rnode);

                for(var j=0; j<keys.length; j++){
                    var key = keys[j];
                    var child = repeat.components.get(key);
                    if(child.uiType == "Repeat"){
                        this.buildSNodeForRepeat(context, rnode, child);
                    }else{
                         var cdomref = child.domRef;
                         if(cdomref == null){
                             cdomref = this.locateChild(context, rnode.domRef, child);
                         }
                         var csdata = new UiSData(this.buildPid(rnode.pid, rnode.rid), key, child, cdomref);
                         var alg = context.alg;
                         alg.addChildUiObject(csdata);
                    }                    
                }
            }
        }
    },

    locateChildren: function(context, domRef, child){
        if(child.locator != null && domRef != null){
            var alg = context.alg;
            var sel = alg.buildSelector(child.locator);
            var $found = teJQuery(domRef);
            if(sel != null && sel.trim().length > 0){
                $found = $found.find(sel);
            }
            if ($found.size() > 1) {
                $found = alg.lookId(child, $found);
                !tellurium.logManager.isUseLog || fbLog("Look Id result for " + child.uid, $found.get());
                if($found.size() > 1){
                    if(child.noCacheForChildren){
                        //Use bestEffort to eliminate multiple matches
                        $found = alg.bestEffort(child, $found);
                        !tellurium.logManager.isUseLog || fbLog("Best Effort result for " + child.uid, $found.get());
                    }else{
                        //Use lookAHead to eliminate multiple matches
                        $found = alg.lookAhead(child, $found);
                        !tellurium.logManager.isUseLog || fbLog("Look Ahead result for " + child.uid, $found.get());
                    }
                }
            }

            return $found.get();
        }else{
            var result = new Array();
            result.push(domRef);
            return result;
        }
    },

    locateChild: function(context, domRef, child){
        if(child.locator != null && domRef != null){
            var alg = context.alg;
            var sel = alg.buildSelector(child.locator);
            var $found = teJQuery(domRef);
            if(sel != null && sel.trim().length > 0){
                $found = $found.find(sel);
            }
            if ($found.size() > 1) {
                $found = alg.lookId(child, $found);
                !tellurium.logManager.isUseLog || fbLog("Look Id result for " + child.uid, $found.get());
                if($found.size() > 1){
                    if(child.noCacheForChildren){
                        //Use bestEffort to eliminate multiple matches
                        $found = alg.bestEffort(child, $found);
                        !tellurium.logManager.isUseLog || fbLog("Best Effort result for " + child.uid, $found.get());
                    }else{
                        //Use lookAHead to eliminate multiple matches
                        $found = alg.lookAhead(child, $found);
                        !tellurium.logManager.isUseLog || fbLog("Look Ahead result for " + child.uid, $found.get());
                    }
                }
            }
            
            if ($found.size() == 1) {
                !tellurium.logManager.isUseLog || fbLog("Found element " + child.uid, $found.get(0));
                return $found.get(0);
            }else if($found.size() == 0){
                fbError("Cannot find UI element " + child.uid, child);
                throw new SeleniumError("Cannot find UI element " + child.uid);
            }else{
                fbError("Found " + $found.size() + " matches for UI element " + child.uid, $found.get());
                throw new SeleniumError("Found " + $found.size() + " matches for UI element " + child.uid);
            }
        }

        return domRef;
    },
    
    walkTo: function(context, uiid){
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);

                    var $found = teJQuery(context.domRef);
                    if(sel != null && sel.trim().length > 0){
                        $found = $found.find(sel);
                    }
                    if ($found.size() > 1) {
                        //first try lookId
                        $found = alg.lookId(this, $found);
                        !tellurium.logManager.isUseLog || fbLog("Look Id result for " + this.uid, $found.get());
                        if($found.size() > 1){
                            //Use lookAHead to eliminate multipe matches
                            $found = alg.lookAhead(this, $found);
                            !tellurium.logManager.isUseLog || fbLog("Look Ahead result for " + this.uid, $found.get());
                        }
                    }

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0){
                            context.domRef = null;
                            fbError("Cannot find UI element " + this.uid, this);
                        }

                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }

        if(uiid.size() < 1)
            return this;

        var cid = uiid.pop();
        var child = this.components.get(cid);
        if(child != null){
            !tellurium.logManager.isUseLog || fbLog("Walk to child " + cid, child);
            return child.walkTo(context, uiid);
        }else{
            fbError("Cannot find child " + cid, child);
            context.domRef = null;
            return null;
        }
    }
});

var UiRepeat = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Repeat';
        //Repeat will return multiple elements for the same locator at runtime, should not cache it
        this.lazy = true;
        //Since Repeat is not unique at runtime, its children are dynamic as well
        this.noCacheForChildren = true;
        this.components = new Hashtable();
    },

    buildSNode: function(context, pid, rid, domref){
        fbError("You should never reach here ", this);
        throw new SeleniumError("You should never reach here ");
    },

/*    buildSNode: function(context, pid, rid, domref){
        var node = new UiCNode();
        node.objRef = this;
        node.rid = rid;
        node.pid = pid;
        node.domRef = domref;

        var selves = this.locateSelf(context);
        if (selves != null && selves.length > 0) {
            var keys = this.components.keySet();
            var alg = context.alg;
            var npid = this.buildPid(pid, rid);

            for (var i = 0; i < selves.length; i++) {
                var cnode = new UiCNode();
                cnode.objRef = this;
//                    cnode.rid = rid + "_" + (i-1);
                cnode.rid = "_" + (i + 1);
                cnode.pid = npid;
                cnode.domRef = selves[i];
                cnode.parent = node;

                for (var j = 0; j < keys.length; j++) {
                    var child = this.components.get(keys[j]);
                    var cdmf = this.locateChild(context, selves[i], child);
                    var csdata = new UiSData(this.buildPid(npid, cnode.rid), keys[j], child, cdmf);

                    alg.addChildUiObject(csdata);
                }
            }
        }

        return node;
    },*/

    locateSelf: function(context){
        !tellurium.logManager.isUseLog || fbLog("Calling locateSelf for " + this.uid, this);
        if (this.locator != null && context.domRef != null) {
            var alg = context.alg;
            var sel = alg.buildSelector(this.locator);

            var $found = teJQuery(context.domRef);
            if(sel != null && sel.trim().length > 0){
                $found = $found.find(sel);
            }
            !tellurium.logManager.isUseLog || fbLog("Context domRef is ", context.domRef);
            !tellurium.logManager.isUseLog || fbLog("Found elements for CSS " + sel, $found.get());
            if ($found.size() > 1) {
                //Use lookAHead to eliminate multiple matches
                $found = alg.lookAhead(this, $found);
                !tellurium.logManager.isUseLog || fbLog("Look Ahead result for " + this.uid, $found.get());
            }

            return $found.get();
        }


        return null;
    },

    getRepeatNum: function(context){
        !tellurium.logManager.isUseLog || fbLog("Calling getRepeatNum for " + this.uid, this);
        var lst = this.locateSelf(context);
        if(lst == null)
            return 0;

        return lst.length;
    },

    walkTo: function(context, uiid){
        if(uiid.size() < 1)
            return this;

        var index = uiid.pop().replace(/^_/, '');

        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid + "[" + index + "]", this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelectorWithPosition(this.locator, index);
                    var $found = teJQuery(context.domRef);
                    if(sel != null && sel.trim().length > 0){
                        $found = $found.find(sel);
                    }

                    if ($found.size() > 1) {
                        //Use lookAHead to eliminate multiple matches
                        $found = alg.lookAhead(this, $found);
                        !tellurium.logManager.isUseLog || fbLog("Look Ahead result for " + this.uid, $found.get());
                    }

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0){
                            context.domRef = null;
                            fbError("Cannot find UI element " + this.uid, this);
                            return null;
                        }
                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                            return null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }

        if(uiid.size() < 1)
            return this;

        var cid = uiid.pop();
        var child = this.components.get(cid);
        if(child != null){
            !tellurium.logManager.isUseLog || fbLog("Walk to child " + cid, child);
            return child.walkTo(context, uiid);
        }else{
            fbError("Cannot find child " + cid, child);
            context.domRef = null;
            return null;
        }
    }
});

var UiDiv = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'div';
        this.tag = "div";
    }
});

var UiForm = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Form';
        this.tag = "form";
    }
});

var UiFrame = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Frame';
        this.id = null;
        this.name = null;
        this.title = null;
    }
});

var UiList = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'List';
        this.noCacheForChildren = true;
        this.separator = null;
        this.defaultUi = new UiTextBox();
        this.rTree= new RTree();
        this.rTree.indices = this.components;
        this.rTree.preBuild();
    },

    goToPlace:  function(uiid, uiobj) {
        if(uiid.size() == 1){
            uiid.pop();
            objectCopy(this, uiobj);
        }else{
            uiid.pop();
            var cuid = uiid.peek();

            if(uiid.size() == 1){
                uiid.pop();
                uiobj.parent = this;
                this.components.put(cuid, uiobj);     
                this.rTree.insert(uiobj);
            }else{
                var child = this.components.get(cuid);
                child.goToPlace(uiid, uiobj);
            }
        }
    },

    findChild: function(id){
        return this.rTree.route(id);
    },

    buildSelectorWithoutPosition: function(locator){
        return tellurium.jqbuilder.buildCssSelector(locator.tag, locator.text, null, locator.direct, locator.attributes);
    },

    getAnySelectorWithSeparator: function(obj){
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > " + this.separator + "has[" + sel + "]";
    },

    getAnySelectorWithoutSeparator: function(obj){

        return this.buildSelectorWithoutPosition(obj.locator);
    },

    getFirstSelectorWithSeparator: function(){

        return " > " + this.separator + ":first";
    },

    getFirstSelectorWithoutSeparator: function(obj){
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > " + sel + ":first";
    },

    getLastSelectorWithSeparator: function(){

        return " > " + this.separator + ":last";
    },

    getLastSelectorWithoutSeparator: function(obj){
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > " + sel + ":last";
    },

    getSelectorByIndexWithSeparator: function(index){
        var inx = parseInt(index) - 1;
        return " > " + this.separator + ":eq(" + inx + ")";
    },

    getSelectorByIndexWithoutSeparator: function(index){
        var locs = new Hashtable();
        var last = null;
        var inx = parseInt(index);
        for (var i = 1; i <= inx; i++) {
            var obj = this.findChild(inx);
            var pl = this.buildSelectorWithoutPosition(obj.locator);
            var occur = locs.get(pl);
            if (occur == null) {
                locs.put(pl, 1);
            } else {
                locs.put(pl, occur + 1);
            }
            if (i == inx) {
                last = pl;
            }
        }

        var lastOccur = locs.get(pl)-1;

        //force to be direct child (if consider List trailer)
        return " > " + last + ":eq(" + lastOccur + ")";
    },

    getListSelector: function(index, obj) {
        var key = index + "";
        if(this.rTree.isId(key)){
            key = obj.metaData.index.value + "";
        }

        if (this.separator != null && this.separator.trim().length > 0) {
            if (key == "any") {
                return this.getAnySelectorWithSeparator(obj);
            } else if (key == "first") {
                return this.getFirstSelectorWithSeparator();
            } else if (key == "last") {
                return this.getLastSelectorWithSeparator();
            } else if (key.match(/[0-9]+/)) {
                return this.getSelectorByIndexWithSeparator(key);
            } else {
                throw new SeleniumError("Invalid ID " + key);
            }
        } else {
            if (key == "any") {
                return this.getAnySelectorWithoutSeparator(obj);
            } else if (key == "first") {
                return this.getFirstSelectorWithoutSeparator(obj);
            } else if (key == "last") {
                return this.getLastSelectorWithoutSeparator(obj);
            } else if (key.match(/[0-9]+/)) {
                return this.getSelectorByIndexWithoutSeparator(key);
            } else {
                throw new SeleniumError("Invalid ID " + key);
            }
        }
    },

    findUiObject: function(index) {

        //first check _index format
        var key = "_" + index;
        var obj = this.components.get(key);

        //then, check _ALL format
        if (obj == null) {
            key = "_ALL";
            obj = this.components.get(key);
        }
        !tellurium.logManager.isUseLog || fbLog("Found List UI object for index " + index, obj);

        return obj;
    },
    
    // Get runtime ID
    getRid: function(index){
        return  "_" + index;    
    },

    deriveListSelector: function(index) {
        var locs = new Hashtable();
        var last = null;
        var pl = null;
        for (var i = 1; i <= index; i++) {
            var obj = this.findUiObject(i);
            //XXX: double check here, if the generated css selectors are the same for two different objects,
            //error may occur, but does it make sense to have to different objects with the same css selectors?
            //seems not make sense.
            pl = obj.uid;
            var occur = locs.get(pl);
            if (occur == null) {
                locs.put(pl, 1);
            } else {
                locs.put(pl, occur + 1);
            }
            if (i == index) {
//                last = pl;
                last = tellurium.jqbuilder.buildCssSelector(obj.locator.tag, obj.locator.text, null, obj.locator.direct, obj.locator.attributes);
            }
        }

//        var lastOccur = locs.get(last)-1;
        var lastOccur = locs.get(pl)-1;

        //force to be direct child (if consider List trailer) 
        return " > " + last + ":eq(" + lastOccur + ")";
    },

    getListSize: function(context){
        //First, get the DOM reference of the List itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for List " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for List " + this.uid + " is null");
        }
        
        var $found = teJQuery(dmr);
        if(this.separator != null){
            $found = $found.find(" > " + this.separator);
        }
        var num = 0;
        if(this.components != null && this.components.size() > 0){
            var alg = context.alg;
            var keys = this.components.keySet();
            for(var i=0; i<keys.length; i++){
                var component = this.components.get(keys[i]);
                if(component.locator != null){
                    var sel = alg.buildSelector(component.locator);
                    num = num + $found.find(sel).size();
                }else{
                    //handle logical container
                    num = $found.size();
                }
            }
        }else{
            //otherwise, consider all immediate children for the List
            num = $found.children().size();
        }
        
        return num;
        
    },

    buildSData: function(context, npid, domref, key, child){
        var alg = context.alg;
        var sel = this.getListSelector(key, child);
        var $found = teJQuery(domref).find(sel);
        !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + sel + "' for " + this.uid, $found.get());
        if ($found.size() == 1) {
            !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, $found.get(0));
            var cdomref;
            if (child.self) {
                cdomref = $found.get(0);
            } else {
                cdomref = this.locateChild(context, $found.get(0), child);
            }

//            var csdata = new UiSData(npid, this.getRid(key), child, cdomref);
            //XXX: for list without separator, this may not work. BE aware, don't use "any" for list without separator
            var index;
            if(key.match(/[0-9]+/)){
                index = key;
            }else{
                index = $found.index() + 1;
            }
            var csdata = new UiSData(npid, this.getRid(index), child, cdomref);
            alg.addChildUiObject(csdata);

            return index;
        } else if ($found.size() == 0) {
            fbError("Cannot find UI element " + child.uid, child);
            throw new SeleniumError("Cannot find UI element " + child.uid);
        } else {
            fbError("Found " + $found.size() + " matches for UI element " + child.uid, $found.get());
            throw new SeleniumError("Found " + $found.size() + " matches for UI element " + child.uid);
        }
    },

    buildSNodeForList: function(context, npid, domref){
        if(domref != null && this.components.size() > 0){
            var keys = this.components.keySet();
            var i, child, index, inx;
            var included = new Array();
            for(i=0; i<keys.length; i++){
                child = this.components.get(keys[i]);
                index = child.metaData.index.value;
                if(index == "any"){
                    inx = this.buildSData(context, npid, domref, index, child);
                    included.push(inx);
                }
            }

            var max = this.getListSize(context);
            child = this.findChild("last");
            if(child != null){
                inx = this.buildSData(context, npid, domref, "last", child);
                included.push(inx);
                max = max - 1;
            }

            for(i=1; i<=max; i++){
                if (teJQuery.inArray(i, included) == -1) {
                    child = this.findChild(i);
                    this.buildSData(context, npid, domref, i.toString(), child);
                }
            }
        }
    },

    buildSNode: function(context, pid, rid, domref){
        var node = new UiCNode();
        node.objRef = this;
        node.rid = rid;
        node.pid = pid;
        node.domRef = domref;

        var npid = this.buildPid(pid, rid);
        this.buildSNodeForList(context, npid, domref);

        return node;
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef);
                    if(sel != null && sel.trim().length > 0){
                        $found = $found.find(sel);
                    }
                    if ($found.size() > 1) {
                        //Use bestEffort() to eliminate multipe matches
                        $found = alg.bestEffort(this, $found);
                    }

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0){
                            context.domRef = null;
                            fbError("Cannot find UI element " + this.uid, this);
                        }
                            
                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }
        !tellurium.logManager.isUseLog || fbLog("Processing the List itself and got the context dom Referece", context.domRef);
        //if not child listed, return itself
        if (uiid.size() < 1)
            return this;

        var child = uiid.pop();

//        var part = child.replace(/^_/, '');
//
//        var nindex = parseInt(part);

        //otherwise, try to find its child
//        var cobj = this.findUiObject(nindex);
        var key = child.replace(/^_/, '');

        var cobj = this.findChild(key);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (context.domRef != null) {
//            var selt = this.getListSelector(nindex);
            var selt = this.getListSelector(key, cobj);

            var $fnd = teJQuery(context.domRef).find(selt);
            !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + selt + "' for List " + this.uid, $fnd.get());
            if ($fnd.size() == 1) {
                context.domRef = $fnd.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($fnd.size() == 0){
                    context.domRef = null;
                    fbError("Cannot find the child UI element " + key, this);
                }

                if ($fnd.size() > 1) {
                    fbError("Found multiple matches for UI element " + key, $fnd.get());
                    context.domRef = null;
                }
            }
        }

        //If the List does not have a separator
        //tell WorkflowContext not to process the next object's locator because List has already added that
        if(this.separator == null || this.separator.trim().length == 0 || child.self){
            context.skipNext = true;
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return List child ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to List child ", cobj);
            return cobj.walkTo(context, uiid);
        }
    }
});

var UiTable = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Table';
        this.tag = "table";
        this.noCacheForChildren = true;
        this.defaultUi = new UiTextBox();
        this.headers = new Hashtable();
        this.rTree= new RTree();
        this.rTree.indices = this.headers;
        this.rTree.preBuild();
        this.rGraph = new RGraph();
        this.rGraph.indices = this.components;
        this.rGraph.preBuild();
        this.multiSet = ["all", "odd", "even"];
    },

    goToPlace:  function(uiid, uiobj) {
        if(uiid.size() == 1){
            uiid.pop();
            objectCopy(this, uiobj);
        }else{
            uiid.pop();
            var cuid = uiid.peek();
            var meta = uiobj.metaData;
            var child;
            if(uiid.size() == 1){
                uiid.pop();
                uiobj.parent = this;
                if(meta.type == "Header"){
                    this.headers.put(cuid, uiobj);
                    this.rTree.insert(uiobj);
                }else if(meta.type == "TBody"){
                    this.components.put(cuid, uiobj);
                    this.rGraph.insert(uiobj);
                }else{
                    throw new SeleniumError("Invalid meta data type " + meta.type);
                }
            }else{
                if(meta.type == "Header"){
                    var header = this.headers.get(cuid);
                    header.goToPlace(uiid, uiobj);
                }else if(meta.type == "TBody"){
                    child = this.components.get(cuid);
                    child.goToPlace(uiid, uiobj);
                }else{
                    child = this.components.get(cuid);
                    child.goToPlace(uiid, uiobj);
                }
            }
        }
    },

    locateTBodyChild: function(id) {
        return this.rGraph.route(id);
    },

    locateHeaderChild: function(id) {
        return this.rTree.route(id);
    },

    inMultiSet: function(key){
        return (teJQuery.inArray(key, this.multiSet) != -1);
    },

    getRowIndex: function($found){
        return $found.closest('tr').prevAll().has('td').size();
    },

    getRowColumn: function(key){
        var parts = key.replace(/^_/, '').split("_");
        !tellurium.logManager.isUseLog || fbLog("Parts for Key " + key, parts);
        var m=0;
        var rc = new Array();
        if(parts.length == 3){
            m++;
        }
        for(var i=m; i<parts.length; i++){
            rc.push(parts[i]);
        }

        return rc;
    },

    buildIndex: function(key, $found){
        var rc = this.getRowColumn(key);
        !tellurium.logManager.isUseLog || fbLog("RC for getRowColumn", rc);
        var r, c;

        if(typeof rc[0] === "number"){
           r = rc[0];
        }else if(rc[0].match(/[0-9]+/)){
            r = rc[0];
        }else{
            r = this.getRowIndex($found) + 1;
        }
        
        if(typeof rc[1] === "number"){
            c = rc[1];
        }else if(rc[1].match(/[0-9]+/)){
            c = rc[1];
        }else{
            c = $found.index() + 1;
        }

        return "_1_" + r + "_" + c;
    },
    
    buildSelectorWithoutPosition: function(locator){
        return tellurium.jqbuilder.buildCssSelector(locator.tag, locator.text, null, locator.direct, locator.attributes);
    },

    getHeaderSelector: function(index, obj) {
        var key = index;
        if(this.rGraph.isRef(index)){
            key = obj.metaData.index.value;
        }
        if ("any" == key) {
            return this.getAnyHeaderSelector(obj);
        } else if ("first" == key) {
            return this.getFirstHeaderSelector();
        } else if ("last" == key) {
            return this.getLastHeaderSelector();
        } else if (key.match(/[0-9]+/)) {
            return this.getIndexedHeaderSelector(parseInt(key));
        } else {
            throw new SeleniumError("Invalid Index " + key);
        }
    },

    getAnyHeaderSelector: function(obj) {
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return "> tbody > tr:has(th) > th:has(" + sel + ")";
    },

    getFirstHeaderSelector: function() {

        return " > tbody > tr:has(th) > th:first";
    },

    getLastHeaderSelector: function() {

        return " > tbody > tr:has(th) > th:last";
    },

    getIndexedHeaderSelector: function(row) {
        return " > tbody > tr:has(th) > th:eq(" + (row - 1) + ")";
    },

    getHeaderIndex: function(context, obj){
        //First, get the DOM reference of the Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var sel = this.getHeaderSelector(obj.metaData.index.value, obj);
        var $found = teJQuery(dmr).find(sel);

        return $found.index() + 1;
    },

    findHeaderIndex: function(context, key) {
        var obj = this.headers.get(key);
        if (obj != null) {
            if ("any" == obj.metaData.index.value) {
                var inx = this.getHeaderIndex(context, obj);
                return new Index(inx);
            }

            return obj.metaData.index;
        }

        return null;
    },

    preprocess: function(context, inx, meta) {
        var ri = new RIndex();
        var t = meta.tbody;
        var tRef, rRef, cRef;
        if (t.type == "REF") {
            tRef = this.findHeaderIndex(context, t.value);
            if (tRef == null)
                throw new SeleniumError("Invalid Index reference " + t.value);
            ri.x = tRef.value;
        } else if ("all" == t.value && this.rGraph.isRef(inx[0])) {
            tRef = this.findHeaderIndex(context, inx[0]);
            if (tRef == null)
                throw new SeleniumError("Invalid Index reference " + inx[0]);
            ri.x = tRef.value;
        } else {
            ri.x = t.value;
        }

        var r = meta.row;
        if (r.type == "REF") {
            rRef = this.findHeaderIndex(context, r.value);
            if (rRef == null)
                throw new SeleniumError("Invalid Index reference " + r.value);
            ri.y = rRef.value;
        }else if("all" == r.value && this.rGraph.isRef(inx[1])){
            rRef = this.findHeaderIndex(context, inx[1]);
            if(rRef == null)
            throw new SeleniumError("Invalid Index reference " + inx[0]);
            ri.y = rRef.value;
        } else {
            ri.y = r.value;
        }

        var c = meta.column;
        if (c.type == "REF") {
            cRef = this.findHeaderIndex(context, c.value);
            if (cRef == null)
                throw new SeleniumError("Invalid Index reference " + c.value);
            ri.z = cRef.value;
        }else if("all" == c.value && this.rGraph.isRef(inx[2])){
            cRef = this.findHeaderIndex(context, inx[2]);
            if(cRef == null)
            throw new SeleniumError("Invalid Index reference " + inx[2]);
            ri.z = cRef.value;
        } else {
            ri.z = c.value;
        }

        return ri;
    },

    getCellSelector: function(context, key, obj) {
        var meta = obj.metaData;
        var parts = key.replace(/^_/, '').split("_");
        var inx = new Array();

        if(parts.length == 1){
            //the key must be a real key
            inx.push(meta.tbody.value);
            inx.push(meta.row.value);
            inx.push(meta.column.value);
        }else{
            if (parts.length < 3) {
                inx.push("1");
            }
            for (var i = 0; i < parts.length; i++) {
                inx.push(parts[i]);
            }
        }
        var ri = this.preprocess(context, inx, meta);

        return this.getTBodySelector() + this.getRowSelector(ri, inx[1], obj) + this.getColumnSelector(ri, inx[2], obj);
    },

    getTBodySelector: function() {
        return " > tbody ";
    },

    getRowSelector: function(ri, key, obj) {
        var index = ri.y + "";
        if ("any" == index) {
            return this.getAnyRowSelector(obj);
        } else if ("first" == index) {
            return this.getFirstRowSelector();
        } else if ("last" == index) {
            return this.getLastRowSelector();
        } else if (key.match(/[0-9]+/)) {
            return this.getIndexedRowSelector(parseInt(key));
        } else if (index.match(/[0-9]+/)) {
            return this.getIndexedRowSelector(parseInt(index));
        } else {
            throw new SeleniumError("Invalid ID " + key);
        }
    },

    getAnyRowSelector: function(obj) {
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > tr:has(td):has(" + sel + ")";
    },

    getFirstRowSelector: function() {

        return " > tr:has(td):first";
    },

    getLastRowSelector: function() {

        return " > tr:has(td):last";
    },

    getIndexedRowSelector: function(row) {
        return " > tr:has(td):eq(" + (row - 1) + ")";
    },

    getColumnSelector: function(ri, key, obj) {
        var index = ri.z + "";
        !tellurium.logManager.isUseLog || fbLog("index " + index, ri);
        if ("any" == index) {
            return this.getAnyColumnSelector(obj);
        } else if ("first" == index) {
            return this.getFirstColumnSelector();
        } else if ("last" == index) {
            return this.getLastColumnSelector();
        } else if (key.match(/[0-9]+/)) {
            return this.getIndexedColumnSelector(parseInt(key));
        } else if (index.match(/[0-9]+/)) {
            return this.getIndexedColumnSelector(parseInt(index));
        } else {
            throw new SeleniumError("Invalid Index " + index);
        }
    },

    getAnyColumnSelector: function(obj) {
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > td:has(" + sel + ")";
    },

    getFirstColumnSelector: function() {

        return " > td:first";
    },

    getLastColumnSelector: function() {

        return " > td:last";
    },

    getIndexedColumnSelector: function(column) {
        return " > td:eq(" + (column - 1) + ")";
    },

    prelocate: function(){
        if(this.amICacheable()){
            this.snapshot();
            var keys = this.components.keySet();
            var child = null;
            var i=0;
            for(i=0; i<keys.length; i++){
                child = this.components.get(keys[i]);
                child.prelocate();
            }

            keys = this.headers.keySet();
            for(i=0; i<keys.length; i++){
                child = this.headers.get(keys[i]);
                child.prelocate();
            }
        }
    },

    locate:  function(uialg){
        uialg.locateInAllSnapshots(this);
        
        if (!this.noCacheForChildren) {
            //need to push all its children into the object queue
            !tellurium.logManager.isUseLog || fbLog("Children for Table " + this.uid + ": ", this.components.showMe());
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Children val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if ((!component.lazy)) {
                    !tellurium.logManager.isUseLog || fbLog("Add child of Table " + this.uid + " to UiAlg : ", component);
                    uialg.addChildUiObject(component);
                }
            }

            !tellurium.logManager.isUseLog || fbLog("Headers for Container " + this.uid + ": ", this.headers.showMe());
            valset = this.headers.valSet();
            !tellurium.logManager.isUseLog || fbLog("Headers val set: ", valset);
            for (var j = 0; j < valset.length; j++) {
                var header = valset[j];
                !tellurium.logManager.isUseLog || fbLog("header: ", header);
                if (!header.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Add header of Table " + this.uid + " to UiAlg : ", header);
                    uialg.addChildUiObject(header);
                }
            }
        }
    },

    lookChildren: function(){
        var validChildren = new Array();

        if (!this.noCacheForChildren) {
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if (!component.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable child of Table " + this.uid + ": ", component);
                    if(component.locator != null && (!component.self)){
                        validChildren.push(component);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                        var ccr = component.lookChildren();
                        if(ccr != null && ccr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                            validChildren = validChildren.concat(ccr);
                        }
                    }
                }
            }

            valset = this.headers.valSet();
            for (var j = 0; j < valset.length; j++) {
                var header = valset[j];
                !tellurium.logManager.isUseLog || fbLog("header: ", header);
                if (!header.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable header of Table " + this.uid + ": ", header);
                    if(header.locator != null && (!header.self)){
                        validChildren.push(header);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + header.uid + " and look for this children", header);
                        var chr = header.lookChildren();
                        if(chr != null && chr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + header.uid + "'s children. ", chr);
                            validChildren = validChildren.concat(chr);
                        }
                    }
                }
            }
        }
        
        return validChildren;
    },

    lookChildrenNoMatterWhat: function() {
        var children = new Array();

        var valset = this.components.valSet();
        !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
        for (var i = 0; i < valset.length; i++) {
            var component = valset[i];
            !tellurium.logManager.isUseLog || fbLog("Look ahead nomatter what at cachable child of Table " + this.uid + ": ", component);
            if (component.locator != null && (!component.self)) {
                children.push(component);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                var ccr = component.lookChildrenNoMatterWhat();
                if (ccr != null && ccr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                    children = children.concat(ccr);
                }
            }
        }
        
        valset = this.headers.valSet();
        for (var j = 0; j < valset.length; j++) {
            var header = valset[j];
            !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable header of Table " + this.uid + ": ", header);
            if (header.locator != null && (!header.self)) {
                children.push(header);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + header.uid + " and look for this children", header);
                var chr = header.lookChildrenNoMatterWhat();
                if (chr != null && chr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + header.uid + "'s children. ", chr);
                    children = children.concat(chr);
                }
            }
        }

        return children;
    },

    // Get runtime ID
    getHeaderRid: function(index){
        return  "_HEADER_" + index;
    },

    // Get runtime ID
    getBodyRid: function(body, row, column){
        return   "_" + body + "_" + row + "_" + column;
    },

    findHeaderUiObject: function(index){
        var key = "_HEADER_" + index;
        var obj = this.headers.get(key);

        if(obj == null){
            key = "_HEADER_ALL";
            obj = this.headers.get(key);
        }

        return obj;
    },

    findBodyKeyTemplatePair: function(row, column){
        var key = "_" + row + "_" + column;
        var obj = this.components.get(key);

        if(obj == null){
            key = "_ALL_" + column;
            obj = this.components.get(key);
        }

        if(obj == null){
            key = "_" + row + "_ALL";
            obj = this.components.get(key);
        }

        if(obj == null){
            key = "_ALL_ALL";
            obj = this.components.get(key);
        }

        var result = null;
        if(obj != null){
            result = new KeyValuePair();
            result.key = key;
            result.val = obj;
        }

        return result;
    },

    findUiObject: function(row, column){
        var key = "_" + row + "_" + column;
        var obj = this.components.get(key);

        if(obj == null){
            key = "_ALL_" + column;
            obj = this.components.get(key);
        }

        if(obj == null){
            key = "_" + row + "_ALL";
            obj = this.components.get(key);
        }

        if(obj == null){
            key = "_ALL_ALL";
            obj = this.components.get(key);
        }

        return obj;
    },

    getAllBodyCell: function(context, worker){
        if (context.domRef != null) {
            var $found = teJQuery(context.domRef).find("> tbody > tr > td");

            if ($found.size() >0) {
                return worker.work(context, $found.get());
            }
        }

        return null;
    },

    getHeaderColumnNum: function(context){
        //First, get the DOM reference of the Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var $found = teJQuery(dmr).find("> tbody > tr:has(th):eq(0) > th");

        return $found.size();
    },

    getTableRowNum: function(context){
         //First, get the DOM reference of the Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var $found = teJQuery(dmr).find(" > tbody > tr:has(td)");

        return $found.size();
    },

    getTableColumnNum: function(context){
          //First, get the DOM reference of the Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var $found = teJQuery(dmr).find("> tbody > tr:has(td):eq(0) > td");

        return $found.size();       
    },

    buildHeaderSData: function(context, npid, domref, key, child){
        var alg = context.alg;
        var sel = this.getHeaderSelector(key, child);
        var $found = teJQuery(domref).find(sel);
        !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + sel + "' for " + this.uid, $found.get());
        if ($found.size() == 1) {
            !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, $found.get(0));
            var cdomref;
            if (child.self) {
                cdomref = $found.get(0);
            } else {
                cdomref = this.locateChild(context, $found.get(0), child);
            }
            
            var index;
            if(key.match(/[0-9]+/)){
                index = key;
            }else{
                index = $found.index() + 1;
            }
            var csdata = new UiSData(npid, this.getHeaderRid(index), child, cdomref);
            alg.addChildUiObject(csdata);

            return index;
        } else if ($found.size() == 0) {
            fbError("Cannot find UI element " + child.uid, child);
            throw new SeleniumError("Cannot find UI element " + child.uid);
        } else {
            fbError("Found " + $found.size() + " matches for UI element " + child.uid, $found.get());
            throw new SeleniumError("Found " + $found.size() + " matches for UI element " + child.uid);
        }
    },
    
    buildSNodeForHeader: function(context, npid, domref){
        if(domref != null && this.headers.size() > 0){
            var keys = this.headers.keySet();
            var i, child, inx, index;
            var included = new Array();
            for(i=0; i<keys.length; i++){
                child = this.headers.get(keys[i]);
                index = child.metaData.index.value;
                if(index == "any"){
                    inx = this.buildHeaderSData(context, npid, domref, index, child);
                    included.push(inx);
                }
            }

            var max = this.getHeaderColumnNum(context);
            child = this.locateHeaderChild("last");
            if(child != null){
                inx = this.buildHeaderSData(context, npid, domref, "last", child);
                included.push(inx);
                max = max - 1;
            }

            for(i=1; i<=max; i++){
                if (teJQuery.inArray(i, included) == -1) {
                    child = this.locateHeaderChild(i);
                    this.buildHeaderSData(context, npid, domref, i.toString(), child);
                }
            }
        }
    },

    buildBodySData: function(context, npid, domref, key, child){
        var alg = context.alg;
        var sel = this.getCellSelector(context, key, child);
        var $found = teJQuery(domref).find(sel);
//        !tellurium.logManager.isUseLog || fbLog("domref", domref);
        !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + sel + "' for " + this.uid, $found.get());
        if ($found.size() == 1) {
            !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, $found.get(0));
            var cdomref;
            if (child.self) {
                cdomref = $found.get(0);
            } else {
                cdomref = this.locateChild(context, $found.get(0), child);
            }

            var index = this.buildIndex(key, $found);
            var csdata = new UiSData(npid, index, child, cdomref);
            alg.addChildUiObject(csdata);
            
            return index;
        } else if ($found.size() == 0) {
            fbError("Cannot find UI element " + child.uid, child);
            throw new SeleniumError("Cannot find UI element " + child.uid);
        } else {
            fbError("Found " + $found.size() + " matches for UI element " + child.uid, $found.get());
            throw new SeleniumError("Found " + $found.size() + " matches for UI element " + child.uid);
        }
    },

    buildSNodeForBody: function(context, npid, domref){
       if(domref != null && this.components.size() > 0){
            var i, j, key, child;
            var included = new Array();
            var keySet = this.components.keySet();
            for(i=0; i<keySet.length; i++){
                key = keySet[i];
                child = this.components.get(key);
                var meta = child.metaData;
                !tellurium.logManager.isUseLog || fbLog("Meta Data for key " + key, meta);
                if(!(this.inMultiSet(meta.row.value) || this.inMultiSet(meta.column.value))){
                    key = "_1_" + meta.row.value + "_" + meta.column.value;
                    var index = this.buildBodySData(context, npid, domref, key, child);
                    included.push(index);
                }
            }
            var rownum = this.getTableRowNum(context);
            var colnum = this.getTableColumnNum(context);
            for(i=1; i<=rownum; i++){
                for(j=1; j<=colnum; j++){
                    key = "_1_" + i + "_" + j;
//                    !tellurium.logManager.isUseLog || fbLog("Table rownum " + rownum + " colnum " + colnum + ", key " + key, this);
                    if (teJQuery.inArray(key, included) == -1) {
//                        key = this.getBodyRid(1, i, j);
                        child = this.locateTBodyChild(key);
                        this.buildBodySData(context, npid, domref, key, child);
                    }
                }
            }
       }
    },

    buildSNode: function(context, pid, rid, domref){
        var node = new UiTNode();
        node.objRef = this;
        node.rid = rid;
        node.pid = pid;
        node.domRef = domref;

        var npid = this.buildPid(pid, rid);

        this.buildSNodeForHeader(context, npid, domref);
        this.buildSNodeForBody(context, npid, domref);

        return node;
    },

    walkToHeader: function(context, uiid) {
        //pop up the "header" indicator
        uiid.pop();
        //reach the actual uiid for the header element
        var child = uiid.pop();

        var key = child.replace(/^_/, '');

        var cobj = this.locateHeaderChild(key);

//        child = child.replace(/^_/, '').replace(/HEADER/, '');

//        var index = parseInt(trimString(child));

        //try to find its child
//        var cobj = this.findHeaderUiObject(index);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (context.domRef != null) {
            var sel = this.getHeaderSelector(key, cobj);

            var $found = teJQuery(context.domRef);
            if(sel != null && sel.trim().length > 0){
                $found = $found.find(sel);
            }
            !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + sel +"' for Table " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0){
                    context.domRef = null;
                    fbError("Cannot find the child UI element " + key, this);
                }

                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element " + key, $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
            if(cobj.self){
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return Table head ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table head ", cobj);
            return cobj.walkTo(context, uiid);
        }
    },

    walkToElement: function(context, uiid) {
        var child = uiid.pop();
//        var parts = child.replace(/^_/, '').split("_");
//
//        var nrow = parseInt(parts[0]);
//        var ncolumn = parseInt(parts[1]);
//
//        //otherwise, try to find its child
//        var cobj = this.findUiObject(nrow, ncolumn);
        var cobj = this.locateTBodyChild(child);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (context.domRef != null) {
            var sel = this.getCellSelector(context, child, cobj);

            var $found = teJQuery(context.domRef);
            if(sel != null && sel.trim().length > 0){
                $found = $found.find(sel);
            }
            !tellurium.logManager.isUseLog || fbLog("Found child with CSS selector '" + sel +"' for Table " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0){
                    context.domRef = null;
                    fbError("Cannot find the child UI element ", this);
                }

                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element ", $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
            if(cobj.self){
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return Table child ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table child ", cobj);
            return cobj.walkTo(context, uiid);
        }
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        if(this.headers != null && this.headers.length > 0){
            var hkeys = this.headers.keySet();
            for(var i=0; i<this.headers.length; i++){
                var header = this.headers.get(hkeys[i]);
                header.traverse(context, visitor);
            }
        }

        if(this.components != null && this.components.length > 0){
            var keys = this.components.keySet();
            for(var j=0; j<keys.length; j++){
                var child = this.components.get(keys[j]);
                child.traverse(context, visitor);
            }
        }
    },

    walkTo: function(context, uiid){
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef);
                    if(sel != null && sel.trim().length > 0){
                        $found = $found.find(sel);
                    }
                    if ($found.size() > 1) {
                        //Use bestGuess() to eliminate multipe matches
//                       $found = alg.lookAhead(this, $found);
                        $found = alg.bestEffort(this, $found);
                    }

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0){
                            context.domRef = null;
                            fbError("Cannot find UI element " + this.uid, this);
                        }

                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }
        !tellurium.logManager.isUseLog || fbLog("Processing the List itself and got the context dom Referece", context.domRef);

        if (uiid.size() < 1)
            return this;

        var child = uiid.peek();

        if(child == "header" || child.startsWith("_HEADER")){
            return this.walkToHeader(context, uiid);
        } else {
            return this.walkToElement(context, uiid);
        }
    }
});

var UiStandardTable = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'StandardTable';
        this.tag = "table";
        this.noCacheForChildren = true;
        this.defaultUi = new UiTextBox();
        this.headers = new Hashtable();
        this.footers = new Hashtable();
        this.hTree= new RTree();
        this.hTree.indices = this.headers;
        this.hTree.preBuild();
        this.fTree= new RTree();
        this.fTree.indices = this.footers;
        this.fTree.preBuild();
        this.rGraph = new RGraph();
        this.rGraph.indices = this.components;
        this.rGraph.preBuild();

        //table header
        this.ht = "thead";
        this.hrt = "tr";
        this.hct = "th";

        //table body
        this.bt = "tbody";
        this.brt = "tr";
        this.bct = "td";

        //table footer
        this.ft = "tfoot";
        this.frt = "tr";
        this.fct = "td";
        
        this.multiSet = ["all", "odd", "even"];
    },

    goToPlace:  function(uiid, uiobj) {
         if(uiid.size() == 1){
            uiid.pop();
            objectCopy(this, uiobj);
        }else{
            uiid.pop();
            var cuid = uiid.peek();
            var meta = uiobj.metaData;
            var child;

            if(uiid.size() == 1){
                uiid.pop();
                uiobj.parent = this;
                if(meta.type == "Header"){
                    this.headers.put(cuid, uiobj);
                    this.hTree.insert(uiobj);
                }else if(meta.type == "Footer"){
                    this.footers.put(cuid, uiobj);
                    this.fTree.insert(uiobj);
                }else if(meta.type == "TBody"){
                    this.components.put(cuid, uiobj);
                    this.rGraph.insert(uiobj);
                }else{
                    throw new SeleniumError("Invalid meta data type " + meta.type);
                }
            }else{
                if(meta.type == "Header"){
                    var header = this.headers.get(cuid);
                    header.goToPlace(uiid, uiobj);
                }else if(meta.type == "Footer"){
                    var footer = this.footers.get(cuid);
                    footer.goToPlace(uiid, uiobj);
                }else if(meta.type == "TBody"){
                    child = this.components.get(cuid);
                    child.goToPlace(uiid, uiobj);
                }else{
                    child = this.components.get(cuid);
                    child.goToPlace(uiid, uiobj);
                }
            }
        }
    },

    locateTBodyChild: function(id) {
        return this.rGraph.route(id);
    },

    locateHeaderChild: function(id) {
        return this.hTree.route(id);
    },

    locateFooterChild: function(id) {
        return this.fTree.route(id);
    },

    inMultiSet: function(key){
        return (teJQuery.inArray(key, this.multiSet) != -1);
    },

    getColumnIndex: function($found){
        return $found.index();
    },

    getRowIndex: function($found){
        return $found.closest(this.brt).prevAll().has(this.bct).size();
    },

    getBodyIndex: function($found){
        var num = $found.closest(this.bt).prevAll().size();
        if(this.headers.size() > 0 && this.bt == this.ht){
            num--;
        }

        return num;
    },

    buildIndex: function(key, $found){
        var parts = key.replace(/^_/, '').split("_");
        var rc = new Array();
        if(parts.length == 2){
            rc.push("1");
        }
        for(var i=0; i<parts.length; i++){
            rc.push(parts[i]);
        }
        var b, r, c;

        if(typeof rc[0] === "number"){
            b = rc[0];
        }else if(rc[0].match(/[0-9]+/)){
            b = rc[0];
        }else{
            b = this.getBodyIndex($found) + 1;
        }

        if(typeof rc[1] === "number"){
            r = rc[1];
        }else if(rc[1].match(/[0-9]+/)){
            r = rc[1];
        }else{
            r = this.getRowIndex($found) + 1;
        }

        if(typeof rc[2] === "number"){
            c = rc[2];
        }else if(rc[2].match(/[0-9]+/)){
            c = rc[2];
        }else{
            c = $found.index() + 1;
        }

        return "_" + b + "_" + r + "_" + c;
    },

    buildSelectorWithoutPosition: function(locator){
        return tellurium.jqbuilder.buildCssSelector(locator.tag, locator.text, null, locator.direct, locator.attributes);
    },

    getHeaderSelector: function(index, obj) {
        var key = index;
        if(this.rGraph.isRef(index)){
            key = obj.metaData.index.value;
        }
        if ("any" == key) {
            return this.getAnyHeaderSelector(obj);
        } else if ("first" == key) {
            return this.getFirstHeaderSelector();
        } else if ("last" == key) {
            return this.getLastHeaderSelector();
        } else if (key.match(/[0-9]+/)) {
            return this.getIndexedHeaderSelector(parseInt(key));
        } else {
            throw new SeleniumError("Invalid Index " + key);
        }
    },

    getAnyHeaderSelector: function(obj) {
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > " + this.ht + ":first > " + this.hrt + " > " + this.hct + ":has(" + sel + ")";
    },

    getFirstHeaderSelector: function() {

        return " > " + this.ht + ":first > " + this.hrt + " > " + this.hct + ":first";
    },

    getLastHeaderSelector: function() {

        return " > " + this.ht + ":first > " + this.hrt + " > " + this.hct + ":last";
    },

    getIndexedHeaderSelector: function(column) {
        return " > " + this.ht + ":first > " + this.hrt + " > " + this.hct + ":eq(" + (column-1) +")";
    },

    getHeaderIndex: function(context, obj){
        //First, get the DOM reference of the Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var sel = this.getHeaderSelector(obj.metaData.index.value, obj);
        var $found = teJQuery(dmr).find(sel);

        return $found.index() + 1;
    },

    findHeaderIndex: function(context, key) {
        var obj = this.headers.get(key);
        if (obj != null) {
            if ("any" == obj.metaData.index.value) {
                var inx = this.getHeaderIndex(context, obj);
                return new Index(inx);
            }

            return obj.metaData.index;
        }

        return null;
    },

    getFooterSelector: function(index, obj) {
        var key = index;
        if(this.rGraph.isRef(index)){
            key = obj.metaData.index.value;
        }
        if ("any" == key) {
            return this.getAnyFooterSelector(obj);
        } else if ("first" == key) {
            return this.getFirstFooterSelector();
        } else if ("last" == key) {
            return this.getLastFooterSelector();
        } else if (key.match(/[0-9]+/)) {
            return this.getIndexedFooterSelector(parseInt(key));
        } else {
            throw new SeleniumError("Invalid Index " + key);
        }
    },

    getAnyFooterSelector: function(obj) {
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > " + this.ft + ":last > " + this.frt + " > " + this.fct + ":has(" + sel + ")";
    },

    getFirstFooterSelector: function() {

        return " > " + this.ft + ":last > " + this.frt + " > " + this.fct + ":first";
    },

    getLastFooterSelector: function() {

        return " > " + this.ft + ":last > " + this.frt + " > " + this.fct + ":last";
    },

    getIndexedFooterSelector: function(column) {
        return " > " + this.ft + ":last > " + this.frt + " > " + this.fct + ":eq(" + (column-1) +")";
    },

    getFooterIndex: function(context, obj){
        //First, get the DOM reference of the Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var sel = this.getFooterSelector(obj.metaData.index.value, obj);
        var $found = teJQuery(dmr).find(sel);

        return $found.index();
    },

    findFooterIndex: function(context, key) {
        var obj = this.footers.get(key);
        if (obj != null) {
            if ("any" == obj.metaData.index.value) {
                var inx = this.getFooterIndex(context, obj);
                return new Index(inx);
            }

            return obj.metaData.index;
        }

        return null;
    },

    preprocess: function(context, inx, meta) {
        var ri = new RIndex();
        var t = meta.tbody;
        if (t.type == "REF") {
            var tRef = this.findHeaderIndex(context, t.value);
            if(tRef == null)
                tRef = this.findFooterIndex(context, t.value);
            if (tRef == null)
                throw new SeleniumError("Invalid Index reference " + t.value);
            ri.x = tRef.value;
        } else if ("all" == t.value && this.rGraph.isRef(inx[0])) {
            tRef = this.findHeaderIndex(context, inx[0]);
            if (tRef == null)
                throw new SeleniumError("Invalid Index reference " + inx[0]);
            ri.x = tRef.value;
        } else {
            ri.x = t.value;
        }

        var r = meta.row;
        if (r.type == "REF") {
            var rRef = this.findHeaderIndex(context, r.value);
            if(rRef == null)
                rRef = this.findFooterIndex(context, r.value);
            if (rRef == null)
                throw new SeleniumError("Invalid Index reference " + r.value);
            ri.y = rRef.value;
        }else if("all" == r.value && this.rGraph.isRef(inx[1])){
            rRef = this.findHeaderIndex(context, inx[1]);
            if(rRef == null)
            throw new SeleniumError("Invalid Index reference " + inx[0]);
            ri.y = rRef.value;
        } else {
            ri.y = r.value;
        }

        var c = meta.column;
        if (c.type == "REF") {
            var cRef = this.findHeaderIndex(context, c.value);
            if(cRef == null)
                cRef = this.findFooterIndex(context, c.value);
            if (cRef == null)
                throw new SeleniumError("Invalid Index reference " + c.value);
            ri.z = cRef.value;
        }else if("all" == c.value && this.rGraph.isRef(inx[2])){
            cRef = this.findHeaderIndex(context, inx[2]);
            if(cRef == null)
            throw new SeleniumError("Invalid Index reference " + inx[2]);
            ri.z = cRef.value;
        } else {
            ri.z = c.value;
        }

        return ri;
    },

    getCellSelector: function(context, key, obj) {
        var meta = obj.metaData;
        var parts = key.replace(/^_/, '').split("_");
        var inx = new Array();
        if(parts.length == 1){
            //the key must be a real key
            inx.push(meta.tbody.value);
            inx.push(meta.row.value);
            inx.push(meta.column.value);
        }else{
            if (parts.length < 3) {
                inx.push("1");
            }
            for (var i = 0; i < parts.length; i++) {
                inx.push(parts[i]);
            }  
        }
        var ri = this.preprocess(context, inx, meta);

        !tellurium.logManager.isUseLog || fbLog("Get Cell selector inx for key " + key, inx);
        
        return this.getTBodySelector(ri, inx[0], obj) + this.getRowSelector(ri, inx[1], obj) + this.getColumnSelector(ri, inx[2], obj);
    },

    getTBodySelector: function(ri, key, obj) {
        var index = ri.x + "";
        if ("any" == index) {
            return this.getAnyBodySelector(obj);
        } else if ("first" == index) {
            return this.getFirstBodySelector();
        } else if ("last" == index) {
            return this.getLastBodySelector();
        } else if (key.match(/[0-9]+/)) {
            return this.getIndexedBodySelector(parseInt(key));
        } else if (index.match(/[0-9]+/)) {
            return this.getIndexedBodySelector(parseInt(index));
        } else {
            throw new SeleniumError("Invalid ID " + key);
        }
    },

    getAnyBodySelector: function(obj) {
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > " + this.bt + ":has(" + sel + ")";
    },

    getFirstBodySelector: function() {
        var inx = 1;
        if(this.headers.size() > 0 && this.bt == this.ht){
            inx++;
        }

        return " > " + this.bt + ":eq(" + (inx-1) + ")";
    },

    getLastBodySelector: function() {
        if(this.footers.size() > 0 && this.bt == this.ft){
            return " > " + this.bt + ":nextToLast";
        }

        return " > " + this.bt + ":last";
    },

    getIndexedBodySelector: function(index) {
        var inx = index;
        if(this.headers.size() > 0 && this.bt == this.ht){
            inx++;
        }

        return " > " + this.bt + ":eq(" + (inx-1) + ")";
    },

    getRowSelector: function(ri, key, obj) {
        var index = ri.y + "";
        if ("any" == index) {
            return this.getAnyRowSelector(obj);
        } else if ("first" == index) {
            return this.getFirstRowSelector();
        } else if ("last" == index) {
            return this.getLastRowSelector();
        } else if (key.match(/[0-9]+/)) {
            return this.getIndexedRowSelector(parseInt(key));
        } else if (index.match(/[0-9]+/)) {
            return this.getIndexedRowSelector(parseInt(index));
        } else {
            throw new SeleniumError("Invalid ID " + key);
        }
    },

    getAnyRowSelector: function(obj) {
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > " + this.brt + ":has(" + sel + ")";
    },

    getFirstRowSelector: function() {

        return " > " + this.brt + ":first";
    },

    getLastRowSelector: function() {

        return " > " + this.brt + ":last";
    },

    getIndexedRowSelector: function(row) {
        return " > " + this.brt + ":eq(" + (row - 1) + ")";
    },

    getColumnSelector: function(ri, key, obj) {
        var index = ri.z + "";
        if ("any" == index) {
            return this.getAnyColumnSelector(obj);
        } else if ("first" == index) {
            return this.getFirstColumnSelector();
        } else if ("last" == index) {
            return this.getLastColumnSelector();
        } else if (key.match(/[0-9]+/)) {
            return this.getIndexedColumnSelector(parseInt(key));
        } else if (index.match(/[0-9]+/)) {
            return this.getIndexedColumnSelector(parseInt(index));
        } else {
            throw new SeleniumError("Invalid Index " + index);
        }
    },

    getAnyColumnSelector: function(obj) {
        var sel = this.buildSelectorWithoutPosition(obj.locator);

        return " > " + this.bct + ":has(" + sel + ")";
    },

    getFirstColumnSelector: function() {

        return " > " + this.bct + ":first";
    },

    getLastColumnSelector: function() {

        return " > " + this.bct + ":last";
    },

    getIndexedColumnSelector: function(column) {
        return " > " + this.bct + ":eq(" + (column - 1) + ")";
    },

    prelocate: function(){
        if(this.amICacheable()){
            this.snapshot();
            var keys = this.components.keySet();
            var child = null;
            var i=0;
            for(i=0; i<keys.length; i++){
                child = this.components.get(keys[i]);
                child.prelocate();
            }

            keys = this.headers.keySet();
            for(i=0; i<keys.length; i++){
                child = this.headers.get(keys[i]);
                child.prelocate();
            }

            keys = this.footers.keySet();
            for(i=0; i<keys.length; i++){
                child = this.footers.get(keys[i]);
                child.prelocate();
            }
        }
    },

    locate:  function(uialg){
        uialg.locateInAllSnapshots(this);
        if (!this.noCacheForChildren) {
            //need to push all its children into the object queue
            !tellurium.logManager.isUseLog || fbLog("Children for StandardTable " + this.uid + ": ", this.components.showMe());
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Children val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if ((!component.lazy)) {
                    !tellurium.logManager.isUseLog || fbLog("Add child of StandardTable " + this.uid + " to UiAlg : ", component);
                    uialg.addChildUiObject(component);
                }
            }

            !tellurium.logManager.isUseLog || fbLog("Headers for StandardTable " + this.uid + ": ", this.headers.showMe());
            valset = this.headers.valSet();
            !tellurium.logManager.isUseLog || fbLog("Headers val set: ", valset);
            for (var j = 0; j < valset.length; j++) {
                var header = valset[j];
                !tellurium.logManager.isUseLog || fbLog("header: ", header);
                if (!header.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Add header of StandardTable " + this.uid + " to UiAlg : ", header);
                    uialg.addChildUiObject(header);
                }
            }

            !tellurium.logManager.isUseLog || fbLog("Footers for StandardTable " + this.uid + ": ", this.footers.showMe());
            valset = this.footers.valSet();
            !tellurium.logManager.isUseLog || fbLog("Footers val set: ", valset);
            for (var k = 0; k < valset.length; k++) {
                var footer = valset[k];
                !tellurium.logManager.isUseLog || fbLog("footer: ", footer);
                if (!footer.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Add footer of StandardTable " + this.uid + " to UiAlg : ", footer);
                    uialg.addChildUiObject(footer);
                }
            }
        }
    },

    lookChildren: function(){
        var validChildren = new Array();

        if (!this.noCacheForChildren) {
            var valset = this.components.valSet();
            !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
            for (var i = 0; i < valset.length; i++) {
                var component = valset[i];
                !tellurium.logManager.isUseLog || fbLog("component: ", component);
                if (!component.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable child of StandardTable " + this.uid + ": ", component);
                    if(component.locator != null && (!component.self)){
                        validChildren.push(component);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                        var ccr = component.lookChildren();
                        if(ccr != null && ccr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                            validChildren = validChildren.concat(ccr);
                        }
                    }
                }
            }

            valset = this.headers.valSet();
            for (var j = 0; j < valset.length; j++) {
                var header = valset[j];
                !tellurium.logManager.isUseLog || fbLog("header: ", header);
                if (!header.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable header of StandardTable " + this.uid + ": ", header);
                    if(header.locator != null && (!header.self)){
                        validChildren.push(header);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + header.uid + " and look for this children", header);
                        var chr = header.lookChildren();
                        if(chr != null && chr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + header.uid + "'s children. ", chr);
                            validChildren = validChildren.concat(chr);
                        }
                    }
                }
            }

            valset = this.footers.valSet();
            for (var k = 0; k < valset.length; k++) {
                var footer = valset[k];
                !tellurium.logManager.isUseLog || fbLog("footer: ", footer);
                if (!footer.lazy) {
                    !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable footer of StandardTable " + this.uid + ": ", footer);
                    if(footer.locator != null && (!footer.self)){
                        validChildren.push(footer);
                    }else{
                        //the component is a logical container, need to go further down to get its children
                        !tellurium.logManager.isUseLog || fbLog("Found logical container " + footer.uid + " and look for this children", footer);
                        var cfr = footer.lookChildren();
                        if(cfr != null && cfr.length > 0){
                            !tellurium.logManager.isUseLog || fbLog("Add logical container " + footer.uid + "'s children. ", cfr);
                            validChildren = validChildren.concat(cfr);
                        }
                    }
                }
            }
        }

        return validChildren;
    },

    lookChildrenNoMatterWhat: function() {
        var children = new Array();

        var valset = this.components.valSet();
        !tellurium.logManager.isUseLog || fbLog("Val set: ", valset);
        for (var i = 0; i < valset.length; i++) {
            var component = valset[i];

            !tellurium.logManager.isUseLog || fbLog("Look ahead nomatter what at cachable child of StandardTable " + this.uid + ": ", component);
            if (component.locator != null && (!component.self)) {
                children.push(component);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + component.uid + " and look for this children", component);
                var ccr = component.lookChildrenNoMatterWhat();
                if (ccr != null && ccr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + component.uid + "'s children. ", ccr);
                    children = children.concat(ccr);
                }
            }
        }

        valset = this.headers.valSet();
        for (var j = 0; j < valset.length; j++) {
            var header = valset[j];

            !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable header of StandardTable " + this.uid + ": ", header);
            if (header.locator != null && (!header.self)) {
                children.push(header);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + header.uid + " and look for this children", header);
                var chr = header.lookChildrenNoMatterWhat();
                if (chr != null && chr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + header.uid + "'s children. ", chr);
                    children = children.concat(chr);
                }
            }
        }

        valset = this.footers.valSet();
        for (var k = 0; k < valset.length; k++) {
            var footer = valset[k];

            !tellurium.logManager.isUseLog || fbLog("Look ahead at cachable footer of StandardTable " + this.uid + ": ", footer);
            if (footer.locator != null && (!footer.self)) {
                children.push(footer);
            } else {
                //the component is a logical container, need to go further down to get its children
                !tellurium.logManager.isUseLog || fbLog("Found logical container " + footer.uid + " and look for this children", footer);
                var cfr = footer.lookChildrenNoMatterWhat();
                if (cfr != null && cfr.length > 0) {
                    !tellurium.logManager.isUseLog || fbLog("Add logical container " + footer.uid + "'s children. ", cfr);
                    children = children.concat(cfr);
                }
            }
        }

        return children;
    },

    // Get runtime ID
    getHeaderRid: function(index){
        return  "_HEADER_" + index;
    },

    findHeaderUiObject: function(index){
        var key = "_HEADER_" + index;
        var obj = this.headers.get(key);

        if(obj == null){
            key = "_HEADER_ALL";
            obj = this.headers.get(key);
        }

        return obj;
    },

    // Get runtime ID
    getFooterRid: function(index){
        return  "_FOOTER_" + index;
    },


    findFooterUiObject: function(index){
        var key = "_FOOTER_" + index;
        var obj = this.footers.get(key);

        if(obj == null){
            key = "_FOOTER_ALL";
            obj = this.footers.get(key);
        }

        return obj;
    },

    // Get runtime ID
    getBodyRid: function(tbody, row, column){
        return "_" + tbody + "_" + row + "_" + column;
    },

    findBodyKeyTemplatePair: function(tbody, row, column){
        var key = "_" + tbody + "_" + row + "_" + column;
        var obj = this.components.get(key);

        //thirdly, check _i_j_ALL format
        if (obj == null) {
            key = "_" + tbody + "_" + row + "_ALL";
            obj = this.components.get(key);
        }

        //then, check _i_ALL_K format
        if (obj == null) {
            key = "_" + tbody + "_ALL_" + column;
            obj = this.components.get(key);
        }

        //check _ALL_j_k format
        if (obj == null) {
            key = "_ALL_" + row + "_" + column;
            obj = this.components.get(key);
        }

        //check _i_ALL_ALL
        if(obj == null){
            key = "_" + tbody + "_ALL_ALL";
            obj = this.components.get(key);
        }

        //check _ALL_j_ALL
        if(obj == null){
            key = "_ALL_" + row + "_ALL";
            obj = this.components.get(key);
        }

        //check _ALL_ALL_k
        if(obj == null){
            key = "_ALL_ALL_" + column;
            obj = this.components.get(key);
        }

        //last, check ALL format
        if (obj == null) {
            key = "_ALL_ALL_ALL";
            obj = this.components.get(key);
        }

        var result = null;
        if(obj != null){
            result = new KeyValuePair();
            result.key = key;
            result.val = obj;
        }

        return result;        
    },

    findUiObject: function(tbody, row, column){
        var key = "_" + tbody + "_" + row + "_" + column;
        var obj = this.components.get(key);

        //thirdly, check _i_j_ALL format
        if (obj == null) {
            key = "_" + tbody + "_" + row + "_ALL";
            obj = this.components.get(key);
        }

        //then, check _i_ALL_K format
        if (obj == null) {
            key = "_" + tbody + "_ALL_" + column;
            obj = this.components.get(key);
        }

        //check _ALL_j_k format
        if (obj == null) {
            key = "_ALL_" + row + "_" + column;
            obj = this.components.get(key);
        }

        //check _i_ALL_ALL
        if(obj == null){
            key = "_" + tbody + "_ALL_ALL";
            obj = this.components.get(key);
        }

        //check _ALL_j_ALL
        if(obj == null){
            key = "_ALL_" + row + "_ALL";
            obj = this.components.get(key);
        }

        //check _ALL_ALL_k
        if(obj == null){
            key = "_ALL_ALL_" + column;
            obj = this.components.get(key);
        }

        //last, check ALL format
        if (obj == null) {
            key = "_ALL_ALL_ALL";
            obj = this.components.get(key);
        }

        return obj;
    },

    getAllBodyCell: function(context, worker){
        !tellurium.logManager.isUseLog || fbLog("Check context in getAllBodyCell", context);
        if (context.domRef != null) {
            var $found = teJQuery(context.domRef).find(this.bt);
            !tellurium.logManager.isUseLog || fbLog("Found table body ", $found.get());
            var bodylist = new Array();

            if ($found.size() > 0) {
                //If the header tag is the same as the body tag
                if (this.ht != this.bt) {
                    bodylist.push($found.first());
                }
                for (var i = 1; i < $found.size() - 1; i++) {
                    bodylist.push($found.eq(i));
                }
                //check the footer tag
                if (this.bt != this.ft) {
                    bodylist.push($found.last());
                }

                !tellurium.logManager.isUseLog || fbLog("Valid table body ", bodylist);
                !tellurium.logManager.isUseLog || fbLog("Type of bodylist ", typeof bodylist);
                var elements = new Array();
                for(var j=0; j<bodylist.length; j++){
                    var $el = teJQuery(bodylist[j]).find(" > " + this.brt + " > " + this.bct);
                    if($el.size() > 0){
                        elements = elements.concat($el.get());
                    }
                }
                !tellurium.logManager.isUseLog || fbLog("Found table body cells ", elements);
                if(elements != null && elements.length > 0){
                    return worker.work(context, elements);
                }
            }
        }
        
        return null;
    },

    getHeaderColumnNum: function(context){
        //First, get the DOM reference of the Standard Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Standard Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Standard Table " + this.uid + " is null");
        }

        var $found = teJQuery(dmr).find("> " + this.ht + ":first > " + this.hrt + ":eq(0) > " + this.hct);

        return $found.size();
    },

    getFooterColumnNum: function(context){
        //First, get the DOM reference of the Standard Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Standard Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Standard Table " + this.uid + " is null");
        }

        var $found = teJQuery(dmr).find("> " + this.ft + ":last > " + this.frt + ":eq(0) > " + this.fct);

        return $found.size();
    },

    getTableRowNum: function(context){
         //First, get the DOM reference of the Standard Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var count = 0;
        if(this.bt == this.ht){
            count++;
        }

        var $found = teJQuery(dmr).find(" > " + this.bt + ":eq(" + count + ") > " + this.brt + ":has(" + this.bct + ")");

        return $found.size();
    },

    getTableRowNumForTbody: function(context, ntbody){
         //First, get the DOM reference of the Standard Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var count = ntbody -1;
        if(this.bt == this.ht){
            count++;
        }

        var $found = teJQuery(dmr).find(" > " + this.bt + ":eq(" + count + ") > " + this.brt + ":has(" + this.bct + ")");

        return $found.size();
    },

    getTableColumnNum: function(context){
          //First, get the DOM reference of the Standard Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var count = 0;
        if(this.bt == this.ht){
            count++;
        }

        var $found = teJQuery(dmr).find(" > " + this.bt + ":eq(" + count + ") > " + this.brt + ":eq(0) > " + this.bct);

        return $found.size();
    },

    getTableColumnNumForTbody: function(context, ntbody){
          //First, get the DOM reference of the Standard Table itself
        var dmr = this.domRef;
        if(dmr == null)
            dmr = context.domRef;

        if(dmr == null){
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var count = ntbody - 1;
        if(this.bt == this.ht){
            count++;
        }

        var $found = teJQuery(dmr).find(" > " + this.bt + ":eq(" + count + ") > " + this.brt + ":eq(0) > " + this.bct);

        return $found.size();
    },

    getTableTbodyNum: function(context){
        //First, get the DOM reference of the Standard Table itself
        var dmr = this.domRef;
        if (dmr == null)
            dmr = context.domRef;

        if (dmr == null) {
            fbError("The DOM reference for Table " + this.uid + " is null", this);
            throw new SeleniumError("The DOM reference for Table " + this.uid + " is null");
        }

        var count = 0;
        if (this.bt == this.ht)
            count++;
        if (this.bt == this.ft)
            count++;

        var $found = teJQuery(dmr).find(" > " + this.bt);

        return $found.size() - count
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        if(this.headers != null && this.headers.length > 0){
            var hkeys = this.headers.keySet();
            for(var i=0; i<this.headers.length; i++){
                var header = this.headers.get(hkeys[i]);
                header.traverse(context, visitor);
            }
        }

        if(this.components != null && this.components.length > 0){
            var keys = this.components.keySet();
            for(var j=0; j<keys.length; j++){
                var child = this.components.get(keys[j]);
                child.traverse(context, visitor);
            }
        }

        if(this.footers != null && this.footers.length > 0){
            var fkeys = this.footers.keySet();
            for(var k=0; k<this.footers.length; k++){
                var footer = this.footers.get(fkeys[k]);
                footer.traverse(context, visitor);
            }
        }
    },

    buildHeaderSData: function(context, npid, domref, key, child){
        var alg = context.alg;
        var sel = this.getHeaderSelector(key, child);
        var $found = teJQuery(domref).find(sel);
        !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + sel + "' for " + this.uid, $found.get());
        if ($found.size() == 1) {
            !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, $found.get(0));
            var cdomref;
            if (child.self) {
                cdomref = $found.get(0);
            } else {
                cdomref = this.locateChild(context, $found.get(0), child);
            }

            var index;
            if(key.match(/[0-9]+/)){
                index = key;
            }else{
                index = $found.index() + 1;
            }
            var csdata = new UiSData(npid, this.getHeaderRid(index), child, cdomref);
            alg.addChildUiObject(csdata);

            return index;
        } else if ($found.size() == 0) {
            fbError("Cannot find UI element " + child.uid, child);
            throw new SeleniumError("Cannot find UI element " + child.uid);
        } else {
            fbError("Found " + $found.size() + " matches for UI element " + child.uid, $found.get());
            throw new SeleniumError("Found " + $found.size() + " matches for UI element " + child.uid);
        }
    },

    buildSNodeForHeader: function(context, npid, domref){
        if(domref != null && this.headers.size() > 0){
            var keys = this.headers.keySet();
            var i, child, inx, index;
            var included = new Array();
            for(i=0; i<keys.length; i++){
                child = this.headers.get(keys[i]);
                index = child.metaData.index.value;
                if(index == "any"){
                    inx = this.buildHeaderSData(context, npid, domref, index, child);
                    included.push(inx);
                }
            }

            var max = this.getHeaderColumnNum(context);
            child = this.locateHeaderChild("last");
            if(child != null){
                inx = this.buildHeaderSData(context, npid, domref, "last", child);
                included.push(inx);
                max = max - 1;
            }

            for (i = 1; i <= max; i++) {
                if (teJQuery.inArray(i, included) == -1) {
                    child = this.locateHeaderChild(i);
                    this.buildHeaderSData(context, npid, domref, i.toString(), child);
                }
            }
        }
    },

    buildFooterSData: function(context, npid, domref, key, child){
        var alg = context.alg;
        var sel = this.getFooterSelector(key, child);
        var $found = teJQuery(domref).find(sel);
        !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + sel + "' for " + this.uid, $found.get());
        if ($found.size() == 1) {
            !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, $found.get(0));
            var cdomref;
            if (child.self) {
                cdomref = $found.get(0);
            } else {
                cdomref = this.locateChild(context, $found.get(0), child);
            }

            var index;
            if(key.match(/[0-9]+/)){
                index = key;
            }else{
                index = $found.index() + 1;
            }
            var csdata = new UiSData(npid, this.getFooterRid(index), child, cdomref);
            alg.addChildUiObject(csdata);

            return index;
        } else if ($found.size() == 0) {
            fbError("Cannot find UI element " + child.uid, child);
            throw new SeleniumError("Cannot find UI element " + child.uid);
        } else {
            fbError("Found " + $found.size() + " matches for UI element " + child.uid, $found.get());
            throw new SeleniumError("Found " + $found.size() + " matches for UI element " + child.uid);
        }
    },

    buildSNodeForFooter: function(context, npid, domref){
        if(domref != null && this.footers.size() > 0){
            var keys = this.footers.keySet();
            var i, child, inx, index;
            var included = new Array();
            for(i=0; i<keys.length; i++){
                child = this.footers.get(keys[i]);
                index = child.metaData.index.value;
                if(index == "any"){
                    inx = this.buildFooterSData(context, npid, domref, index, child);
                    included.push(inx);
                }
            }

            var max = this.getFooterColumnNum(context);
            child = this.locateFooterChild("last");
            if(child != null){
                inx = this.buildFooterSData(context, npid, domref, "last", child);
                included.push(inx);
                max = max - 1;
            }

            for(i=1; i<=max; i++){
                if (teJQuery.inArray(i, included) == -1) {
                    child = this.locateFooterChild(i);
                    this.buildFooterSData(context, npid, domref, i.toString(), child);
                }
            }
        }
    },

    buildBodySData: function(context, npid, domref, key, child){
        var alg = context.alg;
        var sel = this.getCellSelector(context, key, child);
        var $found = teJQuery(domref).find(sel);
        !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + sel + "' for " + this.uid, $found.get());
        if ($found.size() == 1) {
            !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, $found.get(0));
            var cdomref;
            if (child.self) {
                cdomref = $found.get(0);
            } else {
                cdomref = this.locateChild(context, $found.get(0), child);
            }

            var index = this.buildIndex(key, $found);
            var csdata = new UiSData(npid, index, child, cdomref);
            alg.addChildUiObject(csdata);

            return index;
        } else if ($found.size() == 0) {
            fbError("Cannot find UI element " + child.uid, child);
            throw new SeleniumError("Cannot find UI element " + child.uid);
        } else {
            fbError("Found " + $found.size() + " matches for UI element " + child.uid, $found.get());
            throw new SeleniumError("Found " + $found.size() + " matches for UI element " + child.uid);
        }
    },

    buildSNodeForBody: function(context, npid, domref) {
        if (domref != null && this.components.size() > 0) {
            var i, j, k, key, child;
            var included = new Array();
            var keySet = this.components.keySet();
            for(i=0; i<keySet.length; i++){
                key = keySet[i];
                child = this.components.get(key);
                var meta = child.metaData;
                !tellurium.logManager.isUseLog || fbLog("Meta Data for key " + key, meta);
                if(!(this.inMultiSet(meta.tbody.value) || this.inMultiSet(meta.row.value) || this.inMultiSet(meta.column.value))){
                    key = "_" + meta.tbody.value + "_" + meta.row.value + "_" + meta.column.value;
                    var index = this.buildBodySData(context, npid, domref, key, child);
                    included.push(index);
                }
            }

            var bodynum = this.getTableTbodyNum(context);
            var rownum = this.getTableRowNum(context);
            var colnum = this.getTableColumnNum(context);
            for (i = 1; i <= bodynum; i++) {
                for (j = 1; j <= rownum; j++) {
                    for (k = 1; k <= colnum; k++) {
                        key = this.getBodyRid(i, j, k);
                        if (teJQuery.inArray(key, included) == -1) {
                            child = this.locateTBodyChild(key);
                            this.buildBodySData(context, npid, domref, key, child);
                        }
                    }
                }
            }
        }
    },

    buildSNode: function(context, pid, rid, domref){
        var node = new UiTNode();
        node.objRef = this;
        node.rid = rid;
        node.pid = pid;
        node.domRef = domref;

        var npid = this.buildPid(pid, rid);

        this.buildSNodeForHeader(context, npid, domref);
        this.buildSNodeForFooter(context, npid, domref);
        this.buildSNodeForBody(context, npid, domref);

        return node;
    },
    
    walkToHeader: function(context, uiid) {
        //pop up the "header" indicator
        uiid.pop();
        //reach the actual uiid for the header element
        var child = uiid.pop();

        var key = child.replace(/^_/, '');

        var cobj = this.locateHeaderChild(key);

//        child = child.replace(/^_/, '').replace(/HEADER/, '');
//
//        var index = parseInt(trimString(child));
//
//        //try to find its child
//        var cobj = this.findHeaderUiObject(index);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (context.domRef != null) {
            var sel = this.getHeaderSelector(key, cobj);

            var $found = teJQuery(context.domRef);
            if(sel != null && sel.trim().length > 0){
                $found = $found.find(sel);
            }
            !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + sel +"' for Table " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0){
                    context.domRef = null;
                    fbError("Cannot find the child UI element " + key, this);
                }

                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element " + key, $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
            if(cobj.self){                
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return StandardTable head ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to StandardTable head ", cobj);
            return cobj.walkTo(context, uiid);
        }
    },

    walkToFooter: function(context, uiid) {
        //pop up the "foot" indicator
        uiid.pop();
        //reach the actual uiid for the header element
        var child = uiid.pop();

        var key = child.replace(/^_/, '');
        var cobj = this.locateFooterChild(key);

//        child = child.replace(/^_/, '').replace(/FOOTER/, '');
//
//        var index = parseInt(trimString(child));
//
//        //try to find its child
//        var cobj = this.findFooterUiObject(index);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (context.domRef != null) {
            var sel = this.getFooterSelector(cobj);

            var $found = teJQuery(context.domRef);
            if(sel != null && sel.trim().length > 0){
                $found = $found.find(sel);
            }
            !tellurium.logManager.isUseLog || fbLog("Found child " + key + " with CSS selector '" + sel +"' for Table " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0){
                    context.domRef = null;
                    fbError("Cannot find the child UI element " + key, this);
                }

                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element " + key, $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
            if(cobj.self){
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return StandardTable foot ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to StandardTable foot ", cobj);
            return cobj.walkTo(context, uiid);
        }
    },

    walkToElement: function(context, uiid) {
        var child = uiid.pop();
        var cobj = this.locateTBodyChild(child);

//        var parts = child.replace(/^_/, '').split("_");
//        var ntbody;
//        var nrow;
//        var ncolumn;
//        if(parts.length == 3){
//            ntbody = parseInt(parts[0]);
//            nrow = parseInt(parts[1]);
//            ncolumn = parseInt(parts[2]);
//        }else{
//            ntbody = 1;
//            nrow = parseInt(parts[0]);
//            ncolumn = parseInt(parts[1]);
//        }
//
//        //otherwise, try to find its child
//        var cobj = this.findUiObject(ntbody, nrow, ncolumn);

        //If cannot find the object as the object template, return the TextBox as the default object
        if (cobj == null) {
            cobj = this.defaultUi;
        }

        if (context.domRef != null) {
            var sel = this.getCellSelector(context, child, cobj);

            var $found = teJQuery(context.domRef);
            if(sel != null && sel.trim().length > 0){
                $found = $found.find(sel);
            }
            !tellurium.logManager.isUseLog || fbLog("Found child with CSS selector '" + sel +"' for StandardTable " + this.uid, $found.get());
            if ($found.size() == 1) {
                context.domRef = $found.get(0);
                !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
            } else {
                if ($found.size() == 0){
                    context.domRef = null;
                    fbError("Cannot find the child UI element ", this);
                }

                if ($found.size() > 1) {
                    fbError("Found multiple matches for UI element ", $found.get());
                    context.domRef = null;
                }
            }
        }

        if (cobj.locator != null) {
//            if (this.bct == cobj.locator.tag && cobj.locator.header == null) {
            if(cobj.self){
                context.skipNext = true;
            }
        }

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return StandardTable child ", cobj);
            return cobj;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to StandardTable child ", cobj);
            return cobj.walkTo(context, uiid);
        }
    },

    walkTo: function(context, uiid){
        !tellurium.logManager.isUseLog || fbLog("Walk to " + this.uiType + " " + this.uid, this);
        if (!context.skipNext) {
            if (this.domRef != null && this.amICacheable()) {
                context.domRef = this.domRef;
            } else {
                if (this.locator != null && context.domRef != null) {
                    var alg = context.alg;
                    var sel = alg.buildSelector(this.locator);
                    var $found = teJQuery(context.domRef);
                    if(sel != null && sel.trim().length > 0){
                        $found = $found.find(sel);
                    }
                    if ($found.size() > 1) {
                        //Use bestGuess() to eliminate multipe matches
                        $found = alg.bestEffort(this, $found);
                    }

                    if ($found.size() == 1) {
                        context.domRef = $found.get(0);
                        !tellurium.logManager.isUseLog || fbLog("Found element " + this.uid, context.domRef);
                    } else {
                        if ($found.size() == 0){
                            context.domRef = null;
                            fbError("Cannot find UI element " + this.uid, this);
                        }

                        if ($found.size() > 1) {
                            fbError("Found multiple matches for UI element " + this.uid, $found.get());
                            context.domRef = null;
                        }
                    }
                }
            }
        } else {
            context.skipNext = false;
        }
        !tellurium.logManager.isUseLog || fbLog("Processing the StandardTable itself and got the context dom Referece", context.domRef);

        if (uiid.size() < 1)
            return this;

        var child = uiid.peek();
        !tellurium.logManager.isUseLog || fbLog("Peek child " + child, this);
        
        if(child == "header" || child.startsWith("_HEADER")){
            return this.walkToHeader(context, uiid);
        }else if(child == "footer" || child.startsWith("_FOOTER")){
            return this.walkToFooter(context, uiid);
        } else {
            return this.walkToElement(context, uiid);
        }
    }
});

var UiWindow = UiContainer.extend({
    init: function(){
        this._super();
        this.uiType = 'Window';
        this.id = null;
        this.name = null;
        this.title = null;
    }
});


function UiAllPurposeObjectBuilder(){

}

UiAllPurposeObjectBuilder.prototype.build = function(){
   return new UiAllPurposeObject();
};

function UiButtonBuilder(){

}

UiButtonBuilder.prototype.build = function(){
   return new UiButton();
};

function UiCheckBoxBuilder(){

}

UiCheckBoxBuilder.prototype.build = function(){
   return new UiCheckBox();
};

function UiDivBuilder(){

}

UiDivBuilder.prototype.build = function(){
    return new UiDiv();
};

function UiIconBuilder(){

}

UiIconBuilder.prototype.build = function(){
    return new UiIcon();
};

function UiImageBuilder(){

}

UiImageBuilder.prototype.build = function(){
    return new UiImage();
};

function UiInputBoxBuilder(){

}

UiInputBoxBuilder.prototype.build = function(){
    return new UiInputBox();
};

function UiRadioButtonBuilder(){

}

UiRadioButtonBuilder.prototype.build = function(){
    return new UiRadioButton();
};

function UiSelectorBuilder(){

}

UiSelectorBuilder.prototype.build = function(){
    return new UiSelector();
};

function UiSpanBuilder(){

}

UiSpanBuilder.prototype.build = function(){
    return new UiSpan();
};

function UiSubmitButtonBuilder(){

}

UiSubmitButtonBuilder.prototype.build = function(){
    return new UiSubmitButton();
};

function UiTextBoxBuilder(){

}

UiTextBoxBuilder.prototype.build = function(){
     return new UiTextBox();
};

function UiUrlLinkBuilder(){

}

UiUrlLinkBuilder.prototype.build = function(){
    return new UiUrlLink();
};

function UiContainerBuilder(){

}

UiContainerBuilder.prototype.build = function(){
    return new UiContainer();
};

function UiRepeatBuilder(){

}

UiRepeatBuilder.prototype.build = function(){
    return new UiRepeat();
};

function UiFormBuilder(){

}

UiFormBuilder.prototype.build = function(){
    return new UiForm();
};

function UiFrameBuilder(){

}

UiFrameBuilder.prototype.build = function(){
    return new UiFrame();
};

function UiListBuilder(){

}

UiListBuilder.prototype.build = function(){
    return new UiList();
};

function UiTableBuilder(){

}

UiTableBuilder.prototype.build = function(){
    return new UiTable();
};

function UiStandardTableBuilder(){

}

UiStandardTableBuilder.prototype.build = function(){
    return new UiStandardTable();
};

function UiWindowBuilder(){

}

UiWindowBuilder.prototype.build = function(){
    return new UiWindow();
};
