package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.model.HibernateUtil;
import org.apache.log4j.*;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 12/05/2012
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class ContextListener implements ServletContextListener {
    Logger logger;
    private static final String logPath = "/WEB-INF/logs/error.log";

    public ContextListener() {
        logger = Logger.getLogger("Context");
        logger.setLevel(Level.ALL);
    }

    public void contextInitialized(ServletContextEvent event) {
        FileAppender appender = getAppender(event, logPath);
        initLogger(null,appender,Level.ALL);

        logger.info("Context invoked");
        logger.info("Getting session factory...");

        SessionFactory factory = HibernateUtil.getSessionFactory();

        logger.info(String.format("Saving session factory in context attribute"));
        event.getServletContext().setAttribute("sessionFactory", factory);
    }

    public void contextDestroyed(ServletContextEvent event) {
            ((SessionFactory)(event.getServletContext().getAttribute("sessionFactory"))).close();
    }

    private FileAppender getAppender(ServletContextEvent event, String fileName) {
        RollingFileAppender appender = null;
        try {
            appender = new RollingFileAppender(
                    new PatternLayout("%-5p %c %t%n%29d - %m%n"),
                    event.getServletContext().getRealPath(fileName), true
            );
            appender.setMaxBackupIndex(5);
            appender.setMaxFileSize("1MB");
        } catch (IOException e) {
            System.out.println(
                    "Could not create appender for "
                    + fileName + ":"
                    + e.getMessage()
            );
        }
        return appender;
    }

    private void initLogger(String name, FileAppender appender, Level level) {
        Logger logger;
        if (name == null) {
            logger = Logger.getRootLogger();

        } else {
            logger = Logger.getLogger(name);
        }
        logger.setLevel(level);
        logger.addAppender(appender);
        logger.info("Starting " + logger.getName());
    }
}
