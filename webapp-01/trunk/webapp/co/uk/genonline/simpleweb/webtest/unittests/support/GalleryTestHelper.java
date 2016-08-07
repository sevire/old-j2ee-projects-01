package unittests.support;

import co.uk.genonline.simpleweb.web.gallery.*;

import java.io.File;

/**
 * Centralises data used within test classes to avoid repeating set up code in different classes.
 */
public class GalleryTestHelper {

    // Path name related
    private final File testGalleryRootPath = new File("/Users/thomassecondary/Projects/lda-webapp/documents/testing/unit-galleries");
    private final String thumbnailRelPath = "thumbnails";
    private final String testimagesRelPath = "testimages";

    private final File generalTestImagesFullPath = new File(testGalleryRootPath, testimagesRelPath);

    private final String[] testGalleryNames = {
            "testgallery00",
            "testgallery01",
            "testgallery02",
            "testgallery03",
            "testgallery04",
            "testgallery05"
    };

    private final String[] testImageNames = {
            "testimage00000.JPG",
            "testimage00001.JPG",
            "testimage00002.JPG",
            "testimage00003.JPG",
            "testimage00004.JPG",
            "testimage00005.JPG"
    };

    private ThumbnailManager thumbnailManager;
    private GalleryHtmlGenerator galleryHtmlGenerator;
    private GalleryManagerConfiguration galleryManagerConfiguration;

    public File getGeneralTestImagesFolderFullPath() {
        return generalTestImagesFullPath;
    }

    public File getGeneralTestImageFullPath(int generalTestImageNumber) {
        return new File(getGeneralTestImagesFolderFullPath(), testImageNames[generalTestImageNumber]);
    }

    public ThumbnailManager getThumbnailManager() {
        return thumbnailManager;
    }

    public GalleryHtmlGenerator getGalleryHtmlGenerator() {
        return galleryHtmlGenerator;
    }

    public GalleryManagerConfiguration getGalleryManagerConfiguration() {
        return galleryManagerConfiguration;
    }

    public GalleryTestHelper() {
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

    public File getTestGalleryRootPath() {
        return testGalleryRootPath;
    }

    public File getGalleryFullPath(int galleryNumber) {
        return new File(testGalleryRootPath, testGalleryNames[galleryNumber]);
    }

    public File getGalleryThumbnailFullPath(int galleryNumber) {
        return new File(getGalleryFullPath(galleryNumber), thumbnailRelPath);
    }

    public String getGalleryName(int galleryNumber) {
        return testGalleryNames[galleryNumber];
    }

    public File getGalleryImageFullPath(int galleryNumber, int imageNumber) {
        return new File(getGalleryFullPath(galleryNumber), testImageNames[imageNumber]);
    }

    public File getGalleryImageThumbnailFullPath(int galleryNumber, int imageNumber) {
        return new File(getGalleryThumbnailFullPath(galleryNumber), testImageNames[imageNumber]);
    }
}
