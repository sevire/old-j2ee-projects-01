package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.Session;
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
public class UpdateScreenAction extends ActionClass {

    public UpdateScreenAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        String screen = request.getParameter("name");
        logger.info(String.format("Updating screen <%s>", screen));
        logger.debug("Screen contents from form... ");
        logger.debug(request.getParameter("screenContents"));
        data.setName(screen);

        try {
            data.setSortKey(Integer.parseInt(request.getParameter("sortKey")));
        } catch (Exception e) {
            logger.warn("Invalid sort key entered, using 100", e);
            data.setSortKey(100);
        }

        boolean checked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");

        logger.debug("Enabled flag is: " + checked);
        boolean enabledChecked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");
        boolean galleryChecked = request.getParameter("galleryFlag") == null ? false : request.getParameter("galleryFlag").equals("Enabled");
        data.setEnabledFlag(enabledChecked);
        data.setGalleryFlag(galleryChecked);
        data.setScreenContents(request.getParameter("screenContents"));
        data.setScreenTitleLong(request.getParameter("screenTitleLong"));
        data.setScreenTitleShort(request.getParameter("screenTitleShort"));
        data.setScreenType(request.getParameter("screenType"));

        Session session = factory.openSession();
        logger.info(String.format("About to update data, id is <%d>", data.getId()));
        logger.debug("Contents = ");
        logger.debug(data.getScreenContents());
        session.update(data);
        try {
            session.flush();
        } catch (Exception e) {
            status.setStatusMessage(e.getMessage(), "error");
            return new RequestResult(jspLocation("editScreen.jsp"), false);
        }
        status.setStatusMessage("Screen updated", "info");
        return new RequestResult(URLwithContext("/editIndex"), true);
    }
}
