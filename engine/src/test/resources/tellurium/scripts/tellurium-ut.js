var camelizeApiName = function(apiName){
    return "do" + apiName.charAt(0).toUpperCase() + apiName.substring(1);
};

alert(camelizeApiName("waitForPage"));