var Index = Class.extend({
    init: function(inx){
        this.type = "VAL";
        this.value = inx;
    },

    constDefaultIndex: function(inx){
        this.type = "VAL";
        this.value = inx;
    }
});

function RIndex() {
  //for tbody
  this.x = null;

  //for row
  this.y = null;

  //for column
  this.z = null;
}



var MetaData = Class.extend({
   init: function() {
       this.type = null;
       this.id = null;
   }
});

var ListMetaData = MetaData.extend({
    init: function(){
        this._super();
        this.index = null;
    }
});

var TableMetaData = MetaData.extend({
    init: function(){
        this._super();
        this.tbody = null;
        this.row = null;
        this.column = null;
    }
});

function Path(){
    this.stack = new Array();
}

Path.prototype.init = function(paths) {
    if (paths != null && paths.length > 0) {
        for (var i = paths.length - 1; i >= 0; i--) {
            this.stack.push(paths[i]);
        }
    }
};

Path.prototype.size = function(){
    return this.stack.length;
};

Path.prototype.pop = function(){
    if(this.stack.length > 0){
        return this.stack.pop();
    }

    return null;
};

var RNode = Class.extend({
    init: function(key, parent, objectRef, presented) {
        this.key = key;
        this.bias = 0;
        this.parent = parent;
        this.children = new Array();
        this.objectRef = objectRef;
        this.presented = presented;
        this.templates = new Array();
    },

    create: function(key, parent, objectRef, presented) {
        this.key = key;
        this.parent = parent;
        this.objectRef = objectRef;
        this.presented = presented;
    },

    addTemplate: function(template){
        if(!this.contains(template))
            this.templates.push(template);
    },

    contains: function(template){
        for(var i=0; i<this.templates.length; i++){
            if(template == this.templates[i])
                return true;
        }

        return false;
    },

    addChild: function(child) {
        this.children.push(child);
    },

    findChild: function(key){
        if(this.children.length > 0){
            for(var i=0; i<this.children.length; i++){
                if(key == this.children[i].key){
                    return this.children[i];
                }
            }
        }

        return null;
    },

    getLevel: function(){
        if(this.parent == null)
            return 1;
        else
            return this.parent.getLevel() + 1;
    },

    getFitness: function() {
        if (this.parent == null)
            return this.bias;
        else
            return this.parent.getFitness() + 1;
    },

    isInPath: function(key, paths){
        var result = false;

        if(paths != null && paths.length > 0){
            for(var i=0; i<paths.length; i++){
                if(key == paths[i]){
                    result = true;
                    break;
                }
            }
        }

        return result;
    },

    walkTo: function(key, path){
        var i;
        if(path.size() > 0){
            path.pop();
            if(this.children != null && this.children.length > 0){
                for(i=0; i<this.children.length; i++){
                    if(key == this.children[i].key){
                        var result = this.children[i].walkTo(key, path);
                        if(result != null)
                            return result;
                    }
                }
            }
        }else{
            if(this.children != null && this.children.length > 0){
                for(i=0; i<this.children.length; i++){
                    if(key == this.children[i].key){
                        return this.children[i];
                    }
                }
            }
        }

        if(this.presented)
            return this;

        return null;
    }
});

var RTree = Class.extend({
    init: function(){
        this.EMPTY_PATH = [];
        this.ROOT_PATH = ["all"];
        this.ODD_PATH = ["all", "odd"];
        this.EVEN_PATH = ["all", "even"];
        this.INDEX_LIST = ["all", "odd", "even", "any", "first", "last"];

        this.root = null;
        this.indices = null;
    },

    isIndex: function(key) {
        return (key.match(/^\d+$/) || this.INDEX_LIST.indexOf(key) != -1);
    },

    isId: function(key) {
        return !this.isIndex(key);
    },

    insert: function(object){
        var oddNode, evenNode, node;
        var meta = object.metaData;
        var index = meta.index.value;
        if("all" == index){
            this.root.objectRef = object;
            this.root.presented = true;
        }else if("odd" == index){
            oddNode = this.root.findChild("odd");
            oddNode.presented = true;
            oddNode.objectRef = object;
        }else if("even" == index){
            evenNode = this.root.findChild("even");
            evenNode.presented = true;
            evenNode.objectRef = object;
        }else if("last" == index){
            var last = this.root.findChild("last");
            if(last == null){
                last = new RNode("last", this.root, object, true);
                this.root.addChild(last);
            }
        }else if("any" == index){

        }else if("first" == index){
            oddNode = this.root.findChild("odd");
            var first = oddNode.findChild("1");
            if(first == null){
                first = new RNode("1", oddNode, object, true);
                oddNode.addChild(first);
            }
        }else if(index.match(/^\d+$/)){
            var inx = parseInt(index);
            if((inx % 2) == 1 ){
                oddNode = this.root.findChild("odd");
                node = oddNode.findChild(index);
                if(node == null){
                    node = new RNode(index, oddNode, object, true);
                    oddNode.addChild(node);
                }
            }else{
                evenNode = this.root.findChild("even");
                node = evenNode.findChild(Index);
                if(node == null){
                    node = new RNode(index, evenNode, object, true);
                    evenNode.addChild(node);
                }
            }
        }else{
            throw new SeleniumError("Invalid Index" + index);
        }
    },

    preBuild: function(){
        var defaultUi = new UiTextBox();
        var meta = new ListMetaData();
        meta.index = new Index("all");
        defaultUi.metaData = meta;

        var allNode = new RNode("all", null, defaultUi, true);
        this.root = allNode;
        var oddNode = new RNode("odd", allNode, defaultUi, false);
        this.root.addChild(oddNode);
        var evenNode = new RNode("even", allNode, defaultUi, false);
        this.root.addChild(evenNode);
    },

    route: function(key){
        var object = this.indices.get(key);
        if(object == null){
            if("first" == key){
                key = "1";
            }

            var list = this.generatePath(key);
            var path = new Path();
            path.init(list);
            object = this.walkTo(key, path);
        }

        return object;
    },

    generatePath: function(key){
        key = key + "";
        !tellurium.logManager.isUseLog || fbLog("generate path for key " + key, this);
        if("odd" == key || "even" == key || "last" == key){
            return this.ROOT_PATH;
        }else if(key.match(/^\d+$/)){
            var inx = parseInt(key);
            if((inx % 2) == 1){
                return this.ODD_PATH;
            }else{
                return this.EVEN_PATH;
            }
        }else if("all" == key){
            return this.EMPTY_PATH;
        }else{
            throw new SeleniumError("Invalid Index " + key);
        }
    },

    walkTo: function(key, path){
        if("all" == key){
            return this.root.objectRef;
        }

        if(path != null && path.size() > 0){
            path.pop();
            var node = this.root.walkTo(key, path);
            if(node != null){
                return node.objectRef;
            }
        }

        return null;
    },

    createIndex: function(key, obj){
        this.indices.put(key, obj);
    }
});

function RNodeComparator(){

}

RNodeComparator.prototype.compare = function(a, b){
    var f1 = a.getFitness();
    var f2 = b.getFitness();
    if(f1 > f2)
      return -1;
    if(f1 == f2)
      return 0;
    else
      return 1;   
};

var RGraph = Class.extend({
    init: function(){
        this.EMPTY_PATH = [];
        this.ROOT_PATH = ["all"];
        this.ODD_PATH = ["all", "odd"];
        this.EVEN_PATH = ["all", "even"];
        this.INDEX_LIST = ["all", "odd", "even", "any", "first", "last"];

        this.indices = null;

        //Internal ID to Template mapping
        this.templates = new Hashtable();

        //tbody RTree
        this.t = null;

        //row RTree
        this.r = null;

        //column RTree
        this.c = null;
    },

    isIndex: function(key) {
        return (key.match(/^\d+$/) || this.INDEX_LIST.indexOf(key) != -1);
    },

    isRef: function(key) {
        return !this.isIndex(key);
    },

    createIndex: function(key, obj){
        this.indices.put(key, obj);
    },

    //Internal id
    getIId: function(obj){
        var meta = obj.metaData;
        var tx = meta.tbody.value;
        var rx = meta.row.value;
        var cx = meta.column.value;

        return "_" + tx + "_" + rx + "_" + cx;
    },

    getIIdStr: function(tx, rx, cx){
       return "_" + tx + "_" + rx + "_" + cx;
    },

    storeTemplate: function(obj){
        var iid = this.getIId(obj);
        this.templates.put(iid, obj);
    },

    insertRTree: function(rTree, index, obj, iid){
        var oddNode, evenNode, node;

        if("all" == index){
            rTree.objectRef = obj;
            rTree.presented = true;
            rTree.addTemplate(iid);
        }else if("odd" == index){
            oddNode = rTree.findChild("odd");
            oddNode.presented = true;
            oddNode.objectRef =obj;
            oddNode.addTemplate(iid);
        }else if("even" == index){
            evenNode = rTree.findChild("even");
            evenNode.presented = true;
            evenNode.objectRef = obj;
            evenNode.addTemplate(iid);
        }else if("last" == index){
            var last = rTree.findChild("last");
            if(last == null){
                last = new RNode("last", rTree, obj, true);
                rTree.addChild(last);
            }
            last.addTemplate(iid);
        }else if("any" == index){
            var any = rTree.findChild("any");
            if(any == null){
                any = new RNode("any", rTree, obj, true);
                rTree.addChild(any);
            }
            any.addTemplate(iid);
        }else if("first" == index){
            oddNode = rTree.findChild("odd");
            var first = oddNode.findChild("1");
            if(first == null){
                first = new RNode("1", oddNode, obj, true);
                oddNode.addChild(first);
            }
            first.addTemplate(iid);
        }else if(index.match(/^\d+$/)){
            var inx = parseInt(index);
            if((inx % 2) == 1){
                oddNode = rTree.findChild("odd");
                node = oddNode.findChild(index);
                if(node == null){
                    node = new RNode(index, oddNode, obj, true);
                    oddNode.addChild(node);
                }
                node.addTemplate(iid);
            }else{
                evenNode = rTree.findChild("even");
                node = evenNode.findChild(index);
                if(node == null){
                    node = new RNode(index, evenNode, obj, true);
                    evenNode.addChild(node);
                }
                node.addTemplate(iid);
            }
        }else{
            //reference node
            var ref = rTree.findChild(index);
            if(ref == null){
                ref = new RNode(index, rTree, obj, true);
                rTree.addChild(ref);
            }
            ref.addTemplate(iid);
        }
    },

    insertTBody: function(obj, iid){
        var meta = obj.metaData;
        var index = meta.tbody.value;
        this.insertRTree(this.t, index, obj, iid);
    },

    insertRow: function(obj, iid){
        var meta = obj.metaData;
        var index = meta.row.value;
        this.insertRTree(this.r, index, obj, iid);
    },

    insertColumn: function(obj, iid){
        var meta = obj.metaData;
        var index = meta.column.value;
        this.insertRTree(this.c, index, obj, iid);
    },

    insert: function(obj){
        var iid = this.getIId(obj);
        this.templates.put(iid, obj);
        this.insertTBody(obj, iid);
        this.insertRow(obj, iid);
        this.insertColumn(obj, iid);
    },

    preBuild: function(){
        var defaultUi = new UiTextBox();
        var meta = new TableMetaData();
        meta.tbody = new Index("all");
        meta.row = new Index("all");
        meta.column = new Index("all");
        meta.id = "defaultUi";
        defaultUi.metaData = meta;
        this.templates.put("_all_all_all", defaultUi);

        var taNode = new RNode("all", null, defaultUi, true);
        taNode.addTemplate("_all_all_all");
        this.t = taNode;
        this.t.bias = 0.1;
        var toNode = new RNode('odd', taNode, defaultUi, false);
        this.t.addChild(toNode);
        var teNode = new RNode('even', taNode, defaultUi, false);
        this.t.addChild(teNode);

        var raNode = new RNode("all", null, defaultUi, true);
        raNode.addTemplate("_all_all_all");
        this.r = raNode;
        this.r.bias = 0.2;
        var roNode = new RNode('odd', raNode, defaultUi, false);
        this.r.addChild(roNode);
        var reNode = new RNode('even', raNode, defaultUi, false);
        this.r.addChild(reNode);

        var caNode = new RNode("all", null, defaultUi, true);
        caNode.addTemplate("_all_all_all");
        this.c = caNode;
        this.c.bias = 0.3;
        var coNode = new RNode('odd', caNode, defaultUi, false);
        this.c.addChild(coNode);
        var ceNode = new RNode('even', caNode, defaultUi, false);
        this.c.addChild(ceNode);
        !tellurium.logManager.isUseLog || fbLog("RGraph after preBuild ", this);
    },

    generatePath: function(key){
        !tellurium.logManager.isUseLog || fbLog("generate path for key " + key, this);
        if("odd" == key || "even" == key || "last" == key || "any" == key){
            return this.ROOT_PATH;
        }else if(key.match(/^\d+$/)){
            var inx = parseInt(key);
            if((inx % 2) == 1){
                return this.ODD_PATH;
            }else{
                return this.EVEN_PATH;
            }
        }else if("all" == key){
            return this.EMPTY_PATH;
        }else{
            return this.ROOT_PATH;
        }
    },

    route: function(key){
        !tellurium.logManager.isUseLog || fbLog("route key " + key, this);
        var object = this.indices.get(key);
        if(object == null){
            var parts = key.replace(/^_/, '').split("_");
            var ids = new Array();
            if(parts.length < 3){
                ids.push("1");
            }
            for (var i = 0; i < parts.length; i++) {
                ids.push(parts[i]);
            }

            var x = ids[0];
            if("first" == x){
                x = "1";
            }

            var y = ids[1];
            if("first" == y){
                y = "1";
            }

            var z = ids[2];
            if("first" == z){
                z = "1";
            }

            var list = this.generatePath(x);
            var path = new Path();
            path.init(list);
            var nx = this.walkTo(this.t, x, path);
            list = this.generatePath(y);
            path = new Path();
            path.init(list);
            var ny = this.walkTo(this.r, y, path);
            list = this.generatePath(z);
            path = new Path();
            path.init(list);
            var nz = this.walkTo(this.c, z, path);
            
            var iid;
            var smallestFitness = 100 * 4;
            var xp = nx;
            while (xp != null) {
                var yp = ny;
                while (yp != null) {
                    var zp = nz;
                    while (zp != null) {
                        iid = this.getIIdStr(xp.key, yp.key, zp.key);
                        if (xp.contains(iid) && yp.contains(iid) && zp.contains(iid)) {
                            var fitness = (nx.getLevel() - xp.getLevel()) * 100 + (ny.getLevel() - yp.getLevel()) * 10 + (nz.getLevel() - zp.getLevel());
                            if (fitness < smallestFitness) {
                                object = this.templates.get(iid);
                                smallestFitness = fitness;
                                !tellurium.logManager.isUseLog || fbLog("Search for iid " + iid, object);
                            }
                        }
                        zp = zp.parent;
                    }
                    yp = yp.parent;
                }
                xp = xp.parent;
            }
        }
            
        return object;
    },

    shareTemplate: function(x, y, z){
        var iid = this.getIIdStr(x.key, y.key, z.key);

        return x.contains(iid) && y.contains(iid) && z.contains(iid);
    },

    walkTo: function(root, key, path){
        if(key == "all"){
            return root;
        }

        if(path != null && path.size() > 0){
            path.pop();
            var node = root.walkTo(key, path);
            if(node != null){
                return node;
            }
        }

        return null;
    }

});
