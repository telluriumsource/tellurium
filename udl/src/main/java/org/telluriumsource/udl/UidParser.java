package org.telluriumsource.udl;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 19, 2010
 */
public class UidParser {
    
    public static MetaData parse(String uid) throws RecognitionException {
        if (uid == null || uid.trim().length() == 0)
            return null;

        CharStream stream = new ANTLRStringStream(uid);
        UdlLexer lexer = new UdlLexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        UdlParser parser = new UdlParser(tokenStream);
        return parser.uid();
    }
}
