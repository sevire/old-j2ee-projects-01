package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.WebLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:02
 *
 * Supertype used within Controller (ControllerHelper) to process incoming requests. Each request will need
 * a sub-type of this class.  The appropriate sub-type of Action is selected by ActionFactory on the
 * incoming request.
 */
public abstract class Action {

    final protected WebLogger logger = new WebLogger();
    final protected HttpServletRequest request;
    final protected HttpServletResponse response;
    final protected RequestStatus status;
    final protected Configuration configuration;

    protected Action(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Instantiating Action sub-class <%s>", this.getClass().getName());
        this.request = request;
        this.response = response;
        status = (RequestStatus) this.request.getSession().getAttribute("requestStatus");
        configuration = (Configuration) this.request.getServletContext().getAttribute("configuration");
    }

    /**
     * The method which must be implemented in a concrete implementation to carry out the processing
     * required by this Action.
     *
     * @return Tells the controller what to do next (forward or redirect)
     */
    public abstract RequestResult perform();

    public String toString() {
        return String.format("Request: <%s>, Response: <%s>, Status: <%s>, Configuration: <%s>",
                request, response, status, configuration);
    }

}
