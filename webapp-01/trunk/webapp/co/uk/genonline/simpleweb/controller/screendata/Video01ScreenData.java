package co.uk.genonline.simpleweb.controller.screendata;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 24/10/2013
 * Time: 18:42
 * To change this template use File | Settings | File Templates.
 */
public class Video01ScreenData extends MistressScreenData {
    public Video01ScreenData(String screenType) {
        super(screenType);
    }

    public String getJSPname() {
        return "video-01.jsp";  // Need to extend for each jsp used
    }
}
