package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.actions.*;
import co.uk.genonline.simpleweb.model.bean.ConfigurationEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import org.apache.log4j.Level;
import org.hibernate.SessionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 16/05/2012
 * Time: 07:38
 *
 * The Controller Helper is stored in the HttpSession object for this session and is used both to:
 *
 * - Persist data between requests within the same session where appropriate
 * - Make data available to JSPs.
 *
 * All the session data is stored in a single object of type ActionData
 *
 * TODO: Rename ActionData to SessionData but can't until IDEA bug fixed with unresponsive dialogs.
 * TODO: Make sure the helper only carries out these functions.
 */

public class ControllerHelper {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    WebLogger logger = new WebLogger();
    Configuration configuration;

    /**
     * Stores all data for session and makes it available to the JSPs through getters.
     */
    private ActionData sessionData;

    SessionFactory factory;

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response, SessionFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;

        logger.setSession(request);
        logger.setLevel(Level.DEBUG);
        logger.info("Logger initiated");
    }

    /**
     * Exposes sessionData.screens (Screens) to the jsp.
     *
     * @return
     */
    public Object getScreen() {
        logger.info("Returning screens part of 'data' = <%s>", sessionData.getScreen().toString());
        return sessionData.getScreen();
    }

    /**
     * Exposes sessionData.configItems (ConfigItems) to the jsp.
     *
     * @return
     */
    public Object getConfigItems() {
        logger.info("Returning config items part of 'data' = <%s>", sessionData.getConfigItems().toString());
        return sessionData.getConfigItems();
    }

    /**
     * General method which processes both get and post requests from Controller.  Serves a number of purposes:
     *
     * 1. Ensures that session data is persisted from last request
     * 2. Processes request using appropriate subclass of Action.
     * 3. Generates the next action, which will either be a re-direct or forward to another servlet (usually JSP).
     *
     * @throws IOException
     * @throws ServletException
     */
    protected void processRequest() throws IOException, ServletException {

        addHelperToSession("helper", SessionData.READ);

        RequestResult result = null;

        Action action = ActionFactory.createAction(request, response, factory, sessionData);
        if (action != null) {
            result = action.perform();
            if (result.isRedirectFlag()) {
                logger.info("Redirecting to " + result.getNextRequest());
                response.sendRedirect(result.getNextRequest());
            } else {
                logger.info("Forwarding to " + result.getNextRequest());
                RequestDispatcher dispatcher = request.getRequestDispatcher(result.getNextRequest());
                dispatcher.forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * The helper object (ControllerHelper) is stored in the HttpSession between requests to allow data to be
     * persisted throughout a session.  This is key to the correct operation of the application.  The EE framework
     * does all the work of identifying when a request is part of an existing session - all the application
     * developer needs to do is to ensure that the appropriate data is stored in the session object so that it is there
     * for the next action within this session.
     *
     * So the addHelper method carries out the following:
     *
     * <ul>
     * <li>Checks state (SessionData).</li>
     * <li>If state is READ then data from the previous request should be persisted into
     *   this request. This is carried out through the call to copyFromSession.</li>
     * <li>If state is IGNORE then data from previous request will be ignored (and effectively lost).</li>
     * <li>Then this helper will be added to the HttpSession object in place of the previous helper.  This is
     *   to persist data for the next request and to make data available to JSPs through getters.</li>
     * </ul>
     *
     * @param name The name to use when reading the object
     * @param state READ = Retain data from previous request, IGNORE don't pull in data across requests
     */
    public void addHelperToSession(String name, SessionData state) {
        if (SessionData.READ == state) {
            Object sessionObj = request.getSession().getAttribute(name);
            if (sessionObj != null) {
                copyFromSession(sessionObj);
            }
        }
        /**
         * If either we are not persisting session or there was a problem with the sessionData object in the retrieved
         * helper, then there will be no object assigned to sessionData, so create one before we replace the retrieved
         * (previous) helper with this one.
         */
        if (sessionData == null) {
            sessionData = new ActionData(new ScreensEntity(), new ConfigurationEntity());
            logger.info("Initialised sessionData, = <%s>", sessionData.toString());
        }
        request.getSession().setAttribute(name, this);
    }

    /**
     * If this is not the first request within this session, then there will (probably) be a copy of a previous
     * ControllerHelper object stored within the session (HttpSession).
     *
     * The sessionHelper passed in should be the same class as 'this'.  If that is the case then any data which needs
     * to be persisted between session is read from the stored helper and used to populate the equivalent fields within
     * this helper.  In practice all the persisted data is in the data field (ActionData) because (at the time of
     * writing) I just need to store one bean or another in there (Screen or ConfigItem).
     *
     * @param sessionHelper
     */
    public void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            sessionData = ((ControllerHelper)sessionHelper).sessionData;
            logger.info("Copying data from session = <%s>", sessionData.toString());
        }
    }

    public String toString() {
        return String.format("Helper Data: %s", request.getRequestURL().toString());
    }
}
