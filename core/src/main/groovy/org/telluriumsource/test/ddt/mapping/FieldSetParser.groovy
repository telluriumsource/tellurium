package org.telluriumsource.test.ddt.mapping

import org.telluriumsource.test.ddt.mapping.builder.FieldBuilder
import org.telluriumsource.test.ddt.mapping.builder.FieldSetBuilder
import org.telluriumsource.test.ddt.mapping.builder.IdentifierFieldBuilder
import org.telluriumsource.test.ddt.mapping.builder.TestFieldBuilder

/**
 * parse the Field Set definition
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class FieldSetParser extends BuilderSupport{
    protected final static String FIELD_SET = "FieldSet"
    protected final static String FIELD = "Field"
    protected final static String IDENTIFIER = "Identifier"
    protected final static String TEST = "Test"

    private FieldSetRegistry registry

    public FieldSetParser(FieldSetRegistry registry){
        this.registry = registry
    }
    
    private FieldBuilder fb = new FieldBuilder()
    private FieldSetBuilder fsb = new FieldSetBuilder()
    private IdentifierFieldBuilder fsi = new IdentifierFieldBuilder()
    private TestFieldBuilder afb = new TestFieldBuilder()

    protected void setParent(Object parent, Object child) {
        if (parent instanceof FieldSet) {
            FieldSet fs = (FieldSet)parent
            fs.addField(child)
        }
    }

    protected Object createNode(Object name) {
        if(FIELD_SET.equalsIgnoreCase(name))
            return new FieldSet()
        if(FIELD.equalsIgnoreCase(name))
            return new Field()
        if(IDENTIFIER.equalsIgnoreCase(name))
            return new IdentifierField()
        if(TEST.equalsIgnoreCase(name))
            return new TestField()

        return null
    }

    protected Object createNode(Object name, Object value) {
        return null  
    }

    protected Object createNode(Object name, Map map) {
        if(FIELD_SET.equalsIgnoreCase(name))
            return fsb.build(map)
        if(FIELD.equalsIgnoreCase(name))
            return fb.build(map)
        if(IDENTIFIER.equalsIgnoreCase(name))
            return fsi.build(map)
        if(TEST.equalsIgnoreCase(name))
            return afb.build(map)

        return null
    }

    protected Object createNode(Object name, Map map, Object value) {
        if(FIELD_SET.equalsIgnoreCase(name))
            return fsb.build(map, (Closure)value)

        return null
    }

    protected void nodeCompleted(Object parent, Object node) {
        //when the node is completed and it is a FieldSet, put it into the registry
        if (node instanceof FieldSet) {
            
            FieldSet fs = (FieldSet)node

            //need to check if the identifier is presented
            fs.checkFields()

            //only put the top level nodes into the registry
            registry.addFieldSet(fs)
        }

    }

}