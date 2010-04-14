var jb = new JQueryBuilder();
jb.buildSelector("src","*/intl/en_ALL/images/logo.gif");

teJQuery(" #qrForm\\:innie");

var specials = ['#', '&', '~', '=', '>', "'", ':', '"', '!', ';', ',' ];
var regexSpecials = ['.', '*', '+', '|', '[', ']', '(', ')', '/', '^', '$'];
var sRE = new RegExp('(' + specials.join('|') + '|\\' + regexSpecials.join('|\\') + ')', 'g');

var id = "qrForm:innie";
id.replace(sRE, '\\$1');

teJQuery("#" + id);

var sRE = new RegExp("['#', '&', '~', '=', '>', ''", ':', '"', '!', ';', ',', '.', '*', '+', '|', '[', ']', '(', ')', '/', '^', '$']", 'g');

console.log(sRE);

var id = "qrForm:innie";
console.log(id);
var t = id.replace(sRE, '\\$1');
console.log(t)


console.log(teJQuery("#" + t));

var sRE = new RegExp("['#', '&', '~', '=', '>', ':', '!', ';', ',', '.', '*', '+', '|', '[', ']', '(', ')', '/', '^', '$']", 'g');

console.log(sRE);

var id = "qrForm:innie";
console.log("id: " + id);
var t = id.replace(sRE, '\\$1');
console.log("t: " + t);