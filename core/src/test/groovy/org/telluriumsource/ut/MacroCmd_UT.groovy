package org.telluriumsource.ut

import org.telluriumsource.component.bundle.CmdRequest
import org.telluriumsource.component.bundle.MacroCmd

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 22, 2009
 *
 */

public class MacroCmd_UT extends GroovyTestCase {

//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]"],"name":"mouseOver","sequ":1},
//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]","focus"],"name":"fireEvent","sequ":2},
//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]","t"],"name":"keyDown","sequ":3},
//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]","t"],"name":"keyPress","sequ":4},
//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]","t"],"name":"keyUp","sequ":5}

  public void testExtractAllAndConvertToJson(){
    CmdRequest cmd1 = new CmdRequest(1, "Google.Input", "mouseOver", ["jquery=table input[title=Google Search][name=q"]);
    CmdRequest cmd2 = new CmdRequest(2, "Google.Input", "fireEvent", ["jquery=table input[title=Google Search][name=q", "focus"]);
    CmdRequest cmd3 = new CmdRequest(3, "Google.Input", "keyDown", ["jquery=table input[title=Google Search][name=q", "t"]);
    CmdRequest cmd4 = new CmdRequest(4, "Google.Input", "keyPress", ["jquery=table input[title=Google Search][name=q", "t"]);
    CmdRequest cmd5 = new CmdRequest(5, "Google.Input", "keyUp", ["jquery=table input[title=Google Search][name=q", "t"]);

    MacroCmd bundle = new MacroCmd();
    bundle.addToBundle(cmd1);
    bundle.addToBundle(cmd2);
    bundle.addToBundle(cmd3);
    bundle.addToBundle(cmd4);
    bundle.addToBundle(cmd5);
    assertEquals(5, bundle.size());
    String json = bundle.extractAllAndConvertToJson();
    assertEquals(0, bundle.size());
    println(json);
  }
}