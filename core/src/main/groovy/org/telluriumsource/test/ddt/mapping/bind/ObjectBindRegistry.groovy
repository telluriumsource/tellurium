package org.telluriumsource.test.ddt.mapping.bind

import org.telluriumsource.test.ddt.mapping.mapping.FieldSetMapResult

/**
 * The registry holds the fieldset id to its value
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class ObjectBindRegistry {

   Map<String, FieldSetMapResult> results = new HashMap<String, FieldSetMapResult>()

   public void addFieldSetMapResult(String fieldSetId, FieldSetMapResult result){
       this.results.put(fieldSetId, result)
   }

   public FieldSetMapResult getFieldSetMapResult(String fieldSetId){
       return this.results.get(fieldSetId)
   }

   public int size(){
       return results.size()
   }

   public FieldSetMapResult getUniqueOne(){
       if(this.results.size() == 1){
            return results.values().asList().get(0)     
       }

       return null
   }
}