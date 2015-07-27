package unittests.controller;

import co.uk.genonline.simpleweb.configuration.configitems.BlogEnabled;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.ControllerHelper;
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
public class ControllerHelperTest extends TestCase {
    static private MockHttpServletRequest request;
    static private MockHttpServletResponse response;
    static private ServletContext context;
    static private SessionFactory factory;
    static private ControllerHelper helper;
    static private Configuration configurationManager;
    static private GalleryManager galleryManager;

    private static final String contextBaseDir = "/Users/thomassecondary/Projects/webapp-01(trunk)/web";
    private static final String contextBaseDirPrefix = "file:";

     @BeforeClass
    public static void beforeAll() {
        TestSupportLogger.initLogger();

        context = new MockServletContext(contextBaseDirPrefix + contextBaseDir, null);
        assertNotNull(context);

        String realPath = context.getRealPath("/");
        assertEquals("/Users/thomassecondary/Projects/webapp-01(trunk)/web", realPath);

        factory = new TestSupportSessionFactory().getSessionFactory();
        context.setAttribute("sessionFactory", factory);
        assertSame(factory, context.getAttribute("sessionFactory"));

        configurationManager = new Configuration(factory);
        assertTrue(((BlogEnabled) (configurationManager.getConfigurationItem("blogEnabled"))).get());

        context.setAttribute("configuration", configurationManager);
        assertSame(configurationManager, context.getAttribute("configuration"));

        galleryManager = gallerySetup();
        context.setAttribute("Galleries",galleryManager);
        assertSame(galleryManager, context.getAttribute("Galleries"));

        request = new MockHttpServletRequest(context);
        assertNotNull(request);

        ServletContext checkContext;

        request.getServletContext();
        //assertSame(context, checkContext);

        response = new MockHttpServletResponse();
        assertNotNull(response);

        helper = new ControllerHelper(request, response, factory);
        assertNotNull(helper);
    }


    public void testGetScreen() throws Exception {
    }

    public void testGetConfigItems() throws Exception {

    }

    @Test
    public void testProcessRequest() throws Exception {

        request.setQueryString("screen=courtney");
        request.setRequestURI("/view");
        request.setServletPath("/view");
        request.setMethod("get");
        request.setParameter("screen", "courtney");
        helper.processRequest();

        int status = response.getStatus();
        assertEquals(200, status);

        String forwardUrl = response.getForwardedUrl();
        assertEquals("WEB-INF/mistress-03.jsp", forwardUrl);
    }

    public void testAddHelperToSession() throws Exception {

    }

    public void testCopyFromSession() throws Exception {

    }

    public void testToString() throws Exception {

    }
}