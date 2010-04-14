function Selenium(){
    this.browserbot = new BrowserBot();
};

function BrowserBot(){

};

BrowserBot.prototype.findElement = function(locator){
    if(locator.startsWith("jquery=")){
        return teJQuery(locator.substring(7));
    }

    return null;
};

function SeleniumError(message) {
    var error = new Error(message);
    if (typeof(arguments.caller) != 'undefined') { // IE, not ECMA
        var result = '';
        for (var a = arguments.caller; a != null; a = a.caller) {
            result += '> ' + a.callee.toString() + '\n';
            if (a.caller == a) {
                result += '*';
                break;
            }
        }
        error.stack = result;
    }
    error.isSeleniumError = true;
    return error;
}

var selenium = new Selenium();