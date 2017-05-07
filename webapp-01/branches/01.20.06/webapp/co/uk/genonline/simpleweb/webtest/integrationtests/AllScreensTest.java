package integrationtests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import integrationtests.support.TestConfiguration;
import integrationtests.support.TestHelper;
import integrationtests.support.WebsiteTestData;

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
public class AllScreensTest {

    String screenName;
    TestHelper testHelper;

    public AllScreensTest(TestConfiguration testConfiguration, String screenName) {
        this.screenName = screenName;
        testHelper = new TestHelper(testConfiguration);
    }

    @Test
    public void testPageContent() {
        testHelper.testWebsiteScreen(screenName, WebsiteTestData.getInstance().getContentStrings(screenName));
    }

    @Test
    public void testPageLinks() {
        testHelper.linkTestScreen(screenName);
    }

    @Test
    public void testPageGallery() {
        testHelper.galleryTestScreen(screenName);
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
