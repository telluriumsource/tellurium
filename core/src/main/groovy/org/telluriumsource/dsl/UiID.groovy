package org.telluriumsource.dsl
/**
 * Internal representation of ui Id, which could be nested in the format of XXX.YYY.ZZZ.WWW
 * To make it easy to process, use Stack to hold it so that we can alway pop up one and process
 * it one at a time
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * @author Vivek Mongolu (vivekmongolu@gmail.com)
 *
 * Date: Jul 2, 2008
 */
class UiID extends Stack<String>{

    public static final String ID_SEPARATOR = '\\.'

    public static UiID convertToUiID(String id){
        UiID uiid = new UiID()
        if(id != null && (id.trim().length() > 0)){
           String[] ids = id.split(ID_SEPARATOR)
           for(int i=ids.length-1; i>=0; i--)
           {
               String[] pp = preprocess(ids[i])
               if(pp.length == 1){
                 uiid.push(pp[0])
               }else{
                 uiid.push(pp[1])
                 uiid.push(pp[0])
               }
           }
        }

        return uiid
    }


    //Pre-Process to convert the table[x][y] to table, _x_y format
    public static String[] preprocess(String id){
       if(id != null && (id.trim().length() > 0) && id.contains("[")){
           if(id.startsWith("[")){
                String first = id.replaceAll('\\[', '_').replaceAll('\\]','')
                return [first]
           }else{
                int index = id.indexOf("[")
                String first = id.substring(0, index)
                String second = id.substring(index).replaceAll('\\[', '_').replaceAll('\\]','')
                return [first, second]
           }
       }else{
           return [id]
       }
    }

    public String getUiID(){
      
      return this.reverse().join(".")
    }
}