package co.uk.genonline.simpleweb.controller.actions.simpleactions;

import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.actions.Action;
import co.uk.genonline.simpleweb.controller.actions.SessionData;
import org.hibernate.SessionFactory;

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
    protected HttpServletRequest request;
    HttpServletResponse response;
    SessionData data;
    SessionFactory factory;
    protected RequestStatus status;

    protected SimpleAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, SessionData data) {
        super();
        this.request = request;
        this.response = response;
        this.data = data;
        this.factory = (SessionFactory)request.getServletContext().getAttribute("sessionFactory");
        status = (RequestStatus) this.request.getSession().getAttribute("requestStatus");
    }
}
