package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.monitoring.collectables.CollectableDataObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomassecondary on 01/05/15.
 *
 * Maintains status for a number of different attributes of a Gallery, to help with Monitoring and control.
 *
 * Following status information is maintained:
 *
 * - Folder Exists Flag : Set if there is a folder for this Gallery
 * - Number Of Images : The number of image files which were found in the folder
 * - Thumbnails Generated Flag : Set if a thumbnail folder has been generated for this Gallery
 * - Gallery Modified Flag : Set if the Gallery has been modified since last time thumbnails were generated
 */

@XmlRootElement(name="displayData")
public class GalleryStatus extends CollectableDataObject {

    /**
     * Error Status: If errors occurred during the creation of a gallery, then true.  This will help later on when trying to
     * display galleries.
     */
    Boolean galleryError;

    /**
     * Error Status: During creation, if the folder associted with the gallery doesn't exist, then this will be set to true, and
     * so will galleryError, as the gallery is unusable.
     */
    Boolean folderExists;

    /**
     * General Status: It will allow for lazy instantiation of thumbnails. Set to
     * true if the thumbnails have been successfully generated for this gallery.
     */
    Boolean thumbnailGenerated;

    /**
     * General Status: If (last time it was checked) the gallery had been modified since thumbnails have been generated, then
     * set to true.
     */
    Boolean galleryModified;

    /**
     * General Status: The number of images within this gallery.
     */
    Integer numImages;

    /**
     * Time at which the gallery was created.
     */
    LocalDateTime timeCreated;

    /**
     * Keep track of number of times gallery is requested (through calls to getHtml()).
     */
    Integer numRequests;

    /**
     * Indicates whether HTML has been generated for this gallery yet
     */
    Boolean htmlGenerated;

    public GalleryStatus() {
        reset();
    }

    public void reset() {
        this.galleryError = false;
        this.folderExists = false;
        this.numImages = 0;
        this.thumbnailGenerated = false;
        this.galleryModified = false;
        this.timeCreated = LocalDateTime.now();
        this.numRequests = 0;
        this.htmlGenerated = false;
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

    /**
     * Set time created to now (i.e. when method is called).
     */
    public String getTimeCreated() {
        return this.timeCreated.toString();
    }

    public void setHtmlGenerated(Boolean htmlGenerated) {
        this.htmlGenerated = htmlGenerated;
    }

    public boolean isHtmlGenerated() {
        return this.htmlGenerated;
    }

    protected void incrementRequestCount() {
            this.numRequests++;
    }

    public int getNumRequests() {
        return this.numRequests;
    }

    public String toString() {
        return String.format(
                        "Num Requests: %d<br>" +
                        "Time Created: %s<br>" +
                        "Error: %b<br>" +
                        "Folder Exists: %b<br>" +
                        "Num Images: %d<br>" +
                        "Thumbnails Generated?: %b<br>" +
                        "Gallery Modified?: %b<br>",
                getNumRequests(), getTimeCreated().toString(), isGalleryError(), isFolderExists(), getNumImages(), isThumbnailGenerated(), isGalleryModified());
    }


    @XmlElement
    @Override
    public Map<String, String> getDisplayData() {
        return Collections.unmodifiableMap(new HashMap<String, String>() {
            {
                put("galleryError",galleryError.toString());
                put("folderExists",folderExists.toString());
                put("numImages",numImages.toString());
                put("thumbnailGenerated",thumbnailGenerated.toString());
                put("galleryModified",galleryModified.toString());
                put("timeCreated",timeCreated.toString());
                put("numRequests",numRequests.toString());
                put("htmlGenerated",htmlGenerated.toString());
            }
        });
    }
}
