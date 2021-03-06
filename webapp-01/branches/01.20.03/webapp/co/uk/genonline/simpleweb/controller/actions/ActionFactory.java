package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.controller.actions.configactions.*;
import co.uk.genonline.simpleweb.controller.actions.screenactions.*;
import co.uk.genonline.simpleweb.controller.actions.simpleactions.ContactMeProcessForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 26/10/2013
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class ActionFactory {
    public static Action createAction(HttpServletRequest request, HttpServletResponse response) {
        WebLogger logger = new WebLogger(); // ToDo: Confirm whether this will work (Should logger be a field?)
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
                action = new EditScreenProcessForm(request, response);
            } else if (request.getParameter("addButton") != null) {
                action = new AddScreenProcessForm(request, response);
            } else if (request.getParameter("updateConfigButton") != null) {
                action = new EditConfigItemProcessForm(request, response);
            } else if (request.getParameter("addConfigButton") != null) {
                action = new AddConfigItemProcessForm(request, response);
            } else if (request.getParameter("cancelButton") != null) {
                action = new CancelAction(request, response);
            } else if (request.getParameter("cancelConfigButton") != null) {
                action = new CancelConfigAction(request, response);
            } else if (request.getParameter("contactMe") != null) {
                action = new ContactMeProcessForm(request, response);
            } else {
                logger.error(String.format("processRequest: Didn't recognise request <%s>, display error page", command));
                return null;
            }
        } else if (command.equals("/view") || command.equals("/")) {
            action = new ViewScreen(request, response);
        } else if (command.equals("/edit")) {
            status.resetStatusMessage();
            action = new EditScreenDisplayForm(request, response);
        } else if (command.equals("/add")) {
            status.resetStatusMessage();
            action = new AddScreenDisplayForm(request, response);
        } else if (command.equals("/editConfigItem")) {
            status.resetStatusMessage();
            action = new EditConfigItemDisplayForm(request, response);
        } else if (command.equals("/addConfigItem")) {
            status.resetStatusMessage();
            action = new AddConfigItemDisplayForm(request, response);
        } else if (command.equals("/editIndex")) {
            action = new EditIndexDisplayForm(request, response);
        } else if (command.equals("/editConfigIndex")) {
            action = new EditConfigIndexDisplayForm(request, response);
        } else if (command.equals("/reloadConfiguration")) {
            action = new ReloadConfigAction(request, response);
        } else if (command.equals("/delete")) {
            status.resetStatusMessage();
            action = new DeleteScreen(request, response);
        } else if (command.equals("/deleteConfigItem")) {
            status.resetStatusMessage();
            action = new DeleteConfigItem(request, response);
        } else if (command.equals("/viewImage")) {
            status.resetStatusMessage();
            action = new ViewImage(request, response);
        }
        return action;
    }
}
