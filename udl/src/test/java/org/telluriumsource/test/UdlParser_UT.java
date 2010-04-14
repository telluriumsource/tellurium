package org.telluriumsource.test;

import org.junit.Ignore;
import org.junit.Test;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.telluriumsource.udl.*;
import org.telluriumsource.udl.code.IndexType;

import static org.junit.Assert.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 18, 2010
 */
public class UdlParser_UT {

    @Test
    public void testBaseUid(){
 		CharStream stream =
			new ANTLRStringStream("Tellurium");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("Tellurium", data.getId());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }        
    }

    @Test
    public void testListUid(){
  		CharStream stream =
			new ANTLRStringStream("{ odd } as T");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("T", data.getId());
            assertTrue(data instanceof ListMetaData);
            ListMetaData lm = (ListMetaData)data;
            assertEquals("odd", lm.getIndex().getValue());
            assertEquals(IndexType.VAL, lm.getIndex().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testListUidNoId(){
  		CharStream stream =
			new ANTLRStringStream("{10}");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("_10", data.getId());
            assertTrue(data instanceof ListMetaData);
            ListMetaData lm = (ListMetaData)data;
            assertEquals("10", lm.getIndex().getValue());
            assertEquals(IndexType.VAL, lm.getIndex().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Ignore
    @Test
    public void testStarListUid(){
  		CharStream stream =
			new ANTLRStringStream("{*}");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("_all", data.getId());
            assertTrue(data instanceof ListMetaData);
            ListMetaData lm = (ListMetaData)data;
            assertEquals("all", lm.getIndex().getValue());
            assertEquals(IndexType.VAL, lm.getIndex().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

     @Test
    public void testTableHeaderUidNoId(){
 		CharStream stream =
			new ANTLRStringStream("{header: 3}");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertTrue(data instanceof TableHeaderMetaData);
            TableHeaderMetaData th = (TableHeaderMetaData)data;
            assertEquals("_3", th.getId());
            assertEquals("3", th.getIndex().getValue());
            assertEquals(IndexType.VAL, th.getIndex().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableHeaderUid(){
 		CharStream stream =
			new ANTLRStringStream("{header: 3} as A");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertTrue(data instanceof TableHeaderMetaData);
            TableHeaderMetaData th = (TableHeaderMetaData)data;
            assertEquals("A", th.getId());
            assertEquals("3", th.getIndex().getValue());
            assertEquals(IndexType.VAL, th.getIndex().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableFooterUidNoId(){
 		CharStream stream =
			new ANTLRStringStream("{footer: all}");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertTrue(data instanceof TableFooterMetaData);
            TableFooterMetaData th = (TableFooterMetaData)data;
            assertEquals("_all", th.getId());
            assertEquals("all", th.getIndex().getValue());
            assertEquals(IndexType.VAL, th.getIndex().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableFooterUid(){
 		CharStream stream =
			new ANTLRStringStream("{footer: all} as B");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertTrue(data instanceof TableFooterMetaData);
            TableFooterMetaData th = (TableFooterMetaData)data;
            assertEquals("B", th.getId());
            assertEquals("all", th.getIndex().getValue());
            assertEquals(IndexType.VAL, th.getIndex().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyValUidNoId(){
		CharStream stream =
			new ANTLRStringStream("{tbody : 1, row : 2, column : 3}");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("_1_2_3", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tb = (TableBodyMetaData)data;
            assertEquals("1", tb.getTbody().getValue());
            assertEquals(IndexType.VAL, tb.getTbody().getType());
            assertEquals("2", tb.getRow().getValue());
            assertEquals(IndexType.VAL, tb.getRow().getType());
            assertEquals("3", tb.getColumn().getValue());
            assertEquals(IndexType.VAL, tb.getColumn().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyValUid(){
		CharStream stream =
			new ANTLRStringStream("{tbody : 1, row : 2, column : 3} as Search");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("Search", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tb = (TableBodyMetaData)data;
            assertEquals("1", tb.getTbody().getValue());
            assertEquals(IndexType.VAL, tb.getTbody().getType());
            assertEquals("2", tb.getRow().getValue());
            assertEquals(IndexType.VAL, tb.getRow().getType());
            assertEquals("3", tb.getColumn().getValue());
            assertEquals(IndexType.VAL, tb.getColumn().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyRefUidNoId(){
		CharStream stream =
			new ANTLRStringStream("{tbody : 1, row -> good, column -> bad}");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("_1_good_bad", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tbmd = (TableBodyMetaData)data;
            assertEquals("1", tbmd.getTbody().getValue());
            assertEquals(IndexType.VAL, tbmd.getTbody().getType());
            assertEquals("good", tbmd.getRow().getValue());
            assertEquals(IndexType.REF, tbmd.getRow().getType());
            assertEquals("bad", tbmd.getColumn().getValue());
            assertEquals(IndexType.REF, tbmd.getColumn().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyRefUid(){
		CharStream stream =
			new ANTLRStringStream("{tbody : 1, row -> good, column -> bad} as Search");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("Search", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tbmd = (TableBodyMetaData)data;
            assertEquals("1", tbmd.getTbody().getValue());
            assertEquals(IndexType.VAL, tbmd.getTbody().getType());
            assertEquals("good", tbmd.getRow().getValue());
            assertEquals(IndexType.REF, tbmd.getRow().getType());
            assertEquals("bad", tbmd.getColumn().getValue());
            assertEquals(IndexType.REF, tbmd.getColumn().getType());
        }catch(RecognitionException e){
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
}
