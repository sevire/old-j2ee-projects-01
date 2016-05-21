package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 14/12/2012
 * Time: 13:31
 *
 * Common code for displaying a screen for adding or updating.  Allows encapusulation of logic
 * to display and process fields within the form, with specific logic for initialisation.
 */
public abstract class UpdateScreenDisplayForm extends ScreenAction {

    UpdateScreenDisplayForm(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    /**
     *
     * @param addFlag If true then we are adding a new screen so initialise fields to default values.  Otherwise we are editing
     * a screen so get initial values from database.
     *
     * @return
     */
    RequestResult displayScreenForm(boolean addFlag) {
        String screenJsp;
        ScreensEntity screenRecord;
        if (addFlag) {
            screenRecord = new ScreensEntity();
            screensManager.initialiseBean(screenRecord);
            logger.info(String.format("Adding screen"));
        } else {
            String screenName = request.getParameter("screen");
            screenRecord = screensManager.getScreen(screenName, true);
            sessionData.setScreen(screenRecord);
            logger.info(String.format("Editing screen <%s>", screenRecord.getName()));
        }
        sessionData.setScreen(screenRecord); // Replaces screen in session data so will be persisted
        request.setAttribute("addFlag", addFlag);
        request.setAttribute("screen", screenRecord);
        screenJsp = "updateScreen.jsp";
        return new RequestResult(request, screenJsp, false);
    }
}
