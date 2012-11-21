package co.uk.genonline.simpleweb.controller.test;

import co.uk.genonline.simpleweb.controller.test.support.TestDataIndex;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 04/11/2012
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class WebsiteTestData {
    private static WebsiteTestData ourInstance = new WebsiteTestData();

        // Set up array with key data for each field relevant to requesting a page
    // The first column in the array is the html file used in the static version of the site

    protected static Map<String, String[][]> websiteData = new HashMap<String, String[][]>() {
        {
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
                            "Make Me smile and become one of My favoured slaves by surprising Me with a gift"
                    }});
            put("viduitas", new String[][]{{
                    "",
                    "",
                    "Miss Viduitas",
                    "",
                    "",
                    "4",
                    "",
                    "",
                    ""},
                    {
                            "\"There is no more lively sensation than that of pain; its impressions are certain and dependable, they never deceive\" - The Marquis de Sade",
                            "I am the girl your mother warned you about. Enchantingly dark and delightfully devious, I revel in being sadistic and take great pleasure in what I do.",
                            "I favour the Gothic aesthetic both inside the dungeon and out in the 'real' world; I'm slender with pale skin and long dark hair. I have a number of large tattoos and various piercings, so if you like women of an alternative persuasion, I do not disappoint.",
                            "To find out more about myself and what I offer or to arrange a session please visit my website at\n",
                            "http://missviduitas.wordpress.com",
                            "or email me at",
                            "themistressmacabre@gmail.com",
                            "",
                    }});
            put("specialities", new String[][]{{
                    "",
                    "",
                    "Specialities",
                    "Princess Lucina Specialities",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
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
                    "4",
                    "",
                    "",
                    ""},
                    {
                    }});
            put("musings", new String[][]{{
                    "",
                    "",
                    "Slave Musings",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
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
            put("hunteress", new String[][]{{
                    "",
                    "",
                    "The Hunteress",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                    }});
            put("madox", new String[][]{{
                    "",
                    "",
                    "Mistress Madox",
                    "",
                    "",
                    "-1",
                    "",
                    "",
                    ""},
                    {
                    }});
            put("zara", new String[][]{{
                    "",
                    "",
                    "Lady Zara",
                    "",
                    "",
                    "-1",
                    "No Page",
                    "",
                    ""},
                    {
                    }});
            put("tozascarlet", new String[][]{{
                    "",
                    "",
                    "Lady Toza Scarlet",
                    "",
                    "",
                    "-1",
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
                    }});
        }
    };

    public static WebsiteTestData getInstance() {
        return ourInstance;
    }

    private WebsiteTestData() {
    }

    String getTestDataHtmlFilename(String screenName) {
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
    
    int getNumGalleryImages(String screenName) {
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

        if (platform == WebsitePlatform.HTML) {
            return getTestDataHtmlFilename(screenName);
        } else {
            return screenName; // J2EE version uses screenName to request page
        }
    }

    public String getLinkName(WebsitePlatform platform, String screenName) {
        /*
        Returns the string used within the link in the header of the page.  This will be the short name
        but for HTML versions will be capitalised.
         */

        String data = getTestDataScreenShortName(screenName);
        if (platform == WebsitePlatform.HTML) {
            return data.toUpperCase();
        } else {
            return data;
        }
    }

    public String getPageTitle(WebsitePlatform platform, String screenName) {
        String pageTitle;
        if (platform == WebsitePlatform.HTML) {
            /*
            In the HTML version of the site, the <title> element is set to be the same as
            the link text for that page, not a longer title. This will be the short name
            in the test data.
             */

            return getTestDataScreenShortName(screenName);
        } else {
            return getTestDataScreenLongName(screenName);
        }
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
