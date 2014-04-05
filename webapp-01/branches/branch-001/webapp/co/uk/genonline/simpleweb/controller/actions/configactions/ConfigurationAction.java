package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.actions.Action;
import co.uk.genonline.simpleweb.controller.actions.ActionData;
import co.uk.genonline.simpleweb.model.bean.ConfigurationEntity;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 26/10/2013
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
abstract class ConfigurationAction extends Action {
    final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ActionData data;
    final SessionFactory factory;
    final ConfigurationEntity configItems;
    final RequestStatus status;

    ConfigurationAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super();
        this.request = request;
        this.response = response;
        this.data = data;
        this.factory = (SessionFactory)request.getServletContext().getAttribute("sessionFactory");
        this.configItems = data.getConfigItems();
        configItems.setName(request.getParameter("name"));
        this.status = (RequestStatus) this.request.getSession().getAttribute("requestStatus");

    }
}
