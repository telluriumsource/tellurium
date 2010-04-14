package org.telluriumsource.framework.config

import java.util.Locale;

import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.stringtree.json.JSONReader
import org.telluriumsource.entity.config.Config;


/**
 * Parse Tellurium configuration and store the properties to a Hashmap
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 2, 2008
 *
 */
class TelluriumConfigParser {

    protected def conf
    protected Properties props

    protected IResourceBundle i18nBundle

    public TelluriumConfigParser(){
    	i18nBundle = Environment.instance.myResourceBundle()
    }
    public void parse(String fileName){
       try{
            println i18nBundle.getMessage("TelluriumConfigParser.parseConfigFileText" , fileName)
//            conf = new ConfigSlurper().parse(new File(fileName).toURL())
            conf = new ConfigSlurper().parse(ClassLoader.getSystemResource(fileName))
            //convert the ConfigObject to properties to check if it is defined
            props = conf.toProperties()
       }catch(Exception e){
            conf = null
            println i18nBundle.getMessage("TelluriumConfigParser.cannotOpenConfigFile" , e.getMessage())
       }
    }

    public void parse(File file){
       try{
          conf = new ConfigSlurper().parse(file.toURL())
         //convert the ConfigObject to properties to check if it is defined
          props = conf.toProperties()
       }catch(Exception e){
          conf = null
          println i18nBundle.getMessage("TelluriumConfigParser.cannotOpenConfigFile" , e.getMessage())
       }
    }

    public void parse(URL url){
       try{
          conf = new ConfigSlurper().parse(url)
         //convert the ConfigObject to properties to check if it is defined
          props = conf.toProperties()
       }catch(Exception e){
          conf = null
          println i18nBundle.getMessage("TelluriumConfigParser.cannotOpenConfigFile" , e.getMessage())
       }
    }

    public void parseJSON(String json){
      try {
        JSONReader reader = new JSONReader();
        Map map = reader.read(json);
        conf = new Config(map);
        props = conf.toProperties();
      } catch (Exception e) {
        conf = null
        println i18nBundle.getMessage("TelluriumConfigParser.cannotParseJSONConfig", e.getMessage())
      }
    }
}