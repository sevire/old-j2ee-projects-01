package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.controller.actions.ActionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;
import co.uk.genonline.simpleweb.model.bean.Screens;
import co.uk.genonline.simpleweb.web.WebHelper;
import co.uk.genonline.simpleweb.web.gallery.GalleryManager;
import com.petebevin.markdown.MarkdownProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 12/10/2013
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class MistressScreenData implements ScreenData {

    private WebLogger logger = new WebLogger();
    private String chambersLinkBar;
    private String mistressPageLink;
    private String homePage;
    private String maxThumbnailWidth;
    private String maxThumbnailHeight;
    private String blogLink;
    private String galleryHtml;
    private Screens screen;

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getMaxThumbnailWidth() {
        return maxThumbnailWidth;
    }

    public void setMaxThumbnailWidth(String maxImgWidth) {
        this.maxThumbnailWidth = maxImgWidth;
    }

    public String getMaxThumbnailHeight() {
        return maxThumbnailHeight;
    }

    public void setMaxThumbnailHeight(String maxImgHeight) {
        this.maxThumbnailHeight = maxImgHeight;
    }


    public String getJSPname() {
        return "screen.jsp";
    }

    public RequestResult setScreenData(HttpServletRequest request,
                                       HttpServletResponse response,
                                       ScreenBeanManager screenBeanManager,
                                       ActionData data) {
        MarkdownProcessor markdownDecoder = new MarkdownProcessor();
        WebHelper webHelper = new WebHelper(request, response);
        Screens screenRecord;
        setScreen(data.getScreen());
        RequestStatus status = (RequestStatus) request.getSession().getAttribute("requestStatus");

        status.resetStatusMessage();
        if (getScreen().getName() == null || getScreen().getName().equals("")) {
            getScreen().setName(webHelper.getHomePage());
        }
        logger.info("view: screen is " + getScreen().getName());
        setChambersLinkBar(webHelper.generateLinkBarCategory("Chambers"));
        setMistressPageLink(webHelper.getScreenLink("mistresses", screenBeanManager.getShortName("mistresses")));
        setHomePage(webHelper.generateHomeLink());
        setMaxThumbnailWidth(request.getServletContext().getInitParameter("maxThumbnailWidth"));
        setMaxThumbnailHeight(request.getServletContext().getInitParameter("maxThumbnailHeight"));
        if (request.getServletContext().getInitParameter("blogEnabled").equals("true")) {
            request.setAttribute("blogLink", webHelper.generateBlogLink());
        } else {
            request.setAttribute("blogLink", null);
        }

        screenRecord = screenBeanManager.getScreen(screen);

        if (screenRecord == null) {
            logger.warn(String.format("View page: Couldn't retrieve page <%s>", getScreen().getName()));
            response.setStatus(404);
            return new RequestResult(request, "error.jsp", false);
        } else {
            logger.debug("About to parse page with Markdown");
            String pageText = screenRecord.getScreenContents();
            logger.debug("Markdown Input text is " + pageText.substring(0, Math.min(39, pageText.length()))+"...");
            String HTML = markdownDecoder.markdown(pageText);
            logger.debug("Markdown Output HTML is " + HTML.substring(0, Math.min(39, HTML.length()))+"...");
            getScreen().setScreenContents(HTML);
            getScreen().setMetaDescription(screenRecord.getMetaDescription());
            getScreen().setScreenTitleLong(screenRecord.getScreenTitleLong());
            getScreen().setScreenTitleShort(screenRecord.getScreenTitleShort());
            if (screenRecord.isEnabledFlag()) {
                if (screenRecord.isGalleryFlag()) {
                    logger.info("About to create gallery for the page");
                    GalleryManager manager = (GalleryManager)request.getServletContext().getAttribute("Galleries");
                    logger.debug("manager = " + manager);
                    setGalleryHtml(((manager.getGallery(screenRecord.getName())).getHTML()));
                } else {
                    logger.debug("This page is not a gallery: " + screenRecord.getName());
                    setGalleryHtml("");
                }
                return new RequestResult(request, "screen.jsp", false);
            } else {
                logger.info(String.format("Screen disabled, treating like non-existent page <%s>", screenRecord.getName()));
                response.setStatus(404);
                return new RequestResult(request, "error.jsp", false);
            }
        }
    }

    public void setChambersLinkBar(String linkBar) {
        this.chambersLinkBar = linkBar;
    }

    public void setMistressPageLink(String link) {
        this.mistressPageLink = link;
    }

    public String getChambersLinkBar() {
        return chambersLinkBar;
    }

    public String getMistressPageLink() {
        return mistressPageLink;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    public void setGalleryHtml(String galleryHtml) {
        this.galleryHtml = galleryHtml;
    }

    public String getGalleryHtml() {
        return galleryHtml;
    }

    public Screens getScreen() {
        return screen;
    }

    public void setScreen(Screens screen) {
        this.screen = screen;
    }
}