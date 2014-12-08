/**
 * Functionality to manage the folders and images related to web galleries.
 * <p>
 * A set of galleries to be managed as a unit has the following properties:
 * <p><ul>
 * <li>A base folder underneath which all the gallery folders sit.
 * <ul>
 *     <li>Underneath each gallery folder, the following structure will exist:
 *     <li> original images folder: This will contain exact copies of images uploaded for a gallery.
 *       Note: All images to be displayed will be resized to ensure all images for display on a client browser are
 *       less than a specified maximum size.
 *     <li> thumbnails folder: Contains thumbnails for every image to be used to generate images for display
 *       on client browser to be clicked to see larger images (which would be the resized image).
 *     <li> holding images folder: This will hold images which are being prepared to "go live".  At the right point
 *       these will be moved into the xxx
 * </ul>
 * <li> A set of image files within a gallery, which must have an appropriate extension.
 * <li> A thumbnails folder which is used to hold the thumbnail images which will be displayed on screen for a user to
 *   click on to see the larger image.
 *
 * <p>
 *  Workflow which must be supported by the package is as follows:
 *
 *  - Add a new gallery.
 *  - Set the status of a gallery (suspended or active).
 *  - Upload full size (possibly very large) images to a specific gallery. This will automatically trigger the creation
 *    of resized images which are small enough to be displayed over the web (defined by configuration values).
 *    The original and resized images will be held in folders and will stay there until an images is hard-deleted.
 *  - When images are uploaded they will be placed (copied) into the holding state.  In practice this will mean
 *    placing in a holding folder.
 *  - It will be possible change the status of a given image or change all images of one status to another.
 *    For example all holding images could be changed to live.
 *  -
 * <p>
 * Functions defines within the package are:
 *
 * - Set base folder for galleries.  All galleries will sit directly underneath a given folder.
 *   This will probably only happen once on instantiation of gallery management class (whatever that
 *   turns out to be).
 * - Create a gallery.  This will involve creating a folder underneath the base folder.  If the new folder already
 *   exists then this operation will do nothing (not an error).
 * - Delete a gallery.  This will delete the folder and all files (images within it).
 */
package uk.co.genonline.ldav03.model.gallery;