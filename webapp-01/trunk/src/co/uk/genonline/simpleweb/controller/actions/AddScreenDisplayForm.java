package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.Screens;
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
public class AddScreenDisplayForm extends ActionClass {

    public AddScreenDisplayForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        return new RequestResult(jspLocation("addScreen.jsp"), false);
    }
}
