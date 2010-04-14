package org.telluriumsource.test.report

import org.telluriumsource.util.Helper

/**
 * Test result
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 *
 */
class TestResult {

    private static final String STEP_ID = "Step"
    //identifier for a step
    private int stepId

    private static final String TEST_NAME = "TestName"
    private String testName

    private static final String INPUT = "Input"
    private Map input

    private static final String ASSERTION_RESULT = "assertionResults"
    private List<AssertionResult> assertionResults = new ArrayList<AssertionResult>()

    private static final String STATUS = "Status"
    private StepStatus status

    private static final String EXCEPTION = "Exception"
    private Exception exception

    private static final String MESSAGES = "Messages"
    private List<String> messages = new ArrayList<String>()

    private static final String START = "start"
    private long start

    private static final String END = "end"
    private long end
    private static final String ASSERTION = "Assertion"

    private static final String RUN_TIME = "Runtime"

    private static final String PASSED = "Passed"

    public boolean isPassed(){
        if(assertionResults.size() > 0){
            for(AssertionResult atr: assertionResults){
                if(!atr.isPassed()){
                    return false
                }
            }
        }

        return true
    }

    public void addAssertationResult(AssertionResult result){
        assertionResults.add(result)
    }

    public void addMessage(String message){
        messages.add(message)
    }
    
    public List<AssertionResult> getAssertionResult(){
        return this.assertionResults
    }
    
    public String toString(){
        final int typicalLength = 128
        final String avpSeparator = ": "
        final String fieldSeparator = "\n"
        final String fieldStart = "\t"

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(fieldStart).append(STEP_ID).append(avpSeparator).append(stepId).append(fieldSeparator)
        sb.append(fieldStart).append(TEST_NAME).append(avpSeparator).append(testName).append(fieldSeparator)
        sb.append(fieldStart).append(PASSED).append(avpSeparator).append(isPassed()).append(fieldSeparator)
        sb.append(fieldStart).append(INPUT).append(avpSeparator).append(" [ ").append(convertInput()).append(" ]").append(fieldSeparator)
        if(assertionResults.size() > 0){
            for(AssertionResult atr : assertionResults){
                sb.append(fieldStart).append(ASSERTION).append(avpSeparator).append(" [ ").append(atr.toString()).append(" ]").append(fieldSeparator)   
            }
        }

        sb.append(fieldStart).append(STATUS).append(avpSeparator).append(status?.toString()).append(fieldSeparator)
        sb.append(fieldStart).append(RUN_TIME).append(avpSeparator).append((end-start)/1E9).append(" secs").append(fieldSeparator)
        if(messages != null && (!messages.isEmpty())){
            for(String message: messages){
                sb.append(fieldStart).append(MESSAGES).append(avpSeparator).append(message).append(fieldSeparator)  
            }
        }
        if(exception != null){
            sb.append(fieldStart).append(EXCEPTION).append(avpSeparator).append(Helper.logException(exception)).append(fieldSeparator)
        }else{

        }

        return sb.toString()
    }

    private String convertAssertationResults(){
        String str = ""

        if (assertionResults.size() > 0) {
            assertionResults.each{ AssertionResult result ->
                if(i > 0)
                    str = str + "\n\t"

                str = str + "\t" + result.toString()
            }
        }

        return str
    }

    private String convertInput(){
        String str

        if(input!= null && (!input.isEmpty())){
            List<String> list = new ArrayList<String>()
            input.each { key, value ->
                list.add("${key}: ${value}")
            }
            str = list.join(", ")
        }else{
            str = "empty"
        }

        return str
    }
}