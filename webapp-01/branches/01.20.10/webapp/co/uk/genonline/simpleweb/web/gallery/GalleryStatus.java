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
     * Keep track of number of times gallery is requested (through calls to getHtml()).
     */
    Integer numRequests;

    Gallery gallery;

    public GalleryStatus() {

    }

    public GalleryStatus(Gallery gallery) {
        this.gallery = gallery;
        reset();
    }

    public void reset() {
        this.numRequests = 0;
    }

    public boolean isFolderExists() {
        return gallery.galleryFolderExists();
    }

    public int getNumImages() {
        return gallery.getNumberOfImages();
    }

    /**
     * Set time created to now (i.e. when method is called).
     */
    public String getTimeCreated() {
        return gallery.getTimeCreated().toString();
    }

    protected void incrementRequestCount() {
            this.numRequests++;
    }

    public int getNumRequests() {
        return this.numRequests;
    }

    public boolean isHtmlGenerated() {
        return (gallery.isHtmlGenerated());
    }

    public String toString() {
        return String.format(
                        "Num Requests: %d<br>" +
                        "Time Created: %s<br>" +
                        "Folder Exists: %b<br>" +
                        "Num Images: %d<br>" +
                getNumRequests(), getTimeCreated().toString(), isFolderExists(), getNumImages());
    }


    @XmlElement
    @Override
    public Map<String, String> getDisplayData() {
        return Collections.unmodifiableMap(new HashMap<String, String>() {
            {
                put("folderExists",((Boolean)isFolderExists()).toString());
                put("numImages",((Integer)getNumImages()).toString());
                put("timeCreated",getTimeCreated());
                put("numRequests",numRequests.toString());
                put("htmlGenerated",Boolean.toString(isHtmlGenerated()));
            }
        });
    }
}
