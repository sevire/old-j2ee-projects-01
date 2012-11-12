package co.uk.genonline.simpleweb.controller.test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 01/11/2012
 * Time: 13:23
 * To change this template use File | Settings | File Templates.
 */
public class TestHelper {

    private static TestHelper ourInstance = new TestHelper();

    public static TestHelper getInstance() {
        return ourInstance;
    }

    private TestHelper() {
    }

    protected WebClient webClient = null;

    public WebClient getWebClient() {
        if (webClient == null) {
            webClient = new WebClient();
        }
        return webClient;
    }

    boolean isBody(String pageAsXML, String bodyId) {
        return pageAsXML.contains(String.format("<body id=\"%s\">", bodyId));
    }

    HtmlPage getRequest(TestConfiguration configuration, String requestString) {
        /*
         Get response for a general request within the website, depending upon configuration.
         */

        HtmlPage page = null;
        try {
            page = getWebClient().getPage(configuration.getHost() + requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    HtmlPage getScreen(TestConfiguration configuration, String screenName) {
        HtmlPage page = null;
        return getRequest(configuration, TestHelper.getInstance().getScreenRequestString(configuration.getPlatform(), screenName));
    }

    void testWebsitePage(HtmlPage page, String contentStrings[]) {
        // Requests a page using the supplied request string and then checks the returned page for
        // the strings supplied.

        for (int i=0; i<contentStrings.length; i++) {
            assertTrue(String.format("Page does not contain text <%s>", contentStrings[i]), page.asText().contains(contentStrings[i]));
        }
    }

    void testWebsiteScreen(TestConfiguration configuration, String screenName, String contentStrings[]) {
        // Tests that page exists and that the content is as expected.

        String host = configuration.getHost();

        String longTitle = WebsiteData.getInstance().getPageTitle(configuration.getPlatform(), screenName);

        HtmlPage page = getScreen(configuration, screenName);

        assertNotNull(String.format("Screen <%s> does not exist at <%s>", screenName, host), page);
        assertEquals(String.format("Title incorrect for <%s> at <%s>", screenName, host), String.format("Lucifer's Dark Angel - %s", longTitle), page.getTitleText());

        testWebsitePage(page, contentStrings);
    }

    String getScreenRequestString(WebsitePlatform platform, String screenName) {
        // Works out URL to use to get a screen depending upon which platform of the website is being tested

        String request;
        switch (platform) {
            case J2EE:
            default :
                request = String.format("view?screen=%s", screenName);
            break;
            case HTML :
                request = WebsiteData.getInstance().getPageName(WebsitePlatform.HTML, screenName) + ".shtml";
            break;
        }
        return request;
    }

    public void pageContentTest(TestConfiguration configuration) throws Exception {
        TestHelper helper = TestHelper.getInstance();

        Set<String> screens = WebsiteData.getInstance().getScreenList();

        for (String screen : screens) {
            helper.testWebsiteScreen(configuration, screen, WebsiteData.getInstance().getContentStrings(screen));
        }
    }

    public void splashPageTest(TestConfiguration configuration) throws Exception {
        TestHelper helper = TestHelper.getInstance();

        WebClient webClient = new WebClient();
        String splashURL = configuration.getSplashURL();

        HtmlPage page = webClient.getPage(splashURL);
        assertNotNull(String.format("No splash page received at <%s>", splashURL), page);
        assertEquals(String.format("Title incorrect for splash page at ,<%s>", splashURL),
                "Lucifer's Dark Angel - Princess Lucina of Manchester", page.getTitleText());

        String contentStrings[] = WebsiteData.getInstance().getContentStrings("splash");
        for (int i=0; i<contentStrings.length; i++) {
            assertTrue(String.format("Page does not contain text <%s>", contentStrings[i]), page.asText().contains(contentStrings[i]));
        }
    }

    public void editIndexTest(TestConfiguration configuration) throws Exception {
        WebClient webClient = new WebClient();
        TestHelper helper = TestHelper.getInstance();
        String host = configuration.getHost();

        HtmlPage page = webClient.getPage(host + "editIndex");
        assertEquals("Lucifer's Dark Angel - Edit Index", page.getTitleText());

        String pageAsXml = page.asXml();
        assertTrue(helper.isBody(pageAsXml, "editIndex"));

        DomNodeList<HtmlElement> table = page.getElementsByTagName("table");

        assertEquals("Too many table elements", 1, table.size());

        List<HtmlElement> rows = ((HtmlElement) table.item(0)).getHtmlElementsByTagName("tr");

        // Hard code expected number until test data is complete
        assertEquals("Wrong number of screens", 16, rows.size()-3);

        webClient.closeAllWindows();
    }

    public void linkTest(TestConfiguration configuration) {
        // Checks that the header links are correct and the same on all pages

            // Get two copies of the screen list, one for outer loop and one for inner
            Set<String> outerScreens = WebsiteData.getInstance().getScreenList();
            Set<String> innerScreens = WebsiteData.getInstance().getScreenList();

            for (String outerScreen : outerScreens) {
                HtmlPage page = getScreen(configuration, outerScreen);
                HtmlAnchor element;
                String linkText = null;
                for (String innerScreen : innerScreens) {
                    if (configuration.getPlatform() == WebsitePlatform.HTML) {
                        linkText = WebsiteData.getInstance().getLinkName(configuration.getPlatform(), innerScreen);
                    } else {
                        linkText = WebsiteData.getInstance().getLinkName(configuration.getPlatform(), innerScreen);
                    }
                    try {
                        element = page.getAnchorByText(linkText);
                    } catch (Exception e) {
                        element = null;
                    }
                    Assert.assertNotNull(String.format("Link for <%s> does not exist in <%s>",
                            innerScreen, outerScreen), element);
                }
            }
        }

    public void galleryTest(TestConfiguration configuration) {
        Set<String> screens = WebsiteData.getInstance().getScreenList();

        for (String screen : screens) {
            HtmlPage page = getScreen(configuration, screen);

            // Get all table elements in page - should be one if gallery and none otherwise
            DomNodeList<HtmlElement> galleryTable = page.getElementsByTagName("table");
            int numGalleryElements = galleryTable.size();

            assertFalse(String.format("Too many table elements in page <%s>", screen), galleryTable.size() > 1);
            assertFalse(String.format("Gallery present in none gallery page <%s>", screen),
                    !WebsiteData.getInstance().isGallery(screen) && numGalleryElements == 1) ;
            assertFalse(String.format("No Gallery present when there should be one"),
                    WebsiteData.getInstance().isGallery(screen) && numGalleryElements != 1);

            if (WebsiteData.getInstance().isGallery(screen)) {
                HtmlElement galleryElement = (HtmlElement) galleryTable.item(0);
                List<HtmlElement> imageElements = galleryElement.getHtmlElementsByTagName("img");
                assertEquals(String.format("Wrong number of images in page <%s>", screen),
                        WebsiteData.getInstance().numGalleryImages(screen), imageElements.size());
            }
        }
    }

    void checkNumScreens(TestConfiguration configuration, int numScreens) {

    }

    void updatePage(TestConfiguration configuration, String screenName) {
        String screenTestString[] = {"Testing xxx"};
        String editIndexTestString[] = {"Lucifer's Dark Angel - Website Maintenance Screen"};

        /* Carry out the following sequence:
        - Request to edit a screen
        - Edit text area from screen
        - Click update button to update screen
        - Check that response to update is go back to editIndex page
        - Check that updated page has new text in it.
        - Request to edit page again.
        - Update text area back to original text.
        - Check that page is now correct.
         */

        // Do this is a loop - first time to add text and test, second to remove and test.

        HtmlPage page;
        HtmlPage responsePage;
        HtmlForm form;
        HtmlTextArea textArea;
        HtmlSubmitInput button;
        String text = null;
        String updateText;

        for (int i=0; i<2; i++) {

            // First extract main text from within edit page
            page = getRequest(configuration, String.format("edit?screen=%s", screenName));
            form = page.getFormByName("editScreen");
            textArea = form.getTextAreaByName("screenContents");

            if (i == 0) {
                text = textArea.getText();
                updateText = screenTestString[0] + text;
                textArea.setText(updateText);
            } else {
                textArea.setText(text);
            }

            // Then modify text and update page
            button = form.getInputByName("updateButton");
            try {
                responsePage = button.click();
            } catch (IOException e) {
                e.printStackTrace();
                responsePage = null;
            }
            if (responsePage != null) {
                // Check that response is editIndex page
                testWebsitePage(responsePage, editIndexTestString);

                // Check that screen now contains updated text or is back to original screen
                if (i == 0) {
                    testWebsiteScreen(configuration, screenName, screenTestString);
                } else {
                    testWebsiteScreen(configuration, screenName, WebsiteData.getInstance().getContentStrings(screenName));
                }
            }

        }

    }

    void pageEditTest(TestConfiguration configuration) {
        updatePage(configuration, "lucina");
    }
}
