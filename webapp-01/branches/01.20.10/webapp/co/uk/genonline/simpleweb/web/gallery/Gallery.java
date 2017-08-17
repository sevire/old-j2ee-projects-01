package co.uk.genonline.simpleweb.web.gallery;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by thomassecondary on 20/04/15.
 *
 * Manages images and thumbnails for a gallery with a given name.  The objects created with Gallery type will set up a
 * tight binding between the object and a folder which contains the images to be displayed within the Gallery.
 */
public interface Gallery {

    /**
     * Returns a List containing one GalleryImage object for each image being managed within the gallery.
     *
     * @return
     */
    public List<GalleryImage> getImageList();

    /**
     * Returns the string which is the name of the gallery (and folder where images are stored).
     *
     * @return
     */
    public String getName();

    /**
     * Checks whether the gallery has been modified since last updated.  This will check whether any of the images
     * have changed or whether there are any new images.
     *
     * @return
     */
    public boolean isModified();

    /**
     * Instructs the gallery to refresh its data, including:
     * - List of images and information about images
     * - Thumbnails
     * - HTML
     */
    public void updateGallery();

    /**
     * Generates and returns the HTML used to display this gallery.  Normally generates html once and then
     * just returns it.  Can be forced to regenerate html if required.
     *
     * @param forceRegenerate if true then html will be regenerated from scratch
     *
     * @return
     */
    String getHtml(boolean forceRegenerate);

    boolean isHtmlGenerated();

    int getNumberOfImages();

    GalleryStatus getGalleryStatus();

    boolean galleryFolderExists();

    LocalDateTime getTimeCreated();

}
