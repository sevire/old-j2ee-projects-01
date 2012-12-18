package co.uk.genonline.simpleweb.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Level;

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
    WebLogger logger = new WebLogger();

    public HelperBase(HttpServletRequest request, HttpServletResponse response) {
        super();
        Level level;
        this.request = request;
        this.response = response;
        logger.setSession(request);
        level = Level.toLevel(request.getServletContext().getInitParameter("loggingLevel"));
        //level = Level.INFO; // HTTPunit frig
        logger.setLevel(level);
    }

    public abstract void copyFromSession(Object helper);

    public void addHelperToSession(String name, SessionData state) {
        /*
         * If the session already has an attribute for a helper object <name> then there is data already stored for
         * this session so copy data from that into this session.
         *
         * Then replace any stored session (if there was one) with this (current helper).
         */
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
            logger.error("Populate - Illegal Access", e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            logger.error("Populate - Invocation Target", e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


}
