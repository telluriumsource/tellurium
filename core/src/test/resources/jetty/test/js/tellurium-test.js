function TelluriumTestCase(){

};

TelluriumTestCase.prototype.testUiid = function(){
    var uiid = new Uiid();
    uiid.convertToUiid("ProblematicForm.Username.Input");
    fbLog("", uiid);
    fbLog("", uiid.peek());
    var uiid1 = getUiid("Form.Username.Input");
    var uiid2 = getUiid("Form.Username");
    var match = uiid1.matchWith(uiid2);
    var sub = uiid1.subUiid(uiid2.size());
};

TelluriumTestCase.prototype.testConvertRidToUid = function(){
    var rid = "Form";
    var uid = convertRidToUid(rid);
    fbLog("uid " + uid, uid == "Form");
    rid = "Form.Password";
    uid = convertRidToUid(rid);
    fbLog("uid " + uid, uid == "Form.Password");
    rid = "SailingForm.Section_3";
    uid = convertRidToUid(rid);
    fbLog("uid " + uid, uid == "SailingForm.Section[3]");
    rid = "GT._1_2.Input";
    uid = convertRidToUid(rid);
    fbLog("uid " + uid, uid == "GT[1][2].Input");
    rid = "downloadResult._1_3._1";
    uid = convertRidToUid(rid);
    fbLog("uid " + uid, uid == "downloadResult[1][3].[1]");
    rid = "GT._HEADER_3";
    uid = convertRidToUid(rid);
    fbLog("uid " + uid, uid == "GT.HEADER[3]");
};

TelluriumTestCase.prototype.testPrie = function(){
    var trie = new Trie();
    trie.insert("Form", "form1");
    trie.insert("Form.Password", "password2");
    trie.insert("Form.Username.Input", "input4");
    trie.insert("Form.Username.Label", "username3");
    trie.insert("Form.Password.Input", "input6");   
    trie.insert("Form.Submit", "submit5");
    trie.checkLevel();
    trie.printMe();
    trie.dumpMe();
    var form = trie.getChildrenData("Form");
    var username = trie.getChildrenData("Form.Username");
    var label = trie.getChildrenData("Form.Username.Submit");
};

TelluriumTestCase.prototype.testSpecial = function(){
    var json = [{"obj":{"uid":"Form","locator":{"tag":"form","attributes":{"action":"check_phone","method":"POST"}},"uiType":"Form","metaData":{"id":"Form","type":"UiObject"}},"key":"Form"},{"obj":{"uid":"check","locator":{"tag":"input","attributes":{"value":"Check","type":"submit"}},"uiType":"SubmitButton","metaData":{"id":"check","type":"UiObject"}},"key":"Form.check"},{"obj":{"uid":"Number","locator":{"tag":"input","attributes":{"name":"Profile\/Customer\/Telephone\/@PhoneNumber"}},"uiType":"InputBox","metaData":{"id":"Number","type":"UiObject"}},"key":"Form.Number"},{"obj":{"uid":"Country","locator":{"tag":"select","attributes":{"name":"Profile\/Customer\/Telephone\/@CountryAccessCode"}},"uiType":"Selector","metaData":{"id":"Country","type":"UiObject"}},"key":"Form.Country"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
    uim.parseUiModule(json);
    var alg = new UiAlg();
//    alg.allowRelax = true;
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    tellurium.cache.cacheOption = false;
//    tellurium.cache.addToCache("Form", uim);
//    var context = new WorkflowContext();
//    context.alg = alg;
//    var uiid = new Uiid();
//    var tb111 = uim.walkTo(context, uiid.convertToUiid("Form.Number"));
    tellurium.teApi.click("jquery=form[method=POST] select[name=Profile/Customer/Telephone/@CountryAccessCode]");

};

TelluriumTestCase.prototype.testTwoDim = function(){
//    var json = [{"obj":{"uid":"Table","hrt":"tr","locator":{"tag":"table","attributes":{"id":"table"}},"ht":"thead","bct":"div","frt":"tr","hct":"th","ft":"tfoot","brt":"div","uiType":"StandardTable","fct":"td","bt":"div"},"key":"Table"},{"obj":{"uid":"tbody: 1, row: *, column: 1","locator":{"tag":"div"},"uiType":"TextBox"},"key":"Table._1_ALL_1"},{"obj":{"uid":"tbody: 1, row: *, column: 2","locator":{"tag":"img"},"uiType":"Image"},"key":"Table._1_ALL_2"},{"obj":{"uid":"tbody: 2, row: *, column: 1","locator":{"tag":"div"},"uiType":"TextBox"},"key":"Table._2_ALL_1"},{"obj":{"uid":"tbody: 2, row: *, column: 2","locator":{"tag":"div"},"uiType":"TextBox"},"key":"Table._2_ALL_2"},{"obj":{"uid":"tbody: 2, row: *, column: 3","locator":{"tag":"div"},"uiType":"TextBox"},"key":"Table._2_ALL_3"}];
    var json = [{"obj":{"uid":"Table","hrt":"tr","locator":{"tag":"table","attributes":{"id":"table"}},"ht":"thead","bct":"div","frt":"tr","hct":"th","ft":"tfoot","brt":"div","uiType":"StandardTable","fct":"td","bt":"div"},"key":"Table"},{"obj":{"uid":"tbody: 1, row: *, column: 1","locator":{"tag":"div"},"self":true,"uiType":"TextBox"},"key":"Table._1_ALL_1"},{"obj":{"uid":"tbody: 1, row: *, column: 2","locator":{"tag":"img"},"uiType":"Image"},"key":"Table._1_ALL_2"},{"obj":{"uid":"tbody: 2, row: *, column: 1","locator":{"tag":"div"},"self":true,"uiType":"TextBox"},"key":"Table._2_ALL_1"},{"obj":{"uid":"tbody: 2, row: *, column: 2","locator":{"tag":"div"},"self":true,"uiType":"TextBox"},"key":"Table._2_ALL_2"},{"obj":{"uid":"tbody: 2, row: *, column: 3","locator":{"tag":"div"},"self":true,"uiType":"TextBox"},"key":"Table._2_ALL_3"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
    uim.parseUiModule(json);
    var alg = new UiAlg();
    alg.allowRelax = true;
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    tellurium.cache.cacheOption = true;
    tellurium.cache.addToCache("Table", uim);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var tb111 = uim.walkTo(context, uiid.convertToUiid("Table[1][1][1]"));
    var tb211 = uim.walkTo(context, uiid.convertToUiid("Table[2][1][1]"));
    tellurium.teApi.getHTMLSource("Table");
};

TelluriumTestCase.prototype.testRepeat = function(){
//    var json = [{"obj":{"uid":"SailingForm","locator":{"tag":"form","attributes":{"name":"selectedSailingsForm"}},"uiType":"Form"},"key":"SailingForm"},{"obj":{"uid":"Section","locator":{"tag":"div","attributes":{"class":"segment clearfix"}},"uiType":"Repeat"},"key":"SailingForm.Section"},{"obj":{"uid":"Option","locator":{"direct":true,"tag":"div","attributes":{"class":"option"}},"uiType":"Repeat"},"key":"SailingForm.Section.Option"},{"obj":{"uid":"Fares","locator":{"direct":true,"tag":"ul","attributes":{"class":"fares"}},"uiType":"List","separator":"li"},"key":"SailingForm.Section.Option.Fares"},{"obj":{"uid":"all","locator":null,"uiType":"Container"},"key":"SailingForm.Section.Option.Fares._ALL"},{"obj":{"uid":"radio","locator":{"tag":"input","attributes":{"type":"radio"}},"events":["click"],"uiType":"RadioButton"},"key":"SailingForm.Section.Option.Fares._ALL.radio"},{"obj":{"uid":"label","locator":{"tag":"label"},"uiType":"TextBox"},"key":"SailingForm.Section.Option.Fares._ALL.label"},{"obj":{"uid":"Details","locator":{"tag":"div","attributes":{"class":"details"}},"uiType":"Container"},"key":"SailingForm.Section.Option.Details"},{"obj":{"uid":"ShipInfo","locator":{"tag":"dl"},"uiType":"Container"},"key":"SailingForm.Section.Option.Details.ShipInfo"},{"obj":{"uid":"ShipLabel","locator":{"position":"1","tag":"dt"},"uiType":"TextBox"},"key":"SailingForm.Section.Option.Details.ShipInfo.ShipLabel"},{"obj":{"uid":"Ship","locator":{"position":"1","tag":"dd"},"uiType":"TextBox"},"key":"SailingForm.Section.Option.Details.ShipInfo.Ship"},{"obj":{"uid":"DepartureLabel","locator":{"position":"2","tag":"dt"},"uiType":"TextBox"},"key":"SailingForm.Section.Option.Details.ShipInfo.DepartureLabel"},{"obj":{"uid":"Departure","locator":{"position":"2","tag":"dd"},"uiType":"Container"},"key":"SailingForm.Section.Option.Details.ShipInfo.Departure"},{"obj":{"uid":"Time","locator":{"tag":"em"},"uiType":"TextBox"},"key":"SailingForm.Section.Option.Details.ShipInfo.Departure.Time"},{"obj":{"uid":"ArrivalLabel","locator":{"position":"3","tag":"dt"},"uiType":"TextBox"},"key":"SailingForm.Section.Option.Details.ShipInfo.ArrivalLabel"},{"obj":{"uid":"Arrival","locator":{"position":"3","tag":"dd"},"uiType":"Container"},"key":"SailingForm.Section.Option.Details.ShipInfo.Arrival"},{"obj":{"uid":"Time","locator":{"tag":"em"},"uiType":"TextBox"},"key":"SailingForm.Section.Option.Details.ShipInfo.Arrival.Time"}];
    var json = [{"obj":{"uid":"SailingForm","locator":{"tag":"form","attributes":{"name":"selectedSailingsForm"}},"uiType":"Form","metaData":{"id":"SailingForm","type":"UiObject"}},"key":"SailingForm"},{"obj":{"uid":"Section","locator":{"tag":"div","attributes":{"class":"segment clearfix"}},"uiType":"Repeat","metaData":{"id":"Section","type":"UiObject"}},"key":"SailingForm.Section"},{"obj":{"uid":"Option","locator":{"direct":true,"tag":"div","attributes":{"class":"option"}},"uiType":"Repeat","metaData":{"id":"Option","type":"UiObject"}},"key":"SailingForm.Section.Option"},{"obj":{"uid":"Details","locator":{"tag":"div","attributes":{"class":"details"}},"uiType":"Container","metaData":{"id":"Details","type":"UiObject"}},"key":"SailingForm.Section.Option.Details"},{"obj":{"uid":"ShipInfo","locator":{"tag":"dl"},"uiType":"Container","metaData":{"id":"ShipInfo","type":"UiObject"}},"key":"SailingForm.Section.Option.Details.ShipInfo"},{"obj":{"uid":"Ship","locator":{"position":"1","tag":"dd"},"uiType":"TextBox","metaData":{"id":"Ship","type":"UiObject"}},"key":"SailingForm.Section.Option.Details.ShipInfo.Ship"},{"obj":{"uid":"DepartureLabel","locator":{"position":"2","tag":"dt"},"uiType":"TextBox","metaData":{"id":"DepartureLabel","type":"UiObject"}},"key":"SailingForm.Section.Option.Details.ShipInfo.DepartureLabel"},{"obj":{"uid":"Departure","locator":{"position":"2","tag":"dd"},"uiType":"Container","metaData":{"id":"Departure","type":"UiObject"}},"key":"SailingForm.Section.Option.Details.ShipInfo.Departure"},{"obj":{"uid":"Time","locator":{"tag":"em"},"uiType":"TextBox","metaData":{"id":"Time","type":"UiObject"}},"key":"SailingForm.Section.Option.Details.ShipInfo.Departure.Time"},{"obj":{"uid":"ArrivalLabel","locator":{"position":"3","tag":"dt"},"uiType":"TextBox","metaData":{"id":"ArrivalLabel","type":"UiObject"}},"key":"SailingForm.Section.Option.Details.ShipInfo.ArrivalLabel"},{"obj":{"uid":"Arrival","locator":{"position":"3","tag":"dd"},"uiType":"Container","metaData":{"id":"Arrival","type":"UiObject"}},"key":"SailingForm.Section.Option.Details.ShipInfo.Arrival"},{"obj":{"uid":"Time","locator":{"tag":"em"},"uiType":"TextBox","metaData":{"id":"Time","type":"UiObject"}},"key":"SailingForm.Section.Option.Details.ShipInfo.Arrival.Time"},{"obj":{"uid":"ShipLabel","locator":{"position":"1","tag":"dt"},"uiType":"TextBox","metaData":{"id":"ShipLabel","type":"UiObject"}},"key":"SailingForm.Section.Option.Details.ShipInfo.ShipLabel"},{"obj":{"uid":"Fares","locator":{"direct":true,"tag":"ul","attributes":{"class":"fares"}},"uiType":"List","separator":"li","metaData":{"id":"Fares","type":"UiObject"}},"key":"SailingForm.Section.Option.Fares"},{"obj":{"uid":"_all","locator":null,"uiType":"Container","metaData":{"id":"_all","index":{"value":"all","type":"VAL"},"type":"List"}},"key":"SailingForm.Section.Option.Fares._all"},{"obj":{"uid":"radio","locator":{"tag":"input","attributes":{"type":"radio"}},"events":["click"],"uiType":"RadioButton","metaData":{"id":"radio","type":"UiObject"}},"key":"SailingForm.Section.Option.Fares._all.radio"},{"obj":{"uid":"label","locator":{"tag":"label"},"uiType":"TextBox","metaData":{"id":"label","type":"UiObject"}},"key":"SailingForm.Section.Option.Fares._all.label"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
    uim.parseUiModule(json);
    var alg = new UiAlg();
    alg.allowRelax = true;
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    tellurium.cache.cacheOption = true;
    tellurium.cache.addToCache("SailingForm", uim);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var section = uim.walkTo(context, uiid.convertToUiid("SailingForm.Section"));
    var num = section.getRepeatNum(context);
    var invalid =  uim.walkTo(context, uiid.convertToUiid("SailingForm.Section[3]"));
    tellurium.teApi.getHTMLSource("SailingForm");
};

TelluriumTestCase.prototype.testGeneralTableModule = function(){
//    var json = [{"obj":{"uid":"GT","hrt":"tr","locator":{"tag":"table","attributes":{"id":"xyz"}},"ht":"tbody","bct":"td","frt":"tr","hct":"th","ft":"tfoot","brt":"tr","uiType":"StandardTable","fct":"td","bt":"tbody"},"key":"GT"},{"obj":{"uid":"header: all","locator":{},"uiType":"TextBox"},"key":"GT._ALL"},{"obj":{"uid":"row: 1, column: 1","locator":{"tag":"div","attributes":{"class":"abc"}},"uiType":"TextBox"},"key":"GT._1_1_1"},{"obj":{"uid":"row: 1, column: 2","locator":null,"uiType":"Container"},"key":"GT._1_1_2"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"class":"123"}},"uiType":"InputBox"},"key":"GT._1_1_2.Input"},{"obj":{"uid":"Some","locator":{"tag":"div","attributes":{"class":"someclass"}},"uiType":"Container"},"key":"GT._1_1_2.Some"},{"obj":{"uid":"Span","locator":{"tag":"span","attributes":{"class":"x"}},"uiType":"Span"},"key":"GT._1_1_2.Some.Span"},{"obj":{"uid":"Link","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"GT._1_1_2.Some.Link"}];
//    var json = [{"obj":{"uid":"GT","hrt":"tr","locator":{"tag":"table","attributes":{"id":"xyz"}},"ht":"tbody","bct":"td","frt":"tr","hct":"th","ft":"tfoot","brt":"tr","uiType":"StandardTable","fct":"td","bt":"tbody"},"key":"GT"},{"obj":{"uid":"header: all","locator":{},"uiType":"TextBox"},"key":"GT._HEADER_ALL"},{"obj":{"uid":"row: 1, column: 1","locator":{"tag":"div","attributes":{"class":"abc"}},"uiType":"TextBox"},"key":"GT._1_1_1"},{"obj":{"uid":"row: 1, column: 2","locator":null,"uiType":"Container"},"key":"GT._1_1_2"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"class":"123"}},"uiType":"InputBox"},"key":"GT._1_1_2.Input"},{"obj":{"uid":"Some","locator":{"tag":"div","attributes":{"class":"someclass"}},"uiType":"Container"},"key":"GT._1_1_2.Some"},{"obj":{"uid":"Span","locator":{"tag":"span","attributes":{"class":"x"}},"uiType":"Span"},"key":"GT._1_1_2.Some.Span"},{"obj":{"uid":"Link","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"GT._1_1_2.Some.Link"}];
    var json = [{"obj":{"uid":"GT","locator":{"tag":"table","attributes":{"id":"xyz"}},"ht":"tbody","hct":"th","brt":"tr","fct":"td","bt":"tbody","hrt":"tr","bct":"td","frt":"tr","ft":"tfoot","uiType":"StandardTable","metaData":{"id":"GT","type":"UiObject"}},"key":"GT"},{"obj":{"uid":"Headers","locator":{},"uiType":"TextBox","metaData":{"id":"Headers","index":{"value":"all","type":"VAL"},"type":"Header"}},"key":"GT.Headers"},{"obj":{"uid":"Profile","locator":null,"uiType":"Container","metaData":{"id":"Profile","tbody":{"value":"1","type":"VAL"},"column":{"value":"2","type":"VAL"},"type":"TBody","row":{"value":"1","type":"VAL"}}},"key":"GT.Profile"},{"obj":{"uid":"Some","locator":{"tag":"div","attributes":{"class":"someclass"}},"uiType":"Container","metaData":{"id":"Some","type":"UiObject"}},"key":"GT.Profile.Some"},{"obj":{"uid":"Link","locator":{"tag":"a"},"uiType":"UrlLink","metaData":{"id":"Link","type":"UiObject"}},"key":"GT.Profile.Some.Link"},{"obj":{"uid":"Span","locator":{"tag":"span","attributes":{"class":"x"}},"uiType":"Span","metaData":{"id":"Span","type":"UiObject"}},"key":"GT.Profile.Some.Span"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"class":"123"}},"uiType":"InputBox","metaData":{"id":"Input","type":"UiObject"}},"key":"GT.Profile.Input"},{"obj":{"uid":"Label","locator":{"tag":"div","attributes":{"class":"abc"}},"uiType":"TextBox","metaData":{"id":"Label","tbody":{"value":"1","type":"VAL"},"column":{"value":"1","type":"VAL"},"type":"TBody","row":{"value":"1","type":"VAL"}}},"key":"GT.Label"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
    alg.allowRelax = true;
    var dom = teJQuery("html");
    alg.validate(uim, dom);
    alg.santa(uim, dom);
    tellurium.cache.cacheOption = true;
    tellurium.cache.addToCache("GT", uim);    
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var tb = uim.walkTo(context, uiid.convertToUiid("GT"));
    var worker = new TextUiWorker();
    var out = tb.getAllBodyCell(context, worker);
    var header = uim.walkTo(context, uiid.convertToUiid("GT.header[2]"));
    var uinput = uim.walkTo(context, uiid.convertToUiid("GT[1][1]"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("GT[1][2].Input"));
    var smt = uim.walkTo(context, uiid.convertToUiid("GT[1][2].Some.Link"));
    var stree = tellurium.cache.takeSnapshot("GT");
    tellurium.teApi.getHTMLSource("GT");
};

TelluriumTestCase.prototype.testTelluriumDownloadResult = function(){
//    var json = [{"obj":{"uid":"downloadResult","locator":{"tag":"table","attributes":{"id":"resultstable","class":"results"}},"uiType":"Table"},"key":"downloadResult"},{"obj":{"uid":"header: 1","locator":{},"uiType":"TextBox"},"key":"downloadResult._1"},{"obj":{"uid":"header: 2","locator":{"text":"*Filename","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._2"},{"obj":{"uid":"header: 3","locator":{"text":"*Summary + Labels","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._3"},{"obj":{"uid":"header: 4","locator":{"text":"*Uploaded","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._4"},{"obj":{"uid":"header: 5","locator":{"text":"Size","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._5"},{"obj":{"uid":"header: 6","locator":{"text":"*DownloadCount","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._6"},{"obj":{"uid":"header: 7","locator":{"text":"*...","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._7"},{"obj":{"uid":"row: *, column: 1","locator":{},"uiType":"TextBox"},"key":"downloadResult._ALL_1"},{"obj":{"uid":"row:*, column: 3","locator":{},"uiType":"List"},"key":"downloadResult._ALL_3"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._ALL_3._ALL"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._ALL_ALL"}];
//    var json = [{"uid":"issueResult","args":[[{"obj":{"uid":"issueResult","locator":{"tag":"table","attributes":{"id":"resultstable","class":"results"}},"uiType":"Table"},"key":"issueResult"},{"obj":{"uid":"header: 1","locator":{},"uiType":"TextBox"},"key":"issueResult._HEADER_1"},{"obj":{"uid":"header: 2","locator":{"text":"*ID","tag":"a"},"uiType":"UrlLink"},"key":"issueResult._HEADER_2"},{"obj":{"uid":"header: 3","locator":{"text":"*Type","tag":"a"},"uiType":"UrlLink"},"key":"issueResult._HEADER_3"},{"obj":{"uid":"header: 4","locator":{"text":"*Status","tag":"a"},"uiType":"UrlLink"},"key":"issueResult._HEADER_4"},{"obj":{"uid":"header: 5","locator":{"text":"*Priority","tag":"a"},"uiType":"UrlLink"},"key":"issueResult._HEADER_5"},{"obj":{"uid":"header: 6","locator":{"text":"*Milestone","tag":"a"},"uiType":"UrlLink"},"key":"issueResult._HEADER_6"},{"obj":{"uid":"header: 7","locator":{"text":"*Owner","tag":"a"},"uiType":"UrlLink"},"key":"issueResult._HEADER_7"},{"obj":{"uid":"header: 9","locator":{"text":"*Summary + Labels","tag":"a"},"uiType":"UrlLink"},"key":"issueResult._HEADER_9"},{"obj":{"uid":"header: 10","locator":{"text":"*...","tag":"a"},"uiType":"UrlLink"},"key":"issueResult._HEADER_10"},{"obj":{"uid":"row: *, column: 1","locator":{},"uiType":"TextBox"},"key":"issueResult._ALL_1"},{"obj":{"uid":"row: *, column: 8","locator":{},"uiType":"TextBox"},"key":"issueResult._ALL_8"},{"obj":{"uid":"row: *, column: 10","locator":{},"uiType":"TextBox"},"key":"issueResult._ALL_10"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"issueResult._ALL_ALL"}];
    var json = [{"obj":{"uid":"downloadResult","locator":{"tag":"table","attributes":{"id":"resultstable","class":"results"}},"uiType":"Table"},"key":"downloadResult"},{"obj":{"uid":"header: 1","locator":{},"uiType":"TextBox"},"key":"downloadResult._HEADER_1"},{"obj":{"uid":"header: 2","locator":{"text":"*Filename","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._HEADER_2"},{"obj":{"uid":"header: 3","locator":{"text":"*Summary + Labels","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._HEADER_3"},{"obj":{"uid":"header: 4","locator":{"text":"*Uploaded","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._HEADER_4"},{"obj":{"uid":"header: 5","locator":{"text":"Size","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._HEADER_5"},{"obj":{"uid":"header: 6","locator":{"text":"*DownloadCount","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._HEADER_6"},{"obj":{"uid":"header: 7","locator":{"text":"*...","tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._HEADER_7"},{"obj":{"uid":"row: *, column: 1","locator":{},"uiType":"TextBox"},"key":"downloadResult._ALL_1"},{"obj":{"uid":"row:*, column: 3","locator": null,"uiType":"List"},"key":"downloadResult._ALL_3"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._ALL_3._ALL"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"downloadResult._ALL_ALL"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
    uim.parseUiModule(json);
    var alg = new UiAlg();
    alg.allowRelax = true;
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var dr = uim.walkTo(context, uiid.convertToUiid("downloadResult"));
    var dr13 = uim.walkTo(context, uiid.convertToUiid("downloadResult[1][3]"));
    var dr131 = uim.walkTo(context, uiid.convertToUiid("downloadResult[1][3].[1]"));
    var dr122 = uim.walkTo(context, uiid.convertToUiid("downloadResult[12][2]"));  //12 and up
};

TelluriumTestCase.prototype.testTelluriumIssueModule = function(){
    var json = [{"obj":{"uid":"issueSearch","locator":{"tag":"form","attributes":{"action":"list","method":"get"}},"uiType":"Form"},"key":"issueSearch"},{"obj":{"uid":"issueType","locator":{"tag":"select","attributes":{"id":"can","name":"can"}},"uiType":"Selector"},"key":"issueSearch.issueType"},{"obj":{"uid":"searchLabel","locator":{"text":"*for","tag":"span"},"uiType":"TextBox"},"key":"issueSearch.searchLabel"},{"obj":{"uid":"searchBox","locator":{"tag":"input","attributes":{"name":"q","type":"text"}},"uiType":"InputBox"},"key":"issueSearch.searchBox"},{"obj":{"uid":"searchButton","locator":{"tag":"input","attributes":{"value":"Search","type":"submit"}},"uiType":"SubmitButton"},"key":"issueSearch.searchButton"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
    alg.allowRelax = true;
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var uinput = uim.walkTo(context, uiid.convertToUiid("issueSearch.issueType"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("issueSearch.searchBox"));
    var smt = uim.walkTo(context, uiid.convertToUiid("issueSearch.searchButton"));
};

TelluriumTestCase.prototype.testEditPageModule = function(){
    var json = [{"obj":{"uid":"Account","locator":{"tag":"form","attributes":{"method":"post"}},"uiType":"Form"},"key":"Account"},{"obj":{"uid":"Name","locator":{"tag":"input","attributes":{"name":"A","type":"text"}},"uiType":"InputBox"},"key":"Account.Name"},{"obj":{"uid":"Site","locator":{"tag":"input","attributes":{"name":"B","type":"text"}},"uiType":"InputBox"},"key":"Account.Site"},{"obj":{"uid":"Revenue","locator":{"tag":"input","attributes":{"name":"C","type":"text"}},"uiType":"InputBox"},"key":"Account.Revenue"},{"obj":{"uid":"Info","locator":{"tag":"div","attributes":{"class":"info"}},"uiType":"Container"},"key":"Account.Info"},{"obj":{"uid":"Label","locator":{"text":"Test:","tag":"div","attributes":{"id":"label1"}},"uiType":"TextBox"},"key":"Account.Info.Label"},{"obj":{"uid":"Test","locator":{"tag":"input","attributes":{"id":"input5","name":"testname","type":"text"}},"uiType":"InputBox"},"key":"Account.Info.Test"},{"obj":{"uid":"save","locator":{"tag":"input","attributes":{"title":"Save","name":"save","class":"btn","type":"submit"}},"uiType":"SubmitButton"},"key":"Account.Save"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var uinput = uim.walkTo(context, uiid.convertToUiid("Account.Site"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("Account.Info.Test"));
    var smt = uim.walkTo(context, uiid.convertToUiid("Account.Save"));
};

TelluriumTestCase.prototype.testLogicalContainerModule = function(){
//    var json = [{"obj":{"uid":"AbstractForm","locator":{"tag":"form"},"uiType":"Form"},"key":"AbstractForm"},{"obj":{"uid":"Form1","locator":{"loc":null},"uiType":"Container"},"key":"AbstractForm.Form1"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"AbstractForm.Form1.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"AbstractForm.Form1.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"AbstractForm.Form1.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"AbstractForm.Form1.Password"},{"obj":{"uid":"Password1","locator":{"loc":null},"uiType":"Container"},"key":"AbstractForm.Form1.Password.Password1"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"AbstractForm.Form1.Password.Password1.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"AbstractForm.Form1.Password.Password1.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"AbstractForm.Form1.Submit"}];
    var json = [{"obj":{"uid":"AbstractForm","locator":{"tag":"form"},"uiType":"Form"},"key":"AbstractForm"},{"obj":{"uid":"Form1","locator":null,"uiType":"Container"},"key":"AbstractForm.Form1"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"AbstractForm.Form1.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"AbstractForm.Form1.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"AbstractForm.Form1.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"AbstractForm.Form1.Password"},{"obj":{"uid":"Password1","locator":null,"uiType":"Container"},"key":"AbstractForm.Form1.Password.Password1"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"AbstractForm.Form1.Password.Password1.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"AbstractForm.Form1.Password.Password1.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"AbstractForm.Form1.Submit"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var uinput = uim.walkTo(context, uiid.convertToUiid("AbstractForm.Form1.Username.Input"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("AbstractForm.Form1.Password.Password1.Input"));
    var smt = uim.walkTo(context, uiid.convertToUiid("AbstractForm.Form1.Submit")); 
};

TelluriumTestCase.prototype.testLogonUiModule = function(){
 //  var json =[{"obj":{"uid":"Form","locator":{"tag":"table"},"generated":"\/\/descendant-or-self::table","uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Username:\")]","uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"direct":true,"tag":"input","name":"j_username","type":"text"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::input[@type=\"text\" and @name=\"j_username\"]","uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Password:\")]","uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"direct":true,"tag":"input","name":"j_password","type":"password"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::input[@type=\"password\" and @name=\"j_password\"]","uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","name":"submit","value":"Login","type":"submit"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::input[@type=\"submit\" and @value=\"Login\" and @name=\"submit\"]","uiType":"SubmitButton"},"key":"Form.Submit"}];
//    var json = [{"obj":{"uid":"Form","locator":{"tag":"table"},"generated":"\/\/descendant-or-self::table","uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Username:\")]","uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","name":"j_username","type":"text"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/descendant-or-self::input[@type=\"text\" and @name=\"j_username\"]","uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Password:\")]","uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","name":"j_password","type":"password"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/descendant-or-self::input[@type=\"password\" and @name=\"j_password\"]","uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","name":"submit","value":"Login","type":"submit"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::input[@type=\"submit\" and @value=\"Login\" and @name=\"submit\"]","uiType":"SubmitButton"},"key":"Form.Submit"}]
//    var json = [{"obj":{"uid":"Form","locator":{"tag":"table"},"uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"Form.Submit"}];
//    var json = [{"obj":{"uid":"Google","locator":{"tag":"table"},"uiType":"Container"},"key":"Google"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"title":"Google Search","name":"q"}},"uiType":"InputBox"},"key":"Google.Input"},{"obj":{"uid":"Search","locator":{"tag":"input","attributes":{"name":"btnG","value":"Google Search","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.Search"},{"obj":{"uid":"ImFeelingLucky","locator":{"tag":"input","attributes":{"name":"btnI","value":"I'm Feeling Lucky","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.ImFeelingLucky"}];
//    var json = [{"obj":{"uid":"Google","locator":{"tag":"table"},"uiType":"Container"},"key":"Google"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"title":"Google Search","name":"q"}},"uiType":"InputBox"},"key":"Google.Input"},{"obj":{"uid":"Search","locator":{"tag":"input","attributes":{"name":"btnG","value":"Google Search","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.Search"},{"obj":{"uid":"ImFeelingLucky","locator":{"tag":"input","attributes":{"name":"btnI","value":"I'm Feeling Lucky","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.ImFeelingLucky"}];

//    var json = [{"obj":{"uid":"ProblematicForm","locator":{"tag":"table"},"uiType":"Container"},"key":"ProblematicForm"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"text"}},"uiType":"InputBox"},"key":"ProblematicForm.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"password"}},"uiType":"InputBox"},"key":"ProblematicForm.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"logon","type":"submit"}},"uiType":"SubmitButton"},"key":"ProblematicForm.Submit"}];

    var json = [{"obj":{"uid":"Form","locator":{"tag":"form"},"uiType":"Form"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"Form.Submit"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    tellurium.cache.cacheOption = true;
    tellurium.cache.addToCache("Form", uim);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
//    var uinput = uim.walkTo(context, "ProblematicForm.Username.Input");
//    var pinput = uim.walkTo(context, "ProblematicForm.Password.Input");
//    var smt = uim.walkTo(context, "ProblematicForm.Submit");
    var uinput = uim.walkTo(context, uiid.convertToUiid("Form.Username.Input"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("Form.Password.Input"));
    var smt = uim.walkTo(context, uiid.convertToUiid("Form.Submit"));
    tellurium.teApi.getHTMLSource("Form");
    var attrs = [{"val":"text","key":"type"}];
    var teuids = tellurium.teApi.getUiByTag("input", attrs);
    fbLog("result ", teuids);
};

TelluriumTestCase.prototype.testLogoUiModule = function(){
    var json = [{"obj":{"uid":"Logo","locator":{"tag":"img","attributes":{"alt":"Logo","src":"*.gif"}},"uiType":"Image"},"key":"Logo"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var logo = uim.walkTo(context, uiid.convertToUiid("Logo"));
};

TelluriumTestCase.prototype.testThumbnailUiModule = function(){
    var json = [{"obj":{"uid":"Thumbnail","locator":{"tag":"div","attributes":{"class":"thumbnail potd"}},"uiType":"Container"},"key":"Thumbnail"},{"obj":{"uid":"ICon","locator":{"tag":"div","attributes":{"class":"potd:icon png.fix"}},"uiType":"Container"},"key":"Thumbnail.ICon"},{"obj":{"uid":"Image","locator":{"tag":"img","attributes":{"src":"*.jpg"}},"uiType":"Image"},"key":"Thumbnail.ICon.Image"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"id":"Image:name","type":"text"}},"uiType":"InputBox"},"key":"Thumbnail.ICon.Input"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var image = uim.walkTo(context, uiid.convertToUiid("Thumbnail.ICon.Image"));
    var input = uim.walkTo(context, uiid.convertToUiid("Thumbnail.ICon.Input"));
};

TelluriumTestCase.prototype.testBookUiModule = function(){
//    var json = [{"obj":{"uid":"GoogleBooksList","locator":{"tag":"table","attributes":{"id":"hp_table"}},"uiType":"Container"},"key":"GoogleBooksList"},{"obj":{"uid":"category","locator":{"tag":"div","attributes":{"class":"sub_cat_title"}},"uiType":"TextBox"},"key":"GoogleBooksList.category"},{"obj":{"uid":"subcategory","locator":{"tag":"div","attributes":{"class":"sub_cat_section"}},"uiType":"List","separator":"p"},"key":"GoogleBooksList.subcategory"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"GoogleBooksList.subcategory._ALL"}];
//    var json = [{"obj":{"uid":"GoogleBooksList","locator":{"tag":"table","attributes":{"id":"hp_table"}},"uiType":"Container"},"key":"GoogleBooksList"},{"obj":{"uid":"subcategory","locator":{"tag":"td","attributes":{"class":"sidebar"}},"uiType":"List","separator":"div"},"key":"GoogleBooksList.subcategory"},{"obj":{"uid":"all","locator":{"loc":null},"uiType":"Container"},"key":"GoogleBooksList.subcategory._ALL"},{"obj":{"uid":"title","locator":{"tag":"div","attributes":{"class":"sub_cat_title"}},"uiType":"TextBox"},"key":"GoogleBooksList.subcategory._ALL.title"},{"obj":{"uid":"links","locator":{"loc":null},"uiType":"List","separator":"p"},"key":"GoogleBooksList.subcategory._ALL.links"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"GoogleBooksList.subcategory._ALL.links._ALL"}];
//    var json = [{"obj":{"uid":"GoogleBooksList","locator":{"tag":"table","attributes":{"id":"hp_table"}},"uiType":"Container"},"key":"GoogleBooksList"},{"obj":{"uid":"subcategory","locator":{"tag":"td","attributes":{"class":"sidebar"}},"uiType":"List","separator":"div"},"key":"GoogleBooksList.subcategory"},{"obj":{"uid":"all","locator":null,"uiType":"Container"},"key":"GoogleBooksList.subcategory._ALL"},{"obj":{"uid":"title","locator":{"tag":"div","attributes":{"class":"sub_cat_title"}},"uiType":"TextBox"},"key":"GoogleBooksList.subcategory._ALL.title"},{"obj":{"uid":"links","locator":null,"uiType":"List","separator":"p"},"key":"GoogleBooksList.subcategory._ALL.links"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"GoogleBooksList.subcategory._ALL.links._ALL"}];
//    var json = [{"obj":{"uid":"GoogleBooksList","locator":{"tag":"table","attributes":{"id":"hp_table"}},"uiType":"Container"},"key":"GoogleBooksList"},{"obj":{"uid":"subcategory","locator":{"tag":"td","attributes":{"class":"sidebar"}},"uiType":"List","separator":"div"},"key":"GoogleBooksList.subcategory"},{"obj":{"uid":"category","locator":null,"uiType":"Container"},"key":"GoogleBooksList.subcategory.category"},{"obj":{"uid":"title","locator":{"tag":"div","attributes":{"class":"sub_cat_title"}},"uiType":"TextBox","metaData":{"id":"title"}},"key":"GoogleBooksList.subcategory.category.title"},{"obj":{"uid":"links","locator":null,"uiType":"List","separator":"p"},"key":"GoogleBooksList.subcategory.category.links"},{"obj":{"uid":"text","locator":{"tag":"a"},"uiType":"UrlLink","metaData":{"id":"text","index":{"value":"all","type":"VAL"}}},"key":"GoogleBooksList.subcategory.category.links.text"}];
    var json = [{"obj":{"uid":"GoogleBooksList","locator":{"tag":"table","attributes":{"id":"hp_table"}},"uiType":"Container","metaData":{"id":"GoogleBooksList"}},"key":"GoogleBooksList"},{"obj":{"uid":"subcategory","locator":{"tag":"td","attributes":{"class":"sidebar"}},"uiType":"List","separator":"div","metaData":{"id":"subcategory"}},"key":"GoogleBooksList.subcategory"},{"obj":{"uid":"category","locator":null,"uiType":"Container","metaData":{"id":"category","index":{"value":"all","type":"VAL"}}},"key":"GoogleBooksList.subcategory.category"},{"obj":{"uid":"title","locator":{"tag":"div","attributes":{"class":"sub_cat_title"}},"uiType":"TextBox","metaData":{"id":"title"}},"key":"GoogleBooksList.subcategory.category.title"},{"obj":{"uid":"links","locator":null,"uiType":"List","separator":"p","metaData":{"id":"links"}},"key":"GoogleBooksList.subcategory.category.links"},{"obj":{"uid":"text","locator":{"tag":"a"},"uiType":"UrlLink","metaData":{"id":"text","index":{"value":"all","type":"VAL"}}},"key":"GoogleBooksList.subcategory.category.links.text"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
//    var dom = teJQuery("html > body");
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var list = uim.walkTo(context, uiid.convertToUiid("GoogleBooksList"));
//    var category = uim.walkTo(context, uiid.convertToUiid("GoogleBooksList.category"));
    var subcategory = uim.walkTo(context, uiid.convertToUiid("GoogleBooksList.subcategory"));
    var size = subcategory.getListSize(context);
    var subcategorylink1 = uim.walkTo(context, uiid.convertToUiid("GoogleBooksList.subcategory[1]"));
    var links = uim.walkTo(context, uiid.convertToUiid("GoogleBooksList.subcategory[1].links"));
    size = links.getListSize(context);
    var subcategorylink2 = uim.walkTo(context,  uiid.convertToUiid("GoogleBooksList.subcategory[2]"));
};

TelluriumTestCase.prototype.testExpandUiModule = function(){
    var json = [{"obj":{"uid":"expand","locator":{"tag":"a","attributes":{"id":"qrForm:innie"}},"uiType":"UrlLink"},"key":"expand"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var list = uim.walkTo(context, "expand");
};

TelluriumTestCase.prototype.testErisUiModule = function(){
//    var json = [{"obj":{"uid":"EcisPlusUiCAV","locator":{"tag":"table"},"uiType":"Container"},"key":"EcisPlusUiCAV"},{"obj":{"uid":"Save","locator":{"tag":"input","attributes":{"id":"cif:customerInfoSaveButton","name":"cif:customerInfoSaveButton","value":"Save","class":"btn saveButton","type":"button"}},"events":["click"],"uiType":"InputBox"},"key":"EcisPlusUiCAV.Save"}];
    var json = [{"obj":{"uid":"EcisPlusUiCAV","locator":{"tag":"div"},"uiType":"Container"},"key":"EcisPlusUiCAV"},{"obj":{"uid":"Save","locator":{"tag":"input","attributes":{"id":"cif:customerInfoSaveButton","name":"cif:customerInfoSaveButton","value":"Save","class":"btn saveButton","type":"button"}},"events":["click"],"uiType":"InputBox"},"key":"EcisPlusUiCAV.Save"}];
    tellurium.logManager.isUseLog = true;
    var uim = new UiModule();
//    uim.parseUiModule(JSON.stringify(json));
    uim.parseUiModule(json);
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.validate(uim, dom);
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    uiid.convertToUiid("EcisPlusUiCAV.Save");
//    uiid.reverse();
    var save = uim.walkTo(context, uiid);
};

TelluriumTestCase.prototype.testUiCache = function(){

    var json = [{"obj":{"uid":"ProblematicForm","locator":{"tag":"table"},"uiType":"Container"},"key":"ProblematicForm"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"text"}},"uiType":"InputBox"},"key":"ProblematicForm.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"password"}},"uiType":"InputBox"},"key":"ProblematicForm.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"logon","type":"submit"}},"uiType":"SubmitButton"},"key":"ProblematicForm.Submit"}];
    tellurium.logManager.isUseLog = true;
    tellurium.cache.uiAlg.allowRelax = true;
//    tellurium.cache.useUiModule(JSON.stringify(json));  
    tellurium.cache.useUiModule(json);
    tellurium.getUiElementFromCache("ProblematicForm.Username.Input");
    tellurium.getUiElementFromCache("ProblematicForm.Password.Input");
};

TelluriumTestCase.prototype.testSuite = function(){
//    this.testUiid();
//    this.testPrie();
    this.testLogonUiModule(); 
//    this.testEditPageModule();
//    this.testLogicalContainerModule();
//    this.testTelluriumIssueModule();
//    this.testGeneralTableModule();  
//    this.testTelluriumDownloadResult();
//    this.testLogoUiModule();
//    this.testThumbnailUiModule();
//    this.testBookUiModule();
//    this.testExpandUiModule();
//    this.testErisUiModule();
//    this.testUiCache();
//    this.testRepeat();
};
                                   
var teTestCase = new TelluriumTestCase();

