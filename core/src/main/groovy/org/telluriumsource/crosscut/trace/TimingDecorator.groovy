package org.telluriumsource.crosscut.trace
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.crosscut.log.ConsoleAppender
import org.telluriumsource.crosscut.log.SimpleLogger
import org.telluriumsource.crosscut.log.Logger;



/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 27, 2009
 *
 */

public class TimingDecorator {
//    @Delegate
    private delegate
    protected IResourceBundle i18nBundle


    private Logger logger

    private long startTime = -1

    private long endTime = -1

    private long accumulatedTime = 0

    private List<String> whiteList = []

    private List<TimingResult> results = new ArrayList<TimingResult>()

    public TimingDecorator(){
    	  i18nBundle = Environment.instance.myResourceBundle()
    }
    public void setWhiteList(List<String> list){
      this.whiteList = list
    }

    public long getAccumulatedTime(){
      return this.accumulatedTime
    }

    public void resetAccumulatedTime(){
      this.accumulatedTime = 0
      this.startTime = -1
      this.endTime = -1
    }

    public long getStartTime(){
      return this.startTime
    }

    public long getEndTime(){
      return this.endTime
    }

    protected boolean methodInWhiteList(String methodName){
      if(whiteList.indexOf(methodName) != -1)
        return true

      return false
    }

    TimingDecorator(delegate) {
        this.delegate = delegate
        logger = SimpleLogger.getInstance()
        logger.addAppender(new ConsoleAppender())
    }

    java.lang.Object invokeMethod(java.lang.String name, java.lang.Object args) {
        if(startTime == -1)
          startTime = System.currentTimeMillis()
        long beforeTime = System.currentTimeMillis()
        def result = delegate.invokeMethod(name, args)

        long duration = System.currentTimeMillis() - beforeTime

        endTime = System.currentTimeMillis()
        if(methodInWhiteList(name)){
          accumulatedTime += duration
//          println "Calling $name($args) in ${duration} ms <-- Accumulated "
          logger.log(i18nBundle.getMessage("TimingDecorator.MethodInWhiteList" , name,args,duration ))
        }else{
          logger.log(i18nBundle.getMessage("TimingDecorator.MethodNotInWhiteList" , name,args,duration ))
//          println "Calling $name($args) in ${duration} ms"
        }

        result
    }

    public TimingResult storeResult(String testName, int repeatCount, String msg){
      TimingResult result = new TimingResult();
      result.setStartTime(this.startTime);
      result.setEndTime(this.endTime);
      result.setAccumulatedTime(this.accumulatedTime);
      result.setTestName(testName);
      result.setRepeatCount(repeatCount);
      result.setMessage(msg);
      results.add(result);

      return result;
  }

  public void outputResult() {

    logger.log(i18nBundle.getMessage("TimingDecorator.FinalResults"))
    for (TimingResult result: results) {
      logger.log(result.strResult());
    }
  }

}