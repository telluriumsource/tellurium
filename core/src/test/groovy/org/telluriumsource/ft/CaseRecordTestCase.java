package org.telluriumsource.ft;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.module.CaseRecordModule;
import org.junit.Test;
import org.junit.BeforeClass;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 6, 2010
 */
public class CaseRecordTestCase extends TelluriumJUnitTestCase {
    private static CaseRecordModule crm;

    @BeforeClass
    public static void setup(){
        crm = new CaseRecordModule();
        crm.defineUi();
    }
    @Test
    public void testDump(){
        crm.dump("caseRecordPopUp");
    }
}
