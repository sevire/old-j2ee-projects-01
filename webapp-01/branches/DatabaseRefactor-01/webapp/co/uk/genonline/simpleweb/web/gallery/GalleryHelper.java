package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.configuration.configitems.*;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.WebLogger;

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
    Configuration configuration;
    String webRootFullPath;

    private String galleryRoot; // Path from web root to parent gallery root (e.g. gallery)
    private String thumbnailRelPath; // Path from a gallery's folder to its thumbnail folder (e.g. thumbnail)

    File galleryRootFullPathFile; // Full path to parent gallery root folder for file i/o operations
    File galleryThumbnailFullPathFile;

    private String contextPath; // Used to help in constructing URLs for some links in gallery

    public GalleryHelper(ServletContext context) {
        logger.debug("GalleryHelper: Constructor Started");

        this.context = context;
        this.configuration = (Configuration)context.getAttribute("configuration");
        contextPath = this.context.getContextPath();
        webRootFullPath = this.context.getRealPath("/");

        galleryRoot = ((GalleryRoot)configuration.getConfigurationItem("galleryRoot")).get();
        galleryRootFullPathFile = new File(webRootFullPath + File.separator + galleryRoot);
        galleryThumbnailFullPathFile = new File(webRootFullPath + File.separator + thumbnailRelPath);
        thumbnailRelPath = ((ThumbnailRelPath)configuration.getConfigurationItem("thumbnailRelPath")).get();
        logger.debug("GalleryHelper: Constructor Complete");
    }

    public String getGalleryRootRelPath() {
        return galleryRoot;
    }

    File getGalleryFullPathFile(String galleryName) {
        return new File(galleryRootFullPathFile, File.separator + galleryName);
    }

    File getThumbnailDirFullPathFile(String galleryName) {
        return new File(getGalleryFullPathFile(galleryName), File.separator + thumbnailRelPath);
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
}
