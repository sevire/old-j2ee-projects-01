package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
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
     * Case 3, Folder does exist: All good.
     *
     * @param galleryRootFolder The full path of the gallery folder under which to create a thumbnail folder.
     *                          NOTE: this is the specific folder for a specific gallery - not the root of all
     *                          galleries (this has caused confusion!).
     *
     * @return true if folder now exists (i.e. it already existed or we have successfully created it.
     *         false otherwise.
     */
    public boolean createThumbnailFolder(File galleryRootFolder) {

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

    private File getFullPathForImageFile(File galleryRootFolder, GalleryImage galleryImage) {
        return new File(galleryRootFolder, galleryImage.getImageFullName());
    }

    private File getThumbnailFolderFullPathFile(File galleryRootFolder) {
        return new File(galleryRootFolder, galleryManagerConfiguration.getThumbnailRelPath());
    }

    private File thumbnailFullPathFile(File galleryRootFolder, GalleryImage image) {
        return new File (getThumbnailFolderFullPathFile(galleryRootFolder), image.getImageFullName());
    }

    /**
     * Gets a list of all the image files in the thumbnail folder.
     *
     * @param galleryRootFolder
     * @return
     */
    private List<File> getAllThumbnails(File galleryRootFolder) {
        FileFilter filter = new ImageFileFilter(galleryManagerConfiguration.getImageExtensionList());
        File thumbnailFullPath = getThumbnailFolderFullPathFile(galleryRootFolder);
        logger.debug("Looking for thumbnails with these extensions: <%s>", (galleryManagerConfiguration.getImageExtensionList()).toString());
        logger.debug(String.format("About to list files in <%s>", galleryRootFolder));

        File[] list = thumbnailFullPath.listFiles(filter);
        if (list == null) {
            logger.warn(String.format("No image files found in <%s>", galleryRootFolder));
            return null;
        } else {
            logger.debug(String.format("Found <%d> files in <%s>", list.length, galleryRootFolder));
            return Arrays.asList(list);
        }
    }

    public boolean validThumbnailExists(File galleryRootFolder, GalleryImage image) {
        File thumbnailFullPathFile = thumbnailFullPathFile(galleryRootFolder, image);
        if (thumbnailFullPathFile.exists()) {
            // Thumbnail exists but need to check whether it was created after the corresponding image.  If not then it
            // isn't valid.

            long imageModified = image.getImageFullPath().lastModified(); // ToDo Work out what to do with GalleryImage.getLastModified() method as may not be useful
            long thumbnailModified = thumbnailFullPathFile.lastModified();

            if (imageModified >= thumbnailModified) {
                // Image has been modified since thumbnail was created so thumbnail invalid.  Return false
                return false;
            } else {
                return true;
            }
        } else {
            // Thumbnail file for given image doesn't exist, so return false
            return false;
        }
    }

    /**
     *
     * 16/7/2017 Re-write.
     *
     * In order to make the galleries more dynamic, the intention is to go through the whole create gallery sequence
     * every time but only generate when there has been an update since the last generation.  So for thumbnails I
     * need to:
     *
     * [1] Go through every Gallery Image and...
     *     - Check whether there is a thumbnail.  If not create it.
     *     - If there is a thumbnail, check whether the thumbnail was modified after the image was created.  If not
     *       then it is out of date so needs to be created.
     *
     * [2] Go through every Thumbnail Image and...
     *     - Check whether there is still a Gallery Image of the same name.  If not then this is an orphan thumbnail
     *       and can be deleted.
     *
     * @param galleryRootFolder The full path of the gallery folder under which to create the thumbnails.
     * @param imageList
     */
    public void checkAndCreateThumbnails(File galleryRootFolder, List<GalleryImage> imageList) {

        // [1] Ensure there is an up to date thumbnail for each image.
        for(GalleryImage image : imageList) {
            if (!validThumbnailExists(galleryRootFolder, image)) {
                logger.info("Gallery image <%s> has been created/modified, regenerating thumbnail", image.getImageFullName());
                createThumbnail(galleryRootFolder, image);
            }
        }

        // [2] Ensure that any orphan thumbnails are deleted.
        List<File> thumbnails = getAllThumbnails(galleryRootFolder);
        for (File thumbnail : thumbnails) {
            String thumbnailName = thumbnail.getName(); // Not sure whether this gets just the filename or the full path.  Need filename only.
            if (!imageExistsInGallery(thumbnailName, imageList)) {
                logger.info("Orphan thumbnail for image <%s>, deleting", thumbnail.toString());
                deleteFile(thumbnail);
            }
        }
    }

    private boolean imageExistsInGallery(String imageName, List<GalleryImage> galleryImageList) {

        int index = 0;

        while (index < galleryImageList.size() && !galleryImageList.get(index).getImageName().equals(imageName)) index++;
        if (index > galleryImageList.size()) {
            return false;
        } else {
            return true;
        }
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
