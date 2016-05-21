package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.controller.WebLogger;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 20/10/2013
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public class ScreenDataFactory {
    public static ScreenData getScreenData(String screenType) {
        WebLogger logger = new WebLogger();
        logger.debug(String.format("Instantiating ScreenData for type <%s>", screenType));
        if (screenType == null) {
            return new MistressScreenData("mistress-05"); // Default
        } else if (
                screenType.equals("mistress-01") ||
                screenType.equals("mistress-02") ||
                screenType.equals("mistress-03") ||
                screenType.equals("mistress-04") ||
                screenType.equals("mistress-05") ) {
            return new MistressScreenData(screenType);
        } else if (screenType.equals("contactMe")) {
            return new ContactMeScreenData(screenType);
        } else if (screenType.equals("mistress-index-01")) {
            return new MistressIndexScreenData(screenType);
        } else {
            return new DefaultScreenData("mistress-05");
        }
    }
}
