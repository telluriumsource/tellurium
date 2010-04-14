function XPathProcessor(){
    //delimiter for different nodes
    this.DELIMITER = "/";
    //separator for tag and attributes
    this.SEPARATOR = "[";
    //the separator for xpath prefix such as
    //  /default:html/default:body[@class='homepage']/default:div[@id='container']/default:div[@id='header']/default:ul/default:li[@id='menu_projects']/default:a
    this.PREFIX = ":";
}

XPathProcessor.prototype.splitXPath = function(xpath){
    var result = new Array();

    if(xpath != null){
        var splited = xpath.split(this.DELIMITER);
        //need to remove the first empty string
        for(var i=0; i<splited.length; i++){
            if(trimString(splited[i]).length > 0){
                result.push(trimString(splited[i]));
            }
        }
    }
    
    return result;
}

XPathProcessor.prototype.getTag = function(str){
    var tag = null;
    if(str != null){
        var lst = str.split(this.SEPARATOR);
        tag = lst[0];
        var inx = tag.indexOf(this.PREFIX);
        if(inx != -1){
            //We have name space or prefix in the tag
            tag = tag.substring(inx + 1, tag.length);
        }
    }

    return tag;
}

XPathProcessor.prototype.getTags = function(xpath){
    var splited = this.splitXPath(xpath);
    var tags = new Array();
    if(splited != null && splited.length > 0){
        for(var i=0; i<splited.length; i++){
            var tag = this.getTag(splited[i]);
            if(tag != null){
                tags.push(tag);
            }
        }
    }
    
    return tags;
}

XPathProcessor.prototype.getSubXPath = function(xpath, inx){
    var subxp = new StringBuffer();
    if(xpath != null){
        var splited = this.splitXPath(xpath);
        if(inx > splited.length-1){
            //change to warning/error to log later
            logger.warn("XPath " + xpath + "Index " + inx + " out of bound");
            inx = splited.length-1;
        }

        for(var i=0; i<=inx; i++){
            subxp.append(this.DELIMITER);
            subxp.append(splited[i])
        }
    }
    
    return subxp.toString();
}

XPathProcessor.prototype.getLastXPath = function(xpath, inx){
    var subxp = new StringBuffer();
    if(xpath != null){
        var splited = this.splitXPath(xpath);
        if(inx > splited.length-1){
            //change to warning/error to log later
//            logger.warn("XPath " + xpath + "Index " + inx + " out of bound");
//            inx = splited.length-1;
            return null;
        }

        for(var i=inx; i<splited.length; i++){
            subxp.append(this.DELIMITER);
            subxp.append(splited[i])
        }
    }

    return subxp.toString();
}

//remove the last portion from the XPath
XPathProcessor.prototype.popXPath = function(xpath){
    var subxp = new StringBuffer();
    if(xpath != null){
        var splited = this.splitXPath(xpath);

        for(var i=0; i<splited.length-1; i++){
            subxp.append(this.DELIMITER);
            subxp.append(splited[i])
        }
    }

    return subxp.toString();
}

XPathProcessor.prototype.findTagIndex = function(tagList, tag){
    var inx = -1;
    if(tagList != null && tag != null){
        for(var i=0; i<tagList.length; i++){
            if(tag == tagList[i]){
                inx = i;
                break;
            }
        }
    }
    
    return inx;
}

XPathProcessor.prototype.reverseList = function (lst){
    if(lst == null || lst.length <= 1){
        return lst;
    }

    var result = new Array();
    for(var i=lst.length-1 ; i>=0; i--){
        result.push(lst[i]);
    }

    return result;
}


XPathProcessor.prototype.startWith = function(xpath, prefix){
    if(xpath == null || prefix == null || xpath.length < prefix.length){
        return false;
    }

    if(xpath.substring(0, prefix.length-1) == prefix){
        return true;
    }

    return false;
}

XPathProcessor.prototype.checkXPathCount = function(doc, xpath) {
//    var nodesSnapshot = document.evaluate(xpath, document, null, XPathResult.UNORDERED_NODE_SNAPSHOT_TYPE, null);
//    return nodesSnapshot.snapshotLength;

    var xpct = "count(" + xpath + ")";
    var result = doc.evaluate(xpct,  doc, null, XPathResult.NUMBER_TYPE, null);
//    logger.debug("Get XPath evalution result " + result.numberValue + " for xpath " + xpath);

    return result.numberValue;
}

XPathProcessor.prototype.checkPosition = function(str){
    if(str != null){
        //check the pattern such as "//a/td[13]/" and "table[@class='st' and position()=3]"
        var mcs = str.match(/(position\(\)=[\d]+|\[[\d]+\])/);
        if(mcs != null){
            var pos = new String(mcs).match(/[\d]+/);

            return pos;
        }
    }

    return null;
}

XPathProcessor.prototype.checkPositionForlastXPath = function(xpath){
    var parts = this.splitXPath(xpath);
    if(parts != null || parts.length > 0){
        var last = parts[parts.length-1];
        return this.checkPosition(last);
    }

    return null;
}