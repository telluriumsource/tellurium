// http://dogself.com/telluriumTest/index4.htm

var json = "[{\"uid\":\"Google.Input\",\"args\":[\"jquery=table input[title=Google Search][name=q\"],\"name\":\"mouseOver\",\"sequ\":1},{\"uid\":\"Google.Input\",\"args\":[\"jquery=table input[title=Google Search][name=q\",\"focus\"],\"name\":\"fireEvent\",\"sequ\":2},{\"uid\":\"Google.Input\",\"args\":[\"jquery=table input[title=Google Search][name=q\",\"t\"],\"name\":\"keyDown\",\"sequ\":3},{\"uid\":\"Google.Input\",\"args\":[\"jquery=table input[title=Google Search][name=q\",\"t\"],\"name\":\"keyPress\",\"sequ\":4},{\"uid\":\"Google.Input\",\"args\":[\"jquery=table input[title=Google Search][name=q\",\"t\"],\"name\":\"keyUp\",\"sequ\":5}]"

var cmdbundle = JSON.parse(json, null);

alert(cmdbundle.length);

alert(cmdbundle[0].uid);
alert(cmdbundle[4].args);