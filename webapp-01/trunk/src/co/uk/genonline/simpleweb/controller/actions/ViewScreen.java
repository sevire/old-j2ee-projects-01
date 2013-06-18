package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.Screens;
import co.uk.genonline.simpleweb.web.WebHelper;
import co.uk.genonline.simpleweb.web.gallery.GalleryManager;
import com.petebevin.markdown.MarkdownProcessor;
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
public class ViewScreen extends ActionClass {

    public ViewScreen(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        MarkdownProcessor markdownDecoder = new MarkdownProcessor();
        Session session = factory.openSession();
        String query = String.format("from Screens s where s.name = '%s'", this.screen.getName());
        WebHelper helper = new WebHelper(request, response, factory);

        request.setAttribute("chambersLinkBar", helper.generateLinkBarCategory("Chambers"));
        request.setAttribute("mistressPageLink", helper.getScreenLink("mistresses"));
        request.setAttribute("homePage", helper.generateHomeLink());
        request.setAttribute("maxImgWidth", request.getServletContext().getInitParameter("maxThumbnailWidth"));
        request.setAttribute("maxImgHeight", request.getServletContext().getInitParameter("maxThumbnailHeight"));

        if (request.getServletContext().getInitParameter("blogEnabled").equals("true")) {
            request.setAttribute("blogLink", helper.generateBlogLink());
        } else
            request.setAttribute("blogLink", null);
        logger.debug("About to execute HQL query : " + query);

        java.util.List pages = session.createQuery(query).list();

        if (pages.size() != 1) {
            logger.warn(String.format("View page: retrieved <%d> pages for screen <%s>, should be 1",
                    pages.size(), this.screen.getName()));
            response.setStatus(404);
            return new RequestResult(jspLocation("error.jsp"), false);
        }

        if (pages.size() > 0) {
            Screens screen = (Screens) pages.get(0);
            logger.debug("About to parse page with Markdown");
            String pageText = screen.getScreenContents();
            logger.debug("Markdown Input text is " + pageText.substring(0, Math.min(39, pageText.length()))+"...");
            String HTML = markdownDecoder.markdown(pageText);
            logger.debug("Markdown Output HTML is " + HTML.substring(0, Math.min(39, HTML.length()))+"...");
            this.screen.setScreenContents(HTML);
            this.screen.setMetaDescription(screen.getMetaDescription());
            this.screen.setScreenTitleLong(screen.getScreenTitleLong());
            this.screen.setScreenTitleShort(screen.getScreenTitleShort());
            if (screen.isEnabledFlag()) {
                if (screen.isGalleryFlag()) {
                    logger.info("About to create gallery for the page");
                    GalleryManager manager = (GalleryManager)request.getServletContext().getAttribute("Galleries");
                    logger.debug("manager = " + manager);
                    request.setAttribute("galleryHTML", (manager.getGallery(screen.getName())).getHTML());
                } else {
                    logger.debug("This page is not a gallery: " + screen.getName());
                    request.setAttribute("galleryHTML", "");
                }
            } else {
                logger.info(String.format("Screen disabled, treating like non-existent page <%s>", ((Screens) pages.get(0)).isEnabledFlag()));
                response.setStatus(404);
                return new RequestResult(jspLocation("error.jsp"),false);
            }
        }
        return new RequestResult(jspLocation("screen.jsp"), false);

    }
}
