package co.uk.genonline.simpleweb.controller.test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 04/11/2012
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class WebsiteData {
    private static WebsiteData ourInstance = new WebsiteData();

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
                    "No Gallery",
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
                    "No Gallery",   // Number of images in gallery - "No Gallery" means no gallery.
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
                    "No Gallery",
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
                    "",
                    "",
                    "No Gallery",
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
                    "No Gallery",
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
                    "No Gallery",
                    "",
                    ""},
                    {
                    }});
            put("musings", new String[][]{{
                    "",
                    "",
                    "Musings",
                    "",
                    "",
                    "No Gallery",
                    "",
                    ""},
                    {
                    }});
            put("contact", new String[][]{{
                    "",
                    "",
                    "Contact",
                    "",
                    "",
                    "No Gallery",
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
                    "No Gallery",
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
                    "No Gallery",
                    "",
                    ""},
                    {
                    }});
            put("hunteress", new String[][]{{
                    "",
                    "",
                    "Hunteress",
                    "The Hunteress",
                    "",
                    "No Gallery",
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
                    "No Gallery",
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
                    "No Gallery",
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
                    "No Gallery",
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
                    "No Gallery",
                    "",
                    ""},
                    {
                    }});
        }
    };

    public static WebsiteData getInstance() {
        return ourInstance;
    }

    private WebsiteData() {
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
            String fileName = getPageItem(screenName, TestDataIndex.HTML_FILE_NAME_FOR_SCREEN.getIndex());
            if (fileName.equals("")) {
                return generateHTMLFileName(getLinkName(WebsitePlatform.J2EE, screenName));
            } else {
                return fileName;
            }
        } else {
            return screenName; // J2EE version uses screenName to request page
        }
    }

    public String getLinkName(WebsitePlatform platform, String screenName) {
        if (platform == WebsitePlatform.HTML) {
            String linkName = getPageItem(screenName, TestDataIndex.HTML_LINK_NAME.getIndex());
            if (linkName.equals("")) {
                return generateHTMLLinkName(getPageItem(screenName, TestDataIndex.J2EE_LINK_NAME.getIndex()));
            } else {
                return linkName;
            }
        } else {
            return getPageItem(screenName, TestDataIndex.J2EE_LINK_NAME.getIndex());
        }
    }

    public String getPageTitle(WebsitePlatform platform, String screenName) {
        String pageTitle;
        if (platform == WebsitePlatform.HTML) {
            pageTitle = getPageItem(screenName, TestDataIndex.HTML_PAGE_TITLE.getIndex());
        } else {
            pageTitle = getPageItem(screenName, TestDataIndex.J2EE_PAGE_TITLE.getIndex());
        }
        if (pageTitle.equals("")) {
            return getPageItem(screenName, TestDataIndex.J2EE_LINK_NAME.getIndex());
        } else {
            return pageTitle;
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
        String galleryString = getPageItem(screenName, TestDataIndex.NUM_GALLERY_IMAGES.getIndex());
        if (galleryString.equals("No Gallery")) {
            return false;
        } else {
            return true;
        }
    }

    public int numGalleryImages(String screenName) {
        if (!isGallery(screenName)) {
            return 0;
        } else {
            String galleryString = getPageItem(screenName, TestDataIndex.NUM_GALLERY_IMAGES.getIndex());
            int numImages = Integer.parseInt(galleryString);
            if (numImages < 0) {
                numImages = 0;
            }
            return numImages;
        }
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
