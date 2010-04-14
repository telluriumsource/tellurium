package org.telluriumsource.util

import java.lang.reflect.UndeclaredThrowableException

class Helper{

    def static pause(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
        }
    }

    public static String logException(Exception exception){
         // convert the exception to String
        StackTraceElement[] stackTrace = exception.getStackTrace();
        StringBuilder sb = new StringBuilder("\n");
        String exceptionMessage = "";
    	if(exception instanceof UndeclaredThrowableException){
    		exceptionMessage = exception.getCause().getMessage();
    	}else{
    		exceptionMessage = exception.getMessage();
    	}
        sb.append(exceptionMessage + "\n");

        for (StackTraceElement st: stackTrace) {
            sb.append(st.toString() + "\n\t\t");
        }

        return sb.toString();
    }

    public static boolean include(List<String> list, String name){
        if(list == null || list.isEmpty() || name == null)
            return false;

        for(String elem: list){
            if(name.contains(elem) || elem.contains(name)){
                return true;
            }
        }

        return false;
    }

   public static Object[] removeFirst(Object[] args){
      List list = new ArrayList();
      for(int i=1; i<args.length; i++){
        list.add(args[i]);
      }

      if(list.size() > 0)
        return list.toArray();

      return null;
  }

  //deep copy, quite expensive
  def static clone(object) {
    ByteArrayOutputStream bos = new ByteArrayOutputStream()
    ObjectOutputStream oos = new ObjectOutputStream(bos)
    oos.writeObject(object); oos.flush()
    ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray())
    ObjectInputStream ois = new ObjectInputStream(bin)
    return ois.readObject()
  }
}