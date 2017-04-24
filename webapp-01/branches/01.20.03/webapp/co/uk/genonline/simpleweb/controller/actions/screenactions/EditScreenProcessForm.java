package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.RequestResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
public class EditScreenProcessForm extends UpdateScreenAction {

    public EditScreenProcessForm(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public RequestResult perform() {
        return updateScreen(false);
    }
}
