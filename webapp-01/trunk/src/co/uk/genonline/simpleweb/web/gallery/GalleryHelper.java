package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;
import org.apache.log4j.Level;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 15/07/2012
 * Time: 09:49
 *
 * Includes a number of utility methods which support the creation and management of galleries for the website.
 */

public class GalleryHelper {
    WebLogger logger = new WebLogger();

    ServletContext context;
    String webRootFullPath;

    private boolean forceGallery; // Determines whether to re-generate gallery even if exists
    private boolean forceThumbnails ; // Determines whether to re-generate thumbnails even if they exist
    private String galleryRootRelPath; // Path from web root to parent gallery root (e.g. gallery)
    private String thumbnailRelPath; // Path from a gallery's folder to its thumbnail folder (e.g. thumbnail)

    File galleryRootFullPathFile; // Full path to parent gallery root folder for file i/o operations
    File galleryThumbnailFullPathFile;

    private String contextPath; // Used to help in constructing URLs for some links in gallery

    int maxThumbnailWidth;
    int maxThumbnailHeight;
    int numGalleryColumns;

    public GalleryHelper(ServletContext context) {
        logger.setLevel(Level.toLevel(context.getInitParameter("loggingLevel")));

        this.context = context;
        contextPath = this.context.getContextPath();
        webRootFullPath = this.context.getRealPath("/");

        forceGallery = this.context.getInitParameter("forceGallery").equals("true");
        forceThumbnails = this.context.getInitParameter("forceThumbnail").equals("true");
        galleryRootRelPath = this.context.getInitParameter("galleryRoot");
        galleryRootFullPathFile = new File(webRootFullPath + File.separator + galleryRootRelPath);
        galleryThumbnailFullPathFile = new File(webRootFullPath + File.separator + thumbnailRelPath);
        thumbnailRelPath = this.context.getInitParameter("thumbnailRelPath");
        maxThumbnailHeight = Integer.parseInt(this.context.getInitParameter("maxThumbnailHeight"));
        if (maxThumbnailHeight <= 0) {
            logger.warn(String.format("Invalid value for 'maxHeight' (%s), setting to 100", this.maxThumbnailHeight));
            maxThumbnailHeight = 100;
        }
        this.maxThumbnailWidth = Integer.parseInt(this.context.getInitParameter("maxThumbnailWidth"));
        if (this.maxThumbnailWidth <= 0) {
            logger.warn(String.format("Invalid value for 'maxWidth' (%s), setting to 100", this.maxThumbnailWidth));
            maxThumbnailWidth = 100;
        }
        this.numGalleryColumns = Integer.parseInt(context.getInitParameter("numGalleryColumns"));
        if (this.numGalleryColumns <= 0) {
            logger.warn(String.format("Invalid value for 'numGalleryColumns' (%s), setting to 4", this.numGalleryColumns));
            numGalleryColumns = 4;
        }
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

    public int getMaxThumbnailWidth() {
        return maxThumbnailWidth;
    }

    public int getMaxThumbnailHeight() {
        return maxThumbnailHeight;
    }

    public String getThumbnailRelPath() {
        return thumbnailRelPath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public ServletContext getContext() {
        return context;
    }

    public boolean isForceThumbnails() {
        return forceThumbnails;
    }

    public boolean isForceGallery() {
        return forceGallery;
    }
}
