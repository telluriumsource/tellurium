function Locator(){
    this.constants = {
        TAG : "tag",
        TEXT : "text",
        HEADER : "header",
        TRAILER: "trailer",
        POSITION: "position"
    };
    this.header = null;
    this.tag = null;
    this.text = null;
    this.trailer = null;
    this.position = null;
    this.attributes = new HashMap();
    this.direct = false;
    this.xmlutil = new XmlUtil();
}

Locator.prototype.buildLocator = function(attributes){
    if(attributes != null && attributes.size() > 0){
        this.tag = attributes.get(this.constants.TAG);
        this.text = attributes.get(this.constants.TEXT);
        this.position = attributes.get(this.constants.POSITION);
        var header = attributes.get(this.constants.HEADER);
        if (header != null){
            this.header = header;
        }
        var trailer = attributes.get(this.constants.TRAILER);
        if(trailer != null){
            this.trailer = trailer;
        }

        this.buildLocatorFromAttributes(attributes);
    }

    return this;
}

Locator.prototype.buildLocatorFromAttributes = function(attributes) {
    if (attributes != null && attributes.size() > 0) {
        var keys = attributes.keySet();
        for (var i = 0; i < keys.length; i++) {
            var key = keys[i];
            //            if(key != this.constants.TAG && key != this.constants.TEXT && key != this.constants.POSITION && key != this.constants.HEADER && key != this.constants.TRAILER){
            if (key != this.constants.TAG && key != this.constants.HEADER && key != this.constants.TRAILER && key != this.constants.TEXT) {
                var value = attributes.get(key);
                if (value != null && trimString(value).length > 0) {
                    this.attributes.set(key, value);
                }
            }
        }
    }
}

Locator.prototype.updateLocator = function(attributes){
     this.attributes = new HashMap();

     if(attributes != null && attributes.size() > 0){
        //neend to unescape attributes for attributes getting from xml
        var esAttributes = new HashMap();
        var keys = attributes.keySet();
        for(var i=0; i<keys.length; i++){
            var key = keys[i];
            var val = this.xmlutil.escapedCharacterProof(attributes.get(key));
            esAttributes.set(key, val);
        }

        this.text = esAttributes.get(this.constants.TEXT);
        this.position = esAttributes.get(this.constants.POSITION);
        this.header =  esAttributes.get(this.constants.HEADER);
        this.trailer = esAttributes.get(this.constants.TRAILER);

        this.buildLocatorFromAttributes(esAttributes);
    }

    return this;
}

Locator.prototype.isAttributeIncluded = function(attribute){

    if(attribute == this.constants.TEXT && this.text != null && trimString(this.text).length > 0){
        return true;
    }
    if(attribute == this.constants.TRAILER && this.trailer != null && trimString(this.trailer).length > 0){
        return true;
    }
    if(attribute == this.constants.HEADER && this.header != null && trimString(this.header).length > 0){
        return true;
    }

    return (this.attributes.get(attribute) != null);
}

Locator.prototype.addAttribute = function(key, value) {
    this.attributes.set(key, value);
}

Locator.prototype.removeAttribute = function(key) {
    this.attributes.remove(key);
}

Locator.prototype.attributesToString = function(){
    var sb = new StringBuffer();

    if(this.attributes.size() >0 ){
        var keys = this.attributes.keySet();
        for(var i=0; i< keys.length; i++){
            if(keys[i] != this.constants.TEXT && keys[i] != this.constants.POSITION && keys[i] != this.constants.HEADER && keys[i] != this.constants.TRAILER){
                sb.append(", ").append(keys[i]).append(": ").append("\"").append(this.attributes.get(keys[i])).append("\"");
            }
        }
    }

    return sb.toString();
}

Locator.prototype.descAttributes = function(){
    var sb = new StringBuffer();

    if(this.attributes.size() >0 ){
        var keys = this.attributes.keySet();
        for(var i=0; i< keys.length; i++){
            if(keys[i] != this.constants.TEXT && keys[i] != this.constants.POSITION && keys[i] != this.constants.HEADER && keys[i] != this.constants.TRAILER){
                sb.append(", ").append(keys[i]).append(": '").append(this.xmlutil.specialCharacterProof(this.attributes.get(keys[i]))).append("'");
            }
        }

    }

    return sb.toString();
}

Locator.prototype.strLocator = function(){
    var sb = new StringBuffer();

    sb.append("clocator: [");
    if(this.header == null && this.tag == null && this.text == null && this.trailer == null && this.position == null && this.attributes.size() ==0){
        //if empty locator
        sb.append(":");
    }else{
        var count = 0;
        if(this.tag != null && this.tag.length > 0){
            sb.append(this.constants.TAG).append(": ").append("\"").append(this.tag).append("\"");
            ++count;
        }
        if(this.text != null && this.text.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.TEXT).append(": ").append("\"").append(this.text).append("\"");
            ++count;
        }
        if(this.position != null){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.POSITION).append(": ").append("\"").append(this.position).append("\"");
            ++count;
        }
        if(this.header != null && trimString(this.header).length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.HEADER).append(": ").append("\"").append(this.header).append("\"");
            ++count;
        }
        if(this.trailer != null && trimString(this.trailer).length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.TRAILER).append(": ").append("\"").append(this.trailer).append("\"");
            ++count;
        }
        if (this.direct) {
            if(count > 0){
                sb.append(", ");
            }
            sb.append("direct: \"true\"");
            ++count;
        }
        if(this.attributes != null && this.attributes.size() > 0){
            sb.append(this.attributesToString())
        }

    }
    sb.append("]")

    return sb.toString();
}

Locator.prototype.descLocator = function(){
    var sb = new StringBuffer();

    sb.append("clocator: [");
    if(this.header == null && this.tag == null && this.text == null && this.trailer == null && this.position == null && this.attributes.size() ==0){
        //if empty locator
        sb.append(":");
    }else{
        var count = 0;
        if(this.tag != null && this.tag.length > 0){
            sb.append(this.constants.TAG).append(": '").append(this.tag).append("'");
            ++count;
        }
        if(this.text != null && this.text.length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.TEXT).append(": '").append(this.xmlutil.specialCharacterProof(this.text)).append("'");
            ++count;
        }
        if(this.position != null){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.POSITION).append(": '").append(this.position).append("'");
            ++count;
        }
        if(this.header != null && trimString(this.header).length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.HEADER).append(": '").append(this.header).append("'");
            ++count;
        }
        if(this.trailer != null && trimString(this.trailer).length > 0){
            if(count > 0){
                sb.append(", ");
            }
            sb.append(this.constants.TRAILER).append(": '").append(this.trailer).append("'");
            ++count;
        }
        if (this.direct) {
            if(count > 0){
                sb.append(", ");
            }
            sb.append("direct: 'true'");
            ++count;
        }

        if(this.attributes != null && this.attributes.size() > 0){
            sb.append(this.descAttributes())
        }
    }
    sb.append("]")

    return sb.toString();
}

Locator.prototype.setHeader = function(header){
    this.header = header;
}

Locator.prototype.setTag = function(tag){
    this.tag = tag;
}

Locator.prototype.setText = function(text){
    this.text = text;
}

Locator.prototype.setTrailer = function(trailer){
    this.trailer = trailer;
}

Locator.prototype.setPosition = function(pos){
    this.position = pos;
}