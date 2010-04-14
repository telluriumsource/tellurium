//a snapshot of the UI module in the DOM
function UiSnapshot(){
    this.elements = new Hashtable();
    this.color = null;
    this.relaxed = false;
    this.relaxDetails = new Array();
    this.score = 0;
    this.nelem = 0;
}

UiSnapshot.prototype.getScaledScore = function(){
    if(this.nelem == 0)
        return 0;
    else
        return this.score/this.nelem;
};

UiSnapshot.prototype.addUi = function(uid, domref){
    this.elements.put(uid, domref);
};

UiSnapshot.prototype.getUi = function(uid){
    return this.elements.get(uid);
};

UiSnapshot.prototype.clone = function(){
    var snapshot = new UiSnapshot();
    snapshot.elements = this.elements.clone();

    return snapshot;
};

UiSnapshot.prototype.setColor = function(color){
    this.color = color;
};

function UiTemplateAvatar(){
    //shared template UID
    this.uid = null;

    //the actual runtime elements that mapping to the same template
//    this.avatar = new Array();
    //run time id (rid) to snapshot node map
    //Need
    //  uid <--> rid mapping
    this.avatar = new Hashtable();
}

function UiSData(pid, rid, objRef, domRef){
    //parent's rid
    this.pid = pid;
    this.rid = rid;
    this.objRef = objRef;
    this.domRef = domRef;
}

var UiSNode = Class.extend({
    init: function() {

        //parent's rid
        this.pid = null;

        //rid, runtime id
        this.rid = null;

        //point to its parent in the UI SNAPSHOT tree
        this.parent = null;

        //UI object, which is defined in the UI module, reference
        this.objRef = null;

        //DOM reference
        this.domRef = null;
    },

    getLevel: function(){
        if(this.parent == null)
            return 0;

        return this.parent.getLevel() + 1;  
    },

    getFullRid: function(){
        if(this.parent != null){
            return this.parent.getFullRid() + "." + this.rid;
        }

        return this.rid;
    },

    isLeaf: function(){
        return true;
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree Node", this);
        uiid.pop();

        return this;
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
    },

    insert: function(context, node){
        fbError(this.rid + " is not a container type node and it cannot have children", this);
        throw new SeleniumError(this.rid + "is not a container type node and it cannot have children");
    }
});

var UiCNode = UiSNode.extend({
    init: function(){
        this._super();
        //children nodes, regular UI Nodes
        this.components = new Hashtable();

        //children index, hold rid to dom reference mapping for children
//        this.childrenIndex = new Hashtable();
    },

    isLeaf: function(){
        return (this.components.size() == 0);    
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree Container Node", this);

        if (uiid.size() < 1) {
/*            var result = new Array();
            result.push(this);

            return result;*/
            return this;
        }

        var cid = uiid.pop();
        var child = this.components.get(cid);
        if (child != null) {
            !tellurium.logManager.isUseLog || fbLog("Walk to child " + cid, child);
            return child.walkTo(context, uiid);
        } else {
            fbError("Cannot find child " + cid, child);
            return null;
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

    insert: function(context, node){
        node.parent = this;
        this.components.put(node.rid, node);
    }
});

var UiTNode = UiSNode.extend({
    init: function(){
        this._super();

        //header nodes with key as the template UID and value as the UI template Avatar
        this.headers = new Hashtable();

        //footer node with key as the template UID and value as the UI template Avatar
        this.footers = new Hashtable();

        //body nodes with key as the template UID and value as the UI template Avatar
        this.components = new Hashtable();
    },

    isLeaf: function(){
        return !(this.components.size() > 0 || this.headers.size() > 0 && this.footers.size() > 0);
    },

    traverse: function(context, visitor){
        visitor.visit(context, this);
        var i;
        var keys;

        if(this.headers != null && this.headers.length > 0){
             keys = this.headers.keySet();
             for(i=0; i<keys.length; i++){
                this.headers.get(keys[i]).traverse(context, visitor);
             }
        }

        if(this.components != null && this.components.length > 0){
            keys = this.components.keySet();
            for(i=0; i<keys.length; i++){
                this.components.get(keys[i]).traverse(context, visitor);
             }
        }

        if(this.footers != null && this.footers.length > 0){
            keys = this.footers.keySet();
            for(i=0; i<this.footers.length; i++){
                this.footers.get(keys[i]).traverse(context, visitor);
             }
        }        
    },

    walkTo: function(context, uiid) {
        !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree Table Node", this);

        if (uiid.size() < 1) {
/*            var result = new Array();
            result.push(this);

            return result;*/
            return this;
        }

        var child = uiid.peek();

        if(child.startsWith("_HEADER")){
            return this.walkToHeader(context, uiid);
        }else if(child.startsWith("_FOOTER")){
            return this.walkToFooter(context, uiid);
        } else {
            return this.walkToElement(context, uiid);
        }

    },

    walkToHeader: function(context, uiid){
         //pop up the "header" indicator
        uiid.pop();

        if (uiid.size() < 1) {
            //not more child needs to be found
            !tellurium.logManager.isUseLog || fbLog("Return Table head ", this);
/*            var result = new Array();
            result.push(cobj);
            return result;*/
            return this;
//            return cobj.avatar;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table head ", this);
            //reach the actual uiid for the header element
            var cid = uiid.pop();
            var child = this.headers.get("_HEADER" + cid);
            if(child == null){
                fbError("Cannot find child " + cid, this);
                throw new SeleniumError("Cannot find child " + cid);
            }
                
            if(uiid.size() < 1)
                return child;

            return child.walkTo(context, uiid);
        }

    },

    walkToFooter: function(context, uiid) {
         //pop up the "header" indicator
        uiid.pop();

        if (uiid.size() < 1) {
             !tellurium.logManager.isUseLog || fbLog("Return Table foot ", this);
             return this;
//            return cobj.avatar;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table footer", this);

            var cid = uiid.pop();
            var child = this.footers.get("_FOOTER" + cid);
            if(child == null){
                fbError("Cannot find child " + cid, this);
                throw new SeleniumError("Cannot find child " + cid);
            }

            if(uiid.size() < 1)
                return child;

            return child.walkTo(context, uiid);
        }

    },

    walkToElement: function(context, uiid) {
        uiid.pop();
        
        if (uiid.size() < 1) {
             !tellurium.logManager.isUseLog || fbLog("Return Table body ", this);
             return this;
        } else {
            //recursively call walkTo until the object is found
            !tellurium.logManager.isUseLog || fbLog("Walk to Table body", this);

            var cid = uiid.pop();
            var child = this.components.get(cid);
            if(child == null){
                fbError("Cannot find child " + cid, this);
                throw new SeleniumError("Cannot find child " + cid);
            }

            if(uiid.size() < 1)
                return child;

            return child.walkTo(context, uiid);
        }

    },

    insertInto: function(context, hashtable, node){
        node.parent = this;
        hashtable.put(node.rid, node);
    },

    insert: function(context, node){
        var rid = node.rid;

        if(rid.startsWith("_HEADER")){
            this.insertInto(context, this.headers, node);
        }else if(rid.startsWith("_FOOTER")){
            this.insertInto(context, this.footers, node);
        } else {
            this.insertInto(context, this.components, node);
        }
    }
});

function UiSTree(){
    //the root node
    this.root = null;

    //the reference point to the UI module that the UI Snapshot tree is derived
    this.uimRef = null;
}

UiSTree.prototype.walkTo = function(context, uiid){
    !tellurium.logManager.isUseLog || fbLog("Walk to Snapshot Tree Node", this);
    if(this.root != null){
        if(uiid.size() > 0){
            var rid = uiid.pop();
            if(this.root.rid == rid){
                return this.root.walkTo(context, uiid);   
            }

        }
        
        return null;
    }


    return null;
};

UiSTree.prototype.traverse = function(context, visitor){
    if(this.root != null){
        this.root.traverse(context, visitor);
    }
};

UiSTree.prototype.insert = function(context, node){
    if(this.root == null){
        this.root = node;
    }else{
//        var pid = node.objRef.parent.fullUid();
//        var pid = node.parent.getFullRid();
        var pid = node.pid;
        var uiid = getUiid(pid);
//        var pnode = this.root.walkTo(context, uiid);
        var pnode = this.walkTo(context, uiid);
        pnode.insert(context, node);
    }
};

var STreeVisitor = Class.extend({
    init: function(){

    },

    visit: function(context, snode){

    }
});

var STreeChainVisitor = Class.extend({
    init: function(){
        this.chain = new Array();
    },

    removeAll: function(){
        this.chain = new Array();
    },

    addVisitor: function(visitor){
        this.chain.push(visitor);
    },

    size: function(){
        return this.chain.length;
    },

    visit: function(context, snode){
        for(var i=0; i<this.chain.length; i++){
            this.chain[i].visit(context, snode);
        }
    }
});

var UiHTMLSourceVisitor = STreeVisitor.extend({
    init: function(){
//        this.htmlsource = new Hashtable();
        this.htmlsource = new Array();
    },

    visit: function(context, snode){
        var domref = snode.domRef;
        var html = teJQuery(domref).outerHTML();
        var frid = snode.getFullRid();
        var pair = new KeyValuePair();
        pair.key = convertRidToUid(frid);
        pair.val = html;
        this.htmlsource.push(pair);
        !tellurium.logManager.isUseLog || fbLog("HTML Source for " + frid, html);
    }
});

var UiCollectVisitor = STreeVisitor.extend({
    init: function(){
        this.elements = new Array();
    },

    visit: function(context, snode){
        var elem = snode.domRef;
        this.elements.push(elem);

        !tellurium.logManager.isUseLog || fbLog("Collect " + snode.getFullRid(), elem);
    }
});

var UiOutlineVisitor = STreeVisitor.extend({
    
    visit: function(context, snode){
        var elem = snode.domRef;
        teJQuery(elem).data("originalOutline", elem.style.outline);
        elem.style.outline = tellurium.outlines.getDefaultOutline();

        !tellurium.logManager.isUseLog || fbLog("Add outline for " + snode.getFullRid(), elem);
    }
});

var UiOutlineCleaner = STreeVisitor.extend({
    visit: function(context, snode){
        var elem = snode.domRef;
        var $elem = teJQuery(elem);
        var outline = $elem.data("originalOutline");
//        elem.style.outline = "";
        elem.style.outline = outline;
        $elem.removeData("originalOutline");

        !tellurium.logManager.isUseLog || fbLog("Clean outline for " + snode.getFullRid(), elem);
    } 
});

var UiSimpleTipVisitor = STreeVisitor.extend({

    visit: function(context, snode) {
        var elem = snode.domRef;
        var frid = snode.getFullRid();

        var $elem = teJQuery(elem);
        $elem.data("level", snode.getLevel());
        !tellurium.logManager.isUseLog || fbLog("element with simpletip ", elem);
        $elem.simpletip({
            // Configuration properties
/*            onShow: function() {
                var $parent = this.getParent();
                var parent = $parent.get(0);
                !tellurium.logManager.isUseLog || fbLog("Get Parent for element", parent);
                var level = $parent.data("level");

                var outline = $parent.data("outline");
                if(outline == undefined || outline == null){
                    $parent.data("outline", parent.style.outline);
                }

                parent.style.outline = tellurium.outlines.getOutline(level);
            },

            onHide: function() {
                var $parent = this.getParent();
                var parent = $parent.get(0);

                parent.style.outline = $parent.data("outline");
            },
           */
            content: convertRidToUid(frid),
            fixed: false
        });
        !tellurium.logManager.isUseLog || fbLog("Add simple tip for " + frid, elem);
    }
});

var UiSimpleTipCleaner = STreeVisitor.extend({
    visit: function(context, snode){
        var elem = snode.domRef;
        var frid = snode.getFullRid();

        var $elem = teJQuery(elem);
        $elem.removeData("outline");
        $elem.removeData("level");
        $elem.find("~ div.teengine.tooltip").remove();

        !tellurium.logManager.isUseLog || fbLog("Clean simple tip for " + frid, elem);
    }
});