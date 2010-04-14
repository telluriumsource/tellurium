package org.telluriumsource.test.report

import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.framework.config.TelluriumConfigurator

/**
 * Ouput the results as a file
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 4, 2008
 *
 */
class FileOutput implements ResultOutput, Configurable{
    protected String fileName

    public FileOutput(){
        //get the singleton configurator
        TelluriumConfigurator configurator = new TelluriumConfigurator()
        //configure the reader
        configurator.config(this)
    }

    public String output(String results) {
        File wf= new File(fileName)
        wf.append(results)

        return results
    }
}