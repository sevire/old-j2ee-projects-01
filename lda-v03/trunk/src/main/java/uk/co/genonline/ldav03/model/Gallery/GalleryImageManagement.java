package uk.co.genonline.ldav03.model.Gallery;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by thomassecondary on 29/10/2014.
 */
public class GalleryImageManagement {
    Logger logger = Logger.getLogger("");
    GalleryPathManagement galleryPathManagement = new GalleryPathManagement();

    List<File> getGalleryImageList(String galleryName) {
        logger.debug(String.format("galleryPathManagement = %s", galleryPathManagement));
        String[] extensions = {"jpg", "png", "jpeg"};
        FileFilter filter = new ImageFileFilter(extensions);
        File[] fileList = galleryPathManagement.getFullImageDirPath(galleryName).listFiles(filter);
        List<File> list;

        if (fileList == null) {
            // A gallery is configured but there is no folder for the images. Log error but don't flag anything on web
            // page.

            logger.warn(String.format("Gallery configured for page %s but no gallery folder or no images", galleryName));
            list = null;
        } else {
            list = Arrays.asList(fileList);
            logger.info(String.format("Number of images found in %s is %d", galleryName, list.size()));
        }
        return list;
    }
}
