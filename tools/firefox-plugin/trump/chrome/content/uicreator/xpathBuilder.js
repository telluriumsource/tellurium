/**

    Build xpath based on a set of attributes

 **/

function XPathBuilder(){
    
    this.constants = {
        CHILD : "child",
        DESCENDANT : "descendant",
        DESCENDANT_OR_SELF : "descendant-or-self",
        SELF : "self",
        FOLLOWING : "following",
        FOLLOWING_SIBLING : "following-sibling",
        PARENT : "parent",
        DESCENDANT_OR_SELF_PATH : "/descendant-or-self::",
        CHILD_PATH : "/child::",
        DESCENDANT_PREFIX : "descendant::",
        CHILD_PREFIX : "child::",
        MATCH_ALL : "*",
        CONTAIN_PREFIX : "%%",
        TEXT : "text",
        POSITION: "position"
    };

}

XPathBuilder.prototype.buildPosition = function(position){
    if(position < 1)
        return "";

    return "position()=" + position;
}

XPathBuilder.prototype.startsWith = function(str, pattern) {
    return str.indexOf(pattern) === 0;
}

XPathBuilder.prototype.buildText = function(value){
    if(value == null || trimString(value).length <= 0)
        return "";

    var trimed = trimString(value);
    //start with "%%"
    if(trimed.indexOf(this.constants.CONTAIN_PREFIX) == 0){
        var actual = value.substring(2, value.length-1);
        return "contains(text(), \"" + actual + "\")";
    }else{
        return "normalize-space(text())=normalize-space(\"" + trimed + "\")";
    }
}

XPathBuilder.prototype.buildAttribute = function(name, value){
    if(name == null || trimString(name).length <= 0)
        return "";

    if(value == null || trimString(value).length <= 0){
        return "@" + name;
    }

//    var trimed = trimString(value);
    var trimed = value;
    if(trimed.indexOf(this.constants.CONTAIN_PREFIX) == 0){
        var actual = trimed.substring(2, trimed.length);
        return "contains(@" + name + ", \"" + actual + "\")";
    }else{
        return "@" + name + "=\"" + trimed + "\"";
    }
}

XPathBuilder.prototype.buildXPathWithPrefix = function(prefix, tag, text, position, attributes, groupattrs){
    var sb = new StringBuffer();
    sb.append(prefix);

    if(tag != null && tag.length > 0){
        sb.append(tag);
    }else{
        sb.append(this.constants.MATCH_ALL);
    }

    var list = new Array();
    if(groupattrs != null && groupattrs.length > 0){
        for(var j=0; j<groupattrs.length; j++){
            list.push(groupattrs[j]);
        }
    }

    var vText = this.buildText(text);
    if(vText.length > 0){
        list.push(vText);
    }

    var vPosition = this.buildPosition(position);
    if(vPosition.length > 0){
        list.push(vPosition);
    }

    if(attributes != null && attributes.size() > 0){
        var keys = attributes.keySet();
        for(var i=0; i<keys.length; i++){
            var key = keys[i];
            var value = attributes.get(key);
            if(key != this.constants.TEXT && key != this.constants.POSITION){
                var vAttr = this.buildAttribute(key, value);
                if(vAttr.length > 0){
                    list.push(vAttr);
                }
            }
        }
    }

    if(list.length > 0){
        var attri = list.join(" and ");
        sb.append("[").append(attri).append("]")
    }

    return sb.toString();
}

XPathBuilder.prototype.buildDescendantXPath = function(tag, text, position, attributes){
    if(position != null && position >= 0)
        return this.buildXPathWithPrefix(this.constants.DESCENDANT_PREFIX, tag, text, position, attributes, null);

    return  this.buildXPathWithPrefix(this.constants.DESCENDANT_PREFIX, tag, text, -1 , attributes, null);
}

XPathBuilder.prototype.buildChildXPath = function(tag, text, position, attributes){
    if(position != null && position >= 0)
        return this.buildXPathWithPrefix(this.constants.CHILD_PREFIX, tag, text, position, attributes, null);

    return  this.buildXPathWithPrefix(this.constants.CHILD_PREFIX, tag, text, -1 , attributes, null);
}

XPathBuilder.prototype.internBuildXPath = function(tag, text, position, direct, attributes, groupattrs){
    if(direct)
        return this.buildXPathWithPrefix(this.constants.CHILD_PATH, tag, text, position, attributes, groupattrs);
    else
        return this.buildXPathWithPrefix(this.constants.DESCENDANT_OR_SELF_PATH, tag, text, position, attributes, groupattrs);
}

XPathBuilder.prototype.buildGroupXPath = function(tag, text, position, direct, attributes, groupattrs){
    if(position != null && position >= 0)
        return this.internBuildXPath(tag, text, position, direct, attributes, groupattrs);

    return  this.internBuildXPath(tag, text, -1 , direct, attributes, groupattrs);
}

XPathBuilder.prototype.buildXPath = function(tag, text, position, attributes) {
    if (position != null && position >= 0)
        return this.internBuildXPath(tag, text, position, false, attributes, null);

    return  this.internBuildXPath(tag, text, -1, false, attributes, null);
}

XPathBuilder.prototype.buildOptionalXPath = function(tag, text, position, direct, attributes) {
    if (position != null && position >= 0)
        return this.internBuildXPath(tag, text, position, direct, attributes, null);

    return  this.internBuildXPath(tag, text, -1, direct, attributes, null);
}

XPathBuilder.prototype.buildOptionalXPathVHeader = function(tag, text, position, direct, attributes, header) {
    if (position != null && position >= 0)
        return this.internBuildXPathVHeader(tag, text, position, direct, attributes, null, header);

    return  this.internBuildXPathVHeader(tag, text, -1, direct, attributes, null, header);
}

XPathBuilder.prototype.internBuildXPathVHeader = function(tag, text, position, direct, attributes, groupattrs, header){
    var appendheader = "";

    if(header != null && trimString(header).length > 0){
        var inx = header.indexOf("/");
        //need to remove the "/"
        appendheader = header.substring(inx+1, header.length) + "/";
//        logger.debug("Appended header " + appendheader);
    }

    if(direct){
        return this.buildXPathWithPrefix(this.constants.CHILD_PATH + appendheader, tag, text, position, attributes, groupattrs);
    }else{
        return this.buildXPathWithPrefix(this.constants.DESCENDANT_OR_SELF_PATH + appendheader, tag, text, position, attributes, groupattrs);
    }
}

XPathBuilder.prototype.buildGroupXPathVHeader = function(tag, text, position, direct, attributes, groupattrs, header){
    if(position != null && position >= 0)
        return this.internBuildXPathVHeader(tag, text, position, direct, attributes, groupattrs, header);

    return  this.internBuildXPathVHeader(tag, text, -1 , direct, attributes, groupattrs, header);
}