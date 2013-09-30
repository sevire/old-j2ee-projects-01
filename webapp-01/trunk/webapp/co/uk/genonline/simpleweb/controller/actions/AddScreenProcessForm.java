package co.uk.genonline.simpleweb.controller.actions;

import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddScreenProcessForm extends UpdateActionClass {

    public AddScreenProcessForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        return updateScreen(true);
    }
}









