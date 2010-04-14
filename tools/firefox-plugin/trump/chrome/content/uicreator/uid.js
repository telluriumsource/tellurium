function Uid(){
    this.constants = {
        UNDEFINED : "undefined",
        SLASH : "/"
    }

}

Uid.prototype.genUid = function(input){
    if(input == null || trimString(input).length == 0){
        return this.constants.UNDEFINED;
    }

    var parts = input.split(this.constants.SLASH);
    var sb = new StringBuffer();
    sb.append("T4");

    for(var i=0; i< parts.length ; ++i){
        var part = parts[i];
        if(trimString(part).length > 0){
            sb.append(part.substring(0,1));
        }
    }

    return sb.toString();
}