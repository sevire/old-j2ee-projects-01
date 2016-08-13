package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.RequestResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
public class DeleteScreen extends ScreenAction {

    public DeleteScreen(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public RequestResult perform() {

        String screenName = request.getParameter("screen");
        logger.info(String.format("Deleting screen <%s>", screenName));

        boolean success = screensManager.deleteScreen(screenName);
        if (!success) {
            String message = String.format("Unable to delete screen <%s>", screenName, "error");
            status.setStatusMessage(message, "error");
            logger.error(message);

        } else {
            String message = String.format("Deleted screen <%s>", screenName);
            status.setStatusMessage(message, "info");
            logger.info(message);
        }
        return new RequestResult(request, "/editIndex", true);
    }
}
