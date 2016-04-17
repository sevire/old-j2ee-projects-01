package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
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
public class EditScreenProcessForm extends UpdateActionClass {

    public EditScreenProcessForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, SessionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        return updateScreen(false);
    }
}
