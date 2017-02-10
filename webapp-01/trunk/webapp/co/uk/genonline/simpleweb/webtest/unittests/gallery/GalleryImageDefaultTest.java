package unittests.gallery;

import co.uk.genonline.simpleweb.web.gallery.GalleryImage;
import co.uk.genonline.simpleweb.web.gallery.GalleryImageDefault;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

public class GalleryImageDefaultTest extends TestCase {

    private final File testGalleryPath = new File("/Users/thomassecondary/Projects/webapp-01/documents/testing/unit-galleries/testgallery01");
    private final File testImageFile1 = new File(testGalleryPath, "testimage00001.JPG");
    private final File testImageFile2 = new File(testGalleryPath, "testimage00002.JPG");

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testImageProperties() {
        GalleryImage galleryImage = new GalleryImageDefault(testImageFile1);

        Assert.assertEquals("testimage00001.JPG", galleryImage.getImageFullName());
        Assert.assertEquals("testimage00001", galleryImage.getImageName());
        Assert.assertEquals("JPG", galleryImage.getImageExtension());
        Assert.assertEquals(400, galleryImage.getImageWidth());
        Assert.assertEquals(596, galleryImage.getImageHeight());
        Assert.assertFalse(galleryImage.isModified());

        long beforeLastModified = galleryImage.getLastModified();

        // Increase date/time modified by 1 second (1000 milliseconds).  Note won't work with fewer than 1000ms.
        testImageFile1.setLastModified(testImageFile1.lastModified()+1000);

        long afterLastModified = testImageFile1.lastModified();

        Assert.assertTrue(String.format("Last modified should be different (before=%d, after=%d", beforeLastModified, afterLastModified), galleryImage.isModified());

        testImageFile1.setLastModified(testImageFile1.lastModified() - 1000);
    }

    @Test
    public void testCompareTo() {
        GalleryImage galleryImage1 = new GalleryImageDefault(testImageFile1);
        GalleryImage galleryImage2 = new GalleryImageDefault(testImageFile2);

        Assert.assertEquals(0, galleryImage1.compareTo(galleryImage1));
        Assert.assertEquals(-1, (int)Math.signum(galleryImage1.compareTo(galleryImage2)));
        Assert.assertEquals(1, (int)Math.signum(galleryImage2.compareTo(galleryImage1)));

    }

    @Test
    public void testToString() {
        GalleryImage galleryImage1 = new GalleryImageDefault(testImageFile1);

        Assert.assertEquals("<testimage00001.JPG>:<596>:<400>:<2017-01-26 08:08>", galleryImage1.toString());
    }
}