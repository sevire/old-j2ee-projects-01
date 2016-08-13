package integrationtests.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 04/11/2012
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class WebsiteTestData {
    private static final String TEST_DATA_UPDATE_PAGE_SCREEN = "lucina";
    private static final String TEST_DATA_ENABLE_PAGE_SCREEN = "lucina";

    public static String getUpdatePageScreen() {
        return TEST_DATA_UPDATE_PAGE_SCREEN;
    }

    public static String getEnablePageScreen() {
        return TEST_DATA_ENABLE_PAGE_SCREEN;
    }

    private static WebsiteTestData ourInstance = new WebsiteTestData();

        // Set up array with key data for each field relevant to requesting a page
    // The first column in the array is the html file used in the static version of the site

    protected static Map<String, String[][]> websiteData = new HashMap<String, String[][]>() {
        {
            put("sessions", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("reginascarlet", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("helena", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("wishlist", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("contact", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("aboutme", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("philosophy", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("cornelia", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("mistresses", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("musing-01", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("musing-02", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("musing-03", new String[][]{{
                    "",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Dummy content",
                    }});
            put("splash", new String[][]{{
                    "index",
                    "",
                    "",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "This website contains content of an adult nature",
                    }});
            put("lucina", new String[][]{{
                    "", // 0 - (Optional) Name of HTML file for static version of site
                    "", // 1 - (Optional) Text used in header link for static version of site
                    "Princess Lucina", // 2 - (Mandatory) Short name, used in link text for J2EE version of site and as start point to generate other strings
                    "", // 3 - (Optional) Long name, used in <title> element in HTML version of site
                    "", // 4 - (Optional) Long name, J2EE version
                    "-1",   // Number of images in gallery - "-1" means No Gallery.
                    "", // Set to 'No Page' if checking that page doesn't exist (useful to check that a delete has been propagated)
                    "", // Name of first gallery image on page
                    ""}, // Name of last gallery image on page
                {
                        "I have that broken glass feeling",
                        "please email me with a photo"
                }});
            put("wishlist", new String[][]{{
                    "",
                    "",
                    "Wish List",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Make Me smile and become one of My favoured slaves by surprising Me with a gift",
                            "My favourite perfumes are anything by Dior Poison, or John Paul Gautier. I adore cosmetics by Christian Dior and Yves" +
                                    " St Laurent. But I will allow you the privilege of choosing, if you feel capable."
                    }});
            put("images", new String[][]{{
                    "",
                    "",
                    "Images",
                    "",
                    "",
                    "55",
                    "",
                    "",
                    ""},
                    {
                    }});
            put("hos", new String[][]{{
                    "",
                    "",
                    "Hall Of Shame",
                    "",
                    "",
                    "12",
                    "",
                    "",
                    ""},
                    {
                            "This is where I publically humiliate my naughtiest slaves...",
                    }});
            put("contact", new String[][]{{
                    "",
                    "",
                    "Contact",
                    "How To Contact Princess Lucina",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "Firstly, some notes on slave etiquette",
                            "You can find a map of my location in central Manchester here."
                    }});
            put("links", new String[][]{{
                    "",
                    "",
                    "Links",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                            "To exchange links, please email your banner here.",
                    }});
            put("moreimages", new String[][]{{
                    "",
                    "",
                    "More Images",
                    "",
                    "",
                    "63",
                    "",
                    "",
                    ""},
                    {
                    }});
            put("reginascarlet", new String[][]{{
                    "",
                    "",
                    "Regina Scarlet",
                    "Mistress Regina Scarlet",
                    "",
                    "12",
                    "",
                    "",
                    ""},
                    {
                            "I am beautiful and elegant, with porcelain skin and long red hair.",
                            "Remember: be punctual, respectful and clean."
                    }});
        }
    };

    public static WebsiteTestData getInstance() {
        return ourInstance;
    }

    private WebsiteTestData() {
    }



    public String getTestDataHtmlFilename(String screenName) {
        /*
        For HTML versions of the site, the file name for an HTML page is generated from the
        short name by putting underscores where the spaces are.
         */

        String shortName = getTestDataScreenShortName(screenName);
        return shortName.replace(" ", "_");
    }

    String getTestDataScreenShortName(String screenName) {
    return getPageItem(screenName, TestDataIndex.SCREEN_SHORT_NAME.getIndex());
}

    String getTestDataScreenLongName(String screenName) {

    String data = getPageItem(screenName, TestDataIndex.SCREEN_LONG_NAME.getIndex());
    if (data.equals("")) {
        return getTestDataScreenShortName(screenName);
    } else {
        return data;
    }
}
    
    public int getNumGalleryImages(String screenName) {
        return Integer.parseInt(getPageItem(screenName, TestDataIndex.NUM_GALLERY_IMAGES.getIndex()));
    }

    public boolean isConfigured(String screenName) {
        String indicator = getPageItem(screenName, TestDataIndex.NO_PAGE_FLAG.getIndex());
        if (indicator == null) {
            return false;
        } else if (indicator.equals("No Page")) {
            return false;
        } else {
            return true;
        }
    }

    public String getNameFirstGalleryImage(String screenName) {
        return getPageItem(screenName, TestDataIndex.GALLERY_IMAGE_FIRST_IMAGE_NAME.getIndex());
    }

    public String getNameLastGalleryImage(String screenName) {
        return getPageItem(screenName, TestDataIndex.GALLERY_IMAGE_LAST_IMAGE_NAME.getIndex());
    }

    public String getPageItem(String screenName, int itemNumber) {
        String data[][] = websiteData.get(screenName);

        if (data == null) {
            return null;
        } else {
            return data[0][itemNumber];
        }
    }

    public String getPageName(WebsitePlatform platform, String screenName) {
        /*
         Returns string to use in request for a page depending upon whether this is a
         J2EE page or an HTML page.  If it's J2EE then the screenName itself is used.
         If it's an HTML page but the string is blank then it will be generated
         automatically using the string for a link (which must be present in the data).
         */

        // NOTE: Remove HTML option as all website version are Java based.

        return screenName; // J2EE version uses screenName to request page
    }

    public String getLinkName(WebsitePlatform platform, String screenName) {
        /*
        Returns the string used within the link in the header of the page.  This will be the short name
        but for HTML versions will be capitalised.
         */

        String data = getTestDataScreenShortName(screenName);
        return data;
    }

    public String getPageTitle(WebsitePlatform platform, String screenName) {
        String pageTitle;
        return getTestDataScreenLongName(screenName);
    }

    public String[] getContentStrings(String screenName) {
        return websiteData.get(screenName)[1];
    }

    public Set<String> getScreenList() {
        Set<String> keySet = new TreeSet<String>(websiteData.keySet());
        keySet.remove("splash");
        return keySet;
    }

    public boolean isGallery(String screenName) {
        return getNumGalleryImages(screenName) != -1;
    }

    String generateHTMLLinkName(String linkName) {
        // Takes link name entry and calculates a link name in the format of the current live HTML site
        // which has all upper case letters.

        return linkName.toUpperCase();
    }

    String generateHTMLFileName(String linkName) {
        // Converts link name into the name of an HTML file based on current live HTML site conventions

        return linkName.replace(" ", "_");
    }
}
