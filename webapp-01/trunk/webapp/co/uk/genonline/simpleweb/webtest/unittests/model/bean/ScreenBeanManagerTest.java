package unittests.model.bean;

import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import support.TestSupportLogger;
import unittests.support.TestSupportSessionFactory;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.List;

@RunWith(value=BlockJUnit4ClassRunner.class)
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

    public void testGetAllScreens() {

    }

    @Test
    public void testGetCategoryScreens() {
        for (int i=0; i<floodTestCount; i++) {
            List<ScreensEntity> screenList;
            screenList = screenBeanManager.getCategoryScreens("Mistress");
            assertEquals(6, screenList.size());
            screenList = screenBeanManager.getCategoryScreens("Lucina");
            assertEquals(10, screenList.size());
            screenList = screenBeanManager.getCategoryScreens("Gallery");
            assertEquals(3, screenList.size());
            screenList = screenBeanManager.getCategoryScreens("Testimonial");
            assertEquals(7, screenList.size());
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

        for (int i=0; i<floodTestCount; i++) {
            ScreensEntity emptyScreen = new ScreensEntity();
            emptyScreen.setName("cornelia");
            ScreensEntity screen = screenBeanManager.getScreen(emptyScreen);

            assertEquals("cornelia", screen.getName());
            assertEquals("Lady Cornelia", screen.getScreenTitleShort());
            assertEquals("Mistress Lady Cornelia", screen.getScreenTitleLong());
            assertTrue(screen.getEnabledFlag());
            assertTrue(screen.getGalleryFlag());
            assertEquals("Mistress", screen.getScreenType());
            assertEquals("mistress-03", screen.getScreenDisplayType()   );
        }
    }

    public void testInitialiseBean() {

    }

    public void testGetScreenIntoBean() {

    }

    public void testGetRequestIntoScreenBean() {

    }

    public void testGetShortName() {

    }

    public void testFillBeanFromMap() {

    }
}