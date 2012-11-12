package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddScreenAction extends ActionClass {

    public AddScreenAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        String screen = request.getParameter("name");
        logger.info(String.format("Adding screen <%s>", screen));

        if (screen.equals("")) {
            status.setStatusMessage("Screen name cannot be blank", "error");
            return new RequestResult(jspLocation("addScreen.jsp"), false);
        } else {
            logger.debug("Screen contents from form... ");
            logger.debug(request.getParameter("screenContents"));
            data.setId(0);
            data.setName(screen);
            data.setSortKey(Integer.parseInt(request.getParameter("sortKey")));
            boolean enabledChecked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");
            boolean galleryChecked = request.getParameter("galleryFlag") == null ? false : request.getParameter("galleryFlag").equals("Enabled");
            data.setEnabledFlag(enabledChecked);
            data.setGalleryFlag(galleryChecked);
            data.setScreenContents(request.getParameter("screenContents"));
            data.setScreenTitleLong(request.getParameter("screenTitleLong"));
            data.setScreenTitleShort(request.getParameter("screenTitleShort"));
            data.setScreenType(request.getParameter("screenType"));

            Session session = factory.openSession();
            logger.debug(String.format("About to save data"));
            logger.debug("Contents = ");
            logger.debug(data.getScreenContents());
            session.save(data);
            status.resetStatusMessage();
            try {
                session.flush();
            } catch (Exception e) {
                status.setStatusMessage(e.getMessage(), "error");
                return new RequestResult(jspLocation("addScreen.jsp"), false);
            }
            status.setStatusMessage("Screen added", "info");
            return new RequestResult(URLwithContext("/editIndex"), true);
        }
    }
}









