package org.telluriumsource.entity.config

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class EmbeddedServer {
  //port number
  public static String PORT = "port";
  String port = "4444";

  //whether to use multiple windows
  public static String USE_MULTI_WINDOWS = "useMultiWindows";
  boolean useMultiWindows = false;

  //whether to trust all SSL certs, i.e., option "-trustAllSSLCertificates"
  public static String TRUST_ALL_SSL_CERTIFICATES = "trustAllSSLCertificates";
  boolean trustAllSSLCertificates = true;

  //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
  public static String RUN_INTERNALLY = "runInternally";
  boolean runInternally = true;

  //By default, Selenium proxies every browser request; set this flag to make the browser use proxy only for URLs containing '/selenium-server'
  public static String AVOID_PROXY = "avoidProxy";
  boolean avoidProxy = false;

  //stops re-initialization and spawning of the browser between tests
  public static String BROWSER_SESSION_REUSE = "browserSessionReuse";
  boolean browserSessionReuse = false;

  //enabling this option will cause all user cookies to be archived before launching IE, and restored after IE is closed.
  public static String ENSURE_CLEAN_SESSION = "ensureCleanSession";
  boolean ensureCleanSession = false;

  //debug mode, with more trace information and diagnostics on the console
  public static String DEBUG_MODE = "debugMode";
  boolean debugMode = false;

  //interactive mode
  public static String INTERACTIVE = "interactive";
  boolean interactive = false;

  //an integer number of seconds before we should give up
  public static String TIMEOUT_IN_SECONDS = "timeoutInSeconds";
  int timeoutInSeconds = 30;

  //profile location
  public static String PROFILE = "profile";
  String profile = "";

  //user-extension.js file
  public static String USER_EXTENSION = "userExtension";
  String userExtension = "";

  def EmbeddedServer(){

  }

  def EmbeddedServer(Map map) {
    if (map != null) {
      if (map.get(PORT) != null)
        this.port = map.get(PORT);

      if (map.get(USE_MULTI_WINDOWS) != null)
        this.useMultiWindows = map.get(USE_MULTI_WINDOWS);

      if (map.get(TRUST_ALL_SSL_CERTIFICATES) != null)
        this.trustAllSSLCertificates = map.get(TRUST_ALL_SSL_CERTIFICATES);

      if (map.get(RUN_INTERNALLY) != null)
        this.runInternally = map.get(RUN_INTERNALLY);

      if (map.get(AVOID_PROXY) != null)
        this.avoidProxy = map.get(AVOID_PROXY);

      if (map.get(BROWSER_SESSION_REUSE) != null)
        this.browserSessionReuse = map.get(BROWSER_SESSION_REUSE);

      if (map.get(ENSURE_CLEAN_SESSION) != null)
        this.ensureCleanSession = map.get(ENSURE_CLEAN_SESSION);

      if (map.get(DEBUG_MODE) != null)
        this.debugMode = map.get(DEBUG_MODE);

      if (map.get(INTERACTIVE) != null)
        this.interactive = map.get(INTERACTIVE);

      if (map.get(TIMEOUT_IN_SECONDS) != null)
        this.timeoutInSeconds = map.get(TIMEOUT_IN_SECONDS);

      if (map.get(PROFILE) != null)
        this.profile = map.get(PROFILE);

      if (map.get(USER_EXTENSION) != null)
        this.userExtension = map.get(USER_EXTENSION);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(PORT, this.port);
    obj.put(USE_MULTI_WINDOWS, this.useMultiWindows);
    obj.put(TRUST_ALL_SSL_CERTIFICATES, this.trustAllSSLCertificates);
    obj.put(RUN_INTERNALLY, this.runInternally);
    obj.put(AVOID_PROXY, this.avoidProxy);
    obj.put(BROWSER_SESSION_REUSE, this.browserSessionReuse);
    obj.put(ENSURE_CLEAN_SESSION, this.ensureCleanSession);
    obj.put(DEBUG_MODE, this.debugMode);
    obj.put(INTERACTIVE, this.interactive);
    obj.put(TIMEOUT_IN_SECONDS, this.timeoutInSeconds);
    obj.put(PROFILE, this.profile);
    obj.put(USER_EXTENSION, this.userExtension);

    return obj;
  }

  public void toProperties(Properties properties, String path){
    properties.setProperty(path + "." + PORT, this.port);
    properties.setProperty(path + "." + USE_MULTI_WINDOWS, Boolean.toString(this.useMultiWindows));
    properties.setProperty(path + "." + TRUST_ALL_SSL_CERTIFICATES, Boolean.toString(this.trustAllSSLCertificates));
    properties.setProperty(path + "." + RUN_INTERNALLY, Boolean.toString(this.runInternally));
    properties.setProperty(path + "." + AVOID_PROXY, Boolean.toString(this.avoidProxy));
    properties.setProperty(path + "." + BROWSER_SESSION_REUSE, Boolean.toString(this.browserSessionReuse));
    properties.setProperty(path + "." + ENSURE_CLEAN_SESSION, Boolean.toString(this.ensureCleanSession));
    properties.setProperty(path + "." + DEBUG_MODE, Boolean.toString(this.debugMode));
    properties.setProperty(path + "." + INTERACTIVE, Boolean.toString(this.interactive));
    properties.setProperty(path + "." + TIMEOUT_IN_SECONDS, Integer.toString(this.timeoutInSeconds));
    properties.setProperty(path + "." + PROFILE, this.profile);
    properties.setProperty(path + "." + USER_EXTENSION, this.userExtension);
  }

}
