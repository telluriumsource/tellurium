package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 6, 2010
 * 
 */

public class CaseRecordModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "caseRecordPopUp", clocator: [id: "CaseRecordsPopUp", tag: "div"], namespace: "xhtml", group: "true") {
        Container(uid: "date", clocator: [id: "caseRecordPopUpDate", tag: "input"], namespace: "xforms")
        Container(uid: "complaints", clocator: [id: "caseRecordPopUpComplaints", tag: "input"], namespace: "xforms")
        Container(uid: "weight", clocator: [id: "caseRecordPopUpWeight", tag: "input"], namespace: "xforms")
        Container(uid: "bp", clocator: [id: "caseRecordPopUpBP", tag: "input"], namespace: "xforms")
        Container(uid: "fh", clocator: [id: "caseRecordPopUpFH", tag: "input"], namespace: "xforms")
        Container(uid: "fhr", clocator: [id: "caseRecordPopUpFHR", tag: "input"], namespace: "xforms")
        Container(uid: "po", clocator: [id: "caseRecordPopUpPO", tag: "textarea"], namespace: "xforms")
        Container(uid: "pv", clocator: [id: "caseRecordPopUpPV", tag: "textarea"], namespace: "xforms")
        Container(uid: "remarks", clocator: [id: "caseRecordPopUpRemarks", tag: "textarea"], namespace: "xforms")
    }

  }

}