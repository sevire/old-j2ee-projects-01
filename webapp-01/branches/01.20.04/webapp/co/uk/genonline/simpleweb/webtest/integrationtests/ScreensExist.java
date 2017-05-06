package integrationtests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import integrationtests.support.TestConfiguration;
import integrationtests.support.TestHelper;
import integrationtests.support.WebsiteTestData;
import support.TestSupportLogger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static org.junit.runners.Parameterized.Parameters;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/11/2012
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */

@RunWith(Parameterized.class)
public class ScreensExist {

    String screenName;
    TestHelper testHelper;
    static int requestCount = 0;

    public ScreensExist(TestConfiguration testConfiguration, String screenName) {
        this.screenName = screenName;
        testHelper = new TestHelper(testConfiguration);
        TestSupportLogger.initLogger();
    }

    @Test
    public void testPageExists() {
        testHelper.testPageExists(screenName);
    }

    @Parameters
    public static Collection<Object[]> data() {
        /*
         Elements of the array need to be in the right order as they correspond to arguments to the
         constructor
         */

        Collection<Object[]> data = new ArrayList<Object[]>();
        Set<String> screenList = WebsiteTestData.getInstance().getScreenList();

        for (TestConfiguration testConfiguration : TestConfiguration.enumValues()) {
            for (String screen : screenList) {
                Object parameterSet[] = new Object[]{testConfiguration, screen};
                data.add(parameterSet);
            }
        }
        return data;
    }

}
