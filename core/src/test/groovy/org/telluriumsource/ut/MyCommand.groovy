package org.telluriumsource.ut

import org.telluriumsource.component.connector.CustomCommand

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 23, 2009
 * 
 */

public class MyCommand extends CustomCommand{

    public void typeRepeated(String locator, String text){
		String[] arr = [locator, text];
		commandProcessor.doCommand("typeRepeated", arr);
	}
}