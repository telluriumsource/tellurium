var  attrValue = "onclick:_onButtonClick,onmouseenter:_onMouse,onmouseleave:_onMouse,onmousedown:_onMouse";
var map = new HashMap();

    if(trimString(attrValue).length > 0 ){
        var splited = attrValue.split(",");
        alert(splited.length);
        for(var i=0; i<splited.length; i++){
            if(trimString(splited[i]).length > 0){
                var pair = trimString(splited[i]).split(":");
                map.set(trimString(pair[0]), trimString(pair[1]));
                alert(splited[i] + " --> " + pair[0] + ": " + pair[1]);
            }
        }
    }      
   alert("Map: " + map.showMe());

   var keySet = map.keySet();
   alert(keySet.length);

   for(var j=0; j<keySet.length; j++){
        alert("key: " + keySet[j] + ", value: " + map.get(keySet[j]));
   }