/*
 * LogView
 */

function LogView(editor){
    this.name = "log";
    this.changeLogLevel("1"); // INFO
	this.view = document.getElementById("logView");
    var self = this;
	this.view.addEventListener("load", function() { self.reload() }, true);
}


LogView.prototype.show = function() {
    document.getElementById("logButtons").hidden = false;
}

LogView.prototype.hide = function() {
    document.getElementById("logButtons").hidden = true;
}

LogView.prototype.setLog = function(log) {
    this.log = log;
    log.observers.push(this);
}

LogView.prototype.changeLogLevel = function(level, reload) {
    var filterElement = document.getElementById("logFilter");
    var popup = document.getElementById("logFilterPopup");
    this.filterValue = level;
    var i;
    for (i = 0; i < popup.childNodes.length; i++) {
        var node = popup.childNodes[i];
        if (level == node.value) {
            filterElement.label = node.label;
            break;
        }
    }

    if (reload) {
        this.reload();
    }
}

LogView.prototype.getLogElement = function() {
	return this.view.contentDocument.getElementById("log");
}

LogView.prototype.isHidden = function() {
	return this.view.hidden || this.getLogElement() == null;
}

LogView.prototype.clear = function() {
    if (!this.isHidden() && this.log) {
        this.log.clear();
    }
}

LogView.prototype.onClear = function() {
	if (!this.isHidden()) {
		var nodes = this.getLogElement().childNodes;
		var i;
		for (i = nodes.length - 1; i >= 0; i--) {
			this.getLogElement().removeChild(nodes[i]);
		}
	}
}

LogView.prototype.reload = function() {
	if (!this.isHidden() && this.log) {
		var self = this;
		this.onClear();
		this.log.entries.forEach(function(entry) { self.onAppendEntry(entry); });
	}
}

LogView.prototype.onAppendEntry = function(entry) {
    var levels = { debug: 0, info: 1, warn: 2, error: 3 };
    var entryValue = levels[entry.level];
    var filterValue = parseInt(this.filterValue);
    if (filterValue <= entryValue) {
        if (!this.isHidden()) {
			var newEntry = this.view.contentDocument.createElement('li');
			newEntry.className = entry.level;
			newEntry.appendChild(this.view.contentDocument.createTextNode(entry.line()));
			this.getLogElement().appendChild(newEntry);
			newEntry.scrollIntoView();
        } 
    }
}