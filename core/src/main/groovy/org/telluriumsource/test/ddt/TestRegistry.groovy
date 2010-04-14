package org.telluriumsource.test.ddt
/**
 *
 * Registry to hold user defined Tests
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class TestRegistry {

    private Map<String, Closure> tests = new HashMap<String, Closure>()

    public void addTest(String name, Closure c){
        tests.put(name, c)
    }

    public Closure getTest(String name){
        tests.get(name)
    }

    public int size(){
        return tests.size()
    }
}