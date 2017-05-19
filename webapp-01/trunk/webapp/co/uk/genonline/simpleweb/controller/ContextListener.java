package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.configuration.configitems.*;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.model.HibernateUtil;
import co.uk.genonline.simpleweb.web.gallery.*;
import org.apache.log4j.*;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;

/**
 * Created with Intelli J IDEA.
 * User: thomassecondary
 * Date: 12/05/2012
 * Time: 19:00
 *
 * Class used to process changes of context state.  The methods are called by the container on change of state
 * of context (.e.g contextInitialised).
 */
public class ContextListener implements ServletContextListener {
    private static final String LOG_PATH = "/WEB-INF/logs/error.log";

    private WebLogger logger = new WebLogger();
    private ServletContext context;
    private Configuration configurationManager;
    private String appVersion = "nn.nn.nn.xxxx";

    public ContextListener() {
        System.out.format("ContextListener started (before logging)\n");
    }

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        appVersion = context.getInitParameter("app-version");

        initLogger();
        String contextPath = event.getServletContext().getContextPath();

        logger.info(String.format("ContextListener invoked, path = %s", contextPath));

        logger.debug("Getting session factory...");
        SessionFactory factory = HibernateUtil.getSessionFactory();
        factory.getStatistics().setStatisticsEnabled(true);
        System.out.format("Result of getSessionFactory is %s\n", factory);

        logger.debug(String.format("Saving session factory in context attribute"));
        context.setAttribute("sessionFactory", factory);

        logger.info(String.format("context = <%s>, sessionFactory stored in context = <%s>", context, factory));
        logger.info("Creating and saving ConfigManager");

        configurationManager = new Configuration(factory);
        event.getServletContext().setAttribute("configuration", configurationManager);

        Level level = ((LoggingLevel)configurationManager.getConfigurationItem("loggingLevel")).get();
        if (level == null) {
            logger.warn("Logging level not configured, using DEBUG");
            logger.setRootLevel(Level.DEBUG);
        } else {
            logger.info("Logging level configured as %s", level.toString());
            logger.setRootLevel(level);
        }

        initialiseGallery();

        // Initialise session level variable for status message and status type used in JSPs to display error messages etc.

        event.getServletContext().setAttribute("statusMessage", "");
        event.getServletContext().setAttribute("statusType", "none");
    }

    public void contextDestroyed(ServletContextEvent event) {
        SessionFactory sessionFactory = (SessionFactory)event.getServletContext().getAttribute("sessionFactory");
        long sessionCount = sessionFactory.getStatistics().getConnectCount();
        logger.trace(String.format("contextDestroyed: Number of connections is <%d>", sessionCount));
        sessionFactory.close();
    }

    private FileAppender getAppender(String fileName) {
        System.out.format("BeforeLogging:getAppender: file = %s\n", fileName);
        RollingFileAppender appender = null;
        try {
            System.out.format("BeforeLogging:getAppender: Start of try\n");
            System.out.format("BeforeLogging:getAppender: before new RollingFileAppender\n");
            appender = new RollingFileAppender(
                    new PatternLayout("%-5p %c{1} %t %29d - %m%n"),
                    context.getRealPath(fileName), true
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
        System.out.format("BeforeLogging:getAppender: End\n");
        return appender;
    }

    private void initLogger() {
        System.out.format("BeforeLogging:initLogger: v%s\n", appVersion);
        System.out.format("BeforeLogging:initLogger called - is logging working?\n");

        // Initialise level to fine grain in case configured value not set correctly
        logger.setRootLevel(Level.DEBUG);

        System.out.format("BeforeLogging:initLogger: before getAppender\n");
        FileAppender appender = getAppender(LOG_PATH);

        System.out.format("BeforeLogging:initLogger: before addAppender\n");
        logger.addAppenderToRoot(appender);

        System.out.format("BeforeLogging:Logger initialised - %s\n", logger.toString());
        System.out.format("BeforeLogging:Root logger level set to <%s>\n", logger.getRootLevel());
        System.out.format("BeforeLogging:Logger started - should see this message in first logger message now as INFO...\n");
        logger.info("BeforeLogging:Logger started - should see this message in first logger message now...");
        System.out.format("BeforeLogging:initLogger: Leaving \n");
    }

    private void initialiseGallery() {

        GalleryManagerConfiguration galleryManagerConfiguration =
                new GalleryManagerConfigurationDefault(configurationManager);

        ThumbnailManager thumbnailManager = new ThumbnailManagerDefault(galleryManagerConfiguration);

        logger.info("Creating and saving Gallery Manager in context attribute");

        GalleryManager galleryManager = new GalleryManagerDefault(
                galleryManagerConfiguration,
                thumbnailManager,
                new GalleryCarouselHtmlGenerator(galleryManagerConfiguration));
        context.setAttribute("Galleries",galleryManager);
    }
}
