import support.TestConfiguration;
import support.TestHelper;
import support.WebsitePlatform;
import support.WebsiteTestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 20/11/2012
 * Time: 07:42
 * To change this template use File | Settings | File Templates.
 */

@RunWith(Parameterized.class)
public class AdminTests {
    TestConfiguration testConfiguration;
    TestHelper testHelper;

    public AdminTests(TestConfiguration testConfiguration) {
        this.testConfiguration = testConfiguration;
        testHelper = new TestHelper(testConfiguration);
    }

    @Test
    public void editIndexTest() throws Exception {
        if (testHelper.getConfiguration().getPlatform() == WebsitePlatform.J2EE) {
            testHelper.editIndexTest();
        }
    }

    @Test
    public void updateTest() {
        if (testHelper.getConfiguration().getPlatform() == WebsitePlatform.J2EE) {
            testHelper.updatePage(WebsiteTestData.getInstance().getUpdatePageScreen());
        }
    }

    public void pageEnabledTest() {
        if (testHelper.getConfiguration().getPlatform() == WebsitePlatform.J2EE) {
            // Disable a page and then re-enable; check correct response each time.


        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<Object[]>();

        for (TestConfiguration configurationName : TestConfiguration.enumValues()) {
            Object parameterSet[] = new Object[] {configurationName};
            data.add(parameterSet);
        }
        return data;
    }

}
