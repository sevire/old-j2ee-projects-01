package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.controller.actions.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddConfigItemProcessForm extends UpdateConfigActionClass {

    public AddConfigItemProcessForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, SessionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        return updateConfigItem(true);
    }
}









