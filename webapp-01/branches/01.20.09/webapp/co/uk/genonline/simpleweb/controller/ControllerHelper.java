package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.controller.actions.Action;
import co.uk.genonline.simpleweb.controller.actions.ActionFactory;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
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
 *
 * ToDo: ControllerHelper: Update comment as out of date
 * The Controller Helper is stored in the HttpSession object for this session and is used both to:
 *
 * - Persist data between requests within the same session where appropriate
 * - Make data available to JSPs.
 *
 * All the session data is stored in a single object of type ActionData
 *
 * TODO: Make sure the helper only carries out these functions.
 */

public class ControllerHelper {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    WebLogger logger = new WebLogger();

    /**
     * Stores all data for session and makes it available to the JSPs through getters.
     */
    private SessionData sessionData;

    SessionFactory factory;

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response, SessionFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;

        logger.setSession(request);
        logger.info("Logger initiated");
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
    public void processRequest() throws IOException, ServletException {

        addPersistentDataToSession();

        RequestResult result;

        Action action = ActionFactory.createAction(request, response);
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
            logger.warn("Request %s not recognised", request.getRequestURI());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * This method contains the logic which works out whether this request is part of a sequence of requests within a
     * session and therefore extracts the data which needs to be persisted between requests in the session.
     *
     * It does this by:
     * - Checking whether there is an object called "sessionData" within the Session object passed as part of the
     *   request.
     * - If there is, then it copies this object into the sessionData member variable for this class so it can be used
     *   during the processing of the request.
     * - If there isn't, then it creates a new SessionData object in sessionData and then stores this in the session so
     *   it is persisted for the next request in the session (if there is one).
     *
     * NOTE: As a general rule (in this webapp) requests which are just to display a screen only result in one request
     *       within the session so the sessionData object isn't actually accessed.  Where there is a form which then
     *       needs to be processed the form data will
     */
    public void addPersistentDataToSession() {
        String sessionName = "sessionData";
        SessionData sessionDataFromSession = (SessionData)request.getSession().getAttribute(sessionName);
        if (sessionDataFromSession != null) {
            sessionData = sessionDataFromSession;
            sessionData.incrementRequestCount();
            logger.info("Multiple requests in session, copying session data = <%s>", sessionData.toString());
        } else {
            if (sessionData == null) {
                sessionData = new SessionData(new ScreensEntity());
                sessionData.incrementRequestCount();
                logger.info("First request in session, initialising session data, = <%s>", sessionData.toString());
                request.getSession().setAttribute(sessionName, sessionData);
            }
        }
    }

    public String toString() {
        return request.getRequestURL().toString();
    }
}
