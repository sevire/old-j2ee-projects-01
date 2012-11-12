package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.model.HibernateUtil;
import co.uk.genonline.simpleweb.web.gallery.GalleryManager;
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
        Level level;

        level = Level.toLevel(event.getServletContext().getInitParameter("loggingLevel"));
        if (level == null) {
            logger.setLevel(Level.ALL);
        } else {
            logger.setLevel(level);
        }
        FileAppender appender = getAppender(event, logPath);
        initLogger(null,appender,level);
        String contextPath = event.getServletContext().getContextPath();

        logger.info(String.format("Context invoked, path = %s", contextPath));
        logger.debug("Getting session factory...");

        SessionFactory factory = HibernateUtil.getSessionFactory();

        logger.debug(String.format("Saving session factory in context attribute"));
        event.getServletContext().setAttribute("sessionFactory", factory);

        logger.info("Creating and saving Gallery Manager in context attribute");
        String galleryRoot = event.getServletContext().getInitParameter("galleryRoot");

        logger.debug("Saving gallery root = " + galleryRoot);

        event.getServletContext().setAttribute("Galleries", new GalleryManager(event.getServletContext()));

        // Initialise session level variable for status message and status type used in JSPs to display error messages etc.

        event.getServletContext().setAttribute("statusMessage", "");
        event.getServletContext().setAttribute("statusType", "none");
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
