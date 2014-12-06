package uk.co.genonline.ldav03.model.gallery;

import org.apache.log4j.Logger;
import uk.co.genonline.ldav03.web.Html;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thomassecondary on 27/10/2014.
 */
public class Gallery {
    GalleryImageManagement imageManagement = new GalleryImageManagement();
    Logger logger = Logger.getLogger("");
    final int MAX_IMAGES = 100; // In case find folder with 1000s images in

    /**
     * Each Gallery object relates to a specific gallery with this name
     * ToDo: should Gallery be immutable? Probably not because I need the forceThumbnail flag to be modifiable.
     */
    private String galleryName;
    private String html;
    /**
     * Will be set by controller or other external process if config changes to re-generate thumbnails dynamically
     * without having to restart the webapp.
     */
    private boolean forceThumbnail;

    public Gallery(String galleryName) {
        this.galleryName = galleryName;
        forceThumbnail = false;
    }

    public String getHtml() {
        if (html != null && !html.isEmpty()) {
            logger.debug(String.format("Gallery:getHtml: returning pre-generated html : <%s>", html.substring(10)));
            return html;
        } else {
            imageManagement = new GalleryImageManagement();
            List<File> imageArray = imageManagement.getGalleryImageList(galleryName);
            if (imageArray == null) {
                logger.debug(String.format("Gallery:getHtml:No images found for gallery <%s>", galleryName));
                html = null;
            } else {
                html = createGalleryHtml(imageArray, forceThumbnail);
                logger.debug(String.format("Gallery:getHtml: returning generated html : <%s>", html.substring(10)));
            }
        }
        return html;
    }

    /**
     * Iterate through the list of images and:
     * - Don't check whether image exists as have generated listing from files which are there.
     * - Do check whether thumbnail exists and if not generate it.
     * - Also check forceThumbnail flag and re-generate thumbnail whether it exists or not
     *   (this will be used to allow dynamic recreation of thumbnails if configuration changes (e.g. thumbnail
     *   size) without having to re-start the app.
     * - Create the li-a-img html construct for each image.
     * - Construct the html for the gallery around this.
     *
     * @param imageList
     * @return
     */

    private String createGalleryHtml(List<File> imageList, boolean forceThumbnail) {
        Html htmlBuilder = new Html();
        List<String> liContent = new ArrayList<String>();
        GalleryPathManagement pathManagement = new GalleryPathManagement();
        ThumbnailManager thumbnailManager = new ThumbnailManager(new ThumbnailConfiguration());

        thumbnailManager.checkThumbnailFolder(pathManagement.getFullThumbnailDirPath(galleryName));

        Iterator<File> iterator = imageList.iterator();
        while (iterator.hasNext()) {
            String imageName = iterator.next().getName();

            // Generate full pathname for image and thumbnail to drive thumbnail creation (not URLs!)
            File thumbnailPath = pathManagement.getThumbnailFileFullPath(galleryName, imageName);
            File imagePath = pathManagement.getImageFileFullPath(galleryName, imageName);

            thumbnailManager.generateThumbnail(imagePath, thumbnailPath, forceThumbnail);

            // Generate URLs to hook into Rest Controller for gallery images to use in HTML (URLs)
            // Use thumbnail URL for the image on the screen and image URL as the href of the anchor
            // (This sometimes confuses!)

            String imgUrl = pathManagement.getImageUrl(galleryName, imageName);
            String thumbnailUrl = pathManagement.getThumbnailUrl(galleryName, imageName);
            String imgHtml = htmlBuilder.constructImgElement("", "", thumbnailUrl, "");
            String anchorHtml = htmlBuilder.constructAnchorElement("", "", imgUrl, imgHtml);
            liContent.add(anchorHtml);
        }

        String html = htmlBuilder.constructUlElement("gallery", "mainGallery", "", false, false, false, liContent);
        return html;
    }

    public boolean isForceThumbnail() {
        return forceThumbnail;
    }

    public void setForceThumbnail(boolean forceThumbnail) {
        this.forceThumbnail = forceThumbnail;
    }
}
