package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

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

    public RequestResult perform() {
        String separator = File.separator;
        String galleryRoot = (String) request.getServletContext().getInitParameter("galleryRoot");
        String gallery = request.getParameter("gallery");
        String image = request.getParameter("image");
        String img = galleryRoot + separator + gallery + separator + request.getParameter("image");
        logger.debug(String.format("Displaying image for gallery <%s>, image <%s>, img = <%s>", gallery, image, img));
        request.setAttribute("gallery", gallery);
        request.setAttribute("image", img);
        request.setAttribute("maxWidth", request.getServletContext().getInitParameter("maxImageWidth"));
        return new RequestResult(jspLocation("viewImage.jsp"), false);
    }
}
