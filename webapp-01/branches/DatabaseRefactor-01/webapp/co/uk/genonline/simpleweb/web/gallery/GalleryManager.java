package co.uk.genonline.simpleweb.web.gallery;

import java.util.List;

/**
 * Generic type to manage the folders of images which will be used to generate galleries for
 * display on a website.
 *
 * A gallery is a folder under named gallery root folder.  Images are placed under the folder and will be manipulated
 * by a range of classes, including creating a folder for thumbnails and the creation of thumbnail images.  The
 * GalleryManager class carries out high level management of the folders through the other classes.
 *
 * Notes on error handling for Galleries.
 *
 * If there is a problem with generating a gallery then if possible this shouldn't crash the web app.  In almost all cases
 * it is probably better to have the app up and running without a particular gallery than not possible to start the app.
 *
 * ToDo: Use Session Attributes (visible in Manager) to display app status information
 *
 */
public interface GalleryManager {

    /**
     * Causes binding to a given gallery by creating appropriate objects which help to manage the data about the
     * gallery with the supplied name. Gallery will be created with a default HtmlGenerator, so this will effectively
     * be a default gallery style.
     *
     * Error Handling Main Cases
     *
     * - No folder with the given galleryName: Not a fatal error - just means we can't create this gallery and no images
     *   will appear on the associated page.  All we can do is fall through and not do anything but make sure that later
     *   on we don't get fatal error when gallery not present.
     *
     * - I/O Errors (e.g. read error reading files).  Treat like no folder present.
     *
     * @param galleryName The name of the gallery which defines the folder where the images are stored.
     */
    public void addGallery(String galleryName);

    /**
     * Calls addGallery for every galleryName in List provided.
     *
     * @param galleryNames
     */
    public void addGalleries(List<String> galleryNames);

    /**
     * Causes binding to a given gallery by creating appropriate objects which help to manage the data about the
     * gallery with the supplied name.  Also takes supplied HtmlGenerator object and provides to Gallery so can
     * alter the gallery style from the default.
     *
     * @param galleryName The name of the gallery which defines the folder where the images are stored.
     * @param htmlGenerator The object to use to generate the HTML for the page
     */

    public void addGallery(String galleryName, GalleryHtmlGenerator htmlGenerator);
    /**
     * Unbinds a gallery from the objects used to manage it, and removes these objects.
     * Doesn't remove the actual folder or images from the folder.  Also removes any thumbnail folders and files.
     *
     * @param galleryName  The name of the gallery which defines the folder where the images are stored.
     */
    public void removeGallery(String galleryName);

    /**
     * Returns the Gallery object which is being used to manage a gallery with the supplied name.
     *
     * @param galleryName The name of the gallery which defines the folder where the images are stored.
     * @return
     */
    public Gallery getGallery(String galleryName);

    /**
     * Iterates through all the galleries and re-creates them all.  Used as a safe way of ensuring that all the
     * galleries have up to date thumbnails and HTML etc.
     */
    public void regenerateAllGalleries();

    public boolean isGalleryExist(String galleryName);

    public int getNumGalleries();

}
