package co.uk.genonline.simpleweb.web.gallery;

/**
 * Created by thomassecondary on 18/04/15.
 */
public abstract class GalleryHtml implements GalleryHtmlGenerator {
    protected final String galleryUrl;
    protected final String thumbnailUrl;
    protected final String[] imageNames;

    /**
     * Not really sure what to do here.  I need to have a no argument constructor or I will get errors.  But it is
     * completely invalid to invoke an object using a no argument constructor because there will be no data to work
     * with.
     *
     * I have made it private.  That might work.
     */
    protected GalleryHtml() {
        this.galleryUrl = null;
        this.thumbnailUrl = null;
        this.imageNames = null;
    }

    public GalleryHtml(String galleryUrl, String thumbnailUrl, String[] imageNames) {
        this.galleryUrl = galleryUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.imageNames = imageNames;
    }
}
