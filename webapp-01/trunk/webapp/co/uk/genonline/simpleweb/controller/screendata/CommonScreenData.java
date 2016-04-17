package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.controller.actions.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 13/10/2013
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class CommonScreenData implements ScreenData {



    public String getJSPname() {
        return null; // No JSP as this is part of a separate ScreenData object
    }

    public RequestResult setScreenData(HttpServletRequest request, HttpServletResponse response, ScreenBeanManager screenBeanManager, SessionData data) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
