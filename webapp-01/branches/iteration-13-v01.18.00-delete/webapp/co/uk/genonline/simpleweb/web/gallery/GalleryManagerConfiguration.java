package co.uk.genonline.simpleweb.web.gallery;

import java.io.File;

/**
 * Created by thomassecondary on 20/04/15.
 *
 * Includes a list of values which are required to manage galleries (e.g. pathname to root dir of all galleries.
 *
 * The purpose of having this data in an interface/class is to avoid having to change a lot of code (particularly
 * parameter lists) when a new configuration item is identified.
 */
public interface GalleryManagerConfiguration {
    /**
     * Full path in file system of folder where galleries are to be placed.  Not to be used for
     * generating URLs!
     * @return
     */
    public File getGalleriesRootFullPath();

    /**
     * Path to be used in URLs to refer to gallery images.  Must include as first part the reference
     * path set up in server.xml in Context tag.
     * @return
     */
    public String getGalleriesUrlRelPath();

    public String getThumbnailRelPath();

    public int getMaxThumbnailHeight();

    public int getMaxThumbnailWidth();

    public String[] getImageExtensionList();
}
