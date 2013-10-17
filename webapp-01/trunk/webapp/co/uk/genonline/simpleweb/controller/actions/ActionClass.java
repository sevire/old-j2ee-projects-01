package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.model.bean.ConfigItems;
import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;
import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:19
 * To change this template use File | Settings | File Templates.
 */
abstract class ActionClass extends Action {
    WebLogger logger = new WebLogger();
    HttpServletRequest request;
    HttpServletResponse response;
    SessionFactory factory;
    Screens screen; // ToDo: Should this really be here - this is a general purpose class?
    RequestStatus status;
    ConfigItems configItems;
    ScreenBeanManager screenBeanManager;

    public ActionClass(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
//        logger.setLevel(Level.toLevel(request.getServletContext().getInitParameter("loggingLevel")));
        this.request = request;
        this.response = response;
        this.factory = (SessionFactory)request.getServletContext().getAttribute("sessionFactory");
        this.screen = data.getScreen();
        this.configItems = data.getConfigItems();
        this.screenBeanManager = new ScreenBeanManager(factory);

        logger.info("Initialised data = <%s>", data.toString());

        status = (RequestStatus) this.request.getSession().getAttribute("requestStatus");
    }

    public ActionClass() {}

    protected String jspLocation(String page) {
        return "WEB-INF/" + page;
    }

    protected String URLwithContext(String URL) {
        return request.getServletContext().getContextPath() + URL;
    }
}
