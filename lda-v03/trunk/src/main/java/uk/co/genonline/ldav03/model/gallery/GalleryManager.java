package uk.co.genonline.ldav03.model.gallery;

/**
 * Defines operations required to manage a set of folders representing galleries,
 * and the images within the folders.  There are operations to:
 * - Create a gallery folder
 * - Delete a gallery folder
 * - Create and return a Gallery object to manage the images within a gallery folder.
 * - Return a list of Galleries (e.g. for use in a gallery administration screen.
 *
 *
 */
public interface GalleryManager {
    public void createGallery(String galleryName);

    public void deleteGallery(String galleryName);

    public Gallery getGallery(String galleryName);
}
