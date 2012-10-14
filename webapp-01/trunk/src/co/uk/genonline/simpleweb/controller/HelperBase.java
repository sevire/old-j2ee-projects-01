package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.web.SessionData;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

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
    Logger logger;

    public HelperBase(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        logger = Logger.getLogger("ControllerHelper");
        logger.setLevel(Level.toLevel(request.getServletContext().getInitParameter("loggingLevel")));
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

    public void fillBeanFromRequest(Object data) {
        try {
            BeanUtils.populate(data, request.getParameterMap());
        } catch (IllegalAccessException e) {
            logger.error("Populate - Illegal Access", e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            logger.error("Populate - Invocation Target", e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


}
