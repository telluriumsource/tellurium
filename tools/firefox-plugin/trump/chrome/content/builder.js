
var constants = {
    ELEMENT_TYPE_NODE : 1
}

var blackListAttributes = ["size", "maxlength", "width", "height", "style", "align", "autocomplete"]

function Builder(){
    this.bundle = $("string");
}

/**
 * Gets the bunde to use
 */
Builder.getBundle = function(){
    return document.getElementById("strings");
}

Builder.getFormattedString = function(key){

}

Builder.prototype.createTagObject = function(node){
    var lowerCaseNodeName ;
    var attributes;
    var parent;
    var xpath;
    var name;
    //Check if its an ELEMENT TYPE NODE
    if (getNodeType(node) == constants.ELEMENT_TYPE_NODE) {
        lowerCaseNodeName = getNodeName(node).toLowerCase();
        attributes = getNotBlackListedAttributes(node.attributes);
        attributes.set("tag", lowerCaseNodeName);
        var txt = this.getText(node);

        if(txt != null && trimString(txt).length > 0){
            attributes.set("text", txt);
        }

        parent = node.parentNode;
        name = getAttributeNameOrId(node)
        //TODO xpath creation
        xpath = createXPath(node);
        return this.buildTagObject(node, lowerCaseNodeName, name, attributes, parent, xpath);
    }

    return null;
}

Builder.prototype.getText = function(node) {
    var txt = null;
    if (getNodeType(node) == constants.ELEMENT_TYPE_NODE) {
        if (node.childNodes.length > 0) {
            for (var i = 0; i < node.childNodes.length; i++) {
                if (node.childNodes[i].nodeType == Node.TEXT_NODE) {
                    txt = node.childNodes[i].nodeValue;
                    if (txt != null) {
                        //test if the regular expression includes "
                        var regexp = new RegExp(/\"/);
                        if(regexp.test(txt)){
                            //if we do have double quota " inside
                            //throw away the text attribute because if it is way too difficult to escape
                            txt = null;
                        }
                    }

                    break;
                }
            }
        }
    }

    return txt;
}

Builder.prototype.buildTagObject = function(node, tag, name, attributes, parent, xpath){
    var tagObject = new TagObject();
    tagObject.name = name;
    tagObject.node = node;
    tagObject.tag = tag;
    tagObject.attributes = attributes;
    tagObject.parent = parent;
    tagObject.xpath = xpath;

    return tagObject;
}

function createXPath(node){
    return generateXPath(node);    
}


/**
 *  Iterates thru the attributes of a node
 *  and creates and returns a string in key1:value1, key2:value2
 * @param node
 */
function getAttributesString(node){
    var attributes = getNotBlackListedAttributes(node.attributes);
    var attr="";
    for(var i=0; i < attributes.length; ++i){
        if(i != 0){
            attr+=", "
        }
        attr+= attributes[i].name + ": \""+ attributes[i].value+"\"";
    }
    return attr;
}

function getNotBlackListedAttributes(attributes){
    var wantedAttributes = new HashMap();
    for(var i=0; i < attributes.length; ++i){
        if(isNotBlackListed(attributes[i].name)){
            wantedAttributes.set(attributes[i].name, attributes[i].value);
        }
    }
    return wantedAttributes;
}

function isNotBlackListed(attribute){
    return blackListAttributes.indexOf(attribute) == -1;
}

