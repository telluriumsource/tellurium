package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext
import org.telluriumsource.entity.UiByTagResponse

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 23, 2009
 * 
 */

public class JettyLogonModule extends DslContext {
  public static String JSON_CONF = """{"tellurium":{"test":{"result":{"reporter":"XMLResultReporter","filename":"TestResult.output","output":"Console"},"exception":{"filenamePattern":"Screenshot?.png","captureScreenshot":false},"execution":{"trace":false}},"accessor":{"checkElement":false},"embeddedserver":{"port":"4444","browserSessionReuse":false,"debugMode":false,"ensureCleanSession":false,"interactive":false,"avoidProxy":false,"timeoutInSeconds":30,"runInternally":true,"trustAllSSLCertificates":true,"useMultiWindows":false,"userExtension":"","profile":""},"uiobject":{"builder":{}},"eventhandler":{"checkElement":false,"extraEvent":false},"i18n":{"locale":"fr_FR"},"connector":{"baseUrl":"http://localhost:8080","port":"4444","browser":"*chrome","customClass":"","serverHost":"localhost","options":""},"bundle":{"maxMacroCmd":5,"useMacroCommand":true},"datadriven":{"dataprovider":{"reader":"PipeFileReader"}},"widget":{"module":{"included":""}}}}""";

  public static String HTML_BODY ="""
      <H1>FORM Authentication demo</H1>

<div class="box-inner">
    <a href="js/tellurium-test.js">Tellurium Test Cases</a>
    <input name="submit" type="submit" value="Test">
</div>

<form method="POST" action="j_security_check">
    <table border="0" cellspacing="2" cellpadding="1">
        <tr>
            <td>Username:</td>
            <td><input size="12" value="" name="j_username" maxlength="25" type="text"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input size="12" value="" name="j_password" maxlength="25" type="password"></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input name="submit" type="submit" value="Login">
            </td>
        </tr>
    </table>
</form>

  """
  
  public void defineUi() {
    ui.Container(uid: "Welcome", clocator: [tag: "div", class: "welcome"]){
      UrlLink(uid: "MenuLink", clocator: [text: "Menu Nav"])  
    }

    ui.Form(uid: "Form", clocator: [tag: "form"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j_username"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j_password"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])
    }

    ui.Form(uid: "ProblematicForm", clocator: [tag: "form"]){
        Container(uid: "Username", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]){
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j"])
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "logon", name: "submit"])
    }

    ui.Form(uid: "AbstractForm", clocator: [tag: "form"]) {
      Container(uid: "Form1") {
        Container(uid: "Username", clocator: [tag: "tr"]) {
          TextBox(uid: "Label", clocator: [tag: "td", text: "Username:", direct: "true"])
          InputBox(uid: "Input", clocator: [tag: "input", type: "text", name: "j_username"])
        }
        Container(uid: "Password", clocator: [tag: "tr"]) {
          Container(uid: "Password1") {
            TextBox(uid: "Label", clocator: [tag: "td", text: "Password:", direct: "true"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "password", name: "j_password"])
          }
        }
        SubmitButton(uid: "Submit", clocator: [tag: "input", type: "submit", value: "Login", name: "submit"])

      }
    }

    ui.Image(uid: "Logo", clocator: [tag: "img", src: "*.gif", alt: "Logo"])

    ui.Container(uid: "Thumbnail", clocator: [tag: "div", class: "thumbnail potd"]){
        Container(uid: "ICon", clocator: [tag: "div", class: "potd:icon png.fix"]){
            Image(uid: "Image", clocator:  [tag: "img", src: "*.jpg"])
            InputBox(uid: "Input", clocator: [tag: "input", type: "text", id: "Image:name"])
        }
    }
  }

  public void logon(String username, String password){
    keyType "Form.Username.Input", username
    keyType "Form.Password.Input", password
    click "Form.Submit"
    waitForPageToLoad 30000
  }

  public void alogon(String username, String password){
    keyType "AbstractForm.Form1.Username.Input", username
    keyType "AbstractForm.Form1.Password.Password1.Input", password
    click "AbstractForm.Form1.Submit"
    waitForPageToLoad 30000
  }

  public void plogon(String username, String password){
    keyType "ProblematicForm.Username.Input", username
    keyType "ProblematicForm.Password.Input", password
    click "ProblematicForm.Submit"
    waitForPageToLoad 30000
  }

  public String getLogoAlt(){
    return getImageAlt("Logo")
  }

  public String getImageAlt(){
    return getImageAlt("Thumbnail.ICon.Image")
  }

  public void typeImageName(String name){
    keyType "Thumbnail.ICon.Input", name
    pause 500
  }

  public String[] getInputBox(){
     def attrs = ["type" : "text"];
     UiByTagResponse resp = getUiByTag("input", attrs);
    
     return resp.tids;
  }
}