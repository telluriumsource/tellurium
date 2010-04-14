package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 8, 2010
 * 
 */
class RepeatExampleModule extends DslContext {

  public void defineUi(){

    ui.Form(uid: "SailingForm", clocator: [name: "selectedSailingsForm"] ){
      Repeat(uid: "Section", clocator: [tag: "div", class: "segment clearfix"]){
        Repeat(uid: "Option", clocator: [tag: "div", class: "option", direct: "true"]){
          List(uid: "Fares", clocator: [tag: "ul", class: "fares", direct: "true"], separator: "li"){
            Container(uid: "{all}"){
                RadioButton(uid: "radio", clocator: [:], respond: ["click"])
                TextBox(uid: "label", clocator: [tag: "label"])
            }
          }
          Container(uid: "Details", clocator: [tag: "div", class: "details"]){
            Container(uid: "ShipInfo", clocator: [tag: "dl"]){
              TextBox(uid: "ShipLabel", clocator: [tag: "dt", position: "1"])
              TextBox(uid: "Ship", clocator: [tag: "dd", position: "1"])
              TextBox(uid: "DepartureLabel", clocator: [tag: "dt", position: "2"])
              Container(uid: "Departure", clocator: [tag: "dd", position: "2"]){
                TextBox(uid: "Time", clocator: [tag: "em"])
              }
              TextBox(uid: "ArrivalLabel", clocator: [tag: "dt", position: "3"])
              Container(uid: "Arrival", clocator: [tag: "dd", position: "3"]){
                TextBox(uid: "Time", clocator: [tag: "em"])
              }
            }
          }
        }
      }
    }
  }
}
