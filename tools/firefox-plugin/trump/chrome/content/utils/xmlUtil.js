function XmlUtil(){

}

XmlUtil.prototype.specialCharacterProof = function(str){
    if(this.containSpecialCharacters(str)){
        return this.escapeString(str);
    }

    return str;
}

XmlUtil.prototype.escapeString = function(str){
//    return "<![CDATA[" + str + "]]>";
    var newstr = str;

    if(newstr.indexOf("&") != -1){
        newstr = newstr.replace(/\&/g, "&amp;");
//        alert("Replace & in " + str + " as " + newstr);
    }

    if(newstr.indexOf("<") != -1){
        newstr = newstr.replace(/\</g, "&lt;");
    }

    if(newstr.indexOf(">") != -1){
        newstr = newstr.replace(/\>/g, "&gt;");
    }

    if(newstr.indexOf("\"") != -1){
        newstr = newstr.replace(/\"/g, "&quot;");
    }

    return newstr;
}

//Check if the str contains special characters such as '&', '<',  '>', or '\"'
XmlUtil.prototype.containSpecialCharacters = function(str){

    if(str == null || str.length == 0){
        return false;
    }

    //check if str contains special characters
    if(str.indexOf("&") == -1 && str.indexOf("<") == -1 && str.indexOf(">") == -1 && str.indexOf("\"") == -1){
        return false;
    }

    return true;
}

XmlUtil.prototype.containEscapedCharacters = function(str){

    if(str == null || str.length == 0){
        return false;
    }

    //check if str contains special characters
    if(str.indexOf("&amp;") == -1 && str.indexOf("&lt;") == -1 && str.indexOf("&gt;") == -1 && str.indexOf("&quot;") == -1){
        return false;
    }

    return true;
}

XmlUtil.prototype.unescapeString = function(str){
    var newstr = str;

    if(newstr.indexOf("&") != -1){
        newstr = newstr.replace(/\&amp;/g, "&");
    }

    if(newstr.indexOf("<") != -1){
        newstr.replace(/\&lt;</g, "<");
    }

    if(newstr.indexOf(">") != -1){
        newstr.replace(/\&gt/g, ">");
    }

    if(newstr.indexOf("\"") != -1){
        newstr.replace(/\&quot;"/g, "\"");
    }

    return newstr;
}

XmlUtil.prototype.escapedCharacterProof = function(str){
    if(this.containEscapedCharacters(str)){
        return this.unescapeString(str);
    }

    return str;
}