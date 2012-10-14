package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.model.bean.Screens;
import co.uk.genonline.simpleweb.web.SessionData;
import co.uk.genonline.simpleweb.web.WebHelper;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
    protected Screens data;
    Logger logger;
    SessionFactory factory;
    boolean pageNotFound;

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response,
                            SessionFactory factory) {
        super(request, response);

        //System.out.println("getLogger");
        logger = Logger.getLogger("ControllerHelper");
        logger.setLevel(Level.toLevel(request.getServletContext().getInitParameter("loggingLevel")));
        logger.info("Logger initiated - " + logger.getName());

        data = new Screens();
        this.factory = factory;

    }

    public Object getData() {
        return data;
    }

    protected void doPost() throws ServletException, IOException {
        processRequest();
    }

    protected void doGet() throws IOException, ServletException {
        processRequest();
    }

    protected void processRequest() throws IOException, ServletException {
        RequestStatus status;

        addHelperToSession("helper", SessionData.READ);

        // RequestStatus instance needs to be maintained at session level

        if (request.getSession().getAttribute("requestStatus") == null) {
            request.getSession().setAttribute("requestStatus", new RequestStatus());
        }
        status = (RequestStatus) request.getSession().getAttribute("requestStatus");
        status.setStatusMessage("I don't think I will ever see this!", "warning");

        pageNotFound = false;
        String command = request.getServletPath();

        logger.debug("Servlet path is " + command);
        data.setName(request.getParameter("screen"));

        RequestResult result = null;

        if (command.equals("/Controller.do")) {
            if (request.getParameter("updateButton") != null) {
                Action action = new UpdateScreenAction(request,response, factory, data);
                result = action.perform();
            } else if (request.getParameter("addButton") != null) {
                Action action = new AddScreenAction(request,response, factory, data);
                result = action.perform();
            } else if (request.getParameter("cancelButton") != null) {
                Action action = new CancelButtonAction(request,response, factory, data);
                result = action.perform();
            } else {
                pageNotFound = true;
            }
        } else if (command.equals("/view") || command.equals("/")) {
            status.resetStatusMessage();
            if (data.getName() == null || data.getName().equals("")) {
                WebHelper webHelper = new WebHelper(request, response, factory);
                data.setName(webHelper.getHomePage());
            }
            logger.info("view: screen is " + data.getName());
            Action action = new ViewPageAction(request, response, factory, data);
            result = action.perform();
        } else if (command.equals("/edit")) {
            status.resetStatusMessage();
            logger.info("edit: screen is " + data.getName());
            Action action = new EditScreenAction(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/add")) {
            status.resetStatusMessage();
            logger.info("add: screen is " + data.getName());
            Action action = new AddAction(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/editIndex")) {
            status.resetStatusMessage();
            logger.info("editIndex");
            Action action = new EditIndexAction(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/delete")) {
            status.resetStatusMessage();
            logger.info("delete: screen is " + data.getName());
            Action action = new DeleteAction(request,response, factory, data);
            result = action.perform();
        } else if (command.equals("/viewImage")) {
            status.resetStatusMessage();
            logger.info("Gallery View");
            Action action = new ViewImageAction(request,response, factory, data);
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
        }
    }
}
