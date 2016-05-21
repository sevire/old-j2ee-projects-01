package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.controller.actions.RequestResult;

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
abstract class UpdateConfigItemDisplayForm extends ConfigurationAction {

    UpdateConfigItemDisplayForm(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
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
        if (addFlag) {
            configItemBeanManager.initialiseBean();
            logger.info(String.format("Adding config item"));
        } else {
            String configItemName = request.getParameter("name");
            configItemBeanManager.getConfigItemIntoBean(configItemName);
            request.setAttribute("configItem", configItems);
            logger.info(String.format("Editing config item <%s>", configItems.getName()));
        }
        request.setAttribute("addFlag", addFlag);
        return new RequestResult(request, "updateConfigItem", false);
    }
}
