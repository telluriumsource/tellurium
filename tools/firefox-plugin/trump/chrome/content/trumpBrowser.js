var constants = {
    ELEMENT_TYPE_NODE : 1,
    INPUT_NODE : "input",
    SELECT_NODE : "select",
    ANCHOR_NODE : "a"
}

var blackListAttributes = ["size", "maxlength", "width", "height", "style", "align", "onclick"]

function popUpClickInit(){
    var nodeState = window.opener.nodeState;
    var clickedNode = nodeState.currentNode ;

    init(clickedNode);
}


function init(clickedNode){

    var bundle = getBundle();

    var tag = null;
    var attributeString = null;
    var uid = null;
    var uiText = null;
    var propertyKey = null;
    var lowerCaseNodeName = null;
    var upperCaseNodeName = null;

    logger.debug("bundle : " + bundle);

    var nodeType = getNodeType(clickedNode);
    var nodeValue = getNodeValue(clickedNode);
    var nodeName = getNodeName(clickedNode);

    //Check if its an ELEMENT TYPE NODE
    if (nodeType == constants.ELEMENT_TYPE_NODE) {
        lowerCaseNodeName = nodeName.toLowerCase();
        upperCaseNodeName = nodeName.toUpperCase();

        tag = bundle.getFormattedString("TAG", [lowerCaseNodeName]);
        attributeString = getAttributesString(clickedNode);

        logger.debug("attribute : " + attributeString);

        //If its an Input node, check the type
        if(lowerCaseNodeName == constants.INPUT_NODE){
            uid = createInputUID(getInputNodeType(clickedNode))
            propertyKey = getInputNodeAsProperty(clickedNode);
            uiText = bundle.getFormattedString(propertyKey, [uid, attributeString]);

        }else{
            uid = createNodeUID(clickedNode);
            propertyKey = upperCaseNodeName;
            if(lowerCaseNodeName == constants.SELECT_NODE){
                nodeValue = null;
            }
            uiText = bundle.getFormattedString(propertyKey, [uid, createCLocatorText(tag, attributeString, nodeValue)]);
        }

        logger.debug("string : " + uiText);
        updateUIModelText(uiText);
    }
}

/**
 * Returns the node type
 * @param node
 */
function getNodeType(node){
//    alert(node.nodeType)
    return node.nodeType;
}

/**
 * Returns the node name
 * @param node
 */
function getNodeName(node){
    return node.nodeName;
}

function getInputNodeType(node){
    var type = node.getAttribute("type");
    return (type != null) ? type : "text";
}

function getNodeValue(node){
    jslogger.debug(node.innerHTML);
    return node.nodeValue != null ? node.nodeValue : node.innerHTML;
}

function createInputUID(type){
    var uid = "inputBox1";
    switch(type.toLowerCase()){
        case "button" :uid = "button1"
            break;
        case "checkbox" : uid = "checkbox1"
            break;
        case "file" : //TODO
            break;
        case "hidden" : //TODO
            break;
        case "image" ://TODO
            break;
        case "password" : uid = "password1"
            break;
        case "radio" : uid = "radioButton1"
            break;
        case "submit" : uid = "submitButton1"
            break;
        case "text" : break;
        default : break;
    }
    return uid;
}

function createNodeUID(node){
    var nodeName = getNodeName(node);
    var uid = "UNKNOWN";
    switch(nodeName.toLowerCase()){
        case "a" : uid = "urlLink1";
                    break;
        case "select" : uid = "selector1";
                    break;
        case "div": uid = "div1";
                break;
        case "img" : uid = "image1";
                break;
        case "table" : uid = "table1"
                break;
        case "span" :
        case "p" :
        case "h1":
        case "h2":
        case "h3":
        case "h4":
        case "h5":
        case "h6":
            uid = "textBox1"
         
         //TODO add more here
    }
    return uid;
}

function createTextKeyValue(value){
    if(value){
        var textArray;
        var text = value;
        if(value.indexOf(" ") != -1 ){
            textArray = value.split(" ");
            for(var i=0; i< textArray.length ; ++i){
                if(textArray[i] && trimString(textArray[i]) != ''){
                    text = "%%" + textArray[i];
                    break;
                }
            }
        }else if(value.indexOf("&nbsp;") != -1){
            text = "%%"+value.substring(0, value.indexOf("&nbsp;"));
        }
        return "text : \""+ text + "\"";
    }
    return "";
}

function createCLocatorText(tag, attributeString, nodeValue){
    logger.debug(tag);
    logger.debug(attributeString);
    logger.debug(nodeValue);

    var retValue = tag;
    var text = createTextKeyValue(nodeValue);

    if(text && text.length > 0){
        retValue = retValue +", "+ text;
    }

    if(attributeString){
         retValue = retValue + ", " + attributeString;
     }
    return retValue
}

function getInputNodeAsProperty(node){
    var inputProperty = "INPUT"
    var type = node.getAttribute("type");
    if(type != null){
        switch(type){
            case "button" ://TODO
                break;
            case "checkbox" : inputProperty+=".CHECKBOX"
                break;
            case "file" : //TODO
                break;
            case "hidden" : //TODO
                break;
            case "image" ://TODO
                break;
            case "password" : //TODO
                break;
            case "radio" : inputProperty+=".RADIO"
                break;
            case "submit" : inputProperty+=".SUBMIT"
                break;
            case "text" : //TODO
                break;
            default : break;

        }
    }
    return inputProperty;
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
    var wantedAttributes = new Array();
    for(var i=0; i < attributes.length; ++i){
        if(isNotBlackListed(attributes[i].name)){
            wantedAttributes.push(attributes[i]);
        }
    }
    return wantedAttributes;
}

function isNotBlackListed(attribute){
    logger.debug("isNotBlackListed : " + attribute);
    logger.debug(blackListAttributes.indexOf(attribute) == -1);
    return blackListAttributes.indexOf(attribute) == -1;        
}

/**
 * Gets the bunde to use
 */
function getBundle(){
    return document.getElementById("strings");
}

/**
 * Updates the value of the text box
 * text is the UI Model for the node
 * @param text
 */
function updateUIModelText(text){
    var textNode = document.getElementById('uiModelText');
    textNode.value= text;
}
