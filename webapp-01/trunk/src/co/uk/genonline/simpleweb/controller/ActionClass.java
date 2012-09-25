package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
abstract class ActionClass implements Action {
    Logger logger;
    HttpServletRequest request;
    HttpServletResponse response;
    SessionFactory factory;
    Screens data;
    RequestStatus status;

    public ActionClass(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        logger = Logger.getLogger(this.getClass().getName());
        logger.setLevel(Level.ALL);
        logger.info("Logger initiated - " + logger.getName());

        this.request = request;
        this.response = response;
        this.factory = factory;
        this.data = data;

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
