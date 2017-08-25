package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.actions.DatabaseAction;
import co.uk.genonline.simpleweb.model.bean.ScreensManager;
import co.uk.genonline.simpleweb.model.bean.ScreensManagerNonCaching;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 26/10/2013
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public abstract class ScreenAction extends DatabaseAction {
    ScreensManager screensManager;
    SessionData sessionData;

    protected ScreenAction(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.sessionData = (SessionData) request.getSession().getAttribute("sessionData");
        this.screensManager = new ScreensManagerNonCaching(factory);
    }
}
