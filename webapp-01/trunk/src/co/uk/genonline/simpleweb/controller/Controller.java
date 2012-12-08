package co.uk.genonline.simpleweb.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 14/05/2012
 * Time: 08:06
 * To change this template use File | Settings | File Templates.
 */
public class Controller extends HttpServlet {
    protected Logger logger;

    public Controller() {
        logger = Logger.getLogger("Controller");
        logger.setLevel(Level.ALL);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControllerHelper helper = new ControllerHelper(request, response, (SessionFactory)(getServletConfig().getServletContext().getAttribute("sessionFactory")));
        logger.info("doPost called, request = " + request.getRequestURI());
        helper.processRequest();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        ControllerHelper helper = new ControllerHelper(request, response, (SessionFactory)(getServletConfig().getServletContext().getAttribute("sessionFactory")));
        logger.info("doGet called, request = " + request.getRequestURI());
        helper.processRequest();
    }

    public void init() {
        ServletContext context = getServletContext();
        Level level = Level.ALL;
        if (context == null) {
            logger.warn("null Context while setting logger level, setting to ALL");
        } else {
            String levelString = context.getInitParameter("loggingLevel");
            if (levelString == null) {
                logger.warn("'loggingLevel' not found while setting logger level, setting to ALL");
            } else {
                level = Level.toLevel(levelString);
                if (level == null) {
                    logger.warn("Invalid logging level, setting to ALL");
                } else {
                    logger.setLevel(level);
                    logger.info("logging level set to" + level);
                }
            }
        }
    }
}
