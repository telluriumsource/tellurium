package org.telluriumsource.crosscut.trace
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 27, 2009
 * 
 */

public class TimingResult {
  
    private static final int LENGTH = 64;
    private static final String TEST_NAME = " TestName: ";
    private static final String MESSAGE = " message: ";
    private static final String TOTAL_TIME = " TotalTime: ";
    private static final String ACCUMULATED_TIME = " AverageAccumulatedTime: ";
    private static final String REPEAT_COUNT = " RepeatCount: ";
    private static final String TIME_UNIT = "ms";

    private static final String SEPARATOR = ",";

    private long startTime;

    private long endTime;

    private long accumulatedTime;

    private String testName;

    private String message;

    private int repeatCount;

    public long getTotalTime(){
        return this.endTime - this.startTime;
    }

    public long getAverageTime(){
        return accumulatedTime/repeatCount;
    }
    public String strResult(){
        StringBuffer sb = new StringBuffer(LENGTH);
        sb.append(TEST_NAME).append(testName).append(SEPARATOR)
                .append(TOTAL_TIME).append(getTotalTime()).append(TIME_UNIT).append(SEPARATOR)
                .append(REPEAT_COUNT).append(repeatCount).append(SEPARATOR)
                .append(ACCUMULATED_TIME).append(getAverageTime()).append(TIME_UNIT);
        if(message != null && message.trim().length() > 0){
            sb.append(SEPARATOR).append(MESSAGE).append(message);
        }

        return sb.toString();
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getAccumulatedTime() {
        return accumulatedTime;
    }

    public void setAccumulatedTime(long accumulatedTime) {
        this.accumulatedTime = accumulatedTime;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }
}