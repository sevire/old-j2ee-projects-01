package co.uk.genonline.simpleweb.controller.test.support;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 05/11/2012
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class TestConfiguration {
    private WebsitePlatform platform;
    private String domain;
    private String host = "";

    // In a live environment the splash page is by definition at the Domain URL, but may be
    // different for a testing environment so we will maintain a separate member.  If blank use domain.

    private String splashURL = "";

    TestConfiguration(ConfigurationName configurationName) {
        switch(configurationName) {
            case STATIC_HTML_LOCAL_DEV: // Current development environment in Sites on laptop
                platform = WebsitePlatform.HTML;
                domain = "http://localhost/~thomassecondary/lucifersdarkangel(trunk)/";
                host = domain + "lda/";
            break;
            case STATIC_HTML_REMOTE_TEST_1: // Current testing environment on Man Goddesses server
                platform = WebsitePlatform.HTML;
                domain = "http://manchestergoddesses.co.uk/lda-staging/"; // not really domain but required to make tests work
                host = domain;
            break;
/*
            case STATIC_HTML_REMOTE_LIVE: // Live system while still on static (s)html platform
                platform = WebsitePlatform.HTML;
                domain = "http://lucifersdarkangel.co.uk/";
                host = domain + "lda/";
            break;
*/
/*
            case J2EE_LOCAL_DEV: // This refers to the environment set up in IDEA
                platform = WebsitePlatform.J2EE;
                domain = "http://localhost:8080/";
            break;
*/
            case J2EE_LOCAL_TEST_1: // This refers to the local Tomcat server on the dev laptop.
                platform = WebsitePlatform.J2EE;
                domain = "http://localhost:8080/";
                host = domain + "lda/";
            break;
            case J2EE_REMOTE_TEST_1: // This is the Oxxus environment with the IP address
                platform = WebsitePlatform.J2EE;
                domain = "http://31.193.143.174/";
                host = domain + "lda/";
            break;
/*
            case J2EE_REMOTE_LIVE: // This is the Oxxus environment once the domain servers point to it
                platform = WebsitePlatform.J2EE;
                domain = "http://lucifersdarkangel.co.uk/"; // This won't work until we go live
            break;
*/

        }
    }
    public WebsitePlatform getPlatform() {
        return platform;
    }

    public String getHost() {
        if (host.equals("")) {
            return domain;
        } else {
            return host;
        }
    }

    public String getDomain() {
        return domain;
    }

    public String getSplashURL() {
        if (splashURL.equals("")) {
            return domain;
        } else {
            return splashURL;
        }
    }
}
