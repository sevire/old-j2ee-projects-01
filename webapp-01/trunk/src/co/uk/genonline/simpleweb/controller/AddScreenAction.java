package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddScreenAction extends ActionClass {

    public AddScreenAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    public String perform() {
        String screen = request.getParameter("name");
        logger.info(String.format("Adding screen <%s>", screen));
        logger.info("Screen contents from form... ");
        logger.info(request.getParameter("screenContents"));
        data.setId(0);
        data.setName(screen);
        boolean enabledChecked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");
        boolean galleryChecked = request.getParameter("galleryFlag") == null ? false : request.getParameter("galleryFlag").equals("Enabled");
        data.setEnabledFlag(enabledChecked);
        data.setGalleryFlag(galleryChecked);
        data.setScreenContents(request.getParameter("screenContents"));
        data.setScreenTitleLong(request.getParameter("screenTitleLong"));
        data.setScreenTitleShort(request.getParameter("screenTitleShort"));
        data.setScreenType(request.getParameter("screenType"));

        Session session = factory.openSession();
        logger.info(String.format("About to save data"));
        logger.info("Contents = ");
        logger.info(data.getScreenContents());
        session.save(data);
        resetStatusMessage();
        try {
            session.flush();
        } catch (Exception e) {
            setStatusMessage(e.getMessage(), "error");
            // Would like to send back to addScreen but redirect flag is hard coded in controller!
            // I always knew that would be a problem eventually.
        }
        return URLwithContext("/editIndex");
    }
}









