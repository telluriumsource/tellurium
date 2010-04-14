function getUiid(uid){
    var uiid = new Uiid();
    uiid.convertToUiid(uid);

    return uiid;
}

function matchUiid(uiid1, uiid2){
    var result = new Array();
    var ar1 = uiid1.toArray();
    var ar2 = uiid2.toArray();
    if(ar1.length > 0 && ar2.length > 0){
        var len = ar1.length;
        if(len > ar2.length)
            len = ar2.length;
        for(var i=0; i<len; i++){
            if(ar1[i] == ar2[i]){
                result.push(ar1[i]);
            }else{
                break;
            }
        }
    }

    return result;
}

function matchUid(uid1, uid2){
    var uiid1 = getUiid(uid1);
    var uiid2 = getUiid(uid2);

    return matchUiid(uiid1, uiid2);
}


//Tellurium Internal ID presentation
function Uiid(){
    this.stack = new Array();
}

Uiid.prototype.matchWith = function(uiid){
    var result = new Array();
    var ar1 = this.stack;
    var ar2 = uiid.toArray();
    if(ar1.length > 0 && ar2.length > 0){
        var len = ar1.length;
        if(len > ar2.length)
            len = ar2.length;
        for(var i=0; i<len; i++){
            if(ar1[i] == ar2[i]){
                result.push(ar1[i]);
            }else{
                break;
            }
        }
    }

    return result;
};

Uiid.prototype.subUiid = function(index){
    var nuiid = new Uiid();
    if(index >= 0 && index < this.stack.length){
        nuiid.stack = this.stack.slice(index);
    }

    return nuiid;
};

Uiid.prototype.push = function(uid){
    this.stack.push(uid);
};

Uiid.prototype.pop = function(){
    if(this.stack.length > 0){
//        return this.stack.pop();
        return this.stack.shift();
    }

    return null;
};

Uiid.prototype.reverse = function(){
    if(this.stack.length > 0){
        this.stack.reverse();
    }
};

Uiid.prototype.peek = function(){
    if(this.stack.length > 0){
//        return this.stack[this.stack.length-1];
        return this.stack[0];
    }

    return null;
};

Uiid.prototype.getUid = function(){
    return this.stack.join(".");
};

Uiid.prototype.size = function(){
    return this.stack.length;
};

Uiid.prototype.toArray = function(){
    return this.stack;
};

Uiid.prototype.convertToUiid = function(uid){
    if(uid != null && trimString(uid).length > 0){
        var ids = uid.split(".");
        for(var i= 0; i<ids.length; i++){
            var pp = this.preprocess(ids[i]);
            if(pp.length == 1){
                this.push(pp[0]);
            }else{
                this.push(pp[1]);
                this.push(pp[0]);
            }
        }
    }

    return this;
};

Uiid.prototype.preprocess = function(uid){
    if(uid != null && trimString(uid).length > 0 && uid.indexOf("[") != -1){
        if(uid.indexOf("[") == 0){
            var single = uid.replace(/\[/g, "_").replace(/\]/g, '');
            return [single];
        }else{
            var index = uid.indexOf("[");
            var first = uid.substring(0, index);
            var second = uid.substring(index).replace(/\[/g, "_").replace(/\]/g, '');
            return [second, first];
        }
    }

    return [uid];
};

//i.e., DSL Presentation Table[1][2], its internal RID presentation is Table._1_2
//need a converter for:
//                Table[1][2]  <---> Table._1_2

function convertRidToUid(rid){
    if(rid != null && rid.trim().length > 0){
        var ids = rid.split(".");
        var idl = new StringBuffer();
        var last = null;
        for(var i=0; i<ids.length; i++){
            var t = convertRidField(ids[i]);
            if(last != null){
                if(!t.match(/^\[/) || last.match(/\]$/)){
                    idl.append(".");
                }
            }
            idl.append(t);
            last = t;
        }

        return idl.toString();
    }else{
        return "";
    }
}

function convertRidField(field){
    if(field == null || field.trim().length == 0){
        return "";
    }

    var fields = field.split("_");
    var ar = new StringBuffer();
    for(var i=0; i<fields.length; i++){
        var f = fields[i];
        if(f.trim().length > 0){
            if(f.match(/^\d+$/)){
                ar.append("[" + f + "]");
            }else{
                ar.append(f);
            }
        }
    }

    return ar.toString();
}

function WorkflowContext(){
    this.refLocator = null;
    this.domRef = null;
    this.alg = null;
    this.skipNext = false;
    this.context = null;
}

WorkflowContext.prototype.setContext = function(key, val){
    if(this.context == null)
        this.context = new Hashtable();
    this.context.put(key, val);
};

WorkflowContext.prototype.getContext = function(key){
    if(this.context == null)
        return null;
    return this.context.get(key);
};

//Base locator
function BaseLocator(){
    this.loc = null;
}

//composite locator
function CompositeLocator(){
    this.tag = null;
    this.text = null;
    this.position = null;
    this.direct = false;
    this.header = null;
    this.trailer = null;
    this.attributes = new Hashtable();
}
