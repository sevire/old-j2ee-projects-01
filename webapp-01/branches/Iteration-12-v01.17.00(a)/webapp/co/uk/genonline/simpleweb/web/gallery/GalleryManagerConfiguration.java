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
    public File getGalleriesRootFullPath();

    public String getGalleriesRootRelPath(); // Rel Path used to generate URL

    public String getThumbnailRelPath();

    public int getMaxThumbnailHeight();

    public int getMaxThumbnailWidth();

    public String[] getImageExtensionList();
}
