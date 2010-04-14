package org.telluriumsource.test.ddt.mapping
/**
 * Exception for data mapping
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class DataMappingException extends RuntimeException{
   // Track the very first time a message & cause was added in a stack of exception
    private Throwable rootCause = null;
    private String rootMessage = null;
    // Track the most recent time a message was added in a stack of exceptions
    private Throwable lastCause = null;
    private String lastMessage = null;

    /**
     * Create a new <code> DataMappingException </code>
     *
     */
    public DataMappingException() {
        super();
   }

    /**
     * Create a new <code> DataMappingException </code> with the
     * given message
     * @param message  Description of error.
     */
    public DataMappingException(final String message) {
        super(message);
        lastMessage = message;
        rootMessage = message;
   }

    /**
     * Wrap the given cause with a <code> DataMappingException </code> adding
     * the given message.
     *
     * @param message Description of error
     * @param cause Error or Exception to be wrapped
     */
    public DataMappingException(String message, Throwable cause) {
        super(message, cause);
        getRoots(cause);
        if (rootMessage == null) {
            rootMessage = message;
        }
        lastMessage = message;
        lastCause = cause;
     }

    /**
     * Wrap the given cause with a <code> DataMappingException </code>
     * @param cause Error or Exception to be wrapped.
     */
    public DataMappingException(Throwable cause) {
        // null message inserted here, otherwise when a chain of exceptions
        // is wrapped, we get all of the classes being concatentated together
        // as the cause.
        super(cause);
        getRoots(cause);
        lastCause = cause;
    }


    private void getRoots(Throwable cause) {
        if (cause instanceof DataMappingException) {
            DataMappingException e = (DataMappingException) cause;
            Throwable r;
            String m;
            if ( (r = e.getRootCause()) != null) {
                rootCause = r;
            }
            else {
                rootCause = cause;
            }
            if ( (m = e.getRootMessage()) != null) {
                rootMessage = m;
            }
            if ( (m = e.getLastMessage()) != null) {
                lastMessage = m;
            }
        }
        else {
            rootCause = cause;
            rootMessage = cause.getMessage();
        }
    }


    /**
     * Given a chain of exceptions thrown, this will return the first exception
     * that was thrown in the chain, excluding the actual exception that was caught.
     * @return First exception thrown in chain after popping the top.
     * Given a chain composed of just
     * one exception, this will return null.
     */
    public final Throwable getRootCause() {
        return rootCause;
    }

    /**
     * Givne a chain of exceptions that was thrown, thiw will throw the last
     * exceptions in the chain, excluding the first exception.
     * @return Return last exception in chain after popping the top exception.
     * Given a chain composed of just one exception, this will return null.
     */
    public final Throwable getLastCause() {
        return lastCause;
    }

    /**
     * Given a chain of exceptions this will thrown the first message that was
     * attached to the creation of an exception. Given a chain of just one exception,
     * if a message was used in the creation of that exception, it will return
     * the message.  If no messages were ever used, null is returned.
     * @return Message attched to creation of exception of chain, as close to
     * the start of the chain as possible.
     */
    public final String getRootMessage() {
        return rootMessage;
    }

    /**
     * Given a chain of exceptions this will thrown the last message that was
     * attached to the creation of an exception. Given a chain of just one exception,
     * if a message was used in the creation of that exception, it will return
     * the message.  If no messages were ever used, null is returned.
     * @return Message attched to creation of exception of chain, as close to
     * the top of the chain as possible.
     */
    public final String getLastMessage() {
        return lastMessage;
    }

}