package unittests.support;

import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensEntityDecorator;
import org.junit.Assert;

import java.sql.Timestamp;

/**
 * Helps unit tests by providing variations of the ScreenEntity object
 */
public class ScreensEntityTestSupport {
    /**
     *
     * @param option String which indicates which variation of field values is required.
     * @param screenName screenName to use in the ScreenEntity object
     * @return Populated ScreensEntity object for us in testing
     */
    public static ScreensEntity getTestScreensEntity(String option, String screenName) {
        if (option.equals("general-disabled")) {
            ScreensEntityDecorator screensEntityDecorator = new ScreensEntityDecorator(
                    0,
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
            ScreensEntity screen = screensEntityDecorator.getScreen();
            // Check that screen was correctly instantiated.
            Assert.assertEquals(screenName, screen.getName());
            return screen;
        } else if (option.equals("general-enabled")) {
            ScreensEntityDecorator screensEntityDecorator = new ScreensEntityDecorator(
                    0,
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
            ScreensEntity screen = screensEntityDecorator.getScreen();
            // Check that screen was correctly instantiated.
            Assert.assertEquals(screenName, screen.getName());
            return screen;
        } else {
            return null;
        }
    }
}
