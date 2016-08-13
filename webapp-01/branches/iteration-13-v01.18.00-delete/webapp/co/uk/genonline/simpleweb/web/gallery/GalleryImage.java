package co.uk.genonline.simpleweb.web.gallery;

import java.io.File;

/**
 * Created by thomassecondary on 20/04/15.
 *
 * Used to manage and store data relevant for managing images which a gallery. Implements
 * Comparable to make it possible to sort the images, which will allow them to be displayed
 * in a pre-determined order (by sorting on name).
 */
public interface GalleryImage extends Comparable<GalleryImage> {

    /**
     * Returns the full image name (name + extension) for the image being managed.
     *
     * @return
     */
    public String getImageFullName();

    /**
     * Returns the name of the image before the extension
     *
     * @return
     */

    public String getImageName();

    /**
     * Returns the extension of the image.
     *
     * @return
     */

    public String getImageExtension();

    /**
     * Returns the width in pixels of the image.
     *
     * @return
     */

    public int getImageWidth();

    /**
     * Returns the height of the image in pixels.
     * @return
     */

    public int getImageHeight();

    /**
     * Returns the full path of the image.
     *
     * @return
     */

    public File getImageFullPath();

    /**
     * Works out whether the image has been modified since last updated.  Includes check for whether
     * image still exists or whether the modified date has changed. Possibly other checks.
     *
     * @return
     */
    public boolean isModified();

    /**
     * Returns last Modified date/time in long format.  Not sure I will need this in the app but useful for testing.
     *
     * @return
     */
    public long getLastModified();
}
