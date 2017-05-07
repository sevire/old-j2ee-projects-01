package co.uk.genonline.simpleweb.controller.screendata.displaybeans;

/**
 * Created by thomassecondary on 23/04/16.
 */
public class ScreenGalleryBean {
    private String galleryData;
    private String maxThumbnailWidth;
    private String maxThumbnailHeight;

    public String getMaxThumbnailWidth() {
        return maxThumbnailWidth;
    }

    public void setMaxThumbnailWidth(String maxThumbnailWidth) {
        this.maxThumbnailWidth = maxThumbnailWidth;
    }

    public String getMaxThumbnailHeight() {
        return maxThumbnailHeight;
    }

    public void setMaxThumbnailHeight(String maxThumbnailHeight) {
        this.maxThumbnailHeight = maxThumbnailHeight;
    }

    public String getGalleryData() {
        return galleryData;
    }

    public void setGalleryData(String galleryData) {
        this.galleryData = galleryData;
    }


}
