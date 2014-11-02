package uk.co.genonline.ldav03.model.Gallery;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by thomassecondary on 29/10/2014.
 */
public class GalleryImageManagement {
    @Autowired
    GalleryPathManagement galleryPathManagement;

    File[] getGalleryImageList(String galleryName) {
        String[] extensions = {"jpg", "png", "jpeg"};
        FileFilter filter = new ImageFileFilter(extensions);
        File list[] = galleryPathManagement.getFullPath(galleryName).listFiles(filter);
        return list;
    }
}
