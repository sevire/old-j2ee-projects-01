package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.configuration.configitems.GalleryRoot;
import co.uk.genonline.simpleweb.configuration.configitems.LoggingLevel;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
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
 *
 * Class used to process changes of conttext state.  The methods are called by the container on change of state
 * of context (.e.g contextInitialised).
 */
public class ContextListener implements ServletContextListener {
    private WebLogger logger = new WebLogger();
    private static final String logPath = "/WEB-INF/logs/error.log";

    public ContextListener() {
        System.out.format("ContextListener started (before logging)\n");
/*
        logger = Logger.getLogger("Context");
        logger.setLevel(Level.ALL);
        logger.info(String.format("Logger initialised - this is the first message"));
*/
    }

    public void contextInitialized(ServletContextEvent event) {
        Level level;

        System.out.format("BeforeLogging:contextInitialized called - is logging working?\n");
        level = Level.DEBUG; // Initial value until we have read configuration.
        logger.setLevel(Level.DEBUG);
        System.out.format("BeforeLogging:contextInitialized before getAppender\n");
        FileAppender appender = getAppender(event, logPath);
        System.out.format("BeforeLogging:contextInitialized before initLogger\n");
        initLogger(null,appender,level);
        String contextPath = event.getServletContext().getContextPath();

        logger.info(String.format("ContextListener invoked, path = %s", contextPath));

        logger.debug("Getting session factory...");
        SessionFactory factory = HibernateUtil.getSessionFactory();
        System.out.format("Result of getSessionFactory is %s\n", factory);

        logger.debug(String.format("Saving session factory in context attribute"));
        event.getServletContext().setAttribute("sessionFactory", factory);

        logger.info("Creating and saving ConfigManager");

        Configuration configurationManager = new Configuration(factory);
        event.getServletContext().setAttribute("configuration", configurationManager);

        level = ((LoggingLevel)configurationManager.getConfigurationItem("loggingLevel")).get();
        if (level == null) {
            logger.setLevel(Level.ALL);
        } else {
            logger.setLevel(level);
        }


        logger.info("Creating and saving Gallery Manager in context attribute");
        String galleryRoot = ((GalleryRoot)configurationManager.getConfigurationItem("galleryRoot")).get();

        logger.info("Saving gallery root = " + galleryRoot);
        event.getServletContext().setAttribute("Galleries", new GalleryManager(event.getServletContext()));

        // Initialise session level variable for status message and status type used in JSPs to display error messages etc.

        event.getServletContext().setAttribute("statusMessage", "");
        event.getServletContext().setAttribute("statusType", "none");
    }

    public void contextDestroyed(ServletContextEvent event) {
            ((SessionFactory)(event.getServletContext().getAttribute("sessionFactory"))).close();
    }

    private FileAppender getAppender(ServletContextEvent event, String fileName) {
        System.out.format("BeforeLogging:getAppender: file = %s\n", fileName);
        RollingFileAppender appender = null;
        try {
            System.out.format("BeforeLogging:getAppender: Start of try\n");
            System.out.format("BeforeLogging:getAppender: before new RollingFileAppender\n");
            appender = new RollingFileAppender(
                    new PatternLayout("%-5p %c %t%n%29d - %m%n"),
                    event.getServletContext().getRealPath(fileName), true
            );
            System.out.format("BeforeLogging:getAppender: after new RollingFileAppender\n");
            appender.setMaxBackupIndex(5);
            appender.setMaxFileSize("1MB");
            System.out.format("BeforeLogging:getAppender: End of try\n");
        } catch (IOException e) {
            System.out.format("BeforeLogging:getAppender: Start of catch\n");
            System.out.println(
                    "Could not create appender for "
                            + fileName + ":"
                            + e.getMessage()
            );
            System.out.format("BeforeLogging:getAppender: End of catch\n");
        }
        System.out.format("BeforeLogging:getAppender: End\n", fileName);
        return appender;
    }

    private void initLogger(String name, FileAppender appender, Level level) {
        Logger logger;
        System.out.format("BeforeLogging:initLogger: Entering \n");
        if (name == null) {
            logger = Logger.getRootLogger();

        } else {
            logger = Logger.getLogger(name);
        }
        logger.setLevel(level);
        logger.addAppender(appender);
        logger.info("Starting " + logger.getName());
        System.out.format("BeforeLogging:initLogger: Leaving \n");
    }
}
