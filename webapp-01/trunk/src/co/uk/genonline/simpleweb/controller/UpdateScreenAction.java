package co.uk.genonline.simpleweb.controller;

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

    public String perform() {
        String screen = request.getParameter("name");
        logger.info(String.format("Updating screen <%s>", screen));
        logger.info("Screen contents from form... ");
        logger.info(request.getParameter("screenContents"));
        data.setName(screen);

        boolean checked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");

        logger.info("Enabled flag is: " + checked);
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
        logger.info("Contents = ");
        logger.info(data.getScreenContents());
        session.update(data);
        session.flush();
        return "/editIndex";
    }
}
