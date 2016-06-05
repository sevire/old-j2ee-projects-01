package unittests.controller;

import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.ControllerHelper;
import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.screendata.ContactMeScreenData;
import co.uk.genonline.simpleweb.controller.screendata.MistressScreenData;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensManager;
import co.uk.genonline.simpleweb.model.bean.ScreensManagerNonCaching;
import co.uk.genonline.simpleweb.web.gallery.GalleryManager;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import support.TestSupportLogger;
import unittests.support.ScreensEntityTestSupport;
import unittests.support.TestSupport;

import javax.servlet.ServletContext;

import static unittests.support.GallerySetup.gallerySetup;

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
    }

    /**
     * Funs after each test to clean up database and other clean up tasks
     */
    @After
    public void tearDown() {
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
    public void testProcessRequest() throws Exception {

        // Set up screens to be tested.

        ScreensEntity screen;
        boolean addStatus;
        int status;
        String forwardUrl;

        MockHttpServletRequest request;
        MockHttpServletResponse response;
        ControllerHelper helper;

        for (int i=1; i <= ScreensEntityTestSupport.getNumberOfTestCases(); i++) {
            String errorMessage = String.format("Assert failure on test case <%d>", i);

            screen = ScreensEntityTestSupport.getTestCase(i);
            String screenType = screen.getScreenType();
            String screenDisplayType = screen.getScreenDisplayType();
            boolean enabledFlag = screen.getEnabledFlag();

            String jspName = enabledFlag ? "WEB-INF/" + screenDisplayType + ".jsp" : "WEB-INF/error.jsp";
            int responseStatus = enabledFlag ? 200 : 404;

            request = getTestRequest();
            assertNotNull(errorMessage, request);

            response = getTestResponse();
            assertNotNull(errorMessage, response);

            helper = new ControllerHelper(request, response, factory);
            assertNotNull(errorMessage, helper);

            testViewScreenRequestSetup(request, screen.getName());
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

    public void testAddHelperToSession() throws Exception {

    }

    public void testCopyFromSession() throws Exception {

    }

    public void testToString() throws Exception {

    }

    private void testViewScreenRequestSetup(MockHttpServletRequest request, String screenName) {
        String queryString = "screen=" + screenName;
        request.setQueryString(queryString);
        request.setRequestURI("/view");
        request.setServletPath("/view");
        request.setMethod("get");
        request.setParameter("screen", screenName);
    }

    private MockHttpServletRequest getTestRequest() {
        MockHttpServletRequest request;

        request = new MockHttpServletRequest(context);
        assertNotNull(request);
        request.getSession().setAttribute("sessionData", new SessionData(null));
        RequestStatus requestStatus = new RequestStatus();
        request.getSession().setAttribute("requestStatus", requestStatus);

        return request;
    }

    private MockHttpServletResponse getTestResponse() {
        MockHttpServletResponse response;

        response = new MockHttpServletResponse();
        assertNotNull(response);

        return response;
    }
}