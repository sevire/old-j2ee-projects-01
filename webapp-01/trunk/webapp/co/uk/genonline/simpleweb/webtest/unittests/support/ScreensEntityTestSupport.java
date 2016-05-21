package unittests.support;

import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import org.junit.Assert;

import java.sql.Timestamp;

/**
 * Helps unit tests by providing variations of the ScreenEntity object
 */
public class ScreensEntityTestSupport {

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
        ScreensEntity screen = null;
        switch (caseNumber) {
            case 1:
                screen = createScreen(
                        -1,
                        0,
                        "test-case-01",
                        "The Test Case 1",
                        "Test Case 1",
                        "This is test case 1",
                        "Metadata for test case 1",
                        true,
                        false,
                        new Timestamp(0),
                        new Timestamp(0),
                        "Mistress",
                        100,
                        "mistress-05"
                );
            break;
            case 2:
                screen = createScreen(
                        -1,
                        0,
                        "test-case-02",
                        "The Test Case 2",
                        "Test Case 2",
                        "This is test case 2",
                        "Metadata for test case 2",
                        false,
                        false,
                        new Timestamp(0),
                        new Timestamp(0),
                        "Mistress",
                        100,
                        "mistress-04"
                );
                break;
        }
        return screen;
    }
}
