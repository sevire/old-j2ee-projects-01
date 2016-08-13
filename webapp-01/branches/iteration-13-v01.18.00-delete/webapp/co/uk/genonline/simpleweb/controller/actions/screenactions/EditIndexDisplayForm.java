package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensSortType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 */
public class EditIndexDisplayForm extends ScreenAction {

    public EditIndexDisplayForm(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    /**
     */
    public RequestResult perform() {

        List screenList = screensManager.getAllScreens(ScreensSortType.ADMIN_SCREEN, true);

        if (screenList == null) {
            status.setStatusMessage("Error while getting page list", "error");
        } else if (screenList.isEmpty()) {
            status.setStatusMessage("No pages currently set up - click 'add' to create new page", "warning");
        } else {
            if (status.isMessageDisplayed()) {
                status.resetStatusMessage();
            }
            for (Object s : screenList) {
                String contents = ((ScreensEntity)s).getScreenContents();
                ((ScreensEntity)s).setScreenContents(contents.substring(0, Math.min(39, contents.length()))+"...");
            }
        }
        request.setAttribute("editList", screenList);
        // ToDo: EdIndDispForm: Check whether the status message logic is correct - why am I not using RequestResult status
        request.setAttribute("statusType", request.getSession().getAttribute("statusType"));
        request.setAttribute("statusMessage", request.getSession().getAttribute("statusMessage"));
        return new RequestResult(request, "editIndex.jsp", false);
    }
}
