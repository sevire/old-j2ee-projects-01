package unittests.support;

import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensManager;
import co.uk.genonline.simpleweb.model.bean.ScreensManagerNonCaching;
import org.hibernate.SessionFactory;
import org.junit.Assert;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Helps unit tests by providing variations of the ScreenEntity object
 */
public class ScreensEntityTestSupport {
    static SessionFactory factory = TestSupportSessionFactory.getSessionFactory();
    static ScreensManager screensManager = new ScreensManagerNonCaching(factory);
    static Integer[] disabledCases = {2,3,5,7,9,11};

    protected static Map<Integer, ScreensEntity> testScreenData = new HashMap<Integer, ScreensEntity>() {
        {
            // Set up 50 base records using a loop, then modify individual test cases to test different scenarios
            for (int i=1; i<=50; i++) {
                put(i, createScreen(-1, -1, "test-case-"+i, "", "Test Case " + i, "This is test case " + i, "Metadescription for test case "+i,
                        true, false, new Timestamp(0), new Timestamp(0), "Mistress", 100, "mistress-05"));

                if (Arrays.asList(disabledCases).contains(i)) {
                    ScreensEntity screen = get(i);
                    screen.setEnabledFlag(false);
                }
            }

            // Make changes as required



            // Now add to database

            for (int i=1; i<=50; i++) {
                screensManager.addScreen(get(i));
            }
        }
    };


        public static ScreensEntity createScreen(
            int id,
            int parentId,
            String name,
            String screenTitleLong,
            String screenTitleShort,
            String screenContents,
            String metaDescription,
            Boolean enabledFlag,
            Boolean galleryFlag,
            Timestamp created,
            Timestamp modified,
            String screenType,
            Integer sortKey,
            String screenDisplayType) {

        ScreensEntity screen = new ScreensEntity();

        screen.setId(id);
        screen.setName(name);
        screen.setParentId(parentId);
        screen.setScreenTitleLong(screenTitleLong);
        screen.setScreenTitleShort(screenTitleShort);
        screen.setScreenContents(screenContents);
        screen.setMetaDescription(metaDescription);
        screen.setEnabledFlag(enabledFlag);
        screen.setGalleryFlag(galleryFlag);
        screen.setCreated(created);
        screen.setModified(modified);
        screen.setScreenType(screenType);
        screen.setSortKey(sortKey);
        screen.setScreenDisplayType(screenDisplayType);

        return screen;
    }

    /**
     *
     * @param option String which indicates which variation of field values is required.
     * @param screenName screenName to use in the ScreenEntity object
     * @return Populated ScreensEntity object for use in testing
     */
    public static ScreensEntity getTestScreensEntity(String option, String screenName) {
        boolean validOption = true;

        ScreensEntity screen = null;
        if (option.equals("general-disabled")) {
            screen = createScreen(
                    -1,
                    0,
                    screenName,
                    "",
                    "",
                    "",
                    "",
                    false,
                    false,
                    new Timestamp(0),
                    new Timestamp(0),
                    "",
                    0,
                    "");
        } else if (option.equals("general-enabled")) {
            screen = createScreen(
                    -1,
                    0,
                    screenName,
                    "",
                    "",
                    "",
                    "",
                    true,
                    false,
                    new Timestamp(0),
                    new Timestamp(0),
                    "",
                    0,
                    "");
        } else {
            validOption = false;
        }
        if (validOption) {
            // Check that screen was correctly instantiated.
            Assert.assertEquals(screenName, screen.getName());
            return screen;
        } else {
            return null;
        }
    }

    /**
     * Creates chosen test case (hard coded) and optionally saves to database
     * @param caseNumber
     * @return
     */
    public static ScreensEntity getTestCase(int caseNumber) {
        return testScreenData.get(caseNumber);
    }

    public static int getNumberOfTestCases() {
        return testScreenData.size();
    }
}
