package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 5, 2010
 * 
 */
class MenuNavModule extends DslContext {

  public void defineUi(){
    ui.Container(uid: "mainnav", clocator: [tag: "div", class: "mainMenu"], group: true) {
      UrlLink(uid: "events", clocator: [text: "Events"])
      UrlLink(uid: "suppliers", clocator: [text: "Suppliers"])
      UrlLink(uid: "venues", clocator: [text: "Venues"])
      UrlLink(uid: "bookingReport", clocator: [text: "Booking Report"])
      UrlLink(uid: "notifications", clocator: [text: "Notifications"])
      UrlLink(uid: "help", clocator: [text: "Help"])
    }

    ui.Container(uid: "mainnav2", clocator: [tag: "div", class: "mainMenu"]) {
      UrlLink(uid: "events", clocator: [text: "Events"])
      UrlLink(uid: "suppliers", clocator: [text: "Suppliers"])
      UrlLink(uid: "venues", clocator: [text: "Venues"])
      UrlLink(uid: "bookingReport", clocator: [text: "Booking Report"])
      UrlLink(uid: "notifications", clocator: [text: "Notifications"])
      UrlLink(uid: "help", clocator: [text: "Help"])
    }

    ui.Container(uid: "mainnav3", clocator: [tag: "div", class: "welcome"], cacheable: false){
      UrlLink(uid: "Link", clocator: [text: "Menu Nav"])
    }    
  }
}
