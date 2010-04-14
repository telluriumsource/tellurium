package org.telluriumsource.server

import org.openqa.selenium.server.RemoteControlConfiguration
import org.openqa.selenium.server.SeleniumServer
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle;



/**
 * Programmatically run Selenium Server so that we do not have to
 * start the server in the shell.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */
public class SeleniumServerDaemon {
    //determine if it runs
    private boolean listening = false;
    //default port number

    private static final int DEFAULT_PORT = 4444;

    private static final String DEFAULT_LOG_FILE = "selenium.log";

    private int port;

    private String logFile;

    private boolean useMultiWindows = false;

    private boolean slowResources = false;

    private boolean trustAllSSLCertificates = false;
    private int timeoutInSeconds = 30;

    private boolean avoidProxy = false;

    private boolean browserSessionReuse = false;

    private boolean ensureCleanSession = false;

    private boolean debugMode = false;

    private boolean interactive = false;

    private SeleniumServer server;

    private String profileLocation = null;

    private String userExtension = null;

    protected IResourceBundle i18nBundle

    private String [] getParams(){
		String cmd = "-port " + port + " -log " + logFile;

		if(useMultiWindows){
			cmd = cmd + " -multiWindow";
		}

		return cmd.split(" ");
    }

    public SeleniumServerDaemon(int port, String logFile, boolean useMultiWindows, boolean trustAllSSLCertificates,
            boolean avoidProxy, boolean browserSessionReuse, boolean ensureCleanSession, boolean debugMode, boolean interactive,
            int timeoutInSeconds, String profileLocation, String userExtension) {
  		super();
  		this.port = port;
  		this.logFile = logFile;
  		this.useMultiWindows = useMultiWindows;
          this.trustAllSSLCertificates = trustAllSSLCertificates;
          this.avoidProxy = avoidProxy;
          this.browserSessionReuse = browserSessionReuse;
          this.ensureCleanSession = ensureCleanSession;
          this.debugMode = debugMode;
          this.interactive = interactive;
          this.timeoutInSeconds = timeoutInSeconds;

  		listening = false;
  		if(this.port <0 )
  			port = DEFAULT_PORT;
  		if(this.logFile == null)
  			this.logFile = DEFAULT_LOG_FILE;
          if(profileLocation != null && profileLocation.trim().length() > 0){
            this.profileLocation = profileLocation;
          }
          if(userExtension != null && userExtension.trim().length() > 0){
            this.userExtension = userExtension;
          }
        i18nBundle = Environment.instance.myResourceBundle()
  	}
	public final int getPort() {
		return port;
	}

	public final void setPort(int port) {
		this.port = port;
	}

	public final String getLogFile() {
		return logFile;
	}

	public final void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	public void run() {
		RemoteControlConfiguration config = new RemoteControlConfiguration();
		config.port = port;
//		config.multiWindow = useMultiWindows;
        config.singleWindow = !useMultiWindows;
//        config.setSingleWindow(!this.useMultiWindows);

//		config.setProxyInjectionModeArg(true); //this may not be needed, or at least needs to be configurable
        if(this.profileLocation != null && this.profileLocation.trim().length() > 0){
//          config.setProfilesLocation(new File(this.profileLocation));
          config.setFirefoxProfileTemplate(new File(this.profileLocation));
          config.setTrustAllSSLCertificates(this.trustAllSSLCertificates);
          config.setTimeoutInSeconds(this.timeoutInSeconds);
          config.setAvoidProxy(this.avoidProxy);
          config.setReuseBrowserSessions(this.browserSessionReuse);
          config.setInteractive(this.interactive);
          config.setDebugMode(this.debugMode);
          config.setEnsureCleanSession(this.ensureCleanSession);

        }

        if(this.userExtension != null && this.userExtension.trim().length() > 0){
		  File userExt = new File(this.userExtension);
		  if(userExt.exists()){
            config.setUserExtensions(userExt);
            println i18nBundle.getMessage("SeleniumServerDaemon.UserExtensionFile" , this.userExtension)
          } else {
            println i18nBundle.getMessage("SeleniumServerDaemon.NoUserExtension" , userExt.getAbsolutePath())
          }
        }else{
          println i18nBundle.getMessage("SeleniumServerDaemon.NoUserExtensionWarning")
        }
		try {
            server = new SeleniumServer(config);
			server.boot()

            listening = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

    public void stop(){
       try{
            if(server != null)
                server.stop()
        } catch (Exception e) {

			e.printStackTrace();
		}
    }
}

