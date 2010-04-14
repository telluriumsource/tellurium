

function TagObject(){
    this.name = "";
    this.parent = null;
    this.node = null;
    this.tag = null;
    this.attributes = new HashMap();
    this.xpath = null;
}

TagObject.prototype.toString = function(){

    return "TagObject - { node : \"" + this.node + "\" tag : \"" + this.tag + "\" attributes : \""+ this.attributes +
           "\" parent : \""+ this.parent + "\" xpath : \"" + this.xpath +"\" }";
}