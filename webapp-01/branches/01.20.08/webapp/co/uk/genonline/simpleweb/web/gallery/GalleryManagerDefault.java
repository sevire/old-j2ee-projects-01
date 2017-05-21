package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Date: 07/07/2012
 * Time: 15:49
 *
 * Manages all the galleries in a website.
 */
public class GalleryManagerDefault implements GalleryManager {

    private final WebLogger logger = new WebLogger();
    private ThumbnailManager thumbnailManager;

    private Map<String, Gallery> galleries;

    private GalleryHtmlGenerator defaultHtmlGenerator;

    private GalleryManagerConfiguration galleryManagerConfiguration;

    public GalleryManagerDefault (
            GalleryManagerConfiguration galleryManagerConfiguration,
            ThumbnailManager thumbnailManager,
            GalleryHtmlGenerator defaultHtmlGenerator) {

        if (galleryManagerConfiguration == null) {
            logger.error("Null value for GalleryManagerConfiguration");
            throw new NullPointerException("Null value for GalleryManagerConfiguration");
        } else if (thumbnailManager == null) {
            logger.error("Null value for ThumbnailConfiguration");
            throw new NullPointerException("Null value for ThumbnailConfiguration");
        } else if (defaultHtmlGenerator == null) {
            logger.error("Null value for GalleryHtmlGenerator");
            throw new NullPointerException("Null value for GalleryHtmlGenerator");
        } else {
            this.thumbnailManager = thumbnailManager;
            this.defaultHtmlGenerator = defaultHtmlGenerator;
            this.galleryManagerConfiguration = galleryManagerConfiguration;

            galleries = new HashMap<String, Gallery>();
        }
    }

    public void addGallery(String galleryName) {
        if (isGalleryExist(galleryName)) {
            logger.error("Attempt to add gallery <%s> which already exists, doing nothing", galleryName);
        } else {
            Gallery newGallery;
            // Try to create gallery, but catch any exceptions
            try {
                newGallery = new GalleryDefault(
                        galleryManagerConfiguration,
                        galleryName,
                        thumbnailManager,
                        defaultHtmlGenerator);
                galleries.put(galleryName, newGallery);
            }
            catch (Exception e) {
                logger.error("Error creating and adding new gallery for gallery <%s>, no gallery added", galleryName);
            }
        }
    }

    /**
     * @param galleryName The name of the gallery which defines the folder where the images are stored.
     * @param htmlGenerator The object to use to generate the HTML for the page
     *
     * Error Handling Main Cases
     *
     * See addGallery(galleryName) for common errors.  See below for errors relating to htmlGenerator
     *
     * - htmlGenerator is null : Can't create gallery as no way of generating HTML.  Could use default Html
     *   generator but this could make things worse (Html may not match the JSP and cause more errors).  Better
     *   to have no gallery.  Throw Null Pointer Exception.
     */
    public void addGallery(String galleryName, GalleryHtmlGenerator htmlGenerator) {
        if (galleryName == null) {
            logger.error("Attempt to add gallery with null name");
        } else if (htmlGenerator == null) {
            logger.error("Attempt to add gallery <%s> with null html generator", galleryName);
        } else {
            galleries.put(galleryName,
                    new GalleryDefault(
                            galleryManagerConfiguration,
                            galleryName,
                            thumbnailManager,
                            htmlGenerator)
            );
        }
    }

    /**
     *
     * @param galleryName The name of the gallery which defines the folder where the images are stored.
     * @return
     *
     *
     */
    public Gallery getGallery(String galleryName) {
        if (galleryName == null) {
            logger.error("Attempt to get gallery with null name, returning null");
            return null;
        } else {
            /**
             * Need to check existence of entry and get it in atomic operation otherwise there is the potential for another thread
             * to remove or add a gallery after the check is made.
             */
            synchronized (this) {
                if (!isGalleryExist(galleryName)) {
                    //There isn't a gallery with this name, so try to add one.  If this doesn't work return null
                    addGallery(galleryName);
                }

                // If still doesn't exist there was an error so return null
                if (!isGalleryExist(galleryName)) {
                    logger.error("Couldn't create gallery for <%s>", galleryName);
                    return null;
                } else {
                    // Gallery exists, but need to check whether it has changed.  If it has recreate from scratch.
                    Gallery gallery = galleries.get(galleryName);

                    // Ensure that gallery is up to date before returning
                    gallery.updateGallery();
                    return gallery;
                }
            }
        }
    }

    public void removeGallery(String galleryName) {
        logger.trace(String.format("Removing gallery <%s>", galleryName));
        if (galleries.remove(galleryName) == null) {
            logger.warn(String.format("Attempt to remove non-existent gallery <%s>", galleryName));
        }
    }

    public boolean isGalleryExist(String galleryName) {
        if (galleries.containsKey(galleryName)) {
            return true;
        } else {
            return false;
        }
    }

    public void regenerateAllGalleries() {
        logger.trace("Re-generating all galleries");
        for (Map.Entry<String, Gallery> entry : galleries.entrySet()) {
            String galleryName = entry.getKey();
            logger.trace(String.format("About to regenerate gallery <%s>", galleryName));

            Gallery gallery = entry.getValue();

            gallery.updateGallery();
        }
    }

    public int getNumGalleries() {
        return galleries.size();
    }

    public String toString() {
        Iterator galleryNameIterator = galleries.keySet().iterator();
        String galleryNameList = "";
        while (galleryNameIterator.hasNext()) {
            galleryNameList += galleryNameIterator.next();
            if (galleryNameIterator.hasNext()) {
                galleryNameList += ",";
            }
        }
        return String.format("GalleryManager: numGalleries: <%d>, galleries <%s>", getNumGalleries(), galleryNameList);
    }
}