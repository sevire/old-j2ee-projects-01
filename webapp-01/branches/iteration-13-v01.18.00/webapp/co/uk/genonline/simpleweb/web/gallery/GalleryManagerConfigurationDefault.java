package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;

import java.io.File;

/**
 * Created by thomassecondary on 20/04/15.
 *
 * ToDo: I think this should be immutable (e.g. imageExtensionList should be copied into final structure).  Need to read up!.
 */
public class GalleryManagerConfigurationDefault implements GalleryManagerConfiguration {
    WebLogger logger = new WebLogger();

    /**
     * File system full path for folder where all the galleries are.  There will be a sub-folder underneath this one for each
     * gallery.
     */
    final File galleriesRootFullPath;

    /**
     * Path, relative from web app root of gallery folder.  Used to construct URLs within gallery HTML to access
     * gallery folders and images.
     *
     * NOTE: At the time of writing the galleriesRootRelPath is the same as the last element of the galleriesRootFullPath.
     *       This is because the controller is written simply to pass the URL on.  This may not always be true as at some point
     *       we may use URL mapping to use a different format for gallery and gallyer image URLs.
     *
     *       So it would be wrong to try to validate the relPath against the FullPath!
     */
    final String galleriesRootRelPath;
    /**
     * Path (typically just a sub-folder), relative to a specific gallery folder, where the thumbnails reside.  This is used
     * both to calculate the full path of the thumbnail folder within the filesystem, and to generate the URL for the images in the
     * gallery HTML.
     */
    final String thumbnailRelPath;

    /**
     * Used in creation of thumbnails to calculate the proportions of each thumnbnail.  The thumbnail will be sized so that either the
     * height or width is at the maximum allowed value, depending upon aspect ratio and the values of the max height and max width.
     */
    final int maxThumbnailHeight;

    /**
     * Used in creation of thumbnails to calculate the proportions of each thumnbnail.  The thumbnail will be sized so that either the
     * height or width is at the maximum allowed value, depending upon aspect ratio and the values of the max height and max width.
     */
    final int maxThumbnailWidth;

    /**
     * A list of extensions which are used to select which files within the galleries folder are to be included in the list of gallery
     * images.  Allows for situation when other files may be in folder (e.g. files renamed xxx.exclude will be excluded).
     */
    private String[] imageExtensionList = null;

    public GalleryManagerConfigurationDefault(
            File galleriesRootFullPath,
            String galleriesRootRelPath,
            String thumbnailRelPath,
            int maxThumbnailHeight,
            int maxThumbnailWidth,
            String[] imageExtensionList) {

        /**
         * If any of the configuration values are invalid there is no point continuing as we won't be able to do anything at all with Galleries.
         * So just throw Null Pointer Exception to crash. Have thought a lot about this - still not 100% sure whether should crash the program!
         */

        String errorString = "";

        this.galleriesRootFullPath = galleriesRootFullPath;
        if (this.galleriesRootFullPath == null) {
            errorString = "GalleryManagerConfigurationDefault: galleriesRootFullPath is null";
            logger.error(errorString);
            throw new NullPointerException(errorString);
        }

        if (!this.galleriesRootFullPath.exists()) {
            errorString = "GalleryManagerConfigurationDefault: galleriesRootFullPath is not valid path";
            logger.error(errorString);
            throw new IllegalArgumentException(errorString);
        }

        this.galleriesRootRelPath = galleriesRootRelPath;
        if (this.galleriesRootRelPath == null) {
            errorString = "GalleryManagerConfigurationDefault: galleriesRootRelPath is null";
            logger.error(errorString);
            throw new NullPointerException(errorString);
        }

        if (this.galleriesRootRelPath.equals("")) {
            errorString = "GalleryManagerConfigurationDefault: galleriesRootRelPath is empty string";
            logger.error(errorString);
            throw new IllegalArgumentException(errorString);
        }

        this.thumbnailRelPath = thumbnailRelPath;
        if (this.thumbnailRelPath == null) {
            errorString = "GalleryManagerConfigurationDefault: thumbnailRelPath is null";
            logger.error(errorString);
            throw new NullPointerException(errorString);
        }

        if (this.thumbnailRelPath.equals("")) {
            errorString = "GalleryManagerConfigurationDefault: thumbnailRelPath is empty string";
            logger.error(errorString);
            throw new IllegalArgumentException(errorString);
        }

        this.maxThumbnailHeight = maxThumbnailHeight;
        // Check that maxThumbnailHeight is half way reasonable value.  Shouldn't be less than 10px
        if (this.maxThumbnailHeight <= 10) {
            errorString = "GalleryManagerConfigurationDefault: maxThumbnailHeight is less than 10px";
            logger.error(errorString);
            throw new IllegalArgumentException(errorString);
        }

        this.maxThumbnailWidth = maxThumbnailWidth;
        // Check that maxThumbnailWidth is half way reasonable value.  Shouldn't less than 10px
        if (this.maxThumbnailWidth <= 10) {
            errorString = "GalleryManagerConfigurationDefault: maxThumbnailWidth is less than 10px";
            logger.error(errorString);
            throw new IllegalArgumentException(errorString);
        }

        if (imageExtensionList == null) {
            errorString = "GalleryManagerConfigurationDefault: Null array for image extension list";
            logger.error(errorString);
            throw new NullPointerException(errorString);
        }
        if (imageExtensionList.length == 0) {
            errorString = "GalleryManagerConfigurationDefault: No extensions in image extension list";
            logger.error(errorString);
            throw new IllegalArgumentException(errorString);
        }
        this.imageExtensionList = new String[imageExtensionList.length];
        // Take local copy of imageExtensionList to avoid uncontrolled modification. (Should I be doing this in other places?)
        System.arraycopy(imageExtensionList, 0, this.imageExtensionList, 0, imageExtensionList.length);
    }

    public File getGalleriesRootFullPath() {
        return this.galleriesRootFullPath;
    }

    public String getGalleriesRootRelPath() {
        return this.galleriesRootRelPath;
    }

    public String getThumbnailRelPath() {
        return this.thumbnailRelPath;
    }

    public int getMaxThumbnailHeight() {
        return maxThumbnailHeight;
    }

    public int getMaxThumbnailWidth() {
        return maxThumbnailWidth;
    }

    public String[] getImageExtensionList() {
        return this.imageExtensionList;
    }
}
