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
    File galleryThumbnailFullPathFile;
    private String galleryRootRelPath; // Path from web root to parent gallery root (e.g. gallery)
    private String thumbnailRelPath; // Path from a gallery's folder to its thumbnail folder (e.g. thumbnail)

    private String contextPath; // Used to help in constructing URLs for some links in gallery

    int maxWidth;
    int maxHeight;
    int numGalleryColumns;

    public GalleryHelper(File webRootFullPath,
                         String contextPath,
                         String galleryRootRelPath,
                         String thumbnailRelPath,
                         int maxHeight,
                         int maxWidth,
                         int numGalleryColumns) {
        this.galleryRootFullPathFile = new File(webRootFullPath + File.separator + galleryRootRelPath);
        this.galleryThumbnailFullPathFile = new File(webRootFullPath + File.separator + thumbnailRelPath);
        this.contextPath = contextPath;
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

    File getThumbnailDirFullPathFile(String galleryName) {
        return new File(getGalleryFullPathFile(galleryName), File.separator + thumbnailRelPath);
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

    public String getContextPath() {
        return contextPath;
    }
}
