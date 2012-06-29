package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 10/06/2012
 * Time: 09:36
 * To change this template use File | Settings | File Templates.
 */
public class WebHelper {
    Logger logger;
    SessionFactory factory;
    HttpServletRequest request;
    HttpServletResponse response;

    public WebHelper(HttpServletRequest request, HttpServletResponse response,
                            SessionFactory factory) {

        logger = Logger.getLogger("ControllerHelper");
        logger.setLevel(Level.ALL);
        logger.info("Logger initiated - " + logger.getName());
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    public String generateScreenLink(String name, String screenTitleShort) {
        return String.format("<a href='view?screen=%s'>%s</a>",name, screenTitleShort);
    }

    public String generateLinkBarItem(String name, String screenTitleShort) {
        return String.format("<li class='headerLink'>%s</li>",
                generateScreenLink(name, screenTitleShort));
    }

    public String generateLinkBarCategory(String category) {
        String html = "";
        Session session = factory.openSession();
        String query = String.format("from Screens s where s.screenType = '%s'", category);
        logger.info("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        for (Object o : pages) {
            Screens screen = (Screens) o;
            html += generateLinkBarItem(screen.getName(), screen.getScreenTitleShort()) ;
        }
        html = "<ul>" + html + "</ul>";
        return html;
    }

    public String generateHomeLink() {
        String homePage = request.getServletContext().getInitParameter("homePage");
        logger.info(String.format("Generating home page = <%s>", homePage));
        return String.format("<a href='view?screen=%s'>Home</a>", homePage);
    }
}
