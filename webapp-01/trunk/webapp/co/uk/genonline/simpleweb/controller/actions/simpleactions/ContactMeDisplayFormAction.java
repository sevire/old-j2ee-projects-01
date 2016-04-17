package co.uk.genonline.simpleweb.controller.actions.simpleactions;

import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.controller.actions.SessionData;
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
public class ContactMeDisplayFormAction extends SimpleAction {

    public ContactMeDisplayFormAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, SessionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        RequestResult result = new RequestResult(request, "contactMe.jsp", false);
        return result;
    }
}
