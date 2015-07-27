package unittests.gallery;

import co.uk.genonline.simpleweb.web.gallery.*;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class GalleryDefaultTest extends TestCase {
    private final String testImage01 = "testimage00001.JPG";
    private final String testImage02 = "testimage00002.JPG";
    private final String testImage03 = "testimage00003.JPG";
    private final String testImage04 = "testimage00004.JPG";
    private final String testImage05 = "testimage00005.JPG";

    private final File testGalleryRootPath = new File("/Users/thomassecondary/Projects/webapp-01(trunk)/documents/testing/unit-galleries");

    private final String gallery3Name = "testgallery03";
    private final File testGallery3Path = new File(testGalleryRootPath, gallery3Name);
    private final File testGallery3ThumbnailPath = new File(testGallery3Path, "thumbnails");

    private final File testImagesFolder = new File(testGalleryRootPath, "testimages"); // Used for spare images needed for tests.
    private final File testImagesFolderFile1 = new File(testImagesFolder, testImage01);

    private final File testImageGallery3File1 = new File(testGallery3Path, testImage01);
    private final File testImageGallery3File2 = new File(testGallery3Path, testImage02);

    private final File testImageG3F1Thumbnail = new File(testGallery3ThumbnailPath, testImage01);
    private final File testImageG3F2Thumbnail = new File(testGallery3ThumbnailPath, testImage02);

    private GalleryManagerConfiguration galleryManagerConfiguration;

    ThumbnailManager thumbnailManager;
    GalleryHtmlGenerator galleryHtmlGenerator;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        String[] imageExtensionList = {"jpg", "jpeg", "png"};
        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                200,
                100,
                imageExtensionList);

        thumbnailManager = new ThumbnailManagerDefault(galleryManagerConfiguration);
        galleryHtmlGenerator = new GalleryCarouselHtmlGenerator(galleryManagerConfiguration);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidInstantiation() {
        Gallery galleryTestInstantiation;

        galleryTestInstantiation = new GalleryDefault(
                galleryManagerConfiguration,
                gallery3Name,
                thumbnailManager,
                galleryHtmlGenerator);

        // A couple of tests to check instantiation worked ok.
        assertFalse(galleryTestInstantiation.getGalleryStatus().isGalleryError());
        assertEquals(gallery3Name, galleryTestInstantiation.getName());

    }

    // Null configuration
    @Test(expected=NullPointerException.class)
    public void testInvalidInstantiation1a() {
        Gallery galleryTestInstantiation;

        galleryTestInstantiation = new GalleryDefault(
                null,
                gallery3Name,
                thumbnailManager,
                galleryHtmlGenerator);
        assertTrue(galleryTestInstantiation.getGalleryStatus().isGalleryError());
    }

    // Null galleryName
    @Test(expected = NullPointerException.class)
    public void testInvalidInstantiation2a() {
        Gallery galleryTestInstantiation;

        // Now null gallery name.
        galleryTestInstantiation = new GalleryDefault(
                galleryManagerConfiguration,
                null,
                thumbnailManager,
                galleryHtmlGenerator);
        assertTrue(galleryTestInstantiation.getGalleryStatus().isGalleryError());
    }

    // Empty galleryName
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation2b() {
        Gallery galleryTestInstantiation;

        // Now various invalid gallery names
        galleryTestInstantiation = new GalleryDefault(
                galleryManagerConfiguration,
                "",
                thumbnailManager,
                galleryHtmlGenerator);
        assertTrue(galleryTestInstantiation.getGalleryStatus().isGalleryError());
        assertFalse(galleryTestInstantiation.getGalleryStatus().isFolderExists());
        assertFalse(galleryTestInstantiation.getGalleryStatus().isThumbnailGenerated());
    }

    // galleryName with no folder
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation2c() {
        Gallery galleryTestInstantiation;

        galleryTestInstantiation = new GalleryDefault(
                galleryManagerConfiguration,
                "XXXXX",
                thumbnailManager,
                galleryHtmlGenerator);
        assertTrue(galleryTestInstantiation.getGalleryStatus().isGalleryError());
        assertFalse(galleryTestInstantiation.getGalleryStatus().isFolderExists());
        assertFalse(galleryTestInstantiation.getGalleryStatus().isThumbnailGenerated());
    }

    // Null thumbnailManager
    @Test(expected = NullPointerException.class)
    public void testInvalidInstantiation3a() {
        Gallery galleryTestInstantiation;

        // Now null ThumbnailManager
        galleryTestInstantiation = new GalleryDefault(
                galleryManagerConfiguration,
                gallery3Name,
                null,
                galleryHtmlGenerator);

        assertTrue(galleryTestInstantiation.getGalleryStatus().isGalleryError());
    }

    // Null HtmlGenerator
    @Test(expected = NullPointerException.class)
    public void testInvalidInstantiation4a() {
        Gallery galleryTestInstantiation;

        // Now null HtmlGenerator
        galleryTestInstantiation = new GalleryDefault(
                galleryManagerConfiguration,
                gallery3Name,
                thumbnailManager,
                null);

        assertTrue(galleryTestInstantiation.getGalleryStatus().isGalleryError());

    }

    /**
     * Checks reading of gallery folder for images. The test folder should contain images of different valid
     * image types and some different file types (different extensions) to check that these aren't read.
     */

    @Test
    public void testGetImageList() {
        Gallery gallery;

        gallery = new GalleryDefault(
                galleryManagerConfiguration,
                gallery3Name,
                thumbnailManager,
                galleryHtmlGenerator);


        // Check number of images in gallery in two ways - should get same answer.

        List<GalleryImage> imageList = gallery.getImageList();
        assertEquals(5, imageList.size());
        assertEquals(5, gallery.getNumberOfImages());
    }

    @Test
    public void testGetName() {
        Gallery gallery;

        gallery = new GalleryDefault(
                galleryManagerConfiguration,
                gallery3Name,
                thumbnailManager,
                galleryHtmlGenerator);

        assertEquals(gallery3Name, gallery.getName());
    }

    /**
     * Check three cases:
     * 1 - An additional file
     * 2 - Fewer files
     * 3 - A file which has changed (different name, size etc.)
     *
     * @throws Exception
     */

    @Test
    public void testIsModified() throws IOException {
        Gallery galleryTest;

        galleryTest = new GalleryDefault(
                galleryManagerConfiguration,
                gallery3Name,
                thumbnailManager,
                galleryHtmlGenerator);

        // Set up files to be used in this test.
        File testImageGallery3AddedFile = new File (testImageGallery3File1.getParent(), "AddedFile.JPG");
        File testImageGallery3File1Renamed = new File(testImageGallery3File1.getParent(), "RenamedFile.JPG");

        // Check that if nothing has changed then testIsModified is false
        assertFalse(galleryTest.isModified());

        // Case 1 - Add a file.
        testImageGallery3AddedFile.createNewFile();
        assertTrue(galleryTest.isModified());
        // Delete again and check not modified.
        testImageGallery3AddedFile.delete();
        assertFalse(galleryTest.isModified());

        // Case 2 - Remove a file.
        testImageGallery3File1.delete();
        assertTrue(galleryTest.isModified());
        // Add back and check not modified
        FileUtils.copyFileToDirectory(testImagesFolderFile1, testGallery3Path);
        assertFalse(galleryTest.isModified());

        // Case 3(a) - Now change a filename and check is detected.
        testImageGallery3File1.renameTo(testImageGallery3File1Renamed);
        assertTrue(galleryTest.isModified());
        // Rename back and check not modified
        testImageGallery3File1Renamed.renameTo(testImageGallery3File1);
        assertFalse(galleryTest.isModified());

        // Case 3(b) - Change modified date of an image and check is detected
        // Increase date/time modified by 1 second (1000 milliseconds).  Note won't work with fewer than 1000ms.

        testImageGallery3File1.setLastModified(testImageGallery3File1.lastModified()+1000);
        assertTrue(galleryTest.isModified());

        // Now set it back and check not modified now.
        testImageGallery3File1.setLastModified(testImageGallery3File1.lastModified() - 1000);
        assertFalse(galleryTest.isModified());

    }

    /**
     * updateGallery should only do anything if the gallery has changed in some way (as defined by isModified).  So we
     * should test that if nothing has changed updateGallery doesn't do anything.  We can test this by checking the
     * modified date/time of one of the thumbnails as this will change if all the thumbnails are recreated.
     *
     * @throws Exception
     */

    @Test
    public void testUpdateGallery() {
        Gallery galleryTest;

        galleryTest = new GalleryDefault(
                galleryManagerConfiguration,
                gallery3Name,
                thumbnailManager,
                galleryHtmlGenerator);

        // Get lastModified property from one or two of the thumbnails.  Then check whether changed after call to
        // updateGallery().

        long beforeLastModified = testImageG3F1Thumbnail.lastModified();
        galleryTest.updateGallery();
        long afterLastModified = testImageG3F1Thumbnail.lastModified();

        assertTrue(beforeLastModified == afterLastModified);

        // Now change an image and retest.  Should do something this time.
        testImageGallery3File1.setLastModified(testImageGallery3File1.lastModified()+1000);
        galleryTest.updateGallery();
        afterLastModified = testImageG3F1Thumbnail.lastModified();

        // Now set it back and check not modified now. Do this before assertion so that other tests aren't affected if
        // assertion fails.
        testImageGallery3File1.setLastModified(testImageGallery3File1.lastModified()-1000);

        assertFalse(beforeLastModified == afterLastModified);
    }

    /**
     * NOTE: This isn't a very good test and didn't catch the bug which slipped through, which is that the same
     * HtmlGenerator is used for every gallery, so the html member variable keeps getting overwritten.
     *
     * ToDo: Need to write a much better test which checks that the Html at least refers to the right gallery folder.
     *
     * Maybe more.
     */

    @Test
    public void testGetHtml() {
        Gallery galleryTest;

        galleryTest = new GalleryDefault(
                galleryManagerConfiguration,
                gallery3Name,
                thumbnailManager,
                galleryHtmlGenerator);

        assertEquals(gallery3Name, galleryTest.getName());
        String newLine = String.format("%n");
        String htmlResult =
        "<ul class='gallery'>" + newLine + newLine +
        "<li><a href='#'>" + newLine +
        "<img src='galleries/testgallery03/thumbnails/testimage00001.JPG' data-large='galleries/testgallery03/testimage00001.JPG' />" + newLine +
        "</a></li>" + newLine + newLine +

        "<li><a href='#'>" + newLine +
        "<img src='galleries/testgallery03/thumbnails/testimage00002.JPG' data-large='galleries/testgallery03/testimage00002.JPG' />" + newLine +
        "</a></li>" + newLine + newLine +

        "<li><a href='#'>" + newLine +
        "<img src='galleries/testgallery03/thumbnails/testimage00003.jpg' data-large='galleries/testgallery03/testimage00003.jpg' />" + newLine +
        "</a></li>" + newLine + newLine +

        "<li><a href='#'>" + newLine +
        "<img src='galleries/testgallery03/thumbnails/testimage00004.JPG' data-large='galleries/testgallery03/testimage00004.JPG' />" + newLine +
        "</a></li>" + newLine + newLine +

        "<li><a href='#'>" + newLine +
        "<img src='galleries/testgallery03/thumbnails/testimage00005.JPG' data-large='galleries/testgallery03/testimage00005.JPG' />" + newLine +
        "</a></li>" + newLine + newLine +

        "</ul>";

        String html = galleryTest.getHtml(false);
        System.out.println(html);

        assertEquals(htmlResult, html.substring(0, htmlResult.length()));
    }

    @Test
    public void testGetGalleryStatus() {

        Gallery galleryTest;

        galleryTest = new GalleryDefault(
                galleryManagerConfiguration,
                gallery3Name,
                thumbnailManager,
                galleryHtmlGenerator);

        GalleryStatus galleryStatus = galleryTest.getGalleryStatus();

        assertFalse(galleryStatus.isGalleryError());
        assertEquals(5, galleryStatus.getNumImages());
        assertTrue(galleryStatus.isFolderExists());
        assertFalse(galleryStatus.isGalleryModified());
        assertTrue(galleryStatus.isThumbnailGenerated());
    }

    /**
     * For error which occur as a result of the external environment (not coding errors) e.g. gallery folder doesn't exist, the app
     * should be robust and be able to recover from these situations.  So if a folder doesn't exist when a user requests a page
     * which needs a gallery in that folder, if the folder is then created, next time a user requests that page the gallery should
     * appear.
     *
     * Use cases to test:
     *
     * - Folder
     */

    public void testRecoveryConditions() {

    }
}