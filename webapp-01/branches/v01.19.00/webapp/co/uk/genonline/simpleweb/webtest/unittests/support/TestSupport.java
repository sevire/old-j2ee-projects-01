package unittests.support;

import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import support.TestSupportLogger;

import javax.servlet.ServletContext;

import static org.junit.Assert.*;
import static unittests.support.TestSupportSessionFactory.factory;

/**
 * Created by thomassecondary on 16/05/2016.
 */
public class TestSupport {
    public static MockServletContext getNewServletContext() {

        TestSupportLogger.initLogger();

        MockServletContext context = new MockServletContext(TestSupportConstants.contextBaseDirPrefix + TestSupportConstants.contextBaseDir, null);
        assertNotNull(context);

        String realPath = context.getRealPath("/");
        assertEquals(TestSupportConstants.contextBaseDir, realPath);

        factory = new TestSupportSessionFactory().getSessionFactory();
        context.setAttribute("sessionFactory", factory);
        assertSame(factory, context.getAttribute("sessionFactory"));


        return context;
    }

    /**
     * Deletes all records from database before starting and after finishing. Ensures that tests can run after a failure without
     * manual intervention.  Encapsulate in method to allow local instance of manager so that for main test
     * a clean instance of manager is used.  Not sure this is strictly necessary or right but so far that is what I have
     * decided to do.
     */
    public static void clearDatabase(SessionFactory factory) {
        System.out.println("Cleaning up...\n");
        checkDatabaseName();

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from ScreensEntity").executeUpdate();
        session.getTransaction().commit();
    }

    public static void checkDatabaseName() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String dbName = (String)session.createSQLQuery("SELECT database()").uniqueResult();
        session.getTransaction().commit();

        assertEquals("lda_v02_unit", dbName);
    }

    public static void viewScreenRequestSetup(MockHttpServletRequest request, String screenName) {
        String queryString = "screen=" + screenName;
        request.setQueryString(queryString);
        request.setRequestURI("/view");
        request.setServletPath("/view");
        request.setMethod("get");
        request.setParameter("screen", screenName);
    }

    public static void addScreenRequestSetup(MockHttpServletRequest request, ScreensEntity screen) {
        String requestURI = "/Controller.do";
        request.setRequestURI(requestURI);
        request.setServletPath(requestURI);
        request.setMethod("post");

        // Used to indicate which action to process. Not sure what value it should be, just needs to be not null
        request.setParameter("addButton", "");

        requestSetupFromScreen(request, screen);
    }

    /**
     * Used to populate a Request object as though it came from an add or update screen form.  Set attributes in
     * request according to what the value is in ScreensEntity object.
     *
     * May want to move this to a live Class (ScreensEntityDecorator maybe?)
     *
     * ToDo: Decide whether to move requestSetupFromScreen method into a live Class
     */
    public static void requestSetupFromScreen(MockHttpServletRequest request, ScreensEntity screen) {
        request.setParameter("name", screen.getName()==null ? "" : screen.getName());
        request.setParameter("sortKey", screen.getSortKey().toString()==null ? "" : screen.getSortKey().toString());
        request.setParameter("screenTitleShort", screen.getScreenTitleShort()==null ? "" : screen.getScreenTitleShort());
        request.setParameter("screenTitleLong", screen.getScreenTitleLong()==null ? "" : screen.getScreenTitleLong());
        request.setParameter("screenType", screen.getScreenType()==null ? "" : screen.getScreenType());
        request.setParameter("screenDisplayType", screen.getScreenDisplayType()==null ? "" : screen.getScreenDisplayType());
        request.setParameter("screenContents", screen.getScreenContents()==null ? "" : screen.getScreenContents());
        request.setParameter("metaDescription", screen.getMetaDescription()==null ? "" : screen.getMetaDescription());

        request.setParameter("enabledFlag", (screen.getEnabledFlag() != null && screen.getEnabledFlag()) ? "true" : "");
        request.setParameter("galleryFlag", (screen.getGalleryFlag() != null && screen.getGalleryFlag()) ? "true" : "");
    }

    public static MockHttpServletRequest getTestRequest(ServletContext context) {
        MockHttpServletRequest request;

        request = new MockHttpServletRequest(context);
        assertNotNull(request);
        request.getSession().setAttribute("sessionData", new SessionData(null));
        RequestStatus requestStatus = new RequestStatus();
        request.getSession().setAttribute("requestStatus", requestStatus);

        return request;
    }

    public static MockHttpServletResponse getTestResponse() {
        MockHttpServletResponse response;

        response = new MockHttpServletResponse();
        assertNotNull(response);

        return response;
    }

}
