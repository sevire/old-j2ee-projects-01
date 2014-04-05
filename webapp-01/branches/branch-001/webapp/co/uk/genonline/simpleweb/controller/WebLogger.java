package co.uk.genonline.simpleweb.controller;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 16/12/2012
 * Time: 12:40
 *
 * Logs more information about where message comes from, also removes need for initialisation
 */
public class WebLogger {
    private final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    private final String loggerName = stackTraceElements[2].getClassName();
    private final Logger logger;
    private String sessionInformation =  "(No Session) : ";

    public WebLogger() {
        logger = Logger.getLogger(loggerName);
        trace("Logger initiated");
    }

    public WebLogger(HttpServletRequest request) {
        this();
        setSession(request);
    }

    String getCallingInformation() {
        return Thread.currentThread().getStackTrace()[5].toString() + " : ";
    }

    String getSessionInformation() {
        return sessionInformation;
    }

    String getMessagePrefix() {
        return getSessionInformation() + getCallingInformation();
    }

    public void setSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            sessionInformation = session.toString() + " : ";
        }
    }

    public void setLevel(Level level) {
        logger.setLevel(level);
    }

    void log(Level level, String formatString, String[] values) {
        logger.log(level, getMessagePrefix() + String.format(formatString, (Object[])values));
    }

    public void info(String formatString, String ... values) {
        log(Level.INFO, formatString, values);
    }

    public void error(String formatString, String ... values) {
        log(Level.ERROR, formatString, values);
    }

    public void debug(String formatString, String ... values) {
        log(Level.DEBUG, formatString, values);
    }

    public void fatal(String formatString, String ... values) {
        log(Level.FATAL, formatString, values);
    }

    public void trace(String formatString, String ... values) {
        log(Level.TRACE, formatString, values);
    }

    public void warn(String formatString, String ... values) {
        log(Level.WARN, formatString, values);
    }
}
