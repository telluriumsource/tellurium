package org.telluriumsource.ut

import org.telluriumsource.trump.UiParser

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 14, 2009
 * 
 */
class UiParser_UT extends GroovyTestCase{


    public void testParseData() {
        String data = """
        A | tag : table | /html/body/table[@id='mt']
        B | tag : th | /html/body/table[@id='mt']/tbody/tr/th[3]
        C | tag : div | /html/body/table[@id='mt']/tbody/tr/th[3]/div
        D | tag: div | /html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']
        E | tag: table, id: resultstable | /html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']
        F | tag: a | /html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']/tbody/tr[2]/td[3]/a
    """
        UiParser parser = new UiParser();
        parser.parseData(data);
    }

    public void testGoogle() {
        String google = """
        InputBox | tag: input, name: q |  /html/body/center/form/table[1]/tbody/tr/td[2]/input[4]
        GoogleSearch | tag: input, type: submit, name: btnG | /html/body/center/form/table[1]/tbody/tr/td[2]/input[5]
        FeelingLucky | tag: input, type: submit, name: btnI | /html/body/center/form/table[1]/tbody/tr/td[2]/input[6]
      """
        UiParser parser = new UiParser();
        parser.parseData(google);
    }

    public void testGoogleMore() {
        String google = """
        Gmail | tag: a | /html/body/div[@id='gbar']/nobr/a[@class='gb1' and position()=5]
        InputBox | tag: input, name: q |  /html/body/center/form/table[1]/tbody/tr/td[2]/input[4]
        GoogleSearch | tag: input, type: submit, name: btnG | /html/body/center/form/table[1]/tbody/tr/td[2]/input[5]
        FeelingLucky | tag: input, type: submit, name: btnI | /html/body/center/form/table[1]/tbody/tr/td[2]/input[6]
        AdvancedSearch | tag: a | /html/body/center/form/table[1]/tbody/tr/td[3]/font/a[1]
      """
        UiParser parser = new UiParser();
        parser.parseData(google);
    }
}