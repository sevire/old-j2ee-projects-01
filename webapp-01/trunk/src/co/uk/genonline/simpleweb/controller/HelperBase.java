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

    /**
     * The helper object (ControllerHelper) is stored in the HttpSession between requests to allow data to be
     * persisted throughout a session.  This is key to the correct operation of the application.  The EE framework
     * does all the work of identifying when a request is part of an existing session - all the application
     * developer needs to do is to ensure that the appropriate data is stored in the session object so that it is there
     * for the next action within this session.
     *
     * So the addHelper method carries out the following:
     *
     * <ul>
     * <li>Checks state (SessionData).</li>
     * <li>If state is READ then data from the previous request should be persisted into
     *   this request. This is carried out through the call to copyFromSession.</li>
     * <li>If state is IGNORE then data from previous request will be ignored (and effectively lost).</li>
     * <li>Then this helper will be added to the HttpSession object in place of the previous helper.  This is
     *   to persist data for the next request.</li>
     * </ul>
     *
     * @param name The name to use when reading the object
     * @param state READ = Retain data from previous request, IGNORE don't pull in data across requests
     */
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
