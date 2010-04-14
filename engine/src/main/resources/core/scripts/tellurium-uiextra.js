
//base UI Worker
var UiWorker = Class.extend({
     init: function() {

     },

     work: function(context, elements){
         return elements;
     }
});

var TextUiWorker = UiWorker.extend({
    work: function(context, elements){
        var out = [];
        !tellurium.logManager.isUseLog || fbLog("Starting to collect text for elements ", elements);
        if(elements != null && elements.length > 0){
            var $e = teJQuery(elements);
            $e.each(function() {
                out.push(teJQuery(this).text());
            });
            !tellurium.logManager.isUseLog || fbLog("Collected text for element ", out);
        }

        return out;
    }
});

var ToggleUiWorker = UiWorker.extend({
    work: function(context, elements, delay){
        !tellurium.logManager.isUseLog || fbLog("Starting to toggle elements ", elements);
        if(elements != null && elements.length > 0){
            var $e = teJQuery(elements);
            $e.each(function(){
                teJQuery(this).toggle("slow").fadeIn(delay).toggle("slow");

            });
        }
        !tellurium.logManager.isUseLog || fbLog("Finish toggle elements", elements);
    }
});

var ColorUiWorker = UiWorker.extend({
    work: function(context, elements, delay) {
        if (elements != null && elements.length > 0) {
            var $e = teJQuery(elements);
            $e.each(function() {
                var $te = teJQuery(this);
                $te.data("te-color-bak", $te.css("background-color"));
            });
            $e.css("background-color", "red");
            !tellurium.logManager.isUseLog || fbLog("Set elements to red for ", elements);
            $e.slideUp(100).slideDown(100).delay(delay).fadeOut(100).fadeIn(100);
            !tellurium.logManager.isUseLog || fbLog("Delayed for " + delay, this);
            $e.each(function() {
                //back to the original color
                var $te = teJQuery(this);
                $te.css("background-color", $te.data("te-color-bak"));
                $te.removeData("te-color-bak");
            });
            !tellurium.logManager.isUseLog || fbLog("Elements' color restored to original ones for ", elements);
        }
    }
});

var DecorateUiWorker = UiWorker.extend({
    init: function() {
        this.decorator = new UiDecorator();
    },

    work: function(context, elements, delay) {
         if (elements != null && elements.length > 0) {
            this.decorator.cleanShowNode();
            for(var i; i<elements.length; i++){
//                teJQuery("#TE_ENGINE_SHOW_NODE").remove();
                var elem = elements[i];
                teJQuery(elem).append("<div id='TE_ENGINE_SHOW_NODE'>ShowMe</div>");
                this.decorator.showNode(elem);
                teJQuery(elem).find("#TE_ENGINE_SHOW_NODE").fadeIn(100).delay(delay).fadeOut(100);
            }
         }
    }
});

var UiVisitor = Class.extend({
    init: function(){

    },

    visit: function(context, uiobj){

    }
});

var UiDumpVisitor = UiVisitor.extend({
    visit: function(context, uiobj){
        fbLog("UI Object " + uiobj.fullUid(), uiobj);
    }
});

function UiDecorator(){
    this.bgColor = "#A9DA92";
    this.showBgColor = "#00FFFF";
//    this.showBgColor = "#FF00FF";
    this.originalBgColor = "";
    this.outLine = "2px solid #000";
    this.showLine = "3px solid #0000FF";
    this.noBgColor = "";
    this.lastShownNode = null;
    this.currentShownNode = null;
}

UiDecorator.prototype.backupBackground = function(node){
   this.originalBgColor = node.style.backgroundColor;
};

UiDecorator.prototype.restoreBackground = function(node){
   node.style.backgroundColor = this.originalBgColor;     
};

UiDecorator.prototype.addBackground = function(node){
    node.style.backgroundColor = this.bgColor;
};

UiDecorator.prototype.removeBackground = function(node){
    node.style.backgroundColor = this.noBgColor;
};

UiDecorator.prototype.addOutline = function(node){
    node.style.outline = this.outLine;
};

UiDecorator.prototype.removeOutline = function(node){
    node.style.outline = "";
};

UiDecorator.prototype.showNode = function(node){
//    this.cleanShowNode(node);

//    node.style.backgroundColor = this.showBgColor;
    this.cleanShowNode();
    node.style.outline = this.showLine;
    this.currentShownNode = node;
};

UiDecorator.prototype.cleanShowNode = function(){
    if(this.currentShownNode != null){
//        this.currentShownNode.style.backgroundColor = this.noBgColor;
        this.currentShownNode.style.outline = "";
    }
};
