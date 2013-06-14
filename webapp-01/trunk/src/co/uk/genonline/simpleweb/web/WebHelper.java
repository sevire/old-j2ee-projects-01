package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.servlet.ServletContext;
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
    WebLogger logger;
    SessionFactory factory;
    HttpServletRequest request;
    HttpServletResponse response;

    public WebHelper(HttpServletRequest request, HttpServletResponse response,
                            SessionFactory factory) {

        logger = new WebLogger(request);
//        logger.setLevel(Level.toLevel(request.getServletContext().getInitParameter("loggingLevel")));
        logger.info("Logger initiated");
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
        String query = String.format("from Screens s where s.screenType = '%s' and s.enabledFlag = true order by sortKey", category);
        logger.debug("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        for (Object o : pages) {
            Screens screen = (Screens) o;
            html += generateLinkBarItem(screen.getName(), screen.getScreenTitleShort()) ;
        }
        html = "<ul>" + html + "</ul>";
        return html;
    }

    public String generateHomeLink() {
        ServletContext context = request.getServletContext();
        String homePage = context.getInitParameter("homePage");
        logger.info(String.format("Generating home page = <%s>", homePage));
        return String.format("<a href='view?screen=%s'>Home</a>", homePage);
    }

    public String generateBlogLink() {
        return "<a href='http://lucifersdarkangel.co.uk/lucina-blog'>Blog</a>";
    }

    public String getHomePage() {
        return request.getServletContext().getInitParameter("homePage");
    }

    public String getScreenLink(String screenName) {
        Screens screenData = new Screens();
        getScreenIntoBean(screenData, screenName);
        return String.format("<a href='view?screen=%s'>%s</a>", screenName, screenData.getScreenTitleShort());
    }

    public void getScreenIntoBean(Screens screen, String screenName) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Screens.class).add(Restrictions.eq("name", screenName));
        Screens dbBean = (Screens) criteria.uniqueResult();
 
        screen.setName(dbBean.getName());
        screen.setSortKey(dbBean.getSortKey());
        screen.setEnabledFlag(dbBean.isEnabledFlag());
        screen.setGalleryFlag(dbBean.isGalleryFlag());
        screen.setScreenContents(dbBean.getScreenContents());
        screen.setMetaDescription(dbBean.getMetaDescription());
        screen.setScreenTitleLong(dbBean.getScreenTitleLong());
        screen.setScreenTitleShort(dbBean.getScreenTitleShort());
        screen.setScreenType(dbBean.getScreenType());
        screen.setId(dbBean.getId());
    }

    public void getRequestIntoBean(HttpServletRequest request, Screens screen) {
        /**
         * I think this should use standard BeanUtil methods (populateBean) or similar but I can't find a way to do this quickly
         * so will take a less elegant approach for now.
         */
/*
        boolean checked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");

*/
        boolean enabledChecked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");
        boolean galleryChecked = request.getParameter("galleryFlag") == null ? false : request.getParameter("galleryFlag").equals("Enabled");

        screen.setName(request.getParameter("name"));
        screen.setEnabledFlag(enabledChecked);
        screen.setGalleryFlag(galleryChecked);
        screen.setScreenContents(request.getParameter("screenContents"));
        screen.setMetaDescription(request.getParameter("metaDescription"));
        screen.setScreenTitleLong(request.getParameter("screenTitleLong"));
        screen.setScreenTitleShort(request.getParameter("screenTitleShort"));
        screen.setScreenType(request.getParameter("screenType"));
    }
}
 