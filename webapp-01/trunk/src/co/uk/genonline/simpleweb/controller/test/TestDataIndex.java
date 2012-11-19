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
    DUMMY1(1),
    SCREEN_SHORT_NAME(2),
    SCREEN_LONG_NAME(3),
    DUMMY2(4),
    NUM_GALLERY_IMAGES(5),
    NO_PAGE_FLAG(6),
    GALLERY_IMAGE_FIRST_IMAGE_NAME(7),
    GALLERY_IMAGE_LAST_IMAGE_NAME(8);

    private final int index;

    TestDataIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
