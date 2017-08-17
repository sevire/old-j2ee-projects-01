package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.monitoring.Collator;
import co.uk.genonline.simpleweb.monitoring.CollectableCategory;
import co.uk.genonline.simpleweb.monitoring.collectables.Collectable;
import co.uk.genonline.simpleweb.monitoring.collectables.CollectableDataObject;
import co.uk.genonline.simpleweb.monitoring.collectables.CollectableImpl;

import java.io.File;
import java.io.FileFilter;
import java.time.LocalDateTime;
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
    private final LocalDateTime timeCreated;

    //Member variables which are calculated or maintained within the object
    private List<GalleryImage> imageList;
    private final File galleryRootFolder;
    private final String galleryUrl;
    private LocalDateTime lastGeneratedTime; // Used to work out whether anything has changed when generating a Gallery

    // Monitoring related fields
    private Collator monitoringCollator;
    private Collectable galleryStatusCollector;

    private String html;

    public GalleryDefault(GalleryManagerConfiguration galleryConfiguration,
                          String galleryName,
                          ThumbnailManager thumbnailManager,
                          GalleryHtmlGenerator htmlGenerator,
                          Collator monitoringCollator) {

        timeCreated = LocalDateTime.now();

        // Initialise Last Generated Date / Time to be a long time in the past to force generation of gallery.
        lastGeneratedTime = LocalDateTime.of(1900, 1, 1, 0, 0, 0, 0); // 1/1/1900 00:00:00.0

        // Initialise Status object to record error and status values as we go
        this.galleryStatus = new GalleryStatus(this);

        // Initialise injected member variables

        this.galleryManagerConfiguration = galleryConfiguration;

        String error;
        if (this.galleryManagerConfiguration == null) {
            error = String.format("Attempt to create gallery <%s> with null GalleryConfiguration", galleryName);
            logger.error(error);
            throw new NullPointerException(error);
        }

        this.galleryName = galleryName;
        // Check for null galleryName
        if (this.galleryName == null) {
            error = "Attempt to create GalleryDefault object with null galleryName";
            logger.error(error);
            throw new NullPointerException(error);
        }
        // Check for empty galleryName
        if (this.galleryName.equals("")) {
            error = "Attempt to create GalleryDefault object with empty galleryName";
            logger.error(error);
            throw new IllegalArgumentException(error);
        }

        this.thumbnailManager = thumbnailManager;
        if (this.thumbnailManager == null) {
            error = "Attempt to create GalleryDefault object with null thumbnailManager";
            logger.error(error);
            throw new NullPointerException(error);
        }

        this.htmlGenerator = htmlGenerator;
        if (this.htmlGenerator == null) {
            error = "Attempt to create GalleryDefault object with null htmlGenerator";
            logger.error(error);
            throw new NullPointerException(error);
        }

        this.monitoringCollator = monitoringCollator;
        if (this.monitoringCollator == null) {
            error = "Collator for monitoring objects is null, can't maintain monitoring data";
            logger.warn(error);
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
            this.galleryUrl = null;
            throw new IllegalArgumentException(error);
        } else {
            this.galleryUrl = this.galleryManagerConfiguration.getGalleriesUrlRelPath() + "/" + galleryName;
            checkAndUpdateGallery();
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
     *       No gallery so can't do anything. A WARNing needs to be raised as the user will have configured a gallery
     *       for this screen so will expect one, but there's nothing we can do.
     *
     * - CASE 2:
     *     - Gallery folder present
     *     - No images in folder
     *
     *       Not an error but no gallery can be generated so issue a WARNing.  Just return.  getHtml will return
     *       empty gallery (one way or another).
     *
     * - CASE 3 (this is the normal situation for a new gallery):
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
     *     To ensure the gallery is fully dynamic, check thumbnails to confirm they are up to date and regenerate if not
     *     and also check whether any images have been deleted leaving orphan thumbnails and delete them.
     *
     *     Return true if gallery was updated.  This will trigger re-calculation of HTML.
     */
    private boolean checkAndUpdateGallery() {

        String error; // If an error occurs during Gallery checking / creation then store it here.

        if (galleryFolderExists()) {
            if (getNumberOfImages() > 0 ) {
                if (thumbnailManager.createThumbnailFolder(this.galleryRootFolder)) {

                    thumbnailManager.checkAndCreateThumbnails(this.galleryRootFolder, this.getImageList());

                    // Note am not calling getHtml here.  Will be called first time page is requested.  This is to
                    // allow Galleries to be created at start up for monitoring purposes, without having to generate
                    // HTML for every gallery straight away.
                }
            }
            else {
                // No images in gallery folder so issue a WARNing as user will expect to see something.
                error = String.format("No images in gallery <%s>, gallery not generated", getName());
                logger.warn(error);
            }
        } else {
            // No gallery folder so issue a WARNing as user will be expecting to see a gallery.
            error = String.format("No folder for gallery <%s>, gallery not generated", getName());
            logger.warn(error);
        }
        initialiseMonitoringCollators();
        return true; //For now force html to be recalculated.
        // ToDo: Correctly return indicator that gallery has changed or not.
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

    private File[] getGalleryFileList() {
        logger.trace(String.format("Generating image file list for gallery <%s>", galleryName));

        if (!galleryRootFolder.exists() || !galleryRootFolder.isDirectory()) {
            // No folder so no gallery!  Update status to reflect.
            logger.warn("No gallery folder exists at <%s>", galleryRootFolder.toString());
            return null;
        } else {
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
        return getGalleryFileList().length;
    }

    public List<GalleryImage> getImageList() {
        List<GalleryImage> imageList = new ArrayList<>();
        File[] imageFileList = getGalleryFileList();

        /**
         * It's not necessarily an error if imageFileList is null, it may just means there are no images
         * so just return a zero length imageList.
         */
        if (imageFileList != null) {
            for (File galleryImage : imageFileList) {
                imageList.add(new GalleryImageDefault(galleryImage));
            }
        }
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
        return isModified;
    }

    public void updateGallery() {
        checkAndUpdateGallery();
    }

    /**
     * Returns HTML for Gallery.
     *
     * A call to this method will trigger:
     * - An increment of the number of requests for this gallery
     * - An update to the timeLastRequested field, which then enables...
     * - A check as to whether anything has been modified since last request
     *
     * This allows galleries to be fully dynamic, and reduces the need for state to be held for a gallery.
     *
     * @param forceRegenerate if true then html will be regenerated from scratch
     *
     * @return
     */
    public String getHtml(boolean forceRegenerate) {
        galleryStatus.incrementRequestCount();
        if (forceRegenerate || checkAndUpdateGallery()) {
            html = htmlGenerator.getHtml(galleryName, getImageList());
        }
        return html;
    }

    public boolean isHtmlGenerated() {
        return (!(html == null));
    }

    public GalleryStatus getGalleryStatus() {
        if (galleryStatus != null) {
            return galleryStatus;
        } else {
            return null;
        }
    }

    public boolean galleryFolderExists() {
        boolean exists = this.galleryRootFolder.exists();
        String error;

        if (!exists) {
            error = String.format("Gallery folder doesn't exist. Gallery name is <%s>, folder path is <%s>", getName(), this.galleryRootFolder.getPath());
            logger.error(error);
        }
        return exists;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public String toString() {
        return String.format("Gallery <%s>, numImages: <%d>", getName(), getNumberOfImages());
    }
}
