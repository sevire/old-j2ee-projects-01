package uk.co.genonline.ldav03.model.Gallery;

/**
 * Created by thomassecondary on 27/10/2014.
 */
public class Gallery {
    final int THUMBNAIL_WIDTH = 150;
    final int THUMBNAIL_HEIGHT = 150;
    final int MAX_IMAGES = 100; // In case find folder with 1000s images in

    private final String galleryName;
    private String html;
    private String[] imageArray = {"Image1.jpg","Image2.png"};

    public Gallery(String galleryName) {
        this.galleryName = galleryName;
    }

    public String getHtml() {
        if (html != null) {
            return html;
        } else {
            html =  createGalleryHtml(imageArray);
        }
        return html;
    }

    private String createGalleryHtml(String[] imageArray) {
        String html;
        String ul;
        String liElements;

        liElements = String.format("<li><img src=\"%s\" /></li><img src=\"%s\" /><li", "/galleryimage/Image1.jpg", "/galleryimage/Image2.png");
        ul = String.format("<ul class=\"gallery\">%s</ul>", liElements);
        html = String.format("<div id=\"gallery\">%s</div", ul);

        return html;
    }
}
