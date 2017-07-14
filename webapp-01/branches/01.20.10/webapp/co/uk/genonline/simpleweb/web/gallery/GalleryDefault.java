package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.monitoring.Collator;
import co.uk.genonline.simpleweb.monitoring.CollectableCategory;
import co.uk.genonline.simpleweb.monitoring.collectables.Collectable;
import co.uk.genonline.simpleweb.monitoring.collectables.CollectableDataObject;
import co.uk.genonline.simpleweb.monitoring.collectables.CollectableImpl;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages the images and thumbnails to support production of a gallery.  Also manages
 * creation of the HTML for a gallery.
 *
 * Errors will generate exception so calling code knows not to use created object.
 */
public class GalleryDefault implements Gallery {

    private WebLogger logger = new WebLogger();

    // Member variables which are injected through constructor
    private final GalleryManagerConfiguration galleryManagerConfiguration;
    private final String galleryName;
    private final ThumbnailManager thumbnailManager;
    private final GalleryHtmlGenerator htmlGenerator;
    private final GalleryStatus galleryStatus;

    //Member variables which are calculated within the object
    private List<GalleryImage> imageList;
    private final File galleryRootFolder;
    private final String galleryUrl;

    // Monitoring related fields
    private Collator monitoringCollator;
    private Collectable galleryStatusCollector;

    private String html;

    public GalleryDefault(GalleryManagerConfiguration galleryConfiguration,
                          String galleryName,
                          ThumbnailManager thumbnailManager,
                          GalleryHtmlGenerator htmlGenerator,
                          Collator monitoringCollator) {

        // Initialise Status object to record error and status values as we go
        this.galleryStatus = new GalleryStatus();

        // Initialise injected member variables

        this.galleryManagerConfiguration = galleryConfiguration;

        String error;
        if (this.galleryManagerConfiguration == null) {
            error = String.format("Attempt to create gallery <%s> with null GalleryConfiguration", galleryName);
            logger.error(error);
            galleryStatus.setGalleryError(true);
            throw new NullPointerException(error);
        }

        this.galleryName = galleryName;
        // Check for null galleryName
        if (this.galleryName == null) {
            error = "Attempt to create GalleryDefault object with null galleryName";
            logger.error(error);
            galleryStatus.setGalleryError(true);
            throw new NullPointerException(error);
        }
        // Check for empty galleryName
        if (this.galleryName.equals("")) {
            error = "Attempt to create GalleryDefault object with empty galleryName";
            logger.error(error);
            galleryStatus.setGalleryError(true);
            throw new IllegalArgumentException(error);
        }

        this.thumbnailManager = thumbnailManager;
        if (this.thumbnailManager == null) {
            error = "Attempt to create GalleryDefault object with null thumbnailManager";
            logger.error(error);
            galleryStatus.setGalleryError(true);
            throw new NullPointerException(error);
        }

        this.htmlGenerator = htmlGenerator;
        if (this.htmlGenerator == null) {
            error = "Attempt to create GalleryDefault object with null htmlGenerator";
            logger.error(error);
            galleryStatus.setGalleryError(true);
            throw new NullPointerException(error);
        }

        this.monitoringCollator = monitoringCollator;
        if (this.monitoringCollator == null) {
            error = "Collator for monitoring objects is null, can't maintain monitoring data";
            logger.warn(error);
            galleryStatus.setGalleryError(true);
            // Note don't throw exception here.  Can continue without collator.
        }

        // Can only get here if there hasn't been an error
        // Initialise some other member variables
        this.galleryRootFolder = new File(this.galleryManagerConfiguration.getGalleriesRootFullPath(), galleryName);
        if (!this.galleryRootFolder.exists()) {
            error = String.format("Attempt to create GalleryDefault object for gallery <%s> for which no folder <%s> exists",
                    galleryName,
                    this.galleryRootFolder.toString());
            logger.error(error);
            galleryStatus.setGalleryError(true);
            this.galleryUrl = null;
            throw new IllegalArgumentException(error);
        } else {
            this.galleryUrl = this.galleryManagerConfiguration.getGalleriesUrlRelPath() + "/" + galleryName;
            initialiseGallery();
        }
}

    /**
     * Guarantees setting up the gallery correctly, regardless of starting state, unless there is a runtime error
     * that prevents this:
     *
     * Cases to take into account are:
     *
     * - CASE 1:
     *     - Gallery folder not present
     *
     *       No gallery so can't do anything.  Folder will have been there at instantiation or
     *       gallery wouldn't have been stored.  So raise File Not Found exception.
     *
     * - CASE 2:
     *     - Gallery folder present
     *     - No images in folder
     *
     *       Not an error but no images so no gallery to display and no thumbnails to create. Don't bother creating
     *       thumbnail folder.  Just return.  getHtml will return empty gallery (one way or another).
     *
     * - CASE 3:
     *     - Gallery folder present
     *     - Images in folder
     *     - No thumbnail folder present
     *
     *     Create thumbnail folder (which will be empty) then create all thumbnails.
     *
     * - CASE 4:
     *     - Gallery folder present
     *     - Images in folder
     *     - Thumbnail folder present
     *
     *     Regardless of contents of thumbnail folder, need to clear it out and recreate thumbnails.
     */
    private void initialiseGallery() {
        galleryStatus.reset();
        String error;
        if (!this.galleryRootFolder.exists()) {
            error = String.format("Attempt to (re)initialise gallery <%s> for which no folder <%s> exists",
                    galleryName,
                    this.galleryRootFolder.toString());
            logger.error(error);
            galleryStatus.setFolderExists(false);
        } else {
            galleryStatus.setFolderExists(true);
            createGalleryImageList();
            if (this.getNumberOfImages() > 0) {
                if (thumbnailManager.createThumbnailFolder(this.galleryRootFolder));

                // Don't need to delete thumbnails as createAllThumbnails will deal with that
                boolean success = thumbnailManager.createAllThumbnails(this.galleryRootFolder, this.getImageList());

                galleryStatus.setThumbnailGenerated(success);

                // Note am not calling getHtml here.  Will be called first time page is requested.  This is to
                // allow Galleries to be created at start up for monitoring purposes, without having to generate
                // HTML for every gallery straight away.
            }
        }
        initialiseMonitoringCollators();
    }

    private void initialiseMonitoringCollators() {
        galleryStatusCollector = new CollectableImpl(
                CollectableCategory.GALLERY,
                getName(),
                false) {
            @Override
            public CollectableDataObject getData() {
                return getGalleryStatus();
            }
        };
        monitoringCollator.addOrUpdateCollector(galleryStatusCollector);
    }

    private void createGalleryImageList() {
        imageList = new ArrayList<GalleryImage>(20);
        File[] imageFileList = getGalleryFileList();

        // Check whether there is an error on the gallery before continuing
        if (!galleryStatus.isGalleryError()) {

            /**
             * It's not necessarily an error if imageFileList is null, it may just means there are no images
             * so just return a zero length imageList.
             */
            if (imageFileList != null) {
                for (File galleryImage : imageFileList) {
                    this.imageList.add(new GalleryImageDefault(galleryImage));
                }
            }
        }
        galleryStatus.setNumImages(getNumberOfImages());
    }

    private File[] getGalleryFileList() {
        logger.trace(String.format("Generating image file list for gallery <%s>", galleryName));

        if (!galleryRootFolder.exists() || !galleryRootFolder.isDirectory()) {
            galleryStatus.setFolderExists(false);
            // No folder so no gallery!  Update status to reflect.
            logger.warn("No gallery folder exists at <%s>", galleryRootFolder.toString());
            return null;
        } else {
            galleryStatus.setFolderExists(true);
            File[] list;
            FileFilter filter = new ImageFileFilter(galleryManagerConfiguration.getImageExtensionList());
            logger.debug("Looking for files with these extensions: <%s>", (galleryManagerConfiguration.getImageExtensionList()).toString());
            logger.debug(String.format("About to list files in <%s>", galleryRootFolder));
            list = galleryRootFolder.listFiles(filter);
            if (list == null) {
                logger.warn(String.format("No image files found in <%s>", galleryRootFolder));
            } else {
                logger.debug(String.format("Found <%d> files in <%s>", list.length, galleryRootFolder));
            }
            return list;
        }
    }


    public int getNumberOfImages() {
        return imageList.size();
    }

    public List<GalleryImage> getImageList() {
        return imageList;
    }

    public String getName() {
        return galleryName;
    }

    /**
     * This method will work out whether the gallery has been modified in a way which means it should be re-initialised.
     *
     * In practice we are looking for:
     * - Any of the existing images changing
     * - Any of the images missing (i.e. have been deleted)
     * - Any new images.
     * @return
     */
    public boolean isModified() {

        boolean isModified = false;

        // First check all the images we know about to see whether any have changed (including been deleted).
        Iterator<GalleryImage> iterator = imageList.iterator();
        while (!isModified && iterator.hasNext()) {
            GalleryImage image = iterator.next();
            if (image.isModified()) {
                isModified = true;
            }
        }

        if (!isModified) {

            /**
             * Now check whether there are any extra files.  Check this just be getting a list of files with the right extensions
             * and checking the number of files.  It should be exactly the same is it was.
             *
             * Note that it is possible for someone to delete files between checking for this and checking for new files.  There
             * is no way of preventing this.  Best thing is to check whether same number of images and if not then flag as modified.
             */

             if (getGalleryFileList().length != getNumberOfImages()) {
                 isModified = true;
             }
        }
        // Whatever result we got, make sure that the gallery status reflects it
        galleryStatus.setGalleryModified(isModified);
        return isModified;
    }

    public void updateGallery() {
        // Not sure if this is the best way but I think it will work - just check whether gallery has changed
        // and if so initialise from scratch!
        if (isModified() || galleryStatus.isGalleryError()) {
            initialiseGallery();
        }
    }

    public String getHtml(boolean forceRegenerate) {
        if (forceRegenerate || html == null) {
            if (galleryStatus.isGalleryError()) {
                html = "";
            } else {
                html = htmlGenerator.getHtml(galleryName, getImageList());
                if (html != null) {
                    galleryStatus.setHtmlGenerated(true);
                }
            }
        }
        galleryStatus.incrementRequestCount();
        return html;
    }

    public GalleryStatus getGalleryStatus() {
        if (!galleryStatus.isGalleryError()) {
            isModified(); // Will automatically update isModified statusFlag
        }
        return galleryStatus;
    }

    public String toString() {
        return String.format("Gallery <%s>, numImages: <%d>", getName(), getNumberOfImages());
    }
}
