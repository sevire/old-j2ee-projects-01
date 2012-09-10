package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.model.bean.Screens;
import co.uk.genonline.simpleweb.web.SessionData;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 16/05/2012
 * Time: 07:38
 * To change this template use File | Settings | File Templates.
 */
public class ControllerHelper extends HelperBase {
    protected Screens data;
    Logger logger;
    SessionFactory factory;

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response,
                            SessionFactory factory) {
        super(request, response);

        //System.out.println("getLogger");
        logger = Logger.getLogger("ControllerHelper");
        logger.setLevel(Level.ALL);
        logger.info("Logger initiated - " + logger.getName());
        data = new Screens();
        this.factory = factory;
    }

    public Object getData() {
        return data;
    }

    protected String viewImageMethod() {
        String gallery = request.getParameter("gallery");
        String image = request.getParameter("image");
        String img = "/galleries/" + gallery + "/" + request.getParameter("image");
        logger.debug(String.format("Displaying image for gallery <%s>, image <%s>, img = <%s>", gallery, image, img));
        request.setAttribute("gallery", gallery);
        request.setAttribute("image", img);
        return oldJspLocation("viewImage.jsp");
    }

    protected String updateMethod() {
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

    protected String addMethod() {
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
        session.flush();
        return "/editIndex";
    }

    protected String cancelMethod() {
        return "/editIndex";
    }

    protected void doPost()
        throws ServletException, IOException {

        addHelperToSession("helper", SessionData.READ);

        String address;
        boolean redirectFlag = false;

        if (request.getParameter("updateButton") != null) {
            address = updateMethod();
            redirectFlag = true;
        } else if (request.getParameter("addButton") != null) {
            address = addMethod();
            redirectFlag = true;
        } else if (request.getParameter("cancelButton") != null) {
            address = cancelMethod();
            redirectFlag = true;
        } else {
            logger.error(String.format("DoPost1: Didn't recognise request, defaulting to screen view"));
            address = "/view?screen=Home";
            redirectFlag = true;
        }
        if (redirectFlag) {
            response.sendRedirect(address);
        } else {
            logger.info("(doPost): Forwarding to " + address);
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
        }
    }

    protected void doGet() throws IOException, ServletException {

        addHelperToSession("helper", SessionData.READ);
        String command = request.getServletPath();
        logger.info("Servlet path is " + command);

        data.setName(request.getParameter("screen"));

        String address;
        boolean redirectFlag = false;

        if (command.equals("/view")) {
            logger.info("view: screen is " + data.getName());
            Action action = new ViewPageAction(request, response, factory, data);
            address = action.perform();
        } else if (command.equals("/edit")) {
            logger.info("edit: screen is " + data.getName());
            Action action = new EditPageAction(request,response, factory, data);
            address = action.perform();
        } else if (command.equals("/add")) {
            logger.info("add: screen is " + data.getName());
            Action action = new AddAction(request,response, factory, data);
            address = action.perform();
        } else if (command.equals("/editIndex")) {
            logger.info("editIndex");
            Action action = new EditIndexAction(request,response, factory, data);
            address = action.perform();
        } else if (command.equals("/delete")) {
            logger.info("delete: screen is " + data.getName());
            Action action = new DeleteAction(request,response, factory, data);
            address = action.perform();
            redirectFlag = true; // ToDo: remove need for this in here
        } else if (command.equals("/viewImage")) {
            logger.info("Gallery View");
            address = viewImageMethod();
        } else {
            logger.error(String.format("DoGet: Didn't recognise request, defaulting to screen view"));
            address = oldJspLocation("screen.jsp");
            data.setName("Home");
        }
        if (redirectFlag) {
            response.sendRedirect(address);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
        }
    }

    protected String oldJspLocation(String page) {
        return "WEB-INF/" + page;
    }

    public void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            data = ((ControllerHelper)sessionHelper).data;
        }
    }

}
