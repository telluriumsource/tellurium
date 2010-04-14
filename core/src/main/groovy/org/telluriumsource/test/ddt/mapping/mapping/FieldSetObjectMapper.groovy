package org.telluriumsource.test.ddt.mapping.mapping

import org.telluriumsource.test.ddt.mapping.FieldSetType

/**
 *  Interface for the field set object mapping
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
interface FieldSetObjectMapper {

    public FieldSetMapResult mapFieldSet(List data)
    public FieldSetType checkFieldSetType(List data)
    public List readNextLine(BufferedReader inputReader)
 
}