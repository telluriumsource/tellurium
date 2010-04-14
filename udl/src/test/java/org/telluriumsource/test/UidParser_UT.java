package org.telluriumsource.test;

import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.telluriumsource.udl.*;
import org.telluriumsource.udl.code.IndexType;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Mar 31, 2010
 */
public class UidParser_UT {

    @Test
    public void testBaseUid() {
        try {
            MetaData data = UidParser.parse("Tellurium");
            assertNotNull(data);
            assertEquals("Tellurium", data.getId());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testListUid() {
        try {
            MetaData data = UidParser.parse("{ odd } as T");
            assertNotNull(data);
            assertEquals("T", data.getId());
            assertTrue(data instanceof ListMetaData);
            ListMetaData lm = (ListMetaData) data;
            assertEquals("odd", lm.getIndex().getValue());
            assertEquals(IndexType.VAL, lm.getIndex().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testListUidNoId() {
        try {
            MetaData data = UidParser.parse("{10}");
            assertNotNull(data);
            assertEquals("_10", data.getId());
            assertTrue(data instanceof ListMetaData);
            ListMetaData lm = (ListMetaData) data;
            assertEquals("10", lm.getIndex().getValue());
            assertEquals(IndexType.VAL, lm.getIndex().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testTableHeaderUidNoId() {
        try {
            MetaData data = UidParser.parse("{header: 3}");
            assertNotNull(data);
            assertTrue(data instanceof TableHeaderMetaData);
            TableHeaderMetaData th = (TableHeaderMetaData) data;
            assertEquals("_3", th.getId());
            assertEquals("3", th.getIndex().getValue());
            assertEquals(IndexType.VAL, th.getIndex().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableHeaderUid() {
        try {
            MetaData data = UidParser.parse("{header: 3} as A");
            assertNotNull(data);
            assertTrue(data instanceof TableHeaderMetaData);
            TableHeaderMetaData th = (TableHeaderMetaData) data;
            assertEquals("A", th.getId());
            assertEquals("3", th.getIndex().getValue());
            assertEquals(IndexType.VAL, th.getIndex().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableFooterUidNoId() {
        try {
            MetaData data = UidParser.parse("{footer: all}");
            assertNotNull(data);
            assertTrue(data instanceof TableFooterMetaData);
            TableFooterMetaData th = (TableFooterMetaData) data;
            assertEquals("_all", th.getId());
            assertEquals("all", th.getIndex().getValue());
            assertEquals(IndexType.VAL, th.getIndex().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableFooterUid() {
        try {
            MetaData data = UidParser.parse("{footer: all} as B");
            assertNotNull(data);
            assertTrue(data instanceof TableFooterMetaData);
            TableFooterMetaData th = (TableFooterMetaData) data;
            assertEquals("B", th.getId());
            assertEquals("all", th.getIndex().getValue());
            assertEquals(IndexType.VAL, th.getIndex().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyValUidNoId() {
        try {
            MetaData data = UidParser.parse("{tbody : 1, row : 2, column : 3}");
            assertNotNull(data);
            assertEquals("_1_2_3", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tb = (TableBodyMetaData) data;
            assertEquals("1", tb.getTbody().getValue());
            assertEquals(IndexType.VAL, tb.getTbody().getType());
            assertEquals("2", tb.getRow().getValue());
            assertEquals(IndexType.VAL, tb.getRow().getType());
            assertEquals("3", tb.getColumn().getValue());
            assertEquals(IndexType.VAL, tb.getColumn().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyValUid() {
        try {
            MetaData data = UidParser.parse("{tbody : 1, row : 2, column : 3} as Search");
            assertNotNull(data);
            assertEquals("Search", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tb = (TableBodyMetaData) data;
            assertEquals("1", tb.getTbody().getValue());
            assertEquals(IndexType.VAL, tb.getTbody().getType());
            assertEquals("2", tb.getRow().getValue());
            assertEquals(IndexType.VAL, tb.getRow().getType());
            assertEquals("3", tb.getColumn().getValue());
            assertEquals(IndexType.VAL, tb.getColumn().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyRefUidNoId() {
        try {
            MetaData data = UidParser.parse("{tbody : 1, row -> good, column -> bad}");
            assertNotNull(data);
            assertEquals("_1_good_bad", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tbmd = (TableBodyMetaData) data;
            assertEquals("1", tbmd.getTbody().getValue());
            assertEquals(IndexType.VAL, tbmd.getTbody().getType());
            assertEquals("good", tbmd.getRow().getValue());
            assertEquals(IndexType.REF, tbmd.getRow().getType());
            assertEquals("bad", tbmd.getColumn().getValue());
            assertEquals(IndexType.REF, tbmd.getColumn().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyRefUid() {
        try {
            MetaData data = UidParser.parse("{tbody : 1, row -> good, column -> bad} as Search");
            assertNotNull(data);
            assertEquals("Search", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tbmd = (TableBodyMetaData) data;
            assertEquals("1", tbmd.getTbody().getValue());
            assertEquals(IndexType.VAL, tbmd.getTbody().getType());
            assertEquals("good", tbmd.getRow().getValue());
            assertEquals(IndexType.REF, tbmd.getRow().getType());
            assertEquals("bad", tbmd.getColumn().getValue());
            assertEquals(IndexType.REF, tbmd.getColumn().getType());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyMixedUidNoId() {
        try {
            MetaData data = UidParser.parse("{row:3, column-> bad}");
            assertNotNull(data);
            assertEquals("_1_3_bad", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tbmd = (TableBodyMetaData) data;
            assertEquals("1", tbmd.getTbody().getValue());
            assertEquals(IndexType.VAL, tbmd.getTbody().getType());
            assertEquals("3", tbmd.getRow().getValue());
            assertEquals(IndexType.VAL, tbmd.getRow().getType());
            assertEquals("bad", tbmd.getColumn().getValue());
            assertEquals(IndexType.REF, tbmd.getColumn().getType());
        } catch (RecognitionException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyMixedUid() {
        try {
            MetaData data = UidParser.parse("{row:3, column -> bad} as Search");
            assertNotNull(data);
            assertEquals("Search", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tbmd = (TableBodyMetaData) data;
            assertEquals("1", tbmd.getTbody().getValue());
            assertEquals(IndexType.VAL, tbmd.getTbody().getType());
            assertEquals("3", tbmd.getRow().getValue());
            assertEquals(IndexType.VAL, tbmd.getRow().getType());
            assertEquals("bad", tbmd.getColumn().getValue());
            assertEquals(IndexType.REF, tbmd.getColumn().getType());
        } catch (RecognitionException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testHeaderAsId() {

        try {
            MetaData data = UidParser.parse("Header");
            assertNotNull(data);
            assertEquals("Header", data.getId());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testRowAsId() {
        try {
            MetaData data = UidParser.parse("Row");
            assertNotNull(data);
            assertEquals("Row", data.getId());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testVariableWithBaseUi() {
        try {
            MetaData data = UidParser.parse("Tellurium, var key, var value");
            assertNotNull(data);
            assertEquals("Tellurium", data.getId());
            List<String> variables = data.getVariables();
            assertNotNull(variables);
            assertEquals(2, variables.size());
            assertEquals("key", variables.get(0));
            assertEquals("value", variables.get(1));
            System.out.println(data.toJSON().toString());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testVariableWithList() {
        try {
            MetaData data = UidParser.parse("{10} as A, var id1, var id_b");
            assertNotNull(data);
            assertEquals("A", data.getId());
            assertTrue(data instanceof ListMetaData);
            ListMetaData lm = (ListMetaData) data;
            assertEquals("10", lm.getIndex().getValue());
            assertEquals(IndexType.VAL, lm.getIndex().getType());
            List<String> variables = data.getVariables();
            assertNotNull(variables);
            assertEquals(2, variables.size());
            assertEquals("id1", variables.get(0));
            assertEquals("id_b", variables.get(1));            
            System.out.println(data.toJSON().toString());
        } catch (RecognitionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testVariablewithTable(){
         try {
            MetaData data = UidParser.parse("{row:3, column -> bad} as Search, var id, var name, var text");
            assertNotNull(data);
            assertEquals("Search", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tbmd = (TableBodyMetaData) data;
            assertEquals("1", tbmd.getTbody().getValue());
            assertEquals(IndexType.VAL, tbmd.getTbody().getType());
            assertEquals("3", tbmd.getRow().getValue());
            assertEquals(IndexType.VAL, tbmd.getRow().getType());
            assertEquals("bad", tbmd.getColumn().getValue());
            assertEquals(IndexType.REF, tbmd.getColumn().getType());
            List<String> variables = data.getVariables();
            assertNotNull(variables);
            assertEquals(3, variables.size());
            assertEquals("id", variables.get(0));
            assertEquals("name", variables.get(1));
            assertEquals("text", variables.get(2));
            System.out.println(data.toJSON().toString());
        } catch (RecognitionException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
