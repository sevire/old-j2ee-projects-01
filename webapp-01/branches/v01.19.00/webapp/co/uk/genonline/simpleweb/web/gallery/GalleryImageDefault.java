package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by thomassecondary on 20/04/15.
 *
 * Encapsulates properties of an image used by gallery functionality.
 */
public class GalleryImageDefault implements GalleryImage {
    WebLogger logger = new WebLogger();
    private final String imageFullName;
    private final int imageWidth;
    private final int imageHeight;
    private final File imageFullPath;
    private final long dateTimeLastModified;

    public GalleryImageDefault(File galleryImage) {
        this.imageFullName = galleryImage.getName();
        BufferedImage bufferedImage = null;
        boolean abandon = false;
        try {
            bufferedImage = ImageIO.read(galleryImage);
        } catch (IOException e) {
            logger.error(String.format("Error reading image <%s>", galleryImage));
            e.printStackTrace();
            abandon = true;
        }

        if (!abandon) {
            this.imageWidth = bufferedImage.getWidth();
            this.imageHeight = bufferedImage.getHeight();
            this.imageFullPath = galleryImage;
            this.dateTimeLastModified = imageFullPath.lastModified();
        } else {
            this.imageWidth = 0;
            this.imageHeight = 0;
            this.imageFullPath = null;
            this.dateTimeLastModified = 0;
        }
    }


    public String getImageFullName() {
        return imageFullName;
    }

    public String getImageName() {
        return getFilenameNoExtension(imageFullName);
    }

    public String getImageExtension() {
        return getFileExtension(imageFullName);
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public File getImageFullPath() {
        return imageFullPath;
    }

    /**
     * Check whether image has been modified.  Look for:
     * - Image no longer exists
     * - Date/Time modified has changed
     * @return
     */
    public boolean isModified() {
        boolean isModified = false;
        boolean imageExists = imageFullPath.exists();
        boolean imageIsFile = imageFullPath.isFile();

        if (!(imageExists && imageIsFile)) {
            isModified = true;
        } else {
            long imageLastModified = imageFullPath.lastModified();
            if (imageLastModified != dateTimeLastModified) {
                isModified = true;
            }
        }
        return isModified;
    }

    public long getLastModified() {
        return dateTimeLastModified;
    }

    /**
     * Defines the ordering between two GalleryImage objects to allow sorting.  The order is (currently) hard-coded
     * to use the full name of the file, but could be changes to use other sort criteria such as modified date/time.
     *
     * @param image
     * @return
     */
    public int compareTo(GalleryImage image) {
        return getImageFullName().compareTo(image.getImageFullName());
    }

    private String getFileExtension(String fullName) {
        // ToDo: getFileExtension: Check all possibilities (e.g. what if no extension)
        return fullName.substring(fullName.lastIndexOf(".")+1,fullName.length());
    }

    private String getFilenameNoExtension(String fullName) {
        // ToDo: getFilenameNoExtension: Check all possibilities (e.g. what if no extension)
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    public String toString() {
        return String.format("<%s>:<%d>:<%d>:<%d>", getImageFullName(), getImageHeight(), getImageWidth(), getLastModified());
    }


}
