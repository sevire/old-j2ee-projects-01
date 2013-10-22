package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.controller.actions.ActionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 12/10/2013
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
public class DefaultScreenData implements ScreenData {
    public String getJSPname() {
        return "screen";
    }

    public RequestResult setScreenData(HttpServletRequest request, HttpServletResponse response, ScreenBeanManager screenBeanManager,
                                       ActionData data) {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
    }
}
