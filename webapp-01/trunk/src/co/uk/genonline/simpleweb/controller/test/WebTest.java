package co.uk.genonline.simpleweb.controller.test;

import co.uk.genonline.simpleweb.controller.test.support.ConfigurationName;
import co.uk.genonline.simpleweb.controller.test.support.TestHelper;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 13/11/2012
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public abstract class WebTest {
    static TestHelper helper;

    static void testSetup(ConfigurationName cfg) {
        helper = new TestHelper(cfg);
    }
}
