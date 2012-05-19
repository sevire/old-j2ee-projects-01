package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.model.WebPage;
import co.uk.genonline.simpleweb.model.bean.PageData;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 09/05/2012
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class Edit extends HttpServlet {
    Logger logger;
    Connection connection;
    PageData pageData = new PageData(connection);

    public void init() {
        logger = Logger.getLogger(this.getClass().getSimpleName());
        logger.setLevel(Level.ALL);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException, ServletException {

        logger.info("doPost Invoked");
        WebPage webPage = new WebPage((Connection)(getServletConfig().getServletContext().getAttribute("connection")));
        String screen = request.getParameter("screen");
        pageData.setName(screen);
        pageData.setScreenTitleShort(webPage.getField(screen, "screenTitleShort"));
        pageData.setScreenTitleLong(webPage.getField(screen, "screenTitleLong"));
        pageData.setScreenContents(webPage.getField(screen, "screenContents"));
        request.getSession().setAttribute("page", pageData);
        RequestDispatcher view = request.getRequestDispatcher("editScreen.jsp");
        logger.info(String.format("About to dispatch editScreen.jsp, screen is %s", screen));
        view.forward(request, response);
    }
}
