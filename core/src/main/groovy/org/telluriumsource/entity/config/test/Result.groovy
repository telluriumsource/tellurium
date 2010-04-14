package org.telluriumsource.entity.config.test

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Result {
  //specify what result reporter used for the test result
  //valid options include "SimpleResultReporter", "XMLResultReporter", and "StreamXMLResultReporter"
  public static String REPORTER = "reporter";
  String reporter = "XMLResultReporter";

  //the output of the result
  //valid options include "Console", "File" at this point
  //if the option is "File", you need to specify the file name, other wise it will use the default
  //file name "TestResults.output"
  public static String OUTPUT = "output";
  String output = "Console";

  //test result output file name
  public static String FILENAME = "filename";
  String filename = "TestResult.output";

  def Result() {
  }

  def Result(Map map) {
    if (map != null) {
      if (map.get(REPORTER) != null)
        this.reporter = map.get(REPORTER);

      if (map.get(OUTPUT) != null)
        this.output = map.get(OUTPUT);

      if (map.get(FILENAME) != null)
        this.filename = map.get(FILENAME);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(REPORTER, this.reporter);
    obj.put(OUTPUT, this.output);
    obj.put(FILENAME, this.filename);

    return obj;
  }

  public void toProperties(Properties properties, String path){
    properties.setProperty(path + "." + REPORTER, this.reporter);
    properties.setProperty(path + "." + OUTPUT, this.output);
    properties.setProperty(path + "." + FILENAME, this.filename);
  }  
}
