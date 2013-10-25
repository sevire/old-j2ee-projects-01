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
        if (screenType.equals("mistress-01")) {
            return new Mistress01ScreenData();
        } else if (screenType.equals("mistress-02")) {
            return new Mistress02ScreenData();
        } else {
            return new DefaultScreenData();
        }
    }
}
