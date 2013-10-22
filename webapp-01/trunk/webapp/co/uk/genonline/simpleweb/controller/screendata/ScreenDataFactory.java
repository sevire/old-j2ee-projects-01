package co.uk.genonline.simpleweb.controller.screendata;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 20/10/2013
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public class ScreenDataFactory {
    public static ScreenData getScreenData(String screenType) {
        if (screenType.equals("mistress")) {
            return new MistressScreenData();
        } else {
            return new DefaultScreenData();
        }
    }
}
