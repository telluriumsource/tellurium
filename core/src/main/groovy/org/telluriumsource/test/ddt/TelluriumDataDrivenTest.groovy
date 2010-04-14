package org.telluriumsource.test.ddt

import junit.framework.AssertionFailedError
import org.telluriumsource.framework.bootstrap.TelluriumSupport
import org.telluriumsource.component.connector.SeleniumConnector

import org.telluriumsource.test.ddt.mapping.FieldSet
import org.telluriumsource.test.ddt.mapping.FieldSetParser
import org.telluriumsource.test.ddt.mapping.FieldSetRegistry
import org.telluriumsource.test.ddt.mapping.TestField
import org.telluriumsource.test.ddt.mapping.mapping.FieldSetMapResult
import org.telluriumsource.test.ddt.mapping.type.TypeHandlerRegistry
import org.telluriumsource.dsl.UiDslParser

import org.telluriumsource.test.report.*
import org.telluriumsource.test.groovy.BaseTelluriumGroovyTestCase
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle

/**
 * Tellurium Data Driven test and it can include multiple data driven modules so that you do not have
 * to overflow this class with your UI, FieldSet, and Action definitions.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 31, 2008
 *
 */
abstract class TelluriumDataDrivenTest extends BaseTelluriumGroovyTestCase {

  protected static final String STEP = "step"
    protected static final String STEP_OVER = "stepOver"
    protected static final String STEP_TO_END = "stepToEnd"
    protected static final String CLOSE_DATA = "closeData"
    protected static final String COMPARE_RESULT = "compareResult"
    protected static final String RECORD_RESULT = "recordResult"

    protected TypeHandlerRegistry thr

    protected FieldSetRegistry fsr

    protected DataProvider dataProvider

    protected FieldSetParser fs

    protected TestRegistry testreg

    protected ResultListener listener

    protected DefaultTelluriumDataDrivenModule dtddm
    //= new DefaultTelluriumDataDrivenModule(thr, fsr, fs, testreg, dataprovider)

    protected UiDslParser ui
    //= dtddm.getUiDslParser()

//    protected TelluriumFramework tellurium

//    protected TelluriumDataDrivenModuleInterceptor interceptor = new TelluriumDataDrivenModuleInterceptor(this)

 //   def proxy = ProxyMetaClass.getInstance(DefaultTelluriumDataDrivenModule.class)

    //--------------------------------------------------------------------------------------------------------
    // Abstract method for child class to implement
    //--------------------------------------------------------------------------------------------------------

    //put all your test script here
    //For data stepToEnd test, you will only have one test method where you should put all
    //your test script there
    abstract void testDataDriven()

    //----------------------------------------------------------------------------------------------------------
    // Test management methods
    //----------------------------------------------------------------------------------------------------------

//    protected SeleniumConnector connector

    public void openUrl(String url){
        getConnector().connect(url)
    }

    public void connectUrl(String url){
        getConnector().connectUrl(url)
    }

    public SeleniumConnector getConnector() {
        if(this.conn == null)
            return new SeleniumConnector();
        else
            return this.conn;
    }

    protected def init(){
        tellurium = TelluriumSupport.addSupport()
        //should put here, other the UI builder is not updated with customer settings in TelluriumConfig.groovy
        thr  = new TypeHandlerRegistry()
        fsr = new FieldSetRegistry()
        dataProvider = new DataProvider(fsr, thr)
        fs = new FieldSetParser(fsr)
        testreg = new TestRegistry()
        listener = new DefaultResultListener()
        dtddm = new DefaultTelluriumDataDrivenModule(thr, fsr, fs, testreg, dataProvider)
        ui = dtddm.getUiDslParser()
        tellurium.start(customConfig)
        this.conn = tellurium.getConnector()
        connectSeleniumServer();
   }

     protected void shutDown() {
        if(tellurium != null)
            tellurium.stop()
     }

    public void setUp(){
        init()
    }

    public void tearDown(){
        shutDown()
    }

    //----------------------------------------------------------------------------------------------------------
    // Method delegation
    //----------------------------------------------------------------------------------------------------------

    //try to delegate missing methods to the DdDslContext, if still could not find,
    //throw a MissingMethodException
    protected def methodMissing(String name, args) {
        if(COMPARE_RESULT.equals(name)){
            //call recordResult(expected, actual)
            return this.invokeMethod(RECORD_RESULT, args)
        }

        if(dtddm.metaClass.respondsTo(dtddm, name, args)){
              return dtddm.invokeMethod(name, args)
        }

        throw new MissingMethodException(name, TelluriumDataDrivenTest.class, args)
     }


    //----------------------------------------------------------------------------------------------------
    // Method to include modules
    //----------------------------------------------------------------------------------------------------


    //method to include other data driven modules in the format of
    //includeModule abc.dad.FModule.class
    public void includeModule(Class module){
        if(TelluriumDataDrivenModule.class.isAssignableFrom(module)){
            //switch variables
            TelluriumDataDrivenModule tddm = (TelluriumDataDrivenModule)module.newInstance()
            tddm.setProperty("ui",this.ui)
            tddm.setProperty("thr", this.thr)
            tddm.setProperty("fsr", this.fsr)
            tddm.setProperty("fs", this.fs)
            tddm.setProperty("tr", this.testreg)
            tddm.setProperty("dataProvider", this.dataProvider)
            tddm.belongTo(this)

            tddm.defineModule()
        }else{
        	IResourceBundle i18nBundle = Environment.instance.myResourceBundle()
            throw new RuntimeException(i18nBundle.getMessage("TelluriumDataDrivenTest.IncludModule" , module?.getName()))
        }
    }

    //-------------------------------------------------------------------------------------------
    // The flow control methods
    //-------------------------------------------------------------------------------------------

    //the count of number of steps can also be used to identify the ith run of the test
    //also acts as the sequence number
//    protected int stepCount = 0
    protected TextContext context = new TextContext()

   //flow control
    //read the file and run the test script until it reaches the end of the file
    public void stepToEnd(Closure c){
        boolean hasMore = true
        while(hasMore){
            hasMore = step(c)
        }
    }

    public void stepToEnd(){
        stepToEnd(null)
    }

    //def stepToEnd = this.&stepToEnd

    //read one line from the file and run the test script so that you can have different
    //test scripts for each line
    public boolean step(Closure c){
        //get data from the data stream
        FieldSetMapResult fsmr = dataProvider.nextFieldSet()
        //check if we reach the end of data stream
        if(fsmr != null && (!fsmr.isEmpty())){
            //check if the field set includes action name
            String action = getTestForFieldSet(fsmr.getFieldSetName())

            TestResult result = new TestResult()
            result.setProperty("testName", action)
            result.setProperty("stepId",  context.nextStep())
            result.setProperty("start", System.nanoTime())
            result.setProperty("input", fsmr.getResults())

            try{
                if(action != null){
                    //if the field set includes action
                    //get the pre-defined action and run it

                    Closure closure = testreg.getTest(action)
                    closure()
/*
                    //use the proxy so that we can intercept calls for connectUrl and compareResult
                    proxy.interceptor = this.interceptor
                    proxy.use{
                        closure()
                    }
*/
                }

                //if there is other user defined closure, run it
                if(c != null){
                    c()
                }
                result.setProperty("status", StepStatus.PROCEEDED)
            }catch(Exception e){
                result.setProperty("status", StepStatus.EXECPTION)
                result.setProperty("exception", e)
            }
            result.setProperty("end", System.nanoTime())
            listener.listenForInput(result)

            return true
        }

        return false
    }

    public void step(){
        step(null)
    }

    //def step = this.&step

    //read one from the file but do not run the test script. This may apply to the scenario
    //that you need to read multiple lines before you can run the test
    //If the next line is of the same Field set as the current one, the data reading in will
    //be overwritten after this command
    public boolean stepOver(){
        FieldSetMapResult fsmr = dataProvider.nextFieldSet()
        //check if we reach the end of data stream
        if(fsmr != null && (!fsmr.isEmpty())){
            //check if the field set includes action name
            String action = getTestForFieldSet(fsmr.getFieldSetName())

            TestResult result = new TestResult()
            result.setProperty("testName", action)
            result.setProperty("stepId", context.nextStep())
            result.setProperty("input", fsmr.getResults())
            result.setProperty("status", StepStatus.SKIPPED)
//            result.setProperty("passed", true)

            listener.listenForInput(result)

            return true
        }

        return false
    }

    //make DSL more expressive, instead of put stepOver(), but define the following
    //you can simply write stepOver
    //def stepOver = this.&stepOver

    public void loadData(String filePath){
        dataProvider.useFile(filePath)
    }

    //useString data defined in the script file
    public void useData(String data){
        dataProvider.useString(data)
    }

    public void closeData(){
        dataProvider.stop()
        listener.report()
    }

    //def closeData = this.&closeData

    //-----------------------------------------------------------------------------------------
    // Internal methods
    //-----------------------------------------------------------------------------------------
    protected void recordResult(expected, actual, Closure c){
        boolean passed = true

        TestResult result = new TestResult()
        AssertionResult assertResult = new AssertionResult()

        result.setProperty("stepId", context.getStepCount())
        ComparisonAssertionValue value = new ComparisonAssertionValue()
        value.setProperty("expected", expected)
        value.setProperty("actual", actual)
        assertResult.setProperty("value", value)

        try{
            //allow user to override the default assertion use
            //closure to define comparison
            if(c != null){
                c()
            }else{
                //if the closure is not defined, use the default Junit assertion
                assertEquals(expected, actual)
            }
        }catch(AssertionFailedError e){
            passed = false
            assertResult.setProperty("error", e)
        }

        assertResult.setProperty("passed", passed)
        result.addAssertationResult(assertResult)
        listenForResult(result)
    }

    protected void recordResult(value, Closure c){
        boolean passed = true

        TestResult result = new TestResult()
        AssertionResult assertResult = new AssertionResult()

        result.setProperty("stepId", context.getStepCount())
        EvaulationAssertionValue eav = new EvaulationAssertionValue()
        eav.setProperty("value", value)
        assertResult.setProperty("value", eav)

        try{
            //user must define the closure for actual checking
            if(c != null){
                c()
            }
        }catch(AssertionFailedError e){
            passed = false
            assertResult.setProperty("error", e)
        }

        assertResult.setProperty("passed", passed)
        result.addAssertationResult(assertResult)
        listenForResult(result)
    }
    protected void logMessage(String message){
        listener.listenForMessage(context.getStepCount() ,message)
    }

    protected String getTestForFieldSet(String fieldSetName){
        FieldSet tfs = fsr.getFieldSetByName(fieldSetName)
        if(tfs != null){
            TestField taf = tfs.getActionField()
            if(taf != null){
                String tid = fieldSetName + "." + taf.getName()
                return dataProvider.bind(tid)
            }
        }

        return null
    }

    protected void listenForResult(org.telluriumsource.test.report.TestResult result ){
        listener.listenForResult(result)
    }

    protected void cacheVariable(String name, value){
        context.putCacheVariable(name, value)
    }

    protected def getCachedVariable(String name){
        return context.getCachedVariable(name)
    }
}
