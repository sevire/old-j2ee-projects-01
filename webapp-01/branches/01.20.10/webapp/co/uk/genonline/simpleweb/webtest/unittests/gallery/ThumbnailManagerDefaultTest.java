package unittests.gallery;

import co.uk.genonline.simpleweb.web.gallery.*;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThumbnailManagerDefaultTest extends TestCase {

    // Use same image names in all galleries.  Set up constants for these below.  May be some exceptions for certain tests.
    private final String testImage01 = "testimage00001.JPG";
    private final String testImage02 = "testimage00002.JPG";
    private final String testImage03 = "testimage00003.JPG";
    private final String testImage04 = "testimage00004.JPG";
    private final String testImage05 = "testimage00005.JPG";
    private final String invalidImage01 = "xxxxx.jpg";
    private final String invalidImage02 = "xxxxx.yyy";

    private final File testGalleryRootPath = new File("/Users/thomassecondary/Projects/webapp-01/documents/testing/unit-galleries");

    // Constant paths for Gallery 1
    private final File testGallery1Path = new File(testGalleryRootPath, "testgallery01");
    private final File testGallery1ThumbnailPath = new File(testGallery1Path, "thumbnails");

    private final File testImageGallery1File1 = new File(testGallery1Path, testImage01);
    private final File testImageGallery1File2 = new File(testGallery1Path, testImage02);

    private final File testImageG1F1Thumbnail = new File(testGallery1ThumbnailPath, testImage01);
    private final File testImageG1F2Thumbnail = new File(testGallery1ThumbnailPath, testImage02);

    // Constant paths for Gallery 2
    private final File testGallery2Path = new File(testGalleryRootPath, "testgallery02");
    private final File testGallery2ThumbnailPath = new File(testGallery2Path, "thumbnails");

    private final File testImageGallery2File1 = new File(testGallery2Path, testImage01);
    private final File testImageGallery2File2 = new File(testGallery2Path, testImage02);
    private final File testImageInvalid = new File(testGallery2Path, invalidImage01);

    private final File testImageG2F1Thumbnail = new File(testGallery2ThumbnailPath, testImage01);
    private final File testImageG2F2Thumbnail = new File(testGallery2ThumbnailPath, testImage02);


    private GalleryManagerConfiguration galleryManagerConfiguration = null;

    public void setUp() throws Exception {
        super.setUp();

        String[] imageExtensionList = {"jpg", "jpeg", "png"};
        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                100,
                100,
                imageExtensionList);
    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateDeleteThumbnailFolder() {
        File galleryRootFolder = new File(testGalleryRootPath, "testgallery01");
        String[] imageExtensionList = {"jpg", "jpeg", "png"};
        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                100,
                100,
                imageExtensionList);
        ThumbnailManager thumbnailManager = new ThumbnailManagerDefault(galleryManagerConfiguration);

        // Case 2 : Thumbnail folder doesn't exist, create
        thumbnailManager.createThumbnailFolder(galleryRootFolder);

        File thumbnailFolder = new File(galleryRootFolder, "thumbnails");
        assertTrue(thumbnailFolder.exists());
        assertTrue(thumbnailFolder.isDirectory());

        // Case 3 : Folder already exists, should do nothing.

        long beforeLastModified = thumbnailFolder.lastModified();
        thumbnailManager.createThumbnailFolder(galleryRootFolder);
        long afterLastModified = thumbnailFolder.lastModified();

        // Check that new folder hasn't been created by checking that last modified date is same.
        assertTrue(beforeLastModified == afterLastModified);

        // Need to clean up before next createFolder, so may as well test deleteFolder
        thumbnailManager.deleteThumbnailFolder(galleryRootFolder);

        Assert.assertFalse(thumbnailFolder.exists());

        // Case 1 : "thumbnail" file exists but isn't a folder - should just leave it, can't create thumbnails in this case.
        try {
            thumbnailFolder.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thumbnailManager.createThumbnailFolder(galleryRootFolder);
        assertTrue(thumbnailFolder.exists());
        assertFalse(thumbnailFolder.isDirectory());

        // Clean up
        thumbnailFolder.delete();
    }

    public void testThumbnailCreationDeletion() {
        ThumbnailManager thumbnailManager = new ThumbnailManagerDefault(galleryManagerConfiguration);
        assertNotNull(thumbnailManager);

        GalleryImage galleryImage1 = new GalleryImageDefault(testImageGallery2File1);
        GalleryImage galleryImage2 = new GalleryImageDefault(testImageGallery2File2);
        GalleryImage invalidGalleryImage = new GalleryImageDefault(testImageInvalid);

        boolean success;
        success = thumbnailManager.createThumbnail(testGallery2Path, galleryImage1);
        assertTrue(success);
        assertTrue(testImageG2F1Thumbnail.exists());
        assertTrue(testImageG2F1Thumbnail.isFile());

        success = thumbnailManager.createThumbnail(testGallery2Path, galleryImage2);
        assertTrue(success);
        assertTrue(testImageG2F2Thumbnail.exists());
        assertTrue(testImageG2F2Thumbnail.isFile());

        success = thumbnailManager.createThumbnail(testGallery2Path, invalidGalleryImage);
        assertFalse(success);

        // Cleanup - delete thumbnails again
        thumbnailManager.deleteThumbnail(testGallery2Path, galleryImage1);
        assertFalse(testImageG2F1Thumbnail.exists());

        thumbnailManager.deleteThumbnail(testGallery2Path, galleryImage2);
        assertFalse(testImageG2F2Thumbnail.exists());

        // Should be able to call deleteThumbnail again without an error (it's ok if the thumbnail isn't there to delete
        thumbnailManager.deleteThumbnail(testGallery2Path, galleryImage1);
        assertFalse(testImageG2F1Thumbnail.exists());

        thumbnailManager.deleteThumbnail(testGallery2Path, galleryImage2);
        assertFalse(testImageG2F2Thumbnail.exists());

        List<GalleryImage> imageList = new ArrayList<GalleryImage>();
        imageList.add(galleryImage1);
        imageList.add(galleryImage2);

        thumbnailManager.checkAndCreateThumbnails(testGallery2Path, imageList);
        assertTrue(testImageG2F1Thumbnail.exists());
        assertTrue(testImageG2F1Thumbnail.isFile());
        assertTrue(testImageG2F2Thumbnail.exists());
        assertTrue(testImageG2F2Thumbnail.isFile());

        // Shouldn't get an error if I try to create when they already exist.  Result should be the same.

        thumbnailManager.checkAndCreateThumbnails(testGallery2Path, imageList);
        assertTrue(testImageG2F1Thumbnail.exists());
        assertTrue(testImageG2F1Thumbnail.isFile());
        assertTrue(testImageG2F2Thumbnail.exists());
        assertTrue(testImageG2F2Thumbnail.isFile());

        // Now delete them all using the list
        thumbnailManager.deleteAllThumbnails(testGallery2Path, imageList);
        assertFalse(testImageG2F1Thumbnail.exists());
        assertFalse(testImageG2F2Thumbnail.exists());

        // Shouldn't get an error if I try to delete when they don't exist.  Result should be the same.
        thumbnailManager.deleteAllThumbnails(testGallery2Path, imageList);
        assertFalse(testImageG2F1Thumbnail.exists());
        assertFalse(testImageG2F2Thumbnail.exists());

        // Now create them again and delete them not using the list (i.e. just delete all files in the thumbnail folder).
        thumbnailManager.checkAndCreateThumbnails(testGallery2Path, imageList);
        // Don't bother with asserts - we have already test them.  Assume it works.  Just call the deleteThumbnails method.
        thumbnailManager.deleteAllThumbnails(testGallery2Path);
        assertFalse(testImageG2F1Thumbnail.exists());
        assertFalse(testImageG2F2Thumbnail.exists());
    }
}