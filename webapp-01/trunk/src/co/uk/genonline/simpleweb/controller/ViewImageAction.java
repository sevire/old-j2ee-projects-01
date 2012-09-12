package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
public class ViewImageAction extends ActionClass {

    public ViewImageAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    public String perform() {
        String gallery = request.getParameter("gallery");
        String image = request.getParameter("image");
        String img = "/galleries/" + gallery + "/" + request.getParameter("image");
        logger.debug(String.format("Displaying image for gallery <%s>, image <%s>, img = <%s>", gallery, image, img));
        request.setAttribute("gallery", gallery);
        request.setAttribute("image", img);
        return jspLocation("viewImage.jsp");
    }
}
