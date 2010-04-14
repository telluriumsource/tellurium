package org.telluriumsource.test.ddt
/**
 * Transfer object passing through tests
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 17, 2008
 * 
 */
class TextContext {
    //store the step count
    protected int stepCount = 0
    //cache variables so that we can pass variables among tests
    def cache = [:]

    public int getStepCount(){
        return stepCount
    }

    public int nextStep(){
        return ++stepCount
    }

    public void putCacheVariable(String name, value){
        cache.put(name, value)
    }

    public def getCachedVariable(String name){
        return cache.get(name)
    }
}