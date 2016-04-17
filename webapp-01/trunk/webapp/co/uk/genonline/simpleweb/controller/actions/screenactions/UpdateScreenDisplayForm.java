package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import org.hibernate.SessionFactory;

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

    UpdateScreenDisplayForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, SessionData data) {
        super(request, response, factory, data);
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
        if (addFlag) {
            screenBeanManager.initialiseBean(screen);
            request.setAttribute("addFlag", true);
            logger.info(String.format("Adding screen"));
        } else {
            screenBeanManager.getScreen(screen);
            request.setAttribute("addFlag", false);
            logger.info(String.format("Editing screen <%s>", screen.getName()));
        }
        screenJsp = "updateScreen.jsp";
        return new RequestResult(request, screenJsp, false);
    }
}
