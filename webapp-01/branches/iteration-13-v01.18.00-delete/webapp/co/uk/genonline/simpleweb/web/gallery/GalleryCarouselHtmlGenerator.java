package co.uk.genonline.simpleweb.web.gallery;

import co.uk.genonline.simpleweb.controller.WebLogger;

import java.io.File;
import java.util.List;

/**
 * Created by thomassecondary on 18/04/15.
 */
public class GalleryCarouselHtmlGenerator extends GalleryHtml {
    WebLogger logger = new WebLogger();
    GalleryManagerConfiguration galleryManagerConfiguration;
    int imagesAdded;

    public GalleryCarouselHtmlGenerator(GalleryManagerConfiguration galleryManagerConfiguration) {
        if (galleryManagerConfiguration  == null) {
            throw new NullPointerException(String.format("GalleryManagerConfiguration is null in GalleryHtmlGenerator"));
        } else {
            this.galleryManagerConfiguration = galleryManagerConfiguration;
        }
    }

    public String getHtml(String galleryName, List<GalleryImage> galleryImages) {
        String html;

        if (galleryImages.size() == 0) {
            // Not an error but no gallery to create, return appropriate html

            html = ""; // Note it has to be empty string as there is a check in the jsp (may not be best way!)
        } else {
            imagesAdded = 0;
            html = String.format("<ul class='gallery'>%n%n");
            for (GalleryImage image : galleryImages) {
                html += addLItoULelement(galleryName, image.getImageFullName());
            }
            html += String.format("</ul>%n");
            logger.debug("html = \n" + html);
        }
        return html;
    }

    /**
     * Adds a list element to the html String containing the gallery html.  May become a more general purpose
     * html utility method later on but for now it's quite specific for the purpose of creating a gallery.
     *
     * I need to think how best to manage the html string (e.g. pass it in then the result back or just get the
     * additional String back and add it in the main code.  See to do below.
     *
     * ToDo: Rethink this - have re-factored a bit because of HtmlGenerator bug but not sure this is the best approach
     *
     * @param imageName
     */
    private String addLItoULelement(String galleryName, String imageName) {

        String fs = File.separator;
        String thumbPath = galleryName + fs + galleryManagerConfiguration.getThumbnailRelPath() + fs + imageName;
        String imagePath = galleryName + fs + imageName;
        String thumbRelPath = getHTMLRelPath(thumbPath);
        String imageRelPath = getHTMLRelPath(imagePath);
        logger.trace(String.format("Image source is <%s>", imageRelPath));

        String img = String.format("<img src='%s' data-large='%s' />", thumbRelPath, imageRelPath);
        return String.format("<li><a href='#'>%n%s%n</a></li>%n%n", img);
    }

    private String getHTMLRelPath(String path) {
        return galleryManagerConfiguration.getGalleriesUrlRelPath() + File.separator + path;
    }
}
