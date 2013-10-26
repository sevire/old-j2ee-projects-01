package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.controller.actions.*;
import co.uk.genonline.simpleweb.model.bean.ConfigurationEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
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
 * Here is the sequence of events for dealing with a request:
 *
 * - Controller receives the request as a GET or POST
 * - Controller passes request (and response) to Controller Helper via processRequest method
 * - Controller also passes a SessionFactory object (factory) to helper.  This was initialised
 *   by the ContextListener object at context (application) startup.
 * - processRequest method deals with all requests and returns either a jsp to forward the request to
 *   or a url to send back as a re-direct to the client (browser).
 */

public class ControllerHelper extends HelperBase {

   /**
    * The helper object adds this field (and possibly others) to the session to allow certain information
    * to be persisted between requests for the same session. <code>data</code> will be used to store
    * JavaBeans for screen and configuration data.
    */
    private ActionData sessionData;

    SessionFactory factory;

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response,
                            SessionFactory factory) {
        super(request, response);

        logger.info("Logger initiated");

        sessionData = new ActionData(new ScreensEntity(), new ConfigurationEntity());
        logger.info("Initialised data, = <%s>", sessionData.toString());
        this.factory = factory;
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
     * General method which processes both get and post requests from Controller.
     *
     * - Calls <code>addHelperToSession</code> to recover data from previous request
     *   if there is any.
     * - Manages RequestStatus object which stores a status and associated message to be displayed
     *   in the browser as part of the response.
     * - Extracts URL from request and uses it to decide which Action sub-class to instantiate.
     * - NOTE: Should almost certainly be replacing this with a factory method for the Action class
     *   or similar.
     *
     * @throws IOException
     * @throws ServletException
     */
    protected void processRequest() throws IOException, ServletException {
        RequestStatus status;

        addHelperToSession("helper", SessionData.READ);

        sessionData.getScreen().setName(request.getParameter("screen"));

        RequestResult result = null;

        Action action = Action.createAction(request, response, factory, sessionData);
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
     * If this is not the first request within this session, then there will (probably) be a copy of a previous
     * ControllerHelper object stored within the sessions (HttpSession).
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
        return request.getRequestURL().toString();
    }
}
