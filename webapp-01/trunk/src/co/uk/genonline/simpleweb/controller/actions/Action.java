package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.WebLogger;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class Action {

    public Action() {
        WebLogger logger = new WebLogger();
        logger.info("Instantiating Action sub-class <%s>", this.getClass().getName());
    }
    public abstract RequestResult perform();

    public static Action createAction(HttpServletRequest request, HttpServletResponse response,
                                      SessionFactory factory, ActionData data) {
        WebLogger logger = new WebLogger();
        RequestStatus status;
        String command = request.getServletPath();
        Action action = null;

        /**
         * 'requestStatus' maintains the status message across requests so that
         * If this is the first request within this session then
         */
        if (request.getSession().getAttribute("requestStatus") == null) {
            logger.info("Request status was null, session is : " + request.getSession());
            request.getSession().setAttribute("requestStatus", new RequestStatus());
        }
        status = (RequestStatus) request.getSession().getAttribute("requestStatus");
        logger.info("Controller process request initialisation, status = " + status);
        logger.debug("Servlet path is " + command);

        /**
         * 'Controller.do' implies that we are processing the result of a form within a jsp.
         * E.g. editPage, addPage etc.
         */
        if (command.equals("/Controller.do")) {
            if (request.getParameter("updateButton") != null) {
                action = new EditScreenProcessForm(request, response, factory, data);
            } else if (request.getParameter("addButton") != null) {
                action = new AddScreenProcessForm(request, response, factory, data);
            } else if (request.getParameter("updateConfigButton") != null) {
                action = new EditConfigItemProcessForm(request, response, factory, data);
            } else if (request.getParameter("addConfigButton") != null) {
                action = new AddConfigItemProcessForm(request, response, factory, data);
            } else if (request.getParameter("cancelButton") != null) {
                action = new CancelAction(request, response, factory, data);
            } else {
                logger.error(String.format("processRequest: Didn't recognise request <%s>, display error page", command));
                return null; // ToDo: Is this the right place to put this?
            }
        } else if (command.equals("/view") || command.equals("/")) {
            action = new ViewScreen(request, response, factory, data);
        } else if (command.equals("/edit")) {
            status.resetStatusMessage();
            action = new EditScreenDisplayForm(request, response, factory, data);
        } else if (command.equals("/add")) {
            status.resetStatusMessage();
            action = new AddScreenDisplayForm(request, response, factory, data);
        } else if (command.equals("/addConfigItem")) {
            status.resetStatusMessage();
            action = new AddConfigItemDisplayForm(request, response, factory, data);
        } else if (command.equals("/editIndex")) {
            action = new EditIndexDisplayForm(request, response, factory, data);
        } else if (command.equals("/editConfigIndex")) {
            action = new EditConfigIndexDisplayForm(request, response, factory, data);
        } else if (command.equals("/delete")) {
            status.resetStatusMessage();
            action = new DeleteScreen(request, response, factory, data);
        } else if (command.equals("/deleteConfig")) {
            status.resetStatusMessage();
            action = new DeleteConfigItem(request, response, factory, data);
        } else if (command.equals("/viewImage")) {
            status.resetStatusMessage();
            action = new ViewImage(request, response, factory, data);
        }
        return action;
    }
}
