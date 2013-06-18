package co.uk.genonline.simpleweb.controller.actions;

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
public class CancelAction extends ActionClass {

    public CancelAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        status.setStatusMessage("Action cancelled", "info");
        return new RequestResult(URLwithContext("/editIndex"), true);
    }
}
