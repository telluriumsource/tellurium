
function dumpObject(obj) {
    if (typeof(console) != "undefined") {
        var output = '';
        for (var p in obj)
            output += p + '\n';

        console.log(output);
    }
}

function fbLog(msg, obj){
    if (typeof(console) != "undefined") {
        console.log(msg, obj);
    }
}

function fbInfo(msg, obj){
    if (typeof(console) != "undefined") {
        console.info(msg, obj);
    }
}

function fbDebug(msg, obj){
    if (typeof(console) != "undefined") {
        console.debug(msg, obj);
    }
}

function fbWarn(msg, obj){
    if (typeof(console) != "undefined") {
        console.warn(msg, obj);
    }
}

function fbError(msg, obj){
    if (typeof(console) != "undefined") {
        console.trace();
        console.error(msg, obj);
    }
}

function fbTrace(){
    if (typeof(console) != "undefined") {
        console.trace();
    }
}

function fbAssert(expr, obj){
    if (typeof(console) != "undefined") {
        console.assert(expr, obj);
    }
}

function fbDir(obj){
    if (typeof(console) != "undefined") {
        console.dir(obj);
    }
}


function LogManager(){
    this.isUseLog = false;
    this.logLevel = "info";
}

//dump logging message to dummy device, which sallows all messages == no logging
function DummyLogger(){

}

DummyLogger.prototype.info = function(msg){

};

DummyLogger.prototype.warn = function(msg){

};

DummyLogger.prototype.error = function(msg){

};

DummyLogger.prototype.fatal = function(msg){

};

DummyLogger.prototype.debug = function(msg){

};

DummyLogger.prototype.trace = function(msg){

};


 //uncomment this and comment the next line if you want to see the logging message in window
 //but it would slow down the testing dramatically, for debugging purpose only.

/*
var jslogger = new Log4js.getLogger("TeEngine");
jslogger.setLevel(Log4js.Level.ALL);
//jslogger.addAppender(new Log4js.MozillaJSConsoleAppender());
jslogger.addAppender(new Log4js.ConsoleAppender());
*/


var jslogger = new DummyLogger();
