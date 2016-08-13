package integrationtests.support;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 05/11/2012
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public enum TestConfiguration {
/*
    STATIC_HTML_LOCAL_DEV(
            false,
            WebsitePlatform.HTML,
            "http://localhost/~thomassecondary/lucifersdarkangel(trunk)/",
            "lda/"),
    STATIC_HTML_REMOTE_TEST_1(
            false,
            WebsitePlatform.HTML,
            "http://manchestergoddesses.co.uk/lda-staging/", // not really domain but required to make tests work
            ""),
    STATIC_HTML_REMOTE_LIVE(
            false,
            WebsitePlatform.HTML,
            "http://lucifersdarkangel.co.uk/",
            "lda/"),
*/
    J2EE_LOCAL_DEV(
            true,
            WebsitePlatform.J2EE,
            "http://localhost:8080/",
            "lda/");
/*    J2EE_REMOTE_LIVE(
            false,
            WebsitePlatform.J2EE,
            "http://lucifersdarkangel.co.uk/",
            "lda/");
*/

    private boolean enabledFlag;
    private WebsitePlatform platform;
    private String domain;
    private String host;
    private static int numEnabledValues = -1;

    // In a live environment the splash page is by definition at the Domain URL, but may be
    // different for a testing environment so we will maintain a separate member.  If blank use domain.

    private String splashURL = "";

    TestConfiguration(boolean enabledFlag, WebsitePlatform platform, String domain, String host) {
        this.enabledFlag = enabledFlag;
        this.platform = platform;
        this.domain = domain;
        this.host = host.equals("") ? domain : domain + host;
    }

    public boolean isEnabledFlag() {
        return enabledFlag;
    }

    public WebsitePlatform getPlatform() {
        return platform;
    }

    public String getHost() {
        return host;
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

    public String toString() {
        return getPlatform() +  " : " + getHost();
        //String abc = values();
    }

    public static int getNumEnabledValues() {
        if (numEnabledValues == -1) {
            numEnabledValues = 0;
            for (TestConfiguration value : values()) {
                if (value.isEnabledFlag()) {
                    numEnabledValues++;
                }
            }
        }
        return numEnabledValues;
    }

    public static TestConfiguration[] enumValues() {
        TestConfiguration[] enumValues = new TestConfiguration[getNumEnabledValues()];

        int i = 0;
        for (TestConfiguration value : values()) {
            if (value.isEnabledFlag()) {
                enumValues[i++] = value;
            }
        }
        return enumValues;
    }
}
