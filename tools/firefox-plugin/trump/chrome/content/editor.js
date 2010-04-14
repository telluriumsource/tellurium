/*
 * An UI of TrUMP IDE.
 */

const DEFAULT_XML = "<?xml version=\"1.0\"?><UIs id=\"customize_tree_xml\" xmlns=\"\"></UIs>";
const DEFAULT_ATTRIBUTES_XML = "<?xml version=\"1.0\"?><attributes id=\"attributes_tree_xml\" xmlns=\"\"></attributes>";
function Editor(window) {
    this.window = window;
    var self = this;
    window.editor = this;
    this.document = document;

    this.logView = new LogView(this);
    this.logView.setLog(logger);

    this.registerRecorder();
    this.innerTree = null;

    this.buildCustomizeTree(DEFAULT_XML);

    this.currentUid = null;

    this.decorator = new Decorator();

//    this.options = new Preferences();
    
    //Detect the browser properties
    BrowserDetect.init();
    this.os = BrowserDetect.OS;
}

Editor.prototype.registerRecorder = function(){
    this.recorder = new Recorder(this.window);
    this.recorder.registerListener();
}

Editor.prototype.unload = function(){
    this.recorder.unregisterListener();
    this.decorator.cleanShowNode();
}

Editor.prototype.close = function(){
//    this.recorder.unregisterListener();
}

Editor.prototype.toggleRecordButton = function(){
    var recordToolbarButton = document.getElementById("record-button");

    if(!recordToolbarButton.getAttribute("checked")){
        recordToolbarButton.setAttribute("checked", "true");

        var stopToolbarButton = document.getElementById("stop-button");
        stopToolbarButton.removeAttribute("checked");

        this.recorder.registerListener();
    }

}

Editor.prototype.toggleStopButton = function(){
    var stopToolbarButton = document.getElementById("stop-button");

    if(!stopToolbarButton.getAttribute("checked")){
        stopToolbarButton.setAttribute("checked", "true");

        var recordToolbarButton = document.getElementById("record-button");
        recordToolbarButton.removeAttribute("checked");

        this.recorder.unregisterListener();
    }
}

Editor.prototype.generateButton = function(){
    this.switchToSourceTab();


    var tagArrays = this.recorder.tagObjectArray;

    var tagObject;
    var element;
    this.innerTree = new Tree();

    for(var i=0; i<tagArrays.length; ++i){
        tagObject = tagArrays[i];
        element = new ElementObject();
        element.uid = tagObject.tag+i;
        element.xpath = tagObject.xpath;
        element.attributes = tagObject.attributes;
        element.domNode = tagObject.node;
        this.innerTree.addElement(element);
    }
    //do some post processing work
    this.innerTree.postProcess();
//    alert("start to update source");
    this.updateSource();

    logger.debug("start to validate UI object's xpath");

    this.innerTree.validate();

    logger.debug("Done validating UI object's XPath");
}

Editor.prototype.updateSource = function(){
    this.clearSourceTabContent();
    var sourceTextNode = document.getElementById("source");

    var uiModelArray = this.innerTree.printUI();
    var uiModel = new StringBuffer();
    if(uiModelArray != undefined){
        for(var j=0; j<uiModelArray.length; ++j){
            uiModel.append(uiModelArray[j]);
        }
    } else {
        logger.error("uiModelArray is not defined, cannot generate source!");
    }

//    logger.debug("ui model generated:\n"+uiModel);
    sourceTextNode.value = uiModel;
}

Editor.prototype.clearButton = function(){
    this.recorder.clearAll();
    this.clearSourceTabContent();
    this.logView.clear();
    this.clearCustomizeTabContext();
    this.decorator.cleanShowNode();
    this.innerTree = null;
}

Editor.prototype.clearCustomizeTabContext = function(){
    document.getElementById("uid").value = "";
    document.getElementById("uiType").value = "";
    document.getElementById("uid").setAttribute("disabled", "true");
    document.getElementById("uiType").setAttribute("disabled", "true");
    document.getElementById("group_Check_Box").disabled = true;
    this.buildCustomizeTree(DEFAULT_XML);
    this.buildUiAttributeTree(DEFAULT_ATTRIBUTES_XML);
}

Editor.prototype.switchToSourceTab = function(){
    document.getElementById("editorTabs").selectedItem = document.getElementById("sourceTab");
}

Editor.prototype.clearSourceTabContent = function(){
    document.getElementById("source").value = "";
}

Editor.prototype.selectedTreeItem = function(event){
    this.recorder.showSelectedNode();  
}

Editor.prototype.customizeButton = function(){
    this.switchToCustomizeTab();
    var xml = DEFAULT_XML;
    if(this.innerTree != null){
        xml = this.innerTree.buildXML();
    }

    this.buildCustomizeTree(xml);
}

Editor.prototype.switchToCustomizeTab = function(){
    document.getElementById("editorTabs").selectedItem = document.getElementById("customizeTab");
}

Editor.prototype.buildCustomizeTree = function(xml) {
    if (xml != null) {
        var parser = new DOMParser();
        var customize_tree = document.getElementById("customize_tree");
        var pxml = parser.parseFromString(xml, "text/xml");
        customize_tree.builder.datasource = pxml;
        customize_tree.builder.rebuild();
    }
}

Editor.prototype.processCustomizeEvent = function(event){
//    logger.debug('Customize ' + event.target.getAttribute("label"));
    this.currentUid = event.target.getAttribute("uid");
    var uiObject = this.innerTree.uiObjectMap.get(this.currentUid);
//    logger.debug("uiObject : " + uiObject.descObject());
    if(uiObject != null){
        this.fillUiObjectFields(uiObject);
        this.enableUiObjectFields();
    }else{
        logger.warn("Cannot find UI object " + this.currentUid);   
    }
}

Editor.prototype.processCheckEvent = function(event){
//    alert("You selected " + event.target.getAttribute("cid"));
}

Editor.prototype.fillUiObjectFields = function(uiObject){
    document.getElementById("uid").value = uiObject.uid;
    document.getElementById("uiType").value = uiObject.uiType;
    
    if (uiObject.container) {
        document.getElementById("group_Check_Box").disabled = false;
        document.getElementById("group_Check_Box").checked = uiObject.group;
    } else {
        document.getElementById("group_Check_Box").disabled = false;
        document.getElementById("group_Check_Box").checked = false;
        document.getElementById("group_Check_Box").disabled = true;
    }

    if (uiObject.node != null) {
        var xml = uiObject.node.buildAttributeXml();
        this.buildUiAttributeTree(xml);
    } else {
        logger.warn("Ui object " + uiObject.uid + " does not point to a Node in the tree")
    }
}

Editor.prototype.enableUiObjectFields = function(){
    document.getElementById("uid").removeAttribute("disabled")
    document.getElementById("uiType").removeAttribute("disabled");
}

Editor.prototype.disableUiObjectFields = function(){
    document.getElementById("uid").setAttribute("disabled", "true");
    document.getElementById("uiType").setAttribute("disabled", "true");   
}

Editor.prototype.buildUiAttributeTree = function(xml) {
    if (xml != null) {
        var parser = new DOMParser();
        var ui_attribute_tree = document.getElementById("ui_attribute_tree");
        var pxml = parser.parseFromString(xml, "text/xml");
        ui_attribute_tree.builder.datasource = pxml;
        ui_attribute_tree.builder.rebuild();
    }
}

Editor.prototype.getElementsByTagValue = function(tag, attr, val){
    var elements = document.getElementsByTagName(tag);
    if(elements != null){
        for(var i=0; i<elements.length; i++){
            var attrval = elements[i].getAttribute(attr);
            if(attrval == val){
                return elements[i];
            }
        }
    }

    return null;
}

Editor.prototype.showNodeOnWeb = function(){
    var uiObject = this.innerTree.uiObjectMap.get(this.currentUid);
    if(uiObject != null){
        if(uiObject.node != null && uiObject.node.domNode != null){
            this.decorator.showNode(uiObject.node.domNode);
        }else{
            logger.error("UI Object " + this.currentUid + " does not point to a Dom Node");
        }
    }else{
        logger.error("Cannot find UI object " + this.currentUid);
    }
}

Editor.prototype.updateUiObject = function(){
    var uiObject = this.innerTree.uiObjectMap.get(this.currentUid);
    if(uiObject != null){
        logger.debug("Update UI object " + this.currentUid);

        //update UID
        uiObject.setUID(document.getElementById("uid").value);

        //update UI Type
        uiObject.setUiType(document.getElementById("uiType").value);

        //update Group attribute
        if(document.getElementById("group_Check_Box").disabled == false){
            uiObject.group = document.getElementById("group_Check_Box").checked;
        }

        //update attributes
        var attrmap = new HashMap();
        var keys = uiObject.node.attributes.keySet();
        for (var i = 0; i < keys.length; i++) {
            var key = keys[i];
            if (key != "tag") {
                //check each attribute, first look at the check box by the name conversion
                var cid = "CID" + key;

//                var elem = $(':checkbox[cid=' + cid + ']')[0];
//                var elem = $('input[name=' + cid + ']')[0];
                var elem = this.getElementsByTagValue("checkbox", "name", cid);
//                var elem = document.getElementById(cid);
                if (elem != null) {
                    //if the attribute is selected
                    if (elem.checked) {
                        //get the id of the value of the attribute
                        var vid = "VID" + key;
//                        var velem = document.getElementById(vid);
//                        var velem = $("textbox[vid="+vid+"]")[0];
//                        var velem = $(':text[vid='+vid+']')[0];
                        var velem = this.getElementsByTagValue("textbox", "name", vid);
                        if(velem != null){
                            var val = velem.value;
                            attrmap.set(key, val);
                        }else{
                            logger.error("Cannot find value node " + vid + " for attribute " + key);
                        }
                    }

                } else {
                    logger.warn("Cannot Find CheckBox " + cid + " for attribute " + key);
                }
            }
        }
        uiObject.updateAttributes(attrmap);
        
        //validate xpath again
        this.innerTree.validate();
        this.customizeButton();
        this.updateSource();
    }else{
        logger.error("Cannot find UI object " + this.currentUid);
    }
}

Editor.prototype.exportUiModule = function(){
    if(this.innerTree != null){
        var txt = this.innerTree.createUiModule();
//        logger.debug("Create UI Module:\n" + txt);

        var dir = Preferences.getPref("extensions.trump.exportdirectory");
        if (dir == undefined || dir == null) {
            if (this.os == "Windows") {
                dir = Preferences.DEFAULT_OPTIONS.defaultWinDirectory;
            } else {
                dir = Preferences.DEFAULT_OPTIONS.defaultDirectory;
            }
        }

        FileUtils.saveAs(dir, txt);
        logger.debug("UI Module is exported to directory " + dir);
    }
}

Editor.prototype.updateOptions = function(){
    window.openDialog("chrome://trump/content/preferences.xul", "options", "chrome,modal,resizable", this.os);
    var jslog = Preferences.getPref("extensions.trump.jslog");
    if(jslog ==undefined){
        jslog = true;
    }
    logger.jslog = jslog;
    
    var elem = document.getElementById("logging-console");
    var elem = window.frames["logViewFrame"].document.getElementById("logging-console");
    if (elem != null) {
        var logWrap = Preferences.getPref("extensions.trump.logwrap");
        if(logWrap == undefined){
            logWrap = true;    
        }

        if (logWrap) {
            elem.style.whiteSpace = "normal";
        } else {
            elem.style.whiteSpac = "nowrap";
        }
    }
}