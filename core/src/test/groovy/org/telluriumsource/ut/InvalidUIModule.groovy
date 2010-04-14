package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 *
 * @author Jian Fang(John.Jian.Fang@gmail.com)
 *
 * Date: Jun 16, 2009
 *
 */

public class InvalidUIModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "subnav", clocator: [tag: "ul", id: "subnav"]) {
      Container(uid: "CoreLinks", clocator: [tag: "li", id: "core_links"]) {
        List(uid: "links", clocator: [tag: "ul"], separator: "li") {
          UrlLink(uid: "{all}", clocator: [:])
        }
      }
      UlrLink(uid: "subscribe", colocator: [tag: "li", id: "subscribe"])
    }

  }

}