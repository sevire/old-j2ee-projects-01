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
public class HTTPJ2EETest_2 extends WebTest {

    @BeforeClass
    public static void setup() {
        testSetup(ConfigurationName.J2EE_REMOTE_TEST_1);
    }

    @Test
    public void editIndexTest() throws Exception {
        helper.editIndexTest();
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