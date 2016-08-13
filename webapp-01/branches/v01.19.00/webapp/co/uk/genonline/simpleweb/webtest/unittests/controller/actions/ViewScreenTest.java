package unittests.controller.actions;

import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.controller.actions.screenactions.ViewScreen;
import co.uk.genonline.simpleweb.controller.screendata.MistressScreenData;
import co.uk.genonline.simpleweb.controller.screendata.displaybeans.ScreenMenuBean;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensManager;
import co.uk.genonline.simpleweb.model.bean.ScreensManagerNonCaching;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import unittests.support.ScreensUnitTestData;
import unittests.support.TestSupport;
import unittests.support.TestSupportSessionFactory;


/**
 * Created by thomassecondary on 16/05/2016.
 */

@RunWith(value=BlockJUnit4ClassRunner.class)
public class ViewScreenTest {
    static MockServletContext context;
    static SessionFactory factory;
    static Configuration configuration;
    static MockHttpServletRequest request;
    static MockHttpServletResponse response;
    static ScreensManager manager;

    @BeforeClass
    public static void beforeAll() {
        context = TestSupport.getNewServletContext();
        factory = TestSupportSessionFactory.getSessionFactory();
        configuration = new Configuration(factory);
        context.setAttribute("configuration", configuration);
        request = new MockHttpServletRequest(context);
        manager = new ScreensManagerNonCaching(factory);
        request.getSession().setAttribute("sessionData", new SessionData(null));
        RequestStatus requestStatus = new RequestStatus();
        request.getSession().setAttribute("requestStatus", requestStatus);
        response = new MockHttpServletResponse();

        TestSupport.clearDatabase(factory);
        ScreensUnitTestData.initialiseAndCheckTestData();
   }

    @AfterClass
    public static void afterAll() {
        TestSupport.clearDatabase(factory);
    }

    @Test
    public void testPerform1() {
        // Set up test record
        ScreensEntity testScreen = ScreensUnitTestData.getTestCase(1);

        // Set up request to represent request for test case
        TestSupport.viewScreenRequestSetup(request, testScreen.getName());
        ViewScreen viewScreen = new ViewScreen(request, response);
        RequestResult requestResult = viewScreen.perform();

        Assert.assertEquals(200, response.getStatus());

        Assert.assertFalse(requestResult.isRedirectFlag());

        Assert.assertNotNull(requestResult);
        Assert.assertEquals("WEB-INF/mistress-02.jsp", requestResult.getNextRequest());

        MistressScreenData screenData = (MistressScreenData) request.getAttribute("screenData");
        Assert.assertNotNull(screenData);

        ScreenMenuBean screenMenuBean = screenData.getScreenMenus();
        String mistressMenu = screenMenuBean.getMenu("mistressLinkBar");
        Assert.assertNotNull(mistressMenu);
    }

    @Test
    public void testPerform2() {
        // Set up test record
        ScreensEntity testScreen = ScreensUnitTestData.getTestCase(2);

        // Set up request to represent request for test case
        TestSupport.viewScreenRequestSetup(request, testScreen.getName());
        ViewScreen viewScreen = new ViewScreen(request, response);
        RequestResult requestResult = viewScreen.perform();

        Assert.assertEquals(404, response.getStatus());

        Assert.assertFalse(requestResult.isRedirectFlag());

        Assert.assertNotNull(requestResult);
        Assert.assertEquals("WEB-INF/error.jsp", requestResult.getNextRequest());
    }
}