package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 */
public class EditIndexDisplayForm extends ScreenAction {

    public EditIndexDisplayForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, SessionData data) {
        super(request, response, factory, data);
    }

    /**
     */
    public RequestResult perform() {

        List screenList = screenBeanManager.getAllScreens();

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
        request.setAttribute("statusType", request.getSession().getAttribute("statusType"));
        request.setAttribute("statusMessage", request.getSession().getAttribute("statusMessage"));
        return new RequestResult(request, "editIndex.jsp", false);
    }
}
