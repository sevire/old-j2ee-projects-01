package unittests.gallery;

import co.uk.genonline.simpleweb.web.gallery.GalleryManagerConfiguration;
import co.uk.genonline.simpleweb.web.gallery.GalleryManagerConfigurationDefault;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.File;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class GalleryManagerConfigurationDefaultTest extends TestCase {
    private final File testGalleryRootPath = new File("/Users/thomassecondary/Projects/webapp-01/documents/testing/unit-galleries");


    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testValidInstantiation() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        // Normal - correct instantiation
        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                145,
                35,
                imageExtensionList);

        assertEquals(testGalleryRootPath, galleryManagerConfiguration.getGalleriesRootFullPath());
        assertEquals("galleries", galleryManagerConfiguration.getGalleriesUrlRelPath());
        assertEquals("thumbnails", galleryManagerConfiguration.getThumbnailRelPath());
        assertEquals(145, galleryManagerConfiguration.getMaxThumbnailHeight());
        assertEquals(35, galleryManagerConfiguration.getMaxThumbnailWidth());
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidInstantiation1a() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                null,
                "galleries",
                "thumbnails",
                145,
                35,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation1b() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                new File(""),
                "galleries",
                "thumbnails",
                145,
                35,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation1c() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                new File("XXXXX"),
                "galleries",
                "thumbnails",
                145,
                35,
                imageExtensionList);
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidInstantiation2a() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                null,
                "thumbnails",
                145,
                35,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation2b() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "",
                "thumbnails",
                145,
                35,
                imageExtensionList);
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidInstantiation3a() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                null,
                145,
                35,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation3b() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "",
                145,
                35,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation4a() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                0,
                35,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation4b() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                9,
                35,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation4c() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                -10000,
                35,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation5a() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                145,
                0,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation5b() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                145,
                9,
                imageExtensionList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation5c() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                145,
                -1,
                imageExtensionList);
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidInstantiation6a() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {"jpg", "jpeg", "png"};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                145,
                120,
                null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInstantiation6b() {
        GalleryManagerConfiguration galleryManagerConfiguration;
        String[] imageExtensionList = {};

        galleryManagerConfiguration = new GalleryManagerConfigurationDefault(
                testGalleryRootPath,
                "galleries",
                "thumbnails",
                145,
                120,
                imageExtensionList);
    }
}