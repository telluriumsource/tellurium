package org.telluriumsource.dsl

import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.framework.Environment;

class DslScriptExecutor {

    public static void main(String[] args){
       if(args != null && args.length == 1){
            def dsl = new File(args[0]).text
            def script = """
                import org.telluriumsource.dsl.DslScriptEngine
                import org.telluriumsource.framework.Environment;
                import org.telluriumsource.crosscut.i18n.IResourceBundle;
                class DslTest extends DslScriptEngine{
                    def test(){
                        init()
                        ${dsl}
                        shutDown()
                        IResourceBundle i18nBundle = Environment.instance.myResourceBundle()

                        println i18nBundle.getMessage("DslScriptExecutor.DslTestDone")
                    }
                }

                DslTest instance = new DslTest()
                instance.test()
            """

            new GroovyShell().evaluate(script)

       }else{
    	   IResourceBundle i18nBundle = Environment.instance.myResourceBundle()

           println i18nBundle.getMessage("DslScriptExecutor.Usage")
       }

    }
}