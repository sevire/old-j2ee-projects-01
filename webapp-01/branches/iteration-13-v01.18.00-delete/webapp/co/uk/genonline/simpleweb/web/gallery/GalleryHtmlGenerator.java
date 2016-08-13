package co.uk.genonline.simpleweb.web.gallery;

import java.util.List;

/**
 * Defines interface to be used for classes which generate the HTML to drive a gallery.
 * Allows generation of different gallery styles transparently.
 *
 * Inputs:
 * - URL (not path) for image folder
 * - URL for thumbnail folder
 * - List of image names
 *
 */
public interface GalleryHtmlGenerator {
    public String getHtml(String galleryName, List<GalleryImage> galleryImageList);
}
