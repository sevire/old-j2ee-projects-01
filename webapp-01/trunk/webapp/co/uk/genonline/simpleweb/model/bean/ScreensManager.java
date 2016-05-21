package co.uk.genonline.simpleweb.model.bean;

import java.sql.Timestamp;
import java.util.List;

/**
 * Defines the interface to be used to manage all Screen data within the webapp.
 */
public interface ScreensManager {
    /**
     * Returns a ScreensEntity object with data for the named screen.  Transparent to caller whether database access is
     * required.
     *
     * @param screenName Name of the screen for which data is required.
     * @param enabledOverride If not set only enabled screens returned. Set to override this.
     * @return ScreensEntity object filled with data for requested screen.  Null if screen doesn't exist (or error)
     */
    ScreensEntity getScreen(String screenName, boolean enabledOverride);

    /**
     * Takes ScreensEntity object and adds a record to the database for screen corresponding to the contained name.
     * @param screen ScreensEntity object populated with data to use to update database.
     * @return Returns true if screen successfully added. Returns false if screen already exists or there was an error.
     */
    boolean addScreen(ScreensEntity screen);

    /**
     * Takes ScreensEntity object and updates a record to the database for screen corresponding to the contained name.
     * @param screen ScreensEntity object populated with data to use to update database.
     * @return Returns true if screen successfully updated. Returns false if screen doesn't already exist or there was
     *         an error.
     */
    boolean updateScreen(ScreensEntity screen);

    /**
     * Returns all the screens for a given screen type.  Screen type is used to distinguish between types of page and
     * (typically) to create corresponding link bars for all screens of a given type.
     *
     * @param screenType The screen type of the screens to return.
     * @param sortType Indicates the order in which the screens are to be returned.
     * @return
     */
    List<ScreensEntity> getScreensByType(String screenType, ScreensSortType sortType);

    /**
     * Variant without sortType as most of the time sort will be via sortKey to use in linkbars.
     *
     * @param screenType
     * @return
     */
    List<ScreensEntity> getScreensByType(String screenType);

    /**
     * Extracts list of objects contain all enabled screens, or all screens if enabledOverride flag is set.
     *
     * @param sortType Determinse sort order of screens returned.
     * @return List of ScreensEntity objects.
     */
    List<ScreensEntity> getAllScreens(ScreensSortType sortType, boolean enabledOverride);

    /** Extracts just the screen names of the enabled screens within the database, or all screens if enabledOverride
     * flag is set.
     *
     * @param sortType Determines sort order of screens returned.
     * @param enabledOverride If not set only returns enabled screens, otherwise all screens.
     * @return
     */
    List<String> getAllScreenNames(ScreensSortType sortType, boolean enabledOverride);

    boolean deleteScreen(ScreensEntity screen);

    boolean deleteScreen(String screenName);

    boolean deleteScreen(int id);

    Timestamp getScreenCreatedTimestamp(String screenNane);

    Timestamp setScreenModifiedTimestamp(String screenName);

    boolean isScreenExist(String screenName);

    /**
     * Sets value of fields within a ScreensEntity bean to default values.  Used for example when adding a new screen
     * to determine what values are set for the fields in the form on the add screen page.
     *
     * @param screen
     */
    void initialiseBean(ScreensEntity screen);
}
