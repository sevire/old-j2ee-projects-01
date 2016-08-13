package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.configuration.configitems.GalleryRootFullPath;
import co.uk.genonline.simpleweb.configuration.configitems.MaxImageWidth;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;

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
public class ViewImage extends ScreenAction {

    public ViewImage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public RequestResult perform() {
        Configuration configuration = (Configuration)request.getAttribute("configuration");
        String galleryRoot = ((GalleryRootFullPath)configuration.getConfigurationItem("galleryRoot")).get();
        int maxImageWidth = ((MaxImageWidth)(configuration.getConfigurationItem("maxImageWidth"))).get();

        String separator = File.separator;
        String gallery = request.getParameter("gallery");
        String image = request.getParameter("image");
        String img = galleryRoot + separator + gallery + separator + request.getParameter("image");
        logger.debug(String.format("Displaying image for gallery <%s>, image <%s>, img = <%s>", gallery, image, img));
        request.setAttribute("gallery", gallery);
        request.setAttribute("image", img);
        request.setAttribute("maxWidth", maxImageWidth);
        return new RequestResult(request, "viewImage.jsp", false);
    }
}
