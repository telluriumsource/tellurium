
function UiObject(){
    this.constants = {
        TAG : "tag",
        RESPOND : "respond",
        GROUP : "group"
    };
    
    this.uid = null;
    this.uiType = null;
    this.clocator = new Locator();
    this.respond = new Array();
    this.container = false;
    this.group = false;
    //the node associated with this UiObject
    this.node = null;

    this.classifier = new UiType();
    this.filter = new Filter();

    this.locatorStrategy = new LocatorStrategy();

    this.isLocatorValid = false;
}

UiObject.prototype.buildUiObject = function(node, hasChildren){
    this.node = node;

    var attributes = node.attributes;
    
    this.uid = node.id;

    var tag = attributes.get(this.constants.TAG);

    this.uiType = this.classifier.getTypeWithExtra(tag, attributes, hasChildren);

    this.respond = this.filter.processEventAttributes(attributes);

    this.container = hasChildren;

    var whiteListAttributes = this.filter.getWhiteListedAttributes(attributes);
    
    this.clocator.buildLocator(whiteListAttributes);
    
    return this;
}

//build relative xpath from UiObject's locator
//If group is true, we need to use its children attributes
UiObject.prototype.buildXPath = function(){
    var xp;

    if(this.group){
        xp = this.locatorStrategy.groupLocate(this.node);
    }else{
        xp = this.locatorStrategy.compositeLocate(this.node);
    }

//    alert("Build xpath for " + this.uid + " XPath: " + xp);
    return xp;
}


UiObject.prototype.strUiObject = function(level){
    var sb = new StringBuffer();
    for (var i = 0; i < level; i++) {
        sb.append("\t");
    }
    sb.append(this.uiType).append("(uid: \"").append(this.uid).append("\", ");
    sb.append(this.clocator.strLocator());

    if(this.respond != null && this.respond.length > 0){
        sb.append(", respond: [");
        for(var j=0; j<this.respond.length; j++){
            if(j>0){
                sb.append(", ");
            }
            sb.append("\"").append(this.respond[j]).append("\"");
        }
        sb.append("]");
    }
    if(this.group){
        sb.append(", group: \"true\"");
    }
    //comment this line out if you do not want xpath to display
    //        sb.append("[xpath: ").append(xpath).append("]");

    sb.append(")");

    if (this.container) {
        sb.append("{");
    }
    sb.append("\n");

    return sb.toString();
};

UiObject.prototype.descObject = function(){
    var sb = new StringBuffer();
    sb.append(this.uiType).append("(uid: '").append(this.uid).append("', ");
    sb.append(this.clocator.descLocator());

    if(this.respond != null && this.respond.length > 0){
        sb.append(", respond: [");
        for(var j=0; j<this.respond.length; j++){
            if(j>0){
                sb.append(", ");
            }
            sb.append("'").append(this.respond[j]).append("'");
        }
        sb.append("]");
    }
    if(this.group == true){
        sb.append(", group: 'true'");
    }

    sb.append(")");

    return sb.toString();
}


UiObject.prototype.strUiObjectFooter = function(level){
    var sb = new StringBuffer();
    
    if (this.container) {
        for (var l = 0; l < level; l++) {
            sb.append("\t");
        }
        sb.append("}\n");
    }

    return sb.toString();
}

UiObject.prototype.paddingByLevel = function(level) {
    var sb = new StringBuffer();

    for (var l = 0; l < level; l++) {
        sb.append("\t");
    }

    return sb.toString();
}

UiObject.prototype.updateAttributes = function(attributeMap){
    this.clocator.updateLocator(attributeMap);
}

UiObject.prototype.addRespond = function(event){
    this.respond.push(event);
}

UiObject.prototype.setUID = function(uid){
    this.uid = uid;
}

UiObject.prototype.setUiType = function(uitype){
    this.uiType = uitype;
}

UiObject.prototype.isContainer = function(){
    this.container = true;
}

UiObject.prototype.notContainer = function(){
    this.container = false;
}

UiObject.prototype.useGroupLocating = function(){
    this.group = true;
}

UiObject.prototype.noGroupLocating = function(){
    this.group = false;
}

UiObject.prototype.toString = function(){
    return "uid : " + this.uid + " uiType : " + this.uiType;
}