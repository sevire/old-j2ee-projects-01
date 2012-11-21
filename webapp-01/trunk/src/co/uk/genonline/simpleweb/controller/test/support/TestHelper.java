package co.uk.genonline.simpleweb.controller.test.support;

import co.uk.genonline.simpleweb.controller.test.TestHtmlPage;
import co.uk.genonline.simpleweb.controller.test.WebsitePlatform;
import co.uk.genonline.simpleweb.controller.test.WebsiteTestData;
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

    private TestConfiguration configuration = null;
    private WebClient webClient = null;

    public TestHelper(ConfigurationName configurationName) {
        this.configuration = new TestConfiguration(configurationName);
        webClient = new WebClient();
        webClient.setThrowExceptionOnFailingStatusCode(false);
    }

    HtmlPage getRequest(String requestString) {
        /*
         Get response for a general request within the website, depending upon configuration.
         */

        HtmlPage page = null;
        try {
            page = webClient.getPage(configuration.getHost() + requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    HtmlPage getScreen(String screenName) {
        return getRequest(getScreenRequestString(configuration.getPlatform(), screenName));
    }

    void testWebsitePage(HtmlPage page, String contentStrings[]) {
        // Requests a page using the supplied request string and then checks the returned page for
        // the strings supplied.

        for (int i=0; i<contentStrings.length; i++) {
            assertTrue(String.format("Page does not contain text <%s>", contentStrings[i]), page.asText().contains(contentStrings[i]));
        }
    }

    void testWebsiteScreen(String screenName, String contentStrings[]) {
        // Tests that page exists and that the content is as expected or if indicated
        // to be deleted in the testing configuration, that it is not present.

        String host = configuration.getHost();
        String longTitle = WebsiteTestData.getInstance().getPageTitle(configuration.getPlatform(), screenName);

        HtmlPage page = getScreen(screenName);

        if (WebsiteTestData.getInstance().isConfigured(screenName)) {
            assertNotNull(String.format("Screen <%s> does not exist at <%s>", screenName, host), page);
            assertEquals(String.format("Page not found <%s> for screen <%s> at <%s>", WebsiteTestData.getInstance().getTestDataHtmlFilename(screenName), screenName, host),
                    200, page.getWebResponse().getStatusCode());
            assertEquals(String.format("Title incorrect for <%s> at <%s>", screenName, host), String.format("Lucifer's Dark Angel - %s", longTitle), page.getTitleText());
            testWebsitePage(page, contentStrings);
        } else {
            assertEquals(String.format("Page not found for screen <%s> at <%s>", screenName, host),
                    404, page.getWebResponse().getStatusCode());
        }
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
                request = WebsiteTestData.getInstance().getPageName(WebsitePlatform.HTML, screenName) + ".shtml";
            break;
        }
        return request;
    }

    public void pageContentTest() {
        Set<String> screens = WebsiteTestData.getInstance().getScreenList();

        for (String screen : screens) {
            testWebsiteScreen(screen, WebsiteTestData.getInstance().getContentStrings(screen));
        }
    }

    public void splashPageTest() throws Exception {
        String splashURL = configuration.getSplashURL();

        HtmlPage page = webClient.getPage(splashURL);
        assertNotNull(String.format("No splash page received at <%s>", splashURL), page);
        assertEquals(String.format("Title incorrect for splash page at ,<%s>", splashURL),
                "Lucifer's Dark Angel - Princess Lucina of Manchester", page.getTitleText());

        String contentStrings[] = WebsiteTestData.getInstance().getContentStrings("splash");
        for (String contentString : contentStrings) {
            assertTrue(String.format("Page does not contain text <%s>", contentString), page.asText().contains(contentString));
        }
    }

    public void editIndexTest() throws Exception {
        WebClient webClient = new WebClient();
        String host = configuration.getHost();

        TestHtmlPage page = new TestHtmlPage((HtmlPage)webClient.getPage(host + "editIndex"));
        assertEquals("Lucifer's Dark Angel - Edit Index", page.getPage().getTitleText());

        assertEquals(String.format("Body id not correct"), ((TestHtmlPage)page).getBodyId(), "editIndex");

        DomNodeList<HtmlElement> table = page.getPage().getElementsByTagName("table");
        assertEquals("Too many table elements", 1, table.size());

        List<HtmlElement> rows = ((HtmlElement) table.item(0)).getHtmlElementsByTagName("tr");
        int disabledCount = 0;
        for (HtmlElement element : rows) {
            DomNodeList<HtmlElement> tdElements = element.getElementsByTagName("td");
            // if number of TDs is not correct then not a data row so skip
            if (tdElements.size() == 10) {
                HtmlElement enabledNode = tdElements.get(5);
                String enabledText = enabledNode.getTextContent().trim();
                if (enabledText.equals("No")) {
                    disabledCount++;
                }
            }
        }

        // Hard code expected number until testing data is complete
        assertEquals("Wrong number of screens", 15, rows.size()-3-disabledCount);

        webClient.closeAllWindows();
    }

    public void linkTestScreen(String screenName) {
        if (WebsiteTestData.getInstance().isConfigured(screenName)) {
            Set<String> innerScreens = WebsiteTestData.getInstance().getScreenList();
            HtmlPage page = getScreen(screenName);
            HtmlAnchor element;
            String linkText;
            for (String innerScreen : innerScreens) {
                if (WebsiteTestData.getInstance().isConfigured(innerScreen)) {
                    if (configuration.getPlatform() == WebsitePlatform.HTML) {
                        linkText = WebsiteTestData.getInstance().getLinkName(configuration.getPlatform(), innerScreen);
                    } else {
                        linkText = WebsiteTestData.getInstance().getLinkName(configuration.getPlatform(), innerScreen);
                    }
                    try {
                        element = page.getAnchorByText(linkText);
                    } catch (Exception e) {
                        element = null;
                    }
                    Assert.assertNotNull(String.format("Link for <%s> does not exist in <%s>",
                            linkText, screenName), element);
                }
            }
        }
    }

    public void linkTest() {
        Set<String> outerScreens = WebsiteTestData.getInstance().getScreenList();

        for (String outerScreen : outerScreens) {
            if (WebsiteTestData.getInstance().isConfigured(outerScreen)) {
                linkTestScreen(outerScreen);
            }
        }
    }

    public void galleryTestScreen(String screenName) {
        HtmlPage page = getScreen(screenName);

        // Get all table elements in page - should be one if gallery and none otherwise
        DomNodeList<HtmlElement> galleryTable = page.getElementsByTagName("table");
        int numGalleryElements = galleryTable.size();

        assertFalse(String.format("Too many table elements in page <%s>", screenName), galleryTable.size() > 1);
        assertFalse(String.format("Gallery present in none gallery page <%s>", screenName),
                !WebsiteTestData.getInstance().isGallery(screenName) && numGalleryElements == 1) ;
        assertFalse(String.format("No Gallery present when there should be one for <%s>", screenName),
                WebsiteTestData.getInstance().isGallery(screenName) && numGalleryElements != 1);

        if (WebsiteTestData.getInstance().isGallery(screenName)) {
            HtmlElement galleryElement = (HtmlElement) galleryTable.item(0);
            List<HtmlElement> imageElements = galleryElement.getHtmlElementsByTagName("img");
            assertEquals(String.format("Wrong number of images in page <%s>", screenName),
                    WebsiteTestData.getInstance().getNumGalleryImages(screenName), imageElements.size());
        }
    }

    public void galleryTest() {
        Set<String> screens = WebsiteTestData.getInstance().getScreenList();

        for (String screen : screens) {
            galleryTestScreen(screen);
        }
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

        // Do this is a loop - first time to add text and testing, second to remove and testing.

        HtmlPage page;
        HtmlPage responsePage;
        HtmlForm form;
        HtmlTextArea textArea;
        HtmlSubmitInput button;
        String text = null;
        String updateText;

        for (int i=0; i<2; i++) {

            // First extract main text from within edit page
            page = getRequest(String.format("edit?screen=%s", screenName));
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
                    testWebsiteScreen(screenName, screenTestString);
                } else {
                    testWebsiteScreen(screenName, WebsiteTestData.getInstance().getContentStrings(screenName));
                }
            }

        }

    }

    void pageEditTest() {
        updatePage(configuration, "lucina");
    }

    public TestConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(TestConfiguration configuration) {
        this.configuration = configuration;
    }
}
