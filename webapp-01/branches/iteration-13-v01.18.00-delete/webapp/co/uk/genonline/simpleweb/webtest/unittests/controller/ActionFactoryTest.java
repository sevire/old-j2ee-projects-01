package unittests.controller;

import co.uk.genonline.simpleweb.configuration.configitems.BlogEnabled;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.ControllerHelper;
import co.uk.genonline.simpleweb.controller.actions.Action;
import co.uk.genonline.simpleweb.controller.actions.ActionFactory;
import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.actions.screenactions.ViewScreen;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.web.gallery.GalleryManager;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import support.TestSupportLogger;
import unittests.support.TestSupportSessionFactory;

import javax.servlet.ServletContext;

import static unittests.support.GallerySetup.gallerySetup;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class ActionFactoryTest extends TestCase {

    // Mock Servlet objects
    static private ServletContext context;
    static private ControllerHelper helper;
    static private Configuration configurationManager;
    static private GalleryManager galleryManager;

    // Database access
    static private SessionFactory factory;

    private static final String contextBaseDir = "/Users/thomassecondary/Projects/lda-webapp/web";
    private static final String contextBaseDirPrefix = "file:";

    private MockHttpServletRequest createNewTestRequest(String command, String parameterName, String parameterValue) {
        /**
         * Create new MockHttpRequest set up to reflect the needs of the test.
         */
        MockHttpServletRequest request = new MockHttpServletRequest(context);
        request.getSession().setAttribute("sessionData", new SessionData(null));

        String queryString = parameterName + "=" + parameterValue;

        request.setQueryString(queryString);
        request.setRequestURI("/" + command);
        request.setServletPath("/" + command);
        request.setMethod("get");
        request.setParameter("screen", "courtney");

        return request;
    }

    /**
     * This method is for setting up objects and data which will be valid for all the tests carried out.  If some data
     * needs to be created from scratch for each test or initialised differently then it should be moved to the
     * test.
     */
    @BeforeClass
    public static void beforeAll() {
        // The logger is shared by everything so this is the right place for it.
        TestSupportLogger.initLogger();

        /**
         * The context represents the application environment - should be fine to set up once here.
         * It includes creation and adding (as attributes) other one off objects such as:
         * - SessionFactory
         * - Configuration
         * - GalleryManager
         */
        context = new MockServletContext("file:/Users/thomassecondary/Projects/lda-webapp/web", null);
        String realPath = context.getRealPath("/");
        assertEquals("/Users/thomassecondary/Projects/lda-webapp/web", realPath);

        factory = TestSupportSessionFactory.getSessionFactory();

        context.setAttribute("sessionFactory", factory);
        assertSame(factory, context.getAttribute("sessionFactory"));

        configurationManager = new Configuration(factory);
        // Test one configuration value to check this has worked.
        assertTrue(((BlogEnabled) (configurationManager.getConfigurationItem("blogEnabled"))).get());

        context.setAttribute("configuration", configurationManager);
        assertSame(configurationManager, context.getAttribute("configuration"));

        galleryManager = gallerySetup();
        context.setAttribute("Galleries",galleryManager);
        assertSame(galleryManager, context.getAttribute("Galleries"));
    }

    @Test
    public void testCreateAction() throws Exception {

        for (int i=0; i<100; i++) {
            // Start by testing ViewScreen as that is where most of the work is and probably where the
            // "Too many connections" error is coming from.
            MockHttpServletRequest request = createNewTestRequest("view", "screen", "courtney");
            MockHttpServletResponse response = new MockHttpServletResponse();

            // ToDo: Should have a default constructor which creates new ScreenEntity and ConfigurationEntity
            SessionData data = new SessionData(new ScreensEntity());

            Action action = ActionFactory.createAction(request, response);

            assertNotNull(action);
            assertTrue(action instanceof ViewScreen);

            action.perform();
        }
    }
}