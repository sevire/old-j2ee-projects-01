package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.controller.actions.*;
import co.uk.genonline.simpleweb.model.bean.ConfigItems;
import co.uk.genonline.simpleweb.model.bean.Screens;
import co.uk.genonline.simpleweb.web.WebHelper;
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
    // Data which is persisted between sessions via copyFromSessionObj
    ActionData data;
    //boolean addFlag = false; // Used to expose addFlag to JSPs (via getAddFlag() method) to allow common jsp for add or edit screen.

    // Data not persisted between sessions.
    SessionFactory factory;
    boolean pageNotFound;

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response,
                            SessionFactory factory) {
        super(request, response);

        //logger.setLevel(Level.DEBUG);
        logger.info("Logger initiated");

        data = new ActionData(new Screens(), new ConfigItems());
        logger.info("Initialised data, = <%s>", data.toString());
        this.factory = factory;
    }

    // Method used to expose data to JSPs
    public Object getData() {
        logger.info("Returning (ActionData)'data' = <%s>", data.toString());
        return data.getScreen();
    }

/*
    public boolean getAddFlag() {
        return addFlag;
    }
*/

    protected void processRequest() throws IOException, ServletException {
        RequestStatus status;

        addHelperToSession("helper", SessionData.READ);

        // RequestStatus instance needs to be maintained at session level

        if (request.getSession().getAttribute("requestStatus") == null) {
            logger.info("Request status was null, session is : " + request.getSession());
            request.getSession().setAttribute("requestStatus", new RequestStatus());
        }
        status = (RequestStatus) request.getSession().getAttribute("requestStatus");
        logger.info("Controller process request initialisation, status = " + status);

        pageNotFound = false;
        String command = request.getServletPath();

        logger.debug("Servlet path is " + command);
        data.getScreen().setName(request.getParameter("screen"));

        RequestResult result = null;

        if (command.equals("/Controller.do")) {
            if (request.getParameter("updateButton") != null) {
                Action action = new EditScreenProcessForm(request, response, factory, data);
                result = action.perform();
            } else if (request.getParameter("addButton") != null) {
                Action action = new AddScreenProcessForm(request, response, factory, data);
                result = action.perform();
            } else if (request.getParameter("updateConfigButton") != null) {
                Action action = new EditConfigItemProcessForm(request, response, factory, data);
                result = action.perform();
            } else if (request.getParameter("addConfigButton") != null) {
                Action action = new AddConfigItemProcessForm(request, response, factory, data);
                result = action.perform();
            } else if (request.getParameter("cancelButton") != null) {
                Action action = new CancelAction(request, response, factory, data);
                result = action.perform();
            } else {
                pageNotFound = true;
            }
        } else if (command.equals("/view") || command.equals("/")) {
            status.resetStatusMessage();
            if (data.getScreen().getName() == null || data.getScreen().getName().equals("")) {
                WebHelper webHelper = new WebHelper(request, response, factory);
                data.getScreen().setName(webHelper.getHomePage());
            }
            logger.info("view: screen is " + data.getScreen().getName());
            Action action = new ViewScreen(request, response, factory, data);
            result = action.perform();
        } else if (command.equals("/edit")) {
            status.resetStatusMessage();
            logger.info("edit: screen is " + data.getScreen().getName());
            Action action = new EditScreenDisplayForm(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/add")) {
            status.resetStatusMessage();
            logger.info("add:");
            Action action = new AddScreenDisplayForm(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/addConfigItem")) {
            status.resetStatusMessage();
            logger.info("add config item:");
            Action action = new AddConfigItemDisplayForm(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/editIndex")) {
            //status.resetStatusMessage();
            logger.info("editIndex");
            Action action = new EditIndexDisplayForm(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/editConfigIndex")) {
            //status.resetStatusMessage();
            logger.info("editConfigIndex");
            Action action = new EditConfigIndexDisplayForm(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/delete")) {
            status.resetStatusMessage();
            logger.info("delete: screen is " + data.getScreen().getName());
            Action action = new DeleteScreen(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/viewImage")) {
            status.resetStatusMessage();
            logger.info("Gallery View");
            Action action = new ViewImage(request,response, factory, data);
            result = action.perform();
        } else {
            pageNotFound = true;
        }

        if (pageNotFound || result == null) {
            logger.error(String.format("processRequest: Didn't recognise request <%s>, display error page", command));
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else if (result.isRedirectFlag()) {
            logger.info("Redirecting to " + result.getNextRequest());
            response.sendRedirect(result.getNextRequest());
        } else {
            logger.info("Forwarding to " + result.getNextRequest());
            RequestDispatcher dispatcher = request.getRequestDispatcher(result.getNextRequest());
            dispatcher.forward(request, response);
        }
    }

    public void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            data = ((ControllerHelper)sessionHelper).data;
            //addFlag = ((ControllerHelper)sessionHelper).addFlag;
            logger.info("Copying data from session = <%s>", data.toString());
        }
    }
}
