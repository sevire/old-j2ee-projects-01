package unittests.controller;

import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.ControllerHelper;
import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.screendata.ContactMeScreenData;
import co.uk.genonline.simpleweb.controller.screendata.MistressScreenData;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensManager;
import co.uk.genonline.simpleweb.model.bean.ScreensManagerNonCaching;
import co.uk.genonline.simpleweb.web.gallery.GalleryManager;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import support.TestSupportLogger;
import unittests.support.ScreensUnitTestData;
import unittests.support.TestSupport;

import javax.servlet.ServletContext;

import static unittests.support.GallerySetup.gallerySetup;
import static unittests.support.TestSupport.*;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class ControllerHelperTest extends TestCase {
    //static private MockHttpServletRequest request;
    //static private MockHttpServletResponse response;
    static private ServletContext context;
    static private SessionFactory factory;
    // static private ControllerHelper helper;
    static private Configuration configuration;
    static private GalleryManager galleryManager;
    static private ScreensManager screensManager;


    @BeforeClass
    public static void beforeAll() {
        TestSupportLogger.initLogger();

        context = TestSupport.getNewServletContext();
        factory = (SessionFactory) context.getAttribute("sessionFactory");
        configuration = new Configuration(factory);

        context.setAttribute("configuration", configuration);
        assertSame(configuration, context.getAttribute("configuration"));

        galleryManager = gallerySetup();
        context.setAttribute("Galleries",galleryManager);
        assertSame(galleryManager, context.getAttribute("Galleries"));

        screensManager = new ScreensManagerNonCaching(factory);

        TestSupport.clearDatabase(factory);
        ScreensUnitTestData.initialiseAndCheckTestData();
    }

    /**
     * Funs after each test to clean up database and other clean up tasks
     */
    @AfterClass
    public static void afterAll() {
        TestSupport.clearDatabase(factory);
    }

    public void testGetScreen() throws Exception {
    }

    public void testGetConfigItems() throws Exception {

    }

    /**
     * This is the best compromise for testing end to end without going to httpunit, so make the most of it!
     * Tests every type of page with variations in Gallery enabled and page enabled etc.
     *
     * @throws Exception
     */
    @Test
    public void testProcessRequestViewScreen() throws Exception {

        // Set up screens to be tested.

        ScreensEntity screen;
        boolean addStatus;
        int status;
        String forwardUrl;

        MockHttpServletRequest request;
        MockHttpServletResponse response;
        ControllerHelper helper;

        for (int i = 1; i <= ScreensUnitTestData.getNumberOfTestCases(); i++) {
            System.out.println(String.format("ControllerHelperTest; Iteration %d", i));
            // Ignore cases which will have failed to get into the database

            if (ScreensUnitTestData.testCaseShouldBeInDatabase(i)) {
                String errorMessage = String.format("Assert failure on test case <%d>", i);

                screen = ScreensUnitTestData.getTestCase(i); // Raw case, not from database
                String screenType = screen.getScreenType();
                String screenDisplayType = screen.getScreenDisplayType();
                Boolean enabledFlag = screen.getEnabledFlag();

                // If enabledFlag is null need to give it a value.  Not sure best way to do this!
                if (enabledFlag == null) enabledFlag = false;
                String jspName;

                if (screenDisplayType == null || screenDisplayType.equals("dummy-01") || screenDisplayType.equals("")) {
                    jspName = enabledFlag ? "WEB-INF/mistress-05.jsp" : "WEB-INF/error.jsp";
                } else {
                    jspName = enabledFlag ? "WEB-INF/" + screenDisplayType + ".jsp" : "WEB-INF/error.jsp";
                }
                int responseStatus = enabledFlag ? 200 : 404;

                request = getTestRequest(context);
                assertNotNull(errorMessage, request);

                response = getTestResponse();
                assertNotNull(errorMessage, response);

                helper = new ControllerHelper(request, response, factory);
                assertNotNull(errorMessage, helper);

                viewScreenRequestSetup(request, screen.getName());
                helper.processRequest();

                status = response.getStatus();
                assertEquals(errorMessage, responseStatus, status);

                forwardUrl = response.getForwardedUrl();
                assertEquals(errorMessage, jspName, forwardUrl);

                Object screenData = request.getAttribute("screenData");

                if (enabledFlag) {
                    if (screenType.equals("Mistress")) {
                        assertTrue(String.format("Wrong display type for test case <%d>", i), screenData instanceof MistressScreenData);
                    } else if (screenType.equals("ContactMe")) {
                        assertTrue(String.format("Wrong display type for test case <%d>", i), screenData instanceof ContactMeScreenData);
                    }
                }
            }
        }
    }

    @Test
    public void testAddScreenProcessForm() throws Exception {
        for (int i=1; i<=50; i++) {
            if (ScreensUnitTestData.testCaseShouldBeInDatabase(i)) {
                // Have specifically added this method to re-produce a bug when attempting to add a duplicate screen.
                // Set up request to imitate form coming back from Add Screen but with a duplicate name

                ScreensEntity screen;
                int status;
                int responseStatus = 200;
                String forwardUrl;

                MockHttpServletRequest request;
                MockHttpServletResponse response;
                ControllerHelper helper;

                screen = ScreensUnitTestData.getTestCase(i); // Raw case, not from database

                // Set up error message to use on assertion error
                String errorMessage = String.format("Assert failure on test case <%d>", i);

                // We expect the operation to fail and return to Add screen (updateScreen.jsp)
                String expectedJspName = "WEB-INF/updateScreen.jsp";


                // Set up input
                request = getTestRequest(context);
                assertNotNull(errorMessage, request);

                response = getTestResponse();
                assertNotNull(errorMessage, response);

                ScreensEntity defaultScreenForAddScreen = new ScreensEntity();
                screensManager.initialiseBean(defaultScreenForAddScreen);
                SessionData sessionData = (SessionData) request.getSession().getAttribute("sessionData");
                sessionData.setScreen(defaultScreenForAddScreen);

                helper = new ControllerHelper(request, response, factory);
                assertNotNull(errorMessage, helper);

                addScreenRequestSetup(request, screen);
                helper.processRequest();

                status = response.getStatus();
                assertEquals(errorMessage, responseStatus, status);

                forwardUrl = response.getForwardedUrl();
                assertEquals(errorMessage, expectedJspName, forwardUrl);

                Object screenObject = request.getAttribute("screen");
                assertNotNull(screenObject);
                Assert.assertTrue(screenObject instanceof ScreensEntity);

                ScreensEntity redisplayScreen = (ScreensEntity) screenObject;

                // Screen that is being re-displayed should be same as one which was attempted to process.  Check before and after
                // Don't check ids as wouldn't expect to be same


                // ToDo: TEST: These tests should be in a method
                Assert.assertEquals(screen.getName() == null ? "" : screen.getName(), redisplayScreen.getName());
                Assert.assertEquals(screen.getSortKey() == null ? "" : screen.getSortKey(), redisplayScreen.getSortKey());
                Assert.assertEquals(screen.getScreenTitleLong() == null ? "" : screen.getScreenTitleLong(), redisplayScreen.getScreenTitleLong());
                Assert.assertEquals(screen.getScreenTitleShort() == null ? "" : screen.getScreenTitleShort(), redisplayScreen.getScreenTitleShort());
                Assert.assertEquals(screen.getScreenType() == null ? "" : screen.getScreenType(), redisplayScreen.getScreenType());
                Assert.assertEquals(screen.getScreenDisplayType() == null ? "" : screen.getScreenDisplayType(), redisplayScreen.getScreenDisplayType());
                Assert.assertEquals(screen.getScreenContents() == null ? "" : screen.getScreenContents(), redisplayScreen.getScreenContents());
                Assert.assertEquals(screen.getMetaDescription() == null ? "" : screen.getMetaDescription(), redisplayScreen.getMetaDescription());
                Assert.assertEquals(screen.getEnabledFlag() == null ? Boolean.FALSE : screen.getEnabledFlag(), redisplayScreen.getEnabledFlag());
                Assert.assertEquals(screen.getGalleryFlag() == null ? Boolean.FALSE : screen.getGalleryFlag(), redisplayScreen.getGalleryFlag());
            }
        }
    }

    public void testAddHelperToSession() throws Exception {

    }

    public void testCopyFromSession() throws Exception {

    }

    public void testToString() throws Exception {

    }

}