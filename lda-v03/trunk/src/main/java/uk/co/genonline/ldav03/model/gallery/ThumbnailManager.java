package uk.co.genonline.ldav03.model.gallery;

import net.coobird.thumbnailator.Thumbnailator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Manages the creation, refreshing and placement of thumbnails (principally relating to gallery images).
 * */
public class ThumbnailManager {
    Logger logger = Logger.getLogger("");
    private ThumbnailConfiguration config;

    public ThumbnailManager(ThumbnailConfiguration config) {

        this.config = config;
    }

    /**
     * Works out whether to generate a thumbnail for an Image.
     *
     * If forceThumbnail is set, then create a new thumbnail regardless of other factors.
     * If not, then check whether there is already a thumbnail for this image, and only create one if not.
     *
     * If a thumbnail is required, then need to check whether thumbnail folder exists and if not create it.
     *
     * @param image
     * @param thumbnail
     * @param forceThumbnail
     */
    public void generateThumbnail(File image, File thumbnail, boolean forceThumbnail) {
        logger.debug(String.format("ThumbnailManager:generateThumbnail: image:%s, thumbnail:%s", image.getPath(), thumbnail.getPath()));
        if (forceThumbnail || !thumbnail.exists()) {
            logger.debug("Generating thumbnail");
            try {
                Thumbnailator.createThumbnail(image, thumbnail, config.getThumbnailMaxHeight(), config.getThumbnailMaxWidth());
            } catch (IOException e) {
                logger.warn(String.format("Error while trying to create thumbnail for <%s>, error is <%s>", image.getName(), e.getMessage()));
                // ?? e.printStackTrace();
            }
        }
    }

    /**
     * Checks whether thumbnail path exists and creates if not
     * Todo: Do I need to refactor the Gallery / Thumbnail stuff - make sure everything is in the right place?
     * @param galleryFullThumbnailDirPath
     */
    public void checkThumbnailFolder(File galleryFullThumbnailDirPath) {
        if (!galleryFullThumbnailDirPath.exists()) {
            galleryFullThumbnailDirPath.mkdir();
        }
    }
}
