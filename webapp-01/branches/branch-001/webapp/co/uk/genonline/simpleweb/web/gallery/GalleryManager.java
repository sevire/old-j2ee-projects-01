package co.uk.genonline.simpleweb.web.gallery;

import javax.servlet.ServletContext;
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

    private final GalleryHelper helper;

    private final Map<String, Gallery> galleries;

    private File galleryFullPathFile; // Used in IO operations
    private String galleryRelPath; // Used for references in HTML (<a> and <img>)

    public GalleryManager(ServletContext context) {
        helper = new GalleryHelper(context);
        galleries = new HashMap<String, Gallery>();
    }

    void addGallery(String galleryName) {
        galleries.put(galleryName, new Gallery(helper, galleryName));
    }

    public Gallery getGallery(String galleryName) {
        Gallery gallery = galleries.get(galleryName);
        if (gallery == null) {
            // This is first call so create gallery

            addGallery(galleryName);
            gallery = galleries.get(galleryName); // Should always work at this point
        }
        return gallery;
    }


}
