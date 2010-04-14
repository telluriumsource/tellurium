function TrumpLogger() {
	var self = this;
	var levels = ["log","debug","info","warn","error"];
    this.maxEntries = 2000;
	this.entries = [];
    this.jslog = true;

	levels.forEach(function(level) {
					   self[level] = function(message) {
						   self.log(message, level);
                           //check if we also log to Javascript Console

                           if (this.jslog) {
                               self.logToJsConsole(message, level);
                           }
					   }
				   });

	this.observers = [];

	this.exception = function(exception) {
        var msg = "Unexpected Exception: " + describe(exception, ', ');
        this.error(msg);
	}

	this.log = function(message, level) {
		var entry = {
			message: message,
			level: level,
			line: function() {
				return '[' + this.level + '] ' + message + "\n";
			}
		};
		this.entries.push(entry);
        if (this.entries.length > this.maxEntries) this.entries.shift();
		this.observers.forEach(function(o) { o.onAppendEntry(entry) });
	}

	this.clear = function() {
		this.entries.splice(0, this.entries.length);
		this.observers.forEach(function(o) { o.onClear() });
	}

    this.logToJsConsole = function(message, level){
        if(level == "debug"){
            jslogger.debug(message);
        }
        if(level == "info"){
            jslogger.info(message);
        }
        if(level == "warn"){
            jslogger.warn(message);
        }
        if(level == "error"){
            jslogger.error(message);
        }
    }
}

var logger = new TrumpLogger();