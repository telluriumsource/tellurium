package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 * Test scenario for overwriting default Table tag
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date:  Date: Feb 28, 2009
 *
 */

public class Table11 extends DslContext {

  public void defineUi() {
    ui.Table(uid: "Actions", clocator: [tag: "", id: "ipclb1", class: "coolBar"], respond: ["mouseDown", "mouseOut", "mouseOver"]) {
      TextBox(uid: "{header: 1}", clocator: [tag: "th"], self: true, respond: ["click"])
      TextBox(uid: "{header: 2}", clocator: [tag: "th"], self: true, respond: ["click"])
      TextBox(uid: "{header: 3}", clocator: [tag: "th"], self: true, respond: ["click"])
      TextBox(uid: "{header: 4}", clocator: [tag: "th"], self: true, respond: ["click"])
      CheckBox(uid: "{row: all, column: 1}", clocator: [tag: "input", type: "checkbox", name: "EntityKey"])
      UrlLink(uid: "{row: all, column: 2}", clocator: [tag: "a", text: "Y100000542"])
      TextBox(uid: "{row: all, column: all}", clocator: [tag: "td", class: "abc"])
    }

    ui.Table(uid: "Test", clocator: [tag: "", id: "xyz"])
  }
 
}