package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.ConfigItemBeanManager;
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
public abstract class UpdateConfigItemDisplayForm extends ActionClass {

    UpdateConfigItemDisplayForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
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
    RequestResult displayConfigItemForm(boolean addFlag) {
        String loggingName;
        String configJsp;
        ConfigItemBeanManager manager = new ConfigItemBeanManager(configItems, factory);
        if (addFlag) {
            manager.initialiseBean();
            request.setAttribute("addFlag", true);
            logger.info(String.format("Adding config item"));
        } else {
            manager.getConfigItemIntoBean(configItems.getName());
            request.setAttribute("addFlag", false);
            request.setAttribute("configItem", configItems);
            logger.info(String.format("Editing config item <%s>", configItems.getName()));
        }
        configJsp = "updateConfigItem.jsp";
        return new RequestResult(jspLocation(configJsp), false);
    }
}
