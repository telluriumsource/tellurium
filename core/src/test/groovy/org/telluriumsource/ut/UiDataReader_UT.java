package org.telluriumsource.ut;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.telluriumsource.trump.UiDataReader;

import java.io.BufferedReader;
import java.util.Map;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 14, 2009
 */
public class UiDataReader_UT {

    @Test
    public void testReadData(){
        StringBuffer sb = new StringBuffer(128);
        sb.append("A | tag : table | /html/body/table[@id='mt']\n");
        sb.append("B | tag : th | /html/body/table[@id='mt']/tbody/tr/th[3]\n");
        sb.append("C | tag : div | /html/body/table[@id='mt']/tbody/tr/th[3]/div\n");
        sb.append("D | tag: div | /html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']\n");
        sb.append("E | tag: table, id: resultstable | /html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']\n");
        sb.append("F | tag: a | /html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']/tbody/tr[2]/td[3]/a\n");

        UiDataReader reader = new UiDataReader();
        BufferedReader br = reader.getReaderForDate(sb.toString());

        Map<String, String> map;

        for(int i=0; i<6; i++){
            map= reader.readData(br);
            assertNotNull(map);
            assertEquals(3, map.size());
        }
        map = reader.readData(br);
        assertNull(map);
    }
}
