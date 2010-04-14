teJQuery("#selenium_myiframe").contents().find("#category-list > li.division:eq(0) ul > li:eq(0) a").css("background-color");

var elem = teJQuery("#selenium_myiframe").contents().find("#category-list > li.division:eq(0) ul > li:eq(0) a").get(0);

!tellurium.logManager.isUseLog || fbLog(elem.style.backgroundColor);

teJQuery.curCSS(elem, "background-color");

var $e = teJQuery("#category-list > li.division:eq(0) ul > li:eq(0) a");

var dm = $e.get(0).parentNode.parentNode;
teJQuery(dm).css("background-color");