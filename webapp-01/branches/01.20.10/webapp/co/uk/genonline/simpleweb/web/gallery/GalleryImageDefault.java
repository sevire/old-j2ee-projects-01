package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by thomassecondary on 20/04/15.
 *
 * Encapsulates properties of an image used by gallery functionality.
 */
public class GalleryImageDefault implements GalleryImage {
    WebLogger logger = new WebLogger();
    private final File imageFullPathFile;

    public GalleryImageDefault(File galleryImage) {
        this.imageFullPathFile = galleryImage;
    }

    private BufferedImage getBufferedImage(File imageFile) {
        BufferedImage bufferedImage = null;
        boolean abandon = false;
        try {
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            logger.error(String.format("Error reading image <%s>", imageFile));
            e.printStackTrace();
            abandon = true;
        }
        if (abandon) {
            return null;
        } else {
            return bufferedImage;
        }
    }

    public String getImageFullName() {
        return imageFullPathFile.getName();
    }

    public String getImageName() {
        return getFilenameNoExtension(getImageFullName());
    }

    public String getImageExtension() {
        return getFileExtension(getImageFullName());
    }

    public int getImageWidth() {
        return getBufferedImage(getImageFullPath()).getWidth();
    }

    public int getImageHeight() {
        return getBufferedImage(getImageFullPath()).getHeight();
    }

    public File getImageFullPath() {
        if (getBufferedImage(imageFullPathFile) == null) {
            return null;
        } else {
            return imageFullPathFile;
        }
    }

    /**
     * Check whether image has been modified.  Look for:
     * - Image no longer exists
     * - Date/Time modified has changed
     * @return
     */
    public boolean isModified() {
        boolean isModified = false;
        boolean imageExists = getImageFullPath().exists();
        boolean imageIsFile = getImageFullPath().isFile();

        if (!(imageExists && imageIsFile)) {
            isModified = true;
        } else {
            long imageLastModified = getImageFullPath().lastModified();
            if (imageLastModified != getLastModified()) {
                isModified = true;
            }
        }
        return isModified;
    }

    public long getLastModified() {
        return getImageFullPath().lastModified();
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String lastModifiedString = simpleDateFormat.format(getLastModified());
        return String.format("<%s>:<%d>:<%d>:<%s>", getImageFullName(), getImageHeight(), getImageWidth(), lastModifiedString);
    }


}
