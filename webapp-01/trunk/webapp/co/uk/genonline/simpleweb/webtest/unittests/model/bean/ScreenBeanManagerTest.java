package unittests.model.bean;

import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensEntityDecorator;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import support.TestSupportLogger;
import unittests.support.ScreensEntityTestSupport;
import unittests.support.TestSupportSessionFactory;

import java.util.List;

@RunWith(value=BlockJUnit4ClassRunner.class)

/**
 * Start with empty database and add any data required to carry out test as part of the test.  This will also
 * help to increase number of scenarios and tests overall.
 */
public class ScreenBeanManagerTest extends TestCase {
    // Hibernate configuration file location
    int floodTestCount = 1000;
    static ScreenBeanManager screenBeanManager;
    static SessionFactory factory;

    @BeforeClass
    public static void beforeAll() {
        TestSupportLogger.initLogger();
        factory = new TestSupportSessionFactory().getSessionFactory();
        screenBeanManager = new ScreenBeanManager(factory);
    }

    @AfterClass
    public static void afterAll() {
        factory.close();
    }

    public void tearDown() throws Exception {

    }

    protected ScreensEntity addTestCaseToDatabase(int testCastNumber) {
        ScreensEntity screen = ScreensEntityTestSupport.getTestCase(testCastNumber);
        screenBeanManager.saveScreen(screen, true);
        return screen;
    }

    @Test
    public void testGetAllScreens() {
        List<ScreensEntityDecorator> screenList;

        // First - set up some screens
        ScreensEntity screen = addTestCaseToDatabase(1);

        // Now retrieve the screens
        screenList = screenBeanManager.getAllScreens();

        // Check we've got what we expected
        assertEquals(1, screenList.size());

        // Now delete the screens to get back to where we started
        screenBeanManager.deleteScreen("test-case-01");

        // Finally check screens again to ensure there aren't any
        screenList = screenBeanManager.getAllScreens();
        assertEquals(0, screenList.size());
    }

    @Test
    public void saveScreen() throws Exception {

    }

    @Test
    public void deleteScreen() throws Exception {

    }

    @Test
    public void decorateScreenList() throws Exception {

    }

    @Test
    public void moveRequestIntoScreenBean() throws Exception {

    }

    @Test
    public void testGetCategoryScreens() {
        // First - set up some screens
        ScreensEntity screen = addTestCaseToDatabase(1);

        for (int i=0; i<floodTestCount; i++) {
            List<ScreensEntityDecorator> screenList;
            screenList = screenBeanManager.getCategoryScreens("Mistress");
            assertEquals(3, screenList.size());
            screenList = screenBeanManager.getCategoryScreens("Lucina");
            assertEquals(5, screenList.size());
            screenList = screenBeanManager.getCategoryScreens("Gallery");
            assertEquals(7, screenList.size());
            screenList = screenBeanManager.getCategoryScreens("Testimonial");
            assertEquals(9, screenList.size());
            screenList = screenBeanManager.getCategoryScreens("Mistress");
            assertEquals(6, screenList.size());
        }
    }

    /**
     * Tests both functionality and resilience.  Created when had lots of problems from Hibernate of
     * too many connections.  As part of the unit test (probably not strictly a unit test!) carry out many accesses
     * to the database to check that sessions are being handled properly.
     *
     * @throws Exception
     */
    @Test
    public void testGetScreen() {
        ScreensEntity testScreen = addTestCaseToDatabase(1);

        for (int i=0; i<floodTestCount; i++) {
            ScreensEntity retrievedScreen = screenBeanManager.getScreen("test-case-01");

            assertEquals(testScreen.getName(), retrievedScreen.getName());
            assertEquals(testScreen.getScreenTitleShort(), retrievedScreen.getScreenTitleShort());
            assertEquals(testScreen.getScreenTitleLong(), retrievedScreen.getScreenTitleLong());
            assertEquals(testScreen.getEnabledFlag(), retrievedScreen.getEnabledFlag());
            assertEquals(testScreen.getGalleryFlag(), retrievedScreen.getGalleryFlag());
            assertEquals(testScreen.getScreenType(), retrievedScreen.getScreenType());
            assertEquals(testScreen.getScreenDisplayType(), retrievedScreen.getScreenDisplayType());
        }
    }

    public void testInitialiseBean() {

    }

/*
    @Test
    public void testMoveRequestIntoScreenBean() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Add parameters as though they came from an HTML form in a POST request

        request.addParameter("name", "mistress-05");
        request.addParameter("screenTitleShort", "testName");
        request.addParameter("screenTitleLong", "Test Name");
        request.addParameter("screenContents", "Test contents");
        request.addParameter("metaDescription", "Meta description");
        request.addParameter("enabledFlag", "Full Test Name");
        request.addParameter("galleryFlag", "");
        request.addParameter("sortKey", "100");
        request.addParameter("screenType", "");
        request.addParameter("screenDisplayType", "Mistress");

        ScreensEntity screen = screenBeanManager.moveRequestIntoScreenBean(request, -1);
        assertNotNull(screen);

        assertEquals(screen.getName(), "mistress-05");
        assertEquals(screen.getSortKey(), new Integer(100));
    }
*/
}