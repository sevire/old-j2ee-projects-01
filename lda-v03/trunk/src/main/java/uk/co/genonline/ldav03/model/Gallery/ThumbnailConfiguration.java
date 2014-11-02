package uk.co.genonline.ldav03.model.Gallery;

/**
 * Makes available key information which drives the creation of thumbnails for gallery images
 */
public class ThumbnailConfiguration {
    ThumbnailConfiguration() {
        thumbnailMaxHeight = 150;
        thumbnailMaxWidth = 150;
    }
    private int thumbnailMaxWidth;
    private int thumbnailMaxHeight;

    public int getThumbnailMaxWidth() {
        return thumbnailMaxWidth;
    }

    public int getThumbnailMaxHeight() {
        return thumbnailMaxHeight;
    }
}
