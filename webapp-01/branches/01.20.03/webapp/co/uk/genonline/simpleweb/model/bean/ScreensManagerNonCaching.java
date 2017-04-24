package co.uk.genonline.simpleweb.model.bean;

import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.model.HibernateUtil;
import org.hibernate.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Implements a non-caching implementation of ScreensManager.  Note that this started out as a caching implementation
 * and I then decided to remove caching as the focus should initially be on something that is functionally correct
 * and which provides the operations I need for the app.
 *
 * At this point I don't know enough about Hibernate caching to know whether there is any benefit in implementing a
 * caching version of the ScreensManager but for now I will keep things simple and focussed.
 */
public class ScreensManagerNonCaching implements ScreensManager {
    private WebLogger logger = new WebLogger();

    private SessionFactory sessionFactory;

    public ScreensManagerNonCaching(SessionFactory sessionFactory) {
        //this.sessionFactory = sessionFactory;
        // While debugging Hibernate issue...

        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public ScreensEntity getScreen(String screenName, boolean enabledOverride) {
        ScreensEntity screen;
        String enabledClause;

        if (enabledOverride) {
            enabledClause = "";
        } else {
            enabledClause = " and enabledFlag is true";
        }
        screen = getScreenFromDatabase(String.format("from ScreensEntity s where name = '%s'%s", screenName, enabledClause));
        cleanScreen(screen);
        return screen;
    }

    public boolean addScreen(ScreensEntity screen) {
        if (screen.getName() == null || screen.getName().equals("")) {
            logger.error("Attempt to add screen with null or empty screenName");
            return false;
        } else {
            // Update created timestamp to current time
            screen.setCreated(new Timestamp(Calendar.getInstance().getTime().getTime()));
            return saveScreenInDatabase(screen, false);
        }
    }

    public boolean updateScreen(ScreensEntity screen) {
        if (screen.getName() == null || screen.getName().equals("")) {
            logger.error("Attempt to update screen with null or empty screenName");
            return false;
        } else {
            // Update modified timestamp to current time
            screen.setModified(new Timestamp(Calendar.getInstance().getTime().getTime()));
            return saveScreenInDatabase(screen, true);
        }
    }

    public List<ScreensEntity> getScreensByType(String screenType, ScreensSortType sortType, boolean enabledOverride) {
        String enabledClause = enabledOverride ? "" : " and enabledFlag = true ";
        String query = String.format("from ScreensEntity s where screenType = '%s'%s%s", screenType, enabledClause, sortType.getSortClause());
        return getScreensFromDatabase(query);
    }

    public List<ScreensEntity> getScreensByType(String screenType, boolean enabledOverride) {
        String enabledClause = enabledOverride ? "" : " and enabledFlag = true ";
        String query = String.format("from ScreensEntity s where screenType = '%s'%s", screenType, enabledClause);

        List<ScreensEntity> screens;
        screens = getScreensFromDatabase(query);
        if (screens == null || screens.size() == 0) {
            return null;
        } else {
            return screens;
        }
    }

    public List<ScreensEntity> getAllScreens(ScreensSortType sortType, boolean enabledOverride) {
        String enabledClause = enabledOverride ? "" : "where enabledFlag = true";
        String query = String.format("from ScreensEntity %s %s", enabledClause, sortType.getSortClause());
        return getScreensFromDatabase(query);
    }

    public List<String> getAllScreenNames(ScreensSortType sortType, boolean enabledOverride) {
        String enabledClause;
        if (enabledOverride) {
            enabledClause = "";
        } else {
            enabledClause = " where enabledFlag is true";
        }
        List<ScreensEntity> screens = getScreensFromDatabase("from ScreensEntity" + enabledClause);
        if (screens == null || screens.isEmpty()) {
            return null;
        } else {
            List<String> names = new ArrayList<String>();
            for (int i=0; i<screens.size(); i++) {
                names.add(i, screens.get(i).getName());
            }
            return names;
        }
    }

    public boolean deleteScreen(ScreensEntity screen) {
        return deleteScreenFromDatabase(screen.getName());
    }

    public boolean deleteScreen(String screenName) {
        return deleteScreenFromDatabase(screenName);
    }

    public boolean deleteScreen(int id) {
        return false;
    }

    public Timestamp getScreenCreatedTimestamp(String screenName) {
        return null;
    }

    public Timestamp setScreenModifiedTimestamp(String screenName) {
        return null;
    }

    /**
     * Test whether a record with a given screenName exists in the database without returning it.  This is included
     * to allow a more efficient approach than just calling getScreen and then checking whether result is null or not.
     *
     * Have done a bit of research into most efficient way of checking existence of record and not conclusive.  Have
     * decided to use SELECT COUNT() for now.
     *
     * @return true if screen with given screen name exists, false otherwise.
     */
    public boolean isScreenExist(String screenName) {
        return isInDatabase(screenName);
    }

    /**
     * Used to encapsulate any database query which returns a single screen
     * @param hqlQuery Hibernate query which defines row to return
     * @return Returned screen or null if not found.
     */
    private ScreensEntity getScreenFromDatabase(String hqlQuery) {
        logger.trace("getScreenFromDatabase start : hqlQuery = <%s>", hqlQuery);
        Session session = sessionFactory.getCurrentSession();
        ScreensEntity screen;
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            logger.debug("getScreenFromDatabase: Have begun transaction, transaction is <%s>", transaction.toString());

            screen = (ScreensEntity)session.createQuery(hqlQuery).uniqueResult();
            logger.debug("getScreenFromDatabase: About to commit transaction, transaction is <%s>", transaction.toString());
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(String.format("Hibernate error reading screen, error is %s", e.getMessage()));
            screen = null;
        }

        logger.trace("getScreenFromDatabase end, returning screen <%s>",
                screen == null ? "null" : screen.toString());
        return screen;
    }

    private List<ScreensEntity> getScreensFromDatabase(String hqlQuery) {
        logger.trace("getScreensFromDatabase start : hqlQuery = <%s>", hqlQuery);
        boolean error = false;
        Session session = sessionFactory.getCurrentSession();
        List<ScreensEntity> screens = new ArrayList<ScreensEntity>();
        Transaction transaction = null;
        List records;

        try {
            transaction = session.beginTransaction();

            logger.debug("getScreensFromDatabase: Have begun transaction, transaction is <%s>", transaction.toString());
            Query query = session.createQuery(hqlQuery);
            records = query.list();
            logger.debug("getScreensFromDatabase: About to commit transaction, transaction is <%s>", transaction.toString());
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("Error reading Screens, error is <%s>", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(String.format("Hibernate error reading screens, error is %s", e.getMessage()));
            records = null;
        }

        if (records != null) {

            // Avoids unchecked casting warning from compiler.  Not sure how necessary this is!
            for (Object record : records) {
                if (!error) {
                    if (record instanceof ScreensEntity) {
                        ScreensEntity screen = (ScreensEntity) record;
                        screens.add(screen);
                    } else {
                        error = true;
                    }
                }
            }
            if (error) {
                screens = null;
            } else {
                cleanScreens(screens);
            }
            logger.trace(String.format("getScreenFromDatabase end, returning <%d> screens",
                    screens == null ? -1 : screens.size()));
        }
        return screens;
    }

    /**
     * Adds or updates Screens record in database.
     * @param screen ScreensEntity object to be saved
     * @param updateFlag If false, Add record, if true, Update record
     * @return Returns true if successful, false if an error
     */
    private boolean saveScreenInDatabase(ScreensEntity screen, boolean updateFlag) {
        logger.trace("saveScreenInDatabase start : screen = <%s>, updateFlag <%b>",
                screen == null ? "null" : screen.toString(),
                Boolean.toString(updateFlag));

        boolean status = true; //true means success
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
        }
        catch (Exception e) {
            logger.error("Error starting transaction, error message: %s", e.getMessage());
            status = false;
        }
        if (status) {
            if (updateFlag) {
                try {
                    session.update(screen);
                    transaction.commit();
                } catch (HibernateException e) {
                    logger.error("Error updating screen <%s>, error = '%s'",
                            screen == null ? "null" : screen.getName(),
                            e.getMessage());
                    transaction.rollback();
                    status = false;
                }
            } else {
                try {
                    session.save(screen);
                    transaction.commit();
                } catch (HibernateException e) {
                    logger.error("Error adding screen <%s>, error = '%s'",
                            screen == null ? "null" : screen.getName(),
                            e.getMessage());
                    transaction.rollback();
                    status = false;
                }
            }
        }
        return status; // Not sure how to detect an error!
    }

    private boolean deleteScreenFromDatabase(String screenName) {
        boolean status = true;
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(String.format("delete ScreensEntity where name = '%s'", screenName));

        int result = query.executeUpdate();
        transaction.commit();
        if (result == 1) {
            logger.info("Screen %s deleted", screenName);
        } else if (result == 0) {
            logger.error("Screen %s was not deleted", screenName);
            status = false;
        } else {
            logger.error(String.format("Wrong number of rows (%d) deleted when attempting to delete Screen %s", result, screenName));
            status = false;
        }
        return status;
    }

    /**
     *  Checks values of fields in a ScreensEntity object and cleans things up, such as:
     *  - Replaces null values in certain fields with default values to avoid causing crashes.  This is
     *    a safety precaution but shouldn't happen often as database doesn't allow null values
     *  - ??
     */
    private void cleanScreen(ScreensEntity screen) {
        if (screen != null) {
            if (screen.getEnabledFlag() == null) screen.setEnabledFlag(false);
            if (screen.getGalleryFlag() == null) screen.setGalleryFlag(false);
            if (screen.getCreated() == null) screen.setCreated(new Timestamp(0));
            if (screen.getModified() == null) screen.setModified(new Timestamp(0));
        }
    }

    /**
     *  Checks values of fields in a ScreensEntity object and cleans things up, such as:
     *  - Replaces null values in certain fields with default values to avoid causing crashes.  This is
     *    a safety precaution but shouldn't happen often as database doesn't allow null values
     *  - ??
     */
    private void cleanScreens(List<ScreensEntity> screens) {
        for (ScreensEntity screen : screens) {
            cleanScreen(screen);
        }
    }

    private boolean isInDatabase(String screenName) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        long count = (Long)session.createQuery(String.format("select count(id) from ScreensEntity s where name = '%s'", screenName)).uniqueResult();
        try {
            transaction.commit();
        } catch (HibernateException e) {
            logger.error(String.format("Hibernate error reading screen, error is %s", e.getMessage()));
            return false;
        }
        return count == 1;
    }

    /**
     * Sets default values in a newly created ScreensEntity object.
     *
     * @param screen ScreensEntity object to initialise
     */
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

    /**
     * NOTE: Main use is in testing and debugging.  Quite resource hungry so should be used in live code.
     *
     * Calculate and format some summary information, including:
     * - Total number of screens
     * - Total number of screens in Cache
     * - Total number of Enabled screens
     * - Total number of screens with Galleries
     * @return Formatted string with key information for display
     */
    public String toString() {
        List<ScreensEntity> allScreens = getAllScreens(ScreensSortType.ADMIN_SCREEN, true);
        Integer numScreens = allScreens.size();
        return String.format("Num Screens: %d", numScreens);
    }

}
