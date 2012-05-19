package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.model.bean.PageData;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sun.tools.tree.NewArrayExpression;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 16/05/2012
 * Time: 07:38
 * To change this template use File | Settings | File Templates.
 */
public class ControllerHelper extends HelperBase {
    protected PageData data;
    Logger logger;

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response,
                            Connection connection) {
        super(request, response);

        logger = Logger.getLogger("ControllerHelper");
        logger.setLevel(Level.ALL);
        data = new PageData(connection);
    }

    public Object getData() {
        return data;
    }

    protected void doPost()
        throws ServletException, IOException {

        request.getSession().setAttribute("helper", this);

        data.setName(request.getParameter("screen"));
        data.setScreenTitleShort(request.getParameter("screenTitleShort"));
        data.setScreenTitleLong(request.getParameter("screenTitleLong"));
        data.setScreenContents(request.getParameter("screenContents"));

        String address;

        if (request.getParameter("editButton") != null) {
            address = "editScreen.jsp";
        } else {
            logger.error(String.format("DoPost: Didn't recognise request, defaulting to screen view"));
            address = "screen.jsp";
            data.setName("Home");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    protected void doGet() throws IOException, ServletException {

        request.getSession().setAttribute("helper", this);
        String command = request.getServletPath();
        logger.info("Servlet path is " + command);

        data.setName(request.getParameter("screen"));
        data.setScreenTitleShort(request.getParameter("screenTitleShort"));
        data.setScreenTitleLong(request.getParameter("screenTitleLong"));
        data.setScreenContents(request.getParameter("screenContents"));

        String address;

        if (command.equals("/view")) {
            logger.info("view: screen is " + data.getName());
            data.populateScreen();
            address = "screen.jsp";
        } else if (command.equals("/edit")) {
            logger.info("edit: screen is " + data.getName());
            data.populateScreen();
            address = "editScreen.jsp";
        } else {
            logger.error(String.format("DoGet: Didn't recognise request, defaulting to screen view"));
            address = "screen.jsp";
            data.populateScreen();
            data.setName("Home");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }
}
