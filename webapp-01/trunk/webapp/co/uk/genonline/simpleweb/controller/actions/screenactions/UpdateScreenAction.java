package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensRequestManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 10/12/2012
 * Time: 22:20
 *
 */
abstract class UpdateScreenAction extends ScreenAction {
    UpdateScreenAction(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    /**
     * Common code for updating screen, called as part of add screen and update screen.  Only difference is
     * whether to add a record to the database or update existing record.
     *
     * When this method is called, the updated fields from the form will be stored as paramaters in the request
     * object, and the original data will be within the screen member of the sessionData object.  Either way, we
     * need to move the fields from the request into a ScreensEntity object in order to pass it to ScreenBeanManager
     * to save it.  We also need to update fields which aren't included in the form, such as the id.
     *
     * @param addFlag
     */
    RequestResult updateScreen(boolean addFlag) {
        String descriptor = addFlag ? "add" : "update";

        ScreensEntity screenBeforeUpdate = sessionData.getScreen();
        ScreensEntity updatedScreen = ScreensRequestManager.moveRequestIntoScreenBean(request, screenBeforeUpdate.getId());
        String screenName = updatedScreen.getName();

        logger.info(String.format(descriptor + "ing" + " screen <%s>", screenName));
        logger.debug("Screen contents from form...\n%s", request.getParameter("screenContents"));
        boolean statusFlag;

        if (screenName.equals("")) {
            logger.info("Screen added/updated with blank name field - rejected");
            status.setStatusMessage("Name field must be populated", "error");
            statusFlag = false;
        } else {
            statusFlag = addFlag ? screensManager.addScreen(updatedScreen) : screensManager.updateScreen(updatedScreen);
            if (!statusFlag) {
                status.setStatusMessage("Unable to save screen data", "error");
            }
        }
        if (!statusFlag) {
            if (addFlag) {
                logger.info("Re-displaying add screen");
            } else {
                logger.info("Re-displaying update screen");
            }
            request.setAttribute("addFlag", addFlag);
            return new RequestResult(request, "updateScreen.jsp", false);
        } else {
            status.setStatusMessage(String.format("Screen %s, %s sucessfully", screenName, (addFlag ? "added" : "updated")), "info");
            return new RequestResult(request, "/editIndex", true);
        }
    }
}
