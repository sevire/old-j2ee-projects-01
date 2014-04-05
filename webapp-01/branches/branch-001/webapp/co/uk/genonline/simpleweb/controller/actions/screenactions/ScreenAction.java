package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.actions.Action;
import co.uk.genonline.simpleweb.controller.actions.ActionData;
import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
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
public abstract class ScreenAction extends Action {
    protected final HttpServletRequest request;
    final HttpServletResponse response;
    final ActionData data;
    final SessionFactory factory;
    final ScreensEntity screen;
    protected final RequestStatus status;
    final ScreenBeanManager screenBeanManager;

    protected ScreenAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super();
        this.request = request;
        this.response = response;
        this.data = data;
        this.factory = (SessionFactory)request.getServletContext().getAttribute("sessionFactory");
        this.screen = data.getScreen();
        screen.setName(request.getParameter("screen"));
        this.screenBeanManager = new ScreenBeanManager(factory);
        status = (RequestStatus) this.request.getSession().getAttribute("requestStatus");
    }

}
