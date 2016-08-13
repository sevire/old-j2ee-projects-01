package co.uk.genonline.simpleweb.web.gallery;

/**
 * Created by thomassecondary on 01/05/15.
 *
 * Maintains status for a number of different attributes of a Gallery, to help with monitoring and control.
 *
 * Following status information is maintained:
 *
 * - Folder Exists Flag : Set if there is a folder for this Gallery
 * - Number Of Images : The number of image files which were found in the folder
 * - Thumbnails Generated Flag : Set if a thumbnail folder has been generated for this Gallery
 * - Gallery Modified Flag : Set if the Gallery has been modified since last time thumbnails were generated
 */
public class GalleryStatus {

    /**
     * Error Status: If errors occurred during the creation of a gallery, then true.  This will help later on when trying to
     * display galleries.
     */
    boolean galleryError;

    /**
     * Error Status: During creation, if the folder associted with the gallery doesn't exist, then this will be set to true, and
     * so will galleryError, as the gallery is unusable.
     */
    boolean folderExists;

    /**
     * General Status: It will allow for lazy instantiation of thumbnails. Set to
     * true if the thumbnails have been successfully generated for this gallery.
     */
    boolean thumbnailGenerated;

    /**
     * General Status: If (last time it was checked) the gallery had been modified since thumbnails have been generated, then
     * set to true.
     */
    boolean galleryModified;

    /**
     * General Status: The number of images within this gallery.
     */
    int numImages;

    public GalleryStatus() {
        reset();
    }

    public void reset() {
        galleryError = false;
        folderExists = false;
        numImages = 0;
        thumbnailGenerated = false;
        galleryModified = false;
    }

    public boolean isGalleryError() {
        return galleryError;
    }

    protected void setGalleryError(boolean galleryError) {
        this.galleryError = true;
    }

    public boolean isFolderExists() {
        return folderExists;
    }

    protected void setFolderExists(boolean folderExists) {
        this.folderExists = folderExists;
        if (!this.folderExists) {
            setGalleryError(true);
        }
    }

    public int getNumImages() {
        return numImages;
    }

    protected void setNumImages(int numImages) {
        this.numImages = numImages;
    }

    public boolean isThumbnailGenerated() {
        return thumbnailGenerated;
    }

    protected void setThumbnailGenerated(boolean thumbnailGenerated) {
        this.thumbnailGenerated = thumbnailGenerated;
    }

    public boolean isGalleryModified() {
        return galleryModified;
    }

    protected void setGalleryModified(boolean galleryModified) {
        this.galleryModified = galleryModified;
    }

    public String toString() {
        return String.format("galleryError:<%b>, folderExists:<%b>, numImages:<%d>, thumbnailGenerated:<%b>, galleryModified:<%b>",
                isGalleryError(), isFolderExists(), getNumImages(), isThumbnailGenerated(), isGalleryModified());
    }


}
