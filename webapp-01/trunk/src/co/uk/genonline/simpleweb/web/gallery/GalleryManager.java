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

    private GalleryHelper helper;

    private Map<String, Gallery> galleries;

    private File galleryFullPathFile; // Used in IO operations
    private String galleryRelPath; // Used for references in HTML (<a> and <img>)

/*
    private String webRootFullPath;
    private String galleryRoot;
*/

    public GalleryManager(String webRoot, String galleryRelPath) {
        helper = new GalleryHelper(new File(webRoot),
                             galleryRelPath,
                             THUMBNAIL_RELPATH,
                             THUMBNAIL_MAX_DIM,
                             THUMBNAIL_MAX_DIM,
                             GALLERY_COLUMNS);
        galleries = new HashMap<String, Gallery>();

        // For now add gallery manually for testing

        addGallery("chambers");
    }

    public void addGallery(String galleryName) {
        galleries.put(galleryName, new Gallery(helper, galleryName));
    }

    public Gallery getGallery(String galleryName) {
        return galleries.get(galleryName);
    }


}
