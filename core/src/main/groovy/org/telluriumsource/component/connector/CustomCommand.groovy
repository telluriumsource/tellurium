package org.telluriumsource.component.connector

import com.thoughtworks.selenium.CommandProcessor

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 23, 2009
 * 
 */

abstract public class CustomCommand {

    protected CommandProcessor commandProcessor

    public void setProcessor(CommandProcessor processor){
      this.commandProcessor = processor
    }

    public CommandProcessor getProcessor(){
      return this.commandProcessor
    }
}