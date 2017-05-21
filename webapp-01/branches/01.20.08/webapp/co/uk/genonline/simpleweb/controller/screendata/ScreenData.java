package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreensManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 12/10/2013
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public abstract class ScreenData {
    String screenType;

    /**
     * Make no argument constructor private as we don't want anyone instantiating ScreenData if
     * without providing a screenType (it doesn't make any sense to do so).
     */
    private ScreenData() {

    }

    public ScreenData(String screenType) {
        this.screenType = screenType;
    }

    public String getJSPname() {
        return this.screenType + ".jsp";
    };

    public abstract RequestResult setScreenData(HttpServletRequest request,
                                HttpServletResponse response,
                                ScreensManager screensManager,
                                SessionData data
    );
}
