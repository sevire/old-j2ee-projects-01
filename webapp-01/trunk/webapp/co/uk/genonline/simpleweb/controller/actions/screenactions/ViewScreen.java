package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.ActionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.controller.screendata.ScreenData;
import co.uk.genonline.simpleweb.controller.screendata.ScreenDataFactory;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
public class ViewScreen extends ScreenAction {

    public ViewScreen(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        String screenType = data.getScreen().getScreenDisplayType();
        if (screenType == null || screenType.equals("")) {
            screenType = "mistress-01";
        }
        ScreenData screenData = ScreenDataFactory.getScreenData(screenType);

        RequestResult result = screenData.setScreenData(request, response, screenBeanManager, data);

        request.setAttribute("screenData", screenData);
        return result;
    }
}
