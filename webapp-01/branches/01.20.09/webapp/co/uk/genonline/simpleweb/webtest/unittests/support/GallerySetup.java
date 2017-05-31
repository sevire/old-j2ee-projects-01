package unittests.support;

import co.uk.genonline.simpleweb.web.gallery.*;

import java.io.File;

import static org.junit.Assert.assertEquals;


/**
 * Created by thomassecondary on 19/05/15.
 */
public class GallerySetup {
    private static final String galleryRelPath = "galleries";
    private static final File testGalleryRootPath = new File(TestSupportConstants.testGalleryRootPath);

    private static GalleryManagerConfiguration galleryManagerConfiguration;
    private static GalleryManager galleryManager;
    private static GalleryHtmlGenerator defaultHtmlGenerator;

    public static GalleryManager gallerySetup() {
        assertEquals(TestSupportConstants.testGalleryRootPath, testGalleryRootPath.toString());
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
        return galleryManager;
    }

}
