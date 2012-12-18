package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddScreenProcessForm extends UpdateActionClass {

    public AddScreenProcessForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        return updateScreen(true);
    }
}









