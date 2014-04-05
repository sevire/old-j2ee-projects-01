package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.configuration.configitems.LoggingLevel;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import org.apache.log4j.Level;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for the website.  All requests are routed through this class.
 *
 * Here is the sequence of events for dealing with a request:
 *
 * - Controller receives the request as a GET or POST
 * - Controller passes request (and response) to Controller Helper via processRequest method
 * - Controller also passes a SessionFactory object (factory) to helper.  This was initialised
 *   by the ContextListener object at context (application) startup.
 * - processRequest method deals with all requests and returns either a jsp to forward the request to
 *   or a url to send back as a re-direct to the client (browser).
 *
 * It may be appropriate to split this into two or more controllers, but at the moment it isn't clear
 * whether this is going to make things better - possibly having a separate controller for admin may be
 * appropriate as I could put different security around it.
 *
 */
public class Controller extends HttpServlet {
    private final WebLogger logger = new WebLogger();

    /**
     * Calls parent constructor and initialises logger level in advance of reading in from configuration
     * data.
     */
    public Controller() {
        super();
        logger.setLevel(Level.ALL);
    }

    /**
     * Mandated method used to process any request sent using POST method.  In practice this method
     * will always call processRequest which is a common method for any request.  This avoids having to
     * repeat code or move code around if I change the request method.
     *
     * @param request Standard request object supplied from container
     * @param response Standard response object supplied to container
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *
     * Mandated method used to process any request sent using GET method.  In practice this method
     * will always call processRequest which is a common method for any request.  This avoids having to
     * repeat code or move code around if I change the request method.
     *
     * @param request Standard request object supplied from container
     * @param response Standard response object supplied to container
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Called once container has completed initialisation.  Initialisation code for the application
     * should be in here not in the constructor as that would be too early in the start up process.
     */
    public void init() {
        ServletContext context = getServletContext();
        Level level = Level.ALL;
        if (context == null) {
            logger.warn("null Context while setting logger level, setting to ALL");
        } else {
            level = ((LoggingLevel)((Configuration)(context.getAttribute("configuration"))).getConfigurationItem("loggingLevel")).get();
            if (level == null) {
                logger.warn("'loggingLevel' not found while setting logger level, setting to ALL");
            } else {
                logger.setLevel(level);
                logger.info("logging level set to " + level);
            }
        }
    }

    /**
     * Called by both doGet and doPost - generic method to process any request. Creates instance of ControllerHelper.
     * Passes SessionFactory instance into helper which is then used to access sessions for database access.
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.setSession(request);
        String callType = request.getMethod();
        ControllerHelper helper = new ControllerHelper(request, response, (SessionFactory)(getServletConfig().getServletContext().getAttribute("sessionFactory")));
//        ControllerHelper helper = new ControllerHelper(request, response);
        logger.info("%s : request = %s", callType, request.getRequestURI());
        helper.processRequest();
    }
}
