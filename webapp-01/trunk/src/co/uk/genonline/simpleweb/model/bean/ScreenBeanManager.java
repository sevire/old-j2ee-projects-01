package co.uk.genonline.simpleweb.model.bean;

import co.uk.genonline.simpleweb.controller.WebLogger;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 17/12/2012
 * Time: 14:35
 *
 * Not sure whether this is the best way to do this.  I want to carry out some activities on a bean (such as initialising)
 * but I don't want to modify the bean declaration as this is reverse engineered from the database schema.  So I am
 * creating this utility class as a layer of management.
 *
 * This should really be generic and be usable for any bean but I don't want to invest the time for that now and in
 * practice I only need to worry about Screens.
 */
public class ScreenBeanManager {
    Screens screenBean;
    SessionFactory factory;
    WebLogger logger = new WebLogger();

    public ScreenBeanManager(SessionFactory factory) {
        //this.screenBean = screenBean;
        this.factory = factory;
    }

    public List<Screens> getAllScreens() {
        Session session = factory.openSession();
        String query = String.format("from Screens s order by screenType, sortKey");
        logger.debug("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        return pages;
    }

    public List<Screens> getCategoryScreens(String category) {
        Session session = factory.openSession();
        String query = String.format("from Screens s where s.screenType = '%s' and s.enabledFlag = true order by sortKey", category);
        logger.debug("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        return pages;
    }

    public Screens getScreen(String screenName) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Screens.class).add(Restrictions.eq("name", screenName));
        Screens dbBean = (Screens) criteria.uniqueResult();
        return dbBean;
    }

    public void initialiseBean(Screens screen) {
        screenBean = screen;
        screenBean.setEnabledFlag(true);
        screenBean.setGalleryFlag(false);
        screenBean.setScreenContents("");
        screenBean.setMetaDescription("");
        screenBean.setName("");
        screenBean.setScreenTitleLong("");
        screenBean.setScreenTitleShort("");
        screenBean.setScreenType("Mistress");
        screenBean.setSortKey(100);

        screenBean.setId(0);
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
         *  ToDo : Check why I am not using fillBeanFromRequest!
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

    public String getShortName(String screenName) {
        return getScreen(screenName).getScreenTitleShort();
    }

    /**
     * Uses BeanUtils (populate) to transfer data from the request to the appropriate bean.  BeanUtils is generic
     * and uses introspection to work out what the properties of the passed in bean are.  This should work for all
     * the different beans within the application.
     *
     *
     * @param data
     */
    public void fillBeanFromRequest(Object data, HttpServletRequest request) {
        try {
            BeanUtils.populate(data, request.getParameterMap());
        } catch (IllegalAccessException e) {
            logger.error("Populate - Illegal Access", e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            logger.error("Populate - Invocation Target", e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
