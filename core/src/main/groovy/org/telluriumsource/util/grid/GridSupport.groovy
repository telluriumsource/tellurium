package org.telluriumsource.util.grid

import org.telluriumsource.component.connector.CustomSelenium

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 12, 2009
 * 
 */

public class GridSupport {

  private static ThreadLocal<CustomSelenium> threadLocalSelenium = new ThreadLocal<CustomSelenium>();

    public static void startSeleniumSession(String seleniumHost, int seleniumPort, String browser, String webSite) {
        threadLocalSelenium.set(new CustomSelenium(seleniumHost, seleniumPort, browser, webSite));
        session().start();
    }

    public static void startSeleniumSession(String seleniumHost, int seleniumPort, String browser, String webSite, String options) {
        threadLocalSelenium.set(new CustomSelenium(seleniumHost, seleniumPort, browser, webSite));
        session().start(options);
    }
  
    public static void closeSeleniumSession() throws Exception {
        if (null != session()) {
            session().stop();
            resetSession();
        }
    }

    public static CustomSelenium session() {
        return threadLocalSelenium.get();
    }


    public static void resetSession() {
        threadLocalSelenium.set(null);
    }
}