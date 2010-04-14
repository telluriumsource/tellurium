package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 8, 2010
 * 
 */
class ListExampleModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"]) {
      List(uid: "subcategory", clocator: [tag: "td", class: "sidebar"], separator: "div") {
        Container(uid: "{all}") {
          TextBox(uid: "title", clocator: [tag: "div", class: "sub_cat_title"])
          List(uid: "links", separator: "p") {
            UrlLink(uid: "{all}", clocator: [:])
          }
        }
      }
    }
  }

  
  
}
