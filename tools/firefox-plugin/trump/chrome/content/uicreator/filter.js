var blackListAttributes = ["size", "maxlength", "width", "height", "style", "align", "autocomplete", "nowrap"];

//var whiteListAttributes = ["id", "name", "value", "tag", "type", "class", "action", "method", "title", "text", "href", "src", "position"]
var whiteListAttributes = ["id", "name", "value", "tag", "type", "class", "action", "method", "title", "text", "href", "src"];

var eventListAttributes = ["onclick", "ondblclick", "onkeyup", "onkeydown", "onkeypress", "onfocus", "onblur", "onmousedown", "onmouseup", "onmouseover", "onmouseout", "onchange", "onsubmit", "onselect"];

//filter out do not wanted Node attributes, most copied from builder.js
function Filter(){
    this.SEPARATOR = ",";
    this.ATTR_SEPARATOR = ":";
    this.DOJO_ATTACH_EVENT = "dojoAttachEvent";

    this.eventmap =  new HashMap();
    this.eventmap.set("onclick", "click");
    this.eventmap.set("ondblclick", "doubleclick");
    this.eventmap.set("onkeyup", "keyUp");
    this.eventmap.set("onkeydown", "keyDown");
    this.eventmap.set("onkeypress", "keyPress");
    this.eventmap.set("onfocus", "focus");
    this.eventmap.set("onblur", "blur");
    this.eventmap.set("onmousedown", "mouseDown");
    this.eventmap.set("onmouseup", "mouseUp");
    this.eventmap.set("onmouseover", "mouseOver");
    this.eventmap.set("onmouseout", "mouseOut");

    //for dojoAttachEvent
    this.eventmap.set("onmouseenter", "mouseOver");
    this.eventmap.set("onmouseleave", "mouseOut");
};
 
/**
 *  Iterates thru the attributes of a node
 *  and creates and returns a string in key1:value1, key2:value2
 * @param node
 */
Filter.prototype.getAttributesString = function(node){
    var attributes = this.getNotBlackListedAttributes(node.attributes);
    var attr="";
    for(var i=0; i < attributes.length; ++i){
        if(i != 0){
            attr+=", ";
        }
        attr+= attributes[i].name + ": \""+ attributes[i].value+"\"";
    }
    return attr;
};

Filter.prototype.getNotBlackListedAttributes = function(attributes){
    var wantedAttributes = new HashMap();
    for(var i=0; i < attributes.length; ++i){
        if(this.isNotBlackListed(attributes[i].name)){
            if(this.DOJO_ATTACH_EVENT == attributes[i].name){
                this.processDojoAttachEventAttribute(attributes[i].value, wantedAttributes);
            }else{
                wantedAttributes.set(attributes[i].name, attributes[i].value);
            }
        }
    }

    return wantedAttributes;
};

Filter.prototype.processDojoAttachEventAttribute = function(attrValue, map){
    if(trimString(attrValue).length > 0 ){
        var splited = attrValue.split(this.SEPARATOR);
        for(var i=0; i<splited.length; i++){
            if(trimString(splited[i]).length > 0){
                var pair = trimString(splited[i]).split(this.ATTR_SEPARATOR);
                map.set(trimString(pair[0]), trimString(pair[1]));
            }
        }

    }      
};

Filter.prototype.getWhiteListedAttributes = function(attributes){
    var wantedAttributes = new HashMap();
    if(attributes != null && attributes.size() > 0){
        var keys = attributes.keySet();
        for(var i=0; i<keys.length; i++){
            if(whiteListAttributes.indexOf(keys[i]) != -1){
                wantedAttributes.set(keys[i], attributes.get(keys[i]));
//                alert("WhiteListAttribute: " + keys[i] + " " + attributes.get(keys[i]));
            }
        }
    }

    return wantedAttributes;
};

Filter.prototype.isNotBlackListed = function(attribute){
    return blackListAttributes.indexOf(attribute) == -1;
};

Filter.prototype.isWhiteListed = function(attribute){
    return whiteListAttributes.indexOf(attribute) != -1;
};

Filter.prototype.isEventAttribute = function(attribute){
    return eventListAttributes.indexOf(attribute) != -1;
};

Filter.prototype.isEvent = function(attribute){
    var value = this.eventmap.get(attribute);
    return value != null;
};

Filter.prototype.processEventAttributes = function(attributes){
    var respond = new Array();

    if(attributes != null && attributes.size() > 0){
        var keys = attributes.keySet();
        for(var i=0; i<keys.length; i++){
            var key = keys[i];
            if(eventListAttributes.indexOf(key) != -1){
                var event = this.eventmap.get(key);
                if(event != null){
                    respond.push(event);
                }
            }
        }
    }

    return respond;
};