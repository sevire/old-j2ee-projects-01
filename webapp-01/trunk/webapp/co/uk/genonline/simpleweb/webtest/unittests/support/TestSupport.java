package unittests.support;

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
}
