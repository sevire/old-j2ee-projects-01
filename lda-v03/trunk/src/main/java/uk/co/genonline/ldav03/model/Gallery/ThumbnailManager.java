package uk.co.genonline.ldav03.model.Gallery;

import java.io.File;

/**
 * Manages the creation, refreshing and placement of thumbnails (principally relating to gallery images).
 * */
public class ThumbnailManager {
    private ThumbnailConfiguration config;

    public ThumbnailManager(ThumbnailConfiguration config) {

        this.config = config;
    }

    /**
     *
     * @param image File object containing the image to be thumbnailed
     * @param thumbnail
     */
    public void generateThumbnail(File image, File thumbnail) {

    }
}
