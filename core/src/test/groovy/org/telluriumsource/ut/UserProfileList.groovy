package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 7, 2009
 * 
 */

public class UserProfileList extends DslContext {
  public void defineUi() {
    ui.Container(uid: "MainPanel1", clocator: [tag: "div", id: "main"]) {
      List(uid: "UserProfileDetails", clocator: [tag: "div", class: "myBox"], separator: "div") {
        TextBox(uid: "{1}", clocator: [tag: "div"])
        Container(uid: "{all}", clocator: [tag: "div"]) {
          TextBox(uid: "Name", clocator: [tag: "div",
                  position: "1"])
          TextBox(uid: "Value", clocator: [tag: "div",
                  position: "2"])
        }
      }

    }

    ui.Container(uid: "MainPanel2", clocator: [tag: "div", id: "main"]) {
      List(uid: "UserProfileDetails", clocator: [tag: "div", class: "myBox"]) {
        TextBox(uid: "{1}", clocator: [tag: "div", direct: "true"])
        Container(uid: "{all}", clocator: [tag: "div", direct: "true"]) {
          TextBox(uid: "Name", clocator: [tag: "div", direct: "true", position: "1"])
          TextBox(uid: "Value", clocator: [tag: "div", direct: "true", position: "2"])
        }
      }
    }
  }

  
}