package co.uk.genonline.simpleweb.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 16/05/2012
 * Time: 07:31
 * To change this template use File | Settings | File Templates.
 */
public class HelperBase {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public HelperBase(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
}
