package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.controller.actions.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 12/10/2013
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public interface ScreenData {

    public String getJSPname();

    public RequestResult setScreenData(HttpServletRequest request,
                                HttpServletResponse response,
                                ScreenBeanManager screenBeanManager,
                                SessionData data
    );
}
