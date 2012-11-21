package co.uk.genonline.simpleweb.controller.test.support;

import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 20/11/2012
 * Time: 07:36
 * To change this template use File | Settings | File Templates.
 */
public class OtherRequestTests {
    ConfigurationName configurationName;
    TestHelper testHelper;

    public OtherRequestTests(ConfigurationName configurationName) {
        this.configurationName = configurationName;
        testHelper = new TestHelper(configurationName);
    }

    @Test
    public void splashTest() throws Exception {
        testHelper.splashPageTest();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        /*
         Elements of the array need to be in the right order as they correspond to arguments to the
         constructor
         */

        Collection<Object[]> data = new ArrayList<Object[]>();

        for (ConfigurationName configurationName : ConfigurationName.values()) {
            Object parameterSet[] = new Object[] {configurationName};
            data.add(parameterSet);
        }
        return data;
    }
}
