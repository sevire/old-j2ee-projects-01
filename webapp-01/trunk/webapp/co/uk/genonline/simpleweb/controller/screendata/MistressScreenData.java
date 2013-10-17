package co.uk.genonline.simpleweb.controller.screendata;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 12/10/2013
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class MistressScreenData implements ScreenData {

    private String chambersLinkBar;
    private String mistressPageLink;
    private String homePage;
    private String maxThumbnailWidth;
    private String maxThumbnailHeight;
    private String blogLink;
    private String galleryHtml;

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getMaxThumbnailWidth() {
        return maxThumbnailWidth;
    }

    public void setMaxThumbnailWidth(String maxImgWidth) {
        this.maxThumbnailWidth = maxImgWidth;
    }

    public String getMaxThumbnailHeight() {
        return maxThumbnailHeight;
    }

    public void setMaxThumbnailHeight(String maxImgHeight) {
        this.maxThumbnailHeight = maxImgHeight;
    }


    public String getJSPname() {
        return "screen.jsp";
    }

    public void setChambersLinkBar(String linkBar) {
        this.chambersLinkBar = linkBar;
    }

    public void setMistressPageLink(String link) {
        this.mistressPageLink = link;
    }

    public String getChambersLinkBar() {
        return chambersLinkBar;
    }

    public String getMistressPageLink() {
        return mistressPageLink;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    public void setGalleryHtml(String galleryHtml) {
        this.galleryHtml = galleryHtml;
    }

    public String getGalleryHtml() {
        return galleryHtml;
    }
}
