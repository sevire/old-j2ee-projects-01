package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.model.bean.Screens;
import co.uk.genonline.simpleweb.web.SessionData;
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
        logger.setLevel(Level.ALL);
        logger.info("Logger initiated - " + logger.getName());
        data = new Screens();
        this.factory = factory;
    }

    public Object getData() {
        return data;
    }

    protected void doPost()
        throws ServletException, IOException {

        addHelperToSession("helper", SessionData.READ);

        String address = null;
        boolean redirectFlag = false;
        pageNotFound = false;

        if (request.getParameter("updateButton") != null) {
            Action action = new UpdateScreenAction(request,response, factory, data);
            address = action.perform();
            redirectFlag = true;
        } else if (request.getParameter("addButton") != null) {
            Action action = new AddScreenAction(request,response, factory, data);
            address = action.perform();
            redirectFlag = true;
        } else if (request.getParameter("cancelButton") != null) {
            Action action = new CancelButtonAction(request,response, factory, data);
            address = action.perform();
            redirectFlag = true;
        } else {
            logger.error(String.format("DoPost: Didn't recognise request, displaying error page"));
            pageNotFound = true;
        }

        if (pageNotFound || address == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else if (redirectFlag) {
            response.sendRedirect(address);
        } else {
            logger.info("(doPost): Forwarding to " + address);
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
        }
    }

    protected void doGet() throws IOException, ServletException {

        addHelperToSession("helper", SessionData.READ);
        String command = request.getServletPath();
        logger.info("Servlet path is " + command);

        data.setName(request.getParameter("screen"));

        String address = null;
        boolean redirectFlag = false;

        if (command.equals("/view")) {
            logger.info("view: screen is " + data.getName());
            Action action = new ViewPageAction(request, response, factory, data);
            address = action.perform();
        } else if (command.equals("/edit")) {
            logger.info("edit: screen is " + data.getName());
            Action action = new EditScreenAction(request,response, factory, data);
            address = action.perform();
        } else if (command.equals("/add")) {
            logger.info("add: screen is " + data.getName());
            Action action = new AddAction(request,response, factory, data);
            address = action.perform();
        } else if (command.equals("/editIndex")) {
            logger.info("editIndex");
            Action action = new EditIndexAction(request,response, factory, data);
            address = action.perform();
        } else if (command.equals("/delete")) {
            logger.info("delete: screen is " + data.getName());
            Action action = new DeleteAction(request,response, factory, data);
            address = action.perform();
            redirectFlag = true; // ToDo: remove need for this in here
        } else if (command.equals("/viewImage")) {
            logger.info("Gallery View");
            Action action = new ViewImageAction(request,response, factory, data);
            address = action.perform();
        } else {
            logger.error(String.format("DoGet: Didn't recognise request <%s>, display error page", command));
            pageNotFound = true;
        }

        if (pageNotFound || address == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else if (redirectFlag) {
            logger.info("Redirecting to " + address);
            response.sendRedirect(address);
        } else {
            logger.info("Forwarding to " + address);
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
        }
    }

    public void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            data = ((ControllerHelper)sessionHelper).data;
        }
    }
}
