package co.uk.genonline.simpleweb.model.bean;

import co.uk.genonline.simpleweb.controller.WebLogger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
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
    SessionFactory factory;
    WebLogger logger = new WebLogger();

    public ScreenBeanManager(SessionFactory factory) {
        this.factory = factory;
    }

    public List<ScreensEntityDecorator> getAllScreens() {
        Session session = factory.openSession();
        String query = String.format("from ScreensEntity s order by enabledFlag desc, screenType, sortKey");
        logger.debug("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        session.close();
        return decorateScreenList(pages);
    }

    public List<ScreensEntityDecorator> getCategoryScreens(String category) {
        Session session = factory.openSession();

        String query = String.format("from ScreensEntity s where s.screenType = '%s' and s.enabledFlag = true order by sortKey", category);
        logger.debug("About to execute HQL query : " + query);
        List pages = session.createQuery(query).list();
        session.close();
        return decorateScreenList(pages);
    }

    public ScreensEntityDecorator getScreen(String screenName) {
        Session session = factory.openSession(); // Open Session #1

        Criteria criteria = session.createCriteria(ScreensEntity.class).add(Restrictions.eq("name", screenName));
        ScreensEntity dbBean = (ScreensEntity) criteria.uniqueResult();
        session.close();

        if (dbBean == null) {
            return null;
        } else {
            return new ScreensEntityDecorator(dbBean);
        }
    }
    
    public boolean saveScreen(ScreensEntity screen, boolean addFlag) {
        Session session = factory.openSession();
        boolean success = true;

        logger.info(String.format("About to update screen, id is <%d>", screen.getId()));
        logger.debug("Contents = \n%s", screen.getScreenContents());
        if (addFlag) {
            session.save(screen);
        } else {
            session.update(screen);
        }
        try {
            session.flush();
        } catch (Exception e) {
            logger.info("Error saving data : %s", e.getMessage());
            success = false;
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return success;
    }

    public boolean deleteScreen(String screenName) {
        ScreensEntityDecorator deleteScreen;
        Session session = factory.openSession();
        boolean success = true;

        deleteScreen = getScreen(screenName);
        session.delete(deleteScreen.getScreen());
        try {
            session.flush();
        } catch (Exception e) {
            logger.info("Error deleting screen <%s> : error : %s", screenName, e.getMessage());
            success = false;
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return success;

    }

    /**
     * Sets value of fields within a ScreensEntity bean to default values.  Used for example when adding a new screen
     * to determine what values are set for the fields in the form on the add screen page.
     *
     * @param screen
     */
/*
    public void initialiseBean(ScreensEntity screen) {
        screen.setEnabledFlag(true);
        screen.setGalleryFlag(false);
        screen.setScreenContents("");
        screen.setMetaDescription("");
        screen.setName("");
        screen.setScreenTitleLong("");
        screen.setScreenTitleShort("");
        screen.setScreenType("Mistress");
        screen.setScreenDisplayType("");
        screen.setSortKey(100);

        screen.setId(0);
    }
*/

    public List<ScreensEntityDecorator> decorateScreenList(List screenList) {
        List<ScreensEntityDecorator> decoratedScreenList = new ArrayList<ScreensEntityDecorator>();

        for (Object screen : screenList) {
            if (screen.getClass() == ScreensEntity.class) {
                decoratedScreenList.add(new ScreensEntityDecorator((ScreensEntity)screen));
            } else {
                logger.error("Method decorateScreenList, object from Hibernate not ScreensEntity");
            }
        }
        return decoratedScreenList;
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
     * @param id      If the transaction is an update then we need to supply the id (primary key) we are updating.
     *                -1 indicates don't update id, any positive integer indicates use this id.
     */
/*
    public ScreensEntity moveRequestIntoScreenBean(HttpServletRequest request, int id) {

        Map requestMap = request.getParameterMap();
        Map amendedMap = new HashMap();
        amendedMap.putAll(requestMap);

        ScreenRequestDecorator requestDecorator = new ScreenRequestDecorator();
        ScreensEntity screen = new ScreensEntity();
        requestDecorator.fillBeanFromMap(screen, amendedMap);

        if (id >= 0) {
            screen.setId(id);
        }
        return screen;
    }
*/
}
