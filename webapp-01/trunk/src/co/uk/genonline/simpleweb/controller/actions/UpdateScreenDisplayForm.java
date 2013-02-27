package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;
import co.uk.genonline.simpleweb.model.bean.Screens;
import co.uk.genonline.simpleweb.web.WebHelper;
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
public abstract class UpdateScreenDisplayForm extends ActionClass {

    UpdateScreenDisplayForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    /**
     *
     * @param addFlag
     *
     * If true then we are adding a new screen so initialise fields to default values.  Otherwise we are editing
     * a screen so get initial values from database.
     *
     * @return
     */
    RequestResult displayScreenForm(boolean addFlag) {
        String loggingName;
        String screenJsp;
        if (addFlag) {
            ScreenBeanManager manager = new ScreenBeanManager(data);
            manager.initialiseBean();
            request.setAttribute("addFlag", true);
            logger.info(String.format("Adding screen"));
        } else {
            WebHelper webHelper = new WebHelper(request, response, factory);
            webHelper.getScreenIntoBean(data, data.getName());
            request.setAttribute("addFlag", false);
            logger.info(String.format("Editing screen <%s>", data.getName()));
        }
        screenJsp = "updateScreen.jsp";
        return new RequestResult(jspLocation(screenJsp), false);
    }
}
