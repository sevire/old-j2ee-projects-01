package support;

import org.apache.log4j.*;

import java.io.IOException;

/**
 * Created by thomassecondary on 13/05/15.
 */
public class TestSupportLogger {
    static Logger logger;

    private TestSupportLogger() {
    }

    public static void initLogger() {
        if (logger == null) {
            logger = Logger.getRootLogger();
            logger.setLevel(Level.ALL);

            try {
                Appender appender = new RollingFileAppender(
                        new PatternLayout("%-5p %c %t%n%29d - %m%n"),
                        "/Users/thomassecondary/Projects/lda-webapp/documents/testing/logs/testlogfile.log", true
                );
                logger.addAppender(appender);
                setLevel("org.hibernate", Level.OFF);
                setLevel("org.apache.commons.beanutils", Level.OFF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setLevel(String loggerName, Level level) {
        Logger logger = Logger.getLogger(loggerName);
        if (logger != null) {
            logger.setLevel(level);
        }
    }
}