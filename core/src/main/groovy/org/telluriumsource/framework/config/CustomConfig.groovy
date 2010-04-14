package org.telluriumsource.framework.config

/**
 * Class for users to pass in custom settings
 *
 * Jian Fang (Jian.Fang@gmail.com)
 *
 * Date: Feb 25, 2009
 *
 */

public class CustomConfig {

    protected boolean useMultiWindows = false;

    protected int port = 4444;

    protected boolean runInternally = true;

    protected String profileLocation = null;

    protected String browser = "*chrome";

    protected String serverHost = null;

    protected String browserOptions = null;

    public CustomConfig(boolean runInternally, int port, String browser, boolean useMultiWindows, String profileLocation,
                        String serverHost, String browserOptions) {
        this.useMultiWindows = useMultiWindows;
        this.port = port;
        this.runInternally = runInternally;
        this.profileLocation = profileLocation;
        this.browser = browser;
        this.serverHost = serverHost;
        this.browserOptions = browserOptions;
    }

    public CustomConfig(boolean runInternally, int port, String browser, boolean useMultiWindows, String profileLocation,
                        String serverHost) {
        this.useMultiWindows = useMultiWindows;
        this.port = port;
        this.runInternally = runInternally;
        this.profileLocation = profileLocation;
        this.browser = browser;
        this.serverHost = serverHost;
    }

    public CustomConfig(boolean runInternally, int port, String browser, boolean useMultiWindows, String profileLocation) {
        this.useMultiWindows = useMultiWindows;
        this.port = port;
        this.runInternally = runInternally;
        this.profileLocation = profileLocation;
        this.browser = browser;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public boolean isUseMultiWindows() {
        return useMultiWindows;
    }

    public void setUseMultiWindows(boolean useMultiWindows) {
        this.useMultiWindows = useMultiWindows;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isRunInternally() {
        return runInternally;
    }

    public void setRunInternally(boolean runInternally) {
        this.runInternally = runInternally;
    }

    public String getProfileLocation() {
        return profileLocation;
    }

    public void setProfileLocation(String profileLocation) {
        this.profileLocation = profileLocation;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
}