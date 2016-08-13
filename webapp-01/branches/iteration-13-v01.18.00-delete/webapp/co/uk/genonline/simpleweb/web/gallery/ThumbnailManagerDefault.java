package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class which handles the creation of a thumbnail folder and thumbnail files for galleries
 * Includes following functionality:
 * - Check existence of thumbnail folder and creates / recreates as appropriate
 * - Checks whether thumbnails have changed since last time called (this will save time and resources to recreate every time)
 * - Generates thumbnails for each file in the gallery, using supplied maximum height and width
 * Created by thomassecondary on 18/04/15.
 */
public class ThumbnailManagerDefault implements ThumbnailManager {
    WebLogger logger = new WebLogger();

    ImageHelper imageHelper = new ImageHelper();

    GalleryManagerConfiguration galleryManagerConfiguration;

    public ThumbnailManagerDefault(GalleryManagerConfiguration galleryManagerConfiguration) {
        if (galleryManagerConfiguration == null) {
            logger.error("Null value for GalleryManagerConfiguration");
            throw new NullPointerException("Null value for GalleryManagerConfiguration");
        } else {
            this.galleryManagerConfiguration = galleryManagerConfiguration;
        }
    }

    /**
     * Creates folder to hold the thumbnail images for a gallery.
     *
     * Conditions to deal with are:
     *
     * Case 1, File of same name as folder exists but isn't a folder: Abandon gallery (nothing we can do safely)
     * Case 2, Folder doesn't exist: Create it.
     * Case 3, Folder does exist: Do nothing
     *
     * @param galleryRootFolder The full path of the gallery folder under which to create a thumbnail folder.
     *                          NOTE: this is the specific folder for a specific gallery - not the root of all
     *                          galleries (this has caused confusion!).
     *
     * @return true if folder now exists (i.e. it already existed or we have successfully created it.
     *         false otherwise.
     */
    public boolean createThumbnailFolder(File galleryRootFolder) {

        // ToDo: Probably should just pass the name of the gallery as all the information is in the configuration object passed in

        File thumbnailFolderFullPath = new File(galleryRootFolder, galleryManagerConfiguration.getThumbnailRelPath());
        logger.trace(String.format("Creating thumbnail directory at <%s>", thumbnailFolderFullPath));

        if (thumbnailFolderFullPath.exists()) {
            if(!thumbnailFolderFullPath.isDirectory()) {
                // Case 1:
                logger.warn(String.format("Supplied thumbnail path <%s> exists but is a file (not a folder), abandoning thumbnail folder creation",
                        thumbnailFolderFullPath));
                return false;
            } else {
                // Case 3:
                logger.trace(String.format("Thumbnail path for <%s> exists, leave it", thumbnailFolderFullPath));
                return true; // Not an error that folder already exists
            }
        } else {
            // Case 2:
            logger.trace(String.format("Thumbnail path for <%s> doesn't exist, try to create", thumbnailFolderFullPath));
            if (!thumbnailFolderFullPath.mkdir()) {
                logger.error(String.format("Couldn't create thumbnail folder - abandon Gallery!"));
                return false;
            } else {
                return true;
            }
        }
    }

    public void deleteThumbnailFolder(File galleryRootFolder) {

        File thumbnailFolderFullPath = new File(galleryRootFolder, galleryManagerConfiguration.getThumbnailRelPath());
        logger.trace(String.format("Deleting thumbnail directory at <%s>", thumbnailFolderFullPath));

        deleteFolder(thumbnailFolderFullPath);
    }

    /**
     * @param galleryRootFolder The full path of the gallery folder.
     * @param galleryImage Object containing details of the image (including its file location) used to locate file and create
     */
    public boolean createThumbnail(File galleryRootFolder, GalleryImage galleryImage) {

        File image = new File(galleryRootFolder, galleryImage.getImageFullName());
        File thumbnailFullPath = new File(galleryRootFolder, galleryManagerConfiguration.getThumbnailRelPath());
        File thumbnail = new File(thumbnailFullPath, galleryImage.getImageFullName());
        logger.trace(String.format("Creating thumbnail at <%s>", thumbnailFullPath));

        BufferedImage bufferedImage = null;
        BufferedImage thumbnailImage = null;
        logger.debug(String.format("About to read image for resizing to thumbnail <%s>", image));

        boolean failed = false;

        try {
            bufferedImage = ImageIO.read(image);
        } catch (IOException e) {
            logger.error("Error reading image");
            e.printStackTrace();
            failed = true;
        }

        if (!failed) {
            if (bufferedImage == null) {
                logger.warn(String.format("Couldn't create thumbnail for image <%s>, not valid image perhaps", image.getName()));
            } else {
                int height = bufferedImage.getHeight();
                int width = bufferedImage.getWidth();
                float widthScaleFactor = galleryManagerConfiguration.getMaxThumbnailWidth() / (float) width;
                float heightScaleFactor = galleryManagerConfiguration.getMaxThumbnailHeight() / (float) height;

                float scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);

                thumbnailImage = imageHelper.getScaledInstance(bufferedImage, (int) (width * scaleFactor), (int) (height * scaleFactor), RenderingHints.VALUE_INTERPOLATION_BICUBIC, true);
                logger.debug(String.format("thumbnailImage is <%s>", thumbnailImage));

                try {
                    thumbnail.createNewFile();
                } catch (IOException e) {
                    logger.error("Error creating file for thumbnail");
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    failed = true;
                }

                if (!failed) {
                    logger.debug(String.format("About to write thumbnail file <%s>", thumbnail));
                    logger.debug(String.format("(File) thumbnail is <%s>", thumbnail));

                    try {
                        ImageIO.write(thumbnailImage, "jpg", thumbnail);
                        thumbnailImage.flush();
                    } catch (IOException e) {
                        logger.error("Error writing thumbnail");
                        e.printStackTrace();
                        failed = true;
                    }
                }
            }
        }
        return !failed;
    }

    public void deleteThumbnail(File galleryRootFolder, GalleryImage galleryImage) {
        File thumbnailFullPath = new File(galleryRootFolder, galleryManagerConfiguration.getThumbnailRelPath());
        File thumbnail = new File(thumbnailFullPath, galleryImage.getImageFullName());

        deleteFile(thumbnail);
    }

    /**
     * Creates all the thumbnails from scratch from the list of images read from the image folder.  Begins by deleting
     * all thumbnails to ensure that they are up to date.  This is necessary because as we have just created the gallery
     * so can't check whether it has been modified since last time.
     *
     * There are potentially more intelligent ways of deleting the thumbnails to avoid a situation where we delete all
     * the files and then run into an error which stops us re-creating them.  For example we could just delete the file
     * before re-creating it so we have the opportunity to abandon if we need to.  This approach would potentially
     * leave some old thumbnails lying around for images which have been deleted.  For now take the blunt approach
     * of deleting all the files in the folder and come back to it if we need to.
     *
     * Have taken a relaxed approach to error checking as it is unlikely that we would get this far into the code without
     * having generated an error for null values etc.  Hope this is right :-)
     *
     * @param galleryRootFolder The full path of the gallery folder under which to create the thumbnails.
     * @param imageList
     */
    public boolean createAllThumbnails(File galleryRootFolder, List<GalleryImage> imageList) {
        deleteAllThumbnails(galleryRootFolder);
        for (GalleryImage thumbnailImage : imageList) {
            if(!createThumbnail(galleryRootFolder, thumbnailImage)) {
                return false;
            }
        }
        return true;
    }

    public void deleteAllThumbnails(File galleryRootFolder) {
        File thumbnailFullPath = new File(galleryRootFolder, galleryManagerConfiguration.getThumbnailRelPath());
        try {
            FileUtils.cleanDirectory(thumbnailFullPath);
        } catch (IOException e) {
            logger.error(String.format("Error trying to delete all thumbnails in <%s>", thumbnailFullPath));
            e.printStackTrace();
        }
    }

    public void deleteAllThumbnails(File galleryRootFolder, List<GalleryImage> imageList) {
        for (GalleryImage thumbnailImage : imageList) {
            deleteThumbnail(galleryRootFolder, thumbnailImage);
        }
    }

    /**
     * Utility method to delete a directory.  May need to move this somewhere else.
     *
     * @param folderFullPath
     */
    private void deleteFolder(File folderFullPath) {
        try {
            logger.trace(String.format("Attempting to delete existing thumbnail folder <%s>", folderFullPath));
            FileUtils.deleteDirectory(folderFullPath);
        } catch (IOException e) {
            logger.error(String.format("Couldn't delete thumbnail folder <%s>", folderFullPath));
        }
    }

    /**
     * Utility method to delete a file.  May need to move this somewhere else.
     *
     * @param fileFullPath
     */
    private void deleteFile(File fileFullPath) {
        // Make sure the file exists and isn't a directory
        if (!fileFullPath.exists()) {
            logger.warn("Trying to delete non-existant file: %s", fileFullPath.toString());
        } else if (fileFullPath.isDirectory()) {
            logger.warn("Trying to delete file that is a directory: %s", fileFullPath.toString());
        } else if (!fileFullPath.canWrite()) {
            logger.error("Trying to delete file without permissions: %s", fileFullPath.toString());
            throw new IllegalArgumentException("Delete: write protected: " + fileFullPath);
        } else {

            // Attempt to delete it
            boolean success = fileFullPath.delete();

            if (!success) {
                throw new IllegalArgumentException("Delete: deletion failed");
            }
        }
    }
}
