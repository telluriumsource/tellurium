package org.telluriumsource.test.report

import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.framework.config.TelluriumConfigurator

/**
 * Default implementation of Test Listener
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 *
 */
class DefaultResultListener implements ResultListener, Configurable {
    
    private Map<Integer, TestResult> results = new HashMap<Integer, TestResult>()

    //private List<ResultReporter> reporters = new ArrayList<ResultReporter>()
    protected ResultReporter reporter

    protected ResultOutput output

    public DefaultResultListener(){
//        reporters.add(new SimpleResultReporter())
//        reporters.add(new XMLResultReporter())
    }
    
/*
    public void addReporter(ResultReporter reporter){
        reporters.add(reporter)
    }
*/

    public void listenForResult(TestResult result) {
        TestResult tr = results.get(result.getProperty("stepId"))
        if(tr != null){
            List<AssertionResult> lst = result.getAssertionResult()
            if(lst != null && lst.size() > 0){
                lst.each { AssertionResult atr ->
                    tr.addAssertationResult(atr)
                }
            }
            results.put(result.getProperty("stepId"), tr)
        }else{
            results.put(result.getProperty("stepId"), result)
        }
    }

    public void listenForInput(TestResult result) {
        TestResult tr = results.get(result.getProperty("stepId"))
        if(tr != null){
            tr.setProperty("testName", result.getProperty("testName"))
            tr.setProperty("input", result.getProperty("input"))
            tr.setProperty("start", result.getProperty("start"))
            tr.setProperty("end", result.getProperty("end"))
            tr.setProperty("status", result.getProperty("status"))
            if(result.getProperty("exception") != null){
                tr.setProperty("exception", result.getProperty("exception"))
            }
            
            results.put(result.getProperty("stepId"), tr)
        }else{
            results.put(result.getProperty("stepId"), result)
        }
    }

    public void listenForMessage(int step, String message) {
        TestResult tr = results.get(step)
        if(tr != null){
            tr.addMessage(message)
        }else{
            tr = new TestResult()
            tr.setProperty("stepId", step)
            tr.addMessage(message)
        }
        results.put(step, tr)
   }

    public void report() {
        //get the singleton configurator
        TelluriumConfigurator configurator = new TelluriumConfigurator()
        //configure the reader
        configurator.config(this)
        
        if(!this.results.isEmpty()){

            //sort the result by step Id
            List<TestResult> trl = this.results.values().sort { x, y ->
                x.stepId <=> y.stepId
            }

            if(reporter != null && output != null){
                output.output(reporter.report(trl))
            }
/*
            if(!reporters.isEmpty()){
                for(ResultReporter reporter : reporters){
                    reporter.report(trl)
                }
            }
*/
        }
    }

}