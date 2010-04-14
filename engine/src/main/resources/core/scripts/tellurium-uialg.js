function TrieNode() {
    //identifier
    this.key = -1;

    //hold the String value for this node
    this.id = null;

    //the data this node holds
    this.data = null;

    //the level of this node in the Trie tree
    this.level = 0;

    //pointer to its parent
    this.parent = null;

    //child nodes
    this.children = new Array();
}

TrieNode.prototype.addChild = function(child) {
    this.children.push(child);
};

TrieNode.prototype.removeArrayAt = function(ar, index )
{
  var part1 = ar.slice( 0, index);
  var part2 = ar.slice( index+1 );

  return( part1.concat( part2 ) );
};

TrieNode.prototype.findIt = function( key )
{
    var result = (-1);

    for( var i = 0; i < this.children.length; i++ )
    {
        if( this.children[i].key == key )
        {
            result = i;
            break;
        }
    }
    return result;
};

TrieNode.prototype.removeChild = function(child) {
    var elementIndex = this.findIt(child.key);

    if( elementIndex != -1 )
    {
        this.children = this.removeArrayAt(this.children, elementIndex);
    }
};

TrieNode.prototype.getChildrenSize = function() {
    return this.children.length;
};

TrieNode.prototype.checkLevel = function() {
    if (this.parent == null)
        this.level = 0;
    else
        this.level = this.parent.level + 1;
    if (this.children.length > 0) {
        for (var i = 0; i < this.children.length; i++) {
            this.children[i].checkLevel();
        }
    }
};

TrieNode.prototype.getFullId = function() {
    if (this.parent == null) {
        return this.id.getUid();
    }

    return this.parent.getFullId() + "." + this.id.getUid();
};

TrieNode.prototype.printMe = function(isTrace) {
    var hasChildren = false;
    if (this.children.length > 0)
        hasChildren = true;
    var sb = new StringBuffer();
    for (var i = 0; i < this.level; i++) {
        sb.append("  ");
    }
    if(this.parent == null){
        sb.append("Trie").append(": ");
    }else{
        sb.append(this.id.getUid()).append("[").append(this.data).append("]");
    }
    if (hasChildren)
        sb.append("{");
    if(isTrace){
        fbLog(sb.toString(), this);
    }else{
        fbLog(sb.toString(), "");
    }
    if (hasChildren) {
        for (var n = 0; n < this.children.length; n++) {
            this.children[n].printMe(isTrace);
        }
    }
    if (hasChildren) {
        var indent = new StringBuffer();
        for (var j = 0; j < this.level; j++) {
            indent.append("  ");
        }
        indent.append("}");
        if(isTrace){
            fbLog(indent.toString(), this);
        }else{
            fbLog(indent.toString(), "");
        }
    }
};

function Trie() {

    this.root = null;

}

Trie.prototype.getKey = function(){
    return tellurium.idGen.next();
};

Trie.prototype.getChildrenData = function(id){
    var result = new Array();
    if(this.root == null || this.root.getChildrenSize() == 0)
        return result;

    var uiid = getUiid(id);

    return this.walk(this.root, uiid, result);
};

function TrieMatch(){
    this.score = 0;
    this.node = null;
}

Trie.prototype.walk = function(current, uiid, result){
    //there are children for the current node
    //check if the new String is a prefix of current node's child or a child is a prefix of the input String,
    var matches = new Array();
    var maxscore = 0;
    var i;
    for (i = 0; i < current.getChildrenSize(); i++) {
        var anode = current.children[i];
        var pm = uiid.matchWith(anode.id);
        var score = pm.length;
        if(score > 0){
            if(maxscore < score)
                maxscore = score;
            var matchresult = new TrieMatch();
            matchresult.score = score;
            matchresult.node = anode;
            matches.push(matchresult);
        }
    }

    //there may be multiple children
    var mchildren = new Array();
    for(i=0; i < matches.length; i++){
        if(matches[i].score == maxscore){
            mchildren.push(matches[i].node);
        }
    }

    //found one child
    if(mchildren.length == 1){
        //the child is the id itself
        if(uiid.size() == mchildren[0].id.size()){
            //return all the children for this node
            for(i=0; i<mchildren[0].getChildrenSize(); i++){
                result.push(mchildren[0].children[i].data);
            }
        }else if(uiid.size() < mchildren[0].id.size()){
            result.push(mchildren[0].data);
        }else{
             //need to do further walk down
            var leftover = uiid.subUiid(mchildren[0].id.size());
            return this.walk(mchildren[0], leftover, result);
        }

    }else if(mchildren.length > 1){
        //more than one children match
        //
        //consider the scenario, the Prie is
        //         Username.Input
        //   Form
        //         Username.Label
        //
        //  and the input String is
        //
        //    Form.Username.Submit
        //
        // In this case, it cannot find any children. But if the input String is,
        //
        //    Form.Username
        //
        // it found both Form.Username.Input and Form.Username.Label
        //

        if(uiid.size() == maxscore){
            //The id is a prefix of the found children
            //That means all children are found
            for(i=0; i<mchildren.length; i++){
                result.push(mchildren[i].data);
            }
        }
        //otherwise, treat it as not found
    }

    return result;
};

Trie.prototype.insert = function(id, data) {
    var uiid = getUiid(id);
    if (this.root == null) {
        //If it is the first time to insert an word to the Tire
        this.root = new TrieNode();
        //root is an empty String, more like a logic node
        this.root.id = "";
        this.root.level = 0;
        this.root.parent = null;
        this.root.key = this.getKey();

        //add the word as the child of the root node
        var child = new TrieNode();

        child.id = uiid;
        child.data = data;
        child.parent = this.root;
        child.key = this.getKey();
        this.root.addChild(child);
    } else {
        //not the first node, need to walk all the way down to find a place to insert
        this.build(this.root, uiid, data);
    }
};

Trie.prototype.build = function(current, uiid, data) {
    //look at current node's children
    if (current.getChildrenSize() == 0) {
        //no child yet, add itself as the first child
        var child = new TrieNode();
        child.id = uiid;
        child.data = data;
        child.parent = current;
        child.key = this.getKey();
        current.addChild(child);
    } else {
        //there are children for the current node
        //check if the new String is a prefix of a set of children
        var common = new Array();
        for (var i = 0; i < current.getChildrenSize(); i++) {
            var anode = current.children[i];
//            if (anode.id.startsWith(id)) {
            if(anode.id.matchWith(uiid).length == uiid.size()){
                common.push(anode);
            }
        }
        //if the new String is indeed a prefix of a set of children
        if (common.length > 0) {
            var shared = new TrieNode();
            shared.id = uiid;
            shared.data = data;
            shared.parent = current;
            shared.key = this.getKey();
            for (var j = 0; j < common.length; j++) {
                var node = common[j];
                //assume no duplication in the tree, otherwise, need to consider the empty string case for a child
                node.id = node.id.subUiid(uiid.size());
                node.parent = shared;
                shared.addChild(node);
                current.removeChild(node);
            }
            current.addChild(shared);
        } else {
            //no common prefix available, then check if the child is a prefix of the input String
            var found = false;
            var next = null;
            for (var k = 0; k < current.getChildrenSize(); k++) {
                var pnode = current.children[k];
//                if (uiid.startsWith(pnode.id)) {
                if(uiid.matchWith(pnode.id).length == pnode.id.size()){
                    found = true;
                    next = pnode;
                    break;
                }
            }
            if (found) {
                //not a duplication, otherwise, do nothing
                if (uiid.size() != next.id.size()) {
                    var leftover = uiid.subUiid(next.id.size());
                    this.build(next, leftover, data);
                }
            } else {
                //not found, need to create a new node a the child of the current node
                var achild = new TrieNode();
                achild.parent = current;
                achild.id = uiid;
                achild.data = data;
                achild.key = this.getKey();
                current.addChild(achild);
            }
        }
    }
};

Trie.prototype.checkLevel = function() {
    if (this.root != null) {
        this.root.checkLevel();
    }
};

Trie.prototype.printMe = function() {
    if (this.root != null) {
        fbLog("---------------------------- Trie/Prefix Tree ----------------------------\n", "");
        this.root.printMe(false);
        fbLog("--------------------------------------------------------------------------\n", "");
    }
};

Trie.prototype.dumpMe = function() {
    if (this.root != null) {
        fbLog("---------------------------- Trie/Prefix Tree ----------------------------\n", this);
        this.root.printMe(true);
        fbLog("--------------------------------------------------------------------------\n", this);
    }
};

function RelaxDetail(){
    //which UID got relaxed, i.e., closest Match
    this.uid = null;
    //the clocator defintion for the UI object corresponding to the UID
    this.locator = null;
    //The actual html source of the closest match element
    this.html = null;
}


//algorithms to handle UI modules and UI Objects
function UiAlg(){
    //current root DOM element
    this.dom = null;

    this.colors = {
        WHITE: "white",
        GRAY: "gray",
        BLACK: "black"
    };

    this.currentColor = null;

    //whether allow to use closest matching element if no one matches
    this.allowRelax = false;

    //FIFO queue to hold UI snapshots
    this.squeue = new FifoQueue();

    //FIFO queue to hold UI objects in the UI module
    this.oqueue = new FifoQueue();

    //jQuery builder to build CSS selectors
    this.cssbuilder = new JQueryBuilder();

    //array to hold all marked data("uid) so that we can remove them later
    this.uidset = new Array();
}

UiAlg.prototype.clear = function(){
    this.dom = null;
    this.currentColor = this.colors.WHITE;
    this.squeue.clear();
    this.oqueue.clear();
    this.uidset = new Array();
};

//remove all the marked data("uid")
UiAlg.prototype.unmark = function(){
    if(this.uidset != null){
        for(var i=0; i< this.uidset.length; i++){
            !tellurium.logManager.isUseLog || fbLog("Unmarking uid " + this.uidset[i].data("uid"), this.uidset[i]);
            this.uidset[i].removeData("uid");
        }
    }
};

UiAlg.prototype.nextColor = function(){
    if(this.currentColor == null){
        return this.colors.GRAY;
    }else{
        if(this.currentColor == this.colors.GRAY){
            return this.colors.BLACK;
        }

        return this.colors.GRAY;
    }
};

UiAlg.prototype.locateInAllSnapshots = function(uiobj){
    var finished = false;
    !tellurium.logManager.isUseLog || fbLog("Initial snapshot queue in LocateInAllSnapshots", this.squeue);
    while(!finished && this.squeue.size() > 0){
        var first = this.squeue.peek();
        //check the first element color
        if(this.currentColor == first.color){
            first = this.squeue.pop();
            if(uiobj.locator != null){
                this.locate(uiobj, first);
            }else{
                var ncolor = this.nextColor();
                first.setColor(ncolor);
                this.squeue.push(first);
            }
        }else{
            //exit when the snapshot color is marked for the next round
            finished = true;
            this.currentColor = this.nextColor();
            if(this.squeue.size() == 0){
                fbError("Cannot find UI element " + uiobj.uid, uiobj);
                throw new SeleniumError("Cannot find UI element " + uiobj.uid);
            }
        }
    }
};

UiAlg.prototype.lookId = function(uiobj, $found){
    var ids = uiobj.getChildrenIds();
    if(ids != null && ids.length > 0){
         var gsel = new Array();
         for(var c=0; c < ids.length; c++){
             gsel.push(this.buildIdSelector(ids[c]));
         }
         var result = new Array();
         for(var i=0; i<$found.size(); i++){
             if(this.hasChildren($found.get(i), gsel)){
                 result.push($found.get(i));
             }
         }

         return teJQuery(result);
    }

    return $found;
};

UiAlg.prototype.lookAhead = function(uiobj, $found){
    var children = uiobj.lookChildren();

    if(children != null && children.length > 0){
        var gsel = new Array();
        for(var c=0; c < children.length; c++){
            if(children[c].locator != null)
                gsel.push(this.buildSelector(children[c].locator));
        }
        var result = new Array();
        for(var i=0; i<$found.size(); i++){
            if(this.hasChildren($found.get(i), gsel)){
                result.push($found.get(i));
            }
        }

        return teJQuery(result);
    }

    return $found;
};

UiAlg.prototype.calcBonus = function(one, gsel){
    var bonus = 0;
    var $me = teJQuery(one);
    for(var i=0; i<gsel.length; i++){
        if($me.find(gsel[i]).size() > 0){
            bonus++;
        }
    }

    return bonus;
};

UiAlg.prototype.hasChildren = function(one, gsel){
    var result = true;
    var $me = teJQuery(one);
    for(var i=0; i<gsel.length; i++){
        result = result && ($me.find(gsel[i]).size() > 0);
    }

    return result;
};

UiAlg.prototype.buildIdSelector = function(id){
    return this.cssbuilder.buildIdSelector(id);
};

UiAlg.prototype.buildSelector = function(clocator){
    //TODO: need to add header and trailer to the selector if necessary
    return this.cssbuilder.buildCssSelector(clocator.tag, clocator.text, clocator.position, clocator.direct, clocator.attributes);
};

UiAlg.prototype.buildSelectorWithPosition = function(clocator, position){
    //TODO: need to add header and trailer to the selector if necessary
    return this.cssbuilder.buildCssSelector(clocator.tag, clocator.text, position, clocator.direct, clocator.attributes);
};

//TODO: may need to pass in more attributes other than clocator, for instance, the separator in the List object
//TODO: need to consider the Repeat object when calling locate
UiAlg.prototype.locate = function(uiobj, snapshot){
    var uid = uiobj.fullUid();
    var clocator = uiobj.locator;

    //the next color to label the snapshot
    var ncolor = this.nextColor();
    //first find its parent uid
    var vp = this.getValidParentFor(uiobj);
    var puid = null;
    if(vp != null)
        puid = vp.fullUid();

    var pref = null;
    if(puid != null){
        pref = snapshot.getUi(puid);
    }else{
        pref = this.dom;
    }

    //build the CSS selector from the current element's composite locator
    var csel = this.buildSelector(clocator);
    var $found = teJQuery(pref).find(csel);
    var foundWithoutLookAhead = false;
    if($found.size() > 0){
        foundWithoutLookAhead = true;
    }
    //if multiple matches, need to narrow down by looking ahead at the UI object's children
    if($found.size() > 1){
        if(uiobj.noCacheForChildren){
            //if there is no cache for children for UI object uiobj
            $found = this.bestEffort(uiobj, $found);
            !tellurium.logManager.isUseLog || fbLog("UI object has no cache for children, best guess result for UI object " + uiobj.uid, $found.get());
        }else{
            //first try lookId
            $found = this.lookId(uiobj, $found);
            !tellurium.logManager.isUseLog || fbLog("Look Id result for " + uiobj.uid, $found.get());
            if($found.size() > 1){
                $found = this.lookAhead(uiobj, $found);
                !tellurium.logManager.isUseLog || fbLog("Look ahead result for " + uiobj.uid, $found.get());
            }
        }
    }

    //found any nodes in the DOM by using the
    if($found.size() == 1){
        //found exactly one, happy path
        //temporally assign uid to the found element
        !tellurium.logManager.isUseLog || fbLog("Marking uid " + uid, $found.eq(0));
        $found.eq(0).data("uid", uid);
        snapshot.addUi(uid, $found.get(0));
        //store all the elements with data("uid")
        this.uidset.push($found.eq(0));
        snapshot.setColor(ncolor);
        snapshot.score += 100;
        snapshot.nelem++;
        this.squeue.push(snapshot);
    }else if($found.size() > 1){
        //multiple results, need to create more snapshots to expend the search
        for (var i = 1; i < $found.size(); i++){
            //check if the element has the "uid" in data, if not try to clone it
            if ($found.eq(i).data("uid") == undefined){
                var newsnapshot = snapshot.clone();
                newsnapshot.addUi(uid, $found.get(i));
                newsnapshot.setColor(ncolor);
                newsnapshot.score += 100;
                newsnapshot.nelem++;
                this.squeue.push(newsnapshot);
            }
        }
        //still need the push back the orignail snapshot
        if ($found.eq(0).data("uid") == undefined) {
            snapshot.addUi(uid, $found.get(0));
            snapshot.setColor(ncolor);
            snapshot.score += 100;
            snapshot.nelem++;
            this.squeue.push(snapshot);
        }
    }else{
        !tellurium.logManager.isUseLog || fbLog("allowRelax: ", this.allowRelax);
        //if allow us to relax the clocator/attribute constraints and use the closest matching ones instead
        if(this.allowRelax){
            var result = this.relax(clocator, pref);
            var $relaxed = result.closest;

            if ($relaxed.size() > 1) {
                $relaxed = this.lookAheadClosestMatchChildren(uiobj, $relaxed, result);
            }

            if($relaxed.size() == 1){
                //found exactly one
                //temporally assign uid to the found element
                !tellurium.logManager.isUseLog || fbLog("Marking closest match for uid " + uid, $relaxed.get(0));
                $relaxed.eq(0).data("uid", uid);

                if (!foundWithoutLookAhead) {
                    //get the relaxed details
                    var rdz = new RelaxDetail();
                    rdz.uid = uid;
                    rdz.locator = clocator;
                    rdz.html = $relaxed.eq(0).outerHTML();
                    snapshot.relaxed = true;
                    snapshot.relaxDetails.push(rdz);
                }

                snapshot.addUi(uid, $relaxed.get(0));
                snapshot.setColor(ncolor);
                snapshot.score += result.score;
                snapshot.nelem++;
                this.squeue.push(snapshot);
            }else if($relaxed.size() > 1){
                //multiple results, need to create more snapshots to expend the search
                for (var j = 1; j < $relaxed.size(); j++) {
                    if ($relaxed.eq(i).data("uid") == undefined) {

                        var nsnapshot = snapshot.clone();

                        if (!foundWithoutLookAhead) {
                            //get the relaxed details
                            var rdi = new RelaxDetail();
                            rdi.uid = uid;
                            rdi.locator = clocator;
                            rdi.html = $relaxed.eq(i).outerHTML();
                            nsnapshot.relaxed = true;
                            nsnapshot.relaxDetails.push(rdi);
                        }

                        nsnapshot.addUi(uid, $relaxed.get(j));
                        nsnapshot.setColor(ncolor);
                        nsnapshot.score += result.score;
                        nsnapshot.nelem++;
                        this.squeue.push(nsnapshot);
                    }
                }
                //still need the push back the orignail snapshot
                if ($relaxed.eq(0).data("uid") == undefined) {

                    if (!foundWithoutLookAhead) {
                        //get the relaxed details
                        var rdf = new RelaxDetail();
                        rdf.uid = uid;
                        rdf.locator = clocator;
                        rdf.html = $relaxed.eq(0).outerHTML();
                        snapshot.relaxed = true;
                        snapshot.relaxDetails.push(rdf);
                    }

                    snapshot.addUi(uid, $relaxed.get(0));
                    snapshot.setColor(ncolor);
                    snapshot.score += result.score;
                    snapshot.nelem++;
                    this.squeue.push(snapshot);
                }
            }else{
                //otherwise, throw exception
//                throw new SeleniumError("Cannot find UI element " + uid);
                //do not throw exception and do not push the snapshot back to the queue instead
            }
        }else{
            //otherwise, throw exception
//            throw new SeleniumError("Cannot find UI element " + uid);
            //do not throw exception and do not push the snapshot back to the queue instead
        }
    }
};

function MatchResult(){
    //the closest match element
    this.closest = null;

    //scaled match score, 0 - 100, or 100 percentage
    this.score = 0;

    //bonus points for best guess when handle UI templates because each template may not be presented at runtime
//    this.bonus = 0;
}

UiAlg.prototype.relax = function(clocator, pref) {
    var attrs = new Hashtable();
    if(clocator.text != null && clocator.text.trim().length > 0){
        attrs.put("text", clocator.text);
    }
    if(clocator.position != null){
        attrs.put("position", clocator.position);
    }

    var id = null;
    if (clocator.attributes != undefined) {
        for (var key in clocator.attributes) {
            if (!this.cssbuilder.inBlackList(key)) {
                attrs.put(key, clocator.attributes[key]);
            }
        }

        id = clocator.attributes["id"];
    }
    var jqs = "";
    var tag = clocator.tag;

    if (tag == null || tag == undefined || tag.trim().length == 0) {
        //TODO: need to double check if this is correct or not in jQuery
        tag = "*";
    }

    var result = new MatchResult();
    //Use tag for the initial search
    var $closest = teJQuery(pref).find(tag);
    if (id != null && id != undefined && (!this.cssbuilder.isPartial(id))) {
        jqs = this.cssbuilder.buildId(id);
        $closest = teJQuery(pref).find(jqs);
        //Because ID is unique, if ID matches, ignore all others and assign it a big value
        if($closest.size() > 0){
            result.score = 100;
            result.closest = $closest;
        }
        !tellurium.logManager.isUseLog || fbLog("Scaled Matching Score: " + result.score, result);

        return result;
    } else {
        jqs = tag;
        var keys = attrs.keySet();

        //number of properties, tag must be included
        var np = 1;
        //number of matched properties
        var nm = 0;

        if (keys != null && keys.length > 0) {
            np = np + keys.length;
            for (var m = 0; m < keys.length; m++) {
                var attr = keys[m];
                var tsel = this.cssbuilder.buildSelector(attr, attrs.get(attr));
                var $mt = teJQuery(pref).find(jqs + tsel);
                if ($mt.size()> 0) {
                    $closest = $mt;
                    result.closest = $closest;
                    jqs = jqs + tsel;
                    if(nm == 0){
                        nm = 2;
                    }else{
                        nm++;
                    }
                }
            }
        }

        if($closest.size() > 0){
             nm = 1;
             result.closest = $closest;
        }

        //calculate matching score, scaled to 100 percentage
        result.score = 100*nm/np;
        !tellurium.logManager.isUseLog || fbLog("Scaled Matching Score: " + result.score, result);

        return result;
    }
};

UiAlg.prototype.hasClosestMatchChildren = function(one, clocators){
    var score = 0;
    for(var i=0; i<clocators.length; i++){
        var result = this.relax(clocators[i], one);
        if(result.closest == null || result.closest.size() == 0){
            score = 0;
            break;
        }else{
            score = score + result.score;
        }
    }

    return score;
};

UiAlg.prototype.lookAheadClosestMatchChildren = function(uiobj, $found, matchresult){
    var children = uiobj.lookChildren();

    if(children != null && children.length > 0){
        var clocators = new Array();
        for(var c=0; c < children.length; c++){
            clocators.push(children[c].locator);
        }
        var result = new Array();
        var max = 0;
        var closest = null;
        for(var i=0; i<$found.size(); i++){
            var score = this.hasClosestMatchChildren($found.get(i), clocators);
            if(score > 0){
                if(max < score){
                    //try to find the higest matches, for tied condition, i.e., multiple highest matches, select the first one
                    max = score;
                    closest = $found.get(i);
                }
            }
        }

        if(closest != null){
            //average the score over the element and its children
            matchresult.score = (matchresult.score + max)/(children.length + 1);
            matchresult.closest = closest;
            result.push(closest);
        }

        return teJQuery(result);
    }

    return $found;
};

UiAlg.prototype.bestEffort = function(uiobj, $found){
    //Implement bestGuess() for UI templates
    var children = uiobj.lookChildrenNoMatterWhat();

    if(children != null && children.length > 0){
        var gsel = new Array();
        for(var c=0; c < children.length; c++){
            gsel.push(this.buildSelector(children[c].locator));
        }

        //calculate bonus point for each element first
        var bonusArray = new Array();
        var maxbonus = 0;
        for(var i=0; i<$found.size(); i++){
            var bonus = this.calcBonus($found.get(i), gsel);
            bonusArray.push(bonus);
            if(bonus > maxbonus){
                maxbonus = bonus;
            }
        }
        !tellurium.logManager.isUseLog || fbLog("calculated bonus points for " + uiobj.uid + "'s children", bonusArray);
        var result = new Array();

        for(var j=0; j<$found.size(); j++){
            if(bonusArray[j] == maxbonus){
                result.push($found.get(j));
            }
        }

        !tellurium.logManager.isUseLog || fbLog("Get Best Guess result for " + uiobj.uid, result);
        return teJQuery(result);
    }
    return $found;
};

UiAlg.prototype.addChildUiObject = function(uiobj){
    this.oqueue.push(uiobj);
};

UiAlg.prototype.getParentUid = function(uid){
    var indx = uid.lastIndexOf(".");
    if(indx != -1){
        return uid.slice(0, indx);
    }

    return null;
};

UiAlg.prototype.getValidParentFor = function(uiobj){
    var validParent = uiobj.parent;

    while(validParent != null){
        if(validParent.locator == null){
             //walk up if the parent is a logical container
            validParent = validParent.parent;
        }else{
            break;
        }
    }

    return validParent;
};

//traverse the UI module to build a snapshot tree
UiAlg.prototype.buildSTree = function(uimodule){
    this.clear();
    var root = uimodule.root;
    if(root == null){
        fbWarn("Root for UI Module is null", uimodule);
        return null;
    }
    var tree = new UiSTree();
    tree.uimRef = uimodule;
    //start from the root element in the UI module

    var first = new UiSData(null, root.uid, root, root.domRef);
    this.oqueue.push(first);
    while(this.oqueue.size() > 0){
        var sdata = this.oqueue.pop();
        !tellurium.logManager.isUseLog || fbLog("Traverse for Object " + sdata.rid + ": ", sdata);
        var context = new WorkflowContext();
        context.alg = this;
        context.domRef = sdata.domRef;
        var node = sdata.objRef.buildSNode(context, sdata.pid, sdata.rid, sdata.domRef);
        tree.insert(context, node);
    }

    return tree;
};

//
//The santa algorithm, i.e., Tellurium UI module group locating algorithm
//
//The santa name comes from the fact that I designed and finalized the algorithm
//during the Christmas season in 2009, which is a gift for me from Santa Claus
//
// by Jian Fang (John.Jian.Fang@gmail.com)
//
UiAlg.prototype.santa = function(uimodule, rootdom){
    !tellurium.logManager.isUseLog || fbLog("call santa algorithm for UI Module ", uimodule);
    this.clear();
    if(rootdom != null){
        this.dom = rootdom;
    }else{
        //try to find the current html body.
        // TODO: not very elegant, need to refactor this later
//        this.dom = selenium.browserbot.findElement("/html/body");
//        this.dom = selenium.browserbot.findElement("jquery=html > body");
        this.dom = selenium.browserbot.findElement("jquery=html");
    }
    this.currentColor = this.colors.GRAY;
    //start from the root element in the UI module
    if(!uimodule.root.lazy){
        this.oqueue.push(uimodule.root);
        var ust = new UiSnapshot();
        ust.color = this.colors.GRAY;
        this.squeue.push(ust);
    }else{
        fbWarn("Ui Module " + uimodule.root.uid + " is not cachable", uimodule.root);
        return false;
    }
    !tellurium.logManager.isUseLog || fbLog("UiAlg states before group locating: ", this);
    !tellurium.logManager.isUseLog || fbLog("Initial object queue ", this.oqueue);
    !tellurium.logManager.isUseLog || fbLog("Initial snapshot queue ", this.squeue);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        !tellurium.logManager.isUseLog || fbLog("Take snapshot for Object " + uiobj.uid + ": ", uiobj);
        uiobj.locate(this);
    }
    if(this.squeue.size() == 0){
        fbError("Cannot locate UI module " +  uimodule.root.uid, uimodule);
        throw new SeleniumError("Cannot locate UI module " +  uimodule.root.uid);
    }

    //if allow closest match
    if (this.allowRelax) {
        !tellurium.logManager.isUseLog || fbLog("Found " + this.squeue.size() + " matches for UI module " + uimodule.root.uid, this.squeue);
        uimodule.matches = this.squeue.size();
        //use match score to select the best match
        var snapshot = this.squeue.pop();
        var maxscore = snapshot.getScaledScore();
        while (this.squeue.length > 0) {
            var nsnapshot = this.squeue.pop();
            var nscore = nsnapshot.getScaledScore();
            if (nscore > maxscore) {
                snapshot = nsnapshot;
                maxscore = nscore;
            }
        }

        !tellurium.logManager.isUseLog || fbLog("Found UI Module " + uimodule.root.uid + " successfully. ", snapshot);
        this.bindToUiModule(uimodule, snapshot);
        this.unmark();
    } else {
        //for exact match, cannot have multiple matches
        if (this.squeue.size() > 1) {
            fbError("Found " + this.squeue.size() + " matches for UI module " + uimodule.root.uid, this.squeue);
            throw new SeleniumError("Found " + this.squeue.size() + " matches for UI module " + uimodule.root.uid);
        }
        //found only one snapshot, happy path
        var osnapshot = this.squeue.pop();
        !tellurium.logManager.isUseLog || fbLog("Found UI Module " + uimodule.root.uid + " successfully. ", osnapshot);
        this.bindToUiModule(uimodule, osnapshot);
        this.unmark();
        uimodule.matches = 1;
    }

    return true;
};

UiAlg.prototype.validate = function(uimodule, rootdom){
    this.clear();
    var relaxflag = this.allowRelax;
    this.allowRelax = true;
    if(rootdom != null){
        this.dom = rootdom;
    }else{
        //try to find the current html body.
        // TODO: not very elegant, need to refactor this later
//        this.dom = selenium.browserbot.findElement("/html/body");
//        this.dom = selenium.browserbot.findElement("jquery=html > body");
        this.dom = selenium.browserbot.findElement("jquery=html");
    }
    this.currentColor = this.colors.GRAY;
    //start from the root element in the UI module
    this.oqueue.push(uimodule.root);
    var ust = new UiSnapshot();
    ust.color = this.colors.GRAY;
    this.squeue.push(ust);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        !tellurium.logManager.isUseLog || fbLog("Take snapshot for Object " + uiobj.uid + ": ", uiobj);
        uiobj.locate(this);
    }
    if(this.squeue.size() == 0){
        fbError("Cannot locate UI module " +  uimodule.root.uid, uimodule);
        uimodule.matches = 0;
    }
    if (this.squeue.size() >= 1) {
        !tellurium.logManager.isUseLog || fbLog("Found " + this.squeue.size() + " matches for UI module " + uimodule.root.uid, this.squeue);
        uimodule.matches = this.squeue.size();
        //use match score to select the best match
        var snapshot = this.squeue.pop();
        var maxscore = snapshot.getScaledScore();
        while (this.squeue.length > 0) {
            var nsnapshot = this.squeue.pop();
            var nscore = nsnapshot.getScaledScore();
            if (nscore > maxscore) {
                snapshot = nsnapshot;
                maxscore = nscore;
            }
        }

        !tellurium.logManager.isUseLog || fbLog("Found UI Module " + uimodule.root.uid + " successfully. ", snapshot);
        this.bindToUiModule(uimodule, snapshot);
        this.unmark();
    }
    this.allowRelax = relaxflag;
};

UiAlg.prototype.bindToUiModule = function(uimodule, snapshot){
    this.oqueue.clear();
    this.oqueue.push(uimodule.root);
    while(this.oqueue.size() > 0){
        var uiobj = this.oqueue.pop();
        uiobj.bind(snapshot, this);
    }
    //add index to Ui Module for uid to dom reference reference for fast access
    !tellurium.logManager.isUseLog || fbLog("Adding uid to dom reference indices for UI module " + uimodule.root.uid, snapshot.elements);
    uimodule.indices = snapshot.elements;
    uimodule.relaxed = snapshot.relaxed;
    uimodule.relaxDetails = snapshot.relaxDetails;
    uimodule.score = snapshot.getScaledScore();
};
