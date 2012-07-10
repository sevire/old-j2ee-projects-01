package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.model.bean.Screens;
import co.uk.genonline.simpleweb.web.gallery.GalleryManager;
import com.petebevin.markdown.MarkdownProcessor;
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

    protected String editMethod() {
        Session session = factory.openSession();
        String query = String.format("from Screens s where s.name = '%s'", data.getName());
        logger.info("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        data.setId(((Screens) pages.get(0)).getId());
        data.setEnabledFlag(((Screens) pages.get(0)).isEnabledFlag());
        data.setScreenContents(((Screens) pages.get(0)).getScreenContents());
        data.setScreenTitleLong(((Screens) pages.get(0)).getScreenTitleLong());
        data.setScreenTitleShort(((Screens) pages.get(0)).getScreenTitleShort());
        data.setScreenType(((Screens) pages.get(0)).getScreenType());
        //request.setAttribute("screenTypes", );
        return jspLocation("editScreen.jsp");
    }

    protected String viewMethod() {
        MarkdownProcessor markdownDecoder = new MarkdownProcessor();
        Session session = factory.openSession();
        String query = String.format("from Screens s where s.name = '%s'", data.getName());
        WebHelper helper = new WebHelper(request, response, factory);

        request.setAttribute("chambersLinkBar", helper.generateLinkBarCategory("Chambers"));
        request.setAttribute("mistressLinkBar", helper.generateLinkBarCategory("Mistress"));
        request.setAttribute("homePage", helper.generateHomeLink());

        logger.info("About to execute HQL query : " + query);

        java.util.List pages = session.createQuery(query).list();

        if (pages.size() != 1) {
            logger.warn(String.format("View page: retrieved <%d> pages for screen <%s>, should be 1",
                    pages.size(), data.getName()));
            response.setStatus(404);
            return jspLocation("error.jsp");
        }

        if (pages.size() > 0) {
            Screens screen = (Screens) pages.get(0);
            logger.info("About to parse page with Markdown");
            String pageText = screen.getScreenContents();
            logger.info("Markdown Input text is " + pageText.substring(0, Math.min(39, pageText.length()))+"...");
            String HTML = markdownDecoder.markdown(pageText);
            logger.info("Markdown Output HTML is " + HTML.substring(0, Math.min(39, HTML.length()))+"...");
            data.setScreenContents(HTML);
            data.setScreenTitleLong(screen.getScreenTitleLong());
            data.setScreenTitleShort(screen.getScreenTitleShort());
            if (screen.isEnabledFlag()) {
                if (screen.isGalleryFlag()) {
                    logger.info("About to create gallery for the page");
                    GalleryManager manager = (GalleryManager)request.getServletContext().getAttribute("Galleries");
                    logger.debug("manager = " + manager);
                    request.setAttribute("galleryHTML", (manager.getGallery(screen.getName())).getHTML());
                } else {
                    request.setAttribute("galleryHTML", "<p>No Gallery</p>");
                }
            } else {
                logger.info(String.format("Screen disabled, treating like non-existent page <%s>", ((Screens) pages.get(0)).isEnabledFlag()));
                response.setStatus(404);
                return jspLocation("error.jsp");
            }
        }
        return jspLocation("screen.jsp");
    }

    protected String viewImageMethod() {
        String gallery = request.getParameter("gallery");
        String image = request.getParameter("image");
        String img = "/galleries/" + gallery + "/" + request.getParameter("image");
        logger.debug(String.format("Displaying image for gallery <%s>, image <%s>, img = <%s>", gallery, image, img));
        request.setAttribute("gallery", gallery);
        request.setAttribute("image", img);
        return jspLocation("viewImage.jsp");
    }

    protected String updateMethod() {
        String screen = request.getParameter("name");
        logger.info(String.format("Updating screen <%s>", screen));
        logger.info("Screen contents from form... ");
        logger.info(request.getParameter("screenContents"));
        data.setName(screen);

        boolean checked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");

        logger.info("Enabled flag is: " + checked);
        data.setEnabledFlag(checked);
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
        return jspLocation("/editIndex");
    }

    protected String addMethod() {
        String screen = request.getParameter("name");
        logger.info(String.format("Updating screen <%s>", screen));
        logger.info("Screen contents from form... ");
        logger.info(request.getParameter("screenContents"));
        data.setId(0);
        data.setName(screen);
        boolean checked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");
        data.setEnabledFlag(checked);
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
        return jspLocation("/editIndex");
    }

    protected String deleteMethod() {
        String screen = request.getParameter("screen");

        logger.info(String.format("Deleting screen <%s>", screen));

        String query = String.format("from Screens s where s.name = '%s'", screen);

        logger.info("About to execute HQL query : " + query);

        Session session = factory.openSession();
        java.util.List pages = session.createQuery(query).list();
        session.delete(((Screens) pages.get(0)));
        session.flush();
        return jspLocation("/editIndex");
    }

    protected String editIndexMethod() {
        Session session = factory.openSession();
        String query = String.format("from Screens s");
        logger.info("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        for (Object s : pages) {
            String contents = ((Screens)s).getScreenContents();
            ((Screens)s).setScreenContents(contents.substring(0, Math.min(39, contents.length()))+"...");
        }
        request.setAttribute("editList", pages);
        return jspLocation("editIndex.jsp");
    }

    protected String cancelMethod() {
        return jspLocation("/editIndex");
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
            address = viewMethod();
        } else if (command.equals("/edit")) {
            logger.info("edit: screen is " + data.getName());
            address = editMethod();
        } else if (command.equals("/add")) {
            address = jspLocation("addScreen.jsp");
        } else if (command.equals("/editIndex")) {
            logger.info("editIndex");
            address = editIndexMethod();
        } else if (command.equals("/delete")) {
            logger.info("delete");
            redirectFlag = true;
            address = deleteMethod();
        } else if (command.equals("/viewImage")) {
            logger.info("Gallery View");
            address = viewImageMethod();
        } else {
            logger.error(String.format("DoGet: Didn't recognise request, defaulting to screen view"));
            address = jspLocation("screen.jsp");
            data.setName("Home");
        }
        if (redirectFlag) {
            response.sendRedirect(address);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(address);
            dispatcher.forward(request, response);
        }
    }

    public void copyFromSession(Object sessionHelper) {
        if (sessionHelper.getClass() == this.getClass()) {
            data = ((ControllerHelper)sessionHelper).data;
        }
    }

    protected String jspLocation(String page) {
        return page;
    }
}
