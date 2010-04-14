package org.telluriumsource.test.report
/**
 * Simply convert the result as String
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 28, 2008
 *
 */
class SimpleResultReporter implements ResultReporter{

    public String report(List<TestResult> results) {
        int total = 0
        int succeeded = 0
        int failed = 0
        if(results != null && (!results.isEmpty())){
            total = results.size()
            results.each{ TestResult val ->
                if(val.isPassed()){
                    succeeded++
                }else{
                    failed++
                }
            }

        }

        StringBuffer sb = new StringBuffer(512)
        sb.append("\n\n------------------------------------------------------------------------\n")
        sb.append( "Test Results: \n")
        sb.append("Total tests: ${total}\n")
        sb.append("Tests succeeded: ${succeeded}\n")
        sb.append("Tests failed: ${failed}\n")
        sb.append("------------------------------------------------------------------------\n\n")
        if(results != null && (!results.isEmpty())){
            for(TestResult result : results){
                sb.append("{")
                sb.append(result.toString())
                sb.append("}\n")
            }
        }
        sb.append("------------------------------------------------------------------------\n")

/*        println "\n\n------------------------------------------------------------------------\n"
        println "Test Results: \n"
        println "Total tests: ${total}\n"
        println "Tests succeeded: ${succeeded}\n"
        println "Tests failed: ${failed}\n"
        println "------------------------------------------------------------------------\n\n"
        if(results != null && (!results.isEmpty())){
            for(TestResult result : results){
                println "{"
                println result.toString()
                println "}\n"
            }
        }
        println "------------------------------------------------------------------------\n"*/

//        print sb.toString()

        return sb.toString()
    }

}