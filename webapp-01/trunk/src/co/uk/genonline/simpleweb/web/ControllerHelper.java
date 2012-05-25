package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
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

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response,
                            SessionFactory factory) {
        super(request, response);

        logger = Logger.getLogger("ControllerHelper");
        logger.setLevel(Level.ALL);
        data = new Screens();
        this.factory = factory;
    }

    public Object getData() {
        return data;
    }

    public String editMethod() {
        return jspLocation("editScreen.jsp");
    }

    public String viewMethod() {
        Session session = factory.openSession();
        String query = String.format("from Screens s where s.name = '%s'", data.getName());
        logger.info("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        data.setScreenContents(((Screens) pages.get(0)).getScreenContents());
        return jspLocation("screen.jsp");
    }

    protected void doPost()
        throws ServletException, IOException {

        request.getSession().setAttribute("helper", this);

        data.setName(request.getParameter("screen"));
        data.setScreenTitleShort(request.getParameter("screenTitleShort"));
        data.setScreenTitleLong(request.getParameter("screenTitleLong"));
        data.setScreenContents(request.getParameter("screenContents"));

        String address;

        if (request.getParameter("updateButton") != null) {
            address = editMethod();
        } else {
            logger.error(String.format("DoPost1: Didn't recognise request, defaulting to screen view"));
            address = "screen.jsp";
            data.setName("Home");
        }
        logger.info("(doPost): Forwarding to " + address);
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    protected void doGet() throws IOException, ServletException {

        addHelperToSession("helper", SessionData.READ);
        String command = request.getServletPath();
        logger.info("Servlet path is " + command);

        data.setName(request.getParameter("screen"));
/*
        data.setScreenTitleShort(request.getParameter("screenTitleShort"));
        data.setScreenTitleLong(request.getParameter("screenTitleLong"));
        data.setScreenContents(request.getParameter("screenContents"));
*/

        String address;

        if (command.equals("/view")) {
            logger.info("view: screen is " + data.getName());
            address = viewMethod();
        } else if (command.equals("/edit")) {
            logger.info("edit: screen is " + data.getName());
            address = jspLocation("editScreen.jsp");
        } else {
            logger.error(String.format("DoGet: Didn't recognise request, defaulting to screen view"));
            address = jspLocation("screen.jsp");
            data.setName("Home");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    public void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            data = ((ControllerHelper)sessionHelper).data;
        }
    }

    protected String jspLocation(String page) {
        return page;
    }
}
