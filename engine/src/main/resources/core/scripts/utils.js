//Util file to hold commonly used data structures

//FILO (First In Last Out) QUEUE
function FiloQueue(){
    this.queue = new Array();
}

FiloQueue.prototype.clear = function(){
    this.queue = new Array();
};

FiloQueue.prototype.size = function(){
    return this.queue.length;
};

FiloQueue.prototype.peek = function(){
    if(this.queue.length > 0){
        return this.queue[this.queue.length - 1];
    }

    return null;
};

FiloQueue.prototype.push = function(obj){
    this.queue.push(obj);
};

FiloQueue.prototype.pop = function(){
    if(this.queue.length > 0){
        return this.queue.pop();
    }

    return null;
};

FiloQueue.prototype.toArray = function(){
    return this.queue;
};

//FIFo (First In Firt Out) Queue

function FifoQueue(){
    this.queue = new Array();
}

FifoQueue.prototype.clear = function(){
    this.queue = new Array();
};

FifoQueue.prototype.size = function(){
    return this.queue.length;
};

FifoQueue.prototype.peek = function(){
    if(this.queue.length > 0){
        return this.queue[0];
    }

    return null;
};

FifoQueue.prototype.push = function(obj){
    this.queue.push(obj);
};

FifoQueue.prototype.pop = function(){
    if(this.queue.length > 0){
        return this.queue.shift();
    }

    return null;
};

FifoQueue.prototype.toArray = function(){
    return this.queue;    
};

function HashMap()
{
    // members
    this.keyArray = new Array(); // Keys
    this.valArray = new Array(); // Values
}

//clone a copy of the Hash Map
HashMap.prototype.clone = function(){
    var newmap = new HashMap();
    if(this.keyArray.length > 0){
        newmap.keyArray = this.keyArray.slice(0);
    }
    if(this.valArray.length > 0){
        newmap.valArray = this.valArray.slice(0);
    }

    return newmap;
};

HashMap.prototype.put = function(key, val){
    var elementIndex = this.findIt( key );

    if( elementIndex == (-1) )
    {
        this.keyArray.push( key );
        this.valArray.push( val );
    }
    else
    {
        this.valArray[ elementIndex ] = val;
    }
};

HashMap.prototype.get = function( key ){
    var result = null;
    var elementIndex = this.findIt( key );

    if( elementIndex != (-1) )
    {
        result = this.valArray[ elementIndex ];
    }

    return result;
};

 HashMap.prototype.removeAt = function( index )
{
  var part1 = this.slice( 0, index);
  var part2 = this.slice( index+1 );

  return( part1.concat( part2 ) );
};

 HashMap.prototype.removeArrayAt = function(ar, index )
{
  var part1 = ar.slice( 0, index);
  var part2 = ar.slice( index+1 );

  return( part1.concat( part2 ) );
};

HashMap.prototype.remove = function ( key )
{
//    var result = null;
    var elementIndex = this.findIt( key );

    if( elementIndex != -1 )
    {
        this.keyArray = this.removeArrayAt(this.keyArray, elementIndex);
        this.valArray = this.removeArrayAt(this.valArray, elementIndex);
//        this.keyArray = this.keyArray.removeAt(elementIndex);
//        this.valArray = this.valArray.removeAt(elementIndex);
    }

//    return ;
};

HashMap.prototype.size = function()
{
    return (this.keyArray.length);
};

HashMap.prototype.clear = function()
{
    for( var i = 0; i < this.keyArray.length; i++ )
    {
        this.keyArray.pop(); this.valArray.pop();
    }
};

HashMap.prototype.keySet = function()
{
    return (this.keyArray);
};

HashMap.prototype.valSet = function()
{
    return (this.valArray);
};

HashMap.prototype.showMe = function()
{
    var result = "";

    for( var i = 0; i < this.keyArray.length; i++ )
    {
        result += "Key: " + this.keyArray[ i ] + "\tValues: " + this.valArray[ i ] + "\n";
    }
    return result;
};

HashMap.prototype.findIt = function( key )
{
    var result = (-1);

    for( var i = 0; i < this.keyArray.length; i++ )
    {
        if( this.keyArray[ i ] == key )
        {
            result = i;
            break;
        }
    }
    return result;
};

function StringBuffer() {
    this.buffer = [];
}

StringBuffer.prototype.append = function(string) {
    this.buffer.push(string);
    return this;
};

StringBuffer.prototype.toString = function() {
    return this.buffer.join("");
};

function trimString(str) {
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
}

function SimpleCompare(){

}

SimpleCompare.prototype.compare = function(a, b){
    return a > b;    
};

function PriorityQueue(){  

    this.A = new Array();
    //you define your own comparator and overwrite this for our data
    this.comparator = new SimpleCompare();
}

PriorityQueue.prototype.size = function(){
    return this.A.length;
};

PriorityQueue.prototype.parent = function(val){
    return Math.floor((val-1)/2);
};

PriorityQueue.prototype.left = function(val){
    return 2*val+1;
};

PriorityQueue.prototype.right = function(val){
    return 2*val+2;
};

PriorityQueue.prototype.heapify = function(index){
    var l = this.left(index);
    var r = this.right(index);

    var largest;
    if(l < this.A.length && this.comparator.compare(this.A[l], this.A[index])){
        largest = l;
    }else{
        largest = index;
    }

    if(r < this.A.length && this.comparator.compare(this.A[r], this.A[largest])){    
        largest = r;
    }

    if(largest != index){
        //exchange A[index] and A[largest]
        var tmp = this.A[index];
        this.A[index] = this.A[largest];
        this.A[largest] = tmp;
        this.heapify(largest);
    }
};

PriorityQueue.prototype.insert = function(elem){
    this.A.push(elem);
    var i = this.A.length-1;
    while(i > 0 && this.comparator.compare(elem, this.A[this.parent(i)])){
        this.A[i] = this.A[this.parent(i)];
        i = this.parent(i);
    }
    this.A[i] = elem;
};

PriorityQueue.prototype.extractMax = function(){
    if(this.A.length < 1)
        return null;

    var max = this.A[0];
    var last = this.A.pop();
    if(this.A.length > 0){
        this.A[0] = last;
        this.heapify(0);
    }
    
    return max;
};

PriorityQueue.prototype.buildHeap = function(){
    for(var i=Math.floor(this.A.length/2)-1; i>=0; i--){
        this.heapify(i);
    }
};


//since Javascript associate array is actually an object with key as attribute
//and no length information, need to manually trace the size
function Hashtable(){
    this.map = {};
    this.length = 0;
}

Hashtable.prototype.exist = function(key){
    return typeof this.map[key] != "undefined";
};

Hashtable.prototype.put = function(key, val){
    if(!this.exist(key)){
        this.length++;
    }
    this.map[key] = val;
};

Hashtable.prototype.get = function(key){
    if(this.exist(key)){
        return this.map[key];
    }

    return null;
};

Hashtable.prototype.remove = function(key){
    if(this.exist(key)){
        this.length--;
        delete this.map[key];
    }
};

Hashtable.prototype.clear = function(){
    this.map = {};
    this.length = 0;
};

Hashtable.prototype.size = function(){
    return this.length;
};

Hashtable.prototype.keySet = function(){
    var keys = new Array();
    for(var key in this.map){
        keys.push(key);
    }

    return keys;
};

Hashtable.prototype.valSet = function(){
    var vals = new Array();
    for(var key in this.map){
        vals.push(this.map[key]);
    }

    return vals;
};

//clone a copy of the Hash Table
Hashtable.prototype.clone = function(){
    var newmap = new Hashtable();
    for(var key in this.map){
        newmap.put(key, this.map[key]);
    }

    return newmap;
};

Hashtable.prototype.showMe = function(){
    var sb = "";
    for(var key in this.map){
        sb = sb + " [" + key + "]=" + this.map[key];
    }

    return sb;
};

String.prototype.trim = function(){
    return (this.replace(/^[\s\xA0]+/, "").replace(/[\s\xA0]+$/, ""));
};


function objectExtends(destination, source1, source2) {
  for (var p1 in source1) {
    destination[p1] = source1[p1];
  }
  for (var p2 in source2) {
    destination[p2] = source2[p2];
  }

  return destination;
}

function objectCopy(destination, source) {
  for (var property in source) {
    destination[property] = source[property];
  }
  return destination;
}

function getObjectClass(obj) {
    if (obj && obj.constructor && obj.constructor.toString) {
        var arr = obj.constructor.toString().match(
                /function\s*(\w+)/);

        if (arr && arr.length == 2) {
            return arr[1];
        }
    }

    return undefined;
}


String.prototype.startsWith = function(str)
{
    return (this.indexOf(str) === 0);
};
//Have problem if the str starts with "*"
//String.prototype.startsWith = function(str)
//{return (this.match("^"+str)==str);}


String.prototype.beginsWith = function(t, i) {
    if (i==false) {
        return (t == this.substring(0, t.length)); 
    } else {
        return (t.toLowerCase() == this.substring(0, t.length).toLowerCase());
    }
};

String.prototype.endsWith = function(t, i) {
    if (i==false) {
        return (t== this.substring(this.length - t.length));
    } else {
        return (t.toLowerCase() == this.substring(this.length - t.length).toLowerCase());
    }
};

//code copied from http://ejohn.org/blog/simple-javascript-inheritance/
// and the copywright belongs to John Resig
(function(){
  var initializing = false, fnTest = /xyz/.test(function(){xyz;}) ? /\b_super\b/ : /.*/;

  // The base Class implementation (does nothing)
  this.Class = function(){};

  // Create a new Class that inherits from this class
  Class.extend = function(prop) {
    var _super = this.prototype;

    // Instantiate a base class (but only create the instance,
    // don't run the init constructor)
    initializing = true;
    var prototype = new this();
    initializing = false;

    // Copy the properties over onto the new prototype
    for (var name in prop) {
      // Check if we're overwriting an existing function
      prototype[name] = typeof prop[name] == "function" &&
        typeof _super[name] == "function" && fnTest.test(prop[name]) ?
        (function(name, fn){
          return function() {
            var tmp = this._super;

            // Add a new ._super() method that is the same method
            // but on the super-class
            this._super = _super[name];

            // The method only need to be bound temporarily, so we
            // remove it when we're done executing
            var ret = fn.apply(this, arguments);
            this._super = tmp;

            return ret;
          };
        })(name, prop[name]) :
        prop[name];
    }

    // The dummy class constructor
    function Class() {
      // All construction is actually done in the init method
      if ( !initializing && this.init )
        this.init.apply(this, arguments);
    }

    // Populate our constructed prototype object
    Class.prototype = prototype;

    // Enforce the constructor to be what we expect
    Class.constructor = Class;

    // And make this class extendable
    Class.extend = arguments.callee;

    return Class;
  };
})();

function KeyValuePair(){
    this.key = null;
    this.val = null;
}

// Domain Public by Eric Wendelin http://eriwen.com/ (2008)
//                  Luke Smith http://lucassmith.name/ (2008)
//                  Loic Dachary <loic@dachary.org> (2008)
//                  Johan Euphrosine <proppy@aminche.com> (2008)
//                  Øyvind Sean Kinsey http://kinsey.no/blog
//
// Information and discussions
// http://jspoker.pokersource.info/skin/test-printstacktrace.html
// http://eriwen.com/javascript/js-stack-trace/
// http://eriwen.com/javascript/stacktrace-update/
// http://pastie.org/253058
// http://browsershots.org/http://jspoker.pokersource.info/skin/test-printstacktrace.html
//

//
// guessFunctionNameFromLines comes from firebug
//
// Software License Agreement (BSD License)
//
// Copyright (c) 2007, Parakey Inc.
// All rights reserved.
//
// Redistribution and use of this software in source and binary forms, with or without modification,
// are permitted provided that the following conditions are met:
//
// * Redistributions of source code must retain the above
//   copyright notice, this list of conditions and the
//   following disclaimer.
//
// * Redistributions in binary form must reproduce the above
//   copyright notice, this list of conditions and the
//   following disclaimer in the documentation and/or other
//   materials provided with the distribution.
//
// * Neither the name of Parakey Inc. nor the names of its
//   contributors may be used to endorse or promote products
//   derived from this software without specific prior
//   written permission of Parakey Inc.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
// IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
// FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
// IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
// OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

/**
 *
 * @cfg {Error} e The error to create a stacktrace from (optional)
 * @cfg {Boolean} guess If we should try to resolve the names of anonymous functions
 */
function printStackTrace(options) {
    var ex = (options && options.e) ? options.e : null;
    var guess = (options && options.guess) ? options.guess : false;

    var p = new printStackTrace.implementation();
    var result = p.run(ex);
    return (guess) ? p.guessFunctions(result) : result;
}

printStackTrace.implementation = function() {};

printStackTrace.implementation.prototype = {
    run: function(ex) {
        // Use either the stored mode, or resolve it
        var mode = this._mode || this.mode();
        if (mode === 'other') {
            return this.other(arguments.callee);
        }
        else {
            ex = ex ||
                (function() {
                    try {
                        (0)();
                    } catch (e) {
                        return e;
                    }
                })();
            return this[mode](ex);
        }
    },

    mode: function() {
        try {
            (0)();
        } catch (e) {
            if (e.arguments) {
                return (this._mode = 'chrome');
            }
            if (e.stack) {
                return (this._mode = 'firefox');
            }
            if (window.opera && !('stacktrace' in e)) { //Opera 9-
                return (this._mode = 'opera');
            }
        }
        return (this._mode = 'other');
    },

    chrome: function(e) {
        return e.stack.replace(/^.*?\n/, '').
                replace(/^.*?\n/, '').
                replace(/^.*?\n/, '').
                replace(/^[^\(]+?[\n$]/gm, '').
                replace(/^\s+at\s+/gm, '').
                replace(/^Object.<anonymous>\s*\(/gm, '{anonymous}()@').
                split("\n");
    },

    firefox: function(e) {
        return e.stack.replace(/^.*?\n/, '').
                replace(/(?:\n@:0)?\s+$/m, '').
                replace(/^\(/gm, '{anonymous}(').
                split("\n");
    },

    // Opera 7.x and 8.x only!
    opera: function(e) {
        var lines = e.message.split("\n"), ANON = '{anonymous}',
            lineRE = /Line\s+(\d+).*?script\s+(http\S+)(?:.*?in\s+function\s+(\S+))?/i, i, j, len;

        for (i = 4, j = 0, len = lines.length; i < len; i += 2) {
            if (lineRE.test(lines[i])) {
                lines[j++] = (RegExp.$3 ? RegExp.$3 + '()@' + RegExp.$2 + RegExp.$1 : ANON + '()@' + RegExp.$2 + ':' + RegExp.$1) +
                ' -- ' +
                lines[i + 1].replace(/^\s+/, '');
            }
        }

        lines.splice(j, lines.length - j);
        return lines;
    },

    // Safari, Opera 9+, IE, and others
    other: function(curr) {
        var ANON = "{anonymous}", fnRE = /function\s*([\w\-$]+)?\s*\(/i, stack = [], j = 0, fn, args;

        var maxStackSize = 10;
        while (curr && stack.length < maxStackSize) {
            fn = fnRE.test(curr.toString()) ? RegExp.$1 || ANON : ANON;
            args = Array.prototype.slice.call(curr['arguments']);
            stack[j++] = fn + '(' + printStackTrace.implementation.prototype.stringifyArguments(args) + ')';

            //Opera bug: if curr.caller does not exist, Opera returns curr (WTF)
            if (curr === curr.caller && window.opera) {
	            //TODO: check for same arguments if possible
                break;
            }
            curr = curr.caller;
        }
        return stack;
    },

    stringifyArguments: function(args) {
        for (var i = 0; i < args.length; ++i) {
            var argument = args[i];
            if (typeof argument == 'object') {
                args[i] = '#object';
            } else if (typeof argument == 'function') {
                args[i] = '#function';
            } else if (typeof argument == 'string') {
                args[i] = '"' + argument + '"';
            }
        }
        return args.join(',');
    },

    sourceCache: {},

    ajax: function(url) {
        var req = this.createXMLHTTPObject();
        if (!req) {
            return;
        }
        req.open('GET', url, false);
        req.setRequestHeader("User-Agent", "XMLHTTP/1.0");
        req.send('');
        return req.responseText;
    },

    createXMLHTTPObject: function() {
	    // Try XHR methods in order and store XHR factory
        var xmlhttp, XMLHttpFactories = [
            function() {
                return new XMLHttpRequest();
            }, function() {
                return new ActiveXObject("Msxml2.XMLHTTP");
            }, function() {
                return new ActiveXObject("Msxml3.XMLHTTP");
            }, function() {
                return new ActiveXObject("Microsoft.XMLHTTP");
            }
        ];
        for (var i = 0; i < XMLHttpFactories.length; i++) {
            try {
                xmlhttp = XMLHttpFactories[i]();
                // Use memoization to cache the factory
                this.createXMLHTTPObject = XMLHttpFactories[i];
                return xmlhttp;
            } catch (e) {}
        }
    },

    getSource: function(url) {
        if (!(url in this.sourceCache)) {
            this.sourceCache[url] = this.ajax(url).split("\n");
        }
        return this.sourceCache[url];
    },

    guessFunctions: function(stack) {
        for (var i = 0; i < stack.length; ++i) {
            var reStack = /{anonymous}\(.*\)@(\w+:\/\/([-\w\.]+)+(:\d+)?[^:]+):(\d+):?(\d+)?/;
            var frame = stack[i], m = reStack.exec(frame);
            if (m) {
                var file = m[1], lineno = m[4]; //m[7] is character position in Chrome
                if (file && lineno) {
                    var functionName = this.guessFunctionName(file, lineno);
                    stack[i] = frame.replace('{anonymous}', functionName);
                }
            }
        }
        return stack;
    },

    guessFunctionName: function(url, lineNo) {
        try {
            return this.guessFunctionNameFromLines(lineNo, this.getSource(url));
        } catch (e) {
            return 'getSource failed with url: ' + url + ', exception: ' + e.toString();
        }
    },

    guessFunctionNameFromLines: function(lineNo, source) {
        var reFunctionArgNames = /function ([^(]*)\(([^)]*)\)/;
        var reGuessFunction = /['"]?([0-9A-Za-z_]+)['"]?\s*[:=]\s*(function|eval|new Function)/;
        // Walk backwards from the first line in the function until we find the line which
        // matches the pattern above, which is the function definition
        var line = "", maxLines = 10;
        for (var i = 0; i < maxLines; ++i) {
            line = source[lineNo - i] + line;
            if (line !== undefined) {
                var m = reGuessFunction.exec(line);
                if (m) {
                    return m[1];
                }
                else {
                    m = reFunctionArgNames.exec(line);
                }
                if (m && m[1]) {
                    return m[1];
                }
            }
        }
        return "(?)";
    }
};