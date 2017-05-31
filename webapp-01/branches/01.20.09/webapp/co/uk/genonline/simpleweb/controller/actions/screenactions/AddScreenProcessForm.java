package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.RequestResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddScreenProcessForm extends UpdateScreenAction {

    public AddScreenProcessForm(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public RequestResult perform() {
        return updateScreen(true);
    }
}









