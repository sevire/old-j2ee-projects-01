package co.uk.genonline.simpleweb.controller.actions.simpleactions;

import co.uk.genonline.simpleweb.controller.actions.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 26/10/2013
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public abstract class SimpleAction extends Action {

    protected SimpleAction(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
}
