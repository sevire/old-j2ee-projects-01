package co.uk.genonline.simpleweb.web;

import org.apache.log4j.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

        Connection conn;
        String dbName = event.getServletContext().getInitParameter("dbName");
        String dbUser = event.getServletContext().getInitParameter("dbUser");
        String dbPassword = event.getServletContext().getInitParameter("dbPassword");
        if (dbPassword.equals("EMPTY")) {
            dbPassword = "";
        }
        try {
            logger.info(String.format("Just about to establish connection using %s:%s:%s",dbName, dbUser, dbPassword));
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, dbUser, dbPassword);
            logger.info(String.format("Setting attribute 'connection'"));
            event.getServletContext().setAttribute("connection", conn);
        } catch (SQLException e) {
            logger.fatal(String.format("Error establishing connection %s:%s:%s",dbName, dbUser, dbPassword));
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        try {
            ((Connection)(event.getServletContext().getAttribute("connection"))).close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
