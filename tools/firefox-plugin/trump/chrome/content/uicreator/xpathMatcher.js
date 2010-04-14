
function XPathMatcher(){
    this.DELIMITER = "/";
}

XPathMatcher.prototype.match = function(src, dst){
    if(src == null || dst == null)
            return null;

        //try to find the shorter one from the two xpaths
        var minlen = src.split(this.DELIMITER).length;
        var longlen = dst.split(this.DELIMITER).length;

        var shorter = src;
        var longer = dst;

        if(minlen > longlen){
            minlen = longlen;
            shorter = dst;
            longer = src;
        }

        var shortsplit = shorter.split(this.DELIMITER);
        var longsplit = longer.split(this.DELIMITER);

        var match = new StringBuffer();

        for(var i=0; i<minlen; i++){
            if(shortsplit[i] == longsplit[i]){
                //match
                if(i>0){
                    match.append(this.DELIMITER);
                }
                match.append(shortsplit[i]);
            }else
                //not match
                break;
        }

        return match.toString();
}

XPathMatcher.prototype.remainingXPath = function(original, prefix){
    if(original == null || prefix == null)
            return original;
    if(original.indexOf(prefix) != -1){
        return original.substring(prefix.length);
    }
}