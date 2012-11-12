package co.uk.genonline.simpleweb.controller.test;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 01/11/2012
 * Time: 08:22
 * To change this template use File | Settings | File Templates.
 */
public class HTTPHTMLTest_1 {
    private static TestConfiguration configuration;

    @BeforeClass
    public static void setup() {
        configuration = new TestConfiguration(ConfigurationName.STATIC_HTML_REMOTE_TEST_1);
    }

    @Test
    public void splashTest() throws Exception {
        TestHelper.getInstance().splashPageTest(configuration);
    }

    @Test
    public void pageContentTest() throws Exception {
        TestHelper.getInstance().pageContentTest(configuration);
    }

    @Test
    public void linkTest() {
        TestHelper.getInstance().linkTest(configuration);
    }

    @Test
    public void galleryTest() {
        // Checks that pages do or don't have a gallery
        TestHelper.getInstance().galleryTest(configuration);
    }


}
