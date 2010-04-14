//composite type
var firstChoiceTags = ["form", "table", "frame", "iframe"]
//list type
var secondChoiceTags = ["tr", "th", "ul", "dl", "ol"]
//possible list type
var thirdChoiceTags = ["td", "dt", "li","a", "span", "div"]
//single element type
var fourthChoiceTags = ["button", "img", "input", "select", "tt" ]

function TagState() {


}

TagState.prototype.includeTag = function(tagList, tag){
    if(tag != null && tagList != null){
        for(var i=0; i<tagList.length; i++){
            if(tag == tagList[i]){
                return true;
            }
        }
    }

    return false;
}

TagState.prototype.selectOneTag = function(choiceTags, tags){
    for (var i = 0; i < tags.length; i++) {
        if (this.includeTag(choiceTags, tags[i])) {

            return tags[i];
        }
    }

    return null;
}

TagState.prototype.selectTagByPriority = function(tags){
    if(tags != null){
        var tag = this.selectOneTag(firstChoiceTags, tags);
        if(tag != null)
            return tag;
        tag = this.selectOneTag(secondChoiceTags, tags);
        if(tag != null)
            return tag;
        tag = this.selectOneTag(thirdChoiceTags, tags);
        if(tag != null)
            return tag;
        tag = this.selectOneTag(fourthChoiceTags, tags);

        return tag;
    }

    return null;
}