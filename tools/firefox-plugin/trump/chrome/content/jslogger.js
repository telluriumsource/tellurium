var getStackTrace = function() {
    try {(0)()} catch (e) {
        return e.stack.replace(/^.*?\n/,'').replace(/(?:\n@:0)?\s+$/m,'').replace(/^\(/gm,'{anon}(').split("\n");
    }
}
var getLogDetails = function(line){
     //{anon}()@chrome://trump/content/editor.js:85
    var re1='(\\(.*\\))';	// Round Braces 1
    var re2='.*?';	// Non-greedy match on filler
    var re3='((?:\\/[\\w\\.\\-]+)+)';	// Unix Path 1
    var re4='.*?';	// Non-greedy match on filler
    var re5='(\\d+)';	// Integer Number 1

    var p = new RegExp(re1+re2+re3+re4+re5,["i"]);
    var m = p.exec(line);
    if (m.length>0)
    {
      var rbraces1=m[1];
      var path="chrome:/"+m[2];
      var line=m[3];
      return [path, line];
    }
    return ["", -1];

}
var addLineNumber = function(loggingEvent){
    var stack = getStackTrace()[6];  //assuming that this is called from the appender this 6 is what we want!
    getLogDetails(stack);
    stack = stack.substring(stack.indexOf("chrome://trump/content/") + "chrome://trump/content/".length, stack.length);
    //we need to clone the event because otherwise the same event will be used in all appenders and we wont know if the line number
    //was added to it or not, and we dont want to use an expando.
    var clonedEvent = new Log4js.LoggingEvent(loggingEvent.categoryName, loggingEvent.level, "["+stack+"] " + loggingEvent.message, loggingEvent.exception, loggingEvent.logger);
    return clonedEvent;
}
Log4js.MozillaLineNumberJSConsoleAppender = function() {
	this.layout = new Log4js.SimpleLayout();
    this.consoleService = Components.classes["@mozilla.org/consoleservice;1"]
                                         .getService(Components.interfaces.nsIConsoleService);
};

Log4js.MozillaLineNumberJSConsoleAppender.prototype = Log4js.extend(new Log4js.Appender(), {
	/**
	 * @see Log4js.Appender#doAppend
	 */

    doAppend: function(loggingEvent) {
        loggingEvent.message = TRUMP_LOG_ID + loggingEvent.message;

        var pl = getLogDetails(getStackTrace()[6]);
        var consoleService = Components.classes["@mozilla.org/consoleservice;1"]
                        .getService(Components.interfaces.nsIConsoleService);
        var scriptError = Components.classes["@mozilla.org/scripterror;1"]
                                     .createInstance(Components.interfaces.nsIScriptError);

        scriptError.init(loggingEvent.message, pl[0], null, pl[1], 0, this.getFlag(loggingEvent), "trump");
        consoleService.logMessage(scriptError);
    },
	toString: function() {
	    return "Log4js.MozillaLineNumberJSConsoleAppender";
	},
	getFlag: function(loggingEvent)
	{
		var retval;
		switch (loggingEvent.level) {
			case Log4js.Level.FATAL:
				retval = 2;//nsIScriptError.exceptionFlag = 2
				break;
			case Log4js.Level.ERROR:
				retval = 0;//nsIScriptError.errorFlag
				break;
			case Log4js.Level.WARN:
				retval = 1;//nsIScriptError.warningFlag = 1
				break;
			default:
				retval = 1;//nsIScriptError.warningFlag = 1
				break;
		}

		return retval;
	}
});
Log4js.TrumpLogAppender = function() {
	this.currentLine = 0;
};
Log4js.TrumpLogAppender.prototype = Log4js.extend(new Log4js.Appender(), {
	doAppend: function(loggingEvent) {

        if(logWindow && logWindow.document){
            loggingEvent = addLineNumber(loggingEvent);
            var textbox = logWindow.document.getElementById("uiModelText");
            logText += TRUMP_LOG_ID + loggingEvent.message +"\n";
            textbox.value = logText;
        }
    },
	toString: function() {
	    return "Log4js.TrumpLogAppender";
	}
});
var logWindow;
var logText = "";
var showLogWindow = function(){
    logWindow = window.open("chrome://trump/content/trumpLogger.xul","logWindow","chrome,centerscreen,alwaysRaised=true,resizable");
    //TODO: pass logText into window using XUL
}

//and here is the logger!
var jslogger = new Log4js.getLogger("root");
jslogger.setLevel(Log4js.Level.ALL);
jslogger.addAppender(new Log4js.MozillaLineNumberJSConsoleAppender());
//jslogger.addAppender(new Log4js.TrumpLogAppender());

//TRUMP log Identifier to make it easier to parse
const TRUMP_LOG_ID = "[TrUMP 0.1.0] ";