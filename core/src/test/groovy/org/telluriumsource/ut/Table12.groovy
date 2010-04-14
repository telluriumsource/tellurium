package org.telluriumsource.ut

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 17, 2009
 * 
 */

public class Table12 extends DslContext{
  public void defineUI(){
    ui.Table(uid: "OrderDetail", clocator: [tag: "table", class: "table"]){
      TextBox(uid: "{row: 1, column: 1}", clocator: [tag: "td", text: "PO Number", class: "tablecolumnheader"], self: true)
      TextBox(uid: "{row: 2, column: 1}", clocator: [tag: "td", text: "Buyer", class: "tablecolumnheader"], self: true)
      TextBox(uid: "{row: 3, column: 1}", clocator: [tag: "td", text: "Vendor", class: "tablecolumnheader"], self: true)
      TextBox(uid: "{row: 4, column: 1}", clocator: [tag: "td", text: "Department", class: "tablecolumnheader"], self: true)
      TextBox(uid: "{row: 5, column: 1}", clocator: [tag: "td", text: "Order Date", class: "tablecolumnheader"], self: true)
      TextBox(uid: "{row: 1, column: 2}", clocator: [tag: "td", text: "Extended Cost", class: "tablecolumnheader"], self: true)
      TextBox(uid: "{row: 2, column: 2}", clocator: [tag: "td", text: "Extended Retail", class: "tablecolumnheader"], self: true)
      TextBox(uid: "{row: 3, column: 3}", clocator: [tag: "td", text: "Markup %", class: "tablecolumnheader"], self: true)
      TextBox(uid: "{row: 4, column: 4}", clocator: [tag: "td", text: "Days from ", class: "tablecolumnheader"], self: true)
      TextBox(uid: "{row: 5, column: 5}", clocator: [tag: "td", text: "Status", class: "tablecolumnheader"], self: true)
    }

    ui.Table(uid: "chromeDoctorSearchResultsresultstable", clocator: [id: "resultsTable"], group: "true", namespace: "html") {
      TextBox(uid: "{row: all, column:1}", namespace: "html")  /* CardId */
      TextBox(uid: "{row: all, column:2}", namespace: "html")  /* First Name */
      TextBox(uid: "{row: all, column:3}", namespace: "html")  /* Last Name */
      TextBox(uid: "{row: all, column:4}", namespace: "html")  /*Phone Number */
      Container(uid: "{row: all, column:5}", namespace: "html") {  /* Operations */
        UrlLink(uid: "edit", clocator: [text: "View/Edit Records"], namespace: "html")
        UrlLink(uid: "summary", clocator: [text: "Summary"], namespace: "html")
      }
    }
    
  }
}