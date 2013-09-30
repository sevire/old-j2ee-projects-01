import support.TestConfiguration;
import support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@RunWith(Parameterized.class)
public class OtherRequestTests {
    TestHelper testHelper;

    public OtherRequestTests(TestConfiguration testConfiguration) {
        testHelper = new TestHelper(testConfiguration);
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

        for (TestConfiguration testConfiguration : TestConfiguration.enumValues()) {
            Object parameterSet[] = new Object[] {testConfiguration};
            data.add(parameterSet);
        }
        return data;
    }
}
