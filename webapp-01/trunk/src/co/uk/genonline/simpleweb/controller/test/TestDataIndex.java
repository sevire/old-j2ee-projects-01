package co.uk.genonline.simpleweb.controller.test;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/11/2012
 * Time: 07:50
 * To change this template use File | Settings | File Templates.
 */
public enum TestDataIndex {
    HTML_FILE_NAME_FOR_SCREEN(0),
    HTML_LINK_NAME(1),
    J2EE_LINK_NAME(2),
    HTML_PAGE_TITLE(3),
    J2EE_PAGE_TITLE(4),
    NUM_GALLERY_IMAGES(5),
    GALLERY_IMAGE_FIRST_IMAGE_NAME(6),
    GALLERY_IMAGE_LAST_IMAGE_NAME(7);

    private final int index;

    TestDataIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
