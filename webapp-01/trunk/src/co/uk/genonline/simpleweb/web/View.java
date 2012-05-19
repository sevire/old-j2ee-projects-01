package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.model.WebPage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 09/05/2012
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class View extends HttpServlet {
    Logger logger;

    public void init() {
        logger = Logger.getLogger("doGet");
        logger.setLevel(Level.ALL);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        doGet(request, response);
    }
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException {

        logger.info("View (DoGet) Invoked");
        WebPage webPage = new WebPage((Connection)(getServletConfig().getServletContext().getAttribute("connection")));
        String screen = request.getParameter("screen");
        request.setAttribute("screenTitleShort", webPage.getField(screen, "screenTitleShort"));
        request.setAttribute("screenTitleLong", webPage.getField(screen, "screenTitleLong"));
        request.setAttribute("screenContents", webPage.getField(screen, "screenContents"));
        RequestDispatcher view = request.getRequestDispatcher("screen.jsp");
        logger.info(String.format("About to dispatch screen.jsp, screen is %s", screen));
        view.forward(request, response);
    }
}
