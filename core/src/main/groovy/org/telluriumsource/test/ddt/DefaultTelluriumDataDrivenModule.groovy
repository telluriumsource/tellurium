package org.telluriumsource.test.ddt

import org.telluriumsource.test.ddt.mapping.FieldSetParser
import org.telluriumsource.dsl.UiDslParser

import org.telluriumsource.test.ddt.mapping.type.TypeHandlerRegistry
import org.telluriumsource.test.ddt.mapping.FieldSetRegistry

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 31, 2008
 *
 */
class DefaultTelluriumDataDrivenModule extends TelluriumDataDrivenModule{
    
    public DefaultTelluriumDataDrivenModule(){

    }

    public DefaultTelluriumDataDrivenModule(TypeHandlerRegistry thr, FieldSetRegistry fsr,
        FieldSetParser fs, TestRegistry tr, DataProvider dataProvider){
        this.thr = thr
        this.fsr = fsr
        this.fs = fs
        this.tr = tr
        this.dataProvider = dataProvider
    }

    public FieldSetParser getFieldSetParser(){
        return this.fs
    }

    public void setFieldSetParser(FieldSetParser fs){
        this.fs = fs
    }

    public UiDslParser getUiDslParser(){
        return this.ui
    }

    public void setUiDslParser(UiDslParser ui){
        this.ui = ui
    }

    public DataProvider getDataProvider(){
        return this.dataProvider
    }

/*  XXX: seems this will cause endless loop in groovy and lead to stack overflow 
    public void setDataProvider(DataProvider dataprovider){
        this.dataprovider = dataprovider
    }
*/

    public TypeHandlerRegistry getTypeHandlerRegistry(){
        return this.thr
    }

    public void setTypeHandlerRegistry(TypeHandlerRegistry thr){
        this.thr = thr
    }

    public FieldSetRegistry getFieldSetRegistry(){
        return this.fsr
    }

    public void setFieldSetRegistry(FieldSetRegistry fsr){
       this.fsr = fsr
    }

    public void setTestRegistry(TestRegistry ar){
        this.tr = ar
    }

    public TestRegistry getTestRegistry(){
        return this.tr
    }

    public void defineModule() {

    }
}