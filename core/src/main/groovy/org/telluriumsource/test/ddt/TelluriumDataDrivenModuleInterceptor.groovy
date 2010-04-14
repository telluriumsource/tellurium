package org.telluriumsource.test.ddt

/**
 *
 * Intercept the method call so that we can override the method calls in TelluriumDataDrivenModule
 * at runtime, for example, openUrl and compareResult should be ovrriden at runtime.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 1, 2008
 *
 */
class TelluriumDataDrivenModuleInterceptor implements Interceptor{
    private static final String OPEN_URL = "openUrl"
    private static final String CONNECT_URL = "connectUrl"
    private static final String COMPARE_RESULT = "compareResult"

    //decide if we need to invoke the method or not
    boolean shouldInvoke = true
    TelluriumDataDrivenTest executor
    
    public TelluriumDataDrivenModuleInterceptor(TelluriumDataDrivenTest tddt){
        this.executor = tddt
    }

    Object beforeInvoke(Object object, String methodName, Object[] arguments) {
        if (TelluriumDataDrivenModule.class.isAssignableFrom(object) && (OPEN_URL.equals(methodName)) || COMPARE_RESULT.equals(methodName)) {
            shouldInvoke = false //don't invoke openUr, compareResult
        }else{
            shouldInvoke = true
        }
    }

    boolean doInvoke() { shouldInvoke }

    Object afterInvoke(Object object, String methodName, Object[] arguments, Object result) {
        if (TelluriumDataDrivenModule.class.isAssignableFrom(object)){
            if(OPEN_URL.equals(methodName)){
                return executor?.openUrl(arguments)
            }
            if(CONNECT_URL.equals(methodName)){
                return executor?.connectUrl(arguments)
            }
            if(COMPARE_RESULT.equals(methodName)){
               return executor?.recordResult(arguments)
            }
        }

        shouldInvoke = true

        return result
    }

}