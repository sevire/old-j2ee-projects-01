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
    public static ScreenData getScreenData(String screenDisplayType) {
        WebLogger logger = new WebLogger();
        logger.debug(String.format("Instantiating ScreenData for type <%s>", screenDisplayType));
        if (screenDisplayType == null) {
            return new MistressScreenData("mistress-05"); // Default
        } else if (
                screenDisplayType.equals("mistress-01") ||
                screenDisplayType.equals("mistress-02") ||
                screenDisplayType.equals("mistress-03") ||
                screenDisplayType.equals("mistress-04") ||
                screenDisplayType.equals("mistress-05") ) {
            return new MistressScreenData(screenDisplayType);
        } else if (screenDisplayType.equals("contactMe")) {
            return new ContactMeScreenData(screenDisplayType);
        } else if (screenDisplayType.equals("mistress-index-01")) {
            return new MistressIndexScreenData(screenDisplayType);
        } else if (screenDisplayType.equals("general-01")) {
            return new GeneralScreenData(screenDisplayType);
        } else if (screenDisplayType.equals("links")) {
            return new LinksScreenData(screenDisplayType);
        } else {
            return new DefaultScreenData("mistress-05");
        }
    }
}
