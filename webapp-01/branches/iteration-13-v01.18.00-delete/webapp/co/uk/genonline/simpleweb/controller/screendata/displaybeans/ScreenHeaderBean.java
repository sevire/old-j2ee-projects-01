package co.uk.genonline.simpleweb.controller.screendata.displaybeans;

/**
 * Created by thomassecondary on 20/04/16.
 *
 * Bean used to hold the data required to generate the header which is used by most (probably all) of the pages on the
 * website.
 */
public class ScreenHeaderBean {
    private ScreenMenuBean menus;

    private String headerImageLeft;
    private String headerImageRight;

    private String HtmlBlogLink;
    private String HtmlhomePageLink;

    public ScreenMenuBean getMenus() {
        return menus;
    }

    public void setMenus(ScreenMenuBean menus) {
        this.menus = menus;
    }

    public String getHeaderImageLeft() {
        return headerImageLeft;
    }

    public void setHeaderImageLeft(String headerImageLeft) {
        this.headerImageLeft = headerImageLeft;
    }

    public String getHeaderImageRight() {
        return headerImageRight;
    }

    public void setHeaderImageRight(String headerImageRight) {
        this.headerImageRight = headerImageRight;
    }

    public String getHtmlBlogLink() {
        return HtmlBlogLink;
    }

    public void setHtmlBlogLink(String htmlBlogLink) {
        HtmlBlogLink = htmlBlogLink;
    }

    public String getHtmlhomePageLink() {
        return HtmlhomePageLink;
    }

    public void setHtmlhomePageLink(String htmlhomePageLink) {
        HtmlhomePageLink = htmlhomePageLink;
    }
}
