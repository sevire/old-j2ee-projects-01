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
import java.io.File;
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
    private WebLogger logger = new WebLogger();
    private static final String logPath = "/WEB-INF/logs/error.log";
    private ServletContext context;
    private Configuration configurationManager;
    private String appVersion = "nn.nn.nn.xxxx";

    public ContextListener() {
        System.out.format("ContextListener started (before logging)\n");
    }

    public void contextInitialized(ServletContextEvent event) {
        Level level;

        context = event.getServletContext();
        appVersion = context.getInitParameter("app-version");

        System.out.format("contextInitialised: v%s\n", appVersion);
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
        factory.getStatistics().setStatisticsEnabled(true);
        System.out.format("Result of getSessionFactory is %s\n", factory);

        logger.debug(String.format("Saving session factory in context attribute"));
        context.setAttribute("sessionFactory", factory);

        logger.info(String.format("context = <%s>, sessionFactory stored in context = <%s>", context, factory));
        logger.info("Creating and saving ConfigManager");

        configurationManager = new Configuration(factory);
        event.getServletContext().setAttribute("configuration", configurationManager);

        level = ((LoggingLevel)configurationManager.getConfigurationItem("loggingLevel")).get();
        if (level == null) {
            logger.setLevel(Level.ALL);
        } else {
            logger.setLevel(level);
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

    private FileAppender getAppender(ServletContextEvent event, String fileName) {
        System.out.format("BeforeLogging:getAppender: file = %s\n", fileName);
        RollingFileAppender appender = null;
        try {
            System.out.format("BeforeLogging:getAppender: Start of try\n");
            System.out.format("BeforeLogging:getAppender: before new RollingFileAppender\n");
            appender = new RollingFileAppender(
                    new PatternLayout("%-5p %c{1} %t %29d - %m%n"),
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

    private void initialiseGallery() {
        int maxThumbnailHeight = ((MaxThumbnailHeight)configurationManager.getConfigurationItem("maxThumbnailHeight")).get();
        if (maxThumbnailHeight <= 0) {
            logger.warn(String.format("Invalid value for 'maxHeight' (%s), setting to 100", maxThumbnailHeight));
            maxThumbnailHeight = 100;
        }
        int maxThumbnailWidth = ((MaxThumbnailWidth)configurationManager.getConfigurationItem("maxThumbnailWidth")).get();
        if (maxThumbnailWidth <= 0) {
            logger.warn(String.format("Invalid value for 'maxWidth' (%s), setting to 100", maxThumbnailWidth));
            maxThumbnailWidth = 100;
        }

        String webRootFullPath = this.context.getRealPath("/");

        // Change this to hard coded value while testing putting galleries outside webapp
        // String galleryRootRelPath = ((GalleryRoot)configurationManager.getConfigurationItem("galleryRoot")).get();
        // File galleryRootFullPathFile = new File(webRootFullPath + File.separator + galleryRootRelPath);

        String staticFileRootURL = ((StaticFileRootURL)configurationManager.getConfigurationItem("staticFileRootURL")).get();
        String staticFileRootFile = ((StaticFileRootFile)configurationManager.getConfigurationItem("staticFileRootFile")).get();
        String galleryRoot = ((GalleryRoot)configurationManager.getConfigurationItem("galleryRoot")).get();

        String galleryRootFilePath = staticFileRootFile + File.separator + galleryRoot;
        String galleryRootUrlPath = staticFileRootURL + File.separator + galleryRoot;

        File galleryRootFullPathFile = new File(galleryRootFilePath);

        String thumbnailRelPath = ((ThumbnailRelPath)configurationManager.getConfigurationItem("thumbnailRelPath")).get();
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        GalleryManagerConfiguration galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                galleryRootFullPathFile,
                galleryRootUrlPath,
                thumbnailRelPath,
                maxThumbnailHeight,
                maxThumbnailWidth,
                imageExtensionList);

        ThumbnailManager thumbnailManager = new ThumbnailManagerDefault(galleryManagerConfiguration);

        logger.info("Creating and saving Gallery Manager in context attribute");

        GalleryManager galleryManager = new GalleryManagerDefault(
                galleryManagerConfiguration,
                thumbnailManager,
                new GalleryCarouselHtmlGenerator(galleryManagerConfiguration));
        context.setAttribute("Galleries",galleryManager);
    }
}
