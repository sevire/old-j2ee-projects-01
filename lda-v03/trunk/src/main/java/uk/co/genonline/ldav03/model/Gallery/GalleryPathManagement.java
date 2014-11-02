package uk.co.genonline.ldav03.model.Gallery;

import java.io.File;

/**
 * Class to provide path information to classes that need to deal with gallery
 * images.
 *
 * Not sure whether this is the right way to do this - it should be done through configuration but this will do
 * for the current sprint / epic.
  */
public class GalleryPathManagement {
    //ToDo: Extract the constants out to a configuration module of some sort
    public static final String IMAGE_URL_BASE = "/galleryimage";
    public static final String BASE_PATH = "/Users/thomassecondary/Documents/Princess/Website/webgalleries";

    public File getFullPath(String galleryName) {
        String fullPath = BASE_PATH + File.separator + galleryName;
        File file = new File(fullPath);

        return file;

    }
}
