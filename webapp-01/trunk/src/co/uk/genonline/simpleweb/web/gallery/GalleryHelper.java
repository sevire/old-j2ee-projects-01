package co.uk.genonline.simpleweb.web.gallery;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 15/07/2012
 * Time: 09:49
 * To change this template use File | Settings | File Templates.
 */

public class GalleryHelper {
    File galleryRootFullPathFile; // Full path to parent gallery root folder for file i/o operations
    private String galleryRootRelPath; // Path from web root to parent gallery root (e.g. gallery)
    private String thumbnailRelPath; // Path from a gallery's folder to its thumbnail folder (e.g. thumbnail)

    int maxWidth;
    int maxHeight;
    int numGalleryColumns;

    public GalleryHelper(File webRootFullPath,
                         String galleryRootRelPath,
                         String thumbnailRelPath,
                         int maxHeight,
                         int maxWidth,
                         int numGalleryColumns) {
        this.galleryRootFullPathFile = new File(webRootFullPath + File.separator + galleryRootRelPath);
        this.galleryRootRelPath = galleryRootRelPath;
        this.thumbnailRelPath = thumbnailRelPath;
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.numGalleryColumns = numGalleryColumns;
    }

    public String getGalleryRootRelPath() {
        return galleryRootRelPath;
    }

    public int getNumGalleryColumns() {
        return numGalleryColumns;
    }

    File getGalleryFullPathFile(String galleryName) {
        return new File(galleryRootFullPathFile, File.separator + galleryName);
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public String getThumbnailRelPath() {
        return thumbnailRelPath;
    }
}
