function UiType() {
    this.constants = {
        INPUT : "input",
        TYPE : "type",
        CHECKBOX : "checkbox",
        RADIO : "radio",
        SUBMIT : "submit",
        BUTTON : "button",
        IMAGE : "image"
    }

    this.map = new HashMap();
    this.map.set("divN", "Div");
    this.map.set("divY", "Container");
    this.map.set("aN", "UrlLink");
    this.map.set("aY", "Container");
    this.map.set("linkN", "UrlLink");
    this.map.set("linkY", "UrlLink");
    this.map.set("labelN", "TextBox");
    this.map.set("labelY", "Container");
    this.map.set("inputN", "InputBox");
    this.map.set("textareaN", "InputBox");
    this.map.set("textareaY", "Container");
    this.map.set("imgN", "Image");
    this.map.set("selectN", "Selector");
    this.map.set("buttonN", "Button");
    this.map.set("buttonY", "Button");
    this.map.set("spanN", "Span");
    this.map.set("spanY", "Container");
    this.map.set("formN", "Form");
    this.map.set("formY", "Form");
//    this.map.set("tableN", "Table");
//    this.map.set("tableY", "Table");
    //for table, use Container for the timebeing until we can do the post processing to handle UI templates
    this.map.set("tableN", "Container");
    this.map.set("tableY", "Container");}

UiType.prototype.getType = function(tag, hasChildren) {
    return this.getTypeWithExtra(tag, null, hasChildren);
};

UiType.prototype.getTypeWithExtra = function(tag, extra, hasChildren) {
    var addition = "N";
    if (hasChildren) {
        addition = "Y";
    }
    var uitype = this.map.get(tag + addition);

    if (this.map.get(tag + addition) == null) {
        if (hasChildren) {
            uitype = "Container";
        }
        else {
            uitype = "TextBox";
        }
    }

    if (this.constants.INPUT == tag && extra != null) {
        //            alert("extra : " + extra);
        var type = extra.get(this.constants.TYPE);
        if (type != null) {
            if (this.constants.CHECKBOX == type) {
                uitype = "CheckBox";
            } else if (this.constants.RADIO == type) {
                uitype = "RadioButton";
            } else if (this.constants.SUBMIT == type) {
                uitype = "SubmitButton";
            }
        }

        var img = extra.get(this.constants.IMAGE);
        if(img != null){
            uitype = "Button";         
        }
    }

    return uitype;
}