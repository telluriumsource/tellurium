function PriorityQueue() {
   this.priorityArray = [];
    //{node : node, priority : common prefix length}
}

    // "public"
PriorityQueue.prototype.insert = function (node, priority) {
    var i = 0;
    while (i <= this.priorityArray.length && this.priorityArray.length > 0 && priority < (this.priorityArray[i]).priority ) {
        i++;
    }
    this.priorityArray.splice(i, 0, {"xpathObject": node, "priority": priority});
    return true;
}

PriorityQueue.prototype.remove = function () {
    var element;
    if(this.priorityArray && this.priorityArray.length > 0 ){
        element = this.priorityArray.splice(0, 1);
        if(element && element.length == 1){
            return element[0].xpathObject;
        }
    }
}

PriorityQueue.prototype.get = function () {
    return this.priorityArray.shift() ;
}

PriorityQueue.prototype.peek = function () {
    return this.priorityArray[0] ;
}

PriorityQueue.prototype.size = function () {
    return this.priorityArray.length ;
}

PriorityQueue.prototype.display = function () {
    var s = "";
    for(var i=0; i< this.priorityArray.length ; ++i){
        s+= "node : " + this.priorityArray[i].xpathObject +" priority : "+ this.priorityArray[i].priority +"\n";
    }
    return s ;
}
