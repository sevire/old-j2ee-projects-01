package co.uk.genonline.simpleweb.controller.test.old;

import co.uk.genonline.simpleweb.controller.test.support.ConfigurationName;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 01/11/2012
 * Time: 08:22
 * To change this template use File | Settings | File Templates.
 */
public class HTTPHTMLTest_1 extends WebTest {

    @BeforeClass
    public static void setup() {
        testSetup(ConfigurationName.STATIC_HTML_REMOTE_TEST_1);
    }

    @Test
    public void splashTest() throws Exception {
        helper.splashPageTest();
    }

    @Test
    public void pageContentTest() throws Exception {
        helper.pageContentTest();
    }

    @Test
    public void linkTest() {
        helper.linkTest();
    }

    @Test
    public void galleryTest() {
        // Checks that pages do or don't have a gallery
        helper.galleryTest();
    }


}
