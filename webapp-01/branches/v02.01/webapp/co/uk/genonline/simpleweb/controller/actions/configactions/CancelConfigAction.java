package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.controller.actions.ActionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.controller.actions.screenactions.ScreenAction;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
public class CancelConfigAction extends ScreenAction {

    public CancelConfigAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        status.setStatusMessage("Action cancelled", "info");
        return new RequestResult(request, "/editIndex", true);
    }
}