package unittests.gallery;

import co.uk.genonline.simpleweb.web.gallery.*;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class GalleryManagerTest extends TestCase {

    private final File testGalleryRootPath = new File("/Users/thomassecondary/Projects/lda-webapp/documents/testing/unit-galleries");

    private GalleryManagerConfiguration galleryManagerConfiguration;
    private GalleryManager galleryManager;
    private GalleryHtmlGenerator defaultHtmlGenerator;

    public void gallerySetup() {
        String[] imageExtensionList = {"jpg", "jpeg", "png"};
        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                200,
                100,
                imageExtensionList);

        ThumbnailManager thumbnailManager = new ThumbnailManagerDefault(galleryManagerConfiguration);

        defaultHtmlGenerator = new GalleryCarouselHtmlGenerator(galleryManagerConfiguration);
        galleryManager = new GalleryManagerDefault(
                galleryManagerConfiguration,
                thumbnailManager,
                defaultHtmlGenerator);
    }

    public void setUp() throws Exception {
        super.setUp();

        gallerySetup();
    }

    public void tearDown() throws Exception {

    }

    /**
     * ToDo: Need to ensure galleries are cleaned up afterwards to get back to known state for next run of test.
     *
     * @throws Exception
     */
    public void testAddAndGetGallery() throws Exception {
        Gallery gallery;

        // No galleries added yet, so count should be zero
        assertEquals(0, galleryManager.getNumGalleries());
        galleryManager.addGallery("testgallery04");
        assertEquals(1, galleryManager.getNumGalleries());

        // Now retrieve the gallery just added
        gallery = galleryManager.getGallery("testgallery04");
        assertNotNull(gallery);

        // Basic check that the retrieved gallery is the right one.
        assertEquals("testgallery04", gallery.getName());
        // Add in test that gallery was set up ok

        // Add gallery where no folder for images - shouldn't create gallery
        galleryManager.addGallery("testGalleryXX");

        // Check that number of galleries hasn't increased.
        assertEquals(1, galleryManager.getNumGalleries());

        // Try to retrieve non-existent gallery - should be null
        gallery = galleryManager.getGallery("testgalleryXX");
        assertNull(gallery);

        // Add folder for gallery and retry - should work this time (but won't be any images).
        File galleryPath = new File(testGalleryRootPath, "testgalleryXX");
        assertTrue(galleryPath.mkdir());

        // addGallery should work now
        galleryManager.addGallery("testgalleryXX");
        gallery = galleryManager.getGallery("testgalleryXX");
        assertNotNull(gallery);

        // Number of galleries should have increased to 2
        assertEquals(2, galleryManager.getNumGalleries());

        // Basic check that the retrieved gallery is the right one.
        assertEquals("testgalleryXX", gallery.getName());
        // Add in test that gallery was set up ok

        // Adding the same gallery again won't do anything so count won't go up and gallery should remain unmodified
        galleryManager.addGallery("testgallery04");
        assertEquals(2, galleryManager.getNumGalleries());

        // Now remove folder again to reset for next time test is run
        FileUtils.deleteDirectory(galleryPath);
        assertFalse(galleryPath.exists());

        // Should still be able to retrieve galleries legitimately added
        gallery = galleryManager.getGallery("testgallery04");
        assertNotNull(gallery);
        galleryManager.addGallery("testgallery04");

        // Try adding a different gallery - should increase count to 3.
        galleryManager.addGallery("testgallery03");
        assertEquals(3, galleryManager.getNumGalleries());
    }

    public void testRemoveGallery() throws Exception {

    }

    public void testGetGallery() throws Exception {

    }

    public void testRegenerateAllGalleries() throws Exception {

    }

    public void testIsGalleryExist() throws Exception {

    }
}