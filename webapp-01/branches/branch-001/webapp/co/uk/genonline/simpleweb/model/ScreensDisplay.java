package co.uk.genonline.simpleweb.model;

import co.uk.genonline.simpleweb.model.bean.ScreensEntity;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 25/11/2012
 * Time: 14:27
 * Allows what is displayed to vary from what is in the database (e.g. blank long name replaced by short name)
 */
class ScreensDisplay {
    private final ScreensEntity screen;

    ScreensDisplay(ScreensEntity screen) {
        this.screen = screen;
    }

    String getName() {
        return screen.getName();
    }

    String getScreenTitleShort() {
        String shortTitle = screen.getScreenTitleShort();
        if (shortTitle == null || shortTitle.equals("")) {
            return getName();
        } else {
            return shortTitle;
        }
    }

    public String getScreenTitleLong() {
        String longTitle = screen.getScreenTitleLong();
        if (longTitle == null || longTitle.equals("")) {
            return getScreenTitleShort();
        } else {
            return longTitle;
        }
    }
}
