grammar Udl;

options {
  language = Java;
}

@header {
  package org.telluriumsource.udl;
  
  import java.util.List;
  import java.util.ArrayList;
  import org.telluriumsource.udl.code.IndexType;
  import org.telluriumsource.udl.Index;
  import org.telluriumsource.udl.MetaData;
  import org.telluriumsource.udl.ListMetaData;
  import org.telluriumsource.udl.TableHeaderMetaData;
  import org.telluriumsource.udl.TableFooterMetaData;
  import org.telluriumsource.udl.TableBodyMetaData;
}

@lexer::header {
  package org.telluriumsource.udl;

  import java.util.List;
  import java.util.ArrayList;
  import org.telluriumsource.udl.code.IndexType;
  import org.telluriumsource.udl.Index;
  import org.telluriumsource.udl.MetaData;
  import org.telluriumsource.udl.ListMetaData;
  import org.telluriumsource.udl.TableHeaderMetaData;
  import org.telluriumsource.udl.TableFooterMetaData;
  import org.telluriumsource.udl.TableBodyMetaData;  
}

@members{
    List<String> variables = new ArrayList<String>();
}

uid	returns [MetaData metadata]
	: 	bu=baseUid (',' variable)* {metadata=bu; metadata.setVariables(variables);}
	|	lu=listUid (',' variable)* {metadata=lu; metadata.setVariables(variables);}
	|	tu=tableUid (',' variable)* {metadata=tu; metadata.setVariables(variables);}
	;
        
baseUid returns [MetaData metadata]
	:	ID {metadata = new MetaData($ID.text);}
	;

variable
	: 'var' ID {variables.add($ID.text);}
	;
	
listUid returns [ListMetaData metadata]
	:       '{' INDEX '}' {metadata = new ListMetaData('_' +  $INDEX.text, $INDEX.text);}
	|	'{' INDEX '}' 'as' ID {metadata = new ListMetaData($ID.text, $INDEX.text);}
	;
		
tableUid returns [MetaData metadata]
	:	thu=tableHeaderUid {metadata = thu;}
	|	tfu=tableFooterUid {metadata = tfu;}
	|	tbu=tableBodyUid {metadata = tbu;}
	;
	
tableHeaderUid returns [TableHeaderMetaData metadata]
	: 	'{' 'header' ':' INDEX '}' {metadata = new TableHeaderMetaData('_'+$INDEX.text, $INDEX.text);}	
	| 	'{' 'header' ':' INDEX '}' 'as' ID {metadata = new TableHeaderMetaData($ID.text, $INDEX.text);}
	;
	
tableFooterUid returns [TableFooterMetaData metadata]
	:       '{' 'footer' ':' INDEX '}' {metadata = new TableFooterMetaData('_'+$INDEX.text, $INDEX.text);}
	|  	'{' 'footer' ':' INDEX '}' 'as' ID {metadata = new TableFooterMetaData($ID.text, $INDEX.text);}
	;
	
tableBodyUid returns [TableBodyMetaData metadata]
        :	'{' 'row' ':' inx1=INDEX ',' 'column' ':' inx2=INDEX '}' {metadata = new TableBodyMetaData("_1_" + inx1.getText() + '_' + inx2.getText()); metadata.setTbody(new Index("1")); metadata.setRow(new Index(inx1.getText())); metadata.setColumn(new Index(inx2.getText()));} 
        |	'{' 'row' ':' inx1=INDEX ',' 'column' ':' inx2=INDEX '}' 'as' ID {metadata = new TableBodyMetaData($ID.text); metadata.setTbody(new Index("1")); metadata.setRow(new Index(inx1.getText())); metadata.setColumn(new Index(inx2.getText())); }
        |       '{' 'row' '->' id1=ID ',' 'column' ':' INDEX '}' {metadata = new TableBodyMetaData("_1_" + id1.getText() + '_' + $INDEX.text); metadata.setTbody(new Index("1")); metadata.setRow(new Index(IndexType.REF, id1.getText()));  metadata.setColumn(new Index($INDEX.text));}
        |	'{' 'row' '->' id1=ID ',' 'column' ':' INDEX '}' 'as' id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setTbody(new Index("1")); metadata.setRow(new Index(IndexType.REF, id1.getText()));  metadata.setColumn(new Index($INDEX.text));}
        |	'{' 'row' ':' INDEX ',' 'column' '->' id1=ID '}' {metadata = new TableBodyMetaData("_1_" + $INDEX.text + '_' + id1.getText()); metadata.setTbody(new Index("1")); metadata.setRow(new Index($INDEX.text)); metadata.setColumn(new Index(IndexType.REF, id1.getText()));}
        |	'{' 'row' ':' INDEX ',' 'column' '->' id1=ID '}' 'as' id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setTbody(new Index("1")); metadata.setRow(new Index($INDEX.text)); metadata.setColumn(new Index(IndexType.REF, id1.getText()));}
        |	'{' 'row' '->' id1=ID ',' 'column' '->' id2=ID '}' {metadata = new TableBodyMetaData("_1_" + id1.getText() + '_' + id2.getText()); metadata.setTbody(new Index("1")); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(IndexType.REF, id2.getText()));}
        |	'{' 'row' '->' id1=ID ',' 'column' '->' id2=ID '}' 'as' id3=ID {metadata = new TableBodyMetaData(id3.getText()); metadata.setTbody(new Index("1")); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(IndexType.REF, id2.getText()));}
        |       '{' 'tbody' ':' inx1=INDEX ',' 'row' ':' inx2=INDEX ',' 'column' ':' inx3=INDEX '}' {metadata = new TableBodyMetaData('_' + inx1.getText() + '_' + inx2.getText() + '_' + inx3.getText()); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(inx2.getText())); metadata.setColumn(new Index(inx3.getText()));}
        |       '{' 'tbody' ':' inx1=INDEX ',' 'row' ':' inx2=INDEX ',' 'column' ':' inx3=INDEX '}' 'as' ID {metadata = new TableBodyMetaData($ID.text); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(inx2.getText())); metadata.setColumn(new Index(inx3.getText()));}
        |	'{' 'tbody' ':' inx1=INDEX ',' 'row' '->' id1=ID ',' 'column' ':' inx2=INDEX '}' {metadata = new TableBodyMetaData('_' + inx1.getText() + '_' + id1.getText() + '_' + inx2.getText()); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(inx2.getText()));}
        |	'{' 'tbody' ':' inx1=INDEX ',' 'row' '->' id1=ID ',' 'column' ':' inx2=INDEX '}' 'as' id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(inx2.getText()));}
        |    	'{' 'tbody' ':' inx1=INDEX ',' 'row' ':' inx2=INDEX ',' 'column' '->' id1=ID '}' {metadata = new TableBodyMetaData('_' + inx1.getText() + '_' + inx2.getText() + '_' + id1.getText()); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(inx2.getText())); metadata.setColumn(new Index(IndexType.REF, id1.getText()));}
        |    	'{' 'tbody' ':' inx1=INDEX ',' 'row' ':' inx2=INDEX ',' 'column' '->' id1=ID '}' 'as' id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(inx2.getText())); metadata.setColumn(new Index(IndexType.REF, id1.getText()));}
        |	'{' 'tbody' ':' INDEX ',' 'row' '->' id1=ID ',' 'column' '->' id2=ID '}' {metadata = new TableBodyMetaData('_' + $INDEX.text + '_' + id1.getText() + '_' + id2.getText()); metadata.setTbody(new Index($INDEX.text)); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(IndexType.REF, id2.getText()));}
        |	'{' 'tbody' ':' INDEX ',' 'row' '->' id1=ID ',' 'column' '->' id2=ID '}' 'as' id3=ID {metadata = new TableBodyMetaData(id3.getText()); metadata.setTbody(new Index($INDEX.text)); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(IndexType.REF, id2.getText()));}
        ;              			
								
fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT  : '0'..'9';
INDEX	:	(DIGIT+ | 'all' | 'odd' | 'even' | 'any' | 'first' | 'last' );  
ID 	: 	LETTER (LETTER | DIGIT | '_')*;
WS 	: 	(' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};