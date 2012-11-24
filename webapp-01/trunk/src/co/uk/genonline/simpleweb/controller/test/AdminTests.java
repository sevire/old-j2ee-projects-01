package co.uk.genonline.simpleweb.controller.test;

import co.uk.genonline.simpleweb.controller.test.support.ConfigurationName;
import co.uk.genonline.simpleweb.controller.test.support.TestHelper;
import co.uk.genonline.simpleweb.controller.test.support.WebsitePlatform;
import co.uk.genonline.simpleweb.controller.test.support.WebsiteTestData;
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
    ConfigurationName configurationName;
    TestHelper testHelper;

    public AdminTests(ConfigurationName configurationName) {
        this.configurationName = configurationName;
        testHelper = new TestHelper(configurationName);
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

        for (ConfigurationName configurationName : ConfigurationName.values()) {
            Object parameterSet[] = new Object[] {configurationName};
            data.add(parameterSet);
        }
        return data;
    }

}
