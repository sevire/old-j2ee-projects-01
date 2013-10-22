package co.uk.genonline.simpleweb.controller.actions;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 23/09/2012
 * Time: 21:36
 * To change this template use File | Settings | File Templates.
 */
public class RequestResult {
    String nextRequest;
    boolean redirectFlag;
    HttpServletRequest request;

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

    protected String jspLocation(String page) {
        return "WEB-INF/" + page;
    }

    protected String URLwithContext(String URL) {
        return request.getServletContext().getContextPath() + URL;
    }

}
