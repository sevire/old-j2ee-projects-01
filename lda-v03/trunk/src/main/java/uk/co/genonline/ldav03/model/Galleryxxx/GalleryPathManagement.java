package uk.co.genonline.ldav03.model.Galleryxxx;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * Class to provide path information to classes that need to deal with gallery
 * images.
 *
 * Not sure whether this is the right way to do this - it should be done through configuration but this will do
 * for the current sprint / epic.
  */
public class GalleryPathManagement {
    Logger logger = Logger.getLogger("");

    //ToDo: Extract the constants out to a configuration module of some sort
    public static final String IMAGE_URL_BASE = "/galleryimage";
    public static final String BASE_PATH = "/Users/thomassecondary/Documents/Princess/Website/webgalleries";
    public static final String THUMBNAIL_FOLDER_NAME = "thumbnails";

    public File getFullImageDirPath(String galleryName) {
        logger.info(String.format("getFullImageDirPath(%s)", galleryName));
        String fullPath = BASE_PATH + File.separator + galleryName;
        File file = new File(fullPath);
        logger.debug(String.format("File is <%s>", file));

        return file;
    }

    public File getFullThumbnailDirPath(String galleryName) {
        logger.info(String.format("getFullThumbnailDirPath(%s)", galleryName));

        File file = new File(getFullImageDirPath(galleryName).getPath() + File.separator + THUMBNAIL_FOLDER_NAME);

        logger.debug(String.format("File is <%s>", file));
        return file;
    }

    public File getImageFileFullPath(String galleryName, String imageName) {
        return new File (getFullImageDirPath(galleryName) + File.separator + imageName);
    }

    public File getThumbnailFileFullPath(String galleryName, String imageName) {
        return new File (getFullThumbnailDirPath(galleryName) + File.separator + imageName);
    }

    public String getImageDirUrl(String galleryName) {
        return IMAGE_URL_BASE + File.separator + galleryName;
    }

    public String getThumbnailDirURL(String galleryName) {
        return IMAGE_URL_BASE + File.separator + galleryName + File.separator + THUMBNAIL_FOLDER_NAME;
    }

    public String getImageUrl(String galleryName, String imageName) {
        return getImageDirUrl(galleryName) + File.separator + imageName;
    }

    public String getThumbnailUrl(String galleryName, String imageName) {
        return getThumbnailDirURL(galleryName) + File.separator + imageName;
    }

    public String toString() {
        return String.format("URL:%s, BASE:%s, THUMB:%s", IMAGE_URL_BASE, BASE_PATH, THUMBNAIL_FOLDER_NAME);
    }
}
