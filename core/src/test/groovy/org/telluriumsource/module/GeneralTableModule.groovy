package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 22, 2010
 * 
 */

public class GeneralTableModule extends DslContext {

  public void defineUi() {
    ui.StandardTable(uid: "GT", clocator: [id: "xyz"], ht: "tbody"){
      TextBox(uid: "{header: all} as Headers", clocator: [:])
      TextBox(uid: "{row: 1, column: 1} as Label", clocator: [tag: "div", class: "abc"])
      Container(uid: "{row: 1, column: 2} as Profile"){
        InputBox(uid: "Input", clocator: [tag: "input", class: "123"])
        Container(uid: "Some", clocator: [tag: "div", class: "someclass"]){
          Span(uid: "Span", clocator: [tag: "span", class: "x"])
          UrlLink(uid: "Link", clocator: [:])
        }
      }
    }
  }

  public void work(String input){
    keyType "GT[1][2].Input", input
    click "GT[1][2].Some.Link"
    waitForPageToLoad 30000
  }

  public void gwork(String input){
    keyType "GT.Profile.Input", input
    click "GT.Profile.Some.Link"
    waitForPageToLoad 30000
  }

public static String HTML_BODY = """
<table id="xyz">
<tbody>
 <tr>
    <th>one</th>
    <th>two</th>
    <th>three</th>
 </tr>
</tbody>

<tbody>
 <tr>
  <td><div class="abc">Profile</div></td>
  <td><input class="123" /><br/>
        <div class="someclass">
            <span class="x">Framework</span><br/>
            <a href="http://code.google.com/p/aost">Tellurium</a>
        </div>
   </td>
   <td>
       Hello World!
   </td>
 </tr>

</tbody>
</table>
  """
}