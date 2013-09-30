package co.uk.genonline.simpleweb.controller.actions;

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

    RequestResult(String nextRequest, boolean redirectFlag) {
        this.nextRequest = nextRequest;
        this.redirectFlag = redirectFlag;
    }

    public boolean isRedirectFlag() {
        return redirectFlag;
    }

    public String getNextRequest() {
        return nextRequest;
    }
}
