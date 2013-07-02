package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.controller.actions.*;
import co.uk.genonline.simpleweb.model.bean.ConfigItems;
import co.uk.genonline.simpleweb.model.bean.Screens;
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
 * To change this template use File | Settings | File Templates.
 */
public class ControllerHelper extends HelperBase {
    /**
     * The helper object adds this field (and possibly others) to the session to allow certain information
     * to be persisted between requests for the same session. <code>data</code> will be used to store
     * JavaBeans for screen and configuration data.
     *
     * The helper object also contains logic to process the request coming from the Controller.
     *
     * ToDo: This almost certainly needs refactoring to encapsulate out the long string of conditionals
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
    ActionData data;

    SessionFactory factory;

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response,
                            SessionFactory factory) {
        super(request, response);

        logger.info("Logger initiated");

        data = new ActionData(new Screens(), new ConfigItems());
        logger.info("Initialised data, = <%s>", data.toString());
        this.factory = factory;
    }

    /**
     * Used to expose <code>data</code> object to jsps.  Since I added the ConfigItem to the ActionData class
     * (which is what <code>data</code> is declared as, I am re-coding this only to return the <code>screens</code>
     * object so that the screen.jsp continues to work.  Will probably have to re-visit.
     *
     * ToDo: Come back and check whether getData should do something different.
     * @return
     */
    public Object getData() {
        logger.info("Returning screens part of 'data' = <%s>", data.getScreen().toString());
        return data.getScreen();
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

        data.getScreen().setName(request.getParameter("screen"));

        RequestResult result = null;

        Action action = Action.createAction(request, response, factory, data);
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
            data = ((ControllerHelper)sessionHelper).data;
            logger.info("Copying data from session = <%s>", data.toString());
        }
    }
}
