
function XPathComparator(){

}

XPathComparator.prototype.compare = function(firstXPath, secondXPath){
    return secondXPath.xpath.length - firstXPath.xpath.length    
}