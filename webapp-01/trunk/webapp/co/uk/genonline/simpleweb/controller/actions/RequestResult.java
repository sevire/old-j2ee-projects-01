package co.uk.genonline.simpleweb.controller.actions;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 23/09/2012
 * Time: 21:36
 *
 * Used to indicate to the controller what to do once a request has been processed.  The options are:
 * - Forward control to a JSP.  This is typically used when the request is a link from another
 *   page such as display a particular screen.  The controller will invoke the action implied
 *   within the URL from the request and the action will then invoke a JSP to display the
 *   results
 * - Re-direct control to a new URL.  This is typically used once a transaction has been processed
 *   and the intention is then to flow to another page on the browser (such as a screen updated
 *   page).
 *
 */
public class RequestResult {
    private String nextRequest;
    private boolean redirectFlag;
    private HttpServletRequest request;

    /**
     * The class is typically instantiated from an Action object (or sub-type) and this is the
     * way the Action object indicates to the controller what should happen next.
     *
     * @param request Used to calculate the path of the URL to redirect to.  Could do this within the
     *                Controller.  ToDo: Move redirect path calculation to Controller
     * @param nextRequest
     * @param redirectFlag
     */
    public RequestResult(HttpServletRequest request, String nextRequest, boolean redirectFlag) {
        this.request = request;
        this.redirectFlag = redirectFlag;
        if (this.redirectFlag) {
            this.nextRequest = URLwithContext(nextRequest); // We are re-directing using a URL
        } else {
            this.nextRequest = jspLocation(nextRequest); // We are forwarding to another jsp within web app
        }
    }

    public boolean isRedirectFlag() {
        return redirectFlag;
    }

    public String getNextRequest() {
        return nextRequest;
    }

    String jspLocation(String page) {
        return "WEB-INF/" + page;
    }

    String URLwithContext(String URL) {
        return request.getServletContext().getContextPath() + URL;
    }

}
