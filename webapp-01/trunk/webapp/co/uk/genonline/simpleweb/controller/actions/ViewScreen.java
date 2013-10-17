package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.controller.screendata.MistressScreenData;
import co.uk.genonline.simpleweb.model.bean.Screens;
import co.uk.genonline.simpleweb.web.WebHelper;
import co.uk.genonline.simpleweb.web.gallery.GalleryManager;
import com.petebevin.markdown.MarkdownProcessor;
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
        WebHelper webHelper = new WebHelper(request, response);
        Screens screenRecord;
        MistressScreenData screenData = new MistressScreenData();

        status.resetStatusMessage();
        if (screen.getName() == null || screen.getName().equals("")) {
            screen.setName(webHelper.getHomePage());
        }
        logger.info("view: screen is " + screen.getName());
//        List<Screens> pages = screenBeanManager.getCategoryScreens("Chambers");

        screenData.setChambersLinkBar(webHelper.generateLinkBarCategory("Chambers"));
        screenData.setMistressPageLink(webHelper.getScreenLink("mistresses", screenBeanManager.getShortName("mistresses")));
        screenData.setHomePage(webHelper.generateHomeLink());
        screenData.setMaxThumbnailWidth(request.getServletContext().getInitParameter("maxThumbnailWidth"));
        screenData.setMaxThumbnailHeight(request.getServletContext().getInitParameter("maxThumbnailHeight"));


        request.setAttribute("chambersLinkBar", webHelper.generateLinkBarCategory("Chambers"));
        request.setAttribute("mistressPageLink", webHelper.getScreenLink("mistresses", screenBeanManager.getShortName("mistresses")));
        request.setAttribute("homePage", webHelper.generateHomeLink());
        request.setAttribute("maxThumbnailWidth", request.getServletContext().getInitParameter("maxThumbnailWidth"));
        request.setAttribute("maxThumbnailHeight", request.getServletContext().getInitParameter("maxThumbnailHeight"));

        if (request.getServletContext().getInitParameter("blogEnabled").equals("true")) {
            request.setAttribute("blogLink", webHelper.generateBlogLink());
        } else {
            request.setAttribute("blogLink", null);
        }

        screenRecord = screenBeanManager.getScreen(screen);

        if (screenRecord == null) {
            logger.warn(String.format("View page: Couldn't retrieve page <%s>", this.screen.getName()));
            response.setStatus(404);
            return new RequestResult(jspLocation("error.jsp"), false);
        } else {
            logger.debug("About to parse page with Markdown");
            String pageText = screenRecord.getScreenContents();
            logger.debug("Markdown Input text is " + pageText.substring(0, Math.min(39, pageText.length()))+"...");
            String HTML = markdownDecoder.markdown(pageText);
            logger.debug("Markdown Output HTML is " + HTML.substring(0, Math.min(39, HTML.length()))+"...");
            this.screen.setScreenContents(HTML);
            this.screen.setMetaDescription(screenRecord.getMetaDescription());
            this.screen.setScreenTitleLong(screenRecord.getScreenTitleLong());
            this.screen.setScreenTitleShort(screenRecord.getScreenTitleShort());
            if (screenRecord.isEnabledFlag()) {
                if (screenRecord.isGalleryFlag()) {
                    logger.info("About to create gallery for the page");
                    GalleryManager manager = (GalleryManager)request.getServletContext().getAttribute("Galleries");
                    logger.debug("manager = " + manager);
                    screenData.setGalleryHtml(((manager.getGallery(screenRecord.getName())).getHTML()));
                } else {
                    logger.debug("This page is not a gallery: " + screenRecord.getName());
                    screenData.setGalleryHtml("");
                }
            } else {
                logger.info(String.format("Screen disabled, treating like non-existent page <%s>", screenRecord.getName()));
                response.setStatus(404);
                return new RequestResult(jspLocation("error.jsp"),false);
            }
        }
        request.setAttribute("mistressScreenData", screenData);
        return new RequestResult(jspLocation(screenData.getJSPname()), false);

    }
}
