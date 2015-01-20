package co.uk.genonline.simpleweb.model.bean;

import co.uk.genonline.simpleweb.controller.WebLogger;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    ScreensEntity screenBean;
    SessionFactory factory;
    WebLogger logger = new WebLogger();

    public ScreenBeanManager(SessionFactory factory) {
        //this.screenBean = screenBean;
        this.factory = factory;
    }

    public List<ScreensEntity> getAllScreens() {
        Session session = factory.openSession();
        String query = String.format("from ScreensEntity s order by enabledFlag desc, screenType, sortKey");
        logger.debug("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        return pages;
    }

    public List<ScreensEntity> getCategoryScreens(String category) {
        Session session = factory.openSession();
        String query = String.format("from ScreensEntity s where s.screenType = '%s' and s.enabledFlag = true order by sortKey", category);
        logger.debug("About to execute HQL query : " + query);
        List pages = session.createQuery(query).list();
        return pages;
    }

    public ScreensEntity getScreen(ScreensEntity screen) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(ScreensEntity.class).add(Restrictions.eq("name", screen.getName()));
        ScreensEntity dbBean = (ScreensEntity) criteria.uniqueResult();

        // I am setting the timestamp fields to avoid an error in the CopyProperties method.
        // ToDo: Do something better with these timestamp fields here!
        dbBean.setCreated(new Timestamp(0));
        dbBean.setModified(new Timestamp(0));
        try {
            BeanUtils.copyProperties(screen, dbBean);
        } catch (IllegalAccessException e) {
            logger.fatal("Fatal error while moving Screen properties : " + e.getMessage());
        } catch (InvocationTargetException e) {
            logger.fatal("Fatal error while moving Screen properties : " + e.getMessage());
        }
        return dbBean;
    }

    public void initialiseBean(ScreensEntity screen) {
        screenBean = screen;
        screenBean.setEnabledFlag(true);
        screenBean.setGalleryFlag(false);
        screenBean.setScreenContents("");
        screenBean.setMetaDescription("");
        screenBean.setName("");
        screenBean.setScreenTitleLong("");
        screenBean.setScreenTitleShort("");
        screenBean.setScreenType("Mistress");
        screenBean.setScreenDisplayType("");
        screenBean.setSortKey(100);

        screenBean.setId(0);
    }
    public void getScreenIntoBean(ScreensEntity screen, String screenName) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(ScreensEntity.class).add(Restrictions.eq("name", screenName));
        ScreensEntity dbBean = (ScreensEntity) criteria.uniqueResult();

        screen.setName(dbBean.getName());
        screen.setSortKey(dbBean.getSortKey());
        screen.setEnabledFlag(dbBean.getEnabledFlag());
        screen.setGalleryFlag(dbBean.getGalleryFlag());
        screen.setScreenContents(dbBean.getScreenContents());
        screen.setMetaDescription(dbBean.getMetaDescription());
        screen.setScreenTitleLong(dbBean.getScreenTitleLong());
        screen.setScreenTitleShort(dbBean.getScreenTitleShort());
        screen.setScreenType(dbBean.getScreenType());
        screen.setScreenDisplayType(dbBean.getScreenDisplayType());
        screen.setId(dbBean.getId());
    }

    /**
     * This method is effectively a layer between the request object and the ScreensEntity JavaBean.  The reason it is
     * required is because the request object does not represent the value of a checkbox in a way which will be
     * interpreted correctly by BeanUtils.populate(), which requires "true" or "false".  This method assumes that
     * the value assigned to a checkbox is "true" if checked (this requires the HTML form to be set up correctly,
     * and will be null if unchecked.  This method overwrites the null value with "false" if it exists.
     *
     * I am still unconvinced that this is the best way of doing this but at least I have isolated where I have
     * had to write field specific code.
     *
     * @param request HttpServletRequest object passed from EE container
     * @param screen ScreensEntity JavaBean to be populated.
     */
    public void getRequestIntoScreenBean(HttpServletRequest request, ScreensEntity screen) {

        Map requestMap = request.getParameterMap();
        Map amendedMap = new HashMap();
        amendedMap.putAll(requestMap);
        if (amendedMap.get("enabledFlag") == null) {
            logger.info("enabledFlag from request is <null>");
            amendedMap.put("enabledFlag", "false");
        } else {
            logger.info("enabledFlag from request is <%s>", amendedMap.get("enabledFlag").toString());
        }
        if (amendedMap.get("galleryFlag") == null) {
            logger.info("galleryFlag from request is <null>");
            amendedMap.put("galleryFlag", "false");
        } else {
            logger.info("galleryFlag from request is <%s>", amendedMap.get("galleryFlag").toString());
        }
        fillBeanFromMap(screen, amendedMap);
    }

    public String getShortName(String screenName) {
        ScreensEntity screen = new ScreensEntity();
        screen.setName(screenName);
        return getScreen(screen).getScreenTitleShort();
    }

    /**
     * Uses BeanUtils (populate) to transfer data from a Map to the appropriate bean.
     *
     * In order to work the values in the map must have names which are the same as the field names in the bean.
     * The method has been designed to work with request.getParameterMap() in mind.
     *
     * Please note that if using request.getParameterMap() to generate the Map to pass into this method, that
     * checkbox parameters in a request will need to be pre-processed so that the value is 'true' or 'false'.
     *
     * @param data
     * @param parameterMap
     */
    public void fillBeanFromMap(Object data, Map parameterMap) {
        try {
            BeanUtils.populate(data, parameterMap);
        } catch (IllegalAccessException e) {
            logger.error("Populate - Illegal Access", e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            logger.error("Populate - Invocation Target", e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
