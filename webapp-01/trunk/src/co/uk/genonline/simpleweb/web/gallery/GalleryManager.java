package co.uk.genonline.simpleweb.web.gallery;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/07/2012
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class GalleryManager {
    private static final String THUMBNAIL_RELPATH = "thumbnails";
    private static final int THUMBNAIL_MAX_DIM = 130;
    private static final int GALLERY_COLUMNS = 4;

    private Map<String, Gallery> galleries;
    private String webRoot;
    private String galleryRoot;

    public GalleryManager(String webRoot, String galleryRoot) {
        this.webRoot = webRoot;
        this.galleryRoot =  galleryRoot;
        galleries = new HashMap<String, Gallery>();

        // For now add gallery manually for testing

        addGallery("chambers");
    }

    public void addGallery(String galleryName) {
        galleries.put(galleryName, new Gallery(
                webRoot,
                galleryRoot+File.separator+galleryName,
                galleryRoot+File.separator+galleryName+File.separator+THUMBNAIL_RELPATH,
                galleryName,
                THUMBNAIL_MAX_DIM,
                THUMBNAIL_MAX_DIM,
                GALLERY_COLUMNS));
    }

    public Gallery getGallery(String galleryName) {
        return galleries.get(galleryName);
    }


}
