
function Decorator(){
    this.bgColor = "#A9DA92";
    this.showBgColor = "#00FFFF";
//    this.showBgColor = "#FF00FF";
    this.outLine = "2px solid #000";
    this.showLine = "3px solid #0000FF";
    this.noBgColor = "";
    this.lastShownNode = null;
    this.currentShownNode = null;
}

Decorator.prototype.addBackground = function(node){
    node.style.backgroundColor = this.bgColor;
}

Decorator.prototype.removeBackground = function(node){
    node.style.backgroundColor = this.noBgColor;    
}

Decorator.prototype.addOutline = function(node){
    node.style.outline = this.outLine;
}

Decorator.prototype.removeOutline = function(node){
    node.style.outline = "";
}

Decorator.prototype.showNode = function(node){
    this.cleanShowNode(node);

//    node.style.backgroundColor = this.showBgColor;
    node.style.outline = this.showLine;
    this.currentShownNode = node;
}

Decorator.prototype.cleanShowNode = function(node){
    if(this.currentShownNode != null){
//        this.currentShownNode.style.backgroundColor = this.noBgColor;
        this.currentShownNode.style.outline = "";
    }
}