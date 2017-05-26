package co.uk.genonline.simpleweb.controller.screendata.displaybeans;

import co.uk.genonline.simpleweb.model.bean.ScreensEntityDecorator;

/**
 * Created by thomassecondary on 20/04/16.
 *
 * Bean which holds data relating to most of the screens in the website.  Defines the fields needed by the JSP.  The
 * bean will be assigned as an attribute of the Servlet and then can be accessed directly from the JSP files.
 */
public class ScreenDataBean {

    private ScreensEntityDecorator screensEntityDecorator;
    private boolean blogEnabledFlag;
    private String blogLink;
    private String mistressPageLink;
    private String homePageLink;

    public String getMistressPageLink() {
        return mistressPageLink;
    }

    public void setMistressPageLink(String mistressPageLink) {
        this.mistressPageLink = mistressPageLink;
    }

    public String getHomePageLink() {
        return homePageLink;
    }

    public void setHomePageLink(String homePageLink) {
        this.homePageLink = homePageLink;
    }

    public boolean getBlogEnabledFlag() {
        return blogEnabledFlag;
    }

    public void setBlogEnabledFlag(boolean blogEnabledFlag) {

        this.blogEnabledFlag = blogEnabledFlag;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }
    public ScreensEntityDecorator getScreensEntityDecorator() {
        return screensEntityDecorator;
    }

    public void setScreensEntityDecorator(ScreensEntityDecorator screensEntityDecorator) {
        this.screensEntityDecorator = screensEntityDecorator;
    }

}
