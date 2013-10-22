package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Sub-class of ActionClass with responsibility for preparing data in order to display the edit index screen.
 * This screen displays a row for each screen showing the key information with links to edit a screen or add a new screen.
 */
public class EditIndexDisplayForm extends ActionClass {

    public EditIndexDisplayForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super(request, response, factory, data);
    }

    /**
     * The main method of the ActionClass class of which this class is a sub-class.
     * @return Name of next command (e.g. jsp file or url) and whether to forward (jsp) or re-direct (url).
     */
    public RequestResult perform() {
        // ToDo: Need to encapsulate database access better.

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
                String contents = ((Screens)s).getScreenContents();
                ((Screens)s).setScreenContents(contents.substring(0, Math.min(39, contents.length()))+"...");
            }
        }
        request.setAttribute("editList", screenList);
        request.setAttribute("statusType", request.getSession().getAttribute("statusType"));
        request.setAttribute("statusMessage", request.getSession().getAttribute("statusMessage"));
        return new RequestResult(request, "editIndex.jsp", false);
    }
}
