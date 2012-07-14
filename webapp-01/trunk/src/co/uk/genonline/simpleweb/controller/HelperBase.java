package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.web.SessionData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 16/05/2012
 * Time: 07:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class HelperBase {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public HelperBase(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public abstract void copyFromSession(Object helper);

    public void addHelperToSession(String name, SessionData state) {
        if (SessionData.READ == state) {
            Object sessionObj = request.getSession().getAttribute(name);
            if (sessionObj != null) {
                copyFromSession(sessionObj);
            }
        }
        request.getSession().setAttribute(name, this);
    }
}
