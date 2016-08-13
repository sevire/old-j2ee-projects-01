package co.uk.genonline.simpleweb.web.gallery;

import java.io.File;
import java.util.List;

/**
 * Created by thomassecondary on 20/04/15.
 *
 * Type used as utility to support the management of image galleries.  Includes utilities
 * to create thumbnails according to provide criteria and refresh the thumbnails if the underlying
 * image changes, for example.
 */
public interface ThumbnailManager {

    /**
     * Create a directory to place thumbnail images for a gallery
     *
     * @param galleryRootFolder The full path of the gallery folder under which to create a thumbnail folder.
     */
    public boolean createThumbnailFolder(File galleryRootFolder);

    /**
     *
     * @param galleryRootFolder The full path of the gallery folder under which to delete a thumbnail folder.
     */
    public void deleteThumbnailFolder(File galleryRootFolder);

    /**
     * Takes details of a gallery image and its folder, then creates a resized version of the corresponding image and places
     * it in the thumbnail folder for the given gallery.
     *
     * @param galleryRootFolder The full path of the gallery folder under which to create a thumbnail.
     * @param image Object containing details of the image (including its file location) used to locate file and create
     *              thumbnail.
     *
     * @return true if successful, false if error.
     */
    public boolean createThumbnail(File galleryRootFolder, GalleryImage image);

    /**
     *
     * Takes details of a gallery image and its folder, then deletes the thumbnail corresponding to that image if it is present.
     *
     * @param galleryRootFolder The full path of the gallery folder under which to delete a thumbnail.
     * @param image Object containting details of the image (including its file location) used to locate file and delete
     *              thumbnail.
     */
    public void deleteThumbnail(File galleryRootFolder, GalleryImage image);

    /**
     * Takes a list of gallery images and uses this to create a thumbnail for every image, in the location defined by the gallery
     * name.
     *
     * @param galleryRootFolder The full path of the gallery folder under which to create the thumbnails.
     * @param imageList
     *
     * @return Returns false if there is an error creating creating the thumbnails or other error which stops thumbnails
     *         being generated.
     */
    public boolean createAllThumbnails(File galleryRootFolder, List<GalleryImage> imageList);

    /**
     * Takes the root folder for a gallery, works out where the thumbnails are and deletes them all.  Note this method doesn't use a
     * list of images to work from, it just deletes all images in the folder.
     *
     * @param galleryRootFolder The full path of the gallery folder under which to create the thumbnails.
     */
    public void deleteAllThumbnails(File galleryRootFolder);

    /**
     * Takes the root folder for a gallery, and a list of images within the gallery, and deletes all the thumbnails for the images.
     *
     * @param galleryRootFolder
     * @param imageList
     */
    public void deleteAllThumbnails(File galleryRootFolder, List<GalleryImage> imageList);

}
