package unittests.support;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.mock.web.MockServletContext;
import support.TestSupportLogger;

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
        assertEquals("/Users/thomassecondary/Projects/webapp-01(trunk)/web", realPath);

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

        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from ScreensEntity").executeUpdate();
        session.getTransaction().commit();
    }
}
