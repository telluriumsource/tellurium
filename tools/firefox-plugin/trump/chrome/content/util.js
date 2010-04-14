

function trimString(str) {
//    logger.debug("Str to be trimed " + str);
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

function $(id) {
    return document.getElementById(id);
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
    return (node &&  node.nodeName) ? node.nodeName : "";
}

/**
 * Returns the node value or innerHTML
 * @param node
 */

function getNodeValue(node){
    return node.nodeValue != null ? node.nodeValue : node.innerHTML;
}


function getAttributeNameOrId(node){
//    alert("getAttributeNameOrId()");
    var attributes = node.attributes;
    var val = "";
     for(var i=0; i < attributes.length; ++i){
//         alert(attributes[i].name);
        if(attributes[i].name == 'name' || attributes[i].name == 'id'){
            val = attributes[i].value;
//            alert(val);
            break;
        }
    }
    return val;
}

function findAttributeInList(attributes, attr){
    for(var i=0; i < attributes.length; ++i){
//         alert(attributes[i].name);
        if(attributes[i].name == attr ){
            val = attributes[i].value;
//            alert(val);
            break;
        }
    }
}

function createListCell(value){
    var cell = document.createElement("listcell");
    cell.setAttribute("label",value );

    return cell;
}
