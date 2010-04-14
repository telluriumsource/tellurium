import groovy.grape.Grape;

Grape.grab(group:'org.telluriumsource', module:'tellurium-core', version:'0.7.0-SNAPSHOT', classLoader:this.class.classLoader.rootLoader)
Grape.grab(group:'org.telluriumsource', module:'tellurium-website', version:'0.7.0-SNAPSHOT', classLoader:this.class.classLoader.rootLoader)
Grape.grab(group:'org.stringtree', module:'stringtree-json', version:'2.0.10', classLoader:this.class.classLoader.rootLoader)
Grape.grab(group:'caja', module:'json_simple', version:'r1', classLoader:this.class.classLoader.rootLoader)
Grape.grab(group:'org.seleniumhq.selenium.server', module:'selenium-server', version:'1.0.1-te3-SNAPSHOT', classLoader:this.class.classLoader.rootLoader)
Grape.grab(group:'org.seleniumhq.selenium.client-drivers', module:'selenium-java-client-driver', version:'1.0.1', classLoader:this.class.classLoader.rootLoader)
Grape.grab(group:'org.apache.poi', module:'poi', version:'3.0.1-FINAL', classLoader:this.class.classLoader.rootLoader)
Grape.grab(group:'junit', module:'junit', version:'4.7', classLoader:this.class.classLoader.rootLoader)

import org.telluriumsource.dsl.DslScriptExecutor

@Grapes([
   @Grab(group='org.codehaus.groovy', module='groovy-all', version='1.7.0'),
   @Grab(group='org.seleniumhq.selenium.server', module='selenium-server', version='1.0.1-te3-SNAPSHOT'),
   @Grab(group='org.seleniumhq.selenium.client-drivers', module='selenium-java-client-driver', version='1.0.1'),
   @Grab(group='junit', module='junit', version='4.7'),
   @Grab(group='caja', module='json_simple', version='r1'),
   @Grab(group='org.apache.poi', module='poi', version='3.0.1-FINAL'),
   @Grab(group='org.stringtree', module='stringtree-json', version='2.0.10'),
   @Grab(group='org.telluriumsource', module='tellurium-core', version='0.7.0-SNAPSHOT'),
   @Grab(group='org.telluriumsource', module='tellurium-website', version='0.7.0-SNAPSHOT')
])

def runDsl(String[] args) {
  def cli = new CliBuilder(usage: 'rundsl.groovy -[hf] [scriptname]')
  cli.with {
    h longOpt: 'help', 'Show usage information'
    f longOpt: 'scriptname',   'DSL script name'
  }
  def options = cli.parse(args)
  if (!options) {
    return
  }
  if (options.h) {
    cli.usage()
    return
  }
  if (options.f) {
    def extraArguments = options.arguments()
    if (extraArguments) {
      extraArguments.each {String name ->
        def input = [name].toArray(new String[0])
        DslScriptExecutor.main(input)
      }
    }
  }

}

println "Running DSL test script, press Ctrl+C to stop."

runDsl(args)
